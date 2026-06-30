package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.attachment.entity.DroppedItemAttachment;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
import com.aetherteam.aetherii.attachment.player.AerbunnyMountAttachment;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
import com.aetherteam.aetherii.attachment.player.SwetLatchAttachment;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.network.PacketDistributor;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.packet.clientbound.DataAttachmentSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherIIDataAttachments {
    private static final ReferenceQueue<Entity> DATA_REFERENCE_QUEUE = new ReferenceQueue<>();
    private static final Map<EntityReference, Map<AttachmentType<?>, Object>> DATA = new HashMap<>();
    private static final Map<String, AttachmentType<?>> TYPES = new HashMap<>();

    public static final Holder<Boolean> LASSO_CONNECTION = register("lasso_connection", entity -> false, ByteBufCodecs.BOOL, SyncTarget.TRACKING);
    public static final Holder<Boolean> COMPANION = register("companion", entity -> false, ByteBufCodecs.BOOL, SyncTarget.TRACKING);
    public static final Holder<DroppedItemAttachment> DROPPED_ITEM = register("dropped_item", entity -> new DroppedItemAttachment());

    public static final Holder<DamageSystemAttachment> DAMAGE_SYSTEM = register("damage_system", entity -> new DamageSystemAttachment(), DamageSystemAttachment.STREAM_CODEC, SyncTarget.SELF);
    public static final Holder<EffectsSystemAttachment> EFFECTS_SYSTEM = register("effects_system", entity -> new EffectsSystemAttachment(), EffectsSystemAttachment.STREAM_CODEC, SyncTarget.TRACKING);
    public static final Holder<AccessoryContainer> ACCESSORIES = register("accessories", entity -> new AccessoryContainer(), AccessoryContainer.STREAM_CODEC, SyncTarget.TRACKING);

    public static final Holder<AetherIIPlayerAttachment> PLAYER = register("player", entity -> new AetherIIPlayerAttachment(), AetherIIPlayerAttachment.STREAM_CODEC, SyncTarget.TRACKING);
    public static final Holder<SwetLatchAttachment> SWET_LATCH = register("swet_latch", entity -> new SwetLatchAttachment((Player) entity));
    public static final Holder<AerbunnyMountAttachment> AERBUNNY_MOUNT = register("aerbunny_mount", entity -> new AerbunnyMountAttachment());
    public static final Holder<AbilityBehaviorAttachment> ABILITY_BEHAVIOR = register("ability_behavior", entity -> new AbilityBehaviorAttachment(), AbilityBehaviorAttachment.STREAM_CODEC, SyncTarget.TRACKING);
    public static final Holder<CurrencyAttachment> CURRENCY = register("currency", entity -> new CurrencyAttachment(), CurrencyAttachment.STREAM_CODEC, SyncTarget.SELF);
    public static final Holder<GuidebookDiscoveryAttachment> GUIDEBOOK_DISCOVERY = register("guidebook_discovery", entity -> new GuidebookDiscoveryAttachment(), GuidebookDiscoveryAttachment.STREAM_CODEC, SyncTarget.SELF);
    public static final Holder<OutpostTrackerAttachment> OUTPOST_TRACKER = register("outpost_tracker", entity -> new OutpostTrackerAttachment(), OutpostTrackerAttachment.STREAM_CODEC, SyncTarget.SELF);

    private static <T> Holder<T> register(String name, Function<Entity, T> factory) {
        return register(name, factory, null, SyncTarget.NONE);
    }

    private static <T> Holder<T> register(String name, Function<Entity, T> factory, @Nullable StreamCodec<? super FriendlyByteBuf, T> streamCodec, SyncTarget syncTarget) {
        AttachmentType<T> type = new AttachmentType<>(name, factory, streamCodec, syncTarget);
        TYPES.put(name, type);
        return new Holder<>(type);
    }

    public static <T> T get(Entity entity, Supplier<AttachmentType<T>> holder) {
        AttachmentType<T> type = holder.get();
        synchronized (DATA) {
            return type.cast(dataFor(entity).computeIfAbsent(type, key -> type.create(entity)));
        }
    }

    public static <T> void set(Entity entity, Supplier<AttachmentType<T>> holder, T value) {
        AttachmentType<T> type = holder.get();
        synchronized (DATA) {
            dataFor(entity).put(type, value);
        }
    }

    public static <T> void sync(Entity entity, Supplier<AttachmentType<T>> holder) {
        AttachmentType<T> type = holder.get();
        if (entity.level().isClientSide() || !type.isSyncable()) {
            return;
        }
        T value = get(entity, holder);
        DataAttachmentSyncPacket packet = new DataAttachmentSyncPacket(entity.getId(), type, value);
        if (type.syncTarget() == SyncTarget.SELF && entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        } else if (type.syncTarget() == SyncTarget.TRACKING && entity.level() instanceof ServerLevel serverLevel) {
            for (ServerPlayer serverPlayer : serverLevel.players()) {
                PacketDistributor.sendToPlayer(serverPlayer, packet);
            }
        }
    }

    public static AttachmentType<?> getType(String name) {
        AttachmentType<?> type = TYPES.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Unknown Aether II attachment type: " + name);
        }
        return type;
    }

    public static void setSynced(Entity entity, AttachmentType<?> type, Object value) {
        synchronized (DATA) {
            dataFor(entity).put(type, type.cast(value));
        }
    }

    private static Map<AttachmentType<?>, Object> dataFor(Entity entity) {
        cleanupDataReferences();
        return DATA.computeIfAbsent(new EntityReference(entity, DATA_REFERENCE_QUEUE), key -> new HashMap<>());
    }

    private static void cleanupDataReferences() {
        EntityReference reference;
        while ((reference = (EntityReference) DATA_REFERENCE_QUEUE.poll()) != null) {
            DATA.remove(reference);
        }
    }

    private static final class EntityReference extends WeakReference<Entity> {
        private final int hash;

        private EntityReference(Entity entity, ReferenceQueue<Entity> queue) {
            super(entity, queue);
            this.hash = System.identityHashCode(entity);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EntityReference other)) {
                return false;
            }
            Entity entity = this.get();
            return entity != null && entity == other.get();
        }
    }

    public record Holder<T>(AttachmentType<T> type) implements Supplier<AttachmentType<T>> {
        @Override
        public AttachmentType<T> get() {
            return this.type;
        }
    }

    public record AttachmentType<T>(String name, Function<Entity, T> factory, @Nullable StreamCodec<? super FriendlyByteBuf, T> streamCodec, SyncTarget syncTarget) {
        T create(Entity entity) {
            return this.factory.apply(entity);
        }

        public boolean isSyncable() {
            return this.streamCodec != null && this.syncTarget != SyncTarget.NONE;
        }

        public void encode(FriendlyByteBuf buffer, Object value) {
            if (this.streamCodec == null) {
                throw new IllegalStateException("Aether II attachment type is not syncable: " + this.name);
            }
            this.streamCodec.encode(buffer, this.cast(value));
        }

        public T decode(FriendlyByteBuf buffer) {
            if (this.streamCodec == null) {
                throw new IllegalStateException("Aether II attachment type is not syncable: " + this.name);
            }
            return this.streamCodec.decode(buffer);
        }

        @SuppressWarnings("unchecked")
        public T cast(Object value) {
            return (T) value;
        }
    }

    public enum SyncTarget {
        NONE,
        SELF,
        TRACKING
    }
}

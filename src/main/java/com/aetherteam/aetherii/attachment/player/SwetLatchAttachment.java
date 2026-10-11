package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SwetLatchAttachment {
    public static final Identifier DEBUFFED_MOVEMENT_SPEED = Identifier.fromNamespaceAndPath(AetherII.MODID, "player.debuff.swet_movement_speed");
    public static final int MAX_SWET_COUNT = 3;

    private List<LatchedSwetData> latchedSwetData = new ArrayList<>();

    public static final MapCodec<SwetLatchAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LatchedSwetData.CODEC.listOf().fieldOf("latched_swet_data").forGetter(SwetLatchAttachment::getLatchedSwetData)
    ).apply(instance, SwetLatchAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SwetLatchAttachment> STREAM_CODEC = StreamCodec.composite(
            LatchedSwetData.STREAM_CODEC.apply(ByteBufCodecs.list()), SwetLatchAttachment::getLatchedSwetData,
            SwetLatchAttachment::new);

    protected SwetLatchAttachment(List<LatchedSwetData> latchedSwetData) {
        this.latchedSwetData = new ArrayList<>(latchedSwetData);
    }

    public SwetLatchAttachment() {

    }

    public void postTickUpdate(Player player) {
        this.handleSwetTick(player);
    }

    public void handleSwetTick(Player player) {
        AttributeInstance movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        double value = -0.15 * this.getLatchedSwetData().size();
        if (movementSpeedAttribute != null) {
            if (!movementSpeedAttribute.hasModifier(DEBUFFED_MOVEMENT_SPEED) || movementSpeedAttribute.getModifier(DEBUFFED_MOVEMENT_SPEED).amount() != value) {
                if (value != 0.0F) {
                    movementSpeedAttribute.addOrUpdateTransientModifier(new AttributeModifier(DEBUFFED_MOVEMENT_SPEED, value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                } else if (movementSpeedAttribute.hasModifier(DEBUFFED_MOVEMENT_SPEED)) {
                    movementSpeedAttribute.removeModifier(DEBUFFED_MOVEMENT_SPEED);
                }
            }
        }

        if (!this.getLatchedSwetData().isEmpty()) {
            if (player.tickCount % 20 == 0) {
                player.level().playLocalSound(player, AetherIISoundEvents.ENTITY_SWET_LEECH.get(), SoundSource.HOSTILE, 1.0F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            if (player.isInWater()) {
                this.detachSwets(player);
            }
        }
        for (Iterator<LatchedSwetData> iterator = this.getLatchedSwetData().iterator(); iterator.hasNext(); ) {
            LatchedSwetData data = iterator.next();
            if (this.processSucking(player, data)) {
                iterator.remove();
                this.spawnSwet(player, data);
                player.syncData(AetherIIDataAttachments.SWET_LATCH);
            }
        }
    }

    public boolean processSucking(Player player, LatchedSwetData data) {
        if (player.tickCount % 20 == 0) {
            player.causeFoodExhaustion(4.0F);
            data.foodSaturation = data.foodSaturation + 1.0F;
        }
        data.scale = data.scale + 0.0025F;
        return data.foodSaturation >= 8 || player.getFoodData().getFoodLevel() <= 0;
    }

    public void detachSwets(Player player) {
        if (!player.level().isClientSide()) {
            for (Iterator<LatchedSwetData> iterator = this.getLatchedSwetData().iterator(); iterator.hasNext();) {
                LatchedSwetData data = iterator.next();
                data.foodSaturation = 0;
                iterator.remove();
                this.spawnSwet(player, data);
            }
            player.syncData(AetherIIDataAttachments.SWET_LATCH);
        }
    }

    public void spawnSwet(Player player, LatchedSwetData data) {
        if (player.level() instanceof ServerLevel serverLevel) {
            try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(player.problemPath(), AetherII.LOGGER)) {
                EntityType.create(TagValueInput.create(collector.forChild(() -> ".latched"), serverLevel.registryAccess(), data.tag), serverLevel, EntitySpawnReason.LOAD).ifPresent(entity -> {
                    if (entity instanceof Swet swet) {
                        swet.setFoodSaturation(data.foodSaturation);
                        swet.setSwetScale(data.scale);
                    }
                    entity.setPos(player.position().add(0, 0.5, 0));
                    serverLevel.addWithUUID(entity);
                });
            }
        }
    }

    public void latchSwet(Player player, Swet swet) {
        if (this.canLatchOn()) {
            try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(player.problemPath(), AetherII.LOGGER)) {
                TagValueOutput valueOutput = TagValueOutput.createWithContext(problemreporter$scopedcollector, player.registryAccess());
                swet.saveWithoutId(valueOutput);
                valueOutput.putString("id", swet.getEncodeId());
                this.getLatchedSwetData().add(new LatchedSwetData(swet.getType(), valueOutput.buildResult(), swet.getFoodSaturation(), swet.getSwetScale()));
                swet.discard();
                player.syncData(AetherIIDataAttachments.SWET_LATCH);
            }
        }
    }

    public boolean canLatchOn() {
        return this.getLatchedSwetData().size() < MAX_SWET_COUNT;
    }

    public List<LatchedSwetData> getLatchedSwetData() {
        return this.latchedSwetData;
    }

    public static class LatchedSwetData {
        public final EntityType<?> type;
        public CompoundTag tag;
        public float foodSaturation;
        public float scale;
        public Overlay overlay;

        public static final Codec<LatchedSwetData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityType.CODEC.fieldOf("type").forGetter((data) -> data.type),
                CompoundTag.CODEC.fieldOf("tag").forGetter((data) -> data.tag),
                Codec.FLOAT.fieldOf("food_saturation").forGetter((data) -> data.foodSaturation),
                Codec.FLOAT.fieldOf("scale").forGetter((data) -> data.scale)
        ).apply(instance, LatchedSwetData::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, LatchedSwetData> STREAM_CODEC = StreamCodec.composite(
                EntityType.STREAM_CODEC, (data) -> data.type,
                ByteBufCodecs.COMPOUND_TAG, (data) -> data.tag,
                ByteBufCodecs.FLOAT, (data) -> data.foodSaturation,
                ByteBufCodecs.FLOAT, (data) -> data.scale,
                LatchedSwetData::new);

        public LatchedSwetData(EntityType<?> type, CompoundTag tag, float foodSaturation, float scale) {
            this.type = type;
            this.tag = tag;
            this.foodSaturation = foodSaturation;
            this.scale = scale;
            this.overlay = Overlay.create(type.toShortString());
        }
    }

    public record Overlay(Identifier left1, Identifier left2, Identifier right1, Identifier right2) {
        public static Overlay create(String name) {
            return new Overlay(
                    Identifier.fromNamespaceAndPath(AetherII.MODID, "overlay/swet/" + name + "_left_1"),
                    Identifier.fromNamespaceAndPath(AetherII.MODID, "overlay/swet/" + name + "_left_2"),
                    Identifier.fromNamespaceAndPath(AetherII.MODID, "overlay/swet/" + name + "_right_1"),
                    Identifier.fromNamespaceAndPath(AetherII.MODID, "overlay/swet/" + name + "_right_2"));
        }
    }
}

package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.attachment.player.*;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AetherII.MODID);

    // Entity
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> LASSO_CONNECTION = ATTACHMENTS.register("lasso_connection", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("lasso_connection")).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> COMPANION = ATTACHMENTS.register("companion", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("companion")).sync(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> DROPPED_ITEM = ATTACHMENTS.register("dropped_item", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("dropped_item")).build());

    // Living
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DamageSystemAttachment>> DAMAGE_SYSTEM = ATTACHMENTS.register("damage_system", () -> AttachmentType.builder(DamageSystemAttachment::new).serialize(DamageSystemAttachment.CODEC).sync(DamageSystemAttachment.STREAM_CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EffectsSystemAttachment>> EFFECTS_SYSTEM = ATTACHMENTS.register("effects_system", () -> AttachmentType.builder(EffectsSystemAttachment::new).serialize(EffectsSystemAttachment.CODEC).sync(EffectsSystemAttachment.STREAM_CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AccessoryContainer>> ACCESSORIES = ATTACHMENTS.register("accessories", () -> AttachmentType.builder(AccessoryContainer::new).serialize(AccessoryContainer.CODEC).sync(AccessoryContainer.STREAM_CODEC).copyOnDeath().build());

    // Player
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AetherIIPlayerAttachment>> PLAYER = ATTACHMENTS.register("player", () -> AttachmentType.builder(AetherIIPlayerAttachment::new).serialize(AetherIIPlayerAttachment.CODEC).sync(AetherIIPlayerAttachment.STREAM_CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SwetLatchAttachment>> SWET_LATCH = ATTACHMENTS.register("swet_latch", () -> AttachmentType.serializable((entity) -> new SwetLatchAttachment((Player) entity)).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AerbunnyMountAttachment>> AERBUNNY_MOUNT = ATTACHMENTS.register("aerbunny_mount", () -> AttachmentType.serializable(AerbunnyMountAttachment::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AbilityBehaviorAttachment>> ABILITY_BEHAVIOR = ATTACHMENTS.register("ability_behavior", () -> AttachmentType.builder(AbilityBehaviorAttachment::new).serialize(AbilityBehaviorAttachment.CODEC).sync(AbilityBehaviorAttachment.STREAM_CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CurrencyAttachment>> CURRENCY = ATTACHMENTS.register("currency", () -> AttachmentType.builder(CurrencyAttachment::new).serialize(CurrencyAttachment.CODEC).sync(CurrencyAttachment.STREAM_CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<GuidebookDiscoveryAttachment>> GUIDEBOOK_DISCOVERY = ATTACHMENTS.register("guidebook_discovery", () -> AttachmentType.builder(GuidebookDiscoveryAttachment::new).serialize(GuidebookDiscoveryAttachment.CODEC).sync(GuidebookDiscoveryAttachment.STREAM_CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<OutpostTrackerAttachment>> OUTPOST_TRACKER = ATTACHMENTS.register("outpost_tracker", () -> AttachmentType.builder(OutpostTrackerAttachment::new).serialize(OutpostTrackerAttachment.CODEC).sync(OutpostTrackerAttachment.STREAM_CODEC).copyOnDeath().build());
}
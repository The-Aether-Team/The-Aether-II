package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.entity.DroppedItemAttachment;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.attachment.player.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AetherII.MODID);

    // Entity
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DroppedItemAttachment>> DROPPED_ITEM = ATTACHMENTS.register("dropped_item", () -> AttachmentType.builder(DroppedItemAttachment::new).serialize(DroppedItemAttachment.CODEC).build());

    // Living
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DamageSystemAttachment>> DAMAGE_SYSTEM = ATTACHMENTS.register("damage_system", () -> AttachmentType.builder(DamageSystemAttachment::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EffectsSystemAttachment>> EFFECTS_SYSTEM = ATTACHMENTS.register("effects_system", () -> AttachmentType.serializable((entity) -> new EffectsSystemAttachment((LivingEntity) entity)).build());

    // Player
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AetherIIPlayerAttachment>> PLAYER = ATTACHMENTS.register("player", () -> AttachmentType.builder(AetherIIPlayerAttachment::new).serialize(AetherIIPlayerAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CurrencyAttachment>> CURRENCY = ATTACHMENTS.register("currency", () -> AttachmentType.builder(CurrencyAttachment::new).serialize(CurrencyAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<GuidebookDiscoveryAttachment>> GUIDEBOOK_DISCOVERY = ATTACHMENTS.register("guidebook_discovery", () -> AttachmentType.builder(GuidebookDiscoveryAttachment::new).serialize(GuidebookDiscoveryAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<OutpostTrackerAttachment>> OUTPOST_TRACKER = ATTACHMENTS.register("outpost_tracker", () -> AttachmentType.builder(OutpostTrackerAttachment::new).serialize(OutpostTrackerAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SwetAttachment>> SWET = ATTACHMENTS.register("swet", () -> AttachmentType.serializable((entity) -> new SwetAttachment((Player) entity)).build());
}
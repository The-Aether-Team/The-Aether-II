package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.attachment.player.AerbunnyMountAttachment;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.aetherii.attachment.player.PortalTeleportationAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AetherII.MODID);

    // Living
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DamageSystemAttachment>> DAMAGE_SYSTEM = ATTACHMENTS.register("damage_system", () -> AttachmentType.builder(DamageSystemAttachment::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EffectsSystemAttachment>> EFFECTS_SYSTEM = ATTACHMENTS.register("effects_system", () -> AttachmentType.serializable((entity) -> new EffectsSystemAttachment((LivingEntity) entity)).build());

    // Player
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AerbunnyMountAttachment>> AERBUNNY_MOUNT = ATTACHMENTS.register("ride_mob", () -> AttachmentType.builder(attach -> new AerbunnyMountAttachment()).serialize(AerbunnyMountAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CurrencyAttachment>> CURRENCY = ATTACHMENTS.register("currency", () -> AttachmentType.builder(CurrencyAttachment::new).serialize(CurrencyAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PortalTeleportationAttachment>> PORTAL_TELEPORTATION = ATTACHMENTS.register("portal_teleportation", () -> AttachmentType.builder(PortalTeleportationAttachment::new).serialize(PortalTeleportationAttachment.CODEC).copyOnDeath().build());
}
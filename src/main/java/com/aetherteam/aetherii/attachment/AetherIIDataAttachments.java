package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AetherII.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PortalTeleportationAttachment>> PORTAL_TELEPORTATION = ATTACHMENTS.register("portal_teleportation", () -> AttachmentType.builder(PortalTeleportationAttachment::new).serialize(PortalTeleportationAttachment.CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<RideMobAttachment>> RIDE_MOB = ATTACHMENTS.register("ride_mob", () -> AttachmentType.builder(attach -> {
        return new RideMobAttachment();
    }).serialize(RideMobAttachment.CODEC).build());
}
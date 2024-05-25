package com.aetherteam.aetherii.attachment;

import com.aetherteam.aetherii.AetherII;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AetherII.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AetherIIPlayerAttachment>> AETHER_II_PLAYER = ATTACHMENTS.register("aether_ii_player", () -> AttachmentType.builder(AetherIIPlayerAttachment::new).serialize(AetherIIPlayerAttachment.CODEC).copyOnDeath().build());
}
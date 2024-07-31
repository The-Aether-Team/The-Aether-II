package com.aetherteam.aetherii.attachment.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EquipmentAbilitiesAttachment {
    private boolean gravititeHoldingFloatingBlock;
    private boolean gravititeJumpUsed;

    public static final Codec<EquipmentAbilitiesAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("gravitite_holding_floating_block").forGetter(EquipmentAbilitiesAttachment::isGravititeHoldingFloatingBlock),
            Codec.BOOL.fieldOf("gravitite_jump_used").forGetter(EquipmentAbilitiesAttachment::isGravititeJumpUsed)
    ).apply(instance, EquipmentAbilitiesAttachment::new));

    protected EquipmentAbilitiesAttachment(boolean gravititeHoldingFloatingBlock, boolean gravititeJumpUsed) {
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public EquipmentAbilitiesAttachment() {
        this.gravititeHoldingFloatingBlock = false;
        this.gravititeJumpUsed = true;
    }

    public void setGravititeHoldingFloatingBlock(boolean gravititeHoldingFloatingBlock) {
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
    }

    public boolean isGravititeHoldingFloatingBlock() {
        return this.gravititeHoldingFloatingBlock;
    }

    public void setGravititeJumpUsed(boolean gravititeJumpUsed) {
        this.gravititeJumpUsed = gravititeJumpUsed;
    }

    public boolean isGravititeJumpUsed() {
        return this.gravititeJumpUsed;
    }
}

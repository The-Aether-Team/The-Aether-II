package com.aetherteam.aetherii.attachment.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EquipmentAbilitiesAttachment {
    private boolean gravititeHoldingFloatingBlock;

    public static final Codec<EquipmentAbilitiesAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("gravitite_holding_floating_block").forGetter(EquipmentAbilitiesAttachment::isGravititeHoldingFloatingBlock)
    ).apply(instance, EquipmentAbilitiesAttachment::new));

    protected EquipmentAbilitiesAttachment(boolean gravititeHoldingFloatingBlock) {
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
    }

    public EquipmentAbilitiesAttachment() {
        this.gravititeHoldingFloatingBlock = false;
    }

    public void setGravititeHoldingFloatingBlock(boolean gravititeHoldingFloatingBlock) {
        this.gravititeHoldingFloatingBlock = gravititeHoldingFloatingBlock;
    }

    public boolean isGravititeHoldingFloatingBlock() {
        return this.gravititeHoldingFloatingBlock;
    }
}

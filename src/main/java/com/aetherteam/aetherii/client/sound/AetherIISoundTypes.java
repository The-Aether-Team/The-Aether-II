package com.aetherteam.aetherii.client.sound;

import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class AetherIISoundTypes {
    public static final SoundType FERROSITE = new ForgeSoundType(
            1.0F, 1.0F,
            AetherIISoundEvents.BLOCK_FERROSITE_BREAK,
            AetherIISoundEvents.BLOCK_FERROSITE_STEP,
            AetherIISoundEvents.BLOCK_FERROSITE_PLACE,
            AetherIISoundEvents.BLOCK_FERROSITE_HIT,
            AetherIISoundEvents.BLOCK_FERROSITE_FALL
    );
}

package com.aetherteam.aetherii.client;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class AetherIISoundTypes {
    public static final SoundType FERROSITE = new DeferredSoundType(
            1.0F, 1.0F,
            AetherIISoundEvents.BLOCK_FERROSITE_BREAK,
            AetherIISoundEvents.BLOCK_FERROSITE_STEP,
            AetherIISoundEvents.BLOCK_FERROSITE_PLACE,
            AetherIISoundEvents.BLOCK_FERROSITE_HIT,
            AetherIISoundEvents.BLOCK_FERROSITE_FALL
    );
}

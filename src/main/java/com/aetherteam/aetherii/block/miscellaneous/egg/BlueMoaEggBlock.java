package com.aetherteam.aetherii.block.miscellaneous.egg;

import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaTypes;
import com.mojang.serialization.MapCodec;

public class BlueMoaEggBlock extends AbstractMoaEggBlock {
    public static final MapCodec<BlueMoaEggBlock> CODEC = simpleCodec(BlueMoaEggBlock::new);

    public BlueMoaEggBlock(Properties p_277906_) {
        super(AetherIIMoaTypes.BLUE, p_277906_);
    }

    @Override
    protected MapCodec<? extends BlueMoaEggBlock> codec() {
        return CODEC;
    }
}

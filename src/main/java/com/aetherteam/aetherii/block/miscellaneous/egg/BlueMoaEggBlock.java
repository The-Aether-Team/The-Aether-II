package com.aetherteam.aetherii.block.miscellaneous.egg;

import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaFeatherShapes;
import com.mojang.serialization.MapCodec;

public class BlueMoaEggBlock extends AbstractMoaEggBlock {
    public static final MapCodec<BlueMoaEggBlock> CODEC = simpleCodec(BlueMoaEggBlock::new);

    public BlueMoaEggBlock(Properties p_277906_) {  //todo moa variation; remove class probably
        super(p_277906_);
    }

    @Override
    protected MapCodec<? extends BlueMoaEggBlock> codec() {
        return CODEC;
    }
}

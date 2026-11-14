package com.aetherteam.aetherii.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

public record DensityTest(BlockState block, double min, double max) {
    public static final Codec<DensityTest> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("block").forGetter(DensityTest::block),
            Codec.DOUBLE.fieldOf("min").forGetter(DensityTest::min),
            Codec.DOUBLE.fieldOf("max").forGetter(DensityTest::max)
    ).apply(inst, DensityTest::new));

    public BlockState test(double value) {
        if (value > this.min() && value < this.max()) {
            return this.block();
        }
        return null;
    }
}

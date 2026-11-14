package com.aetherteam.aetherii.world.structure.processor.ruletest;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class BlockIgnoreTest extends RuleTest {
    public static final MapCodec<BlockIgnoreTest> CODEC = BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").xmap(BlockIgnoreTest::new, (t) -> t.block);
    private final Block block;

    public BlockIgnoreTest(Block block) {
        this.block = block;
    }

    public boolean test(BlockState state, RandomSource random) {
        return !state.is(this.block);
    }

    protected RuleTestType<?> getType() {
        return AetherIIRuleTests.BLOCK_IGNORE_TEST.get();
    }
}
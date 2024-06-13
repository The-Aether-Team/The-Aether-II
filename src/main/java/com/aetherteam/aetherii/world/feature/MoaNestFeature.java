package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.api.moaegg.MoaType;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.MoaAi;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.MoaNestConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MoaNestFeature extends Feature<MoaNestConfiguration> {
    public MoaNestFeature(Codec<MoaNestConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MoaNestConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        MoaNestConfiguration config = context.config();
        float radius = random.nextInt((int) config.additionalRadius().getMinValue()) + config.baseRadius().getMinValue() + 0.25F;

        BlockPlacementUtil.placeDisk(level, config.nestBlock(), pos.below(), radius, random, true);
        BlockPlacementUtil.placeDisk(level, config.nestBlock(), pos, radius + 1, random, true);
        BlockPlacementUtil.placeDisk(level, BlockStateProvider.simple(Blocks.AIR), pos, radius, random, true);

        MoaType moaType = AetherIIMoaTypes.getWeightedChance(level.registryAccess(), level.getRandom());
        this.setBlock(level, pos, Block.byItem(moaType.egg().getItem()).defaultBlockState());
        for (int i = 0; i < 2; i++) {
            Moa moa = AetherIIEntityTypes.MOA.get().create(level.getLevel());
            moa.setPos(pos.getCenter().add(i, 0, i));
            moa.setMoaTypeByKey(AetherIIMoaTypes.getResourceKey(level.registryAccess(), moaType));
            MoaAi.initMoaHomeMemories(moa, level.getRandom());
            moa.setBaby(false);
            level.getLevel().addFreshEntity(moa);
        }
        return true;
    }
}
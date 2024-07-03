package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.MoaAi;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.MoaNestConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
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
        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();

        BlockPlacementUtil.placeNest(level, config.nestBlock(), pos.below(), radius, random);
        BlockPlacementUtil.placeNest(level, config.nestBlock(), pos, radius + 1F, random);
        BlockPlacementUtil.placeNest(level, BlockStateProvider.simple(Blocks.AIR), pos, radius, random);

        BlockPlacementUtil.placeNest(level, BlockStateProvider.simple(Blocks.AIR), pos.above(), radius + 1, random);
        BlockPlacementUtil.placeNest(level, BlockStateProvider.simple(Blocks.AIR), pos.above(2), radius, random);

        Moa.KeratinColor keratinColor = Moa.KeratinColor.getRandom(random);
        Moa.EyeColor eyeColor = Moa.EyeColor.getRandom(random);
        Moa.FeatherColor featherColor = Moa.FeatherColor.getRandom(random);
        this.setBlock(level, pos, AetherIIBlocks.MOA_EGG.get().defaultBlockState().setValue(MoaEggBlock.KERATIN, keratinColor).setValue(MoaEggBlock.EYES, eyeColor).setValue(MoaEggBlock.FEATHERS, featherColor));

        if (config.spawnMoas()) {
             for (int i = 0; i < 2; i++) {
                Moa moa = AetherIIEntityTypes.MOA.get().create(level.getLevel());
                moa.setPos(pos.getCenter().add(i, 0, i));
                MoaAi.initMoaHomeMemories(moa, level.getRandom());
                moa.setBaby(false);
                moa.setKeratinColor(keratinColor.getColor());
                moa.setEyeColor(eyeColor.getColor());
                moa.setFeatherColor(featherColor.getColor());
                level.getLevel().addFreshEntity(moa);
            }
        }
        return true;
    }
}
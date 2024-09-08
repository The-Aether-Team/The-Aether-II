package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;

public class AechorCuttingBlock extends AetherFlowerBlock {
    public AechorCuttingBlock(Properties properties) {
        super(AetherIIEffects.TOXIN, 5, properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (CommonHooks.canCropGrow(level, pos, state, random.nextInt(20) == 0)) {
            AechorPlant aechorPlant = new AechorPlant(AetherIIEntityTypes.AECHOR_PLANT.get(), level);
            aechorPlant.setPos(Vec3.atBottomCenterOf(pos));
            level.addFreshEntity(aechorPlant);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
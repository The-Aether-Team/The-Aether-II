package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.AechorPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.function.Supplier;

public class PlantMobCuttingBlock extends AetherFlowerBlock implements BonemealableBlock {
    private final Supplier<EntityType<?>> spawnableEntityTypeSupplier;

    public PlantMobCuttingBlock(Supplier<EntityType<?>> spawnableEntityTypeSupplier, Properties properties) {
        super(AetherIIEffects.TOXIN, 5, properties);
        this.spawnableEntityTypeSupplier = spawnableEntityTypeSupplier;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (CommonHooks.canCropGrow(level, pos, state, random.nextInt(20) == 0)) {
            this.spawnableEntityTypeSupplier.get().spawn(level, BlockPos.containing(Vec3.atBottomCenterOf(pos)), EntitySpawnReason.NATURAL);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() <= 0.25;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.spawnableEntityTypeSupplier.get().spawn(level, BlockPos.containing(Vec3.atBottomCenterOf(pos)), EntitySpawnReason.NATURAL);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }
}
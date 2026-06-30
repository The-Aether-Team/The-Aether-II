package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FullAetherBushBlock extends AetherBushBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FullAetherBushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context.isAbove(Shapes.block(), pos, true) && !context.isDescending()) {
            return Shapes.block();
        } else {
            return Shapes.empty();
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelReader, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(state, direction, neighborState, levelReader, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            if (!livingEntity.level().isClientSide()) {
                livingEntity.addEffect(new MobEffectInstance(AetherIIMobEffects.NATURAL_CAMOUFLAGE.get(), 1, 0, false, false, false));
            } else {
                if (entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
                    if (level.getRandom().nextInt(10) == 0) {
                        level.playSound(null, pos, AetherIISoundEvents.BLOCK_BUSH_RUSTLE.get(), SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
                    }
                    int count = entity.isCrouching() ? 1 : 2;
                    this.spawnParticles(level, entity.position(), count);
                }
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.isCrouching() && entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
            this.spawnParticles(level, entity.position().subtract(0, 1, 0), 1);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, entity.damageSources().fall());
        this.spawnParticles(level, entity.position().subtract(0, 1, 0), 3);
    }

    private void spawnParticles(Level level, Vec3 vec3, int count) {
        for (int j = 0; j < count; ++j) {
            double d0 = vec3.x + Mth.nextDouble(level.getRandom(), -0.3, 0.3);
            double d1 = vec3.y + Mth.nextDouble(level.getRandom(), 0, 1.0);
            double d2 = vec3.z + Mth.nextDouble(level.getRandom(), -0.3, 0.3);
            double d3 = Mth.nextDouble(level.getRandom(), -0.3, 0.3);
            double d4 = Mth.nextDouble(level.getRandom(), 0, 1.0);
            double d5 = Mth.nextDouble(level.getRandom(), -0.3, 0.3);
            level.addParticle(AetherIIParticleTypes.SKYROOT_LEAVES.get(), d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public @Nullable BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
        if (mob == null || mob.getType() != EntityType.FOX && mob.getType() != EntityType.BEE) {
            return BlockPathTypes.DAMAGE_OTHER;
        }
        return super.getAdjacentBlockPathType(state, level, pos, mob, originalType);
    }
}

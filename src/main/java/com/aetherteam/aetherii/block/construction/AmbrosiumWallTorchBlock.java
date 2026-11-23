package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class AmbrosiumWallTorchBlock extends WallTorchBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.boxZ(5.0F, 3.0F, 14.0F, 11.0F, 16.0F));

    public AmbrosiumWallTorchBlock(Properties properties) {
        super(ParticleTypes.SMOKE, properties.overrideLootTable(AetherIIBlocks.AMBROSIUM_TORCH.get().getLootTable()));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        double d0 = (double) pos.getX() + 0.5;
        double d1 = (double) pos.getY() + 0.7;
        double d2 = (double) pos.getZ() + 0.5;
        Direction opposite = direction.getOpposite();
        if (random.nextInt(7) == 0) {
            level.addParticle(AetherIIParticleTypes.AMBROSIUM.get(), d0 + 0.27 * (double) opposite.getStepX(), d1 + 0.2, d2 + 0.27 * (double) opposite.getStepZ(), 0.0, 0.0, 0.0);
        }
    }
}
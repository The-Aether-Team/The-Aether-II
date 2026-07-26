package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.utility.AmbrosiumCampfireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AmbrosiumCampfireBlockEntity extends CampfireBlockEntity {
    public AmbrosiumCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<AmbrosiumCampfireBlockEntity> getType() {
        return AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get();
    }

    public static void particleTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity entity) {
        RandomSource random = level.getRandom();
        if (random.nextFloat() < 0.11F) {
            for (int i = 0; i < random.nextInt(2) + 2; ++i) {
                AmbrosiumCampfireBlock.makeParticles(level, pos, state.getValue(CampfireBlock.SIGNAL_FIRE), false);
            }
        }

        int rotation = state.getValue(CampfireBlock.FACING).get2DDataValue();
        for (int slot = 0; slot < entity.getItems().size(); ++slot) {
            if (!(entity.getItems().get(slot)).isEmpty() && random.nextFloat() < 0.2F) {
                Direction direction = Direction.from2DDataValue(Math.floorMod(slot + rotation, 4));
                float distanceFromCenter = 0.4F;
                double x = pos.getX() + 0.5F - (direction.getStepX() * distanceFromCenter);
                double y = pos.getY() + 0.25F;
                double z = pos.getZ() + 0.5F - (direction.getStepZ() * distanceFromCenter);
                for (int i = 0; i < 4; ++i) {
                    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0F, 5.0E-4, 0.0F);
                }
            }
        }
    }
}
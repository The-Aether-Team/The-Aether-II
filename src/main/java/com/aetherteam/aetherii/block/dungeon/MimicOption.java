package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public interface MimicOption {
    static void spawnMimic(BlockState state, Level level, BlockPos pos) {
        if (state.getBlock() instanceof MimicOption mimicOption) {
            Mimic mimic = mimicOption.getMimicEntityType().get().create(level, EntitySpawnReason.TRIGGERED);
            if (mimic != null) {
                Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                float angle = direction.toYRot();
                mimic.moveOrInterpolateTo(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), angle, 0.0F);
                mimic.setYHeadRot(angle);
                level.addFreshEntity(mimic);
                mimic.spawnAnim();
            }
        }
    }

    Optional<EntityType<? extends Mimic>> getMimicEntityType();
}

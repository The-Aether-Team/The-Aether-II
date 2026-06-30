package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface MimicOption {
    static void spawnMimic(BlockState state, ServerLevel level, BlockPos pos) {
        Mimic mimic = AetherIIEntityTypes.MIMIC.get().create(level);
        if (mimic != null) {
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            float angle = direction.toYRot();
            mimic.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, angle, 0.0F);
            mimic.setYHeadRot(angle);
            level.addFreshEntity(mimic);
            mimic.spawnAnim();
        }
    }
}

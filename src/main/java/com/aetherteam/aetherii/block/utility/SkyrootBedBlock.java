package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class SkyrootBedBlock extends BedBlock {
    public SkyrootBedBlock(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootBedBlockEntity(pos, state);
    }
}

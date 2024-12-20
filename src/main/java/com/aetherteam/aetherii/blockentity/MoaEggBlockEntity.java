package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MoaEggBlockEntity extends BlockEntity {
    public int tickCount;

    public MoaEggBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.MOA_EGG.get(), pos, state);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MoaEggBlockEntity moaEggBlockEntity) {
        moaEggBlockEntity.tickCount++;
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(AetherIIDataComponents.MOA_EGG_TYPE, new MoaEggType(this.getBlockState().getValue(MoaEggBlock.KERATIN), this.getBlockState().getValue(MoaEggBlock.EYES), this.getBlockState().getValue(MoaEggBlock.FEATHERS), this.getBlockState().getValue(MoaEggBlock.FEATHER_SHAPE)));
    }
}
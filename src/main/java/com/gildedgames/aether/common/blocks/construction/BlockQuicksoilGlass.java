package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuicksoilGlass extends BlockGlass
{
    public BlockQuicksoilGlass()
    {
        super(Material.GLASS, false);

        this.setHardness(0.3f);
        this.setResistance(0.5f);

        this.setLightOpacity(3);

        this.setLightLevel(1f);

        this.setSoundType(SoundType.GLASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        BlockPos offset = pos.offset(side);
        IBlockState iblockstate = blockAccess.getBlockState(offset);

        return iblockstate.getBlock() != this && (iblockstate.getBlock().isAir(iblockstate, blockAccess, offset) || !iblockstate.isFullCube());
    }
}

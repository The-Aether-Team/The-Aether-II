package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOreBlock extends Block
{
    public BlockOreBlock(Material material)
    {
        super(material);
    }

    @Override
    public BlockOreBlock setSoundType(SoundType type)
    {
        super.setSoundType(type);

        return this;
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
        return true;
    }
}

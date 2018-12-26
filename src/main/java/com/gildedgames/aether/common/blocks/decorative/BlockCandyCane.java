package com.gildedgames.aether.common.blocks.decorative;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCandyCane extends BlockRotatedPillar
{
    public BlockCandyCane()
    {
        super(Material.GLASS);
        this.setHardness(1.3F);
        this.setSoundType(SoundType.GLASS);
    }
}

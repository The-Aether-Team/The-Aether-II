package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuicksoilGlassPane extends BlockPane
{
    public BlockQuicksoilGlassPane()
    {
        super(Material.GLASS, false);

        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);

        this.setLightOpacity(0);

        this.setLightLevel(1f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}

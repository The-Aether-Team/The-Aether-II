package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRockGlassPane extends BlockPane
{
	private final Block block;

	public BlockRockGlassPane(Block block)
	{
		super(Material.ROCK, true);

		this.block = block;

		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);

		this.setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return this.block.getRenderLayer();
	}
}

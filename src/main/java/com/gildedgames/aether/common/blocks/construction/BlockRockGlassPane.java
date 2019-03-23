package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockRockGlassPane extends BlockPane
{
	private final Block block;

	public BlockRockGlassPane(Block block)
	{
		super(Material.ROCK, false);

		this.block = block;

		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);

		this.setLightOpacity(0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return this.block.getRenderLayer();
	}
}

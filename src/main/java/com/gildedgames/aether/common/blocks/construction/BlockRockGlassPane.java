package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockRockGlassPane extends PaneBlock
{
	private final Block block;

	public BlockRockGlassPane(Block.Properties properties, Block block)
	{
		super(properties);

		this.block = block;

		this.setLightOpacity(0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return this.block.getRenderLayer();
	}
}

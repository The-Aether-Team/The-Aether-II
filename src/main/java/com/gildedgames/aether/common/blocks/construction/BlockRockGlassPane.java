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

	public BlockRockGlassPane(Block block, Material material)
	{
		super(material, true);

		this.block = block;

		this.setHardness(1.0F);
		this.setResistance(2000f);

		this.setSoundType(SoundType.GLASS);

		this.setLightOpacity(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return this.block.getRenderLayer();
	}
}

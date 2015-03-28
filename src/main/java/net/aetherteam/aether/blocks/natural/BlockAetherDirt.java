package net.aetherteam.aether.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockAetherDirt extends Block
{
	public BlockAetherDirt()
	{
		super(Material.ground);
		this.setStepSound(Block.soundTypeGravel);
		this.setHardness(0.5F);

		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}

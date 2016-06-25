package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.blocks.util.BlockFloating;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGravititeOre extends BlockFloating
{

	public BlockGravititeOre()
	{
		super(Material.ROCK);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 2);

		this.setSoundType(SoundType.STONE);
	}

}

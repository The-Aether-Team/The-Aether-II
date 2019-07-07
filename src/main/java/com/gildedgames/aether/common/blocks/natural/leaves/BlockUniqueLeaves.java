package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherUniqueSapling;
import com.gildedgames.aether.common.blocks.natural.wood.AetherWoodType;
import net.minecraft.item.ItemStack;

public class BlockUniqueLeaves extends BlockAetherLeaves
{
	private final AetherWoodType type;

	public BlockUniqueLeaves(AetherWoodType type)
	{
		this.type = type;
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		if (this.type == AetherWoodType.AMBERROOT)
		{
			return new ItemStack(BlocksAether.unique_sapling, 1, BlockAetherUniqueSapling.AMBEROOT.getMeta());
		}

		return null;
	}
}

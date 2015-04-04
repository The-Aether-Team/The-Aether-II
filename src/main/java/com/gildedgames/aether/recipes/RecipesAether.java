package com.gildedgames.aether.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.BlocksAether;

public class RecipesAether
{
	public void preInit()
	{
		BlocksAether blocks = Aether.getBlocks();

		this.registerShapelessRecipe(new ItemStack(blocks.skyroot_planks, 4), new ItemStack(blocks.aether_log, 1));
	}

	private void registerShapelessRecipe(ItemStack output, Object... stacks)
	{
		GameRegistry.addShapelessRecipe(output, stacks);
	}
}

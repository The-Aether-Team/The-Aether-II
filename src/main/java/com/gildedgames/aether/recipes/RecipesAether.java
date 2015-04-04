package com.gildedgames.aether.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.BlocksAether;
import com.gildedgames.aether.items.ItemsAether;

public class RecipesAether
{
	public void preInit()
	{
		BlocksAether blocks = Aether.getBlocks();
		ItemsAether items = Aether.getItems();

		this.registerShapelessRecipe(new ItemStack(blocks.skyroot_planks, 4), new ItemStack(blocks.aether_log, 1));

		this.registerShapedRecipe(new ItemStack(items.skyroot_stick, 4), "X", "X",
				'X', new ItemStack(blocks.skyroot_planks, 1));
	}

	private void registerShapelessRecipe(ItemStack output, Object... stacks)
	{
		GameRegistry.addShapelessRecipe(output, stacks);
	}

	private void registerShapedRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapedRecipe(output, params);
	}
}

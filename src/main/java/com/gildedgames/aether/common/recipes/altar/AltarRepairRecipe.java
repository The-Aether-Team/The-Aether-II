package com.gildedgames.aether.common.recipes.altar;

import com.gildedgames.aether.common.AetherMaterials;
import com.gildedgames.aether.common.items.tools.ItemAetherTool;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import java.util.List;

public class AltarRepairRecipe implements IAltarRecipe
{
	@Override
	public boolean matchesItem(ItemStack stack)
	{
		return (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor) && stack.isItemDamaged();
	}

	@Override
	public int getAmbrosiumCost(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemAetherTool || stack.getItem() instanceof ItemAetherSword)
		{
			return 6;
		}
		else if (stack.getItem() instanceof ItemTool)
		{
			ItemTool tool = (ItemTool) stack.getItem();

			switch (tool.getToolMaterial())
			{
			case WOOD:
				return 2;
			case STONE:
				return 3;
			case IRON:
				return 4;
			case EMERALD:
				return 5;
			case GOLD:
				return 3;
			}
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			ItemArmor armor = (ItemArmor) stack.getItem();

			switch (armor.getArmorMaterial())
			{
			case LEATHER:
				return 2;
			case IRON:
				return 3;
			case GOLD:
			case CHAIN:
				return 3;
			case DIAMOND:
				return 5;
			}

			if (armor.getArmorMaterial() == AetherMaterials.LEGENDARY_ARMOR || armor.getArmorMaterial() == AetherMaterials.OBSIDIAN_ARMOR || armor.getArmorMaterial() == AetherMaterials.VALKYRIE_ARMOR)
			{
				return 6;
			}
		}

		return 5;
	}

	@Override
	public ItemStack getOutput(ItemStack stack)
	{
		stack.setItemDamage(0);

		return stack;
	}
}

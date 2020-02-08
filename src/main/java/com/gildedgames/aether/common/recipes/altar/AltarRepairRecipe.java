package com.gildedgames.aether.common.recipes.altar;

import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import com.gildedgames.aether.common.init.MaterialsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherShield;
import com.gildedgames.aether.common.items.tools.ItemArkeniumShears;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.item.*;

public class AltarRepairRecipe implements IAltarRecipe
{
	@Override
	public boolean matchesInput(final ItemStack stack)
	{
		return (stack.getItem() instanceof ItemSword
				|| stack.getItem() instanceof ItemTool
				|| stack.getItem() instanceof ItemArmor
				|| stack.getItem() instanceof ItemShears
				|| stack.getItem() instanceof ItemArkeniumShears
				|| stack.getItem() instanceof ItemCrossbow
				|| stack.getItem() instanceof ItemBow
				|| stack.getItem() instanceof ItemAetherShield
		) && stack.isItemDamaged();
	}

	@Override
	public boolean matchesOutput(ItemStack stack)
	{
		return false;
	}

	@Override
	public int getAmbrosiumCost(final ItemStack stack)
	{
		if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemCrossbow || stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemAetherShield)
		{
			return 6;
		}
		else if (stack.getItem() instanceof ItemTool)
		{
			final ItemTool tool = (ItemTool) stack.getItem();

			switch (tool.getToolMaterialName())
			{
				case "WOOD":
					return 2;
				case "STONE":
					return 3;
				case "IRON":
					return 4;
				case "DIAMOND":
					return 5;
				case "GOLD":
					return 3;
			}
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			final ItemArmor armor = (ItemArmor) stack.getItem();

			switch (armor.getArmorMaterial())
			{
				case LEATHER:
					return 2;
				case IRON:
					return 3;
				case GOLD:
					return 3;
				case CHAIN:
					return 3;
				case DIAMOND:
					return 5;
			}

			if (armor.getArmorMaterial() == MaterialsAether.LEGENDARY_ARMOR || armor.getArmorMaterial() == MaterialsAether.VALKYRIE_ARMOR)
			{
				return 6;
			}
		}
		else if (stack.getItem() instanceof ItemShears || stack.getItem() instanceof ItemArkeniumShears)
		{
			return 4;
		}

		return 5;
	}

	@Override
	public ItemStack getOutput(final ItemStack stack)
	{
		stack.setItemDamage(0);

		return stack;
	}

	@Override
	public ItemStack getInput()
	{
		return null;
	}
}

package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemAetherShovel extends ItemSpade
{
	public ItemAetherShovel(ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("shovel", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolHandler.onEntityHit(stack, this.getToolMaterial(), target, attacker);
	}
}

package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemAetherAxe extends ItemAxe
{
	public ItemAetherAxe(ToolMaterial material)
	{
		// The parent constructor will crash trying to set parameters, we need to do it here
		this(material, 6.0F, -3.2F);
	}

	public ItemAetherAxe(ToolMaterial material, float damageVsEntity, float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);

		this.setHarvestLevel("axe", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolHandler.onEntityHit(stack, this.getToolMaterial(), target, attacker);
	}
}

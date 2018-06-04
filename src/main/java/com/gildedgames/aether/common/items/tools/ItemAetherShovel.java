package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemAetherShovel extends ItemSpade
{
	public ItemAetherShovel(final ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("shovel", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolHandler.onEntityHit(stack, this.toolMaterial, target, attacker);
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		if (this.toolMaterial == MaterialsAether.SKYROOT_TOOL)
		{
			return 100;
		}

		return super.getItemBurnTime(itemStack);
	}
}

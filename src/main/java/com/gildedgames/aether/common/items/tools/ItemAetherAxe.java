package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.events.listeners.items.ItemToolListener;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemAetherAxe extends ItemAxe
{
	public ItemAetherAxe(final ToolMaterial material)
	{
		// The parent constructor will crash trying to set parameters, we need to do it here
		this(material, 6.0F, -3.2F);
	}

	public ItemAetherAxe(final ToolMaterial material, final float damageVsEntity, final float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);

		this.setHarvestLevel("axe", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TAB_TOOLS);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final LivingEntity target, final LivingEntity attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolListener.onEntityHit(stack, this.toolMaterial, target, attacker);
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

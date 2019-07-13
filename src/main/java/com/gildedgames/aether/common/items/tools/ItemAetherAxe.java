package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.events.listeners.items.ItemToolListener;
import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAetherAxe extends AxeItem
{
	public ItemAetherAxe(final IItemTier tier, final float damageVsEntity, final float attackSpeed, Item.Properties properties)
	{
		super(tier, damageVsEntity, attackSpeed, properties);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final LivingEntity target, final LivingEntity attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolListener.onEntityHit(stack, this.getTier(), target, attacker);
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		if (this.getTier() == MaterialsAether.SKYROOT_TOOL)
		{
			return 100;
		}

		return super.getBurnTime(itemStack);
	}
}

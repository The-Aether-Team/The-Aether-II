package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemSkyrootConsumableBucket extends Item
{
	public ItemSkyrootConsumableBucket()
	{
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));

		return itemStack;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}

		if (!world.isRemote)
		{
			this.applyEffect(stack, world, player);
		}

		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

		return stack.stackSize <= 0 ? new ItemStack(ItemsAether.skyroot_bucket) : stack;
	}

	private void applyEffect(ItemStack stack, World world, EntityPlayer player)
	{
		if (stack.getItem() == ItemsAether.skyroot_milk_bucket)
		{
			player.curePotionEffects(new ItemStack(Items.milk_bucket));
		}
		else if (stack.getItem() == ItemsAether.skyroot_poison_bucket)
		{
			// TODO: Poison player.
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
}

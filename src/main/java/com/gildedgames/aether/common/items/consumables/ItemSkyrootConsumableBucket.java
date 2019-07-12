package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.item.*;
import net.minecraft.item.UseAction;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemSkyrootConsumableBucket extends Item implements IDropOnDeath
{
	public ItemSkyrootConsumableBucket()
	{
		this.setMaxStackSize(1);

		this.setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		playerIn.setActiveHand(hand);

		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity living)
	{
		if (living instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) living;

			if (!((PlayerEntity) living).isCreative())
			{
				stack.shrink(1);
			}

			player.addStat(StatList.getObjectUseStats(this));
		}

		if (!world.isRemote)
		{
			this.applyEffect(stack, world, living);
		}

		return stack.getCount() <= 0 ? new ItemStack(ItemsAether.skyroot_bucket) : stack;
	}

	private void applyEffect(ItemStack stack, World world, LivingEntity player)
	{
		if (stack.getItem() == ItemsAether.skyroot_milk_bucket)
		{
			player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}
		else if (stack.getItem() == ItemsAether.skyroot_poison_bucket)
		{
			player.addPotionEffect(new PotionEffect(Effects.POISON, 100, 3));
		}
	}

	@Override
	public UseAction getItemUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
}

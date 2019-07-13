package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemSkyrootConsumableBucket extends Item
{
	public ItemSkyrootConsumableBucket(Item.Properties properties)
	{
		super(properties);
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

			player.addStat(Stats.ITEM_USED.get(this));
		}

		if (!world.isRemote())
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
			player.addPotionEffect(new EffectInstance(Effects.POISON, 100, 3));
		}
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 32;
	}
}

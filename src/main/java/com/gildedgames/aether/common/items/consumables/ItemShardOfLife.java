package com.gildedgames.aether.common.items.consumables;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemShardOfLife extends Item
{

	public ItemShardOfLife(Properties properties)
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
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity)
	{
		if (!worldIn.isRemote)
		{
			entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 600, 0));
		}

		boolean decreaseStackSize = true;

		if (entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entity;

			if (player.isCreative())
			{
				decreaseStackSize = false;
			}
		}

		if (decreaseStackSize)
		{
			stack.shrink(1);
		}

		return stack;
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

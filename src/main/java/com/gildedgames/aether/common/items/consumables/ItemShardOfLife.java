package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemShardOfLife extends Item implements IDropOnDeath
{

	public ItemShardOfLife()
	{
		super();
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
			entity.addPotionEffect(new PotionEffect(Effects.REGENERATION, 600, 0));
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

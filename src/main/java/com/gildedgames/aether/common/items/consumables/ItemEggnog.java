package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemEggnog extends Item
{
	public ItemEggnog(Item.Properties properties)
	{
		super(properties);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		PlayerEntity entityplayer = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

		if (entityplayer == null || !entityplayer.isCreative())
		{
			stack.shrink(1);
		}

		if (entityplayer instanceof ServerPlayerEntity)
		{
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) entityplayer, stack);
		}

		if (!worldIn.isRemote)
		{
			entityLiving.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
			PlayerAether.getPlayer(entityplayer).setDrankEggnog();
		}

		if (entityplayer != null)
		{
			entityplayer.addStat(Stats.ITEM_USED.get(this));
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		playerIn.setActiveHand(handIn);

		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
	}
}

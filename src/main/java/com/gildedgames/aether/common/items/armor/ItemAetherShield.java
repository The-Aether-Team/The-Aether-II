package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemAetherShield extends ShieldItem
{
	public ItemAetherShield(Item.Properties properties)
	{
		super(properties);

		this.addPropertyOverride(new ResourceLocation("blocking"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);

		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		if (itemStack.getItem() == ItemsAether.skyroot_shield)
		{
			return 100;
		}

		return super.getBurnTime(itemStack);
	}

}

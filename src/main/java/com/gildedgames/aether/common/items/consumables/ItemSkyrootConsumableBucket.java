package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSkyrootConsumableBucket extends Item
{
	public ItemSkyrootConsumableBucket()
	{
		this.setMaxStackSize(1);

		this.setCreativeTab(CreativeTabsAether.MISCELLANEOUS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		playerIn.setActiveHand(hand);

		return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) living;

			if (!((EntityPlayer) living).capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}

			player.addStat(StatList.getObjectUseStats(this));
		}

		if (!world.isRemote)
		{
			this.applyEffect(stack, world, living);
		}

		return stack.stackSize <= 0 ? new ItemStack(ItemsAether.skyroot_bucket) : stack;
	}

	private void applyEffect(ItemStack stack, World world, EntityLivingBase player)
	{
		if (stack.getItem() == ItemsAether.skyroot_milk_bucket)
		{
			player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}
		else if (stack.getItem() == ItemsAether.skyroot_poison_bucket)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 3));
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

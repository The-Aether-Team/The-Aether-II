package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemHealingStone extends Item implements IDropOnDeath
{

	public static final int MAX_USES = 5;

	public ItemHealingStone()
	{
		super();

		this.addPropertyOverride(new ResourceLocation("uses"), (stack, world, entity) ->
		{
			if (!stack.isEmpty())
			{
				return ItemHealingStone.getUsesLeft(stack) * 0.2F;
			}

			return 1.0F;
		});
	}

	private static void initTagCompound(ItemStack stack)
	{
		CompoundNBT tag = new CompoundNBT();

		tag.putInt("usesLeft", MAX_USES);

		stack.setTagCompound(tag);
	}

	public static int getUsesLeft(ItemStack stack)
	{
		if (stack == null)
		{
			return 0;
		}

		if (stack.getTagCompound() == null)
		{
			initTagCompound(stack);
		}

		return stack.getTagCompound().getInt("usesLeft");
	}

	public static void setUsesLeft(ItemStack stack, int usesLeft)
	{
		if (stack == null)
		{
			return;
		}

		if (stack.getTagCompound() == null)
		{
			initTagCompound(stack);
		}

		stack.getTagCompound().putInt("usesLeft", usesLeft);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (getUsesLeft(stack) > 0 && player.getAbsorptionAmount() < 20.0F)
		{
			player.setActiveHand(Hand.MAIN_HAND);
			return new ActionResult<>(ActionResultType.SUCCESS, stack);
		}

		return new ActionResult<>(ActionResultType.FAIL, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity)
	{
		setUsesLeft(stack, getUsesLeft(stack) - 1);

		if (!worldIn.isRemote)
		{
			if (entity.getHealth() < entity.getMaxHealth())
			{
				float dif = entity.getMaxHealth() - entity.getHealth();
				float leftOver = 4.0F - dif;

				if (dif > 4.0F)
				{
					entity.heal(4.0F);
				}
				else if (leftOver > 0.0F)
				{
					entity.heal(dif);
					entity.setAbsorptionAmount(Math.min(20.0F, entity.getAbsorptionAmount() + leftOver));
				}
			}
			else
			{
				entity.setAbsorptionAmount(Math.min(20.0F, entity.getAbsorptionAmount() + 4.0F));
			}
		}

		if (getUsesLeft(stack) <= 0)
		{
			stack = new ItemStack(ItemsAether.healing_stone_depleted);
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

	@Override
	public boolean getShareTag()
	{
		return true;
	}

}

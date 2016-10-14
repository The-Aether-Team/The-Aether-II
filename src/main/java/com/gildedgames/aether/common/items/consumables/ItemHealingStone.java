package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemMoaEgg;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemCrossbow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemHealingStone extends Item
{

	public static final int MAX_USES = 5;

	public ItemHealingStone()
	{
		super();

		this.addPropertyOverride(new ResourceLocation("uses"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				if (stack != null)
				{
					return ItemHealingStone.getUsesLeft(stack) * 0.2F;
				}

				return 1.0F;
			}
		});
	}

	private static void initTagCompound(ItemStack stack)
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("usesLeft", MAX_USES);

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

		return stack.getTagCompound().getInteger("usesLeft");
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

		stack.getTagCompound().setInteger("usesLeft", usesLeft);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (getUsesLeft(stack) > 0 && playerIn.getAbsorptionAmount() < 20.0F)
		{
			playerIn.setActiveHand(EnumHand.MAIN_HAND);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult(EnumActionResult.FAIL, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity)
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
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
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

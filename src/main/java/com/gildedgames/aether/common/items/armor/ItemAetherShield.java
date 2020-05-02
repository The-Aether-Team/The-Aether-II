package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherShield extends Item
{
	public final ShieldType shieldType;

	public boolean isBlocking;

	public ItemAetherShield(ShieldType type)
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(336);

		this.shieldType = type;

		this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
		{
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entity)
			{
				return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

		this.setCreativeTab(CreativeTabsAether.TAB_ARMOR);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);

		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUsingTick(final ItemStack stack, final EntityLivingBase living, final int count)
	{
		this.isBlocking = true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		this.isBlocking = false;

		return stack;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		if (itemStack.getItem() == ItemsAether.skyroot_shield)
		{
			return 200;
		}

		return super.getItemBurnTime(itemStack);
	}

	public double getStunResistance()
	{
		return this.shieldType.getStunResistance();
	}
	public enum ShieldType
	{
		SKYROOT(0.0D),
		HOLYSTONE(0.1D),
		ZANITE(0.25D),
		ARKENIUM(0.6D),
		GRAVITITE(0.4D);

		private final double stunResistance;

		ShieldType(double stunResistance)
		{
			this.stunResistance = stunResistance;
		}

		public double getStunResistance()
		{
			return this.stunResistance;
		}
	}
}

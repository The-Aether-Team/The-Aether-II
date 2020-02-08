package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherShield extends Item
{
	public ItemAetherShield()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(336);

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
	public int getItemBurnTime(ItemStack itemStack)
	{
		if (itemStack.getItem() == ItemsAether.skyroot_shield)
		{
			return 200;
		}

		return super.getItemBurnTime(itemStack);
	}

}

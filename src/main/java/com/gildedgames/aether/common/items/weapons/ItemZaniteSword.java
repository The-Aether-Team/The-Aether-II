package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemZaniteSword extends ItemAetherSword
{
	public ItemZaniteSword()
	{
		super(ToolMaterial.IRON);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		float damage = 6f + (2.0F * stack.getItemDamage() / stack.getItem().getMaxDamage());

		if (target instanceof EntityPlayer)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), damage);
		}
		else
		{
			target.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage);
		}

		stack.damageItem(1, attacker);

		return false;
	}

	@Override
	public float getDamageVsEntity()
	{
		// We apply our own DamageSource in hitEntity(stack, target, attacker).
		return 0.0f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Becomes stronger as");
		tooltip.add(EnumChatFormatting.WHITE + "durability decreases");
	}
}

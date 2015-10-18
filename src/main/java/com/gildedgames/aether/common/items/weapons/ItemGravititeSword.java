package com.gildedgames.aether.common.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemGravititeSword extends ItemAetherSword
{
	public ItemGravititeSword()
	{
		super(ToolMaterial.EMERALD);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (attacker.isSneaking())
		{
			// This prevents players from sending entities into the air rapidly
			if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
			{
				target.addVelocity(0.0D, 0.5D, 0.0D);
			}
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Shifts gravity on mobs");
		tooltip.add(EnumChatFormatting.DARK_AQUA + "Use: " + EnumChatFormatting.WHITE + "Sneak while attacking");
	}
}

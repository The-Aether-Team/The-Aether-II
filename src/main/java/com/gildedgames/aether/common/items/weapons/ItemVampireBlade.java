package com.gildedgames.aether.common.items.weapons;

import java.util.List;

import com.gildedgames.aether.common.AetherMaterials;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class ItemVampireBlade extends ItemSword
{
	public ItemVampireBlade()
	{
		super(AetherMaterials.LEGENDARY_TOOL);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		int healPlayer = (int)(Math.min(target.getHealth(), 20) / getDamageVsEntity());
		
		if (attacker instanceof EntityPlayer)
		{
			attacker.heal(Math.max(0, healPlayer));
		}

		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Leeches life");
	}

}

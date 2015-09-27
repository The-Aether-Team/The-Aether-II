package com.gildedgames.aether.common.items.weapons;

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
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Shifts gravity on mobs");
		tooltip.add(EnumChatFormatting.DARK_AQUA + "Use: " + EnumChatFormatting.WHITE + "Sneak while attacking");
	}
}

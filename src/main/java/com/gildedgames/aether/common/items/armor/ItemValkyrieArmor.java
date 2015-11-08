package com.gildedgames.aether.common.items.armor;

import java.util.List;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemValkyrieArmor extends ItemAetherArmor
{
	public ItemValkyrieArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}
	
	public boolean wearingFullSet(PlayerAether player)
	{
		return player.wearingArmour(ItemsAether.zanite_boots) &&
				player.wearingArmour(ItemsAether.zanite_chestplate) &&
				player.wearingArmour(ItemsAether.zanite_leggings) &&
				player.wearingArmour(ItemsAether.zanite_helmet);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (this.wearingFullSet(PlayerAether.get(player)))
			{
				// Add wearing full set effect here
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Wings and Gliding");
	}
}

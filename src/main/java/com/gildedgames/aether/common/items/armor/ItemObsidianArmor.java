package com.gildedgames.aether.common.items.armor;

import java.util.List;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemObsidianArmor extends ItemAetherArmor
{
	public ItemObsidianArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}

	public boolean wearingFullSet(PlayerAether player)
	{
		return player.wearingArmour(ItemsAether.obsidian_boots) &&
				player.wearingArmour(ItemsAether.obsidian_chestplate) &&
				player.wearingArmour(ItemsAether.obsidian_leggings) &&
				player.wearingArmour(ItemsAether.obsidian_helmet);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (this.wearingFullSet(PlayerAether.get(player)))
			{
				// Resistance is multiplied by 3; I wanted to do 4 but that made you practically invincible
				// For estimation: 3: 5 zombie hits take half a heart ; 4: 10+ zombie hits...
				PotionEffect resistanceUp = new PotionEffect(11, 2, 3, false, false);
				player.addPotionEffect(resistanceUp);
			}
		}
	}
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Extreme Protection");
	}
}

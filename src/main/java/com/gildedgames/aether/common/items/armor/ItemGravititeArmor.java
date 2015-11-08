package com.gildedgames.aether.common.items.armor;

import java.util.List;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.player.PlayerAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGravititeArmor extends ItemAetherArmor
{
	public ItemGravititeArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}
	
	public boolean isWearingFullSet(EntityPlayer player)
	{
		
		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack == null || !(stack.getItem() instanceof ItemGravititeArmor))
			{
					return false;
			}
		}
		return true;
	}

	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (this.isWearingFullSet(player))
			{
				if (player.isSneaking())
				{
					// Allows the player to jump 5 blocks
					PotionEffect extendedJump = new PotionEffect(Potion.jump.getId(), 2, 4, false, false);
					player.addPotionEffect(extendedJump);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Extended Jump");
	}
}

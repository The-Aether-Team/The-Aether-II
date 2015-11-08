package com.gildedgames.aether.common.items.armor;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.util.PlayerUtil;

public class ItemZaniteArmor extends ItemAetherArmor
{
	public ItemZaniteArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (this.armorType == 0 && PlayerUtil.isWearingFullSet(player, ItemZaniteArmor.class))
			{
				// 3 = helmet, deplets the fastest
				// Dividing everything by 45 so max resistance is 3 (after this damage reduction gets ridiculous)
				int armourVal = player.getCurrentArmor(3).getItemDamage() / 45;

				// Potion Effect Params: Resistance, duration, resistance multiplier, splash, particles
				PotionEffect resistanceUp = new PotionEffect(11, 1, armourVal / 45, false, false);

				if (armourVal >= 1)
				{
					player.addPotionEffect(resistanceUp);
				}
			}
			else if (player.isPotionActive(11))
			{
				player.removePotionEffect(11);
			}
		}
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

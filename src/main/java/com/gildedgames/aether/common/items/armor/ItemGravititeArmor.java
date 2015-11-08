package com.gildedgames.aether.common.items.armor;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.util.PlayerUtil;

public class ItemGravititeArmor extends ItemAetherArmor
{
	public ItemGravititeArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!world.isRemote)
		{
			if (PlayerUtil.isWearingFullSet(player, ItemGravititeArmor.class))
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

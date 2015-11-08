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

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}
	
	public boolean isWearingFullSet(EntityPlayer player)
	{
		
		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack == null || !(stack.getItem() instanceof ItemNeptuneArmor))
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
				if (player.handleWaterMovement())
				{
					// duration 4 is the lowest value where the screen doesn't bounce back and forth while swimming (holding space on surface)
					PotionEffect speedUp = new PotionEffect(Potion.moveSpeed.getId(), 4, 3, false, false);
					PotionEffect waterBreath = new PotionEffect(Potion.waterBreathing.getId(), 2, 0, false, false);
					
					//player.setSprinting(true);
					player.addPotionEffect(speedUp);
					player.addPotionEffect(waterBreath);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Breath and walk");
		tooltip.add(EnumChatFormatting.WHITE + "underwater");
	}
}

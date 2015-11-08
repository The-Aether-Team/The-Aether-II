package com.gildedgames.aether.common.items.armor;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.util.PlayerUtil;

public class ItemZaniteArmor extends ItemAetherArmor
{
	public ItemZaniteArmor(EnumAetherArmorVariant material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
	}

<<<<<<< HEAD
	public boolean isWearingFullSet(EntityPlayer player)
	{
		
		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack == null || !(stack.getItem() instanceof ItemZaniteArmor))
			{
					return false;
			}
		}
		return true;
	}

	
	/*
	public void onPlayerAttacked(LivingAttackEvent event)
=======
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
>>>>>>> origin/master
	{
		if (event.entityLiving instanceof EntityPlayer && !event.source.isMagicDamage())
		{
<<<<<<< HEAD
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (this.armorType == 0 && this.isWearingFullSet(player))
=======
			if (this.armorType == 0 && PlayerUtil.isWearingFullSet(player, ItemZaniteArmor.class))
>>>>>>> origin/master
			{

				int peicesWorn = 0; 
				int totalDamage = 0;
				int maxDamage = 0;
				for (ItemStack stack : player.inventory.armorInventory)
				{
					if (stack != null && stack.getItem() == this)
					{
						peicesWorn++;
						totalDamage += stack.getItemDamage();
						maxDamage += stack.getItem().getMaxDamage();
					}
				}

				float percentDamaged = (float) totalDamage / ((float) maxDamage*peicesWorn);

				// 3 = helmet, deplets the fastest
				// Dividing everything by 45 so max resistance is 3 (after this damage reduction gets ridiculous)
<<<<<<< HEAD
				int armorVal = (int)(percentDamaged / 45);

				// Potion Effect Params: Resistance, duration, resistance multiplier, splash, particles
				// duration is set to 2 for invincible effects. I noticed some "unstableness" when it was set to 1
				PotionEffect resistanceUp = new PotionEffect(11, 2, armorVal, false, false);
				System.out.println(armorVal);
=======
				int armourVal = player.getCurrentArmor(3).getItemDamage() / 45;

				// Potion Effect Params: Resistance, duration, resistance multiplier, splash, particles
				PotionEffect resistanceUp = new PotionEffect(11, 1, armourVal / 45, false, false);
>>>>>>> origin/master

				if (armorVal >= 1)
				{
					player.addPotionEffect(resistanceUp);
				}

				if (player.getHealth() < 2)
				{
					player.setHealth(20);
				}
			}
			else if (player.isPotionActive(11))
			{
				player.removePotionEffect(11);
			}
		}
	}
*/
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Becomes stronger as");
		tooltip.add(EnumChatFormatting.WHITE + "durability decreases");
	}
}

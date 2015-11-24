package com.gildedgames.aether.common.items.armor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemAetherArmorAbility 
{
	
	ItemAetherArmorAbility(EntityPlayer player, int ability) 
	{
		if (ability == 1) { this.phoenixArmorAbility(player); }
		if (ability == 2) { this.neptuneArmorAbility(player); }
		if (ability == 3) { this.obsidianArmorAbility(player); }
		
	}
	
	private void phoenixArmorAbility(EntityPlayer player)
	{
		PotionEffect flameResistance = new PotionEffect(Potion.fireResistance.getId(), 2, 0, false, false);
		player.addPotionEffect(flameResistance);
		player.extinguish();
	}

	private void neptuneArmorAbility(EntityPlayer player)
	{
		if (player.isInWater()) // if water is touching the player (excludes rain)
		{
		}
		if (player.isInsideOfMaterial(Material.water)) // if head is submerged
		{
			// mining speed boost should go here.
			PotionEffect waterBreath = new PotionEffect(Potion.waterBreathing.getId(), 2, 0, false, false);
			player.addPotionEffect(waterBreath);
		}
	}
	private void obsidianArmorAbility(EntityPlayer player)
	{
		PotionEffect slowMovement = new PotionEffect(Potion.moveSlowdown.getId(), 2, 1, false, false);
		
		//Resistance is handled by a player event
		player.addPotionEffect(slowMovement);
		player.setSprinting(false);
	}
	
}

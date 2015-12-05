package com.gildedgames.aether.common.items.armor;

import java.util.Set;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;

public class ItemAetherArmorAbility 
{
	
	ItemAetherArmorAbility(EntityPlayer player, int ability) 
	{
		if (ability == 1) { this.phoenixArmorAbility(player); }
		if (ability == 2) { this.neptuneArmorAbility(player); }
		if (ability == 3) { this.obsidianArmorAbility(player); }
		if (ability == 4) { this.sentryBootsAbility(player); }
		
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
	
	private void sentryBootsAbility(EntityPlayer player) {
		// Still working on porting from 1.7, sorta ignore this code.
		/**********************************************************
		double fallDistance = 0;

		if (player.fallDistance > 0) {
			fallDistance = player.fallDistance;
		}

		BlockPos getBlockPos = new BlockPos((int) player.posX, player.posY-1, player.posZ);
		Block getBlock = player.worldObj.getBlockState(getBlockPos).getBlock();
		
		
		Set blockExceptions = Sets.newHashSet(new Block[] { Blocks.air, Blocks.water, Blocks.lava, BlocksAether.aercloud }); // removed BlocksAether.PurpleAercloud

		
		if (player.motionY < 0) {
			if (!blockExceptions.contains(getBlock)) {
				player.fallDistance = 0;
				//int featherFallingAmount = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.featherFalling.effectId, player.getInventory());

				System.out.println(fallDistance);

				if (fallDistance > 5) {
					//if (fallDistance > 5 + (featherFallingAmount * 2)) {
						player.attackEntityFrom(DamageSource.fall, (float) fallDistance/2 );
					}
				//} else if (featherFallingAmount <= 0 && fallDistance > 5) {
				//	player.attackEntityFrom(DamageSource.fall, (float) fallDistance - 5);
				//}
			}
		}
		*/

		/*
		 * if (player.motionY <= -1.65) { if
		 * (!blockExceptions.contains(getBlock) && !player.isDead) {
		 * player.triggerAchievement(AetherAchievements.aetherture); } }
		 */
	}

}

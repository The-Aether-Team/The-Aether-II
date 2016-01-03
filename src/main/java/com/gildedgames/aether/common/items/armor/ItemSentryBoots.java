package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public class ItemSentryBoots extends ItemAetherArmor {

	public ItemSentryBoots(ArmorMaterial material, int armorType)
	{
		super(material, "sentry_boots", armorType);
	}
	
	// Big block containing code from 1.7
	
	/*****
	 * 		if (this.player.wearingArmour(AetherItems.SentryBoots))
		{
			double fallDistance = 0;

			if (this.entityPlayer.fallDistance > 0)
			{
				fallDistance = this.entityPlayer.fallDistance;
			}

			Block getBlock = this.entityPlayer.worldObj.getBlock((int) this.entityPlayer.posX, (int) this.entityPlayer.posY - 1, (int) this.entityPlayer.posZ);
			Set blockExceptions = Sets.newHashSet(new Block[] { Blocks.air, Blocks.water, Blocks.lava, AetherBlocks.Aercloud, AetherBlocks.PurpleAercloud });

			if (this.entityPlayer.motionY < 0)
			{
				if (!blockExceptions.contains(getBlock))
				{
					this.entityPlayer.fallDistance = 0;
					int featherFallingAmmount = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.featherFalling.effectId, this.entityPlayer.getInventory());

					if (featherFallingAmmount > 0)
					{
						if (fallDistance > 5 + (featherFallingAmmount * 2))
						{
							this.entityPlayer.attackEntityFrom(DamageSource.fall, (float) fallDistance - (5 + (featherFallingAmmount * 2)));
						}
					}
					else if (featherFallingAmmount <= 0 && fallDistance > 5)
					{
						this.entityPlayer.attackEntityFrom(DamageSource.fall, (float) fallDistance - 5);
					}
				}
			}

			if (this.entityPlayer.motionY <= -1.65)
			{
				if (!blockExceptions.contains(getBlock) && !this.entityPlayer.isDead)
				{
					this.entityPlayer.triggerAchievement(AetherAchievements.aetherture);
				}
			}
		}
	 */

}

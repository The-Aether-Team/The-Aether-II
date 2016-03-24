package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherMaterials;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemPigSlayer extends ItemAetherSword
{
	public ItemPigSlayer()
	{
		super(AetherMaterials.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);

		this.setMaxDamage(200);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target instanceof EntityPig || target instanceof EntityPhyg || target instanceof EntityPigZombie)
		{
			if (target.getHealth() > 0)
			{
				if (attacker instanceof EntityPlayer)
				{
					target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), Integer.MAX_VALUE);
				}
				else
				{
					target.attackEntityFrom(DamageSource.causeMobDamage(attacker), Integer.MAX_VALUE);
				}

				target.setVelocity(0, 0, 0);
			}
		}

		return super.hitEntity(stack, target, attacker);
	}
}

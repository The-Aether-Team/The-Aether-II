package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class ItemGravititeSword extends ItemAetherSword
{

	public ItemGravititeSword(IItemTier tier, ItemAbilityType abilityType, int attackDamageIn, float attackSpeedIn,
			Properties builder)
	{
		super(tier, abilityType, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		if (attacker.isSneaking())
		{
			// This prevents players from sending entities into the air rapidly
			if (target.hurtTime == target.maxHurtTime && target.deathTime <= 0)
			{
				target.addVelocity(0.0D, 0.5D, 0.0D);
			}
		}

		return super.hitEntity(stack, target, attacker);
	}
}

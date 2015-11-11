package com.gildedgames.aether.common.items.weapons.swords;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemGravititeSword extends ItemAetherSword
{
	public ItemGravititeSword()
	{
		super(ToolMaterial.EMERALD);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
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

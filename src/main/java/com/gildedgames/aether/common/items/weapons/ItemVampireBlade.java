package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemVampireBlade extends ItemAetherSword
{
	public ItemVampireBlade()
	{
		super(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		final int healPlayer = (int) (Math.min(target.getHealth(), 20) / this.getAttackDamage());

		if (attacker instanceof EntityPlayer)
		{
			attacker.heal(Math.max(0, healPlayer));
		}

		return super.hitEntity(stack, target, attacker);
	}
}

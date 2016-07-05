package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.MaterialsAether;
import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
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
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		int healPlayer = (int) (Math.min(target.getHealth(), 20) / getDamageVsEntity());

		if (attacker instanceof EntityPlayer)
		{
			attacker.heal(Math.max(0, healPlayer));
		}

		return super.hitEntity(stack, target, attacker);
	}
}

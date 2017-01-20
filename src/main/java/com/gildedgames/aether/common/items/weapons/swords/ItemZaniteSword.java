package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import javax.tools.Tool;

public class ItemZaniteSword extends ItemAetherSword
{
	private float mobDamage = 6;
	public ItemZaniteSword()
	{
		super(ToolMaterial.IRON, ItemAbilityType.PASSIVE);
	}


	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		float damage = ToolMaterial.IRON.getDamageVsEntity() + ((float) (stack.getItemDamage() * 4) / stack.getItem().getMaxDamage()); // on last hit, sword will deal 10 damage (5 hearts)

		if (target instanceof EntityPlayer)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), damage);
		}
		else
		{
			target.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage);
		}

		stack.damageItem(1, attacker);
		return false;
	}
}

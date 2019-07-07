package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.living.companions.EntityCompanion;
import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCandyCaneSword extends ItemAetherSword
{
	public ItemCandyCaneSword()
	{
		super(ToolMaterial.STONE, ItemAbilityType.PASSIVE);

		this.setMaxDamage(25);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target instanceof EntityCompanion)
		{
			return true;
		}

		if (!attacker.world.isRemote && target != null && attacker instanceof EntityPlayer)
		{
			if (attacker.world.rand.nextInt(3) == 0)
			{

				target.dropItemWithOffset(ItemsAether.candy_cane, 1, 0F);
			}
		}

		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return (repair.getItem() == ItemsAether.candy_cane);
	}
}

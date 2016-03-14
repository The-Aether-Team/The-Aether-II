package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

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
		if (!attacker.worldObj.isRemote && target != null && attacker instanceof EntityPlayer)
		{
			if (attacker.worldObj.rand.nextInt(3) == 0)
			{
				// TODO: When companions are added we need to add a check to make sure player isn't hitting companion

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

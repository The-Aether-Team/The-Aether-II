package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemCandyCaneSword extends ItemAetherSword
{
	public ItemCandyCaneSword() {
		super(ToolMaterial.STONE);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (new Random().nextBoolean())
		{
			if (target != null && attacker instanceof EntityPlayer && !attacker.worldObj.isRemote)
			{
				// When companions are added we need to add a checkt o make sure player isnt hitting companion
				target.dropItemWithOffset(ItemsAether.candy_cane, 1, 0F);
			}
		}

		stack.damageItem(10, attacker);
		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		System.out.println("Can repair");
		return (repair.getItem() == ItemsAether.candy_cane);
	}
}

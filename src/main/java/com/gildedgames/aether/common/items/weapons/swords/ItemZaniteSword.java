package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemZaniteSword extends ItemAetherSword
{
	public double bonusDamage = 0.0;

	public ItemZaniteSword()
	{
		super(ToolMaterial.IRON, ItemAbilityType.PASSIVE);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			if (((EntityLivingBase) entityIn).getHeldItemMainhand() == stack)
			{
				//This equals to a bonus of 6 Slash Damage Levels.
				this.bonusDamage = (float) (stack.getItemDamage() * 7)
						/ stack.getItem().getMaxDamage();
			}
		}
	}
}

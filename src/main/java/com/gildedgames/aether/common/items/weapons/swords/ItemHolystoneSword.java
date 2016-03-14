package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class ItemHolystoneSword extends ItemAetherSword
{
	public ItemHolystoneSword()
	{
		super(ToolMaterial.STONE, ItemAbilityType.PASSIVE);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target.worldObj.rand.nextInt(100) <= 5)
		{
			EntityItem entityItem = new EntityItem(target.worldObj, target.posX, target.posY, target.posZ);
			entityItem.setEntityItemStack(new ItemStack(ItemsAether.ambrosium_shard, 1));

			target.worldObj.spawnEntityInWorld(entityItem);
		}

		return super.hitEntity(stack, target, attacker);
	}
}

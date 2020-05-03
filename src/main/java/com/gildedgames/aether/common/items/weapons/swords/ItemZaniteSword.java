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
	public ItemZaniteSword()
	{
		super(ToolMaterial.IRON, ItemAbilityType.PASSIVE);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		/**************************
		 |*	It appears that stack.getItemDamage() / stack.getItem().getMaxDamage() never returns anything >= 1. So it adds small decimals to the health decrese
		 |* Originally I felt we could just multiply stack.getItemDamage by 2, but that essentially only allowed a max of +2 damage (so 1 more heart)
		 |* This really doesn't feel powerful.
		 |* what I ended up figuring out: Whatever number we multiply stack.getItemDamage() by (in this case 4) is the TOTAL extra damage that will be dealt to
		 |* to the enemy when the sword is on its last hit. As in, the final hit of the sword will deal 4 extra damage, prior hits will deal building up to this
		 |* point.
		 |* Also, this item breaks when item damage is 250. Just in case someone wanted to add an added effect or extra damage on that last hit.
		 */
		final float damage = 6f + ((float) (stack.getItemDamage() * 4)
				/ stack.getItem().getMaxDamage()); // on last hit, sword will deal 10 damage (5 hearts)

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

	@Override
	public float getAttackDamage()
	{
		// We apply our own DamageSource in hitEntity(stack, target, attacker).
		return 0.0f;
	}
}

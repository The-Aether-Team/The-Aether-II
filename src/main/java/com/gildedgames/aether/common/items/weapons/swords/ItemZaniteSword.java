package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.DamageSource;

import java.util.function.Consumer;

public class ItemZaniteSword extends ItemAetherSword
{

	public ItemZaniteSword(IItemTier tier, ItemAbilityType abilityType, int attackDamageIn, float attackSpeedIn,
			Properties builder)
	{
		super(tier, abilityType, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final LivingEntity target, final LivingEntity attacker)
	{
		/**************************
		 |*	It appears that stack.getDamage() / stack.getItem().getMaxDamage() never returns anything >= 1. So it adds small decimals to the health decrese
		 |* Originally I felt we could just multiply stack.getItemDamage by 2, but that essentially only allowed a max of +2 damage (so 1 more heart)
		 |* This really doesn't feel powerful.
		 |* what I ended up figuring out: Whatever number we multiply stack.getDamage() by (in this case 4) is the TOTAL extra damage that will be dealt to
		 |* to the enemy when the sword is on its last hit. As in, the final hit of the sword will deal 4 extra damage, prior hits will deal building up to this
		 |* point. 
		 |* Also, this item breaks when item damage is 250. Just in case someone wanted to add an added effect or extra damage on that last hit.
		 */
		final float damage = 6f + ((float) (stack.getDamage() * 4)
				/ stack.getItem().getMaxDamage()); // on last hit, sword will deal 10 damage (5 hearts)

		if (target instanceof PlayerEntity)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), damage);
		}
		else
		{
			target.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage);
		}

		// TODO: What is this last arg
		stack.damageItem(1, attacker, (item) -> { });

		return false;
	}

	@Override
	public float getAttackDamage()
	{
		// We apply our own DamageSource in hitEntity(stack, target, attacker).
		return 0.0f;
	}
}

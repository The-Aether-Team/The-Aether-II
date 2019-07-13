package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class ItemHolystoneSword extends ItemAetherSword
{

	public ItemHolystoneSword(IItemTier tier, ItemAbilityType abilityType, int attackDamageIn, float attackSpeedIn,
			Properties builder)
	{
		super(tier, abilityType, attackDamageIn, attackSpeedIn, builder);
	}

	public static void trySpawnAmbrosium(final ItemStack stack, final Entity target, final LivingEntity attacker)
	{
		if (!target.world.isRemote() && target.world.rand.nextInt(100) <= 3)
		{
			final ItemEntity entityItem = new ItemEntity(target.world, target.posX, target.posY, target.posZ);
			entityItem.setItem(new ItemStack(ItemsAether.ambrosium_shard, 1));

			target.world.addEntity(entityItem);
		}
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final LivingEntity target, final LivingEntity attacker)
	{
		ItemHolystoneSword.trySpawnAmbrosium(stack, target, attacker);

		return super.hitEntity(stack, target, attacker);
	}
}

package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.world.World;

public class EntityAetherMonster extends MonsterEntity
{

	public EntityAetherMonster(EntityType<? extends MonsterEntity> type, World world)
	{
		super(type, world);
	}

	@Override
	protected PathNavigator createNavigator(final World worldIn)
	{
		AetherNavigateGround navigateGround = new AetherNavigateGround(this, worldIn);

		navigateGround.setAvoidSun(true);

		return navigateGround;
	}

	protected void applyStatusEffectOnAttack(final Entity target)
	{

	}

}

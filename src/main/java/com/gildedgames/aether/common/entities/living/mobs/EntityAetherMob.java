package com.gildedgames.aether.common.entities.living.mobs;

import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityAetherMob extends EntityMob
{

	public EntityAetherMob(World world)
	{
		super(world);
	}

	@Override
	protected PathNavigate createNavigator(final World worldIn)
	{
		AetherNavigateGround navigateGround = new AetherNavigateGround(this, worldIn);

		navigateGround.setAvoidSun(true);

		return navigateGround;
	}

	protected void applyStatusEffectOnAttack(final Entity target)
	{

	}

}

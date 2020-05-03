package com.gildedgames.aether.common.entities.monsters;

import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.common.entities.ai.AetherNavigateGround;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

import java.util.Map;

public class EntityAetherMob extends EntityMob implements IDefenseLevelsHolder
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

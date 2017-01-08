package com.gildedgames.aether.common.entities.living.companions;

import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityFangrin extends EntityCombatCompanion
{
	public EntityFangrin(World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 0.9F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void addEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAether aePlayer)
	{

	}
}

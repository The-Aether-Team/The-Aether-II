package com.gildedgames.aether.common.entities.companions;

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
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
	}
}

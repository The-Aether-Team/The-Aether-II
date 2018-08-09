package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class EntityAIForcedWander extends EntityAIWander
{

	private EntityCreature entity;

	private int chance;

	private int xScatter, yScatter;

	public EntityAIForcedWander(EntityCreature entity, double speedIn, int chance)
	{
		this(entity, speedIn, chance, 10, 7);
	}

	public EntityAIForcedWander(EntityCreature entity, double speedIn, int chance, int xScatter, int yScatter)
	{
		super(entity, speedIn, chance);

		this.entity = entity;
		this.chance = chance;
		this.xScatter = xScatter;
		this.yScatter = yScatter;
	}

	@Override
	public boolean shouldExecute()
	{
		this.makeUpdate();

		if (this.entity.getRNG().nextInt(this.chance) != 0)
		{
			return false;
		}

		return super.shouldExecute();
	}

	@Override
	@Nullable
	protected Vec3d getPosition()
	{
		return RandomPositionGenerator.findRandomTarget(this.entity, this.xScatter, this.yScatter);
	}

}

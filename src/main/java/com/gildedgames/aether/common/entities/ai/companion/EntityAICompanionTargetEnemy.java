package com.gildedgames.aether.common.entities.ai.companion;

import com.gildedgames.aether.common.entities.companions.EntityCompanion;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAICompanionTargetEnemy extends EntityAITarget
{

	private EntityLivingBase target;

	private final EntityCompanion entity;

	private int timestamp;

	public EntityAICompanionTargetEnemy(EntityCompanion entity)
	{
		super(entity, false);

		this.entity = entity;
	}

	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase owner = this.entity.getOwner();

		if (owner == null)
		{
			return false;
		}
		else
		{
			this.target = owner.getLastAttacker();
			int i = owner.getLastAttackerTime();
			return i != this.timestamp && this.isSuitableTarget(this.target, false) && this.target != owner;
		}
	}

	@Override
	public void startExecuting()
	{
		if (this.target == this.entity.getOwner())
		{
			return;
		}

		this.taskOwner.setAttackTarget(this.target);
		EntityLivingBase owner = this.entity.getOwner();

		if (owner != null)
		{
			this.timestamp = owner.getLastAttackerTime();
		}

		super.startExecuting();
	}

}

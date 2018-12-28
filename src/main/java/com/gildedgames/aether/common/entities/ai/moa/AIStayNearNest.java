package com.gildedgames.aether.common.entities.ai.moa;

import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class AIStayNearNest extends EntityAIBase
{

	public final World world;

	public final EntityMoa moa;

	public final float moveSpeed;

	public int nestX;

	public int nestY;

	public int nestZ;

	public final int stayCloseDist;

	public AIStayNearNest(final EntityMoa moa, final int stayCloseDist, final float moveSpeed)
	{
		this.world = moa.world;
		this.moveSpeed = moveSpeed;
		this.moa = moa;
		this.stayCloseDist = stayCloseDist;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.moa == null || this.world == null)
		{
			return false;
		}

		if (this.moa.getFamilyNest() == null)
		{
			return false;
		}
		else if (!this.moa.getFamilyNest().hasInitialized)
		{
			return false;
		}

		this.nestX = this.moa.getFamilyNest().pos.getX();
		this.nestY = this.moa.getFamilyNest().pos.getY() + 1;
		this.nestZ = this.moa.getFamilyNest().pos.getZ();

		return !(this.moa.getDistance(this.nestX, this.nestY, this.nestZ) < this.stayCloseDist);
	}

	@Override
	public void updateTask()
	{
		super.updateTask();

		this.moa.getNavigator().tryMoveToXYZ(this.nestX - 1, this.nestY, this.nestZ - 1, this.moveSpeed);
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.shouldExecute();
	}

}

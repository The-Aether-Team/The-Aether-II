package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityAIHideFromRain extends EntityAIBase
{

	private final double movementSpeed;

	private EntityCreature entity;

	private double shelterX;

	private double shelterY;

	private double shelterZ;

	public EntityAIHideFromRain(EntityCreature entity, double movementSpeed)
	{
		this.entity = entity;
		this.movementSpeed = movementSpeed;

		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity == null)
		{
			return false;
		}

		if (!this.entity.world.canSeeSky(new BlockPos(this.entity.posX, this.entity.getEntityBoundingBox().minY, this.entity.posZ)))
		{
			return false;
		}

		if (this.entity.world.isRaining())
		{
			Vec3d vec3d = this.findPossibleShelter();

			if (vec3d == null)
			{
				return false;
			}
			else
			{
				this.shelterX = vec3d.x;
				this.shelterY = vec3d.y;
				this.shelterZ = vec3d.z;

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
	}

	@Nullable
	private Vec3d findPossibleShelter()
	{
		Random random = this.entity.getRNG();
		BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.getEntityBoundingBox().minY, this.entity.posZ);

		for (int i = 0; i < 10; ++i)
		{
			BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

			if (!this.entity.world.canSeeSky(blockpos1) && this.entity.getBlockPathWeight(blockpos1) < 0.0F)
			{
				return new Vec3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
			}
		}

		return null;
	}

}

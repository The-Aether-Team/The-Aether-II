package com.gildedgames.aether.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityUtil
{

	private EntityUtil()
	{

	}

	public static void copyDataFromOld(Entity clone, Entity oldEntity)
	{
		NBTTagCompound nbttagcompound = oldEntity.writeToNBT(new NBTTagCompound());
		nbttagcompound.removeTag("Dimension");
		clone.readFromNBT(nbttagcompound);
		clone.timeUntilPortal = oldEntity.timeUntilPortal;
	}

	public static void teleport(Entity entity, BlockPos pos)
	{
		World world = entity.worldObj;

		entity.isDead = false;

		world.updateEntityWithOptionalForce(entity, false);
		Entity clone = EntityList.createEntityByName(EntityList.getEntityString(entity), world);

		if (clone != null)
		{
			EntityUtil.copyDataFromOld(clone, entity);
		}

		world.removeEntityDangerously(entity);

		clone.prevPosX = pos.getX();
		clone.prevPosY = pos.getY();
		clone.prevPosZ = pos.getZ();

		clone.lastTickPosX = pos.getX();
		clone.lastTickPosY = pos.getY();
		clone.lastTickPosZ = pos.getZ();

		clone.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());

		world.spawnEntityInWorld(clone);
		world.updateEntityWithOptionalForce(clone, false);

		entity.isDead = true;
	}

	public static void facePos(Entity entity, BlockPos pos, float maxYawIncrease, float maxPitchIncrease)
	{
		double x = pos.getX() - entity.posX;
		double y = pos.getY() - entity.posY;
		double z = pos.getZ() - entity.posZ;

		double d3 = (double) MathHelper.sqrt_double(x * x + z * z);
		float f = (float)(MathHelper.atan2(z, x) * (180D / Math.PI)) - 90.0F;
		float f1 = (float)(-(MathHelper.atan2(y, d3) * (180D / Math.PI)));

		entity.rotationPitch = EntityUtil.updateRotation(entity.rotationPitch, f1, maxPitchIncrease);
		entity.rotationYaw = EntityUtil.updateRotation(entity.rotationYaw, f, maxYawIncrease);
	}

	public static float updateRotation(float angle, float targetAngle, float maxIncrease)
	{
		float f = MathHelper.wrapDegrees(targetAngle - angle);

		if (f > maxIncrease)
		{
			f = maxIncrease;
		}

		if (f < -maxIncrease)
		{
			f = -maxIncrease;
		}

		return angle + f;
	}

}

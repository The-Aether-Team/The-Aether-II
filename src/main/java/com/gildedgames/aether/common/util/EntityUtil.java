package com.gildedgames.aether.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
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
	
	public static void spawnParticleLineBetween(Entity e1, Entity e2, double density, EnumParticleTypes type, int... parameters)
	{
		for (int k = 0; k < 1; ++k)
		{
			World world = e1.worldObj;

			double currentX = e2.posX;
			double currentY = (e2.getEntityBoundingBox().maxY - e2.getEntityBoundingBox().minY) / 2 + e2.getEntityBoundingBox().minY;
			double currentZ = e2.posZ;

			double width = e1.getEntityBoundingBox().maxX - e1.getEntityBoundingBox().minX;
			double length1 = e1.getEntityBoundingBox().maxZ - e1.getEntityBoundingBox().minZ;

			double targetX = e1.getEntityBoundingBox().minX + (width / 2);
			double targetY = e1.posY;
			double targetZ = e1.getEntityBoundingBox().minZ + (length1 / 2);

			double difXt = targetX - e2.posX;
			double difY = targetY - e2.posY;
			double difZ = targetZ - e2.posZ;

			double length = Math.sqrt(difXt * difXt + difY * difY + difZ * difZ);

			double difX = difXt / (length * density);
			difY /= length * density;
			difZ /= length * density;

			double veldif = difX / difXt;
			double velcur = 1;

			double randX = difX * world.rand.nextDouble();
			double randZ = difZ * world.rand.nextDouble();

			while (Math.signum(difX) == 1 ? currentX < targetX : currentX > targetX)
			{
				double motionX = e2.motionX * velcur;
				double motionY = e2.motionY * velcur;
				double motionZ = e2.motionZ * velcur;

				e1.worldObj.spawnParticle(type, currentX + randX, currentY, currentZ + randZ, motionX, motionY, motionZ, parameters);

				currentX += difX;
				currentY += difY;
				currentZ += difZ;
				velcur -= veldif;
			}
		}
	}

}

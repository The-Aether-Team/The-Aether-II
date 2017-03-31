package com.gildedgames.aether.common.util.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class EntityUtil
{

	public static void despawnEntityDuringDaytime(Entity entity)
	{
		if (!entity.world.isRemote && entity.world.isDaytime())
		{
			boolean canSee = false;

			Vec3d vec3d = entity.getPositionVector();

			for (EntityPlayer player : entity.world.playerEntities)
			{
				Vec3d look = player.getLook(1.0F);

				Vec3d reverse = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				reverse = new Vec3d(reverse.xCoord, 0.0D, reverse.zCoord);

				if (reverse.dotProduct(look) < 0.0D && player.getDistanceToEntity(entity) < 64.0D)
				{
					canSee = true;
					break;
				}
			}

			if (!canSee)
			{
				entity.setDead();
			}
		}
	}

	public static Entity getEntityFromUUID(World world, UUID uuid)
	{
		for (int i = 0; i < world.loadedEntityList.size(); ++i)
		{
			Entity entity = world.loadedEntityList.get(i);

			if (uuid.equals(entity.getUniqueID()))
			{
				return entity;
			}
		}

		return null;
	}

	public static void facePos(Entity entity, BlockPos pos, float maxYawIncrease, float maxPitchIncrease)
	{
		double x = pos.getX() - entity.posX;
		double y = pos.getY() - entity.posY;
		double z = pos.getZ() - entity.posZ;

		double d3 = (double) MathHelper.sqrt(x * x + z * z);
		float f = (float) (MathHelper.atan2(z, x) * (180D / Math.PI)) - 90.0F;
		float f1 = (float) (-(MathHelper.atan2(y, d3) * (180D / Math.PI)));

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
			World world = e1.world;

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

				if (e1.world.isRemote)
				{
					e1.world.spawnParticle(type, currentX + randX, currentY, currentZ + randZ, motionX, motionY, motionZ, parameters);
				}
				else if (e1.world instanceof WorldServer)
				{
					WorldServer worldServer = (WorldServer) e1.world;
					worldServer.spawnParticle(type,
							currentX + randX, currentY,
							currentZ + randZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.01D : -0.01D), parameters);
				}

				currentX += difX;
				currentY += difY;
				currentZ += difZ;
				velcur -= veldif;
			}
		}
	}

}

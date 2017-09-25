package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Method;
import java.util.UUID;

public class EntityUtil
{

	private static Method COPY_DATA_FROM_OLD;

	static
	{
		COPY_DATA_FROM_OLD = ReflectionAether.getMethod(Entity.class, new Class<?>[] { Entity.class }, "copyDataFromOld", "func_180432_n");
	}

	public static <T extends Entity> T clone(final T entity)
	{
		final T newEnt = (T) EntityList.newEntity(entity.getClass(), entity.getEntityWorld());

		if (newEnt != null)
		{
			ReflectionAether.invokeMethod(COPY_DATA_FROM_OLD, newEnt, entity);
		}

		return newEnt;
	}

	public static void despawnEntityDuringDaytime(final Entity entity)
	{
		if (!entity.world.isRemote && entity.world.isDaytime())
		{
			boolean canSee = false;

			final Vec3d vec3d = entity.getPositionVector();

			for (final EntityPlayer player : entity.world.playerEntities)
			{
				final Vec3d look = player.getLook(1.0F);

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

	public static Entity getEntityFromUUID(final World world, final UUID uuid)
	{
		for (int i = 0; i < world.loadedEntityList.size(); ++i)
		{
			final Entity entity = world.loadedEntityList.get(i);

			if (uuid.equals(entity.getUniqueID()))
			{
				return entity;
			}
		}

		return null;
	}

	public static void facePos(final Entity entity, final BlockPos pos, final float maxYawIncrease, final float maxPitchIncrease)
	{
		final double x = pos.getX() - entity.posX;
		final double y = pos.getY() - entity.posY;
		final double z = pos.getZ() - entity.posZ;

		final double d3 = (double) MathHelper.sqrt(x * x + z * z);
		final float f = (float) (MathHelper.atan2(z, x) * (180D / Math.PI)) - 90.0F;
		final float f1 = (float) (-(MathHelper.atan2(y, d3) * (180D / Math.PI)));

		entity.rotationPitch = EntityUtil.updateRotation(entity.rotationPitch, f1, maxPitchIncrease);
		entity.rotationYaw = EntityUtil.updateRotation(entity.rotationYaw, f, maxYawIncrease);
	}

	public static float updateRotation(final float angle, final float targetAngle, final float maxIncrease)
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

	public static void spawnParticleLineBetween(final Entity e1, final Entity e2, final double density, final EnumParticleTypes type, final int... parameters)
	{
		for (int k = 0; k < 1; ++k)
		{
			final World world = e1.world;

			double currentX = e2.posX;
			double currentY = (e2.getEntityBoundingBox().maxY - e2.getEntityBoundingBox().minY) / 2 + e2.getEntityBoundingBox().minY;
			double currentZ = e2.posZ;

			final double width = e1.getEntityBoundingBox().maxX - e1.getEntityBoundingBox().minX;
			final double length1 = e1.getEntityBoundingBox().maxZ - e1.getEntityBoundingBox().minZ;

			final double targetX = e1.getEntityBoundingBox().minX + (width / 2);
			final double targetY = e1.posY;
			final double targetZ = e1.getEntityBoundingBox().minZ + (length1 / 2);

			final double difXt = targetX - e2.posX;
			double difY = targetY - e2.posY;
			double difZ = targetZ - e2.posZ;

			final double length = Math.sqrt(difXt * difXt + difY * difY + difZ * difZ);

			final double difX = difXt / (length * density);
			difY /= length * density;
			difZ /= length * density;

			final double veldif = difX / difXt;
			double velcur = 1;

			final double randX = difX * world.rand.nextDouble();
			final double randZ = difZ * world.rand.nextDouble();

			while (Math.signum(difX) == 1 ? currentX < targetX : currentX > targetX)
			{
				final double motionX = e2.motionX * velcur;
				final double motionY = e2.motionY * velcur;
				final double motionZ = e2.motionZ * velcur;

				if (e1.world.isRemote)
				{
					e1.world.spawnParticle(type, currentX + randX, currentY, currentZ + randZ, motionX, motionY, motionZ, parameters);
				}
				else if (e1.world instanceof WorldServer)
				{
					final WorldServer worldServer = (WorldServer) e1.world;
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

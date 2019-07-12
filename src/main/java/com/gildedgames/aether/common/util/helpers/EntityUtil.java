package com.gildedgames.aether.common.util.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityUtil
{
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T clone(final T entity)
	{
		final T newEnt = (T) EntityList.newEntity(entity.getClass(), entity.getEntityWorld());

		if (newEnt != null)
		{
			newEnt.copyDataFromOld(entity);
		}

		return newEnt;
	}

	public static void despawnEntityDuringDaytime(final LivingEntity entity)
	{
		if (entity.ticksExisted % 20 == 0)
		{
			final BlockPos blockpos = new BlockPos(entity.posX, entity.getBoundingBox().minY, entity.posZ);

			if (!entity.world.isRemote && entity.world.getLightFor(LightType.SKY, blockpos) - entity.world.getSkylightSubtracted() >= 15 && entity.world
					.canBlockSeeSky(blockpos))
			{
				entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);

				final double x = entity.posX;
				final double y = (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) / 2 + entity.getBoundingBox().minY;
				final double z = entity.posZ;

				final double motionX = 0;
				final double motionY = 0;
				final double motionZ = 0;

				for (int i = 0; i < 15; i++)
				{
					final IParticleData type = entity.getRNG().nextBoolean() ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE;
					final double radius = 0.3;

					final double randX = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;
					final double randZ = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;

					if (entity.world.isRemote)
					{
						entity.world.addParticle(type, x + randX, y, z + randZ, motionX, motionY, motionZ);
					}
					else if (entity.world instanceof ServerWorld)
					{
						final ServerWorld worldServer = (ServerWorld) entity.world;
						worldServer.spawnParticle(type,
								x + randX, y, z + randZ, 1, motionX, motionY, motionZ, (entity.world.rand.nextBoolean() ? 0.01D : -0.01D));
					}
				}
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
		facePos(entity, pos.getX(), pos.getY(), pos.getZ(), maxYawIncrease, maxPitchIncrease);
	}

	public static double getYawFacingPosition(final Entity entity, final double posX, final double posZ)
	{
		final double x = posX - entity.posX;
		final double z = posZ - entity.posZ;

		return (MathHelper.atan2(z, x) * (180D / Math.PI)) - 90.0D;
	}

	public static void facePos(final Entity entity, final double posX, final double posY, final double posZ, final float maxYawIncrease,
			final float maxPitchIncrease)
	{
		final double x = posX - entity.posX;
		final double y = posY - entity.posY;
		final double z = posZ - entity.posZ;

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

	public static void spawnParticleLineBetween(final Entity e1, final Entity e2, final double density, final ParticleTypes type, final int... parameters)
	{
		for (int k = 0; k < 1; ++k)
		{
			final World world = e1.world;

			double currentX = e2.posX;
			double currentY = (e2.getBoundingBox().maxY - e2.getBoundingBox().minY) / 2 + e2.getBoundingBox().minY;
			double currentZ = e2.posZ;

			final double width = e1.getBoundingBox().maxX - e1.getBoundingBox().minX;
			final double length1 = e1.getBoundingBox().maxZ - e1.getBoundingBox().minZ;

			final double targetX = e1.getBoundingBox().minX + (width / 2);
			final double targetY = e1.posY;
			final double targetZ = e1.getBoundingBox().minZ + (length1 / 2);

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
				Vec3d motion = e2.getMotion();
				final double motionX = motion.x * velcur;
				final double motionY = motion.y * velcur;
				final double motionZ = motion.z * velcur;

				if (e1.world.isRemote)
				{
					e1.world.spawnParticle(type, currentX + randX, currentY, currentZ + randZ, motionX, motionY, motionZ, parameters);
				}
				else if (e1.world instanceof ServerWorld)
				{
					final ServerWorld worldServer = (ServerWorld) e1.world;
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

	public static String getSkin(final PlayerEntity player)
	{
		String skinType = DefaultPlayerSkin.getSkinType(player.getUniqueID());

		if (player instanceof AbstractClientPlayerEntity)
		{
			skinType = ((AbstractClientPlayerEntity) player).getSkinType();
		}

		return skinType;
	}

	public static BlockState getBlockBelow(final Entity entity)
	{
		return getBlockBelow(entity.world, entity.getPosition());
	}

	public static BlockState getBlockBelow(final World world, BlockPos pos)
	{
		BlockState state;

		state = world.getBlockState(pos);

		while (state == Blocks.AIR.getDefaultState() && pos.getY() > 0 && pos.getY() < 256)
		{
			pos = pos.down();

			state = world.getBlockState(pos);
		}

		return state;
	}

}

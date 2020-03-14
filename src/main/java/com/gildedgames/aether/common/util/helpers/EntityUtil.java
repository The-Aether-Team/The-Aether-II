package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.client.gui.EffectSystemOverlay;
import com.gildedgames.aether.client.renderer.particles.ParticleAetherStatusEffect;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class EntityUtil
{
	public static boolean checkEntityClass(final Entity entity, final Class<?> clazz)
	{
		// Some entities use this, need to check for cast to get parent
		if (entity instanceof MultiPartEntityPart)
		{
			final MultiPartEntityPart multi = (MultiPartEntityPart) entity;

			if (multi.parent.getClass() != clazz)
			{
				return false;
			}
		}

		return entity.getClass() == clazz;
	}

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

	public static void despawnEntityDuringDaytime(final EntityLivingBase entity)
	{
		if (entity.ticksExisted % 20 == 0)
		{
			final BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().minY, entity.posZ);

			if (!entity.world.isRemote && entity.world.getLightFor(EnumSkyBlock.SKY, blockpos) - entity.world.getSkylightSubtracted() >= 15 && entity.world
					.canBlockSeeSky(blockpos))
			{
				entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);

				final double x = entity.posX;
				final double y = (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2 + entity.getEntityBoundingBox().minY;
				final double z = entity.posZ;

				final double motionX = 0;
				final double motionY = 0;
				final double motionZ = 0;

				for (int i = 0; i < 15; i++)
				{
					final EnumParticleTypes type = entity.getRNG().nextBoolean() ? EnumParticleTypes.SMOKE_LARGE : EnumParticleTypes.SMOKE_NORMAL;
					final double radius = 0.3;

					final double randX = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;
					final double randZ = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;

					if (entity.world.isRemote)
					{
						entity.world.spawnParticle(type, x + randX, y, z + randZ, motionX, motionY, motionZ);
					}
					else if (entity.world instanceof WorldServer)
					{
						final WorldServer worldServer = (WorldServer) entity.world;
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

	public static String getSkin(final EntityPlayer player)
	{
		String skinType = DefaultPlayerSkin.getSkinType(player.getUniqueID());

		if (player instanceof AbstractClientPlayer)
		{
			skinType = ((AbstractClientPlayer) player).getSkinType();
		}

		return skinType;
	}

	public static IBlockState getBlockBelow(final Entity entity)
	{
		return getBlockBelow(entity.world, entity.getPosition());
	}

	public static IBlockState getBlockBelow(final World world, BlockPos pos)
	{
		IBlockState state;

		state = world.getBlockState(pos);

		while (state == Blocks.AIR.getDefaultState() && pos.getY() > 0 && pos.getY() < 256)
		{
			pos = pos.down();

			state = world.getBlockState(pos);
		}

		return state;
	}

	@SideOnly(Side.CLIENT)
	public static void spawnEffectParticles(final EntityLivingBase entity, final IAetherStatusEffects.effectTypes effect)
	{
		IAetherStatusEffectPool statusEffectPool = entity.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool != null)
		{
			if (effect != null)
			{
				float r = 0, g = 0, b = 0;
				r = EffectSystemOverlay.Color.getColorFromEffect(effect).r / 255.F;
				g = EffectSystemOverlay.Color.getColorFromEffect(effect).g / 255.F;
				b = EffectSystemOverlay.Color.getColorFromEffect(effect).b / 255.F;

				final double x = entity.posX;
				final double y = (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2 + entity.getEntityBoundingBox().minY;
				final double z = entity.posZ;

				for (int i = 0; i < 2; i++)
				{
					final double radius = 0.4;

					final double randX = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;
					final double randY = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY);
					final double randZ = entity.getRNG().nextDouble() * (entity.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;

					final double randMotionY = entity.getRNG().nextInt(5);

					//if (entity.world.isRemote)
					//{
					//	spawnEffectParticlesClient(entity.world, x + randX, y + randY, z + randZ,
					//			0, randMotionY == 0 ? 0.1 : randMotionY / 50, 0, r, g, b);
					//}
					//else
					//{
						AetherCore.PROXY.spawnEffectParticles(Minecraft.getMinecraft().world, x + randX, y + randY, z + randZ,
								0, randMotionY == 0 ? 0.1 : randMotionY / 50, 0, r, g, b);
					//}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static void spawnEffectParticlesClient(
			final World world, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ,
			final float particleRed, final float particleGreen, final float particleBlue)
	{
		AetherCore.PROXY.spawnEffectParticles(world, x, y, z, motionX, motionY, motionZ, particleRed, particleGreen, particleBlue);
	}
}

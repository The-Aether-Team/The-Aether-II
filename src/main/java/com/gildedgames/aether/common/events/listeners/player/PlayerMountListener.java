package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.mounts.IFlyingMountData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerMountListener
{

	@SubscribeEvent
	public static void onPlayerInteract(final PlayerInteractEvent.EntityInteractSpecific event)
	{
		Entity target = event.getTarget();

		if (target instanceof IMount)
		{
			final IMount mount = (IMount) target;
			final IMountProcessor processor = mount.getMountProcessor();

			if (processor.canBeMounted(target) && !event.getEntityPlayer().isRiding()
					&& processor.canProcessMountInteraction(event.getTarget(), event.getEntityPlayer()))
			{
				if (target.getPassengers().contains(event.getEntityPlayer()))
				{
					event.getEntityPlayer().dismountEntity(target);

					processor.onDismountedBy(target, event.getEntityPlayer());
				}
				else
				{
					if (!event.getWorld().isRemote)
					{
						event.getEntityPlayer().startRiding(target);
					}

					processor.onMountedBy(target, event.getEntityPlayer());

					AetherCore.PROXY.displayDismountMessage(event.getEntityPlayer());
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingEntityUpdate(final LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof PlayerEntity)
		{
			final PlayerEntity player = (PlayerEntity) event.getEntity();

			final Entity riding = player.getRidingEntity();

			if (riding instanceof IMount && riding instanceof LivingEntity)
			{
				final IMount mount = (IMount) riding;
				final IMountProcessor processor = mount.getMountProcessor();

				final LivingEntity livingMount = (LivingEntity) riding;

				livingMount.renderYawOffset = player.renderYawOffset;
				livingMount.setJumping(false);
				livingMount.moveStrafing = 0.0F;
				livingMount.moveForward = 0.0F;
				livingMount.randomYawVelocity = 0.0F;
				livingMount.stepHeight = processor.getMountedStepHeight(livingMount);

				PlayerMountListener.processSteering(mount, livingMount, player);

				processor.onUpdate(livingMount, player);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(final LivingAttackEvent event)
	{
		if (event.getEntity() instanceof IMount && !event.getEntity().getPassengers().isEmpty())
		{
			final Entity riding = event.getEntity().getPassengers().get(0);

			if (event.getSource().getTrueSource() == riding)
			{
				event.setResult(Event.Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public static void onMountEvent(final EntityMountEvent event)
	{
		final Entity entity = event.getEntityBeingMounted();

		if (event.isDismounting() && entity instanceof IMount && !entity.world.isRemote())
		{
			if (!entity.onGround || (entity instanceof IFlyingMountData && ((IFlyingMountData) entity).isFastFalling()))
			{
				event.setCanceled(true);
			}
		}
	}

	private static void processSteering(final IMount mountImpl, final LivingEntity mount, final PlayerEntity rider)
	{
		final IMountProcessor processor = mountImpl.getMountProcessor();

		if (mount instanceof CreatureEntity)
		{
			((CreatureEntity) mount).getNavigator().clearPath();
		}

		mount.setAIMoveSpeed((float) mount.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());

		mount.rotationYaw = rider.rotationYaw;
		mount.prevRotationYaw = mount.rotationYaw;
		mount.rotationPitch = rider.rotationPitch * 0.5F;

		mount.rotationYaw = mount.rotationYaw % 360.0F;
		mount.rotationPitch = mount.rotationPitch % 360.0F;

		mount.renderYawOffset = mount.rotationYaw;
		mount.rotationYawHead = mount.renderYawOffset;

		float strafe = rider.moveStrafing * 0.5F;
		float forward = rider.moveForward * (mount.onGround ? 0.5F : 1.0F);

		if (forward <= 0.0F)
		{
			forward *= 0.25F;
		}

		final boolean riderIsJumping = rider.isJumping;

		if (riderIsJumping)
		{
			processor.onHoldSpaceBar(mount, rider);

			mount.isAirBorne = true;
		}

		mount.jumpMovementFactor = mount.getAIMoveSpeed() * 0.1F;

		final double oldMotionY = mount.motionY;

		mount.motionY = 0.0F;

		final float oldLimbSwingAmount = mount.limbSwingAmount;
		final float oldPrevLimbSwingAmount = mount.prevLimbSwingAmount;
		final float oldLimbSwing = mount.limbSwing;

		mount.limbSwing = 0.0F;
		mount.limbSwingAmount = 0.0F;
		mount.prevLimbSwingAmount = 0.0F;

		if ((strafe > 0 || strafe < 0) && (forward > 0 || forward < 0))
		{
			strafe *= 0.5F;
			forward *= 0.5F;
		}

		float f6 = 0.91F;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos
				.retain(mount.posX, mount.getBoundingBox().minY - 1.0D, mount.posZ);

		if (mount.onGround)
		{
			BlockState underState = mount.world.getBlockState(blockpos$pooledmutableblockpos);
			f6 = underState.getBlock().getSlipperiness(underState, mount.world, blockpos$pooledmutableblockpos, mount) * 0.91F;
		}

		float f7 = 0.16277136F / (f6 * f6 * f6);
		float friction;

		if (mount.onGround)
		{
			friction = mount.getAIMoveSpeed() * f7;
		}
		else
		{
			friction = mount.jumpMovementFactor;
		}

		mount.moveRelative(strafe, 0.0F, forward, friction);

		mount.limbSwingAmount = oldLimbSwingAmount;
		mount.prevLimbSwingAmount = oldPrevLimbSwingAmount;
		mount.limbSwing = oldLimbSwing;

		mount.motionY = oldMotionY;

		/*mount.prevLimbSwingAmount = mount.limbSwingAmount;
		double d1 = mount.posX - mount.prevPosX;
		double d0 = mount.posZ - mount.prevPosZ;
		float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

		if (f2 > 1.0F)
		{
			f2 = 1.0F;
		}

		mount.limbSwingAmount += (f2 - mount.limbSwingAmount) * 0.4F;
		mount.limbSwing += mount.limbSwingAmount;*/
	}

}

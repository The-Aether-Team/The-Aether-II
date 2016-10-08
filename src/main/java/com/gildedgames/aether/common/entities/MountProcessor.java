package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MountProcessor
{

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.EntityInteractSpecific event)
	{
		Entity target = event.getTarget();

		if (target instanceof IMount)
		{
			IMount mount = (IMount)target;
			IMountProcessor processor = mount.getMountProcessor();

			if (processor.canBeMounted(target) && !event.getEntityPlayer().isRiding() && processor.canProcessMountInteraction(event.getTarget(), event.getEntityPlayer()))
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

					AetherCore.PROXY.displayDismountMessage();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();

			Entity riding = player.getRidingEntity();

			if (riding instanceof IMount && riding instanceof EntityLivingBase)
			{
				IMount mount = (IMount) riding;
				IMountProcessor processor = mount.getMountProcessor();

				EntityLivingBase livingMount = (EntityLivingBase)riding;

				livingMount.renderYawOffset = player.renderYawOffset;
				livingMount.setJumping(false);
				livingMount.moveStrafing = 0.0F;
				livingMount.moveForward = 0.0F;
				livingMount.randomYawVelocity = 0.0F;
				livingMount.stepHeight = processor.getMountedStepHeight(livingMount);

				MountProcessor.processSteering(mount, livingMount, player);

				processor.onUpdate(livingMount, player);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof IMount && !event.getEntity().getPassengers().isEmpty())
		{
			Entity riding = event.getEntity().getPassengers().get(0);

			if (event.getSource().getSourceOfDamage() == riding)
			{
				event.setResult(Event.Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public static void onMountEvent(EntityMountEvent event)
	{
		if (event.isDismounting() && event.getEntityBeingMounted() instanceof IMount)
		{
			if (!event.getEntityBeingMounted().worldObj.isRemote && !event.getEntityBeingMounted().onGround)
			{
				event.setCanceled(true);
			}
		}
	}

	private static void processSteering(IMount mountImpl, EntityLivingBase mount, EntityPlayer rider)
	{
		IMountProcessor processor = mountImpl.getMountProcessor();

		if (mount instanceof EntityCreature)
		{
			((EntityCreature)mount).getNavigator().clearPathEntity();
		}

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

		boolean riderIsJumping = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, rider, ReflectionAether.IS_JUMPING.getMappings());

		if (riderIsJumping)
		{
			processor.onHoldSpaceBar(mount, rider);

			mount.isAirBorne = true;
		}

		mount.jumpMovementFactor = mount.getAIMoveSpeed() * 0.1F;

		mount.setAIMoveSpeed((float)mount.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());

		double oldMotionY = mount.motionY;

		mount.motionY = 0.0F;

		float oldLimbSwingAmount = mount.limbSwingAmount;
		float oldPrevLimbSwingAmount = mount.prevLimbSwingAmount;
		float oldLimbSwing = mount.limbSwing;

		mount.limbSwing = 0.0F;
		mount.limbSwingAmount = 0.0F;
		mount.prevLimbSwingAmount = 0.0F;

		if ((strafe > 0 || strafe < 0) && (forward > 0 || forward < 0))
		{
			strafe *= 0.5F;
			forward *= 0.5F;
		}

		mount.moveEntityWithHeading(strafe, forward);

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

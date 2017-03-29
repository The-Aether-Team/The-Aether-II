package com.gildedgames.aether.common.blocks;

import com.gildedgames.aether.common.ReflectionAether;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class QuicksoilProcessor
{

	private QuicksoilProcessor()
	{

	}

	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		final Entity entity = event.getEntity();

		List<AxisAlignedBB> boxes = entity.world.getCollisionBoxes(entity.getEntityBoundingBox().offset(0.0D, -0.1D, 0.0D));

		boolean onQuicksoil = false;

		for (AxisAlignedBB box : boxes)
		{
			if (box != null)
			{
				BlockPos pos = new BlockPos(MathHelper.floor(box.minX + 0.5D), MathHelper.floor(
						box.minY + 0.5D), MathHelper.floor(box.minZ + 0.5D));

				Block block = entity.world.getBlockState(pos).getBlock();

				if (block == BlocksAether.quicksoil)
				{
					onQuicksoil = true;

					if (entity.isSneaking())
					{
						boolean jumping = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, (EntityLivingBase) entity, ReflectionAether.IS_JUMPING.getMappings());

						if (!jumping)
						{
							entity.onGround = false;
						}

						entity.motionX *= 1.25D;
						entity.motionZ *= 1.25D;

						break;
					}
				}
			}
		}

		if (onQuicksoil)
		{
			entity.motionX *= 1.7D;
			entity.motionZ *= 1.7D;

			double maxMotion = 0.7D;

			if (entity.motionX > maxMotion)
			{
				entity.motionX = maxMotion;
			}

			if (entity.motionX < -maxMotion)
			{
				entity.motionX = -maxMotion;
			}

			if (entity.motionZ > maxMotion)
			{
				entity.motionZ = maxMotion;
			}

			if (entity.motionZ < -maxMotion)
			{
				entity.motionZ = -maxMotion;
			}
		}
	}

}

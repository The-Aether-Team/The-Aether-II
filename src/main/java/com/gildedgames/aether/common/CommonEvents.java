package com.gildedgames.aether.common;

import com.gildedgames.aether.blocks.BlockAetherPortal;
import com.gildedgames.aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEvents
{
	@SubscribeEvent
	public void onPlayerInteractEvent(FillBucketEvent event)
	{
		if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			if (event.world.getBlockState(event.target.getBlockPos()).getBlock() == Blocks.glowstone)
			{
				if (FluidContainerRegistry.isFilledContainer(event.current) && FluidContainerRegistry.getFluidForFilledItem(event.current).fluidID == FluidRegistry.WATER.getID())
				{
					if (this.tryToCreatePortal(event.world, event.target.getBlockPos(), event.target.sideHit))
					{
						if (!event.entityPlayer.capabilities.isCreativeMode)
						{
							event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(event.current));
						}

						event.setCanceled(true);
					}
				}
			}
		}
	}

	private boolean tryToCreatePortal(World world, BlockPos pos, EnumFacing facing)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.glowstone)
		{
			BlockPos portalPos = pos.offset(facing);

			BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, portalPos, EnumFacing.Axis.X);

			if (size.isWithinSizeBounds() && size.get_field_150864_e() == 0)
			{
				size.createPortal();

				return true;
			}
			else
			{
				BlockAetherPortal.Size size1 = new BlockAetherPortal.Size(world, portalPos, EnumFacing.Axis.Z);

				if (size1.isWithinSizeBounds() && size1.get_field_150864_e() == 0)
				{
					size1.createPortal();

					return true;
				}
			}
		}

		return false;
	}

	@SubscribeEvent
	public void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		Block blockBeneath = event.entity.worldObj.getBlockState(event.entity.getPosition().down()).getBlock();

		if (blockBeneath == BlocksAether.quicksoil)
		{
			// This doesn't work all the time, because it's only called when the player is
			// directly on top of Quicksoil. If you go slow enough, during the next player update, it
			// will see the player is on top of an air block instead, and this won't be called.

			if (event.entity.isSneaking())
			{
				event.entity.onGround = false;

				event.entity.motionX *= 1.03D;
				event.entity.motionZ *= 1.03D;
			}
		}
	}
}

package com.gildedgames.aether;

import com.gildedgames.aether.blocks.BlockAetherPortal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
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
}

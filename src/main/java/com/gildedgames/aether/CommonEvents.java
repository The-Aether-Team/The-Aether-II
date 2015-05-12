package com.gildedgames.aether;

import com.gildedgames.aether.blocks.BlockAetherPortal;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
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
					if (BlockAetherPortal.tryToCreatePortal(event.world, event.target.getBlockPos(), event.target.sideHit))
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
}

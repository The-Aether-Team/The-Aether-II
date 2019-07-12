package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CompatibilityAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemBucketEventListener
{
	@SubscribeEvent
	public static void onPlayerUseBucket(final FillBucketEvent event)
	{
		if (CompatibilityAether.isAetherLegacyInstalled())
		{
			return;
		}

		if (event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK)
		{
			FluidStack fluidStack = null;

			if (event.getEmptyBucket().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
			{
				fluidStack = FluidUtil.getFluidContained(event.getEmptyBucket());
			}

			final PlayerEntity player = event.getEntityPlayer();

			final BlockPos pos = event.getTarget().getBlockPos().offset(event.getTarget().sideHit);

			final boolean hasWaterFluid = fluidStack != null && fluidStack.getFluid().getName().equals(FluidRegistry.WATER.getName());

			if (hasWaterFluid || event.getEmptyBucket().getItem() == Items.WATER_BUCKET
					|| event.getEmptyBucket().getItem() == ItemsAether.skyroot_water_bucket)
			{
				BlockState targetState = event.getWorld().getBlockState(event.getTarget().getBlockPos());

				if (targetState.getBlock() == Blocks.GLOWSTONE && isPortalFrame(event.getWorld(), event.getTarget().getBlockPos(), pos))
				{
					event.setCanceled(true);

					player.openGui(AetherCore.MOD_ID, AetherGuiHandler.TELEPORTER_NOTICE_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
				}
				else if (targetState.getBlock() == Blocks.QUARTZ_BLOCK)
				{
					ItemBucketEventListener.onPlayerAttemptPortalCreation(event, player, pos);
				}
			}
		}
	}

	private static boolean isPortalFrame(final World world, final BlockPos target, final BlockPos pos)
	{
		BlockState state = world.getBlockState(target);

		if (state.getBlock() == Blocks.QUARTZ_BLOCK || state.getBlock() == Blocks.GLOWSTONE)
		{
			BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, Direction.Axis.X);

			if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0)
			{
				return true;
			}
			else
			{
				size = new BlockAetherPortal.Size(world, pos, Direction.Axis.Z);

				return size.isWithinSizeBounds() && size.getPortalBlocks() == 0;
			}
		}

		return false;
	}

	private static void onPlayerAttemptPortalCreation(final FillBucketEvent event, final PlayerEntity player, final BlockPos pos)
	{
		if (ItemBucketEventListener.createPortal(event.getWorld(), event.getTarget().getBlockPos(), pos))
		{
			if (!event.getEntityPlayer().isCreative())
			{
				final IFluidHandler fluidHandler = FluidUtil.getFluidHandler(event.getEmptyBucket());

				ItemStack stack = ItemStack.EMPTY;

				if (fluidHandler != null)
				{
					final FluidActionResult result = FluidUtil.tryEmptyContainer(event.getEmptyBucket(), fluidHandler, Integer.MAX_VALUE, player, true);

					stack = result.getResult();
				}

				if (event.getEmptyBucket().getItem() == Items.WATER_BUCKET)
				{
					stack = new ItemStack(Items.BUCKET);
				}

				if (event.getEmptyBucket().getItem() == ItemsAether.skyroot_water_bucket)
				{
					stack = new ItemStack(ItemsAether.skyroot_bucket);
				}

				event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, stack);
			}

			event.setCanceled(true);
		}

	}


	private static boolean createPortal(final World world, final BlockPos target, final BlockPos pos)
	{
		BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, Direction.Axis.X);

		if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0)
		{
			size.createPortal();

			return true;
		}
		else
		{
			size = new BlockAetherPortal.Size(world, pos, Direction.Axis.Z);

			if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0)
			{
				size.createPortal();

				return true;
			}
		}

		return false;
	}

}

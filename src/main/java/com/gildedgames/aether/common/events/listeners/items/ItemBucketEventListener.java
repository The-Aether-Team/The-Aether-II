package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CompatibilityAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPortal;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemBucketEventListener
{
	@SubscribeEvent
	public static void onPlayerUseBucket(final FillBucketEvent event)
	{
		if (event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK)
		{
			FluidStack fluidStack = null;

			if (event.getEmptyBucket().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
			{
				fluidStack = FluidUtil.getFluidContained(event.getEmptyBucket());
			}

			final EntityPlayer player = event.getEntityPlayer();

			final BlockPos pos = event.getTarget().getBlockPos().offset(event.getTarget().sideHit);

			final boolean hasWaterFluid = fluidStack != null && fluidStack.getFluid().getName().equals(FluidRegistry.WATER.getName());

			if (hasWaterFluid || event.getEmptyBucket().getItem() == Items.WATER_BUCKET
					|| event.getEmptyBucket().getItem() == ItemsAether.skyroot_water_bucket)
			{
				IBlockState targetState = event.getWorld().getBlockState(event.getTarget().getBlockPos());

				if (targetState.getBlock() == Blocks.GLOWSTONE)
				{
					if (!CompatibilityAether.isAetherLegacyInstalled() && isPortalFrame(event.getWorld(), event.getTarget().getBlockPos(), pos))
					{
						event.setCanceled(true);

						player.openGui(AetherCore.MOD_ID, AetherGuiHandler.TELEPORTER_NOTICE_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
					}
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
		IBlockState state = world.getBlockState(target);

		if (state.getBlock() == Blocks.QUARTZ_BLOCK || state.getBlock() == Blocks.GLOWSTONE)
		{
			BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.X);

			if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0 && size.isPortalBase(state))
			{
				return true;
			}
			else
			{
				size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.Z);

				return size.isWithinSizeBounds() && size.getPortalBlocks() == 0 && size.isPortalBase(state);
			}
		}

		return false;
	}

	private static void onPlayerAttemptPortalCreation(final FillBucketEvent event, final EntityPlayer player, final BlockPos pos)
	{
		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			return;
		}

		if (ItemBucketEventListener.createPortal(event.getWorld(), event.getTarget().getBlockPos(), pos))
		{
			if (!event.getEntityPlayer().capabilities.isCreativeMode)
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
		BlockAetherPortal.Size size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.X);

		if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0 && size.isPortalBase(world.getBlockState(target)))
		{
			size.createPortal();

			return true;
		}
		else
		{
			size = new BlockAetherPortal.Size(world, pos, EnumFacing.Axis.Z);

			if (size.isWithinSizeBounds() && size.getPortalBlocks() == 0 && size.isPortalBase(world.getBlockState(target)))
			{
				size.createPortal();

				return true;
			}
		}

		return false;
	}

}

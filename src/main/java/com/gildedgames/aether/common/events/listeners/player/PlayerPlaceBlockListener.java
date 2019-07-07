package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.passive.EntityCarrionSprout;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PlayerPlaceBlockListener
{
	@SubscribeEvent
	public static void onBlockPlaced(final BlockEvent.PlaceEvent event)
	{
		final List<EntityLiving> entities = event.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(event.getPos()));

		for (final EntityLiving entity : entities)
		{
			if (entity instanceof EntityAechorPlant || entity instanceof EntityCarrionSprout)
			{
				event.setCanceled(true);

				break;
			}
		}
	}

	@SubscribeEvent
	public static void onPlaceBlockEvent(final BlockEvent.PlaceEvent event)
	{
		final IPlacementFlagCapability data = event.getWorld().getChunk(event.getPos())
				.getCapability(CapabilitiesAether.CHUNK_PLACEMENT_FLAG, EnumFacing.UP);

		if (data != null)
		{
			data.markModified(event.getPos());
		}

		final PlayerAether aePlayer = PlayerAether.getPlayer(event.getPlayer());

		IBlockState replaced = event.getBlockSnapshot().getReplacedBlock(), placed = event.getPlacedBlock();
		Block block = placed.getBlock();

		if (replaced.getBlock() instanceof BlockSnow && replaced.getValue(BlockSnow.LAYERS) == 1 && block instanceof IBlockSnowy)
		{
			event.getWorld().setBlockState(event.getPos(), placed.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE), 2);
		}
		else if (replaced.getBlock() instanceof IBlockSnowy)
		{
			boolean snowy = replaced.getValue(IBlockSnowy.PROPERTY_SNOWY);

			if (block instanceof IBlockSnowy && snowy)
			{
				event.getWorld().setBlockState(event.getPos(), placed.withProperty(IBlockSnowy.PROPERTY_SNOWY, true), 2);
			}
			else if (block instanceof BlockSnow)
			{
				event.getWorld().setBlockState(event.getPos(), replaced.withProperty(IBlockSnowy.PROPERTY_SNOWY, Boolean.TRUE), 2);
			}
		}

		aePlayer.onPlaceBlock(event);
	}
}

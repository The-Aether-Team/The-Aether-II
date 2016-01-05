package com.gildedgames.aether.common.world.chunk;

import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

public class PlacementFlagWorldHandler
{
	public static final PlacementFlagWorldHandler INSTANCE = new PlacementFlagWorldHandler();

	private final HashMap<Integer, HashMap<ChunkCoordIntPair, PlacementFlagChunkData>> chunks = new HashMap<>();

	@SubscribeEvent
	public void onChunkLoad(ChunkDataEvent.Load event)
	{
		PlacementFlagChunkData data;

		if (event.getData().hasKey("aether_placementFlags"))
		{
			data = new PlacementFlagChunkData(event.getData().getByteArray("aether_placementFlags"));
		}
		else
		{
			data = new PlacementFlagChunkData();
		}

		this.addDataToWorld(data, event.getChunk().getChunkCoordIntPair(), event.world);
	}

	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save event)
	{
		PlacementFlagChunkData data = this.getDataFromWorld(event.getChunk().getChunkCoordIntPair(), event.world);

		if (data != null)
		{
			event.getData().setByteArray("aether_placementFlags", data.toBytes());
		}
	}

	@SubscribeEvent
	public void onChunkUnload(ChunkDataEvent.Unload event)
	{
		PlacementFlagChunkData data = this.getDataFromWorld(event.getChunk().getChunkCoordIntPair(), event.world);

		if (data != null)
		{
			this.removeDataFromWorld(event.getChunk().getChunkCoordIntPair(), event.world);
		}
	}

	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.PlaceEvent event)
	{
		if (event.player.capabilities.isCreativeMode)
		{
			this.clearPlaced(event.world, event.pos);
		}
		else
		{
			this.setPlaced(event.world, event.pos);
		}
	}

	public void addDataToWorld(PlacementFlagChunkData data, ChunkCoordIntPair coord, World world)
	{
		HashMap<ChunkCoordIntPair, PlacementFlagChunkData> map = this.chunks.get(world.provider.getDimensionId());

		if (map == null)
		{
			map = new HashMap<>();

			this.chunks.put(world.provider.getDimensionId(), map);
		}

		map.put(coord, data);
	}

	public PlacementFlagChunkData getDataFromWorld(ChunkCoordIntPair coord, World world)
	{
		if (this.chunks.containsKey(world.provider.getDimensionId()))
		{
			return this.chunks.get(world.provider.getDimensionId()).get(coord);
		}

		return null;
	}

	public void removeDataFromWorld(ChunkCoordIntPair coord, World world)
	{
		if (this.chunks.containsKey(world.provider.getDimensionId()))
		{
			this.chunks.get(world.provider.getDimensionId()).remove(coord);
		}
	}

	public boolean wasPlaced(World world, BlockPos pos)
	{
		return this.getChunkDataForPos(world, pos).wasPlacedAt(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
	}

	public void setPlaced(World world, BlockPos pos)
	{
		this.getChunkDataForPos(world, pos).setPlaced(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
	}

	public void clearPlaced(World world, BlockPos pos)
	{
		this.getChunkDataForPos(world, pos).clearPlaced(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
	}

	public PlacementFlagChunkData getChunkDataForPos(World world, BlockPos pos)
	{
		ChunkCoordIntPair coord = world.getChunkFromBlockCoords(pos).getChunkCoordIntPair();

		return this.getDataFromWorld(coord, world);
	}
}

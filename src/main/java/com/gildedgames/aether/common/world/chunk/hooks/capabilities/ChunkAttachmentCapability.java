package com.gildedgames.aether.common.world.chunk.hooks.capabilities;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachmentCapability;
import com.gildedgames.aether.common.world.chunk.hooks.events.AttachCapabilitiesChunkEvent;
import com.gildedgames.aether.api.util.NBT;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.world.ChunkDataEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkAttachmentCapability implements IChunkAttachmentCapability
{
	private Long2ObjectMap<ChunkAttachmentPool> hooks = new Long2ObjectArrayMap<>();

	public static IChunkAttachmentCapability get(World world)
	{
		return world.getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
	}

	public void load(ChunkDataEvent.Load event)
	{
		AttachCapabilitiesChunkEvent event2 = new AttachCapabilitiesChunkEvent(event.getChunk());

		MinecraftForge.EVENT_BUS.post(event2);

		ChunkAttachmentPool pool = new ChunkAttachmentPool(event2.getCapabilities());

		this.setHookPool(event.getChunk().getChunkCoordIntPair(), pool);

		if (pool.getWritableSize() <= 0)
		{
			return;
		}

		NBTTagCompound root = event.getData().getCompoundTag("aether_capabilities");

		for (Map.Entry<ResourceLocation, ICapabilitySerializable<NBTBase>> entry : pool.writers().entrySet())
		{
			NBTTagCompound tag = root.getCompoundTag(entry.getKey().toString());

			if (tag == null)
			{
				continue;
			}

			entry.getValue().deserializeNBT(tag);
		}
	}

	public void save(ChunkDataEvent.Save event)
	{
		ChunkAttachmentPool pool = this.getHookPool(event.getChunk().getChunkCoordIntPair());

		if (pool == null || pool.getWritableSize() <= 0)
		{
			return;
		}

		NBTTagCompound compound = new NBTTagCompound();

		for (Map.Entry<ResourceLocation, ICapabilitySerializable<NBTBase>> entry : pool.writers().entrySet())
		{
			compound.setTag(entry.getKey().toString(), entry.getValue().serializeNBT());
		}

		event.getData().setTag("aether_capabilities", compound);
	}

	public <T extends NBT> T getAttachment(ChunkPos pos, Capability<T> capability)
	{
		ChunkAttachmentPool pool = this.getHookPool(pos);

		if (pool == null)
		{
			return null;
		}

		return pool.getCapability(capability);
	}

	private ChunkAttachmentPool getHookPool(ChunkPos pos)
	{
		return this.hooks.get(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos));
	}

	private void setHookPool(ChunkPos pos, ChunkAttachmentPool pool)
	{
		this.hooks.put(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos), pool);
	}

	private class ChunkAttachmentPool
	{
		private ArrayList<ICapabilityProvider> providers = new ArrayList<>();

		private HashMap<ResourceLocation, ICapabilitySerializable<NBTBase>> writers = new HashMap<>();

		public ChunkAttachmentPool(Map<ResourceLocation, ICapabilityProvider> map)
		{
			for (Map.Entry<ResourceLocation, ICapabilityProvider> entry : map.entrySet())
			{
				this.register(entry.getKey(), entry.getValue());
			}
		}

		public void register(ResourceLocation res, ICapabilityProvider provider)
		{
			this.providers.add(provider);

			if (provider instanceof ICapabilitySerializable)
			{
				this.writers.put(res, (ICapabilitySerializable<NBTBase>) provider);
			}
		}

		public <T> T getCapability(Capability<T> capability)
		{
			for (ICapabilityProvider provider : this.providers)
			{
				if (provider.hasCapability(capability, null))
				{
					return provider.getCapability(capability, null);
				}
			}

			return null;
		}

		public int getWritableSize()
		{
			return this.writers.size();
		}

		public HashMap<ResourceLocation, ICapabilitySerializable<NBTBase>> writers()
		{
			return this.writers;
		}
	}

	public static class Storage implements Capability.IStorage<IChunkAttachmentCapability>
	{

		@Override
		public NBTBase writeNBT(Capability<IChunkAttachmentCapability> capability, IChunkAttachmentCapability instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IChunkAttachmentCapability> capability, IChunkAttachmentCapability instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}

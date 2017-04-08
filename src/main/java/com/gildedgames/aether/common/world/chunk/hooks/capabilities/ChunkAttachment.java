package com.gildedgames.aether.common.world.chunk.hooks.capabilities;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachment;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.world.chunk.hooks.events.AttachCapabilitiesChunkEvent;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkAttachment implements IChunkAttachment
{
	private Long2ObjectMap<ChunkAttachmentPool> hooks = new Long2ObjectArrayMap<>();

	public static IChunkAttachment get(World world)
	{
		return world.getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
	}

	@Override
	public void load(ChunkDataEvent.Load event)
	{
		NBTTagCompound root = event.getData().getCompoundTag("aether_capabilities");

		ChunkAttachmentPool pool = this.createOrGetHookPool(event.getChunk());

		if (pool == null || root == null)
		{
			return;
		}

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

	@Override
	public void save(ChunkDataEvent.Save event)
	{
		ChunkAttachmentPool pool = this.getHookPool(event.getChunk().getPos());

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

	@Override
	public void init(ChunkEvent.Load event)
	{
		this.createOrGetHookPool(event.getChunk());
	}

	@Override
	public void destroy(ChunkEvent.Unload event)
	{
		this.destroyHookPool(event.getChunk().getPos());
	}

	@Override
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

	private ChunkAttachmentPool createOrGetHookPool(Chunk chunk)
	{
		ChunkAttachmentPool pool = this.getHookPool(chunk.getPos());

		if (pool == null)
		{
			AttachCapabilitiesChunkEvent attachEvent = new AttachCapabilitiesChunkEvent(chunk);

			MinecraftForge.EVENT_BUS.post(attachEvent);

			pool = new ChunkAttachmentPool(attachEvent.getCapabilities());

			this.setHookPool(chunk.getPos(), pool);
		}

		return pool;
	}

	private void setHookPool(ChunkPos pos, ChunkAttachmentPool pool)
	{
		this.hooks.put(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos), pool);
	}

	private void destroyHookPool(ChunkPos pos)
	{
		this.hooks.remove(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos));
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

		@SuppressWarnings("unchecked")
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

	public static class Storage implements Capability.IStorage<IChunkAttachment>
	{

		@Override
		public NBTBase writeNBT(Capability<IChunkAttachment> capability, IChunkAttachment instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IChunkAttachment> capability, IChunkAttachment instance, EnumFacing side, NBTBase nbt)
		{

		}
	}
}

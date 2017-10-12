package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IChunkAttachment;
import com.gildedgames.aether.api.util.NBT;
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
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkAttachment implements IChunkAttachment
{
	private final Long2ObjectMap<ChunkAttachmentPool> hooks = new Long2ObjectArrayMap<>();

	private World world;

	private int chunkX, chunkZ;

	public static IChunkAttachment get(final World world)
	{
		return world.getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public int getChunkX()
	{
		return this.chunkX;
	}

	@Override
	public int getChunkZ()
	{
		return this.chunkZ;
	}

	@Override
	public void load(final ChunkDataEvent.Load event)
	{
		this.world = event.getWorld();

		final NBTTagCompound root = event.getData().getCompoundTag("aether_capabilities");

		final ChunkAttachmentPool pool = this.createOrGetHookPool(event.getChunk());

		if (pool == null || root == null)
		{
			return;
		}

		for (final Map.Entry<ResourceLocation, ICapabilitySerializable<NBTBase>> entry : pool.writers().entrySet())
		{
			final NBTTagCompound tag = root.getCompoundTag(entry.getKey().toString());

			if (tag == null)
			{
				continue;
			}

			entry.getValue().deserializeNBT(tag);
		}
	}

	@Override
	public void save(final ChunkDataEvent.Save event)
	{
		final ChunkAttachmentPool pool = this.getHookPool(event.getChunk().getPos());

		if (pool == null || pool.getWritableSize() <= 0)
		{
			return;
		}

		final NBTTagCompound compound = new NBTTagCompound();

		for (final Map.Entry<ResourceLocation, ICapabilitySerializable<NBTBase>> entry : pool.writers().entrySet())
		{
			compound.setTag(entry.getKey().toString(), entry.getValue().serializeNBT());
		}

		event.getData().setTag("aether_capabilities", compound);
	}

	@Override
	public void init(final ChunkEvent.Load event)
	{
		this.chunkX = event.getChunk().xPosition;
		this.chunkZ = event.getChunk().zPosition;

		this.createOrGetHookPool(event.getChunk());
	}

	@Override
	public void destroy(final ChunkEvent.Unload event)
	{
		this.destroyHookPool(event.getChunk().getPos());
	}

	@Override
	public <T extends NBT> T getAttachment(final ChunkPos pos, final Capability<T> capability)
	{
		final ChunkAttachmentPool pool = this.getHookPool(pos);

		if (pool == null)
		{
			return null;
		}

		return pool.getCapability(capability);
	}

	private ChunkAttachmentPool getHookPool(final ChunkPos pos)
	{
		return this.hooks.get(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos));
	}

	private ChunkAttachmentPool createOrGetHookPool(final Chunk chunk)
	{
		ChunkAttachmentPool pool = this.getHookPool(chunk.getPos());

		if (pool == null)
		{
			final AttachCapabilitiesEvent<IChunkAttachment> attachEvent = new AttachCapabilitiesEvent<>(IChunkAttachment.class, this);

			MinecraftForge.EVENT_BUS.post(attachEvent);

			pool = new ChunkAttachmentPool(attachEvent.getCapabilities());

			this.setHookPool(chunk.getPos(), pool);
		}

		return pool;
	}

	private void setHookPool(final ChunkPos pos, final ChunkAttachmentPool pool)
	{
		this.hooks.put(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos), pool);
	}

	private void destroyHookPool(final ChunkPos pos)
	{
		this.hooks.remove(ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos));
	}

	public static class Storage implements Capability.IStorage<IChunkAttachment>
	{

		@Override
		public NBTBase writeNBT(final Capability<IChunkAttachment> capability, final IChunkAttachment instance, final EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(final Capability<IChunkAttachment> capability, final IChunkAttachment instance, final EnumFacing side, final NBTBase nbt)
		{

		}
	}

	private class ChunkAttachmentPool
	{
		private final ArrayList<ICapabilityProvider> providers = new ArrayList<>();

		private final HashMap<ResourceLocation, ICapabilitySerializable<NBTBase>> writers = new HashMap<>();

		public ChunkAttachmentPool(final Map<ResourceLocation, ICapabilityProvider> map)
		{
			for (final Map.Entry<ResourceLocation, ICapabilityProvider> entry : map.entrySet())
			{
				this.register(entry.getKey(), entry.getValue());
			}
		}

		@SuppressWarnings("unchecked")
		public void register(final ResourceLocation res, final ICapabilityProvider provider)
		{
			this.providers.add(provider);

			if (provider instanceof ICapabilitySerializable)
			{
				this.writers.put(res, (ICapabilitySerializable<NBTBase>) provider);
			}
		}

		public <T> T getCapability(final Capability<T> capability)
		{
			for (final ICapabilityProvider provider : this.providers)
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
}

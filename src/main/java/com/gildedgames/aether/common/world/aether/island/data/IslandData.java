package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IVirtualDataManager;
import com.gildedgames.aether.common.world.aether.island.data.virtual.VirtualDataManager;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class IslandData implements IIslandData
{
	private final World world;

	private IIslandBounds bounds;

	private IVirtualDataManager chunkManager;

	private Biome biome;

	private BlockPos respawnPoint;

	private long seed;

	public IslandData(final World world, final IIslandBounds bounds, final Biome biome, final long seed)
	{
		this.world = world;
		this.bounds = bounds;
		this.biome = biome;

		this.seed = seed;

		this.chunkManager = new VirtualDataManager(world, this);
	}

	public IslandData(final World world, final NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	@Override
	public IIslandBounds getBounds()
	{
		return this.bounds;
	}

	@Override
	public Biome getBiome()
	{
		return this.biome;
	}

	@Override
	public BlockPos getRespawnPoint()
	{
		return this.respawnPoint;
	}

	@Override
	public void setRespawnPoint(final BlockPos pos)
	{
		this.respawnPoint = pos;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public IVirtualDataManager getVirtualDataManager()
	{
		return this.chunkManager;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setTag("Bounds", this.bounds.serialize());

		tag.setString("BiomeID", this.biome.getRegistryName().toString());
		tag.setLong("Seed", this.seed);

		tag.setTag("RespawnPoint", NBTHelper.writeBlockPos(this.respawnPoint));
		tag.setTag("VirtualManager", NBTHelper.writeRaw(this.chunkManager));
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.bounds = new IslandBounds(tag.getCompoundTag("Bounds"));

		this.biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("BiomeID")));
		this.seed = tag.getLong("Seed");

		if (tag.hasKey("RespawnPoint"))
		{
			this.respawnPoint = NBTHelper.readBlockPos(tag.getCompoundTag("RespawnPoint"));
		}

		this.chunkManager = new VirtualDataManager(this.world, this);
		this.chunkManager.read(tag.getCompoundTag("VirtualManager"));
	}
}

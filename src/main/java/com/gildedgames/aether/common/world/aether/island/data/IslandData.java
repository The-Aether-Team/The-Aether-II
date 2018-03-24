package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.islands.IVirtualDataManager;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.data.virtual.VirtualDataManager;
import com.gildedgames.orbis.api.util.io.NBTFunnel;
import com.gildedgames.orbis.api.util.mc.NBT;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public class IslandData implements IIslandData
{
	private final World world;

	private IIslandBounds bounds;

	private IVirtualDataManager chunkManager;

	private BiomeAetherBase biome;

	private BlockPos respawnPoint;

	private long seed;

	private List<NBT> components = Lists.newArrayList();

	private IIslandGenerator generator;

	public IslandData(final World world, final IIslandBounds bounds, final BiomeAetherBase biome, final long seed)
	{
		this.world = world;
		this.bounds = bounds;
		this.biome = biome;
		this.generator = biome.getIslandGenerator();

		this.seed = seed;

		this.chunkManager = new VirtualDataManager(world, this);
	}

	public IslandData(final World world, final NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	@Override
	public <T extends NBT> void addComponents(final Collection<T> components)
	{
		this.components.addAll(components);
	}

	@Override
	public Collection<NBT> getComponents()
	{
		return this.components;
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

	@Nonnull
	@Override
	public IIslandGenerator getGenerator()
	{
		return this.generator;
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
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.setTag("Bounds", this.bounds.serialize());

		tag.setString("BiomeID", this.biome.getRegistryName().toString());
		tag.setLong("Seed", this.seed);

		tag.setTag("RespawnPoint", NBTHelper.writeBlockPos(this.respawnPoint));
		tag.setTag("VirtualManager", NBTHelper.writeRaw(this.chunkManager));

		funnel.setList("Components", this.components);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.bounds = new IslandBounds(tag.getCompoundTag("Bounds"));

		this.biome = (BiomeAetherBase) Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("BiomeID")));

		this.generator = this.biome.getIslandGenerator();

		this.seed = tag.getLong("Seed");

		if (tag.hasKey("RespawnPoint"))
		{
			this.respawnPoint = NBTHelper.readBlockPos(tag.getCompoundTag("RespawnPoint"));
		}

		this.chunkManager = new VirtualDataManager(this.world, this);
		this.chunkManager.read(tag.getCompoundTag("VirtualManager"));

		this.components = funnel.getList("Components");
	}
}

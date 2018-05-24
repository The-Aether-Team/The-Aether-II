package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.orbis_api.preparation.IPrepSectorData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class IslandDataPartial implements IIslandDataPartial
{
	protected final IPrepSectorData parent;

	protected final World world;

	protected IIslandBounds bounds;

	protected BiomeAetherBase biome;

	protected final IPrecipitationManager precipitation;

	public IslandDataPartial(final World world, IPrepSectorData parent, final IIslandBounds bounds, final BiomeAetherBase biome)
	{
		this(world, parent);

		this.bounds = bounds;
		this.biome = biome;
	}

	public IslandDataPartial(final World world, IPrepSectorData parent)
	{
		this.world = world;
		this.parent = parent;

		this.precipitation = new IslandPrecipitationManager(this.world, this);
	}

	public IslandDataPartial(World world, IPrepSectorData parent, NBTTagCompound tag)
	{
		this(world, parent);

		this.read(tag);
	}

	@Nonnull
	@Override
	public IIslandBounds getBounds()
	{
		return this.bounds;
	}

	@Nonnull
	@Override
	public Biome getBiome()
	{
		return this.biome;
	}

	@Override
	public IPrecipitationManager getPrecipitation()
	{
		return this.precipitation;
	}

	@Override
	public IPrepSectorData getParentSectorData()
	{
		return this.parent;
	}

	@Override
	public void writePartial(NBTTagCompound tag)
	{
		tag.setTag("Bounds", this.bounds.serialize());
		tag.setString("BiomeID", this.biome.getRegistryName().toString());
		tag.setTag("Precipitation", this.precipitation.serializeNBT());
	}

	@Override
	public void readPartial(NBTTagCompound tag)
	{
		this.bounds = new IslandBounds(tag.getCompoundTag("Bounds"));
		this.biome = (BiomeAetherBase) Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("BiomeID")));
		this.precipitation.deserializeNBT(tag.getCompoundTag("Precipitation"));
	}

	@Override
	public void tick()
	{
		this.precipitation.tick();
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void write(NBTTagCompound tag)
	{
		this.writePartial(tag);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void read(NBTTagCompound tag)
	{
		this.readPartial(tag);
	}
}

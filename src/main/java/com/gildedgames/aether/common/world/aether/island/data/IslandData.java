package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.generation.WorldDecoration;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.orbis_api.core.BlueprintDefinition;
import com.gildedgames.orbis_api.core.ICreationData;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.NBT;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class IslandData implements IIslandData
{
	private final World world;

	private IIslandBounds bounds;

	private BiomeAetherBase biome;

	private BlockPos respawnPoint;

	private long seed;

	private List<NBT> components = Lists.newArrayList();

	private IIslandGenerator generator;

	private List<WorldDecoration> basicDecorations;

	private List<WorldDecoration> treeDecorations;

	private float forestTreeCountModifier, openAreaDecorationGenChance;

	private List<PlacedBlueprint> blueprintInstances = Lists.newArrayList();

	public IslandData(final World world, final IIslandBounds bounds, final BiomeAetherBase biome, final long seed)
	{
		this.world = world;
		this.bounds = bounds;
		this.biome = biome;

		final Random rand = new Random(seed);

		this.initProperties(rand);

		this.seed = seed;
	}

	public IslandData(final World world, final NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	private void initProperties(Random rand)
	{
		this.generator = this.biome.createIslandGenerator(rand, this);
		this.basicDecorations = this.biome.createBasicDecorations(rand);
		this.treeDecorations = this.biome.createTreeDecorations(rand);
		this.forestTreeCountModifier = this.biome.createForestTreeCountModifier(rand);
		this.openAreaDecorationGenChance = this.biome.createOpenAreaDecorationGenChance(rand);
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
	public List<WorldDecoration> getBasicDecorations()
	{
		return this.basicDecorations;
	}

	@Override
	public List<WorldDecoration> getTreeDecorations()
	{
		return this.treeDecorations;
	}

	@Override
	public float getForestTreeCountModifier()
	{
		return this.forestTreeCountModifier;
	}

	@Override
	public float getOpenAreaDecorationGenChance()
	{
		return this.openAreaDecorationGenChance;
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
	public BlockPos getOutpostPos()
	{
		return this.respawnPoint;
	}

	@Override
	public void setOutpostPos(final BlockPos pos)
	{
		this.respawnPoint = pos;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public PlacedBlueprint placeBlueprint(BlueprintDefinition def, ICreationData data)
	{
		final PlacedBlueprint instance = new PlacedBlueprint(data.getWorld(), def, data);

		this.blueprintInstances.add(instance);

		return instance;
	}

	@Override
	public List<PlacedBlueprint> getPlacedBlueprints()
	{
		return this.blueprintInstances;
	}

	@Override
	public void setPlacedBlueprints(List<PlacedBlueprint> instances)
	{
		this.blueprintInstances = instances;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.setTag("Bounds", this.bounds.serialize());

		tag.setString("BiomeID", this.biome.getRegistryName().toString());
		tag.setLong("Seed", this.seed);

		tag.setTag("RespawnPoint", NBTHelper.writeBlockPos(this.respawnPoint));

		final NBTTagList blueprintInstances = new NBTTagList();

		for (final PlacedBlueprint instance : this.blueprintInstances)
		{
			final NBTTagCompound data = new NBTTagCompound();

			instance.write(data);

			blueprintInstances.appendTag(data);
		}

		tag.setTag("blueprintInstances", blueprintInstances);

		funnel.setList("Components", this.components);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.bounds = new IslandBounds(tag.getCompoundTag("Bounds"));

		this.biome = (BiomeAetherBase) Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("BiomeID")));

		this.seed = tag.getLong("Seed");

		if (tag.hasKey("RespawnPoint"))
		{
			this.respawnPoint = NBTHelper.readBlockPos(tag.getCompoundTag("RespawnPoint"));
		}

		final NBTTagList blueprintInstances = tag.getTagList("blueprintInstances", 10);

		if (this.blueprintInstances.isEmpty())
		{
			for (int i = 0; i < blueprintInstances.tagCount(); i++)
			{
				final NBTTagCompound data = blueprintInstances.getCompoundTagAt(i);

				this.blueprintInstances.add(new PlacedBlueprint(this.world, data));
			}
		}

		this.components = funnel.getList("Components");

		final Random rand = new Random(this.seed);

		this.initProperties(rand);
	}
}

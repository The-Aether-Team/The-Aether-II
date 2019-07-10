package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.api.world.decoration.WorldDecoration;
import com.gildedgames.aether.api.world.generation.caves.ICaveSystemGenerator;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import com.gildedgames.aether.common.world.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.decorations.caves.VanillaCaveSystemGenerator;
import com.gildedgames.orbis.lib.core.ICreationData;
import com.gildedgames.orbis.lib.core.PlacedBlueprint;
import com.gildedgames.orbis.lib.core.baking.BakedBlueprint;
import com.gildedgames.orbis.lib.util.ChunkMap;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.NBT;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IslandData implements IIslandData
{
	private final IPrepSectorData parent;

	private BlockPos respawnPoint;

	private long seed;

	private List<NBT> components = Lists.newArrayList();

	private IIslandGenerator generator;

	private List<WorldDecoration> basicDecorations;

	private List<WorldDecoration> treeDecorations;

	private float forestTreeCountModifier, openAreaDecorationGenChance;

	private ChunkMap<List<PlacedBlueprint>> placedBlueprints = new ChunkMap<>();

	private IIslandBounds bounds;

	private BiomeAetherBase biome;

	private ICaveSystemGenerator caveSystemGenerator;

	public IslandData(final IPrepSectorData parent, final IIslandBounds bounds, final BiomeAetherBase biome, final long seed)
	{
		this.parent = parent;

		this.seed = seed;

		this.bounds = bounds;
		this.biome = biome;

		this.initProperties(new Random(seed));
	}

	public IslandData(final IPrepSectorData parent, NBTTagCompound tag)
	{
		this.parent = parent;

		this.read(tag);
	}

	private void initProperties(Random rand)
	{
		this.generator = this.biome.createIslandGenerator(rand, this);
		this.basicDecorations = this.biome.createBasicDecorations(rand);
		this.treeDecorations = this.biome.createTreeDecorations(rand);
		this.forestTreeCountModifier = this.biome.createForestTreeCountModifier(rand);
		this.openAreaDecorationGenChance = this.biome.createOpenAreaDecorationGenChance(rand);
		this.caveSystemGenerator = new VanillaCaveSystemGenerator(this.seed);
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
	public PlacedBlueprint placeBlueprint(BakedBlueprint baked, BlockPos offset)
	{
		ICreationData<?> data = baked.getCreationData().clone();
		data.pos(offset);

		final PlacedBlueprint instance = new PlacedBlueprint(baked, data);

		for (ChunkPos pos : baked.getOccupiedChunks(offset))
		{
			if (!this.placedBlueprints.containsKey(pos.x, pos.z))
			{
				this.placedBlueprints.put(pos.x, pos.z, Lists.newArrayList());
			}

			this.placedBlueprints.get(pos.x, pos.z).add(instance);
		}

		return instance;
	}

	@Override
	public List<PlacedBlueprint> getPlacedBlueprintsInChunk(int chunkX, int chunkZ)
	{
		if (!this.placedBlueprints.containsKey(chunkX, chunkZ))
		{
			return Collections.emptyList();
		}

		return this.placedBlueprints.get(chunkX, chunkZ);
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

	@Nonnull
	@Override
	public ICaveSystemGenerator getCaveSystemGenerator()
	{
		return this.caveSystemGenerator;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public IPrepSectorData getParentSectorData()
	{
		return this.parent;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setTag("Bounds", this.bounds.serialize());
		tag.setString("BiomeID", this.biome.getRegistryName().toString());
		tag.setLong("Seed", this.seed);
		tag.setTag("RespawnPoint", NBTHelper.writeBlockPos(this.respawnPoint));

		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setLongMap("placedBlueprints", this.placedBlueprints.getInnerMap(), NBTFunnel.listSetter());
		funnel.setList("Components", this.components);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.bounds = new IslandBounds(tag.getCompoundTag("Bounds"));
		this.biome = (BiomeAetherBase) Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("BiomeID")));

		final NBTFunnel funnel = new NBTFunnel(tag);

		this.seed = tag.getLong("Seed");

		if (tag.hasKey("RespawnPoint"))
		{
			this.respawnPoint = NBTHelper.readBlockPos(tag.getCompoundTag("RespawnPoint"));
		}

		this.placedBlueprints = ChunkMap.createFrom(funnel.getLongMap("placedBlueprints", NBTFunnel.listGetter()));

		this.components = funnel.getList("Components");

		final Random rand = new Random(this.seed);

		this.initProperties(rand);
	}
}

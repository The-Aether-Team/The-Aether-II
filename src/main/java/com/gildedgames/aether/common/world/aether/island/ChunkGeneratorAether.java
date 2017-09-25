package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.IslandSectorHelper;
import com.gildedgames.aether.api.world.TemplateInstance;
import com.gildedgames.aether.api.world.generation.PostPlacement;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.world.templates.TemplatePrimer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorAether implements IChunkGenerator
{

	private final World world;

	private final Random rand;

	private final WorldPreparationAether preparation;

	public ChunkGeneratorAether(final World world, final long seed)
	{
		this.world = world;

		if (!this.world.isRemote)
		{
			this.world.setSeaLevel(255);
		}

		this.rand = new Random(seed);
		this.preparation = new WorldPreparationAether(this.world, this.rand);
	}

	@Override
	public Chunk provideChunk(final int chunkX, final int chunkZ)
	{
		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

		this.preparation.checkAndPrepareIfAvailable(chunkX, chunkZ);

		// TODO: support multiple islands in same chunk
		final IIslandData island = this.preparation.getIslands(chunkX, chunkZ).stream().findFirst().orElse(null);

		if (island == null)
		{
			throw new IllegalStateException(
					"The Aether's chunk provider is trying to generate chunks before the island has been prepared. Something is VERY WRONG!");
		}

		final ChunkPrimer primer = new ChunkPrimer();

		this.preparation.generateBaseTerrain(primer, island, chunkX, chunkZ);

		// Prime placed templates
		for (final TemplateInstance instance : island.getVirtualDataManager().getPlacedTemplates())
		{
			for (final ChunkPos chunkPos : instance.getChunksOccupied())
			{
				if (chunkPos.chunkXPos == chunkX && chunkPos.chunkZPos == chunkZ)
				{
					TemplatePrimer.primeTemplateSingleChunk(chunkPos, this.world, primer, instance.getDef(), instance.getLoc());
				}
			}
		}

		final Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, this.world, this.rand, chunkX, chunkZ, false));

		final int x = chunkX * 16;
		final int z = chunkZ * 16;

		final BlockPos pos = new BlockPos(x, 0, z);

		final Biome biome = this.world.getBiome(pos.add(16, 0, 16));

		this.rand.setSeed(this.world.getSeed());

		final long seedX = this.rand.nextLong() / 2L * 2L + 1L;
		final long seedZ = this.rand.nextLong() / 2L * 2L + 1L;

		this.rand.setSeed(chunkX * seedX + chunkZ * seedZ ^ this.world.getSeed());

		final ISectorAccess access = IslandSectorHelper.getAccess(this.world);
		final ISector sector = access.provideSector(chunkX, chunkZ);

		// TODO: support multiple islands in same chunk
		final IIslandData island = sector.getIslandsForRegion(pos.getX(), 0, pos.getZ(), 16, 255, 16)
				.stream().findFirst().orElse(null);

		if (island == null)
		{
			return;
		}

		final BlockAccessExtendedWrapper blockAccess = new BlockAccessExtendedWrapper(this.world);

		// Populate placed templates
		for (final TemplateInstance instance : island.getVirtualDataManager().getPlacedTemplates())
		{
			for (final ChunkPos chunkPos : instance.getChunksOccupied())
			{
				if (chunkPos.chunkXPos == chunkX && chunkPos.chunkZPos == chunkZ)
				{
					TemplatePrimer.generateTemplateSingleChunk(chunkPos, this.world, blockAccess, instance.getDef(), instance.getLoc());

					if (!instance.hasGeneratedAChunk())
					{
						instance.markGeneratedAChunk();

						for (final PostPlacement post : instance.getDef().getPostPlacements())
						{
							post.postGenerate(this.world, this.rand, instance.getLoc());
						}
					}
				}
			}
		}

		biome.decorate(this.world, this.rand, pos);
	}

	@Override
	public boolean generateStructures(final Chunk chunkIn, final int x, final int z)
	{
		return false;
	}

	@Override
	public void recreateStructures(final Chunk chunk, final int chunkX, final int chunkZ)
	{

	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final BlockPos pos)
	{
		final Biome biomegenbase = this.world.getBiome(pos);

		if (biomegenbase == null)
		{
			return null;
		}
		else
		{
			return biomegenbase.getSpawnableList(creatureType);
		}
	}

	@Nullable
	@Override
	public BlockPos getStrongholdGen(final World worldIn, final String structureName, final BlockPos position, final boolean p_180513_4_)
	{
		return null;
	}
}

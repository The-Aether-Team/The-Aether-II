package com.gildedgames.aether.common.world.dimensions.aether.island;

import java.util.List;
import java.util.Random;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.WorldGeneratorIsland;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ChunkGeneratorIsland implements IChunkGenerator
{

	private final World worldObj;

	private final Random rand;

	private final WorldGeneratorIsland islandGenerator;

	private final WorldGenAetherCaves caveGenerator;

	private final NoiseGeneratorPerlin surfaceNoise;

	private Biome[] biomes;

	private double[] depthBuffer;

	public ChunkGeneratorIsland(final World world, final long seed)
	{
		this.worldObj = world;

		if (!this.worldObj.isRemote)
		{
			this.worldObj.setSeaLevel(255);
		}

		this.rand = new Random(seed);

		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

		this.islandGenerator = new WorldGeneratorIsland(world);
		this.caveGenerator = new WorldGenAetherCaves();
	}

	@Override
	public BlockPos getStrongholdGen(final World world, final String structureName, final BlockPos pos)
	{
		return null;
	}

	public void replaceBiomeBlocks(ChunkPrimer primer, int chunkX, int chunkZ, Biome[] biomes)
	{
		// Penetration depth noise generation
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer,
				(double) (chunkX * 16), (double) (chunkZ * 16),16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				Biome biome = biomes[x + (z * 16)];

				double val = this.depthBuffer[x + (z * 16)];

				// Calculate max penetration depth
				int depth = (int) (val / 3.0D + 3.0D + rand.nextDouble() * 0.25D);

				int pentration = 0;
				int top = 0;

				// Find top-most block
				for (int y = 256; y > 0; y--)
				{
					if (primer.getBlockState(x, y, z).getBlock() != Blocks.AIR)
					{
						top = y;

						break;
					}
				}

				// Penetrate ground and set biome blocks
				for (int y = top; pentration <= depth & y > 0; y--)
				{
					IBlockState state = primer.getBlockState(x, y, z);

					if (state.getBlock() == BlocksAether.holystone)
					{
						primer.setBlockState(x, y, z, pentration < 1 ? biome.topBlock : biome.fillerBlock);
					}

					pentration++;
				}
			}
		}
	}

	private void generateIslands(final ChunkPrimer primer, final int chunkX, final int chunkZ)
	{
		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final int minX = chunkX * 16;
		final int minY = 0;
		final int minZ = chunkZ * 16;

		final int maxX = minX + 15;
		final int maxY = this.worldObj.getActualHeight();
		final int maxZ = minZ + 15;

		//StructureBoundingBox chunkBB = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

		long sectorSeed = 0;

		if (!IslandSectorAccess.inst().wasSectorEverCreatedInChunk(this.worldObj, chunkX, chunkZ))
		{
			this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

			sectorSeed = this.rand.nextLong();
		}

		final IslandSector sector = IslandSectorAccess.inst().attemptToLoadSectorInChunk(this.worldObj, chunkX, chunkZ, sectorSeed);

		if (sector == null)
		{
			return;
		}

		final List<IslandData> islandsToGenerate = Lists.newArrayList();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final List<IslandData> islands = sector.getIslandDataAtBlockPos(posX + x, posZ + z);

				if (islands.size() <= 0)
				{
					continue;
				}

				for (final IslandData data : islands)
				{
					if (!islandsToGenerate.contains(data))
					{
						islandsToGenerate.add(data);
					}
				}

				/*int islandWidth = (int)island.getBounds().getMinX();
				int islandHeight = (int)island.getBounds().getMinY();
				
				int stepX = posX - islandWidth + x;
				int stepZ = posZ - islandHeight + z;
				
				if (stepX < 0 || stepZ < 0 || stepX > island.getBounds().getWidth() - 1 || stepZ > island.getBounds().getHeight() - 1)
				{
					continue;
				}
				
				if (!islandsToGenerate.contains(island))
				{
				
				}*/
			}
		}

		for (final IslandData island : islandsToGenerate)
		{
			this.islandGenerator.genIslandForChunk(primer, island, sector, chunkX, chunkZ);
		}
	}

	@Override
	public Chunk provideChunk(final int chunkX, final int chunkZ)
	{
		Biome[] biomes = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomes, chunkX * 16, chunkZ * 16, 16, 16);

		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

		final ChunkPrimer primer = new ChunkPrimer();

		this.generateIslands(primer, chunkX, chunkZ);
		this.replaceBiomeBlocks(primer, chunkX, chunkZ, biomes);

		this.caveGenerator.generate(this.worldObj, chunkX, chunkZ, primer);

		final Chunk chunk = new Chunk(this.worldObj, primer, chunkX, chunkZ);
		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, this.worldObj, this.rand, chunkX, chunkZ, false));

		final int x = chunkX * 16;
		final int z = chunkZ * 16;

		final BlockPos pos = new BlockPos(x, 0, z);

		final Biome biome = this.worldObj.getBiome(pos.add(16, 0, 16));

		this.rand.setSeed(this.worldObj.getSeed());

		final long seedX = this.rand.nextLong() / 2L * 2L + 1L;
		final long seedZ = this.rand.nextLong() / 2L * 2L + 1L;

		this.rand.setSeed(chunkX * seedX + chunkZ * seedZ ^ this.worldObj.getSeed());

		biome.decorate(this.worldObj, this.rand, pos);
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
		final Biome biomegenbase = this.worldObj.getBiome(pos);

		if (biomegenbase == null)
		{
			return null;
		}
		else
		{
			return biomegenbase.getSpawnableList(creatureType);
		}
	}
}

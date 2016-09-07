package com.gildedgames.aether.common.world.labyrinth;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceHandler;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;

import java.util.List;
import java.util.Random;

public class ChunkProviderSliderLabyrinth implements IChunkGenerator
{
	
	private final World world;
	
	private final Random random;
	
	public ChunkProviderSliderLabyrinth(World world, long seed)
	{
		this.world = world;
		this.random = new Random(seed);
	}

	@Override
	public BlockPos getStrongholdGen(World world, String structureName, BlockPos pos)
	{
		return null;
	}

	@Override
	public void populate(int chunkX, int chunkZ)
	{
		DungeonInstanceHandler handler = AetherCore.PROXY.getDungeonInstanceHandler();
		
		DungeonInstance inst = handler.getFromDimId(this.world.provider.getDimension());
		
		inst.getGenerator().populateChunk(this.world, this.random, inst, chunkX, chunkZ);
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	public void generateBedrock(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 255; y >= 0; --y)
				{
					if (!(y < 255 - this.random.nextInt(5) && y > this.random.nextInt(5)))
					{
						primer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
					}
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		DungeonInstanceHandler handler = AetherCore.PROXY.getDungeonInstanceHandler();
		
		DungeonInstance inst = handler.getFromDimId(this.world.provider.getDimension());

		if (!inst.getGenerator().isLayoutReady())
		{
			inst.getGenerator().generateLayout(this.world.getMinecraftServer(), inst.getLayoutSeed(), inst, inst.getRoomProvider());
		}
		
		this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

		ChunkPrimer primer = new ChunkPrimer();//newsystem ChunkPrimerDefaultState(BlocksAether.labyrinth_strongblock.getDefaultState());
		
		inst.getGenerator().generateChunk(this.world, this.random, inst, primer, chunkX, chunkZ);

		Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();
		chunk.resetRelightChecks();

		return chunk;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		Biome biome = this.world.getBiome(pos);

		if (biome == null)
		{
			return null;
		}
		else
		{
			return biome.getSpawnableList(creatureType);
		}
	}

	@Override
	public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
	{

	}

}

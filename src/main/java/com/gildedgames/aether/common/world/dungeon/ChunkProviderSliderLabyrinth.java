package com.gildedgames.aether.common.world.dungeon;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import com.gildedgames.aether.common.blocks.BlocksAether;

public class ChunkProviderSliderLabyrinth implements IChunkProvider
{
	
	private final World world;
	
	private final Random random;
	
	public ChunkProviderSliderLabyrinth(World world, long seed)
	{
		this.world = world;
		this.random = new Random(seed);
	}
	
	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ)
	{
		return true;
	}

	@Override
	public BlockPos getStrongholdGen(World world, String structureName, BlockPos pos)
	{
		return null;
	}
	
	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public String makeString()
	{
		return "RandomSliderLabyrinthLevelSource";
	}
	
	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ)
	{
		
	}
	
	public void genHolystoneEverywhere(ChunkPrimer primer, int chunkX, int chunkZ)
	{
		for (int index = 0; index < 65536; index++)
		{
			primer.setBlockState(index, BlocksAether.holystone.getDefaultState());
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 255; y >= 0; --y)
				{
					if (!(y < 255 - this.random.nextInt(5) && y > 0 + this.random.nextInt(5)))
					{
						primer.setBlockState(x, y, z, Blocks.bedrock.getDefaultState());
					}
				}
			}
		}
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

		ChunkPrimer primer = new ChunkPrimer();

		this.genHolystoneEverywhere(primer, chunkX, chunkZ);

		Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public Chunk provideChunk(BlockPos pos)
	{
		return this.provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
	}

	@Override
	public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunk, int chunkX, int chunkZ)
	{
		return false;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate progressUpdate)
	{
		return true;
	}
	
	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public void saveExtraData()
	{

	}

	@Override
	public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		BiomeGenBase biomegenbase = this.world.getBiomeGenForCoords(pos);

		if (biomegenbase == null)
		{
			return null;
		}
		else
		{
			return biomegenbase.getSpawnableList(creatureType);
		}
	}

	@Override
	public void recreateStructures(Chunk chunk, int chunkX, int chunkZ)
	{

	}

}

package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis.api.core.*;
import com.gildedgames.orbis.api.processing.BlockAccessChunkPrimer;
import com.gildedgames.orbis.api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis.api.processing.DataPrimer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkProviderNecromancerTower implements IChunkGenerator
{

	private final World world;

	private final Random random;

	private final PlacedBlueprint tower;

	public ChunkProviderNecromancerTower(final World world, final long seed)
	{
		this.world = world;

		if (!this.world.isRemote)
		{
			this.world.setSeaLevel(255);
		}

		this.random = new Random(seed);

		this.tower = new PlacedBlueprint(world, GenerationAether.NECROMANCER_TOWER, new CreationData(world).pos(BlockPos.ORIGIN));
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{
		final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(this.world));

		for (final BlockDataChunk dataChunk : this.tower.getDataChunks())
		{
			if (dataChunk == null)
			{
				continue;
			}

			if (dataChunk.getPos().x == chunkX && dataChunk.getPos().z == chunkZ)
			{
				final ICreationData data = this.tower.getCreationData();

				primer.create(dataChunk.getContainer(),
						data.clone().pos(new BlockPos(dataChunk.getPos().getXStart(), data.getPos().getY(), dataChunk.getPos().getZStart())));

				if (!this.tower.hasGeneratedAChunk())
				{
					this.tower.markGeneratedAChunk();

					for (final PostPlacement post : this.tower.getDef().getPostPlacements())
					{
						post.postGenerate(this.world, this.random, this.tower.getCreationData(), this.tower.getDef().getData().getBlockDataContainer());
					}
				}
			}
		}
	}

	@Override
	public boolean generateStructures(final Chunk chunkIn, final int x, final int z)
	{
		return false;
	}

	public void generateBedrock(final ChunkPrimer primer, final int chunkX, final int chunkZ)
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
	public Chunk generateChunk(final int chunkX, final int chunkZ)
	{
		this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

		final ChunkPrimer primer = new ChunkPrimer();

		this.generateBedrock(primer, chunkX, chunkZ);

		final DataPrimer dataPrimer = new DataPrimer(new BlockAccessChunkPrimer(this.world, primer));

		for (final BlockDataChunk dataChunk : this.tower.getDataChunks())
		{
			if (dataChunk == null)
			{
				continue;
			}

			if (dataChunk.getPos().x == chunkX && dataChunk.getPos().z == chunkZ)
			{
				final ICreationData data = this.tower.getCreationData();

				dataPrimer.create(dataChunk.getContainer(),
						data.clone().pos(new BlockPos(dataChunk.getPos().getXStart(), data.getPos().getY(), dataChunk.getPos().getZStart())));
			}
		}

		final Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

		chunk.generateSkylightMap();
		//chunk.resetRelightChecks();

		return chunk;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final BlockPos pos)
	{
		final Biome biome = this.world.getBiome(pos);

		if (biome == null)
		{
			return null;
		}
		else
		{
			return biome.getSpawnableList(creatureType);
		}
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(final World worldIn, final String structureName, final BlockPos position, final boolean findUnexplored)
	{
		return null;
	}

	@Override
	public void recreateStructures(final Chunk chunk, final int chunkX, final int chunkZ)
	{

	}

	@Override
	public boolean isInsideStructure(final World worldIn, final String structureName, final BlockPos pos)
	{
		return false;
	}

}

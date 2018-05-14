package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.orbis_api.core.BlockDataChunk;
import com.gildedgames.orbis_api.core.ICreationData;
import com.gildedgames.orbis_api.core.PlacedEntity;
import com.gildedgames.orbis_api.core.PostPlacement;
import com.gildedgames.orbis_api.processing.BlockAccessChunkPrimer;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.DataPrimer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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

	public ChunkProviderNecromancerTower(final World world, final long seed)
	{
		this.world = world;

		if (!this.world.isRemote)
		{
			this.world.setSeaLevel(255);
		}

		this.random = new Random(seed);
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{
		final ChunkPos p = new ChunkPos(chunkX, chunkZ);

		final NecromancerTowerInstance inst = InstancesAether.NECROMANCER_TOWER_HANDLER.getFromDimId(this.world.provider.getDimension());

		final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(this.world));

		if (inst.getTower() == null)
		{
			return;
		}

		for (final BlockDataChunk dataChunk : inst.getTower().getDataChunks())
		{
			if (dataChunk == null)
			{
				continue;
			}

			if (dataChunk.getPos().x == chunkX && dataChunk.getPos().z == chunkZ)
			{
				final ICreationData<?> data = inst.getTower().getCreationData();

				primer.create(dataChunk.getContainer(),
						data.clone().pos(new BlockPos(dataChunk.getPos().getXStart(), data.getPos().getY(), dataChunk.getPos().getZStart())));

				if (!inst.getTower().hasGeneratedAChunk())
				{
					inst.getTower().markGeneratedAChunk();

					for (final PostPlacement post : inst.getTower().getDef().getPostPlacements())
					{
						post.postGenerate(this.world, this.random, inst.getTower().getCreationData(),
								inst.getTower().getDef().getData().getBlockDataContainer());
					}
				}
			}
		}

		if (inst.getTower().getPlacedEntities().containsKey(p))
		{
			for (final PlacedEntity e : inst.getTower().getPlacedEntities().get(p))
			{
				e.spawn(primer);
			}
		}
	}

	@Override
	public boolean generateStructures(final Chunk chunkIn, final int x, final int z)
	{
		return false;
	}

	@Override
	public Chunk generateChunk(final int chunkX, final int chunkZ)
	{
		final NecromancerTowerInstance inst = InstancesAether.NECROMANCER_TOWER_HANDLER.getFromDimId(this.world.provider.getDimension());

		this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

		final ChunkPrimer primer = new ChunkPrimer();

		if (inst.getTower() != null)
		{
			final DataPrimer dataPrimer = new DataPrimer(new BlockAccessChunkPrimer(this.world, primer));

			for (final BlockDataChunk dataChunk : inst.getTower().getDataChunks())
			{
				if (dataChunk == null)
				{
					continue;
				}

				if (dataChunk.getPos().x == chunkX && dataChunk.getPos().z == chunkZ)
				{
					final ICreationData<?> data = inst.getTower().getCreationData();

					dataPrimer.create(dataChunk.getContainer(),
							data.clone().pos(new BlockPos(dataChunk.getPos().getXStart(), data.getPos().getY(), dataChunk.getPos().getZStart())));
				}
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

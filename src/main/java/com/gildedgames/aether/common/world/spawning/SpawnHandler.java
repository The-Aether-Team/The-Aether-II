package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.ChunkMap;
import com.gildedgames.orbis_api.util.mc.NBT;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class SpawnHandler implements NBT
{
	private final String uniqueID;

	private final List<WorldCondition> worldConditions = Lists.newArrayList();

	private final List<PosCondition> posConditions = Lists.newArrayList();

	private final List<SpawnEntry> entries = Lists.newArrayList();

	private final Map<Integer, ChunkMap<SpawnArea>> activeAreas = Maps.newHashMap();

	private int targetEntityCountPerArea, chunkArea = 1, updateFrequencyInTicks = 20;

	public SpawnHandler(final String uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	private static String createAreaID(final int dim, final int areaX, final int areaZ)
	{
		return dim + "_" + areaX + "_" + areaZ;
	}

	public static boolean isNotColliding(final World world, final Entity entity)
	{
		return !world.containsAnyLiquid(entity.getEntityBoundingBox())
				&& world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty()
				&& world.checkNoEntityCollision(entity.getEntityBoundingBox(), entity);
	}

	public String getUniqueID()
	{
		return this.uniqueID;
	}

	public SpawnHandler targetEntityCountPerArea(final int targetEntityCountPerArea)
	{
		this.targetEntityCountPerArea = targetEntityCountPerArea;

		return this;
	}

	public SpawnHandler chunkArea(final int chunkArea)
	{
		this.chunkArea = chunkArea;

		return this;
	}

	public SpawnHandler updateFrequencyInTicks(final int updateFrequencyInTicks)
	{
		this.updateFrequencyInTicks = updateFrequencyInTicks;

		return this;
	}

	public <T extends WorldCondition> SpawnHandler worldCondition(final T condition)
	{
		this.worldConditions.add(condition);

		return this;
	}

	public <T extends PosCondition> SpawnHandler condition(final T condition)
	{
		this.posConditions.add(condition);

		return this;
	}

	public void addEntry(final SpawnEntry entry)
	{
		this.entries.add(entry);
	}

	public void tick(final World world, final List<EntityPlayer> players)
	{
		if (world.getWorldTime() % this.updateFrequencyInTicks != 0)
		{
			return;
		}

		for (final WorldCondition condition : this.worldConditions)
		{
			if (!condition.isMet(world))
			{
				return;
			}
		}

		if (!this.activeAreas.containsKey(world.provider.getDimension()))
		{
			this.activeAreas.put(world.provider.getDimension(), new ChunkMap<>());
		}

		final ChunkMap<SpawnArea> areas = this.activeAreas.get(world.provider.getDimension());

		this.createMissingAreasAndRemoveInactiveAreas(world.provider.getDimension(), areas, players);

		try
		{
			this.checkAndSpawnEntries(world, areas);
		}
		catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isAreaLoaded(final int dim, final int areaX, final int areaZ)
	{
		final ChunkMap<SpawnArea> areas = this.activeAreas.get(dim);

		return areas != null && areas.containsKey(areaX, areaZ);
	}

	public SpawnArea getAreaReadOnly(final int dim, final int areaX, final int areaZ)
	{
		final ChunkMap<SpawnArea> areas = this.activeAreas.get(dim);

		if (areas != null && areas.containsKey(areaX, areaZ))
		{
			return areas.get(areaX, areaZ);
		}
		else
		{
			return this.loadArea(dim, areaX, areaZ);
		}
	}

	public void saveArea(final int dim, final SpawnArea area)
	{
		final String areaID = SpawnHandler.createAreaID(dim, area.getAreaX(), area.getAreaZ());

		final File areaFile = new File(AetherCore.getWorldDirectory(), "//data/spawn_areas/" + this.uniqueID + "/" + areaID + ".dat");
		final NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("areaX", area.getAreaX());
		tag.setInteger("areaZ", area.getAreaZ());
		tag.setInteger("entityCount", area.getEntityCount());

		NBTHelper.writeNBTToFile(tag, areaFile);
	}

	private SpawnArea loadArea(final int dim, final int areaX, final int areaZ)
	{
		final String areaID = SpawnHandler.createAreaID(dim, areaX, areaZ);

		final File areaFile = new File(AetherCore.getWorldDirectory(), "//data/spawn_areas/" + this.uniqueID + "/" + areaID + ".dat");

		if (areaFile.exists())
		{
			final NBTTagCompound tag = NBTHelper.readNBTFromFile(areaFile);

			final SpawnArea area = new SpawnArea(this.chunkArea, tag.getInteger("areaX"), tag.getInteger("areaZ"));

			area.setEntityCount(tag.getInteger("entityCount"));

			return area;
		}
		else
		{
			final SpawnArea area = new SpawnArea(this.chunkArea, areaX, areaZ);

			this.saveArea(dim, area);

			return area;
		}
	}

	private void createMissingAreasAndRemoveInactiveAreas(final int dimension, final ChunkMap<SpawnArea> areas, final List<EntityPlayer> players)
	{
		for (final SpawnArea area : areas.getValues())
		{
			if (area != null)
			{
				area.setInPlayersRenderDistance(false);
			}
		}

		for (final EntityPlayer player : players)
		{
			final int chunkX = MathHelper.floor(player.posX) >> 4;
			final int chunkZ = MathHelper.floor(player.posZ) >> 4;

			final int centerAreaX = chunkX / this.chunkArea;
			final int centerAreaZ = chunkZ / this.chunkArea;

			for (int areaX = centerAreaX - 1; areaX <= centerAreaX + 1; areaX++)
			{
				for (int areaZ = centerAreaZ - 1; areaZ <= centerAreaZ + 1; areaZ++)
				{
					if (!areas.containsKey(areaX, areaZ))
					{
						final SpawnArea area = this.loadArea(dimension, areaX, areaZ);

						area.setInPlayersRenderDistance(true);

						areas.put(areaX, areaZ, area);
					}
					else
					{
						areas.get(areaX, areaZ).setInPlayersRenderDistance(true);
					}
				}
			}
		}

		final List<SpawnArea> areasToRemove = Lists.newArrayList();

		for (final SpawnArea area : areas.getValues())
		{
			if (area != null && !area.hasPlayerInside())
			{
				areasToRemove.add(area);
			}
		}

		for (final SpawnArea area : areasToRemove)
		{
			this.saveArea(dimension, area);

			areas.remove(area.getAreaX(), area.getAreaZ());
		}
	}

	private SpawnEntry getWeightedEntry()
	{
		int maxRoll = 0;
		int roll;

		final List<SpawnEntry> table = Lists.newArrayList();

		for (final SpawnEntry entry : this.entries)
		{
			table.add(entry);

			maxRoll += entry.getRarityWeight();
		}

		if (table.size() == 0)
		{
			return null;
		}

		roll = (int) (Math.random() * maxRoll);

		for (final SpawnEntry entry : table)
		{
			// return element if roll < weight
			if (roll < entry.getRarityWeight())
			{
				return entry;
			}

			// otherwise, subtract weight before moving on
			roll -= entry.getRarityWeight();
		}

		return null;
	}

	private void checkAndSpawnEntries(final World world, final ChunkMap<SpawnArea> areas)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		final int areaInBlocks = this.chunkArea * 16;

		for (final SpawnArea area : areas.getValues())
		{
			while (area.getEntityCount() < this.targetEntityCountPerArea)
			{
				final SpawnEntry entry = this.getWeightedEntry();

				if (entry == null)
				{
					break;
				}

				final int dif = entry.getMaxGroupSize() - entry.getMinGroupSize();

				final int randSize = dif > 0 ? world.rand.nextInt(dif) : 0;

				final int groupSize = entry.getMinGroupSize() + randSize;

				if (area.getEntityCount() + groupSize > this.targetEntityCountPerArea)
				{
					break;
				}

				final int minX = (area.getMinChunkPos().x) * 16;
				final int minZ = (area.getMinChunkPos().z) * 16;

				final int groupPosX = minX + world.rand.nextInt(areaInBlocks);
				final int groupPosZ = minZ + world.rand.nextInt(areaInBlocks);

				int attempts = 0;

				final int MAX_ATTEMPTS = 100;

				inner:
				for (int count = 0; count < groupSize; count++)
				{
					final int scatterX =
							(world.rand.nextBoolean() ? 1 : -1) * (1 + world.rand.nextInt(entry.getPositionSelector().getScatter(world)));
					final int scatterZ =
							(world.rand.nextBoolean() ? 1 : -1) * (1 + world.rand.nextInt(entry.getPositionSelector().getScatter(world)));

					final float posX = groupPosX + scatterX;
					final float posZ = groupPosZ + scatterZ;

					final int posY = entry.getPositionSelector().getPosY(world, MathHelper.floor(posX), MathHelper.floor(posZ));

					final BlockPos spawnAt = new BlockPos(posX, posY, posZ);

					if (!world.isBlockLoaded(spawnAt, true))
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						continue;
					}

					if (world.isAnyPlayerWithinRangeAt(posX, posY, posZ, 24.0D))
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						continue;
					}

					for (final PosCondition condition : this.posConditions)
					{
						if (!condition.isMet(world, spawnAt, spawnAt.down()))
						{
							if (attempts < MAX_ATTEMPTS)
							{
								attempts++;
								count--;
							}

							continue inner;
						}
					}

					for (final PosCondition condition : entry.getConditions())
					{
						if (!condition.isMet(world, spawnAt, spawnAt.down()))
						{
							if (attempts < MAX_ATTEMPTS)
							{
								attempts++;
								count--;
							}

							continue inner;
						}
					}

					final Constructor<?> cons = entry.getEntityClass().getConstructor(World.class);
					final Entity entity = (Entity) cons.newInstance(world);

					entity.setLocationAndAngles(posX + 0.5F, posY, posZ + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);

					world.spawnEntity(entity);

					if (world instanceof WorldServer)
					{
						final WorldServer worldServer = (WorldServer) world;

						worldServer.updateEntityWithOptionalForce(entity, true);
					}

					if (SpawnHandler.isNotColliding(world, entity))
					{
						if (entity instanceof EntityLiving)
						{
							final EntityLiving living = (EntityLiving) entity;

							if (!ForgeEventFactory.doSpecialSpawn(living, world, posX, posY, posZ))
							{
								living.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(living)), null);
							}
						}

						final ISpawningInfo spawningInfo = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

						spawningInfo.setSpawnArea(new EntitySpawn(this.uniqueID, world.provider.getDimension(), area.getAreaX(), area.getAreaZ()));

						area.addToEntityCount(1);
					}
					else
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						entity.setDead();
					}
				}

				if (attempts >= MAX_ATTEMPTS)
				{
					break;
				}
			}
		}
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		output.setInteger("dimensionCount", this.activeAreas.size());

		int index = 0;

		for (final Map.Entry<Integer, ChunkMap<SpawnArea>> set : this.activeAreas.entrySet())
		{
			final int dimension = set.getKey();
			final ChunkMap<SpawnArea> chunkMap = set.getValue();

			output.setInteger("dimension" + index, dimension);
			output.setInteger("areaCount" + index, chunkMap.size());

			int areaIndex = 0;

			for (final SpawnArea area : chunkMap.getValues())
			{
				output.setInteger("map" + index + "_areaX" + areaIndex, area.getAreaX());
				output.setInteger("map" + index + "_areaZ" + areaIndex, area.getAreaZ());

				output.setInteger("map" + index + "_entityCount" + areaIndex, area.getEntityCount());

				areaIndex++;
			}

			index++;
		}
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		this.activeAreas.clear();

		final int dimensionCount = input.getInteger("dimensionCount");

		for (int index = 0; index < dimensionCount; index++)
		{
			final int dimension = input.getInteger("dimension" + index);
			final int areaCount = input.getInteger("areaCount" + index);

			final ChunkMap<SpawnArea> chunkMap = new ChunkMap<>();

			for (int areaIndex = 0; areaIndex < areaCount; areaIndex++)
			{
				final int areaX = input.getInteger("map" + index + "_areaX" + areaIndex);
				final int areaZ = input.getInteger("map" + index + "_areaZ" + areaIndex);

				final int entityCount = input.getInteger("map" + index + "_entityCount" + areaIndex);

				final SpawnArea area = new SpawnArea(this.chunkArea, areaX, areaZ);

				area.setEntityCount(entityCount);

				chunkMap.put(areaX, areaZ, area);
			}

			this.activeAreas.put(dimension, chunkMap);
		}
	}
}

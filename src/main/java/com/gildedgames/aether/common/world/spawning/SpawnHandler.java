package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.entity.spawning.EntitySpawn;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.util.GGHelper;
import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.core.util.ChunkMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class SpawnHandler implements NBT
{

	private final Map<Integer, TickTimer> tickTimers = Maps.newHashMap();

	private final String uniqueID;

	private int targetEntityCountPerArea, chunkArea = 1, updateFrequencyInTicks = 20;

	private final List<WorldCondition> worldConditions = Lists.newArrayList();

	private final List<PosCondition> posConditions = Lists.newArrayList();

	private final List<SpawnEntry> entries = Lists.newArrayList();

	private final Map<Integer, ChunkMap<SpawnArea>> activeAreas = Maps.newHashMap();

	public SpawnHandler(String uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	public String getUniqueID()
	{
		return this.uniqueID;
	}

	public SpawnHandler targetEntityCountPerArea(int targetEntityCountPerArea)
	{
		this.targetEntityCountPerArea = targetEntityCountPerArea;

		return this;
	}

	public SpawnHandler chunkArea(int chunkArea)
	{
		this.chunkArea = chunkArea;

		return this;
	}

	public SpawnHandler updateFrequencyInTicks(int updateFrequencyInTicks)
	{
		this.updateFrequencyInTicks = updateFrequencyInTicks;

		return this;
	}

	public <T extends WorldCondition> SpawnHandler worldCondition(T condition)
	{
		this.worldConditions.add(condition);

		return this;
	}

	public <T extends PosCondition> SpawnHandler condition(T condition)
	{
		this.posConditions.add(condition);

		return this;
	}

	public void addEntry(SpawnEntry entry)
	{
		this.entries.add(entry);
	}

	public void tick(World world, List<EntityPlayer> players)
	{
		if (!this.tickTimers.containsKey(world.provider.getDimension()))
		{
			this.tickTimers.put(world.provider.getDimension(), new TickTimer());
		}

		TickTimer timer = this.tickTimers.get(world.provider.getDimension());

		timer.tick();

		if (!timer.isMultipleOfTicks(this.updateFrequencyInTicks))
		{
			return;
		}

		for (WorldCondition condition : this.worldConditions)
		{
			if (!condition.isMet(world))
			{
				return;
			}
		}

		if (!this.activeAreas.containsKey(world.provider.getDimension()))
		{
			this.activeAreas.put(world.provider.getDimension(), new ChunkMap<SpawnArea>());
		}

		ChunkMap<SpawnArea> areas = this.activeAreas.get(world.provider.getDimension());

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

	public boolean isAreaLoaded(int dim, int areaX, int areaZ)
	{
		ChunkMap<SpawnArea> areas = this.activeAreas.get(dim);

		return areas.containsKey(areaX, areaZ);
	}

	public SpawnArea getAreaReadOnly(int dim, int areaX, int areaZ)
	{
		ChunkMap<SpawnArea> areas = this.activeAreas.get(dim);

		if (areas.containsKey(areaX, areaZ))
		{
			return areas.get(areaX, areaZ);
		}
		else
		{
			return this.loadArea(dim, areaX, areaZ);
		}
	}

	private static String createAreaID(int dim, int areaX, int areaZ)
	{
		return dim + "_" + areaX + "_" + areaZ;
	}

	public void saveArea(int dim, SpawnArea area)
	{
		String areaID = SpawnHandler.createAreaID(dim, area.getAreaX(), area.getAreaZ());

		File areaFile = new File(UtilModule.getWorldDirectory(), "//data/spawn_areas/" + this.uniqueID + "/" + areaID + ".dat");
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("areaX", area.getAreaX());
		tag.setInteger("areaZ", area.getAreaZ());
		tag.setInteger("entityCount", area.getEntityCount());

		GGHelper.writeNBTToFile(tag, areaFile);
	}

	private SpawnArea loadArea(int dim, int areaX, int areaZ)
	{
		String areaID = SpawnHandler.createAreaID(dim, areaX, areaZ);

		File areaFile = new File(UtilModule.getWorldDirectory(), "//data/spawn_areas/" + this.uniqueID + "/" + areaID + ".dat");

		if (areaFile.exists())
		{
			NBTTagCompound tag = GGHelper.readNBTFromFile(areaFile);

			SpawnArea area = new SpawnArea(this.chunkArea, tag.getInteger("areaX"), tag.getInteger("areaZ"));

			area.setEntityCount(tag.getInteger("entityCount"));

			return area;
		}
		else
		{
			SpawnArea area = new SpawnArea(this.chunkArea, areaX, areaZ);

			this.saveArea(dim, area);

			return area;
		}
	}

	private void createMissingAreasAndRemoveInactiveAreas(int dimension, ChunkMap<SpawnArea> areas, List<EntityPlayer> players)
	{
		for (SpawnArea area : areas.getValues())
		{
			if (area != null)
			{
				area.setInPlayersRenderDistance(false);
			}
		}

		for (EntityPlayer player : players)
		{
			final int chunkX = MathHelper.floor_double(player.posX) >> 4;
			final int chunkZ = MathHelper.floor_double(player.posZ) >> 4;

			int centerAreaX = chunkX / this.chunkArea;
			int centerAreaZ = chunkZ / this.chunkArea;

			for (int areaX = centerAreaX - 1; areaX <= centerAreaX + 1; areaX++)
			{
				for (int areaZ = centerAreaZ - 1; areaZ <= centerAreaZ + 1; areaZ++)
				{
					if (!areas.containsKey(areaX, areaZ))
					{
						SpawnArea area = this.loadArea(dimension, areaX, areaZ);

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

		List<SpawnArea> areasToRemove = Lists.newArrayList();

		for (SpawnArea area : areas.getValues())
		{
			if (area != null && !area.hasPlayerInside())
			{
				areasToRemove.add(area);
			}
		}

		for (SpawnArea area : areasToRemove)
		{
			this.saveArea(dimension, area);

			areas.remove(area.getAreaX(), area.getAreaZ());
		}
	}

	private void checkAndSpawnEntries(World world, ChunkMap<SpawnArea> areas) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		int areaInBlocks = this.chunkArea * 16;

		for (SpawnArea area : areas.getValues())
		{
			outer: for (final SpawnEntry entry : this.entries)
			{
				if (area.getEntityCount() >= this.targetEntityCountPerArea)
				{
					break;
				}

				int groupSize = entry.getMinGroupSize() + world.rand.nextInt(entry.getMaxGroupSize() - entry.getMinGroupSize());

				if (area.getEntityCount() + groupSize > this.targetEntityCountPerArea)
				{
					break;
				}

				int minX = (area.getMinChunkPos().chunkXPos) * 16;
				int minZ = (area.getMinChunkPos().chunkZPos) * 16;

				int groupPosX = minX + world.rand.nextInt(areaInBlocks);
				int groupPosZ = minZ + world.rand.nextInt(areaInBlocks);

				int attempts = 0;

				final int MAX_ATTEMPTS = 100;

				inner: for (int count = 0; count < groupSize; count++)
				{
					int scatterX = (world.rand.nextBoolean() ? 1 : -1) * world.rand.nextInt(entry.getScatterSize());
					int scatterZ = (world.rand.nextBoolean() ? 1 : -1) * world.rand.nextInt(entry.getScatterSize());

					float posX = groupPosX + scatterX;
					float posZ = groupPosZ + scatterZ;

					int posY = entry.getHeightSelector().getPosY(world, MathHelper.floor_float(posX), MathHelper.floor_float(posZ));

					BlockPos spawnAt = new BlockPos(posX, posY, posZ);

					if (world.isAnyPlayerWithinRangeAt(posX, posY, posZ, 24.0D))
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						continue;
					}

					for (PosCondition condition : entry.getConditions())
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

					Constructor<?> cons = entry.getEntityClass().getConstructor(World.class);
					final Entity entity = (Entity) cons.newInstance(world);

					entity.setLocationAndAngles(posX + 0.5F, posY, posZ + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);

					if (SpawnHandler.isNotColliding(world, entity))
					{
						if (entity instanceof EntityLiving)
						{
							EntityLiving living = (EntityLiving)entity;

							if (!ForgeEventFactory.doSpecialSpawn(living, world, posX, posY, posZ))
							{
								living.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(living)), null);
							}
						}

						world.spawnEntityInWorld(entity);

						ISpawningInfo spawningInfo = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

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
			}
		}
	}

	private static boolean isNotColliding(World world, Entity entity)
	{
		return !world.containsAnyLiquid(entity.getEntityBoundingBox()) && world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && world.checkNoEntityCollision(entity.getEntityBoundingBox(), entity);
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("dimensionCount", this.activeAreas.size());

		int index = 0;

		for (Map.Entry<Integer, ChunkMap<SpawnArea>> set : this.activeAreas.entrySet())
		{
			int dimension = set.getKey();
			ChunkMap<SpawnArea> chunkMap = set.getValue();

			output.setInteger("dimension" + index, dimension);
			output.setInteger("areaCount" + index, chunkMap.size());

			int areaIndex = 0;

			for (SpawnArea area : chunkMap.getValues())
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
	public void read(NBTTagCompound input)
	{
		this.activeAreas.clear();

		int dimensionCount = input.getInteger("dimensionCount");

		for (int index = 0; index < dimensionCount; index++)
		{
			int dimension = input.getInteger("dimension" + index);
			int areaCount = input.getInteger("areaCount" + index);

			ChunkMap<SpawnArea> chunkMap = new ChunkMap<>();

			for (int areaIndex = 0; areaIndex < areaCount; areaIndex++)
			{
				int areaX = input.getInteger("map" + index + "_areaX" + areaIndex);
				int areaZ = input.getInteger("map" + index + "_areaZ" + areaIndex);

				int entityCount = input.getInteger("map" + index + "_entityCount" + areaIndex);

				SpawnArea area = new SpawnArea(this.chunkArea, areaX, areaZ);

				area.setEntityCount(entityCount);

				chunkMap.put(areaX, areaZ, area);
			}

			this.activeAreas.put(dimension, chunkMap);
		}
	}
}

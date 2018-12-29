package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.world.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_api.OrbisAPI;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.lang.reflect.Constructor;
import java.util.List;

public class SpawnHandler implements ISpawnHandler
{
	private final String uniqueID;

	private final List<WorldCondition> worldConditions = Lists.newArrayList();

	private final List<PosCondition> posConditions = Lists.newArrayList();

	private final List<ISpawnEntry> entries = Lists.newArrayList();

	private ISpawnAreaManager access;

	private int targetEntityCountPerArea, chunkArea = 1, updateFrequencyInTicks = 20;

	public SpawnHandler(String uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	public static boolean isNotColliding(EntityLiving.SpawnPlacementType placementType, final World world, final Entity entity)
	{
		return (placementType == EntityLiving.SpawnPlacementType.IN_AIR || WorldEntitySpawner
				.canCreatureTypeSpawnAtLocation(placementType, world, entity.getPosition()))
				&& world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty()
				&& world.checkNoEntityCollision(entity.getEntityBoundingBox(), entity);
	}

	@Override
	public void init(World world)
	{
		this.access = new SpawnAreaManager(world, this);

		OrbisAPI.services().getWorldDataManager(world).register(this.access);
	}

	@Override
	public String getUniqueID()
	{
		return this.uniqueID;
	}

	@Override
	public SpawnHandler targetEntityCountPerArea(final int targetEntityCountPerArea)
	{
		this.targetEntityCountPerArea = targetEntityCountPerArea;

		return this;
	}

	@Override
	public SpawnHandler chunkArea(final int chunkArea)
	{
		this.chunkArea = chunkArea;

		return this;
	}

	@Override
	public SpawnHandler updateFrequencyInTicks(final int updateFrequencyInTicks)
	{
		this.updateFrequencyInTicks = updateFrequencyInTicks;

		return this;
	}

	@Override
	public <T extends WorldCondition> SpawnHandler addWorldCondition(final T condition)
	{
		this.worldConditions.add(condition);

		return this;
	}

	@Override
	public <T extends PosCondition> SpawnHandler condition(final T condition)
	{
		this.posConditions.add(condition);

		return this;
	}

	@Override
	public void addEntry(final ISpawnEntry entry)
	{
		this.entries.add(entry);
	}

	@Override
	public void tick()
	{
		this.access.tick();

		World world = this.access.getWorld();

		if (this.updateFrequencyInTicks != 0 && world.getWorldTime() % this.updateFrequencyInTicks != 0)
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

		try
		{
			this.checkAndSpawnEntries(this.access);
		}
		catch (Exception e)
		{
			AetherCore.LOGGER.warn("Failed to check and spawn entries", e);
		}
	}

	@Override
	public int getChunkArea()
	{
		return this.chunkArea;
	}

	@Override
	public void onLivingDeath(LivingDeathEvent event)
	{
		this.access.onLivingDeath(event);
	}

	private ISpawnEntry getWeightedEntry()
	{
		int maxRoll = 0;
		int roll;

		final List<ISpawnEntry> table = Lists.newArrayList();

		for (final ISpawnEntry entry : this.entries)
		{
			table.add(entry);

			maxRoll += entry.getRarityWeight();
		}

		if (table.size() == 0)
		{
			return null;
		}

		roll = (int) (Math.random() * maxRoll);

		for (final ISpawnEntry entry : table)
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

	private void checkAndSpawnEntries(ISpawnAreaManager manager) throws ReflectiveOperationException
	{
		IEntityLivingData livingData = null;

		final int areaInBlocks = this.chunkArea * 16;

		for (ISpawnArea area : manager.getLoaded())
		{
			while (area.getEntityCount() < this.targetEntityCountPerArea)
			{
				ISpawnEntry entry = this.getWeightedEntry();

				if (entry == null)
				{
					break;
				}

				int dif = entry.getMaxGroupSize() - entry.getMinGroupSize();

				int randSize = dif > 0 ? manager.getWorld().rand.nextInt(dif) : 0;

				int groupSize = entry.getMinGroupSize() + randSize;

				if (area.getEntityCount() + groupSize > this.targetEntityCountPerArea)
				{
					break;
				}

				int minX = (area.getMinChunkPos().x) * 16;
				int minZ = (area.getMinChunkPos().z) * 16;

				int groupPosX = minX + manager.getWorld().rand.nextInt(areaInBlocks);
				int groupPosZ = minZ + manager.getWorld().rand.nextInt(areaInBlocks);

				int attempts = 0;

				int MAX_ATTEMPTS = 100;

				inner:

				for (int count = 0; count < groupSize; count++)
				{
					int scatterX =
							(manager.getWorld().rand.nextBoolean() ? 1 : -1) * (1 + manager.getWorld().rand
									.nextInt(entry.getPositionSelector().getScatter(manager.getWorld())));
					int scatterZ =
							(manager.getWorld().rand.nextBoolean() ? 1 : -1) * (1 + manager.getWorld().rand
									.nextInt(entry.getPositionSelector().getScatter(manager.getWorld())));

					int posX = MathHelper.floor( groupPosX + scatterX);
					int posZ = MathHelper.floor( groupPosZ + scatterZ);

					if (!manager.getWorld().isBlockLoaded(new BlockPos(posX, 0, posZ)))
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						continue;
					}

					int posY = entry.getPositionSelector().getPosY(manager.getWorld(), posX, posZ);

					BlockPos spawnAt = new BlockPos(posX, posY, posZ);

					if (manager.getWorld().isAnyPlayerWithinRangeAt(posX, posY, posZ, 24.0D))
					{
						if (attempts < MAX_ATTEMPTS)
						{
							attempts++;
							count--;
						}

						continue;
					}

					for (PosCondition condition : this.posConditions)
					{
						if (!condition.isMet(manager.getWorld(), spawnAt, spawnAt.down()))
						{
							if (attempts < MAX_ATTEMPTS)
							{
								attempts++;
								count--;
							}

							continue inner;
						}
					}

					for (PosCondition condition : entry.getConditions())
					{
						if (!condition.isMet(manager.getWorld(), spawnAt, spawnAt.down()))
						{
							if (attempts < MAX_ATTEMPTS)
							{
								attempts++;
								count--;
							}

							continue inner;
						}
					}

					Constructor<?> constructor = entry.getEntityClass().getConstructor(World.class);

					Entity entity = (Entity) constructor.newInstance(manager.getWorld());
					entity.setLocationAndAngles(posX + 0.5F, posY, posZ + 0.5F, manager.getWorld().rand.nextFloat() * 360.0F, 0.0F);

					if (entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityliving = (EntityLivingBase) entity;

						entityliving.rotationYawHead = entityliving.rotationYaw;
						entityliving.renderYawOffset = entityliving.rotationYaw;
					}

					if (SpawnHandler.isNotColliding(entry.getPlacementType(), manager.getWorld(), entity))
					{
						if (entity instanceof EntityLiving)
						{
							EntityLiving living = (EntityLiving) entity;

							if (!ForgeEventFactory.doSpecialSpawn(living, manager.getWorld(), posX, posY, posZ, null))
							{
								living.onInitialSpawn(manager.getWorld().getDifficultyForLocation(new BlockPos(living)), null);
							}
						}

						ISpawningInfo info = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);
						info.setSpawnArea(new EntitySpawn(this.uniqueID, manager.getWorld().provider.getDimension(), area.getAreaX(), area.getAreaZ()));

						area.addToEntityCount(1);

						manager.getWorld().spawnEntity(entity);

						if (manager.getWorld() instanceof WorldServer)
						{
							manager.getWorld().updateEntityWithOptionalForce(entity, true);
						}

						if (entity instanceof EntityLiving)
						{
							livingData = ((EntityLiving) entity).onInitialSpawn(manager.getWorld().getDifficultyForLocation(new BlockPos(entity)), livingData);
						}
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
}

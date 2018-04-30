package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mobs.*;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.world.spawning.PosCondition;
import com.gildedgames.aether.common.world.spawning.SpawnArea;
import com.gildedgames.aether.common.world.spawning.SpawnEntry;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBlockStateUnderneath;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBlockUnderneath;
import com.gildedgames.aether.common.world.spawning.conditions.CheckDimension;
import com.gildedgames.aether.common.world.spawning.conditions.CheckTime;
import com.gildedgames.aether.common.world.spawning.util.FlyingPositionSelector;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.List;

public class SpawnRegistry
{

	private final List<SpawnHandler> spawnHandlers = Lists.newArrayList();

	public SpawnRegistry()
	{

	}

	public void registerAetherSpawnHandlers()
	{
		/** PASSIVE **/
		final SpawnHandler animals = new SpawnHandler("aether_animals").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		animals.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		final PosCondition grassCheck = new CheckBlockUnderneath(BlocksAether.aether_grass);

		final SpawnEntry burrukai = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityBurrukai.class, 10F, 2, 3).conditiion(grassCheck);
		final SpawnEntry ram = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityKirrid.class, 10F, 2, 3).conditiion(grassCheck);
		final SpawnEntry aerbunny = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityAerbunny.class, 13F, 3, 5).conditiion(grassCheck);
		final SpawnEntry taegore = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityTaegore.class, 13F, 2, 3).conditiion(grassCheck);
		final SpawnEntry carrion_sprout = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityCarrionSprout.class, 10F, 2, 3)
				.conditiion(grassCheck);

		animals.addEntry(burrukai);
		animals.addEntry(ram);
		animals.addEntry(aerbunny);
		animals.addEntry(taegore);
		animals.addEntry(carrion_sprout);

		/** ATMOSPHERIC **/
		final SpawnHandler atmospheric = new SpawnHandler("aether_atmospheric").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		atmospheric.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry butterfly = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityGlitterwing.class, 10F, 1, 6).conditiion(grassCheck);

		atmospheric.addEntry(butterfly);

		/** HOSTILES **/
		final SpawnHandler hostiles = new SpawnHandler("aether_hostiles").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(1200);
		hostiles.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		final PosCondition groundCheck = new CheckBlockUnderneath(BlocksAether.aether_grass, BlocksAether.holystone);

		final SpawnEntry zephyr = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityZephyr.class, 3F, 2, 3, new FlyingPositionSelector())
				.conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		final SpawnEntry tempest = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityTempest.class, 10F, 2, 3, new FlyingPositionSelector())
				.conditiion(new CheckTime(CheckTime.Time.NIGHT))
				.conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		final SpawnEntry cockatrice = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityCockatrice.class, 12F, 1, 1)
				.conditiion(new CheckTime(CheckTime.Time.NIGHT)).conditiion(groundCheck);
		final SpawnEntry swet = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntitySwet.class, 10F, 2, 4).conditiion(groundCheck);
		final SpawnEntry aechor_plant = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityAechorPlant.class, 10F, 2, 3).conditiion(grassCheck);

		hostiles.addEntry(zephyr);
		hostiles.addEntry(tempest);
		hostiles.addEntry(cockatrice);
		hostiles.addEntry(swet);
		hostiles.addEntry(aechor_plant);

		/** FLYING **/
		final SpawnHandler flying = new SpawnHandler("aether_flying").chunkArea(9).targetEntityCountPerArea(1).updateFrequencyInTicks(1200);
		flying.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry aerwhale = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityAerwhale.class, 10F, 1, 1, new FlyingPositionSelector())
				.conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));

		flying.addEntry(aerwhale);

		this.registerSpawnHandler(animals);
		this.registerSpawnHandler(atmospheric);
		this.registerSpawnHandler(hostiles);
		this.registerSpawnHandler(flying);
	}

	public void registerSpawnHandler(final SpawnHandler spawnHandler)
	{
		this.spawnHandlers.add(spawnHandler);
	}

	public void read()
	{
		for (final SpawnHandler handler : this.spawnHandlers)
		{
			final NBTTagCompound tag = NBTHelper.readNBTFromFile("//data/spawn_areas/" + handler.getUniqueID() + ".dat");

			if (tag == null)
			{
				return;
			}

			handler.read(tag);
		}
	}

	public void write()
	{
		for (final SpawnHandler handler : this.spawnHandlers)
		{
			final NBTTagCompound tag = new NBTTagCompound();

			handler.write(tag);

			NBTHelper.writeNBTToFile(tag, "//data/spawn_areas/" + handler.getUniqueID() + ".dat");
		}
	}

	@SubscribeEvent
	public void onTick(final WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			final World world = event.world;

			if (!world.isRemote)
			{
				for (final SpawnHandler handler : this.spawnHandlers)
				{
					handler.tick(world, world.playerEntities);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeath(final LivingDeathEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (!entity.world.isRemote && entity.hasCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null))
		{
			final ISpawningInfo spawningInfo = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

			final EntitySpawn area = spawningInfo.getSpawnArea();

			if (area != null)
			{
				for (final SpawnHandler handler : this.spawnHandlers)
				{
					if (handler.getUniqueID().equals(area.getSpawnHandlerUniqueID()))
					{
						final boolean areaLoaded = handler.isAreaLoaded(area.getDim(), area.getAreaX(), area.getAreaZ());
						final SpawnArea fetchedArea = handler.getAreaReadOnly(area.getDim(), area.getAreaX(), area.getAreaZ());

						fetchedArea.addToEntityCount(-1);

						if (!areaLoaded)
						{
							handler.saveArea(area.getDim(), fetchedArea);
						}
					}
				}
			}
		}
	}

}

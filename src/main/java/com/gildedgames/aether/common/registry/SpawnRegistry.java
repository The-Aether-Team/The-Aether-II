package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.EntitySpawn;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.entities.living.mobs.*;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.gildedgames.aether.common.world.spawning.PosCondition;
import com.gildedgames.aether.common.world.spawning.SpawnArea;
import com.gildedgames.aether.common.world.spawning.SpawnEntry;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBiome;
import com.gildedgames.aether.common.world.spawning.conditions.CheckBlockStateUnderneath;
import com.gildedgames.aether.common.world.spawning.conditions.CheckDimension;
import com.gildedgames.aether.common.world.spawning.conditions.CheckTime;
import com.gildedgames.aether.common.world.spawning.util.FlyingPositionSelector;
import com.google.common.collect.Lists;
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
		SpawnHandler animals = new SpawnHandler("aether_animals").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		animals.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		PosCondition grassCheck = new CheckBlockStateUnderneath(BlocksAether.aether_grass.getDefaultState(), BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED));

		SpawnEntry burrukai = new SpawnEntry(EntityBurrukai.class, 10F, 2, 3).conditiion(grassCheck);
		SpawnEntry ram = new SpawnEntry(EntityKirrid.class, 10F, 2, 3).conditiion(grassCheck);
		SpawnEntry aerbunny = new SpawnEntry(EntityAerbunny.class, 13F, 3, 5).conditiion(grassCheck);
		SpawnEntry taegore = new SpawnEntry(EntityTaegore.class, 13F, 2, 3).conditiion(grassCheck);
		SpawnEntry carrion_sprout = new SpawnEntry(EntityCarrionSprout.class, 10F, 2, 3).conditiion(grassCheck);

		animals.addEntry(burrukai);
		animals.addEntry(ram);
		animals.addEntry(aerbunny);
		animals.addEntry(taegore);
		animals.addEntry(carrion_sprout);

		/** ATMOSPHERIC **/
		SpawnHandler atmospheric = new SpawnHandler("aether_atmospheric").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		atmospheric.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		SpawnEntry butterfly = new SpawnEntry(EntityGlitterwing.class, 10F, 1, 6).conditiion(grassCheck);

		atmospheric.addEntry(butterfly);

		/** HOSTILES **/
		SpawnHandler hostiles = new SpawnHandler("aether_hostiles").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(1200);
		hostiles.worldCondition(new CheckDimension(DimensionsAether.AETHER));

		PosCondition groundCheck = new CheckBlockStateUnderneath(BlocksAether.aether_grass.getDefaultState(), BlocksAether.holystone.getDefaultState(), BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.ENCHANTED));

		SpawnEntry zephyr = new SpawnEntry(EntityZephyr.class, 3F, 2, 3, new FlyingPositionSelector()).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		SpawnEntry tempest = new SpawnEntry(EntityTempest.class, 10F, 2, 3, new FlyingPositionSelector()).conditiion(new CheckTime(CheckTime.Time.NIGHT)).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));
		SpawnEntry cockatrice = new SpawnEntry(EntityCockatrice.class, 12F, 1, 1).conditiion(new CheckTime(CheckTime.Time.NIGHT)).conditiion(groundCheck);
		SpawnEntry swet = new SpawnEntry(EntitySwet.class, 10F, 2, 4).conditiion(groundCheck);
		SpawnEntry aechor_plant = new SpawnEntry(EntityAechorPlant.class, 10F, 2, 3).conditiion(grassCheck);

		hostiles.addEntry(zephyr);
		hostiles.addEntry(tempest);
		hostiles.addEntry(cockatrice);
		hostiles.addEntry(swet);
		hostiles.addEntry(aechor_plant);

		/** FLYING **/
		SpawnHandler flying = new SpawnHandler("aether_flying").chunkArea(9).targetEntityCountPerArea(1).updateFrequencyInTicks(1200);
		flying.worldCondition(new CheckDimension(DimensionsAether.AETHER)).condition(new CheckBiome(BiomesAether.HIGHLANDS));

		SpawnEntry aerwhale = new SpawnEntry(EntityAerwhale.class, 10F, 1, 1, new FlyingPositionSelector()).conditiion(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));

		flying.addEntry(aerwhale);

		this.registerSpawnHandler(animals);
		this.registerSpawnHandler(atmospheric);
		this.registerSpawnHandler(hostiles);
		this.registerSpawnHandler(flying);
	}

	public void registerSpawnHandler(SpawnHandler spawnHandler)
	{
		this.spawnHandlers.add(spawnHandler);
	}

	public void read()
	{
		for (SpawnHandler handler : this.spawnHandlers)
		{
			NBTTagCompound tag = NBTHelper.readNBTFromFile("//data/spawn_areas/" + handler.getUniqueID() + ".dat");

			if (tag == null)
			{
				return;
			}

			handler.read(tag);
		}
	}

	public void write()
	{
		for (SpawnHandler handler : this.spawnHandlers)
		{
			NBTTagCompound tag = new NBTTagCompound();

			handler.write(tag);

			NBTHelper.writeNBTToFile(tag, "//data/spawn_areas/" + handler.getUniqueID() + ".dat");
		}
	}

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			World world = event.world;

			if (!world.isRemote)
			{
				for (SpawnHandler handler : this.spawnHandlers)
				{
					handler.tick(world, world.playerEntities);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();

		if (!entity.world.isRemote && entity.hasCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null))
		{
			ISpawningInfo spawningInfo = entity.getCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null);

			EntitySpawn area = spawningInfo.getSpawnArea();

			if (area != null)
			{
				for (SpawnHandler handler : this.spawnHandlers)
				{
					if (handler.getUniqueID().equals(area.getSpawnHandlerUniqueID()))
					{
						boolean areaLoaded = handler.isAreaLoaded(area.getDim(), area.getAreaX(), area.getAreaZ());
						SpawnArea fetchedArea = handler.getAreaReadOnly(area.getDim(), area.getAreaX(), area.getAreaZ());

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

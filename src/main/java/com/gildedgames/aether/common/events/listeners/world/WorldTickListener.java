package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.spawn.ISpawnHandler;
import com.gildedgames.aether.api.world.spawn.ISpawnSystem;
import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import com.gildedgames.aether.common.entities.animals.*;
import com.gildedgames.aether.common.entities.monsters.*;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.world.spawning.SpawnEntry;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import com.gildedgames.aether.common.world.spawning.conditions.*;
import com.gildedgames.aether.common.world.spawning.util.FlyingPositionSelector;
import com.gildedgames.aether.common.world.spawning.util.OffsetFromTopBlockPositionSelector;
import com.gildedgames.aether.common.world.spawning.util.UndergroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public class WorldTickListener
{

	public static List<ISpawnHandler> getSpawnHandlers(final World world)
	{
		if (world.getDimension().getType() != DimensionsAether.AETHER)
		{
			return Collections.emptyList();
		}

		final IConditionPosition grassCheck = new CheckBlockUnderneath(BlocksAether.aether_grass);
		final IConditionPosition iceCheck = new CheckBlockUnderneath(BlocksAether.highlands_packed_ice, BlocksAether.highlands_ice, BlocksAether.aether_grass);
		final IConditionPosition groundCheck = new CheckBlockUnderneath(BlocksAether.aether_grass, BlocksAether.holystone);
		final IConditionPosition stoneCheck = new CheckBlockUnderneath(BlocksAether.holystone);
		final IConditionPosition isUnderground = new CheckIsUnderground();
		final IConditionPosition arcticPeaks = new CheckBiome(BiomesAether.ARCTIC_PEAKS);
		final IConditionPosition notForgot = new CheckBannedBiomes(BiomesAether.FORGOTTEN_HIGHLANDS);
		final IConditionPosition forgottenHighlands = new CheckBiome(BiomesAether.FORGOTTEN_HIGHLANDS);

		/** PASSIVE **/
		final SpawnHandler animals = new SpawnHandler("aether_animals").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		animals.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry burrukai = new SpawnEntry(PlacementType.ON_GROUND, EntityBurrukai.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		final SpawnEntry ram = new SpawnEntry(PlacementType.ON_GROUND, EntityKirrid.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		final SpawnEntry aerbunny = new SpawnEntry(PlacementType.ON_GROUND, EntityAerbunny.class, 13F, 3, 5)
				.addCondition(grassCheck).addCondition(notForgot);
		final SpawnEntry taegore = new SpawnEntry(PlacementType.ON_GROUND, EntityTaegore.class, 13F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		final SpawnEntry carrion_sprout = new SpawnEntry(PlacementType.ON_GROUND, EntityCarrionSprout.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		final SpawnEntry glactrix = new SpawnEntry(PlacementType.ON_GROUND, EntityGlactrix.class, 14F, 2, 3).addCondition(arcticPeaks)
				.addCondition(iceCheck).addCondition(notForgot);

		animals.addEntry(burrukai);
		animals.addEntry(ram);
		animals.addEntry(aerbunny);
		animals.addEntry(taegore);
		animals.addEntry(carrion_sprout);
		animals.addEntry(glactrix);

		/** FORGOTTEN HIGHLANDS **/

		final SpawnEntry sheepuff = new SpawnEntry(PlacementType.ON_GROUND, EntitySheepuff.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(forgottenHighlands);
		animals.addEntry(sheepuff);

		/** ATMOSPHERIC **/
		final SpawnHandler atmospheric = new SpawnHandler("aether_atmospheric").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		atmospheric.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry butterfly = new SpawnEntry(PlacementType.IN_AIR, EntityGlitterwing.class, 10F, 1, 6).addCondition(grassCheck);

		atmospheric.addEntry(butterfly);

		/** DAYTIME HOSTILES **/
		final SpawnHandler daytimeHostiles = new SpawnHandler("aether_daytime_hostiles").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(1200);
		daytimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry zephyr = new SpawnEntry(PlacementType.NO_RESTRICTIONS, EntityZephyr.class, 5F, 1, 1,
				new OffsetFromTopBlockPositionSelector(15))
				.addCondition(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState())).addCondition(new CheckBlockAtPosition(Blocks.AIR))
				.addCondition(notForgot);

		final SpawnEntry swet = new SpawnEntry(PlacementType.ON_GROUND, EntitySwet.class, 10F, 2, 4).addCondition(groundCheck)
				.addCondition(notForgot);

		final SpawnEntry aechor_plant = new SpawnEntry(PlacementType.ON_GROUND, EntityAechorPlant.class, 10F, 2, 3).addCondition(grassCheck);

		daytimeHostiles.addEntry(zephyr);
		daytimeHostiles.addEntry(swet);
		daytimeHostiles.addEntry(aechor_plant);

		/** NIGHTTIME HOSTILES **/
		final SpawnHandler nighttimeHostiles = new SpawnHandler("aether_nighttime_hostiles").chunkArea(4).targetEntityCountPerArea(5)
				.updateFrequencyInTicks(1200);
		nighttimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));
		nighttimeHostiles.addWorldCondition(new CheckTime(CheckTime.Time.NIGHT));

		final SpawnEntry tempest = new SpawnEntry(PlacementType.NO_RESTRICTIONS, EntityTempest.class, 3F, 1, 1, new FlyingPositionSelector())
				.addCondition(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState())).addCondition(new CheckTime(CheckTime.Time.NIGHT))
				.addCondition(new CheckBlockAtPosition(Blocks.AIR)).addCondition(notForgot);

		final SpawnEntry cockatrice = new SpawnEntry(PlacementType.ON_GROUND, EntityCockatrice.class, 12F, 2, 3)
				.addCondition(groundCheck).addCondition(new CheckTime(CheckTime.Time.NIGHT));

		final SpawnEntry varanys = new SpawnEntry(PlacementType.ON_GROUND, EntityVaranys.class, 10f, 1, 2)
				.addCondition(groundCheck).addCondition(new CheckTime(CheckTime.Time.NIGHT)).addCondition(arcticPeaks);

		nighttimeHostiles.addEntry(tempest);
		nighttimeHostiles.addEntry(cockatrice);
		nighttimeHostiles.addEntry(varanys);

		/** FLYING **/
		final SpawnHandler flying = new SpawnHandler("aether_flying")
				.chunkArea(20)
				.targetEntityCountPerArea(1)
				.updateFrequencyInTicks(1200);

		flying.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		/*
		SpawnEntry aerwhale = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityAerwhale.class, 10F, 1, 1,
				new OffsetFromTopBlockPositionSelector(35))
				.addCondition(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState()));

		flying.addEntry(aerwhale);
		 */

		/** UNDERGROUND **/
		final SpawnHandler underground = new SpawnHandler("aether_underground").chunkArea(4).targetEntityCountPerArea(10).updateFrequencyInTicks(1200);
		daytimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		final SpawnEntry cockatriceUnderground = new SpawnEntry(PlacementType.ON_GROUND, EntityCockatrice.class, 12F, 1, 3,
				new UndergroundPositionSelector())
				.addCondition(stoneCheck).addCondition(isUnderground).addCondition(new CheckBlockAtPosition(Blocks.AIR));

		final SpawnEntry varanysUnderground = new SpawnEntry(PlacementType.ON_GROUND, EntityVaranys.class, 20f, 1, 3,
				new UndergroundPositionSelector())
				.addCondition(stoneCheck).addCondition(isUnderground).addCondition(arcticPeaks).
						addCondition(new CheckLightLevel(5)).addCondition(new CheckBlockAtPosition(Blocks.AIR));

		final SpawnEntry tempestUnderground = new SpawnEntry(PlacementType.ON_GROUND, EntityTempest.class, 10F, 2, 3,
				new UndergroundPositionSelector())
				.addCondition(stoneCheck).addCondition(isUnderground).addCondition(new CheckBlockAtPosition(Blocks.AIR)).addCondition(notForgot);

		underground.addEntry(cockatriceUnderground);
		underground.addEntry(tempestUnderground);
		underground.addEntry(varanysUnderground);

		return Lists.newArrayList(animals, atmospheric, daytimeHostiles, nighttimeHostiles, flying, underground);
	}

	@SubscribeEvent
	public static void onWorldTick(final WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			final World world = event.world;

			if (!world.isRemote)
			{
				final ISpawnSystem system = event.world.getCapability(CapabilitiesAether.SPAWN_SYSTEM, null);

				if (system != null)
				{
					system.tick();
				}
			}
		}
	}

}

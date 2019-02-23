package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.ISpawnHandler;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.PosCondition;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationCapabilityProvider;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationManagerImpl;
import com.gildedgames.aether.common.entities.living.mobs.*;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.world.spawning.SpawnEntry;
import com.gildedgames.aether.common.world.spawning.SpawnHandler;
import com.gildedgames.aether.common.world.spawning.SpawnSystem;
import com.gildedgames.aether.common.world.spawning.SpawnSystemProvider;
import com.gildedgames.aether.common.world.spawning.conditions.*;
import com.gildedgames.aether.common.world.spawning.util.FlyingPositionSelector;
import com.gildedgames.aether.common.world.spawning.util.OffsetFromTopBlockPositionSelector;
import com.gildedgames.aether.common.world.spawning.util.UndergroundPositionSelector;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public class WorldEvents
{
	@SubscribeEvent
	public static void onWorldAttachCapabilities(AttachCapabilitiesEvent<World> event)
	{
		event.addCapability(AetherCore.getResource("AetherPrecipitation"),
				new PrecipitationCapabilityProvider(new PrecipitationManagerImpl(event.getObject())));

		if (event.getObject().isRemote)
		{
			return;
		}

		World world = event.getObject();

		ISpawnSystem spawnSystem = new SpawnSystem(world, getSpawnHandlers(world));

		event.addCapability(AetherCore.getResource("SpawnSystem"), new SpawnSystemProvider(spawnSystem));
	}

	private static List<ISpawnHandler> getSpawnHandlers(World world)
	{
		if (world.provider.getDimensionType() != DimensionsAether.AETHER)
		{
			return Collections.emptyList();
		}

		PosCondition grassCheck = new CheckBlockUnderneath(BlocksAether.aether_grass);
		PosCondition iceCheck = new CheckBlockUnderneath(BlocksAether.highlands_packed_ice, BlocksAether.highlands_ice, BlocksAether.aether_grass);
		PosCondition groundCheck = new CheckBlockUnderneath(BlocksAether.aether_grass, BlocksAether.holystone);
		PosCondition stoneCheck = new CheckBlockUnderneath(BlocksAether.holystone);
		PosCondition isUnderground = new CheckIsUnderground();
		PosCondition arcticPeaks = new CheckBiome(BiomesAether.ARCTIC_PEAKS);
		PosCondition notForgot = new CheckBannedBiomes(BiomesAether.FORGOTTEN_HIGHLANDS);
		PosCondition forgottenHighlands = new CheckBiome(BiomesAether.FORGOTTEN_HIGHLANDS);

		/** PASSIVE **/
		SpawnHandler animals = new SpawnHandler("aether_animals").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		animals.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		SpawnEntry burrukai = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityBurrukai.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		SpawnEntry ram = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityKirrid.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		SpawnEntry aerbunny = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityAerbunny.class, 13F, 3, 5)
				.addCondition(grassCheck).addCondition(notForgot);
		SpawnEntry taegore = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityTaegore.class, 13F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		SpawnEntry carrion_sprout = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityCarrionSprout.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(notForgot);
		SpawnEntry glactrix = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityGlactrix.class, 14F, 2, 3).addCondition(arcticPeaks)
				.addCondition(iceCheck).addCondition(notForgot);

		animals.addEntry(burrukai);
		animals.addEntry(ram);
		animals.addEntry(aerbunny);
		animals.addEntry(taegore);
		animals.addEntry(carrion_sprout);
		animals.addEntry(glactrix);

		/** FORGOTTEN HIGHLANDS **/


		SpawnEntry sheepuff = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntitySheepuff.class, 10F, 2, 3)
				.addCondition(grassCheck).addCondition(forgottenHighlands);
		animals.addEntry(sheepuff);


		/** ATMOSPHERIC **/
		SpawnHandler atmospheric = new SpawnHandler("aether_atmospheric").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(200);
		atmospheric.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		SpawnEntry butterfly = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityGlitterwing.class, 10F, 1, 6).addCondition(grassCheck);

		atmospheric.addEntry(butterfly);

		/** DAYTIME HOSTILES **/
		SpawnHandler daytimeHostiles = new SpawnHandler("aether_daytime_hostiles").chunkArea(4).targetEntityCountPerArea(9).updateFrequencyInTicks(1200);
		daytimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		SpawnEntry zephyr = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityZephyr.class, 5F, 1, 1, new OffsetFromTopBlockPositionSelector(15))
				.addCondition(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState())).addCondition(new CheckBlockAtPosition(Blocks.AIR)).addCondition(notForgot);

		SpawnEntry swet = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntitySwet.class, 10F, 2, 4).addCondition(groundCheck).addCondition(notForgot);

		SpawnEntry aechor_plant = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityAechorPlant.class, 10F, 2, 3).addCondition(grassCheck);

		daytimeHostiles.addEntry(zephyr);
		daytimeHostiles.addEntry(swet);
		daytimeHostiles.addEntry(aechor_plant);

		/** NIGHTTIME HOSTILES **/
		SpawnHandler nighttimeHostiles = new SpawnHandler("aether_nighttime_hostiles").chunkArea(4).targetEntityCountPerArea(5).updateFrequencyInTicks(1200);
		nighttimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));
		nighttimeHostiles.addWorldCondition(new CheckTime(CheckTime.Time.NIGHT));

		SpawnEntry tempest = new SpawnEntry(EntityLiving.SpawnPlacementType.IN_AIR, EntityTempest.class, 10F, 2, 3, new FlyingPositionSelector())
				.addCondition(new CheckBlockStateUnderneath(Blocks.AIR.getDefaultState())).addCondition(new CheckTime(CheckTime.Time.NIGHT))
				.addCondition(new CheckBlockAtPosition(Blocks.AIR)).addCondition(notForgot);

		SpawnEntry cockatrice = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityCockatrice.class, 12F, 1, 1)
				.addCondition(groundCheck).addCondition(new CheckTime(CheckTime.Time.NIGHT));

		SpawnEntry varanys = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityVaranys.class, 10f,1,2)
				.addCondition(groundCheck).addCondition(new CheckTime(CheckTime.Time.NIGHT)).addCondition(arcticPeaks);

		nighttimeHostiles.addEntry(tempest);
		nighttimeHostiles.addEntry(cockatrice);
		nighttimeHostiles.addEntry(varanys);

		/** FLYING **/
		SpawnHandler flying = new SpawnHandler("aether_flying")
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
		SpawnHandler underground = new SpawnHandler("aether_underground").chunkArea(4).targetEntityCountPerArea(10).updateFrequencyInTicks(1200);
		daytimeHostiles.addWorldCondition(new CheckDimension(DimensionsAether.AETHER));

		SpawnEntry cockatriceUnderground = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityCockatrice.class, 12F, 1, 3,
				new UndergroundPositionSelector())
				.addCondition(stoneCheck).addCondition(isUnderground).addCondition(new CheckBlockAtPosition(Blocks.AIR));

		SpawnEntry varanysUnderground = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityVaranys.class, 20f, 1, 3,
				new UndergroundPositionSelector())
				.addCondition(stoneCheck).addCondition(isUnderground).addCondition(arcticPeaks).
						addCondition(new CheckLightLevel(5)).addCondition(new CheckBlockAtPosition(Blocks.AIR));

		SpawnEntry tempestUnderground = new SpawnEntry(EntityLiving.SpawnPlacementType.ON_GROUND, EntityTempest.class, 10F, 2, 3,
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
				ISpawnSystem system = event.world.getCapability(AetherCapabilities.SPAWN_SYSTEM, null);

				if (system != null)
				{
					system.tick();
				}
			}
		}
	}

}

package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinition;
import com.gildedgames.aether.api.orbis_core.api.BlueprintDefinitionPool;
import com.gildedgames.aether.api.orbis_core.api.PlacementCondition;
import com.gildedgames.aether.api.orbis_core.api.core.OrbisAPI;
import com.gildedgames.aether.api.orbis_core.api.registry.IOrbisDefinitionRegistry;
import com.gildedgames.aether.api.orbis_core.api.registry.OrbisDefinitionRegistry;
import com.gildedgames.aether.api.orbis_core.api.util.PlacementConditions;
import com.gildedgames.aether.api.world.generation.CenterOffsetProcessor;
import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateDefinitionPool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.world.aether.features.WorldGenFloorPlacer;
import com.gildedgames.aether.common.world.aether.features.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.templates.conditions.TemplateConditions;
import com.gildedgames.aether.common.world.templates.post.PostPlacementMoaFamily;
import com.gildedgames.aether.common.world.templates.post.PostPlacementSetBlock;
import com.gildedgames.aether.common.world.templates.post.PostPlacementSpawnEntity;
import net.minecraft.block.material.Material;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class GenerationAether
{

	public static IOrbisDefinitionRegistry registry = new OrbisDefinitionRegistry(AetherCore.MOD_ID);

	public static TemplateDefinition blue_skyroot_tree_1, blue_skyroot_tree_2, blue_skyroot_tree_3;

	public static TemplateDefinition green_skyroot_tree_1, green_skyroot_tree_2, green_skyroot_tree_3;

	public static TemplateDefinition green_skyroot_oak_1, green_skyroot_oak_2;

	public static TemplateDefinition golden_oak_1, golden_oak_2;

	public static TemplateDefinition green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3, green_skyroot_windswept_4, green_skyroot_windswept_5, green_skyroot_windswept_6, green_skyroot_windswept_7;

	public static TemplateDefinition large_green_skyroot_pine_1, large_green_skyroot_pine_2;

	public static TemplateDefinition green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4, green_skyroot_pine_5;

	public static TemplateDefinition dark_blue_skyroot_tree_1, dark_blue_skyroot_tree_2, dark_blue_skyroot_tree_3;

	public static TemplateDefinition dark_blue_skyroot_oak_1, dark_blue_skyroot_oak_2;

	public static TemplateDefinition skyroot_moa_nest_tree_1, skyroot_moa_nest_1, skyroot_moa_nest_2;

	public static TemplateDefinition aether_portal_for_world, aether_portal, nether_portal, end_portal;

	public static TemplateDefinition mysterious_henge, outpost_a, outpost_b, outpost_c;

	public static TemplateDefinitionPool green_skyroot_windswept;

	public static TemplateDefinitionPool large_green_skyroot_pine, green_skyroot_pine, green_skyroot_small_pine;

	public static TemplateDefinitionPool blue_skyroot_tree, green_skyroot_tree, golden_oak, green_skyroot_oak;

	public static TemplateDefinitionPool dark_blue_skyroot_tree, dark_blue_skyroot_oak;

	public static TemplateDefinitionPool skyroot_moa_nest;

	public static BlueprintDefinition ABAND_ANGEL_STOREROOM_1A;

	public static BlueprintDefinitionPool ABAND_ANGEL_STOREROOM;

	public static BlueprintDefinition ABAND_ANGEL_WATCHTOWER_1A;

	public static BlueprintDefinitionPool ABAND_ANGEL_WATCHTOWER;

	public static BlueprintDefinition ABAND_CAMPSITE_1A;

	public static BlueprintDefinitionPool ABAND_CAMPSITE;

	public static BlueprintDefinition ABAND_HUMAN_HOUSE_1A;

	public static BlueprintDefinition ABAND_HUMAN_HOUSE_1B;

	public static BlueprintDefinitionPool ABAND_HUMAN_HOUSE;

	public static BlueprintDefinition OUTPOST_HIGHLANDS_A;

	public static BlueprintDefinition OUTPOST_HIGHLANDS_B;

	public static BlueprintDefinitionPool OUTPOST_HIGHLANDS;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_1A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_1B;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_2A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_2B;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_3A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_3B;

	public static BlueprintDefinitionPool SKYROOT_WATCHTOWER;

	public static BlueprintDefinition WELL_1A;

	public static BlueprintDefinition WELL_1B;

	public static BlueprintDefinitionPool WELL;

	public static WorldGenFloorPlacer short_aether_grass, aether_grass, long_aether_grass, skyroot_twigs, holystone_rocks;

	public static WorldGenAercloud green_aercloud, golden_aercloud, storm_aercloud;

	private GenerationAether()
	{

	}

	public static void init()
	{
		OrbisAPI.services().register(registry);

		blue_skyroot_tree_1 = new TemplateDefinition(TemplatesAether.blue_skyroot_tree_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		blue_skyroot_tree_2 = new TemplateDefinition(TemplatesAether.blue_skyroot_tree_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		blue_skyroot_tree_3 = new TemplateDefinition(TemplatesAether.blue_skyroot_tree_3)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);

		green_skyroot_tree_1 = new TemplateDefinition(TemplatesAether.green_skyroot_tree_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_tree_2 = new TemplateDefinition(TemplatesAether.green_skyroot_tree_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_tree_3 = new TemplateDefinition(TemplatesAether.green_skyroot_tree_3)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);

		final CenterOffsetProcessor oak_center = rotation ->
		{
			if (rotation == Rotation.CLOCKWISE_90)
			{
				return new BlockPos(-1, 0, 0);
			}

			if (rotation == Rotation.COUNTERCLOCKWISE_90)
			{
				return new BlockPos(0, 0, -1);
			}

			if (rotation == Rotation.CLOCKWISE_180)
			{
				return new BlockPos(-1, 0, -1);
			}

			return new BlockPos(0, 0, 0);
		};

		green_skyroot_oak_1 = new TemplateDefinition(TemplatesAether.green_skyroot_oak_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE).setOffset(oak_center);
		green_skyroot_oak_2 = new TemplateDefinition(TemplatesAether.green_skyroot_oak_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE).setOffset(oak_center);

		golden_oak_1 = new TemplateDefinition(TemplatesAether.golden_oak_1).setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
				TemplateConditions.REPLACEABLE).setOffset(oak_center);
		golden_oak_2 = new TemplateDefinition(TemplatesAether.golden_oak_2).setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
				TemplateConditions.REPLACEABLE).setOffset(oak_center);

		dark_blue_skyroot_oak_1 = new TemplateDefinition(TemplatesAether.dark_blue_skyroot_oak_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE).setOffset(oak_center);
		dark_blue_skyroot_oak_2 = new TemplateDefinition(TemplatesAether.dark_blue_skyroot_oak_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE).setOffset(oak_center);

		green_skyroot_windswept_1 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_2 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_3 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_3)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_4 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_4)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_5 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_5)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_6 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_6)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_7 = new TemplateDefinition(TemplatesAether.green_skyroot_windswept_7)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE_CANOPY);

		large_green_skyroot_pine_1 = new TemplateDefinition(TemplatesAether.large_green_skyroot_pine_1).setConditions(TemplateConditions.FLAT_GROUND,
				TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		large_green_skyroot_pine_2 = new TemplateDefinition(TemplatesAether.large_green_skyroot_pine_2).setConditions(TemplateConditions.FLAT_GROUND,
				TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		green_skyroot_pine_1 = new TemplateDefinition(TemplatesAether.green_skyroot_pine_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_pine_2 = new TemplateDefinition(TemplatesAether.green_skyroot_pine_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_pine_3 = new TemplateDefinition(TemplatesAether.green_skyroot_pine_3)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_pine_4 = new TemplateDefinition(TemplatesAether.green_skyroot_pine_4)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		green_skyroot_pine_5 = new TemplateDefinition(TemplatesAether.green_skyroot_pine_5)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);

		dark_blue_skyroot_tree_1 = new TemplateDefinition(TemplatesAether.dark_blue_skyroot_tree_1)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		dark_blue_skyroot_tree_2 = new TemplateDefinition(TemplatesAether.dark_blue_skyroot_tree_2)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);
		dark_blue_skyroot_tree_3 = new TemplateDefinition(TemplatesAether.dark_blue_skyroot_tree_3)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL,
						TemplateConditions.REPLACEABLE);

		skyroot_moa_nest_tree_1 = new TemplateDefinition(TemplatesAether.skyroot_moa_nest_tree_1).setConditions(TemplateConditions.FLAT_GROUND,
				TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE).setPostPlacements(new PostPlacementMoaFamily(new BlockPos(4, 5, 4)));

		skyroot_moa_nest_1 = new TemplateDefinition(TemplatesAether.skyroot_moa_nest_1).setConditions(TemplateConditions.INSIDE_GROUND,
				TemplateConditions.REPLACEABLE_GROUND).setPostPlacements(new PostPlacementMoaFamily(new BlockPos(2, 0, 2)));
		skyroot_moa_nest_2 = new TemplateDefinition(TemplatesAether.skyroot_moa_nest_2).setConditions(TemplateConditions.INSIDE_GROUND,
				TemplateConditions.REPLACEABLE_GROUND).setPostPlacements(new PostPlacementMoaFamily(new BlockPos(3, 0, 3)));

		aether_portal = new TemplateDefinition(TemplatesAether.aether_portal).setConditions(TemplateConditions.REPLACEABLE);
		nether_portal = new TemplateDefinition(TemplatesAether.nether_portal).setConditions(TemplateConditions.REPLACEABLE);
		end_portal = new TemplateDefinition(TemplatesAether.end_portal).setConditions(TemplateConditions.REPLACEABLE);

		aether_portal_for_world = new TemplateDefinition(TemplatesAether.aether_portal)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL,
						TemplateConditions.REPLACEABLE_GROUND);

		final BlockPos edisonPos = new BlockPos(-1.0, 2, -1.0);
		final BlockPos campfirePos = new BlockPos(0.0, 1, 0.0);

		mysterious_henge = new TemplateDefinition(TemplatesAether.mysterious_henge)
				.setConditions(TemplateConditions.INSIDE_GROUND, TemplateConditions.REPLACEABLE_GROUND)
				.setPostPlacements(new PostPlacementSpawnEntity(EntityEdison::new, edisonPos));

		outpost_a = new TemplateDefinition(TemplatesAether.outpost_a).setConditions(TemplateConditions.INSIDE_GROUND_AT_SOURCE)
				.setPostPlacements(new PostPlacementSpawnEntity(EntityEdison::new, edisonPos),
						new PostPlacementSetBlock(BlocksAether.outpost_campfire.getDefaultState(), campfirePos));

		outpost_b = new TemplateDefinition(TemplatesAether.outpost_b).setConditions(TemplateConditions.INSIDE_GROUND_AT_SOURCE)
				.setPostPlacements(new PostPlacementSpawnEntity(EntityEdison::new, edisonPos),
						new PostPlacementSetBlock(BlocksAether.outpost_campfire.getDefaultState(), campfirePos));

		outpost_c = new TemplateDefinition(TemplatesAether.outpost_c).setConditions(TemplateConditions.INSIDE_GROUND_AT_SOURCE)
				.setPostPlacements(new PostPlacementSpawnEntity(EntityEdison::new, edisonPos),
						new PostPlacementSetBlock(BlocksAether.outpost_campfire.getDefaultState(), campfirePos));

		mysterious_henge.setRandomRotation(false);

		outpost_a.setRandomRotation(false);
		outpost_b.setRandomRotation(false);
		outpost_c.setRandomRotation(false);

		blue_skyroot_tree = new TemplateDefinitionPool(blue_skyroot_tree_1, blue_skyroot_tree_2, blue_skyroot_tree_3);
		green_skyroot_tree = new TemplateDefinitionPool(green_skyroot_tree_1, green_skyroot_tree_2, green_skyroot_tree_3);
		golden_oak = new TemplateDefinitionPool(golden_oak_1, golden_oak_2);
		green_skyroot_oak = new TemplateDefinitionPool(green_skyroot_oak_1, green_skyroot_oak_2);
		green_skyroot_windswept = new TemplateDefinitionPool(green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3,
				green_skyroot_windswept_4, green_skyroot_windswept_5, green_skyroot_windswept_6, green_skyroot_windswept_7);
		large_green_skyroot_pine = new TemplateDefinitionPool(large_green_skyroot_pine_1, large_green_skyroot_pine_2);
		green_skyroot_pine = new TemplateDefinitionPool(green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4,
				green_skyroot_pine_5);
		green_skyroot_small_pine = new TemplateDefinitionPool(green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3);
		dark_blue_skyroot_tree = new TemplateDefinitionPool(dark_blue_skyroot_tree_1, dark_blue_skyroot_tree_2, dark_blue_skyroot_tree_3);
		dark_blue_skyroot_oak = new TemplateDefinitionPool(dark_blue_skyroot_oak_1, dark_blue_skyroot_oak_2);
		skyroot_moa_nest = new TemplateDefinitionPool(skyroot_moa_nest_1, skyroot_moa_nest_2);

		short_aether_grass = new WorldGenFloorPlacer(
				BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.SHORT));
		aether_grass = new WorldGenFloorPlacer(
				BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.NORMAL));
		long_aether_grass = new WorldGenFloorPlacer(
				BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.LONG));

		skyroot_twigs = new WorldGenFloorPlacer(4, BlocksAether.skyroot_twigs.getDefaultState(), BlocksAether.skyroot_twigs.getDefaultState(),
				BlocksAether.holystone_rock.getDefaultState());
		holystone_rocks = new WorldGenFloorPlacer(BlocksAether.holystone_rock.getDefaultState());

		holystone_rocks.setStatesToPlaceOn(BlocksAether.holystone.getDefaultState());

		green_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.GREEN_AERCLOUD), 4, false);
		golden_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.GOLDEN_AERCLOUD), 4, false);
		storm_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.STORM_AERCLOUD), 16, false);

		final PlacementCondition replace = PlacementConditions.replaceableGround();
		final PlacementCondition no_quicksoil = PlacementConditions.ignoreBlock(0, BlocksAether.quicksoil.getDefaultState());

		final PlacementCondition[] structureConditions = new PlacementCondition[]
				{
						PlacementConditions.onSpecificBlock(0, BlocksAether.aether_grass, BlocksAether.aether_dirt),
						replace,
						no_quicksoil
				};

		ABAND_ANGEL_STOREROOM_1A = new BlueprintDefinition(BlueprintsAether.ABAND_ANGEL_STOREROOM_1A).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(0, BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone), replace,
						PlacementConditions.ignoreBlock(0, BlocksAether.quicksoil.getDefaultState()));

		ABAND_ANGEL_WATCHTOWER_1A = new BlueprintDefinition(BlueprintsAether.ABAND_ANGEL_WATCHTOWER_1A).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(0, BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone), replace,
						PlacementConditions.ignoreBlock(0, BlocksAether.quicksoil.getDefaultState()));

		ABAND_CAMPSITE_1A = new BlueprintDefinition(BlueprintsAether.ABAND_CAMPSITE_1A).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(0, BlocksAether.aether_grass), replace, no_quicksoil);

		ABAND_HUMAN_HOUSE_1A = new BlueprintDefinition(BlueprintsAether.ABAND_HUMAN_HOUSE_1A).setRegistry(registry).setConditions(structureConditions);
		ABAND_HUMAN_HOUSE_1B = new BlueprintDefinition(BlueprintsAether.ABAND_HUMAN_HOUSE_1B).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(5, BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone),
						PlacementConditions.replaceable(true, Material.ROCK, Material.GROUND, Material.GRASS, Material.AIR),
						PlacementConditions.ignoreBlock(5, BlocksAether.quicksoil.getDefaultState()));

		OUTPOST_HIGHLANDS_A = new BlueprintDefinition(BlueprintsAether.OUTPOST_HIGHLANDS_A).setRegistry(registry).setConditions(structureConditions)
				.setRandomRotation(false);
		OUTPOST_HIGHLANDS_B = new BlueprintDefinition(BlueprintsAether.OUTPOST_HIGHLANDS_B).setRegistry(registry).setConditions(structureConditions)
				.setRandomRotation(false);

		SKYROOT_WATCHTOWER_1A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_1A).setRegistry(registry).setConditions(structureConditions);
		SKYROOT_WATCHTOWER_1B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_1B).setRegistry(registry).setConditions(structureConditions);

		SKYROOT_WATCHTOWER_2A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_2A).setRegistry(registry).setConditions(structureConditions);
		SKYROOT_WATCHTOWER_2B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_2B).setRegistry(registry).setConditions(structureConditions);

		SKYROOT_WATCHTOWER_3A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_3A).setRegistry(registry).setConditions(structureConditions);
		SKYROOT_WATCHTOWER_3B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_3B).setRegistry(registry).setConditions(structureConditions);

		WELL_1A = new BlueprintDefinition(BlueprintsAether.WELL_1A).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(9, BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone),
						PlacementConditions.replaceable(true, Material.ROCK, Material.GROUND, Material.GRASS, Material.AIR),
						PlacementConditions.ignoreBlock(9, BlocksAether.quicksoil.getDefaultState()));
		WELL_1B = new BlueprintDefinition(BlueprintsAether.WELL_1B).setRegistry(registry)
				.setConditions(PlacementConditions.onSpecificBlock(9, BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone),
						PlacementConditions.replaceable(true, Material.ROCK, Material.GROUND, Material.GRASS, Material.AIR),
						PlacementConditions.ignoreBlock(9, BlocksAether.quicksoil.getDefaultState()));

		ABAND_ANGEL_STOREROOM = new BlueprintDefinitionPool(ABAND_ANGEL_STOREROOM_1A);
		ABAND_ANGEL_WATCHTOWER = new BlueprintDefinitionPool(ABAND_ANGEL_WATCHTOWER_1A);
		ABAND_CAMPSITE = new BlueprintDefinitionPool(ABAND_CAMPSITE_1A);
		ABAND_HUMAN_HOUSE = new BlueprintDefinitionPool(ABAND_HUMAN_HOUSE_1A, ABAND_HUMAN_HOUSE_1B);
		OUTPOST_HIGHLANDS = new BlueprintDefinitionPool(OUTPOST_HIGHLANDS_A, OUTPOST_HIGHLANDS_B);
		SKYROOT_WATCHTOWER = new BlueprintDefinitionPool(SKYROOT_WATCHTOWER_1A, SKYROOT_WATCHTOWER_1B, SKYROOT_WATCHTOWER_2A, SKYROOT_WATCHTOWER_2B,
				SKYROOT_WATCHTOWER_3A, SKYROOT_WATCHTOWER_3B);
		WELL = new BlueprintDefinitionPool(WELL_1A, WELL_1B);

		registry.register(0, ABAND_ANGEL_STOREROOM_1A);

		registry.register(1, ABAND_ANGEL_WATCHTOWER_1A);

		registry.register(2, ABAND_CAMPSITE_1A);

		registry.register(3, ABAND_HUMAN_HOUSE_1A);
		registry.register(4, ABAND_HUMAN_HOUSE_1B);

		registry.register(5, OUTPOST_HIGHLANDS_A);
		registry.register(6, OUTPOST_HIGHLANDS_B);

		registry.register(7, SKYROOT_WATCHTOWER_1A);
		registry.register(8, SKYROOT_WATCHTOWER_1B);

		registry.register(9, SKYROOT_WATCHTOWER_2A);
		registry.register(10, SKYROOT_WATCHTOWER_2B);

		registry.register(11, SKYROOT_WATCHTOWER_3A);
		registry.register(12, SKYROOT_WATCHTOWER_3B);

		registry.register(13, WELL_1A);
		registry.register(14, WELL_1B);

		reg(0, blue_skyroot_tree_1);
		reg(1, blue_skyroot_tree_2);
		reg(2, blue_skyroot_tree_3);
		reg(3, green_skyroot_tree_1);
		reg(4, green_skyroot_tree_2);
		reg(5, green_skyroot_tree_3);
		reg(6, green_skyroot_oak_1);
		reg(7, green_skyroot_oak_2);
		reg(8, golden_oak_1);
		reg(9, golden_oak_2);
		reg(10, dark_blue_skyroot_oak_1);
		reg(11, dark_blue_skyroot_oak_2);
		reg(12, green_skyroot_windswept_1);
		reg(13, green_skyroot_windswept_2);
		reg(14, green_skyroot_windswept_3);
		reg(15, green_skyroot_windswept_4);
		reg(16, green_skyroot_windswept_5);
		reg(17, green_skyroot_windswept_6);
		reg(18, green_skyroot_windswept_7);
		reg(19, large_green_skyroot_pine_1);
		reg(20, large_green_skyroot_pine_2);
		reg(21, green_skyroot_pine_1);
		reg(22, green_skyroot_pine_2);
		reg(23, green_skyroot_pine_3);
		reg(24, green_skyroot_pine_4);
		reg(25, green_skyroot_pine_5);
		reg(26, dark_blue_skyroot_tree_1);
		reg(27, dark_blue_skyroot_tree_2);
		reg(28, dark_blue_skyroot_tree_3);
		reg(29, skyroot_moa_nest_tree_1);
		reg(30, skyroot_moa_nest_1);
		reg(31, skyroot_moa_nest_2);
		reg(32, aether_portal);
		reg(33, nether_portal);
		reg(34, end_portal);
		reg(35, aether_portal_for_world);
		reg(36, mysterious_henge);
		reg(37, outpost_a);
		reg(38, outpost_b);
		reg(39, outpost_c);
	}

	public static TemplateDefinition reg(final int id, final TemplateDefinition def)
	{
		AetherCore.PROXY.content().templates().register(id, def);

		return def;
	}

}

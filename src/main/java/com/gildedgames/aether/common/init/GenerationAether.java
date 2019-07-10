package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.templates.TemplateDefinition;
import com.gildedgames.aether.api.world.templates.TemplateDefinitionPool;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.world.decorations.WorldGenFloorPlacer;
import com.gildedgames.aether.common.world.decorations.WorldGenIceCrystals;
import com.gildedgames.aether.common.world.decorations.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.templates.conditions.TemplateConditions;
import com.gildedgames.aether.common.world.templates.post.PostPlacementMoaFamily;
import com.gildedgames.aether.common.world.util.GenUtil;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.core.BlueprintDefinition;
import com.gildedgames.orbis.lib.core.BlueprintDefinitionPool;
import com.gildedgames.orbis.lib.core.PlacementCondition;
import com.gildedgames.orbis.lib.core.conditions.PlacementConditionAvoidBlock;
import com.gildedgames.orbis.lib.core.conditions.PlacementConditionOnBlock;
import com.gildedgames.orbis.lib.core.conditions.PlacementConditionReplaceableMaterials;
import com.gildedgames.orbis.lib.core.registry.IOrbisDefinitionRegistry;
import com.gildedgames.orbis.lib.core.registry.OrbisDefinitionRegistry;
import com.gildedgames.orbis.lib.processing.CenterOffsetProcessor;
import net.minecraft.block.material.Material;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class GenerationAether
{

	public static final IOrbisDefinitionRegistry registry = new OrbisDefinitionRegistry(AetherCore.MOD_ID);

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

	public static TemplateDefinition aether_portal_for_world, aether_portal;

	public static TemplateDefinitionPool green_skyroot_windswept;

	public static TemplateDefinitionPool large_green_skyroot_pine, green_skyroot_pine, green_skyroot_small_pine;

	public static TemplateDefinitionPool blue_skyroot_tree, green_skyroot_tree, golden_oak, green_skyroot_oak;

	public static TemplateDefinitionPool dark_blue_skyroot_tree, dark_blue_skyroot_oak;

	public static TemplateDefinitionPool skyroot_moa_nest;

	public static WorldGenFloorPlacer short_aether_grass, aether_grass, long_aether_grass, skyroot_twigs, holystone_rocks;

	public static WorldGenIceCrystals ice_crystals;

	public static WorldGenAercloud green_aercloud, golden_aercloud, storm_aercloud;

	public static BlueprintDefinition OUTPOST_A;

	public static BlueprintDefinition OUTPOST_B;

	public static BlueprintDefinitionPool OUTPOST;

	public static BlueprintDefinition NECROMANCER_TOWER;

	public static BlueprintDefinition ABAND_ANGEL_STOREROOM_1A;

	public static BlueprintDefinitionPool ABAND_ANGEL_STOREROOM;

	public static BlueprintDefinition ABAND_ANGEL_WATCHTOWER_1A;

	public static BlueprintDefinitionPool ABAND_ANGEL_WATCHTOWER;

	public static BlueprintDefinition ABAND_CAMPSITE_1A;

	public static BlueprintDefinitionPool ABAND_CAMPSITE;

	public static BlueprintDefinition ABAND_HUMAN_HOUSE_1A;

	public static BlueprintDefinition ABAND_HUMAN_HOUSE_1B;

	public static BlueprintDefinitionPool ABAND_HUMAN_HOUSE;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_1A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_1B;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_2A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_2B;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_3A;

	public static BlueprintDefinition SKYROOT_WATCHTOWER_3B;

	public static BlueprintDefinition GREATROOT_TREE;

	public static BlueprintDefinition SKYROOT_OAK_GREEN, SKYROOT_OAK_BLUE, SKYROOT_OAK_DARK_BLUE;

	public static BlueprintDefinition CRAZY_MUTANT_TREE;

	public static BlueprintDefinition WISPROOT_GREEN, WISPROOT_BLUE, WISPROOT_DARK_BLUE;

	public static BlueprintDefinitionPool SKYROOT_WATCHTOWER;

	public static BlueprintDefinition WELL_1A;

	public static BlueprintDefinition WELL_1B;

	public static BlueprintDefinitionPool WELL;

	public static BlueprintDefinition AMBEROOT_TREE;

	private GenerationAether()
	{

	}

	public static void load()
	{
		registerLegacyTemplates();
		registerDynamicGenerators();
		registerBlueprints();
	}

	private static void registerDynamicGenerators()
	{
        short_aether_grass = new WorldGenFloorPlacer(GenUtil.equalStateFetcher(),
                GenUtil.standardStateDefiner(
                        BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.SHORT)));
        aether_grass = new WorldGenFloorPlacer(GenUtil.equalStateFetcher(),
                GenUtil.standardStateDefiner(
                        BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.NORMAL)));
        long_aether_grass = new WorldGenFloorPlacer(GenUtil.equalStateFetcher(),
                GenUtil.standardStateDefiner(
                        BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.LONG)));

        skyroot_twigs = new WorldGenFloorPlacer(4, GenUtil.equalStateFetcher(), GenUtil.standardStateDefiner(BlocksAether.skyroot_twigs.getDefaultState(),
                BlocksAether.holystone_rock.getDefaultState()));

        holystone_rocks = new WorldGenFloorPlacer(GenUtil.equalStateFetcher(), GenUtil.standardStateDefiner(BlocksAether.holystone_rock.getDefaultState()));
		holystone_rocks.setStatesToPlaceOn(BlocksAether.holystone.getDefaultState());

		ice_crystals = new WorldGenIceCrystals(64);
		ice_crystals.setStatesToPlaceOn(BlocksAether.holystone.getDefaultState(), BlocksAether.highlands_ice.getDefaultState());

		green_aercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.GREEN_AERCLOUD), 4, false);
		golden_aercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.GOLDEN_AERCLOUD), 4, false);
		storm_aercloud = new WorldGenAercloud(BlockAercloud.getAercloudState(BlockAercloud.STORM_AERCLOUD), 16, false);
	}

	private static void registerBlueprints()
	{
		OrbisLib.services().register(registry);

		final PlacementCondition avoidQuicksoil = new PlacementConditionAvoidBlock(BlocksAether.quicksoil);
		final PlacementCondition isReplaceableGround = new PlacementConditionReplaceableMaterials(Material.GROUND, Material.GRASS);
		final PlacementCondition isReplaceableTerrain = new PlacementConditionReplaceableMaterials(Material.ROCK, Material.GROUND, Material.GRASS);
		final PlacementCondition isOnSoil = new PlacementConditionOnBlock(BlocksAether.aether_grass);
		final PlacementCondition isOnEarth = new PlacementConditionOnBlock(BlocksAether.aether_grass, BlocksAether.aether_dirt, BlocksAether.holystone);

		final PlacementCondition[] structureConditions = new PlacementCondition[] { isOnEarth, avoidQuicksoil, isReplaceableGround };

		OUTPOST_A = new BlueprintDefinition(BlueprintsAether.OUTPOST_A)
				.setRegistry(registry)
				.setConditions(structureConditions)
				.setRandomRotation(false);

		OUTPOST_B = new BlueprintDefinition(BlueprintsAether.OUTPOST_B)
				.setRegistry(registry)
				.setConditions(structureConditions)
				.setRandomRotation(false);

		NECROMANCER_TOWER = new BlueprintDefinition(BlueprintsAether.NECROMANCER_TOWER)
				.setRegistry(registry)
				.setRandomRotation(false);

		ABAND_ANGEL_STOREROOM_1A = new BlueprintDefinition(BlueprintsAether.ABAND_ANGEL_STOREROOM_1A, 1)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableGround, avoidQuicksoil);

		ABAND_ANGEL_WATCHTOWER_1A = new BlueprintDefinition(BlueprintsAether.ABAND_ANGEL_WATCHTOWER_1A, 1)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableGround, avoidQuicksoil);

		ABAND_CAMPSITE_1A = new BlueprintDefinition(BlueprintsAether.ABAND_CAMPSITE_1A)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		ABAND_HUMAN_HOUSE_1A = new BlueprintDefinition(BlueprintsAether.ABAND_HUMAN_HOUSE_1A, 1)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableGround, avoidQuicksoil);

		ABAND_HUMAN_HOUSE_1B = new BlueprintDefinition(BlueprintsAether.ABAND_HUMAN_HOUSE_1B, 5)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableGround, avoidQuicksoil);

		SKYROOT_WATCHTOWER_1A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_1A)
				.setRegistry(registry)
				.setConditions(structureConditions);

		SKYROOT_WATCHTOWER_1B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_1B)
				.setRegistry(registry)
				.setConditions(structureConditions);

		SKYROOT_WATCHTOWER_2A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_2A)
				.setRegistry(registry)
				.setConditions(structureConditions);

		SKYROOT_WATCHTOWER_2B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_2B)
				.setRegistry(registry)
				.setConditions(structureConditions);

		SKYROOT_WATCHTOWER_3A = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_3A)
				.setRegistry(registry)
				.setConditions(structureConditions);

		SKYROOT_WATCHTOWER_3B = new BlueprintDefinition(BlueprintsAether.SKYROOT_WATCHTOWER_3B)
				.setRegistry(registry)
				.setConditions(structureConditions);

		WELL_1A = new BlueprintDefinition(BlueprintsAether.WELL_1A, 10)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableTerrain, avoidQuicksoil);

		WELL_1B = new BlueprintDefinition(BlueprintsAether.WELL_1B, 11)
				.setRegistry(registry)
				.setConditions(isOnEarth, isReplaceableTerrain, avoidQuicksoil);

		GREATROOT_TREE = new BlueprintDefinition(BlueprintsAether.GREATROOT_TREE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		SKYROOT_OAK_GREEN = new BlueprintDefinition(BlueprintsAether.SKYROOT_OAK_GREEN, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		SKYROOT_OAK_BLUE = new BlueprintDefinition(BlueprintsAether.SKYROOT_OAK_BLUE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		SKYROOT_OAK_DARK_BLUE = new BlueprintDefinition(BlueprintsAether.SKYROOT_OAK_DARK_BLUE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		AMBEROOT_TREE = new BlueprintDefinition(BlueprintsAether.AMBEROOT_TREE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		WISPROOT_GREEN = new BlueprintDefinition(BlueprintsAether.WISPROOT_GREEN, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		WISPROOT_BLUE = new BlueprintDefinition(BlueprintsAether.WISPROOT_BLUE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		WISPROOT_DARK_BLUE = new BlueprintDefinition(BlueprintsAether.WISPROOT_DARK_BLUE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		OUTPOST = new BlueprintDefinitionPool(OUTPOST_A, OUTPOST_B);
		ABAND_ANGEL_STOREROOM = new BlueprintDefinitionPool(ABAND_ANGEL_STOREROOM_1A);
		ABAND_ANGEL_WATCHTOWER = new BlueprintDefinitionPool(ABAND_ANGEL_WATCHTOWER_1A);
		ABAND_CAMPSITE = new BlueprintDefinitionPool(ABAND_CAMPSITE_1A);
		ABAND_HUMAN_HOUSE = new BlueprintDefinitionPool(ABAND_HUMAN_HOUSE_1A, ABAND_HUMAN_HOUSE_1B);
		SKYROOT_WATCHTOWER = new BlueprintDefinitionPool(SKYROOT_WATCHTOWER_1A, SKYROOT_WATCHTOWER_1B, SKYROOT_WATCHTOWER_2A, SKYROOT_WATCHTOWER_2B,
				SKYROOT_WATCHTOWER_3A, SKYROOT_WATCHTOWER_3B);
		WELL = new BlueprintDefinitionPool(WELL_1A, WELL_1B);

		CRAZY_MUTANT_TREE = new BlueprintDefinition(BlueprintsAether.CRAZY_MUTANT_TREE, 0)
				.setRegistry(registry)
				.setConditions(isOnSoil, isReplaceableGround, avoidQuicksoil);

		registry.register(0, OUTPOST_A);
		registry.register(1, NECROMANCER_TOWER);

		registry.register(2, ABAND_CAMPSITE_1A);

		registry.register(3, ABAND_HUMAN_HOUSE_1A);
		registry.register(4, ABAND_HUMAN_HOUSE_1B);

		registry.register(5, ABAND_ANGEL_STOREROOM_1A);
		registry.register(6, ABAND_ANGEL_WATCHTOWER_1A);

		registry.register(7, SKYROOT_WATCHTOWER_1A);
		registry.register(8, SKYROOT_WATCHTOWER_1B);

		registry.register(9, SKYROOT_WATCHTOWER_2A);
		registry.register(10, SKYROOT_WATCHTOWER_2B);

		registry.register(11, SKYROOT_WATCHTOWER_3A);
		registry.register(12, SKYROOT_WATCHTOWER_3B);

		registry.register(13, WELL_1A);
		registry.register(14, WELL_1B);
		registry.register(15, OUTPOST_B);

		registry.register(17, GREATROOT_TREE);
		registry.register(18, SKYROOT_OAK_GREEN);
		registry.register(19, SKYROOT_OAK_BLUE);
		registry.register(20, SKYROOT_OAK_DARK_BLUE);
		registry.register(21, AMBEROOT_TREE);
		registry.register(22, WISPROOT_GREEN);
		registry.register(23, WISPROOT_BLUE);
		registry.register(24, WISPROOT_DARK_BLUE);
		registry.register(25, CRAZY_MUTANT_TREE);
	}

	private static void registerLegacyTemplates()
	{
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
				TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE, TemplateConditions.ON_SOIL)
				.setPostPlacements(new PostPlacementMoaFamily(new BlockPos(4, 5, 4)));

		skyroot_moa_nest_1 = new TemplateDefinition(TemplatesAether.skyroot_moa_nest_1).setConditions(TemplateConditions.INSIDE_GROUND,
				TemplateConditions.REPLACEABLE_GROUND).setPostPlacements(new PostPlacementMoaFamily(new BlockPos(2, 0, 2)));
		skyroot_moa_nest_2 = new TemplateDefinition(TemplatesAether.skyroot_moa_nest_2).setConditions(TemplateConditions.INSIDE_GROUND,
				TemplateConditions.REPLACEABLE_GROUND).setPostPlacements(new PostPlacementMoaFamily(new BlockPos(3, 0, 3)));

		aether_portal = new TemplateDefinition(TemplatesAether.aether_portal).setConditions(TemplateConditions.REPLACEABLE);

		aether_portal_for_world = new TemplateDefinition(TemplatesAether.aether_portal)
				.setConditions(TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL,
						TemplateConditions.REPLACEABLE_GROUND);

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

		registerTemplate(0, blue_skyroot_tree_1);
		registerTemplate(1, blue_skyroot_tree_2);
		registerTemplate(2, blue_skyroot_tree_3);
		registerTemplate(3, green_skyroot_tree_1);
		registerTemplate(4, green_skyroot_tree_2);
		registerTemplate(5, green_skyroot_tree_3);
		registerTemplate(6, green_skyroot_oak_1);
		registerTemplate(7, green_skyroot_oak_2);
		registerTemplate(8, golden_oak_1);
		registerTemplate(9, golden_oak_2);
		registerTemplate(10, dark_blue_skyroot_oak_1);
		registerTemplate(11, dark_blue_skyroot_oak_2);
		registerTemplate(12, green_skyroot_windswept_1);
		registerTemplate(13, green_skyroot_windswept_2);
		registerTemplate(14, green_skyroot_windswept_3);
		registerTemplate(15, green_skyroot_windswept_4);
		registerTemplate(16, green_skyroot_windswept_5);
		registerTemplate(17, green_skyroot_windswept_6);
		registerTemplate(18, green_skyroot_windswept_7);
		registerTemplate(19, large_green_skyroot_pine_1);
		registerTemplate(20, large_green_skyroot_pine_2);
		registerTemplate(21, green_skyroot_pine_1);
		registerTemplate(22, green_skyroot_pine_2);
		registerTemplate(23, green_skyroot_pine_3);
		registerTemplate(24, green_skyroot_pine_4);
		registerTemplate(25, green_skyroot_pine_5);
		registerTemplate(26, dark_blue_skyroot_tree_1);
		registerTemplate(27, dark_blue_skyroot_tree_2);
		registerTemplate(28, dark_blue_skyroot_tree_3);
		registerTemplate(29, skyroot_moa_nest_tree_1);
		registerTemplate(30, skyroot_moa_nest_1);
		registerTemplate(31, skyroot_moa_nest_2);
		registerTemplate(32, aether_portal);
		registerTemplate(35, aether_portal_for_world);
	}

	private static void registerTemplate(final int id, final TemplateDefinition def)
	{
		AetherCore.PROXY.content().templates().register(id, def);
	}

}

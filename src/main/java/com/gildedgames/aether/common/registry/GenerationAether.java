package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.registry.content.TemplatesAether;
import com.gildedgames.aether.common.world.GenUtil;
import com.gildedgames.aether.common.world.dimensions.aether.features.*;
import com.gildedgames.aether.common.world.dimensions.aether.features.aerclouds.WorldGenAercloud;
import com.gildedgames.aether.common.world.gen.templates.conditions.*;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenerationAether
{
	
	public static WorldGenTemplate blue_skyroot_tree_1, blue_skyroot_tree_2, blue_skyroot_tree_3;

	public static WorldGenTemplate green_skyroot_tree_1, green_skyroot_tree_2, green_skyroot_tree_3;

	public static WorldGenTemplate green_skyroot_oak_1, green_skyroot_oak_2;

	public static WorldGenTemplate golden_oak_1, golden_oak_2;

	public static WorldGenTemplate green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3, green_skyroot_windswept_4, green_skyroot_windswept_5, green_skyroot_windswept_6, green_skyroot_windswept_7;

	public static WorldGenTemplate large_green_skyroot_pine_1, large_green_skyroot_pine_2;

	public static WorldGenTemplate green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4, green_skyroot_pine_5;

	public static WorldGenTemplate dark_blue_skyroot_tree_1, dark_blue_skyroot_tree_2, dark_blue_skyroot_tree_3;

	public static WorldGenTemplate dark_blue_skyroot_oak_1, dark_blue_skyroot_oak_2;

	public static WorldGenTemplate labyrinth_ruins_1, labyrinth_ruins_2, labyrinth_ruins_3, labyrinth_ruins_4, labyrinth_ruins_5;

	public static WorldGenTemplate skyroot_moa_nest_tree_1, skyroot_moa_nest_1, skyroot_moa_nest_2;

	public static WorldGenTemplate labyrinth_entrance_1, labyrinth_entrance_underground_1;

	public static WorldGenTemplate aether_portal_for_world, aether_portal, nether_portal, end_portal;

	public static WorldGenTemplate mysterious_henge;

	public static WorldGenTemplateGroup green_skyroot_windswept, labyrinth_ruins;

	public static WorldGenTemplateGroup large_green_skyroot_pine, green_skyroot_pine, green_skyroot_small_pine;

	public static WorldGenTemplateGroup blue_skyroot_tree, green_skyroot_tree, golden_oak, green_skyroot_oak;

	public static WorldGenTemplateGroup dark_blue_skyroot_tree, dark_blue_skyroot_oak;

	public static WorldGenTemplateGroup skyroot_moa_nest, labyrinth_entrance;

	public static WorldGenFloorPlacer short_aether_grass, aether_grass, long_aether_grass, skyroot_twigs, holystone_rocks;

	public static WorldGenAercloud green_aercloud, golden_aercloud, storm_aercloud;

	public static WorldGenTemplate kura_tree_1, kura_tree_2, kura_tree_3, kura_tree_4, kura_tree_5;

	public static WorldGenTemplate large_kura_tree_1;

	public static WorldGenTemplate kura_bush_1, kura_bush_2, kura_bush_3, kura_bush_4;

	public static WorldGenTemplateGroup kura_tree, large_kura_tree, kura_bush;

	private GenerationAether()
	{

	}

	public static void init()
	{
		blue_skyroot_tree_1 = new WorldGenTemplate(TemplatesAether.blue_skyroot_tree_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		blue_skyroot_tree_2 = new WorldGenTemplate(TemplatesAether.blue_skyroot_tree_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		blue_skyroot_tree_3 = new WorldGenTemplate(TemplatesAether.blue_skyroot_tree_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		green_skyroot_tree_1 = new WorldGenTemplate(TemplatesAether.green_skyroot_tree_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_tree_2 = new WorldGenTemplate(TemplatesAether.green_skyroot_tree_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_tree_3 = new WorldGenTemplate(TemplatesAether.green_skyroot_tree_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		green_skyroot_oak_1 = new WorldGenTemplate(TemplatesAether.green_skyroot_oak_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_oak_2 = new WorldGenTemplate(TemplatesAether.green_skyroot_oak_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		golden_oak_1 = new WorldGenTemplate(TemplatesAether.golden_oak_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		golden_oak_2 = new WorldGenTemplate(TemplatesAether.golden_oak_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		dark_blue_skyroot_oak_1 = new WorldGenTemplate(TemplatesAether.dark_blue_skyroot_oak_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		dark_blue_skyroot_oak_2 = new WorldGenTemplate(TemplatesAether.dark_blue_skyroot_oak_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		WorldGenTemplate.CenterOffsetProcessor oak_center = rotation ->
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

		golden_oak_1.setCenterOffsetProcessor(oak_center);
		golden_oak_2.setCenterOffsetProcessor(oak_center);
		green_skyroot_oak_1.setCenterOffsetProcessor(oak_center);
		green_skyroot_oak_2.setCenterOffsetProcessor(oak_center);
		dark_blue_skyroot_oak_1.setCenterOffsetProcessor(oak_center);
		dark_blue_skyroot_oak_2.setCenterOffsetProcessor(oak_center);

		green_skyroot_windswept_1 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_2 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_3 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_4 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_4, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_5 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_5, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_6 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_6, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		green_skyroot_windswept_7 = new WorldGenTemplate(TemplatesAether.green_skyroot_windswept_7, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);

		large_green_skyroot_pine_1 = new WorldGenTemplate(TemplatesAether.large_green_skyroot_pine_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		large_green_skyroot_pine_2 = new WorldGenTemplate(TemplatesAether.large_green_skyroot_pine_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		green_skyroot_pine_1 = new WorldGenTemplate(TemplatesAether.green_skyroot_pine_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_pine_2 = new WorldGenTemplate(TemplatesAether.green_skyroot_pine_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_pine_3 = new WorldGenTemplate(TemplatesAether.green_skyroot_pine_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_pine_4 = new WorldGenTemplate(TemplatesAether.green_skyroot_pine_4, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		green_skyroot_pine_5 = new WorldGenTemplate(TemplatesAether.green_skyroot_pine_5, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		dark_blue_skyroot_tree_1 = new WorldGenTemplate(TemplatesAether.dark_blue_skyroot_tree_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		dark_blue_skyroot_tree_2 = new WorldGenTemplate(TemplatesAether.dark_blue_skyroot_tree_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		dark_blue_skyroot_tree_3 = new WorldGenTemplate(TemplatesAether.dark_blue_skyroot_tree_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		labyrinth_ruins_1 = new WorldGenTemplate(TemplatesAether.labyrinth_ruins_1, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);
		labyrinth_ruins_2 = new WorldGenTemplate(TemplatesAether.labyrinth_ruins_2, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);
		labyrinth_ruins_3 = new WorldGenTemplate(TemplatesAether.labyrinth_ruins_3, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);
		labyrinth_ruins_4 = new WorldGenTemplate(TemplatesAether.labyrinth_ruins_4, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);
		labyrinth_ruins_5 = new WorldGenTemplate(TemplatesAether.labyrinth_ruins_5, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);

		labyrinth_entrance_1 = new WorldGenDungeonEntrance(TemplatesAether.labyrinth_entrance_1, new BlockPos(4, 2, 4), TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		labyrinth_entrance_underground_1 = new WorldGenDungeonEntrance(TemplatesAether.labyrinth_entrance_underground_1, new BlockPos(8, 1, 2), new UndergroundEntrancePlacementCondition(), new UndergroundPlacementCondition());

		skyroot_moa_nest_tree_1 = new WorldGenMoaNest(TemplatesAether.skyroot_moa_nest_tree_1, new BlockPos(4, 5, 4), TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE);
		skyroot_moa_nest_1 = new WorldGenMoaNest(TemplatesAether.skyroot_moa_nest_1, new BlockPos(2, 0, 2), TemplateConditions.INSIDE_GROUND, TemplateConditions.REPLACEABLE_GROUND);
		skyroot_moa_nest_2 = new WorldGenMoaNest(TemplatesAether.skyroot_moa_nest_2, new BlockPos(3, 0, 3), TemplateConditions.INSIDE_GROUND, TemplateConditions.REPLACEABLE_GROUND);
		
		aether_portal = new WorldGenTemplate(TemplatesAether.aether_portal, TemplateConditions.REPLACEABLE);
		nether_portal = new WorldGenTemplate(TemplatesAether.nether_portal, TemplateConditions.REPLACEABLE);
		end_portal = new WorldGenTemplate(TemplatesAether.end_portal, TemplateConditions.REPLACEABLE);

		aether_portal_for_world = new WorldGenTemplate(TemplatesAether.aether_portal, TemplateConditions.FLAT_GROUND, TemplateConditions.IGNORE_QUICKSOIL, TemplateConditions.REPLACEABLE_GROUND);

		mysterious_henge = new WorldGenTemplate(TemplatesAether.mysterious_henge, TemplateConditions.INSIDE_GROUND, TemplateConditions.REPLACEABLE_GROUND)
		{
			private final BlockPos offset = new BlockPos(5.5, 2, 5.5);

			@Override
			public void postGenerate(World world, Random random, BlockPos pos, Rotation rotation)
			{
				EntityEdison edison = new EntityEdison(world);

				BlockPos spawnAt = GenUtil.rotate(pos, pos.add(this.offset), rotation);

				edison.setPositionAndUpdate(spawnAt.getX(), spawnAt.getY(), spawnAt.getZ());

				world.spawnEntityInWorld(edison);
			}

		};

		mysterious_henge.setRandomRotation(false);

		kura_tree_1 = new WorldGenTemplate(TemplatesAether.kura_tree_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		kura_tree_2 = new WorldGenTemplate(TemplatesAether.kura_tree_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		kura_tree_3 = new WorldGenTemplate(TemplatesAether.kura_tree_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		kura_tree_4 = new WorldGenTemplate(TemplatesAether.kura_tree_4, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);
		kura_tree_5 = new WorldGenTemplate(TemplatesAether.kura_tree_5, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE);

		large_kura_tree_1 = new WorldGenTemplate(TemplatesAether.large_kura_tree_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);

		kura_bush_1 = new WorldGenTemplate(TemplatesAether.kura_bush_1, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		kura_bush_2 = new WorldGenTemplate(TemplatesAether.kura_bush_2, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		kura_bush_3 = new WorldGenTemplate(TemplatesAether.kura_bush_3, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);
		kura_bush_4 = new WorldGenTemplate(TemplatesAether.kura_bush_4, TemplateConditions.FLAT_GROUND, TemplateConditions.ON_SOIL, TemplateConditions.REPLACEABLE_CANOPY);

		blue_skyroot_tree = new WorldGenTemplateGroup(blue_skyroot_tree_1, blue_skyroot_tree_2, blue_skyroot_tree_3);
		green_skyroot_tree = new WorldGenTemplateGroup(green_skyroot_tree_1, green_skyroot_tree_2, green_skyroot_tree_3);
		golden_oak = new WorldGenTemplateGroup(golden_oak_1, golden_oak_2);
		green_skyroot_oak = new WorldGenTemplateGroup(green_skyroot_oak_1, green_skyroot_oak_2);
		green_skyroot_windswept = new WorldGenTemplateGroup(green_skyroot_windswept_1, green_skyroot_windswept_2, green_skyroot_windswept_3, green_skyroot_windswept_4, green_skyroot_windswept_5, green_skyroot_windswept_6, green_skyroot_windswept_7);
		large_green_skyroot_pine = new WorldGenTemplateGroup(large_green_skyroot_pine_1, large_green_skyroot_pine_2);
		green_skyroot_pine = new WorldGenTemplateGroup(green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3, green_skyroot_pine_4, green_skyroot_pine_5);
		green_skyroot_small_pine = new WorldGenTemplateGroup(green_skyroot_pine_1, green_skyroot_pine_2, green_skyroot_pine_3);
		dark_blue_skyroot_tree = new WorldGenTemplateGroup(dark_blue_skyroot_tree_1, dark_blue_skyroot_tree_2, dark_blue_skyroot_tree_3);
		dark_blue_skyroot_oak = new WorldGenTemplateGroup(dark_blue_skyroot_oak_1, dark_blue_skyroot_oak_2);
		labyrinth_ruins = new WorldGenTemplateGroup(labyrinth_ruins_1, labyrinth_ruins_2, labyrinth_ruins_3, labyrinth_ruins_4, labyrinth_ruins_5);
		skyroot_moa_nest = new WorldGenTemplateGroup(skyroot_moa_nest_1, skyroot_moa_nest_2);
		labyrinth_entrance = new WorldGenTemplateGroup(labyrinth_entrance_1);
		kura_tree = new WorldGenTemplateGroup(kura_tree_1, kura_tree_2, kura_tree_3, kura_tree_4, kura_tree_5);
		large_kura_tree = new WorldGenTemplateGroup(large_kura_tree_1);
		kura_bush = new WorldGenTemplateGroup(kura_bush_1, kura_bush_2, kura_bush_3, kura_bush_4);

		short_aether_grass = new WorldGenFloorPlacer(BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.SHORT));
		aether_grass = new WorldGenFloorPlacer(BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.NORMAL));
		long_aether_grass = new WorldGenFloorPlacer(BlocksAether.tall_aether_grass.getDefaultState().withProperty(BlockTallAetherGrass.PROPERTY_VARIANT, BlockTallAetherGrass.LONG));

		skyroot_twigs = new WorldGenFloorPlacer(4, BlocksAether.skyroot_twigs.getDefaultState(), BlocksAether.skyroot_twigs.getDefaultState(), BlocksAether.holystone_rock.getDefaultState());
		holystone_rocks = new WorldGenFloorPlacer(BlocksAether.holystone_rock.getDefaultState());

		holystone_rocks.getStatesCanPlaceOn().add(BlocksAether.holystone.getDefaultState());

		green_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.GREEN_AERCLOUD), 4, false);
		golden_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.GOLDEN_AERCLOUD), 4, false);
		storm_aercloud = new WorldGenAercloud(BlocksAether.aercloud.getAercloudState(BlockAercloud.STORM_AERCLOUD), 16, false);
	}

}

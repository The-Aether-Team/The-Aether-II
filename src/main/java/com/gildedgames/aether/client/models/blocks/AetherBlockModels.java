package com.gildedgames.aether.client.models.blocks;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import com.gildedgames.aether.common.blocks.util.BlockSkyrootMinable;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.LinkedHashMap;

public class AetherBlockModels
{
	public static void preInit()
	{
		registerStateMappers();
	}

	/**
	 * Creates the state mappers for certain blockstates.
	 */
	private static void registerStateMappers()
	{
		StateMap leavesMapper = new StateMap.Builder().addPropertiesToIgnore(BlockAetherLeaves.PROPERTY_CHECK_DECAY, BlockAetherLeaves.PROPERTY_DECAYABLE).build();

		ModelLoader.setCustomStateMapper(BlocksAether.blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.green_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.dark_blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.golden_oak_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.purple_crystal_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.purple_fruit_leaves, leavesMapper);

		StateMap skyrootMinableMapper = new StateMap.Builder().addPropertiesToIgnore(BlockSkyrootMinable.PROPERTY_WAS_PLACED).build();

		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_log, skyrootMinableMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.golden_oak_log, skyrootMinableMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.aether_grass, skyrootMinableMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.aether_dirt, skyrootMinableMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.holystone, skyrootMinableMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.quicksoil, skyrootMinableMapper);

		ModelLoader.setCustomStateMapper(BlocksAether.aether_sapling, new StateMap.Builder().addPropertiesToIgnore(BlockAetherSapling.PROPERTY_STAGE).build());

		ModelLoader.setCustomStateMapper(BlocksAether.aercloud, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.PROPERTY_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.PROPERTY_FACING);
				}

				ResourceLocation resource = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resource, this.getPropertyString(mappings));
			}
		});

		ModelLoader.setCustomStateMapper(BlocksAether.orange_tree, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if ((Boolean) state.getValue(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK))
				{
					if ((Integer) state.getValue(BlockOrangeTree.PROPERTY_STAGE) < 3)
					{
						mappings.remove(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK);
						mappings.remove(BlockOrangeTree.PROPERTY_STAGE);
					}
				}

				ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resourceLocation, this.getPropertyString(mappings));
			}
		});
	}
}

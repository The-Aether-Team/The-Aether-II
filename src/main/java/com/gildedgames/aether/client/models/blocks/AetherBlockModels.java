package com.gildedgames.aether.client.models.blocks;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
import com.gildedgames.aether.common.blocks.containers.BlockAltar;
import com.gildedgames.aether.common.blocks.containers.BlockIcestoneCooler;
import com.gildedgames.aether.common.blocks.containers.BlockMasonryBench;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBrettlPlant;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import com.gildedgames.aether.common.blocks.natural.plants.BlockValkyrieGrass;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.util.BlockCustomDoor;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedHashMap;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class AetherBlockModels
{
	@SubscribeEvent()
	public static void onModelRegistryReady(final ModelRegistryEvent event)
	{
		registerStateMappers();
	}

	private static void registerStateMappers()
	{
		final StateMap leavesMapper = new StateMap.Builder().ignore(BlockAetherLeaves.PROPERTY_CHECK_DECAY, BlockAetherLeaves.PROPERTY_DECAYABLE).build();

		ModelLoader.setCustomStateMapper(BlocksAether.therawood_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.green_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.dark_blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.amberoot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.blue_dark_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.green_dark_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.dark_blue_dark_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.blue_light_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.green_light_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.dark_blue_light_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.mutant_tree_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.mutant_tree_leaves_decorated, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.highlands_bush, leavesMapper);

		ModelLoader.setCustomStateMapper(BlocksAether.aether_skyroot_sapling, new StateMap.Builder().ignore(BlockAetherSapling.PROPERTY_STAGE).build());
		ModelLoader.setCustomStateMapper(BlocksAether.aether_unique_sapling, new StateMap.Builder().ignore(BlockAetherSapling.PROPERTY_STAGE).build());
		ModelLoader.setCustomStateMapper(BlocksAether.aether_wisproot_sapling, new StateMap.Builder().ignore(BlockAetherSapling.PROPERTY_STAGE).build());
		ModelLoader.setCustomStateMapper(BlocksAether.aether_greatroot_sapling, new StateMap.Builder().ignore(BlockAetherSapling.PROPERTY_STAGE).build());

		ModelLoader.setCustomStateMapper(BlocksAether.tall_aether_grass, new StateMap.Builder().ignore().build());

		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_door, new StateMap.Builder().ignore(BlockCustomDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(BlocksAether.secret_skyroot_door, new StateMap.Builder().ignore(BlockCustomDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(BlocksAether.arkenium_door, new StateMap.Builder().ignore(BlockCustomDoor.POWERED).build());

		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_chest, new StateMap.Builder().ignore(BlockChest.FACING).build());

		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_log_wall, new StateMap.Builder().ignore(BlockSkyrootWall.PROPERTY_GENERATED).build());

		ModelLoader.setCustomStateMapper(BlocksAether.aercloud, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(final IBlockState state)
			{
				final LinkedHashMap<IProperty<?>, Comparable<?>> mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.PROPERTY_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.PROPERTY_FACING);
				}

				final ResourceLocation resource = Block.REGISTRY.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resource, this.getPropertyString(mappings));
			}
		});

		ModelLoader.setCustomStateMapper(BlocksAether.orange_tree, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(final IBlockState state)
			{
				final LinkedHashMap<IProperty<?>, Comparable<?>> mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK))
				{
					if (state.getValue(BlockOrangeTree.PROPERTY_STAGE) < 3)
					{
						mappings.remove(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK);
						mappings.remove(BlockOrangeTree.PROPERTY_STAGE);
					}
				}

				final ResourceLocation resourceLocation = Block.REGISTRY.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resourceLocation, this.getPropertyString(mappings));
			}
		});

		ModelLoader.setCustomStateMapper(BlocksAether.valkyrie_grass, new StateMap.Builder().ignore(BlockValkyrieGrass.PROPERTY_HARVESTABLE).build());
		ModelLoader.setCustomStateMapper(BlocksAether.brettl_plant,
				new StateMap.Builder().ignore(BlockBrettlPlant.PROPERTY_HARVESTABLE).ignore(BlockBrettlPlant.PROPERTY_AGE).build());

		ModelLoader.setCustomStateMapper(BlocksAether.altar, new StateMap.Builder().ignore(BlockAltar.PROPERTY_FACING).build());
		ModelLoader.setCustomStateMapper(BlocksAether.icestone_cooler, new StateMap.Builder().ignore(BlockIcestoneCooler.PROPERTY_FACING).build());
		ModelLoader.setCustomStateMapper(BlocksAether.masonry_bench, new StateMap.Builder().ignore(BlockMasonryBench.PROPERTY_FACING).build());
		ModelLoader.setCustomStateMapper(BlocksAether.aether_teleporter, new StateMap.Builder().ignore(BlockAltar.PROPERTY_FACING).build());

		ModelLoader.setCustomStateMapper(BlocksAether.standing_skyroot_sign, new StateMap.Builder().ignore(BlockStandingSkyrootSign.ROTATION).build());
		ModelLoader.setCustomStateMapper(BlocksAether.wall_skyroot_sign, new StateMap.Builder().ignore(BlockWallSkyrootSign.FACING).build());
		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(BlocksAether.wisproot_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(BlocksAether.greatroot_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(BlocksAether.skyroot_bed, new StateMap.Builder().ignore(BlockBed.OCCUPIED).build());

		ModelLoader.setCustomStateMapper(BlocksAether.moa_egg, new StateMap.Builder().ignore().build());
	}
}

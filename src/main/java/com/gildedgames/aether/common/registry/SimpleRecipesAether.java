package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.*;
import com.gildedgames.aether.common.recipes.simple.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class SimpleRecipesAether
{

	private static int nextIdMasonry;

	private SimpleRecipesAether()
	{

	}

	private static void registerMasonryRecipes()
	{
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.HEADSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.holystone_pillar), new ItemStack(BlocksAether.holystone_brick));

		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.HEADSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_brick));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_pillar),
				new ItemStack(BlocksAether.holystone_brick));

		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick));
		addMasonry(new ItemStack(BlocksAether.agiosite_pillar), new ItemStack(BlocksAether.agiosite_brick));

		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_PLANKS.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_BEAM.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_PLANKS.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_BEAM.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.FLOORBOARDS.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES_SMALL.getMeta()),
				new ItemStack(BlocksAether.skyroot_planks));
		addMasonry(new ItemStack(BlocksAether.skyroot_beam), new ItemStack(BlocksAether.skyroot_planks));

		addMasonry(new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass));
		addMasonry(new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass));

		addMasonry(new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass));
		addMasonry(new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass));

		addMasonry(new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass));
		addMasonry(new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass));

		addMasonry(new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 1, BlockRockGlassPaneDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass_pane));
		addMasonry(new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 1, BlockRockGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass_pane));

		addMasonry(new ItemStack(BlocksAether.scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass_pane));
		addMasonry(new ItemStack(BlocksAether.scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass_pane));

		addMasonry(new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.ARKENIUM_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass_pane));
		addMasonry(new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass_pane));
	}

	public static void postInit()
	{
		AetherAPI.content().masonry().clearAllRecipes();

		registerMasonryRecipes();

		AetherAPI.content().masonry().finalizeRecipes();
	}

	private static void addMasonry(ItemStack result, Object... required)
	{
		AetherAPI.content().masonry().registerRecipe(nextIdMasonry++, new SimpleRecipe(result, required));
	}

}

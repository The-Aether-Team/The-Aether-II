package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIRecipeProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class AetherIIRecipeData extends AetherIIRecipeProvider {
    public AetherIIRecipeData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        // Blocks
        // Dirt
        this.ambrosiumEnchanting(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_GRASS_BLOCK.get()).save(consumer, this.name("ambrosium_enchant_aether_grass_to_enchanted_aether_grass"));
        this.swetGelConversion(Blocks.GRASS_BLOCK, Blocks.DIRT).save(consumer, this.name("swet_ball_dirt_to_grass"));
        this.swetGelConversion(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get()).save(consumer, this.name("swet_ball_aether_dirt_to_aether_grass"));
        this.swetGelConversionTag(Blocks.MYCELIUM, Blocks.DIRT, AetherIITags.Biomes.MYCELIUM_CONVERSION).save(consumer, this.name("swet_ball_dirt_to_mycelium"));
        this.swetGelConversionTag(Blocks.PODZOL, Blocks.GRASS_BLOCK, AetherIITags.Biomes.PODZOL_CONVERSION).save(consumer, this.name("swet_ball_grass_to_podzol"));
        this.swetGelConversionTag(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, AetherIITags.Biomes.CRIMSON_NYLIUM_CONVERSION).save(consumer, this.name("swet_ball_netherrack_to_crimson_nylium"));
        this.swetGelConversionTag(Blocks.WARPED_NYLIUM, Blocks.NETHERRACK, AetherIITags.Biomes.WARPED_NYLIUM_CONVERSION).save(consumer, this.name("swet_ball_netherrack_to_warped_nylium"));

        // Highfields
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherIIBlocks.HOLYSTONE.get())
                .requires(Blocks.VINE)
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get()))
                .save(consumer, this.name("mossy_holystone_with_vine"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherIIBlocks.HOLYSTONE.get())
                .requires(Blocks.MOSS_BLOCK)
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get()))
                .save(consumer, this.name("mossy_holystone_with_moss"));

        // Arctic
        twoByTwoPacker(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARCTIC_SNOW_BLOCK, AetherIIItems.ARCTIC_SNOWBALL);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.ARCTIC_SNOW, 6)
                .define('#', AetherIIBlocks.ARCTIC_SNOW_BLOCK)
                .pattern("###")
                .unlockedBy("has_snowball", has(AetherIIItems.ARCTIC_SNOWBALL))
                .save(consumer);

        this.icestoneFreezable(Blocks.ICE, Blocks.WATER).save(consumer, this.name("icestone_freeze_water"));
        this.icestoneFreezableTag(AetherIIBlocks.ARCTIC_ICE.get(), Blocks.WATER, AetherIITags.Biomes.ARCTIC_ICE_FREEZING).save(consumer, this.name("icestone_freeze_water_to_arctic_ice"));
        this.icestoneFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(consumer, this.name("icestone_freeze_lava"));

        // Moa Nest
        twoByTwoPacker(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WOVEN_SKYROOT_STICKS, AetherIIItems.SKYROOT_STICK.get());

        // Logs
        woodFromLogs(consumer, AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        woodFromLogs(consumer, AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());

        // Leaf Pile
        leafPile(consumer, AetherIIBlocks.SKYROOT_LEAF_PILE, AetherIIBlocks.SKYROOT_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.SKYPLANE_LEAF_PILE, AetherIIBlocks.SKYPLANE_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.SKYBIRCH_LEAF_PILE, AetherIIBlocks.SKYBIRCH_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.SKYPINE_LEAF_PILE, AetherIIBlocks.SKYPINE_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.WISPROOT_LEAF_PILE, AetherIIBlocks.WISPROOT_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.WISPTOP_LEAF_PILE, AetherIIBlocks.WISPTOP_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.GREATROOT_LEAF_PILE, AetherIIBlocks.GREATROOT_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.GREATOAK_LEAF_PILE, AetherIIBlocks.GREATOAK_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.GREATBOA_LEAF_PILE, AetherIIBlocks.GREATBOA_LEAVES.get());
        leafPile(consumer, AetherIIBlocks.AMBEROOT_LEAF_PILE, AetherIIBlocks.AMBEROOT_LEAVES.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherIIBlocks.SKYROOT_TWIG.get())
                .define('#', AetherIIItems.SKYROOT_STICK)
                .pattern(" #")
                .pattern("# ")
                .unlockedBy("has_stick", has(AetherIIItems.SKYROOT_STICK))
                .save(consumer);

        // Skyroot Planks
        planksFromLog(consumer, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_SKYROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.SKYROOT_ARTISANRY_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.SKYROOT_ARTISANRY_BLOCKS))
                .save(consumer, name("skyroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.SKYROOT_FENCE, AetherIIBlocks.SKYROOT_PLANKS).save(consumer);
        this.fenceGate(AetherIIBlocks.SKYROOT_FENCE_GATE, AetherIIBlocks.SKYROOT_PLANKS).save(consumer);
        doorBuilder(AetherIIBlocks.SKYROOT_DOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(AetherIIBlocks.SKYROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        buttonBuilder(AetherIIBlocks.SKYROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_button").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS, AetherIIBlocks.SKYROOT_PLANKS).group("wooden_stairs").save(consumer);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        // Skyroot Artisanry Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FLOORBOARDS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_HIGHLIGHT.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SHINGLES.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        
        // Greatroot Planks
        planksFromLog(consumer, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_GREATROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.GREATROOT_ARTISANRY_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.GREATROOT_ARTISANRY_BLOCKS))
                .save(consumer, name("greatroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.GREATROOT_FENCE, AetherIIBlocks.GREATROOT_PLANKS).save(consumer);
        this.fenceGate(AetherIIBlocks.GREATROOT_FENCE_GATE, AetherIIBlocks.GREATROOT_PLANKS).save(consumer);
        doorBuilder(AetherIIBlocks.GREATROOT_DOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(AetherIIBlocks.GREATROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        buttonBuilder(AetherIIBlocks.GREATROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_button").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS, AetherIIBlocks.GREATROOT_PLANKS).group("wooden_stairs").save(consumer);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .save(consumer);

        // Greatroot Artisanry Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_FLOORBOARDS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_HIGHLIGHT.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SHINGLES.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());

        // Wisproot Planks
        planksFromLog(consumer, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_WISPROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.WISPROOT_ARTISANRY_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.WISPROOT_ARTISANRY_BLOCKS))
                .save(consumer, name("wisproot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.WISPROOT_FENCE, AetherIIBlocks.WISPROOT_PLANKS).save(consumer);
        this.fenceGate(AetherIIBlocks.WISPROOT_FENCE_GATE, AetherIIBlocks.WISPROOT_PLANKS).save(consumer);
        doorBuilder(AetherIIBlocks.WISPROOT_DOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(AetherIIBlocks.WISPROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        buttonBuilder(AetherIIBlocks.WISPROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_button").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS, AetherIIBlocks.WISPROOT_PLANKS).group("wooden_stairs").save(consumer);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .save(consumer);

        // Wisproot Artisanry Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_FLOORBOARDS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_HIGHLIGHT.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SHINGLES.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TOP_PLANKS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BASE_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TOP_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());

        // Holystone
        this.stairs(AetherIIBlocks.HOLYSTONE_STAIRS, AetherIIBlocks.HOLYSTONE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get());
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(consumer);
        buttonBuilder(AetherIIBlocks.HOLYSTONE_BUTTON.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(consumer);

        // Mossy Holystone
        this.stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, AetherIIBlocks.MOSSY_HOLYSTONE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());

        // Irradiated Holystone
        this.stairs(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, AetherIIBlocks.IRRADIATED_HOLYSTONE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());

        // Holystone Bricks
        polished(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.HOLYSTONE_ARTISANRY_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.HOLYSTONE_ARTISANRY_BLOCKS))
                .save(consumer, name("holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.HOLYSTONE_BRICKS).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());

        // Holystone Artisanry  Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_HEADSTONE.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_KEYSTONE.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());

        // Faded Holystone Bricks
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.HOLYSTONE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), 0.1F, 200).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_BRICKS.get()), has(AetherIIBlocks.HOLYSTONE_BRICKS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.HOLYSTONE_ARTISANRY_BLOCKS)
                .unlockedBy("has_faded_holystone_blocks", has(AetherIITags.Items.FADED_HOLYSTONE_ARTISANRY_BLOCKS))
                .save(consumer, name("faded_holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());

        // Faded Holystone Artisanry Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());

        // Undershale
        this.stairs(AetherIIBlocks.UNDERSHALE_STAIRS, AetherIIBlocks.UNDERSHALE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get());

        // Agiosite
        this.stairs(AetherIIBlocks.AGIOSITE_STAIRS, AetherIIBlocks.AGIOSITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get());

        // Agiosite Bricks
        polished(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.AGIOSITE_ARTISANRY_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.AGIOSITE_ARTISANRY_BLOCKS))
                .save(consumer, name("agiosite_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, AetherIIBlocks.AGIOSITE_BRICKS).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());

        // Agiosite Artisanry Bench
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_FLAGSTONES.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_KEYSTONE.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.artisanryRecipe(consumer, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());

        // Glass
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.QUICKSOIL.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.QUICKSOIL_GLASS.get(), 0.1F, 200).unlockedBy("has_quicksoil", has(AetherIIBlocks.QUICKSOIL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), 16).define('#', AetherIIBlocks.QUICKSOIL_GLASS.get()).pattern("###").pattern("###").unlockedBy("has_quicksoil_glass", has(AetherIIBlocks.QUICKSOIL_GLASS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), 16).define('#', AetherIIBlocks.CRUDE_SCATTERGLASS.get()).pattern("###").pattern("###").unlockedBy("has_crude_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.CRUDE_SCATTERGLASS.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SCATTERGLASS.get(), 0.1F, 200).unlockedBy("has_crude_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS_PANE.get(), 16).define('#', AetherIIBlocks.SCATTERGLASS.get()).pattern("###").pattern("###").unlockedBy("has_scatterglass", has(AetherIIBlocks.SCATTERGLASS.get())).save(consumer);

        // Wool
        carpet(consumer, AetherIIBlocks.CLOUDWOOL_CARPET, AetherIIBlocks.CLOUDWOOL.get());

        // Mineral Blocks
        oreBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, AetherIIItems.AMBROSIUM_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AMBROSIUM_BLOCK, "ambrosium_shard_from_ambrosium_block", "ambrosium_shard");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, AetherIIItems.ZANITE_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ZANITE_BLOCK, "zanite_gemstone_from_zanite_block", "zanite_gemstone");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GRAVITITE_BLOCK, "gravitite_plate_from_gravitite_block", "gravitite_plate");

        // Utility
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.AMBROSIUM_TORCH.get(), 4)
                .define('A', AetherIIItems.AMBROSIUM_SHARD.get())
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .pattern("A")
                .pattern("/")
                .unlockedBy(getHasName(AetherIIItems.HOLYSTONE_PICKAXE.get()), has(AetherIIItems.HOLYSTONE_PICKAXE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .define('#', AetherIITags.Items.STONE_CRAFTING)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_FURNACE.get()), has(AetherIITags.Items.STONE_CRAFTING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.ARTISANRY_BENCH.get())
                .define('A', AetherIIItems.ARKENIUM_PLATE.get())
                .define('P', AetherIITags.Items.PLANKS_CRAFTING)
                .define('H', AetherIITags.Items.STONE_CRAFTING)
                .pattern("AAA")
                .pattern("PPP")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ARTISANRY_BENCH.get()), has(AetherIIItems.ARKENIUM_PLATE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_CHEST.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_CHEST.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_LADDER.get(), 3)
                .define('#', AetherIITags.Items.RODS_SKYROOT)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_LADDER.get()), has(AetherIITags.Items.RODS_SKYROOT))
                .save(consumer);

        // Bookshelves
        bookshelf(consumer, AetherIIBlocks.SKYROOT_BOOKSHELF, AetherIIBlocks.SKYROOT_PLANKS);
        bookshelf(consumer, AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS);

        // Items
        // Tools
        this.makePickaxeWithTag(AetherIIItems.SKYROOT_PICKAXE, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);
        this.makeAxeWithTag(AetherIIItems.SKYROOT_AXE, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);
        this.makeShovelWithTag(AetherIIItems.SKYROOT_SHOVEL, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);
        this.makeHoeWithTag(AetherIIItems.SKYROOT_TROWEL, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);

        this.makePickaxeWithTag(AetherIIItems.HOLYSTONE_PICKAXE, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);
        this.makeAxeWithTag(AetherIIItems.HOLYSTONE_AXE, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);
        this.makeShovelWithTag(AetherIIItems.HOLYSTONE_SHOVEL, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);
        this.makeHoeWithTag(AetherIIItems.HOLYSTONE_TROWEL, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);

        this.makePickaxeWithTag(AetherIIItems.ZANITE_PICKAXE, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);
        this.makeAxeWithTag(AetherIIItems.ZANITE_AXE, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);
        this.makeShovelWithTag(AetherIIItems.ZANITE_SHOVEL, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);
        this.makeHoeWithTag(AetherIIItems.ZANITE_TROWEL, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);

        this.makePickaxeWithTag(AetherIIItems.ARKENIUM_PICKAXE, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);
        this.makeAxeWithTag(AetherIIItems.ARKENIUM_AXE, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);
        this.makeShovelWithTag(AetherIIItems.ARKENIUM_SHOVEL, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);
        this.makeHoeWithTag(AetherIIItems.ARKENIUM_TROWEL, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);

        this.makePickaxeWithTag(AetherIIItems.GRAVITITE_PICKAXE, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);
        this.makeAxeWithTag(AetherIIItems.GRAVITITE_AXE, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);
        this.makeShovelWithTag(AetherIIItems.GRAVITITE_SHOVEL, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);
        this.makeHoeWithTag(AetherIIItems.GRAVITITE_TROWEL, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);

        // Weapons
        this.makeSwordWithTag(AetherIIItems.SKYROOT_SHORTSWORD, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);
        this.makeHammerWithTag(AetherIIItems.SKYROOT_HAMMER, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);
        this.makeSpearWithTag(AetherIIItems.SKYROOT_SPEAR, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(consumer);

        this.makeSwordWithTag(AetherIIItems.HOLYSTONE_SHORTSWORD, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);
        this.makeHammerWithTag(AetherIIItems.HOLYSTONE_HAMMER, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);
        this.makeSpearWithTag(AetherIIItems.HOLYSTONE_SPEAR, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(consumer);

        this.makeSwordWithTag(AetherIIItems.ZANITE_SHORTSWORD, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);
        this.makeHammerWithTag(AetherIIItems.ZANITE_HAMMER, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);
        this.makeSpearWithTag(AetherIIItems.ZANITE_SPEAR, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(consumer);

        this.makeSwordWithTag(AetherIIItems.ARKENIUM_SHORTSWORD, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);
        this.makeHammerWithTag(AetherIIItems.ARKENIUM_HAMMER, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);
        this.makeSpearWithTag(AetherIIItems.ARKENIUM_SPEAR, AetherIITags.Items.PLATES_ARKENIUM, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_arkenium").save(consumer);

        this.makeSwordWithTag(AetherIIItems.GRAVITITE_SHORTSWORD, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);
        this.makeHammerWithTag(AetherIIItems.GRAVITITE_HAMMER, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);
        this.makeSpearWithTag(AetherIIItems.GRAVITITE_SPEAR, AetherIITags.Items.PLATES_GRAVITITE, Ingredient.of(AetherIIItems.ARKENIUM_STRIP.get()), "has_gravitite").save(consumer);

        //Foods
        foodCooking(AetherIIItems.BURRUKAI_RIB_CUT, AetherIIItems.BURRUKAI_RIBS, 0.1F, consumer);
        foodCooking(AetherIIItems.KIRRID_LOIN, AetherIIItems.KIRRID_CUTLET, 0.1F, consumer);
        foodCooking(AetherIIItems.RAW_TAEGORE_MEAT, AetherIIItems.TAEGORE_STEAK, 0.1F, consumer);


        // Materials
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 4)
                .group("sticks")
                .define('#', AetherIITags.Items.CRAFTS_SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_planks", has(AetherIITags.Items.CRAFTS_SKYROOT_STICKS))
                .save(consumer, "skyroot_stick_from_planks");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 2)
                .group("sticks")
                .requires(AetherIIBlocks.SKYROOT_TWIG)
                .unlockedBy("has_twig", has(AetherIIBlocks.SKYROOT_TWIG))
                .save(consumer, "skyroot_stick_from_twig");
        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, this.name("ambrosium_shard_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, this.name("ambrosium_shard_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, this.name("zanite_gemstone_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, this.name("zanite_gemstone_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIBlocks.ARKENIUM_ORE.get(), 1.0F).save(consumer, this.name("arkenium_plate_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIBlocks.ARKENIUM_ORE.get(), 1.0F).save(consumer, this.name("arkenium_plate_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIItems.RAW_ARKENIUM.get(), 1.0F).save(consumer, this.name("arkenium_plate_from_smelting_raw"));
        this.blastingOreRecipe(AetherIIItems.ARKENIUM_PLATE.get(), AetherIIItems.RAW_ARKENIUM.get(), 1.0F).save(consumer, this.name("arkenium_plate_from_blasting_raw"));
        this.smeltingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIBlocks.GRAVITITE_ORE.get(), 1.0F).save(consumer, this.name("gravitite_plate_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIBlocks.GRAVITITE_ORE.get(), 1.0F).save(consumer, this.name("gravitite_plate_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIItems.RAW_GRAVITITE.get(), 1.0F).save(consumer, this.name("gravitite_plate_from_smelting_raw"));
        this.blastingOreRecipe(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIItems.RAW_GRAVITITE.get(), 1.0F).save(consumer, this.name("gravitite_plate_from_blasting_raw"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherIIItems.ARKENIUM_STRIP.get(), 4)
                .define('#', AetherIITags.Items.PLATES_ARKENIUM)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_arkenium", has(AetherIITags.Items.PLATES_ARKENIUM))
                .save(consumer);
    }
}

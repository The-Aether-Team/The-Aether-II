package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIRecipeProvider;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AetherIIRecipeData extends AetherIIRecipeProvider {
    public AetherIIRecipeData(RecipeOutput output, HolderLookup.Provider provider) {
        super(output, provider, AetherII.MODID);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);

        List<Item> dyes = List.of(
                Items.BLACK_DYE,
                Items.BLUE_DYE,
                Items.BROWN_DYE,
                Items.CYAN_DYE,
                Items.GRAY_DYE,
                Items.GREEN_DYE,
                Items.LIGHT_BLUE_DYE,
                Items.LIGHT_GRAY_DYE,
                Items.LIME_DYE,
                Items.MAGENTA_DYE,
                Items.ORANGE_DYE,
                Items.PINK_DYE,
                Items.PURPLE_DYE,
                Items.RED_DYE,
                Items.YELLOW_DYE,
                Items.WHITE_DYE
        );
        List<Item> wool = List.of(
                AetherIIBlocks.BLACK_CLOUDWOOL.asItem(),
                AetherIIBlocks.BLUE_CLOUDWOOL.asItem(),
                AetherIIBlocks.BROWN_CLOUDWOOL.asItem(),
                AetherIIBlocks.CYAN_CLOUDWOOL.asItem(),
                AetherIIBlocks.GRAY_CLOUDWOOL.asItem(),
                AetherIIBlocks.GREEN_CLOUDWOOL.asItem(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.asItem(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.asItem(),
                AetherIIBlocks.LIME_CLOUDWOOL.asItem(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL.asItem(),
                AetherIIBlocks.ORANGE_CLOUDWOOL.asItem(),
                AetherIIBlocks.PINK_CLOUDWOOL.asItem(),
                AetherIIBlocks.PURPLE_CLOUDWOOL.asItem(),
                AetherIIBlocks.RED_CLOUDWOOL.asItem(),
                AetherIIBlocks.YELLOW_CLOUDWOOL.asItem(),
                AetherIIBlocks.WHITE_CLOUDWOOL.asItem()
        );
        List<Item> carpet = List.of(
                AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.LIME_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.PINK_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.RED_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.asItem(),
                AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.asItem()
        );
        Map<Moa.FeatherColor, Item> featherDyes = Map.ofEntries(
                Map.entry(Moa.FeatherColor.BLACK, Items.BLACK_DYE),
                Map.entry(Moa.FeatherColor.BLOOMING_RED, Items.RED_DYE),
                Map.entry(Moa.FeatherColor.BLUE, Items.BLUE_DYE),
                Map.entry(Moa.FeatherColor.BROWN, Items.BROWN_DYE),
                Map.entry(Moa.FeatherColor.CLASSIC_BLACK, Items.BLACK_DYE),
                Map.entry(Moa.FeatherColor.CYAN, Items.CYAN_DYE),
                Map.entry(Moa.FeatherColor.GRAY, Items.GRAY_DYE),
                Map.entry(Moa.FeatherColor.GREEN, Items.GREEN_DYE),
                Map.entry(Moa.FeatherColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE),
                Map.entry(Moa.FeatherColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE),
                Map.entry(Moa.FeatherColor.LIME, Items.LIME_DYE),
                Map.entry(Moa.FeatherColor.MAGENTA, Items.MAGENTA_DYE),
                Map.entry(Moa.FeatherColor.ORANGE, Items.ORANGE_DYE),
                Map.entry(Moa.FeatherColor.PINK, Items.PINK_DYE),
                Map.entry(Moa.FeatherColor.PURPLE, Items.PURPLE_DYE),
                Map.entry(Moa.FeatherColor.RED, Items.RED_DYE),
                Map.entry(Moa.FeatherColor.WHITE, Items.WHITE_DYE),
                Map.entry(Moa.FeatherColor.YELLOW, Items.YELLOW_DYE)
        );
        
        // Blocks
        // Dirt
        this.ambrosiumEnchanting(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_GRASS_BLOCK.get()).save(this.output, this.name("ambrosium_enchant_aether_grass_to_enchanted_aether_grass"));
        this.swetGelConversion(Blocks.GRASS_BLOCK, Blocks.DIRT).save(this.output, this.name("swet_ball_dirt_to_grass"));
        this.swetGelConversion(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get()).save(this.output, this.name("swet_ball_aether_dirt_to_aether_grass"));
        this.swetGelConversionTag(Blocks.MYCELIUM, Blocks.DIRT, AetherIITags.Biomes.MYCELIUM_CONVERSION).save(this.output, this.name("swet_ball_dirt_to_mycelium"));
        this.swetGelConversionTag(Blocks.PODZOL, Blocks.GRASS_BLOCK, AetherIITags.Biomes.PODZOL_CONVERSION).save(this.output, this.name("swet_ball_grass_to_podzol"));
        this.swetGelConversionTag(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, AetherIITags.Biomes.CRIMSON_NYLIUM_CONVERSION).save(this.output, this.name("swet_ball_netherrack_to_crimson_nylium"));
        this.swetGelConversionTag(Blocks.WARPED_NYLIUM, Blocks.NETHERRACK, AetherIITags.Biomes.WARPED_NYLIUM_CONVERSION).save(this.output, this.name("swet_ball_netherrack_to_warped_nylium"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.COARSE_AETHER_DIRT, 4)
                .define('D', AetherIIBlocks.AETHER_DIRT)
                .define('G', AetherIIBlocks.SHIMMERING_SILT)
                .pattern("DG")
                .pattern("GD")
                .unlockedBy("has_silt", has(AetherIIBlocks.SHIMMERING_SILT))
                .save(this.output);

        // Underground
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNSTABLE_HOLYSTONE, 2)
                .define('S', AetherIIBlocks.HOLYSTONE)
                .define('R', AetherIIBlocks.HOLYSTONE_ROCK)
                .pattern("SR")
                .pattern("RS")
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE), has(AetherIIBlocks.HOLYSTONE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNSTABLE_UNDERSHALE, 2)
                .define('S', AetherIIBlocks.UNDERSHALE)
                .define('R', AetherIIBlocks.HOLYSTONE_ROCK)
                .pattern("SR")
                .pattern("RS")
                .unlockedBy(getHasName(AetherIIBlocks.UNDERSHALE), has(AetherIIBlocks.UNDERSHALE))
                .save(this.output);
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.CRUDE_SCATTERGLASS, AetherIIItems.SCATTERGLASS_SHARD);
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE, AetherIIBlocks.POINTED_HOLYSTONE);
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE, AetherIIBlocks.POINTED_ICHORITE);

        // Highfields
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherIIBlocks.HOLYSTONE.get())
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .unlockedBy(getHasName(AetherIIBlocks.BRYALINN_MOSS_VINES), has(AetherIIBlocks.BRYALINN_MOSS_VINES))
                .save(this.output, this.name("mossy_holystone_with_vine"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherIIBlocks.HOLYSTONE.get())
                .requires(AetherIIBlocks.BRYALINN_MOSS_BLOCK)
                .unlockedBy(getHasName(AetherIIBlocks.BRYALINN_MOSS_BLOCK), has(AetherIIBlocks.BRYALINN_MOSS_BLOCK))
                .save(this.output, this.name("mossy_holystone_with_moss"));
        this.carpet(AetherIIBlocks.BRYALINN_MOSS_CARPET, AetherIIBlocks.BRYALINN_MOSS_BLOCK.get());

        // Arctic
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARCTIC_SNOW_BLOCK, AetherIIItems.ARCTIC_SNOWBALL);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARCTIC_SNOW, 6)
                .define('#', AetherIIBlocks.ARCTIC_SNOW_BLOCK)
                .pattern("###")
                .unlockedBy("has_snowball", has(AetherIIItems.ARCTIC_SNOWBALL))
                .save(this.output);
        threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARCTIC_PACKED_ICE, AetherIIBlocks.ARCTIC_ICE);
        this.carpet(AetherIIBlocks.SHAYELINN_MOSS_CARPET, AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());

        this.icestoneFreezable(Blocks.ICE, Blocks.WATER).save(this.output, this.name("icestone_freeze_water"));
        this.icestoneFreezableTag(AetherIIBlocks.ARCTIC_ICE.get(), Blocks.WATER, AetherIITags.Biomes.ARCTIC_ICE).save(this.output, this.name("icestone_freeze_water_to_arctic_ice"));
        this.icestoneFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(this.output, this.name("icestone_freeze_lava"));

        // Irradiated
        this.carpet(AetherIIBlocks.AMBRELINN_MOSS_CARPET, AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get());

        // Moa Nest
        twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WOVEN_SKYROOT_STICKS, AetherIIItems.SKYROOT_STICK.get());

        // Logs
        woodFromLogs(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        woodFromLogs(AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        woodFromLogs(AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        woodFromLogs(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIBlocks.MOSSY_WISPROOT_LOG_END.get(), 1)
                .requires(AetherIIBlocks.WISPROOT_LOG)
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .unlockedBy("has_vines", has(AetherIIBlocks.BRYALINN_MOSS_VINES))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIBlocks.MOSSY_WISPROOT_LOG.get(), 1)
                .requires(AetherIIBlocks.WISPROOT_LOG)
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .unlockedBy("has_vines", has(AetherIIBlocks.BRYALINN_MOSS_VINES))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIBlocks.MOSSY_WISPROOT_WOOD.get(), 1)
                .requires(AetherIIBlocks.WISPROOT_WOOD)
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .requires(AetherIIBlocks.BRYALINN_MOSS_VINES)
                .unlockedBy("has_vines", has(AetherIIBlocks.BRYALINN_MOSS_VINES))
                .save(this.output);

        // Leaf Pile
        leafPile(getter, AetherIIBlocks.SKYROOT_LEAF_PILE, AetherIIBlocks.SKYROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.SKYPLANE_LEAF_PILE, AetherIIBlocks.SKYPLANE_LEAVES.get());
        leafPile(getter, AetherIIBlocks.SKYBIRCH_LEAF_PILE, AetherIIBlocks.SKYBIRCH_LEAVES.get());
        leafPile(getter, AetherIIBlocks.SKYPINE_LEAF_PILE, AetherIIBlocks.SKYPINE_LEAVES.get());
        leafPile(getter, AetherIIBlocks.WISPROOT_LEAF_PILE, AetherIIBlocks.WISPROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.WISPTOP_LEAF_PILE, AetherIIBlocks.WISPTOP_LEAVES.get());
        leafPile(getter, AetherIIBlocks.GREATROOT_LEAF_PILE, AetherIIBlocks.GREATROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.GREATOAK_LEAF_PILE, AetherIIBlocks.GREATOAK_LEAVES.get());
        leafPile(getter, AetherIIBlocks.GREATBOA_LEAF_PILE, AetherIIBlocks.GREATBOA_LEAVES.get());
        leafPile(getter, AetherIIBlocks.AMBEROOT_LEAF_PILE, AetherIIBlocks.AMBEROOT_LEAVES.get());

        leafPile(getter, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
        leafPile(getter, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.SKYROOT_TWIG.get())
                .define('#', AetherIIItems.SKYROOT_STICK)
                .pattern(" #")
                .pattern("# ")
                .unlockedBy("has_stick", has(AetherIIItems.SKYROOT_STICK))
                .save(this.output);

        // Skyroot Planks
        planksFromLog(AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_SKYROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS))
                .save(this.output, name("skyroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.SKYROOT_FENCE, AetherIIBlocks.SKYROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.SKYROOT_FENCE_GATE, AetherIIBlocks.SKYROOT_PLANKS).save(this.output);
        doorBuilder(AetherIIBlocks.SKYROOT_DOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_door").save(this.output);
        trapdoorBuilder(AetherIIBlocks.SKYROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        buttonBuilder(AetherIIBlocks.SKYROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_button").save(this.output);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS, AetherIIBlocks.SKYROOT_PLANKS).group("wooden_stairs").save(this.output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_SLAB, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .save(this.output);
        sign(getter, AetherIIBlocks.SKYROOT_SIGN.get(), AetherIIBlocks.SKYROOT_PLANKS);
        hangingSign(getter, AetherIIBlocks.SKYROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_STAIRS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SLAB.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), 2);

        // Skyroot Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FLOORBOARDS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_HIGHLIGHT.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SHINGLES.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BASE_PLANKS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TOP_PLANKS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BASE_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TOP_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BEAM.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_SKYROOT_DOOR.get(), AetherIIBlocks.SKYROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(), AetherIIBlocks.SKYROOT_TRAPDOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_BASE_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_TOP_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIIBlocks.SKYROOT_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_DOOR.get(), AetherIIBlocks.SECRET_SKYROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TRAPDOOR.get(), AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get());
        
        // Greatroot Planks
        planksFromLog(AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_GREATROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS))
                .save(this.output, name("greatroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.GREATROOT_FENCE, AetherIIBlocks.GREATROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.GREATROOT_FENCE_GATE, AetherIIBlocks.GREATROOT_PLANKS).save(this.output);
        doorBuilder(AetherIIBlocks.GREATROOT_DOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_door").save(this.output);
        trapdoorBuilder(AetherIIBlocks.GREATROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        buttonBuilder(AetherIIBlocks.GREATROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_button").save(this.output);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS, AetherIIBlocks.GREATROOT_PLANKS).group("wooden_stairs").save(this.output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .save(this.output);
        sign(getter, AetherIIBlocks.GREATROOT_SIGN.get(), AetherIIBlocks.GREATROOT_PLANKS);
        hangingSign(getter, AetherIIBlocks.GREATROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_STAIRS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SLAB.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), 2);

        // Greatroot Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_FLOORBOARDS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_HIGHLIGHT.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SHINGLES.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BASE_PLANKS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TOP_PLANKS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BASE_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TOP_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_BEAM.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_GREATROOT_DOOR.get(), AetherIIBlocks.GREATROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(), AetherIIBlocks.GREATROOT_TRAPDOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_BASE_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_TOP_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIIBlocks.GREATROOT_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_DOOR.get(), AetherIIBlocks.SECRET_GREATROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TRAPDOOR.get(), AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get());

        // Wisproot Planks
        planksFromLog(AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIITags.Items.CRAFTS_WISPROOT_PLANKS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS))
                .save(this.output, name("wisproot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.WISPROOT_FENCE, AetherIIBlocks.WISPROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.WISPROOT_FENCE_GATE, AetherIIBlocks.WISPROOT_PLANKS).save(this.output);
        doorBuilder(AetherIIBlocks.WISPROOT_DOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_door").save(this.output);
        trapdoorBuilder(AetherIIBlocks.WISPROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        buttonBuilder(AetherIIBlocks.WISPROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_button").save(this.output);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS, AetherIIBlocks.WISPROOT_PLANKS).group("wooden_stairs").save(this.output);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .save(this.output);
        sign(getter, AetherIIBlocks.WISPROOT_SIGN.get(), AetherIIBlocks.WISPROOT_PLANKS);
        hangingSign(getter, AetherIIBlocks.WISPROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_STAIRS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SLAB.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), 2);

        // Wisproot Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_FLOORBOARDS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_HIGHLIGHT.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SHINGLES.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BASE_PLANKS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TOP_PLANKS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BASE_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TOP_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_BEAM.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_WISPROOT_DOOR.get(), AetherIIBlocks.WISPROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(), AetherIIBlocks.WISPROOT_TRAPDOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_BASE_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_TOP_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIIBlocks.WISPROOT_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_DOOR.get(), AetherIIBlocks.SECRET_WISPROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TRAPDOOR.get(), AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get());

        // Holystone
        this.stairs(AetherIIBlocks.HOLYSTONE_STAIRS, AetherIIBlocks.HOLYSTONE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get());
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(this.output);
        buttonBuilder(AetherIIBlocks.HOLYSTONE_BUTTON.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_STAIRS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get());

        // Mossy Holystone
        this.stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, AetherIIBlocks.MOSSY_HOLYSTONE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());

        // Irradiated Holystone
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_HOLYSTONE, 8)
                .define('/', AetherIIBlocks.HOLYSTONE.get())
                .define('#', AetherIIItems.IRRADIATED_DUST.get())
                .pattern("///")
                .pattern("/#/")
                .pattern("///")
                .unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST.get()), has(AetherIIItems.IRRADIATED_DUST.get()))
                .save(this.output);
        this.stairs(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, AetherIIBlocks.IRRADIATED_HOLYSTONE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());

        // Holystone Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.HOLYSTONE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());

        // Holystone Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_HEADSTONE.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_KEYSTONE.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_FLAGSTONES.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_HEADSTONE.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_KEYSTONE.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_PILLAR.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE_PILLAR.get());

        // Faded Holystone Bricks
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.HOLYSTONE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), 0.1F, 200).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_BRICKS.get()), has(AetherIIBlocks.HOLYSTONE_BRICKS.get())).save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_faded_holystone_blocks", has(AetherIITags.Items.FADED_HOLYSTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("faded_holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());

        // Faded Holystone Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get());

        // Ichorite
        this.stairs(AetherIIBlocks.ICHORITE_STAIRS, AetherIIBlocks.ICHORITE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_SLAB.get(), AetherIIBlocks.ICHORITE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_WALL.get(), AetherIIBlocks.ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_STAIRS.get(), AetherIIBlocks.ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_SLAB.get(), AetherIIBlocks.ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_WALL.get(), AetherIIBlocks.ICHORITE.get());

        // Smooth Ichorite
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.ICHORITE.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SMOOTH_ICHORITE.get(), 0.1F, 200).unlockedBy(getHasName(AetherIIBlocks.ICHORITE.get()), has(AetherIIBlocks.ICHORITE.get())).save(this.output);
        this.stairs(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS, AetherIIBlocks.SMOOTH_ICHORITE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_WALL.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get(), AetherIIBlocks.SMOOTH_ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_WALL.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());

        // Ichorite Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.ICHORITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.ICHORITE_DECORATIVE_BLOCKS))
                .save(this.output, name("ichorite_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.ICHORITE_BRICK_STAIRS, AetherIIBlocks.ICHORITE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICK_SLAB.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_WALL.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_STAIRS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_SLAB.get(), AetherIIBlocks.SMOOTH_ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_WALL.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_STAIRS.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_SLAB.get(), AetherIIBlocks.ICHORITE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_WALL.get(), AetherIIBlocks.ICHORITE_BRICKS.get());

        // Ichorite Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_FLAGSTONES.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_RUNESTONE.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_KEYSTONE.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BASE_BRICKS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BASE_PILLAR.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_PILLAR.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_FLAGSTONES.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_RUNESTONE.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_KEYSTONE.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BASE_BRICKS.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BASE_PILLAR.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_PILLAR.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_RUNESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.ICHORITE_PILLAR.get());

        // Marbled Ichorite
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_ICHORITE, 2)
                .define('I', AetherIIBlocks.ICHORITE.get())
                .define('Q', Items.QUARTZ)
                .pattern("IQ")
                .pattern("QI")
                .unlockedBy(getHasName(AetherIIBlocks.ICHORITE.get()), has(AetherIIBlocks.ICHORITE.get()))
                .save(this.output);
        this.stairs(AetherIIBlocks.MARBLED_ICHORITE_STAIRS, AetherIIBlocks.MARBLED_ICHORITE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_ICHORITE_SLAB.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_WALL.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_SLAB.get(), AetherIIBlocks.MARBLED_ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_WALL.get(), AetherIIBlocks.MARBLED_ICHORITE.get());

        // Marbled Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_ICHORITE.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.MARBLED_ICHORITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.MARBLED_ICHORITE_DECORATIVE_BLOCKS))
                .save(this.output, name("marbled_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.MARBLED_BRICK_STAIRS, AetherIIBlocks.MARBLED_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_BRICK_SLAB.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICK_WALL.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICK_STAIRS.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICK_SLAB.get(), AetherIIBlocks.MARBLED_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICK_WALL.get(), AetherIIBlocks.MARBLED_BRICKS.get());

        // Marbled Ichorite Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_FLAGSTONES.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_KEYSTONE.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BASE_BRICKS.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BASE_PILLAR.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_PILLAR.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_PILLAR.get());

        // Undershale
        this.stairs(AetherIIBlocks.UNDERSHALE_STAIRS, AetherIIBlocks.UNDERSHALE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get());

        // Undershale Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stairs(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS, AetherIIBlocks.UNDERSHALE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());

        // Agiosite
        this.stairs(AetherIIBlocks.AGIOSITE_STAIRS, AetherIIBlocks.AGIOSITE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_STAIRS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get());

        // Agiosite Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.AGIOSITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.AGIOSITE_DECORATIVE_BLOCKS))
                .save(this.output, name("agiosite_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, AetherIIBlocks.AGIOSITE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());

        // Agiosite Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_FLAGSTONES.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_KEYSTONE.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_FLAGSTONES.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_KEYSTONE.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BASE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_PILLAR.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE_PILLAR.get());

        // Icestone
        this.stairs(AetherIIBlocks.ICESTONE_STAIRS, AetherIIBlocks.ICESTONE).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get());

        // Icestone Bricks
        polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.ICESTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.ICESTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("icestone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.ICESTONE_BRICK_STAIRS, AetherIIBlocks.ICESTONE_BRICKS).save(this.output);
        slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICK_SLAB.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_STAIRS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_SLAB.get(), AetherIIBlocks.ICESTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_STAIRS.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_SLAB.get(), AetherIIBlocks.ICESTONE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE_BRICKS.get());

        // Icestone Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_FLAGSTONES.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_KEYSTONE.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BASE_BRICKS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BASE_PILLAR.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_PILLAR.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_FLAGSTONES.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_KEYSTONE.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BASE_BRICKS.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BASE_PILLAR.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_PILLAR.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE_PILLAR.get());

        // Glass
        this.altarEnchanting(RecipeCategory.MISC, AetherIIBlocks.QUICKSOIL_GLASS, AetherIIBlocks.QUICKSOIL, 1, 0.0F).save(this.output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.CRUDE_SCATTERGLASS.get()), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SCATTERGLASS.get(), 0.1F, 200).unlockedBy("has_crude_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS.get())).save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.TILED_QUICKSOIL_GLASS.get(), AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get(), AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get(), AetherIIBlocks.SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS.get(), AetherIIBlocks.TILED_QUICKSOIL_GLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS.get(), AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get());

        // Glass Panes
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), 16).define('#', AetherIIBlocks.QUICKSOIL_GLASS.get()).pattern("###").pattern("###").unlockedBy("has_quicksoil_glass", has(AetherIIBlocks.QUICKSOIL_GLASS.get())).save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), 16).define('#', AetherIIBlocks.CRUDE_SCATTERGLASS.get()).pattern("###").pattern("###").unlockedBy("has_crude_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS.get())).save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS_PANE.get(), 16).define('#', AetherIIBlocks.SCATTERGLASS.get()).pattern("###").pattern("###").unlockedBy("has_scatterglass", has(AetherIIBlocks.SCATTERGLASS.get())).save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.TILED_QUICKSOIL_GLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.GRIDDED_QUICKSOIL_GLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get());

        // Wool
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.CLOUDTWINE, 4)
                .requires(Ingredient.of(getter.getOrThrow(AetherIITags.Items.CLOUDWOOL)))
                .group("cloudtwine")
                .unlockedBy("has_cloudwool", has(AetherIITags.Items.CLOUDWOOL))
                .save(this.output, this.name("cloudtwine_from_cloudwool"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.CLOUDWOOL)
                .define('#', AetherIIItems.CLOUDTWINE)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherIIItems.CLOUDTWINE), has(AetherIIItems.CLOUDTWINE))
                .save(this.output, this.name(getSimpleRecipeName(AetherIIBlocks.CLOUDWOOL)));
        this.colorBlockWithDye(dyes, wool, AetherIIBlocks.CLOUDWOOL.asItem(), "wool");
        
        // Carpet
        this.colorBlockWithDye(dyes, carpet, AetherIIBlocks.CLOUDWOOL_CARPET.asItem(), "carpet");
        this.carpet(AetherIIBlocks.CLOUDWOOL_CARPET, AetherIIBlocks.CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET, AetherIIBlocks.WHITE_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET, AetherIIBlocks.ORANGE_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET, AetherIIBlocks.MAGENTA_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET, AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET, AetherIIBlocks.YELLOW_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.LIME_CLOUDWOOL_CARPET, AetherIIBlocks.LIME_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.PINK_CLOUDWOOL_CARPET, AetherIIBlocks.PINK_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET, AetherIIBlocks.GRAY_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET, AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET, AetherIIBlocks.CYAN_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET, AetherIIBlocks.PURPLE_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET, AetherIIBlocks.BLUE_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET, AetherIIBlocks.BROWN_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET, AetherIIBlocks.GREEN_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.RED_CLOUDWOOL_CARPET, AetherIIBlocks.RED_CLOUDWOOL.get());
        this.carpet(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET, AetherIIBlocks.BLACK_CLOUDWOOL.get());

        // Arkenium Blocks
        doorBuilder(AetherIIBlocks.ARKENIUM_DOOR, Ingredient.of(AetherIIItems.ARKENIUM_PLATES.get())).unlockedBy(getHasName(AetherIIItems.ARKENIUM_PLATES.get()), has(AetherIIItems.ARKENIUM_PLATES.get())).save(this.output);
        twoByTwoPacker(RecipeCategory.REDSTONE, AetherIIBlocks.ARKENIUM_TRAPDOOR, AetherIIItems.ARKENIUM_PLATES);

        // Mineral Blocks
        oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.AMBROSIUM_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AMBROSIUM_BLOCK, "ambrosium_shard_from_ambrosium_block", "ambrosium_shard");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.ZANITE_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ZANITE_BLOCK, "zanite_gemstone_from_zanite_block", "zanite_gemstone");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATES.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARKENIUM_BLOCK, "arkenium_plate_from_arkenium_block", "arkenium_plate");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GRAVITITE_BLOCK, "gravitite_plate_from_gravitite_block", "gravitite_plate");

        // Arilum Lantern
        arilumLantern(getter, AetherIIBlocks.GREEN_ARILUM_LANTERN, AetherIIItems.GREEN_SWET_GEL);
        arilumLantern(getter, AetherIIBlocks.BLUE_ARILUM_LANTERN, AetherIIItems.BLUE_SWET_GEL);
        arilumLantern(getter, AetherIIBlocks.PURPLE_ARILUM_LANTERN, AetherIIItems.PURPLE_SWET_GEL);
        arilumLantern(getter, AetherIIBlocks.GOLDEN_ARILUM_LANTERN, AetherIIItems.GOLDEN_SWET_GEL);
        arilumLantern(getter, AetherIIBlocks.WHITE_ARILUM_LANTERN, AetherIIItems.WHITE_SWET_GEL);

        // Utility
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBROSIUM_TORCH.get(), 4)
                .define('A', AetherIIItems.AMBROSIUM_SHARD.get())
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .pattern("A")
                .pattern("/")
                .unlockedBy(getHasName(AetherIIItems.HOLYSTONE_PICKAXE.get()), has(AetherIIItems.HOLYSTONE_PICKAXE.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .define('#', AetherIITags.Items.STONE_CRAFTING)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_FURNACE.get()), has(AetherIITags.Items.STONE_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.SMOKER)
                .define('#', ItemTags.LOGS)
                .define('F', AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .pattern(" # ")
                .pattern("#F#")
                .pattern(" # ")
                .unlockedBy(getHasName(Blocks.SMOKER), has(ItemTags.LOGS))
                .save(this.output, "smoker_from_holystone_furnace");
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.BLAST_FURNACE)
                .define('#', Blocks.SMOOTH_STONE)
                .define('I', Items.IRON_INGOT)
                .define('F', AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .pattern("III")
                .pattern("IFI")
                .pattern("###")
                .unlockedBy(getHasName(Blocks.BLAST_FURNACE), has(Blocks.SMOOTH_STONE))
                .save(this.output, "blast_furnace_from_holystone_furnace");
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ALTAR.get())
                .define('H', AetherIIBlocks.HOLYSTONE.get())
                .define('Z', AetherIITags.Items.GEMS_ZANITE)
                .pattern("HHH")
                .pattern("HZH")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ALTAR.get()), has(AetherIITags.Items.GEMS_ZANITE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARTISANS_BENCH.get())
                .define('A', AetherIITags.Items.PLATES_ARKENIUM)
                .define('P', AetherIITags.Items.PLANKS_CRAFTING)
                .define('H', AetherIIBlocks.HOLYSTONE.get())
                .pattern("AAA")
                .pattern("PPP")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ARTISANS_BENCH.get()), has(AetherIITags.Items.PLATES_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FORGE.get())
                .define('H', AetherIIBlocks.HOLYSTONE.get())
                .define('A', AetherIITags.Items.PLATES_ARKENIUM)
                .pattern("AAA")
                .pattern(" A ")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ARKENIUM_FORGE.get()), has(AetherIITags.Items.PLATES_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_CHEST.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_CHEST.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_LADDER.get(), 3)
                .define('#', AetherIITags.Items.RODS_SKYROOT)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_LADDER.get()), has(AetherIITags.Items.RODS_SKYROOT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BED.get(), 1)
                .define('W', ItemTags.WOOL)
                .define('P', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("WWW")
                .pattern("PPP")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Items.ITEM_FRAME, 1)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .define('#', AetherIITags.Items.ANIMALS_FURS)
                .pattern("///")
                .pattern("/#/")
                .pattern("///")
                .unlockedBy("has_leather", has(AetherIITags.Items.ANIMALS_FURS))
                .save(this.output, "item_frame_from_hide");

        // Bookshelves
        bookshelf(getter, AetherIIBlocks.SKYROOT_BOOKSHELF, AetherIIBlocks.SKYROOT_PLANKS);
        bookshelf(getter, AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS);

        // Items
        // Tools
        this.makePickaxeWithTag(AetherIIItems.SKYROOT_PICKAXE, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeAxeWithTag(AetherIIItems.SKYROOT_AXE, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeShovelWithTag(AetherIIItems.SKYROOT_SHOVEL, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeHoeWithTag(AetherIIItems.SKYROOT_TROWEL, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);

        this.makePickaxeWithTag(AetherIIItems.HOLYSTONE_PICKAXE, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeAxeWithTag(AetherIIItems.HOLYSTONE_AXE, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeShovelWithTag(AetherIIItems.HOLYSTONE_SHOVEL, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeHoeWithTag(AetherIIItems.HOLYSTONE_TROWEL, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);

        this.makePickaxeWithTag(AetherIIItems.ZANITE_PICKAXE, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeAxeWithTag(AetherIIItems.ZANITE_AXE, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeShovelWithTag(AetherIIItems.ZANITE_SHOVEL, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeHoeWithTag(AetherIIItems.ZANITE_TROWEL, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);

        this.makePickaxeWithTag(AetherIIItems.ARKENIUM_PICKAXE, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeAxeWithTag(AetherIIItems.ARKENIUM_AXE, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeShovelWithTag(AetherIIItems.ARKENIUM_SHOVEL, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeHoeWithTag(AetherIIItems.ARKENIUM_TROWEL, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);

        this.makePickaxeWithTag(AetherIIItems.GRAVITITE_PICKAXE, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeAxeWithTag(AetherIIItems.GRAVITITE_AXE, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeShovelWithTag(AetherIIItems.GRAVITITE_SHOVEL, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeHoeWithTag(AetherIIItems.GRAVITITE_TROWEL, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.ARKENIUM_SHEARS.get())
                .define('#', AetherIITags.Items.PLATES_ARKENIUM)
                .pattern(" #")
                .pattern("# ")
                .unlockedBy("has_arkenium", has(AetherIITags.Items.PLATES_ARKENIUM))
                .save(this.output);

        // Combat
        this.makeSwordWithTag(AetherIIItems.SKYROOT_SHORTSWORD, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeHammerWithTag(AetherIIItems.SKYROOT_HAMMER, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeSpearWithTag(AetherIIItems.SKYROOT_SPEAR, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.SKYROOT_CROSSBOW, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeShieldWithTag(AetherIIItems.SKYROOT_SHIELD, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);

        this.makeSwordWithTag(AetherIIItems.HOLYSTONE_SHORTSWORD, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeHammerWithTag(AetherIIItems.HOLYSTONE_HAMMER, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeSpearWithTag(AetherIIItems.HOLYSTONE_SPEAR, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.HOLYSTONE_CROSSBOW, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeShieldWithTag(AetherIIItems.HOLYSTONE_SHIELD, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);

        this.makeSwordWithTag(AetherIIItems.ZANITE_SHORTSWORD, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeHammerWithTag(AetherIIItems.ZANITE_HAMMER, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeSpearWithTag(AetherIIItems.ZANITE_SPEAR, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.ZANITE_CROSSBOW, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeShieldWithTag(AetherIIItems.ZANITE_SHIELD, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);

        this.makeSwordWithTag(AetherIIItems.ARKENIUM_SHORTSWORD, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeHammerWithTag(AetherIIItems.ARKENIUM_HAMMER, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeSpearWithTag(AetherIIItems.ARKENIUM_SPEAR, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.ARKENIUM_CROSSBOW, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);
        this.makeShieldWithTag(AetherIIItems.ARKENIUM_SHIELD, AetherIITags.Items.PLATES_ARKENIUM, "has_arkenium").save(this.output);

        this.makeSwordWithTag(AetherIIItems.GRAVITITE_SHORTSWORD, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeHammerWithTag(AetherIIItems.GRAVITITE_HAMMER, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeSpearWithTag(AetherIIItems.GRAVITITE_SPEAR, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.GRAVITITE_CROSSBOW, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);
        this.makeShieldWithTag(AetherIIItems.GRAVITITE_SHIELD, AetherIITags.Items.PLATES_GRAVITITE, "has_gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SCATTERGLASS_BOLT.get(), 4)
                .define('S', AetherIIItems.SCATTERGLASS_SHARD)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .define('F', Tags.Items.FEATHERS)
                .pattern(" S ")
                .pattern(" / ")
                .pattern(" F ")
                .unlockedBy("has_scatterglass_shard", has(AetherIIItems.SCATTERGLASS_SHARD))
                .save(this.output);

        // Armor
        this.makeHelmet(getter, AetherIIItems.TAEGORE_HIDE_HELMET, AetherIIItems.TAEGORE_HIDE).save(this.output);
        this.makeChestplate(getter, AetherIIItems.TAEGORE_HIDE_CHESTPLATE, AetherIIItems.TAEGORE_HIDE).save(this.output);
        this.makeLeggings(getter, AetherIIItems.TAEGORE_HIDE_LEGGINGS, AetherIIItems.TAEGORE_HIDE).save(this.output);
        this.makeBoots(getter, AetherIIItems.TAEGORE_HIDE_BOOTS, AetherIIItems.TAEGORE_HIDE).save(this.output);
        this.makeGloves(getter, AetherIIItems.TAEGORE_HIDE_GLOVES, AetherIIItems.TAEGORE_HIDE).save(this.output);

        this.makeHelmet(getter, AetherIIItems.BURRUKAI_PELT_HELMET, AetherIIItems.BURRUKAI_PELT).save(this.output);
        this.makeChestplate(getter, AetherIIItems.BURRUKAI_PELT_CHESTPLATE, AetherIIItems.BURRUKAI_PELT).save(this.output);
        this.makeLeggings(getter, AetherIIItems.BURRUKAI_PELT_LEGGINGS, AetherIIItems.BURRUKAI_PELT).save(this.output);
        this.makeBoots(getter, AetherIIItems.BURRUKAI_PELT_BOOTS, AetherIIItems.BURRUKAI_PELT).save(this.output);
        this.makeGloves(getter, AetherIIItems.BURRUKAI_PELT_GLOVES, AetherIIItems.BURRUKAI_PELT).save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.ZANITE_HELMET, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.ZANITE_CHESTPLATE, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.ZANITE_LEGGINGS, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.ZANITE_BOOTS, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.ZANITE_GLOVES, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.ARKENIUM_HELMET, AetherIITags.Items.PLATES_ARKENIUM, "arkenium").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.ARKENIUM_CHESTPLATE, AetherIITags.Items.PLATES_ARKENIUM, "arkenium").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.ARKENIUM_LEGGINGS, AetherIITags.Items.PLATES_ARKENIUM, "arkenium").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.ARKENIUM_BOOTS, AetherIITags.Items.PLATES_ARKENIUM, "arkenium").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.ARKENIUM_GLOVES, AetherIITags.Items.PLATES_ARKENIUM, "arkenium").save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.GRAVITITE_HELMET, AetherIITags.Items.PLATES_GRAVITITE, "gravitite").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.GRAVITITE_CHESTPLATE, AetherIITags.Items.PLATES_GRAVITITE, "gravitite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.GRAVITITE_LEGGINGS, AetherIITags.Items.PLATES_GRAVITITE, "gravitite").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.GRAVITITE_BOOTS, AetherIITags.Items.PLATES_GRAVITITE, "gravitite").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.GRAVITITE_GLOVES, AetherIITags.Items.PLATES_GRAVITITE, "gravitite").save(this.output);

        // Foods
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.ENCHANTED_BLUEBERRY, AetherIIItems.BLUEBERRY, 2, 0.0F).save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.ENCHANTED_WYNDBERRY, AetherIIItems.WYNDBERRY, 5, 0.0F).save(this.output);
        this.foodCooking(AetherIIItems.BURRUKAI_RIB_CUT, AetherIIItems.BURRUKAI_RIBS, 0.35F, this.output);
        this.foodCooking(AetherIIItems.KIRRID_LOIN, AetherIIItems.KIRRID_CUTLET, 0.35F, this.output);
        this.foodCooking(AetherIIItems.RAW_TAEGORE_MEAT, AetherIIItems.TAEGORE_STEAK, 0.35F, this.output);
        this.foodCooking(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK, AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK, 0.35F, this.output);

        // Parachutes
        this.parachute(getter, AetherIIItems.COLD_AERCLOUD_GLIDER, AetherIIBlocks.COLD_AERCLOUD);
        this.parachute(getter, AetherIIItems.GOLDEN_AERCLOUD_GLIDER, AetherIIBlocks.GOLDEN_AERCLOUD);
        this.parachute(getter, AetherIIItems.BLUE_AERCLOUD_GLIDER, AetherIIBlocks.BLUE_AERCLOUD);
        this.parachute(getter, AetherIIItems.PURPLE_AERCLOUD_GLIDER, AetherIIBlocks.PURPLE_AERCLOUD);

        // Materials
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 4)
                .group("sticks")
                .define('#', AetherIITags.Items.CRAFTS_SKYROOT_STICKS)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_planks", has(AetherIITags.Items.CRAFTS_SKYROOT_STICKS))
                .save(this.output, "skyroot_stick_from_planks");
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 2)
                .group("sticks")
                .requires(AetherIIBlocks.SKYROOT_TWIG)
                .unlockedBy("has_twig", has(AetherIIBlocks.SKYROOT_TWIG))
                .save(this.output, "skyroot_stick_from_twig");
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.MOA_FEED.get(), 3)
                .requires(AetherIIItems.SKYROOT_PINECONE)
                .unlockedBy("has_skyroot_pinecone", has(AetherIIItems.SKYROOT_PINECONE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_BUCKET.get(), 1)
                .define('#', AetherIITags.Items.CRAFTS_SKYROOT_TOOLS)
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_planks", has(AetherIITags.Items.CRAFTS_SKYROOT_TOOLS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.ARKENIUM_CANISTER.get(), 1)
                .define('#', AetherIITags.Items.PLATES_ARKENIUM)
                .define('S', AetherIIBlocks.SCATTERGLASS.get())
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_arkenium", has(AetherIITags.Items.PLATES_ARKENIUM))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SCATTERGLASS_SHARD.get(), 4)
                .requires(AetherIIBlocks.CRUDE_SCATTERGLASS)
                .unlockedBy("has_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.BRETTL_ROPE.get(), 2)
                .define('#', AetherIIItems.BRETTL_GRASS)
                .pattern("  #")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_brettl_grass", has(AetherIIItems.BRETTL_GRASS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SCATTERGLASS_VIAL.get(), 4)
                .define('#', AetherIIBlocks.SCATTERGLASS.get())
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_scatterglass", has(AetherIIBlocks.SCATTERGLASS.get()))
                .save(this.output);

        this.smeltingOreRecipe(Items.QUARTZ, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(), 0.5F).group("quartz").save(this.output, this.name("quartz_from_smelting_holystone_quartz_ore"));
        this.blastingOreRecipe(Items.QUARTZ, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(), 0.5F).group("quartz").save(this.output, this.name("quartz_from_blasting_holystone_quartz_ore"));
        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_smelting_undershale_ambrosium_ore"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_blasting_undershale_ambrosium_ore"));
        this.smeltingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.3F).group("zanite").save(this.output, this.name("zanite_gemstone_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.ZANITE_ORE.get(), 0.3F).group("zanite").save(this.output, this.name("zanite_gemstone_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get(), 0.3F).group("zanite").save(this.output, this.name("zanite_gemstone_from_smelting_undershale_zanite_ore"));
        this.blastingOreRecipe(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get(), 0.3F).group("zanite").save(this.output, this.name("zanite_gemstone_from_blasting_undershale_zanite_ore"));
        this.smeltingOreRecipe(AetherIIItems.GLINT_GEMSTONE.get(), AetherIIBlocks.GLINT_ORE.get(), 0.3F).group("glint").save(this.output, this.name("glint_gemstone_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.GLINT_GEMSTONE.get(), AetherIIBlocks.GLINT_ORE.get(), 0.3F).group("glint").save(this.output, this.name("glint_gemstone_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.GLINT_GEMSTONE.get(), AetherIIBlocks.UNDERSHALE_GLINT_ORE.get(), 0.3F).group("glint").save(this.output, this.name("glint_gemstone_from_smelting_undershale_glint_ore"));
        this.blastingOreRecipe(AetherIIItems.GLINT_GEMSTONE.get(), AetherIIBlocks.UNDERSHALE_GLINT_ORE.get(), 0.3F).group("glint").save(this.output, this.name("glint_gemstone_from_blasting_undershale_glint_ore"));

        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATES, AetherIIItems.INERT_ARKENIUM, 4, 0.0F).group("arkenium").save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATES, AetherIIBlocks.ARKENIUM_ORE, 4, 0.0F).group("arkenium").save(this.output, this.name("arkenium_plates_from_arkenium_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATES, AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE, 4, 0.0F).group("arkenium").save(this.output, this.name("arkenium_plates_from_undershale_arkenium_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIItems.INERT_GRAVITITE, 8, 0.0F).group("gravitite").save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIBlocks.GRAVITITE_ORE, 8, 0.0F).group("gravitite").save(this.output, this.name("gravitite_plates_from_gravitite_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE, 8, 0.0F).group("gravitite").save(this.output, this.name("gravitite_plates_from_undershale_gravitite_ore"));

        for (Map.Entry<Moa.FeatherColor, Item> featherDye : featherDyes.entrySet()) {
            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, featherDye.getValue(), 1).
                    requires(DataComponentIngredient.of(false, AetherIIDataComponents.FEATHER_COLOR, featherDye.getKey(), AetherIIItems.MOA_FEATHER))
                    .group(getItemName(featherDye.getValue()))
                    .unlockedBy(getHasName(AetherIIItems.MOA_FEATHER), has(AetherIIItems.MOA_FEATHER))
                    .save(this.output, getItemName(featherDye.getValue()) + "_from_" + featherDye.getKey().getSerializedName());
        }

        oneToOneConversionRecipe(Items.YELLOW_DYE, AetherIIBlocks.BLADE_POA, "yellow_dye");
        oneToOneConversionRecipe(Items.WHITE_DYE, AetherIIBlocks.HESPEROSE, "white_dye");
        oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIBlocks.TARABLOOM, "purple_dye");
        oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIItems.SATIVAL_BULB, "purple_dye");
        oneToOneConversionRecipe(Items.WHITE_DYE, AetherIIBlocks.POASPROUT, "white_dye");
        oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, AetherIIItems.BRETTL_FLOWER, "light_blue_dye");
        oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, AetherIIBlocks.LILICHIME, "light_blue_dye");
        oneToOneConversionRecipe(Items.CYAN_DYE, AetherIIBlocks.PLURACIAN, "cyan_dye");
        oneToOneConversionRecipe(Items.BLUE_DYE, AetherIIBlocks.SATIVAL_SHOOT, "blue_dye");
        oneToOneConversionRecipe(Items.PINK_DYE, AetherIIBlocks.BRYALINN_MOSS_FLOWERS, "pink_dye");
        oneToOneConversionRecipe(Items.MAGENTA_DYE, AetherIIBlocks.HOLPUPEA, "magenta_dye");
        oneToOneConversionRecipe(Items.MAGENTA_DYE, AetherIIBlocks.TARAHESP_FLOWERS, "magenta_dye");
        oneToOneConversionRecipe(Items.BROWN_DYE, AetherIIBlocks.SKY_ROOTS, "brown_dye");

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.GREEN_SWET_JELLY.get(), 1)
                .requires(AetherIIItems.GREEN_SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.GREEN_SWET_GEL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.BLUE_SWET_JELLY.get(), 1)
                .requires(AetherIIItems.BLUE_SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.BLUE_SWET_GEL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.PURPLE_SWET_JELLY.get(), 1)
                .requires(AetherIIItems.PURPLE_SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.PURPLE_SWET_GEL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.GOLDEN_SWET_JELLY.get(), 1)
                .requires(AetherIIItems.GOLDEN_SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.GOLDEN_SWET_GEL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.WHITE_SWET_JELLY.get(), 1)
                .requires(AetherIIItems.WHITE_SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.WHITE_SWET_GEL))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.BLUEBERRY_MOA_FEED.get(), 1)
                .requires(AetherIIItems.MOA_FEED)
                .requires(AetherIIItems.BLUEBERRY)
                .unlockedBy("has_feed", has(AetherIIItems.MOA_FEED))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.ENCHANTED_MOA_FEED.get(), 1)
                .requires(AetherIIItems.MOA_FEED)
                .requires(AetherIIItems.ENCHANTED_BLUEBERRY)
                .unlockedBy("has_feed", has(AetherIIItems.MOA_FEED))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.MOA_SADDLE.get())
                .define('#', AetherIIItems.TAEGORE_HIDE)
                .define('/', AetherIIItems.CLOUDTWINE)
                .pattern("###")
                .pattern("#/#")
                .unlockedBy("has_taegore_hide", has(AetherIIItems.TAEGORE_HIDE))
                .save(this.output);


        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.SPLINT.get()))
                .requires(AetherIIItems.SKYROOT_STICK.get())
                .requires(AetherIITags.Items.CLOUDWOOL)
                .unlockedBy("has_cloudwool", has(AetherIITags.Items.CLOUDWOOL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.BANDAGE.get()))
                .requires(AetherIIItems.CLOUDTWINE.get())
                .requires(AetherIITags.Items.CLOUDWOOL)
                .unlockedBy("has_cloudwool", has(AetherIITags.Items.CLOUDWOOL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.ANTIVENOM_VIAL.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.AECHOR_PETAL.get())
                .requires(AetherIIBlocks.HESPEROSE.get())
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.ANTITOXIN_VIAL.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.AECHOR_PETAL.get())
                .requires(AetherIIBlocks.TARABLOOM.get())
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.VALKYRIE_TEA.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.VALKYRIE_WINGS.get())
                .requires(AetherIIItems.AMBROSIUM_SHARD.get())
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 1).build()))
                .define('A', AetherIIItems.AMBROSIUM_SHARD.get())
                .define('H', AetherIIBlocks.HOLYSTONE.get())
                .pattern("HAH")
                .pattern("AAA")
                .pattern("HAH")
                .unlockedBy("has_ambrosium_shard", has(AetherIIItems.AMBROSIUM_SHARD.get()))
                .save(this.output);

        this.altarEnchanting(RecipeCategory.MISC,
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 0).build()),
                5, 0.0F).group("healing_stone").save(this.output, "healing_stone_recharging_0");

        this.altarEnchanting(RecipeCategory.MISC,
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 1).build()),
                4, 0.0F).group("healing_stone").save(this.output, "healing_stone_recharging_1");

        this.altarEnchanting(RecipeCategory.MISC,
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 2).build()),
                3, 0.0F).group("healing_stone").save(this.output, "healing_stone_recharging_2");

        this.altarEnchanting(RecipeCategory.MISC,
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 3).build()),
                2, 0.0F).group("healing_stone").save(this.output, "healing_stone_recharging_3");

        this.altarEnchanting(RecipeCategory.MISC,
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStack(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 4).build()),
                1, 0.0F).group("healing_stone").save(this.output, "healing_stone_recharging_4");

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_PICKAXE, 1).group("repair_skyroot").save(this.output, "repair_skyroot_pickaxe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_AXE, 1).group("repair_skyroot").save(this.output, "repair_skyroot_axe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_SHOVEL, 1).group("repair_skyroot").save(this.output, "repair_skyroot_shovel");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_TROWEL, 1).group("repair_skyroot").save(this.output, "repair_skyroot_trowel");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_SHORTSWORD, 1).group("repair_skyroot").save(this.output, "repair_skyroot_shortsword");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_HAMMER, 1).group("repair_skyroot").save(this.output, "repair_skyroot_hammer");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_SPEAR, 1).group("repair_skyroot").save(this.output, "repair_skyroot_spear");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_CROSSBOW, 1).group("repair_skyroot").save(this.output, "repair_skyroot_crossbow");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_SHIELD, 1).group("repair_skyroot").save(this.output, "repair_skyroot_shield");

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_PICKAXE, 2).group("repair_holystone").save(this.output, "repair_holystone_pickaxe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_AXE, 2).group("repair_holystone").save(this.output, "repair_holystone_axe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_SHOVEL, 2).group("repair_holystone").save(this.output, "repair_holystone_shovel");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_TROWEL, 2).group("repair_holystone").save(this.output, "repair_holystone_trowel");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_SHORTSWORD, 2).group("repair_holystone").save(this.output, "repair_holystone_shortsword");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_HAMMER, 2).group("repair_holystone").save(this.output, "repair_holystone_hammer");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_SPEAR, 2).group("repair_holystone").save(this.output, "repair_holystone_spear");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_CROSSBOW, 2).group("repair_holystone").save(this.output, "repair_holystone_crossbow");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_SHIELD, 2).group("repair_holystone").save(this.output, "repair_holystone_shield");

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_PICKAXE, 3).group("repair_zanite").save(this.output, "repair_zanite_pickaxe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_AXE, 3).group("repair_zanite").save(this.output, "repair_zanite_axe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_SHOVEL, 3).group("repair_zanite").save(this.output, "repair_zanite_shovel");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_TROWEL, 3).group("repair_zanite").save(this.output, "repair_zanite_trowel");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_SHORTSWORD, 3).group("repair_zanite").save(this.output, "repair_zanite_shortsword");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_HAMMER, 3).group("repair_zanite").save(this.output, "repair_zanite_hammer");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_SPEAR, 3).group("repair_zanite").save(this.output, "repair_zanite_spear");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_CROSSBOW, 3).group("repair_zanite").save(this.output, "repair_zanite_crossbow");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_SHIELD, 3).group("repair_zanite").save(this.output, "repair_zanite_shield");

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_PICKAXE, 5).group("repair_arkenium").save(this.output, "repair_arkenium_pickaxe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_AXE, 5).group("repair_arkenium").save(this.output, "repair_arkenium_axe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_SHOVEL, 5).group("repair_arkenium").save(this.output, "repair_arkenium_shovel");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_TROWEL, 5).group("repair_arkenium").save(this.output, "repair_arkenium_trowel");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_SHORTSWORD, 5).group("repair_arkenium").save(this.output, "repair_arkenium_shortsword");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_HAMMER, 5).group("repair_arkenium").save(this.output, "repair_arkenium_hammer");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_SPEAR, 5).group("repair_arkenium").save(this.output, "repair_arkenium_spear");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_CROSSBOW, 5).group("repair_arkenium").save(this.output, "repair_arkenium_crossbow");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_SHIELD, 5).group("repair_arkenium").save(this.output, "repair_arkenium_shield");

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_PICKAXE, 8).group("repair_gravitite").save(this.output, "repair_gravitite_pickaxe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_AXE, 8).group("repair_gravitite").save(this.output, "repair_gravitite_axe");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_SHOVEL, 8).group("repair_gravitite").save(this.output, "repair_gravitite_shovel");
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_TROWEL, 8).group("repair_gravitite").save(this.output, "repair_gravitite_trowel");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_SHORTSWORD, 8).group("repair_gravitite").save(this.output, "repair_gravitite_shortsword");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_HAMMER, 8).group("repair_gravitite").save(this.output, "repair_gravitite_hammer");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_SPEAR, 8).group("repair_gravitite").save(this.output, "repair_gravitite_spear");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_CROSSBOW, 8).group("repair_gravitite").save(this.output, "repair_gravitite_crossbow");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_SHIELD, 8).group("repair_gravitite").save(this.output, "repair_gravitite_shield");

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.TAEGORE_HIDE_HELMET, 1).group("repair_taegore_hide_armor").save(this.output, "repair_taegore_hide_helmet");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.TAEGORE_HIDE_CHESTPLATE, 1).group("repair_taegore_hide_armor").save(this.output, "repair_taegore_hide_chestplate");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.TAEGORE_HIDE_LEGGINGS, 1).group("repair_taegore_hide_armor").save(this.output, "repair_taegore_hide_leggings");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.TAEGORE_HIDE_BOOTS, 1).group("repair_taegore_hide_armor").save(this.output, "repair_taegore_hide_boots");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.TAEGORE_HIDE_GLOVES, 1).group("repair_taegore_hide_armor").save(this.output, "repair_taegore_hide_gloves");

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PELT_HELMET, 2).group("repair_burrukai_pelt_armor").save(this.output, "repair_burrukai_pelt_helmet");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PELT_CHESTPLATE, 2).group("repair_burrukai_pelt_armor").save(this.output, "repair_burrukai_pelt_chestplate");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PELT_LEGGINGS, 2).group("repair_burrukai_pelt_armor").save(this.output, "repair_burrukai_pelt_leggings");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PELT_BOOTS, 2).group("repair_burrukai_pelt_armor").save(this.output, "repair_burrukai_pelt_boots");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PELT_GLOVES, 2).group("repair_burrukai_pelt_armor").save(this.output, "repair_burrukai_pelt_gloves");

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_HELMET, 3).group("repair_zanite_armor").save(this.output, "repair_zanite_helmet");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_CHESTPLATE, 3).group("repair_zanite_armor").save(this.output, "repair_zanite_chestplate");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_LEGGINGS, 3).group("repair_zanite_armor").save(this.output, "repair_zanite_leggings");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_BOOTS, 3).group("repair_zanite_armor").save(this.output, "repair_zanite_boots");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_GLOVES, 3).group("repair_zanite_armor").save(this.output, "repair_zanite_gloves");

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_HELMET, 5).group("repair_arkenium_armor").save(this.output, "repair_arkenium_helmet");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_CHESTPLATE, 5).group("repair_arkenium_armor").save(this.output, "repair_arkenium_chestplate");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_LEGGINGS, 5).group("repair_arkenium_armor").save(this.output, "repair_arkenium_leggings");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_BOOTS, 5).group("repair_arkenium_armor").save(this.output, "repair_arkenium_boots");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_GLOVES, 5).group("repair_arkenium_armor").save(this.output, "repair_arkenium_gloves");

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_HELMET, 8).group("repair_gravitite_armor").save(this.output, "repair_gravitite_helmet");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_CHESTPLATE, 8).group("repair_gravitite_armor").save(this.output, "repair_gravitite_chestplate");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_LEGGINGS, 8).group("repair_gravitite_armor").save(this.output, "repair_gravitite_leggings");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_BOOTS, 8).group("repair_gravitite_armor").save(this.output, "repair_gravitite_boots");
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_GLOVES, 8).group("repair_gravitite_armor").save(this.output, "repair_gravitite_gloves");

        this.acidCorrosion(AetherIIBlocks.ICHORITE.get(), AetherIIBlocks.UNDERSHALE.get()).save(this.output, "corrode_undershale_to_ichorite");

        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.HOLYSTONE.toStack()), AetherIIBlocks.IRRADIATED_HOLYSTONE, this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.HOLYSTONE_STAIRS.toStack()), AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.HOLYSTONE_SLAB.toStack()), AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB, this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.HOLYSTONE_WALL.toStack()), AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL, this.output);

        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYROOT_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYPLANE_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYBIRCH_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYPINE_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.WISPROOT_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.WISPTOP_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATROOT_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATOAK_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, "irradiated_leaf_pile", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATBOA_LEAF_PILE.toStack()), AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, "irradiated_leaf_pile", this.output);

        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYROOT_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYPLANE_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYBIRCH_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.SKYPINE_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.WISPROOT_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.WISPTOP_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATROOT_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATOAK_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES, "irradiated_leaves", this.output);
        this.irradiationCleansing(RecipeCategory.BUILDING_BLOCKS, SimpleWeightedRandomList.single(AetherIIBlocks.GREATBOA_LEAVES.toStack()), AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES, "irradiated_leaves", this.output);

        this.irradiationCleansing(RecipeCategory.COMBAT, SimpleWeightedRandomList.<ItemStack>builder()
                .add(AetherIIItems.TAEGORE_HIDE_HELMET.toStack(), 1)
                .add(AetherIIItems.BURRUKAI_PELT_HELMET.toStack(), 1)
                .add(AetherIIItems.ZANITE_HELMET.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_HELMET.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_HELMET.toStack(), 1)
                .add(AetherIIItems.TAEGORE_HIDE_CHESTPLATE.toStack(), 1)
                .add(AetherIIItems.BURRUKAI_PELT_CHESTPLATE.toStack(), 1)
                .add(AetherIIItems.ZANITE_CHESTPLATE.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_CHESTPLATE.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_CHESTPLATE.toStack(), 1)
                .add(AetherIIItems.TAEGORE_HIDE_LEGGINGS.toStack(), 1)
                .add(AetherIIItems.BURRUKAI_PELT_LEGGINGS.toStack(), 1)
                .add(AetherIIItems.ZANITE_LEGGINGS.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_LEGGINGS.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_LEGGINGS.toStack(), 1)
                .add(AetherIIItems.TAEGORE_HIDE_BOOTS.toStack(), 1)
                .add(AetherIIItems.BURRUKAI_PELT_BOOTS.toStack(), 1)
                .add(AetherIIItems.ZANITE_BOOTS.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_BOOTS.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_BOOTS.toStack(), 1)
                .add(AetherIIItems.TAEGORE_HIDE_GLOVES.toStack(), 1)
                .add(AetherIIItems.BURRUKAI_PELT_GLOVES.toStack(), 1)
                .add(AetherIIItems.ZANITE_GLOVES.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_GLOVES.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_GLOVES.toStack(), 1)
                .build(), AetherIIItems.IRRADIATED_ARMOR, this.output);
        this.irradiationCleansing(RecipeCategory.COMBAT, SimpleWeightedRandomList.<ItemStack>builder()
                .add(AetherIIItems.SKYROOT_SHORTSWORD.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_SHORTSWORD.toStack(), 1)
                .add(AetherIIItems.ZANITE_SHORTSWORD.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_SHORTSWORD.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_SHORTSWORD.toStack(), 1)
                .add(AetherIIItems.SKYROOT_SPEAR.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_SPEAR.toStack(), 1)
                .add(AetherIIItems.ZANITE_SPEAR.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_SPEAR.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_SPEAR.toStack(), 1)
                .add(AetherIIItems.SKYROOT_HAMMER.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_HAMMER.toStack(), 1)
                .add(AetherIIItems.ZANITE_HAMMER.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_HAMMER.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_HAMMER.toStack(), 1)
                .build(), AetherIIItems.IRRADIATED_WEAPON, this.output);
        this.irradiationCleansing(RecipeCategory.COMBAT, SimpleWeightedRandomList.<ItemStack>builder()
                .add(AetherIIItems.SKYROOT_AXE.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_AXE.toStack(), 1)
                .add(AetherIIItems.ZANITE_AXE.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_AXE.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_AXE.toStack(), 1)
                .add(AetherIIItems.SKYROOT_PICKAXE.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_PICKAXE.toStack(), 1)
                .add(AetherIIItems.ZANITE_PICKAXE.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_PICKAXE.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_PICKAXE.toStack(), 1)
                .add(AetherIIItems.SKYROOT_SHOVEL.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_SHOVEL.toStack(), 1)
                .add(AetherIIItems.ZANITE_SHOVEL.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_SHOVEL.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_SHOVEL.toStack(), 1)
                .add(AetherIIItems.SKYROOT_TROWEL.toStack(), 1)
                .add(AetherIIItems.HOLYSTONE_TROWEL.toStack(), 1)
                .add(AetherIIItems.ZANITE_TROWEL.toStack(), 1)
                .add(AetherIIItems.ARKENIUM_TROWEL.toStack(), 1)
                .add(AetherIIItems.GRAVITITE_TROWEL.toStack(), 1)
                .build(), AetherIIItems.IRRADIATED_TOOL, this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, 1).requires(AetherIIBlocks.SKYROOT_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skyroot_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, 1).requires(AetherIIBlocks.SKYPLANE_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skyplane_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, 1).requires(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skybirch_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, 1).requires(AetherIIBlocks.SKYPINE_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skypine_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, 1).requires(AetherIIBlocks.WISPROOT_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("wisproot_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, 1).requires(AetherIIBlocks.WISPTOP_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("wisptop_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, 1).requires(AetherIIBlocks.GREATROOT_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatroot_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, 1).requires(AetherIIBlocks.GREATOAK_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatoak_leaf_pile_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, 1).requires(AetherIIBlocks.GREATBOA_LEAF_PILE.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaf_pile").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatboa_leaf_pile_irradiation_crafting"));

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES, 1).requires(AetherIIBlocks.SKYROOT_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skyroot_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES, 1).requires(AetherIIBlocks.SKYPLANE_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skyplane_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES, 1).requires(AetherIIBlocks.SKYBIRCH_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skybirch_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES, 1).requires(AetherIIBlocks.SKYPINE_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("skypine_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES, 1).requires(AetherIIBlocks.WISPROOT_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("wisproot_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES, 1).requires(AetherIIBlocks.WISPTOP_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("wisptop_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES, 1).requires(AetherIIBlocks.GREATROOT_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatroot_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES, 1).requires(AetherIIBlocks.GREATOAK_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatoak_leaves_irradiation_crafting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES, 1).requires(AetherIIBlocks.GREATBOA_LEAVES.get()).requires(AetherIIItems.IRRADIATED_DUST.get())
                .group("irradiated_leaves").unlockedBy(getHasName(AetherIIItems.IRRADIATED_DUST), has(AetherIIItems.IRRADIATED_DUST)).save(this.output, this.name("greatboa_leaves_irradiation_crafting"));

        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get(), AetherIIBlocks.SKYROOT_LEAF_PILE.get()).save(this.output, "skyroot_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get(), AetherIIBlocks.SKYPLANE_LEAF_PILE.get()).save(this.output, "skyplane_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get(), AetherIIBlocks.SKYBIRCH_LEAF_PILE.get()).save(this.output, "skybirch_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get(), AetherIIBlocks.SKYPINE_LEAF_PILE.get()).save(this.output, "skypine_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get(), AetherIIBlocks.WISPROOT_LEAF_PILE.get()).save(this.output, "wisproot_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get(), AetherIIBlocks.WISPTOP_LEAF_PILE.get()).save(this.output, "wisptop_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get(), AetherIIBlocks.GREATROOT_LEAF_PILE.get()).save(this.output, "greatroot_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get(), AetherIIBlocks.GREATOAK_LEAF_PILE.get()).save(this.output, "greatoak_leaf_pile_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get(), AetherIIBlocks.GREATBOA_LEAF_PILE.get()).save(this.output, "greatboa_leaf_pile_irradiation");

        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(), AetherIIBlocks.SKYROOT_LEAVES.get()).save(this.output, "skyroot_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(), AetherIIBlocks.SKYPLANE_LEAVES.get()).save(this.output, "skyplane_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(), AetherIIBlocks.SKYBIRCH_LEAVES.get()).save(this.output, "skybirch_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(), AetherIIBlocks.SKYPINE_LEAVES.get()).save(this.output, "skypine_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(), AetherIIBlocks.WISPROOT_LEAVES.get()).save(this.output, "wisproot_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(), AetherIIBlocks.WISPTOP_LEAVES.get()).save(this.output, "wisptop_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(), AetherIIBlocks.GREATROOT_LEAVES.get()).save(this.output, "greatroot_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(), AetherIIBlocks.GREATOAK_LEAVES.get()).save(this.output, "greatoak_leaves_irradiation");
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get(), AetherIIBlocks.GREATBOA_LEAVES.get()).save(this.output, "greatboa_leaves_irradiation");
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new AetherIIRecipeData(output, provider);
        }

        public String getName() {
            return "Aether II Recipes";
        }
    }
}
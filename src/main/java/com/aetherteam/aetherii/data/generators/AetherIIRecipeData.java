package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIRecipeProvider;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.special.LootRepairRecipe;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.crafting.CookingBookCategory;
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
        List<Item> bed = List.of(
                AetherIIBlocks.BLACK_SKYROOT_BED.asItem(),
                AetherIIBlocks.BLUE_SKYROOT_BED.asItem(),
                AetherIIBlocks.BROWN_SKYROOT_BED.asItem(),
                AetherIIBlocks.CYAN_SKYROOT_BED.asItem(),
                AetherIIBlocks.GRAY_SKYROOT_BED.asItem(),
                AetherIIBlocks.GREEN_SKYROOT_BED.asItem(),
                AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.asItem(),
                AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.asItem(),
                AetherIIBlocks.LIME_SKYROOT_BED.asItem(),
                AetherIIBlocks.MAGENTA_SKYROOT_BED.asItem(),
                AetherIIBlocks.ORANGE_SKYROOT_BED.asItem(),
                AetherIIBlocks.PINK_SKYROOT_BED.asItem(),
                AetherIIBlocks.PURPLE_SKYROOT_BED.asItem(),
                AetherIIBlocks.RED_SKYROOT_BED.asItem(),
                AetherIIBlocks.YELLOW_SKYROOT_BED.asItem(),
                AetherIIBlocks.WHITE_SKYROOT_BED.asItem()
        );
        Map<DyeColor, Item> dyeMap = Map.ofEntries(
                Map.entry(DyeColor.BLACK, Items.BLACK_DYE),
                Map.entry(DyeColor.BLUE, Items.BLUE_DYE),
                Map.entry(DyeColor.BROWN, Items.BROWN_DYE),
                Map.entry(DyeColor.CYAN, Items.CYAN_DYE),
                Map.entry(DyeColor.GRAY, Items.GRAY_DYE),
                Map.entry(DyeColor.GREEN, Items.GREEN_DYE),
                Map.entry(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE),
                Map.entry(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE),
                Map.entry(DyeColor.LIME, Items.LIME_DYE),
                Map.entry(DyeColor.MAGENTA, Items.MAGENTA_DYE),
                Map.entry(DyeColor.ORANGE, Items.ORANGE_DYE),
                Map.entry(DyeColor.PINK, Items.PINK_DYE),
                Map.entry(DyeColor.PURPLE, Items.PURPLE_DYE),
                Map.entry(DyeColor.RED, Items.RED_DYE),
                Map.entry(DyeColor.YELLOW, Items.YELLOW_DYE),
                Map.entry(DyeColor.WHITE, Items.WHITE_DYE)
        );

        // Special
        SpecialRecipeBuilder.special(LootRepairRecipe::new).save(this.output, this.name("loot_repairing"));

        this.dyedItem(AetherIIItems.MOA_SADDLE.get(), "dyed_moa_saddle");
        this.dyedItem(AetherIIItems.BEAST_PELT_BOOTS.get(), "dyed_beast_pelt_armor");
        this.dyedItem(AetherIIItems.BEAST_PELT_LEGGINGS.get(), "dyed_beast_pelt_armor");
        this.dyedItem(AetherIIItems.BEAST_PELT_CHESTPLATE.get(), "dyed_beast_pelt_armor");
        this.dyedItem(AetherIIItems.BEAST_PELT_HELMET.get(), "dyed_beast_pelt_armor");
        this.dyedItem(AetherIIItems.BEAST_PELT_GLOVES.get(), "dyed_beast_pelt_armor");
        this.dyedItem(AetherIIItems.BURRUKAI_PLATE_BOOTS.get(), "dyed_burrukai_plate_armor");
        this.dyedItem(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get(), "dyed_burrukai_plate_armor");
        this.dyedItem(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get(), "dyed_burrukai_plate_armor");
        this.dyedItem(AetherIIItems.BURRUKAI_PLATE_HELMET.get(), "dyed_burrukai_plate_armor");
        this.dyedItem(AetherIIItems.BURRUKAI_PLATE_GLOVES.get(), "dyed_burrukai_plate_armor");

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
        this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.CRUDE_SCATTERGLASS, AetherIIItems.SCATTERGLASS_SHARD);
        this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE, AetherIIBlocks.POINTED_HOLYSTONE);
        this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE, AetherIIBlocks.POINTED_ICHORITE);

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
        this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARCTIC_SNOW_BLOCK, AetherIIItems.ARCTIC_SNOWBALL);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARCTIC_SNOW, 6)
                .define('#', AetherIIBlocks.ARCTIC_SNOW_BLOCK)
                .pattern("###")
                .unlockedBy("has_snowball", has(AetherIIItems.ARCTIC_SNOWBALL))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FRAGILE_ARCTIC_ICE, 2)
                .define('S', AetherIIBlocks.ARCTIC_ICE)
                .define('B', AetherIIItems.ARCTIC_SNOWBALL)
                .pattern("SB")
                .pattern("BS")
                .unlockedBy(getHasName(AetherIIBlocks.ARCTIC_ICE), has(AetherIIBlocks.ARCTIC_ICE))
                .save(this.output);
        this.threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARCTIC_PACKED_ICE, AetherIIBlocks.ARCTIC_ICE);
        this.carpet(AetherIIBlocks.SHAYELINN_MOSS_CARPET, AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());

        // Irradiated
        this.carpet(AetherIIBlocks.AMBRELINN_MOSS_CARPET, AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get());

        // Nest Blocks
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WOVEN_SKYROOT_STICKS, 2)
                .define('#', AetherIIItems.SKYROOT_STICK)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherIIItems.SKYROOT_STICK), has(AetherIIItems.SKYROOT_STICK))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 2)
                .requires(AetherIIBlocks.WOVEN_SKYROOT_STICKS)
                .unlockedBy("has_woven_skyroot_sticks", has(AetherIIBlocks.WOVEN_SKYROOT_STICKS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ANIMAL_STASH)
                .define('#', AetherIIBlocks.WOVEN_SKYROOT_STICKS)
                .define('M', AetherIIBlocks.BRYALINN_MOSS_BLOCK)
                .pattern("M#M")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.WOVEN_SKYROOT_STICKS), has(AetherIIBlocks.WOVEN_SKYROOT_STICKS))
                .save(this.output);

        // Logs
        this.woodFromLogs(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.SKYROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.GREATROOT_WOOD.get(), AetherIIBlocks.GREATROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.WISPROOT_WOOD.get(), AetherIIBlocks.WISPROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
        this.woodFromLogs(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.AMBEROOT_LOG.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIBlocks.MOSSY_WISPROOT_LOG_BASE.get(), 1)
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

        // Trunks
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TRUNK, AetherIIBlocks.SKYROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_SKYROOT_TRUNK, AetherIIBlocks.STRIPPED_SKYROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TRUNK, AetherIIBlocks.GREATROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GREATROOT_TRUNK, AetherIIBlocks.STRIPPED_GREATROOT_TRUNK);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TRUNK, AetherIIBlocks.WISPROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_WISPROOT_TRUNK, AetherIIBlocks.MOSSY_WISPROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_WISPROOT_TRUNK, AetherIIBlocks.STRIPPED_WISPROOT_TRUNK);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_TRUNK, AetherIIBlocks.AMBEROOT_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.GUARDIAN_TRUNK, AetherIIBlocks.GUARDIAN_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.INFECTED_TRUNK, AetherIIBlocks.INFECTED_WOOD);
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_INFECTED_TRUNK, AetherIIBlocks.STRIPPED_INFECTED_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_TRUNK, AetherIIBlocks.SKYROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_SKYROOT_TRUNK, AetherIIBlocks.STRIPPED_SKYROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_TRUNK, AetherIIBlocks.GREATROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GREATROOT_TRUNK, AetherIIBlocks.STRIPPED_GREATROOT_TRUNK);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_TRUNK, AetherIIBlocks.WISPROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_WISPROOT_TRUNK, AetherIIBlocks.MOSSY_WISPROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_WISPROOT_TRUNK, AetherIIBlocks.STRIPPED_WISPROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_TRUNK, AetherIIBlocks.AMBEROOT_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GUARDIAN_TRUNK, AetherIIBlocks.GUARDIAN_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.INFECTED_TRUNK, AetherIIBlocks.INFECTED_WOOD);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_INFECTED_TRUNK, AetherIIBlocks.STRIPPED_INFECTED_WOOD);

        // Leaf Pile
        this.leafPile(getter, AetherIIBlocks.SKYROOT_LEAF_PILE, AetherIIBlocks.SKYROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.SKYPLANE_LEAF_PILE, AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.SKYBIRCH_LEAF_PILE, AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.SKYPINE_LEAF_PILE, AetherIIBlocks.SKYPINE_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.WISPROOT_LEAF_PILE, AetherIIBlocks.WISPROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.WISPTOP_LEAF_PILE, AetherIIBlocks.WISPTOP_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.GREATROOT_LEAF_PILE, AetherIIBlocks.GREATROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.GREATOAK_LEAF_PILE, AetherIIBlocks.GREATOAK_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.GREATBOA_LEAF_PILE, AetherIIBlocks.GREATBOA_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.AMBEROOT_LEAF_PILE, AetherIIBlocks.AMBEROOT_LEAVES.get());

        this.leafPile(getter, AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
        this.leafPile(getter, AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.SKYROOT_TWIG.get())
                .define('#', AetherIIItems.SKYROOT_STICK)
                .pattern(" #")
                .pattern("# ")
                .unlockedBy("has_stick", has(AetherIIItems.SKYROOT_STICK))
                .save(this.output);

        // Skyroot Planks
        this.planksFromLog(AetherIIBlocks.SKYROOT_PLANKS.get(), AetherIITags.Items.SKYROOT_LOGS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.SKYROOT_DECORATIVE_BLOCKS))
                .save(this.output, name("skyroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.SKYROOT_FENCE, AetherIIBlocks.SKYROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.SKYROOT_FENCE_GATE, AetherIIBlocks.SKYROOT_PLANKS).save(this.output);
        this.doorBuilder(AetherIIBlocks.SKYROOT_DOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_door").save(this.output);
        this.trapdoorBuilder(AetherIIBlocks.SKYROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        this.buttonBuilder(AetherIIBlocks.SKYROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_button").save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.SKYROOT_STAIRS, AetherIIBlocks.SKYROOT_PLANKS).group("wooden_stairs").save(this.output);
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SKYROOT_SLAB, Ingredient.of(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_PLANKS.get()), has(AetherIIBlocks.SKYROOT_PLANKS.get()))
                .save(this.output);
        this.sign(getter, AetherIIBlocks.SKYROOT_SIGN.get(), AetherIIBlocks.SKYROOT_PLANKS);
        this.hangingSign(getter, AetherIIBlocks.SKYROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_STAIRS.get(), AetherIIBlocks.SKYROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_SLAB.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), 2);
        this.shelf(AetherIIBlocks.SKYROOT_SHELF, AetherIIBlocks.STRIPPED_SKYROOT_LOG);

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
        this.planksFromLog(AetherIIBlocks.GREATROOT_PLANKS.get(), AetherIITags.Items.GREATROOT_LOGS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_masonry_blocks", has(AetherIITags.Items.GREATROOT_DECORATIVE_BLOCKS))
                .save(this.output, name("greatroot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.GREATROOT_FENCE, AetherIIBlocks.GREATROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.GREATROOT_FENCE_GATE, AetherIIBlocks.GREATROOT_PLANKS).save(this.output);
        this.doorBuilder(AetherIIBlocks.GREATROOT_DOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_door").save(this.output);
        this.trapdoorBuilder(AetherIIBlocks.GREATROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        this.buttonBuilder(AetherIIBlocks.GREATROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_button").save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.GREATROOT_STAIRS, AetherIIBlocks.GREATROOT_PLANKS).group("wooden_stairs").save(this.output);
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GREATROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.GREATROOT_PLANKS.get()), has(AetherIIBlocks.GREATROOT_PLANKS.get()))
                .save(this.output);
        this.sign(getter, AetherIIBlocks.GREATROOT_SIGN.get(), AetherIIBlocks.GREATROOT_PLANKS);
        this.hangingSign(getter, AetherIIBlocks.GREATROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_GREATROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_STAIRS.get(), AetherIIBlocks.GREATROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GREATROOT_SLAB.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), 2);
        this.shelf(AetherIIBlocks.GREATROOT_SHELF, AetherIIBlocks.STRIPPED_GREATROOT_LOG);

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
        this.planksFromLog(AetherIIBlocks.WISPROOT_PLANKS.get(), AetherIITags.Items.WISPROOT_LOGS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.WISPROOT_DECORATIVE_BLOCKS))
                .save(this.output, this.name("wisproot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.WISPROOT_FENCE, AetherIIBlocks.WISPROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.WISPROOT_FENCE_GATE, AetherIIBlocks.WISPROOT_PLANKS).save(this.output);
        this.doorBuilder(AetherIIBlocks.WISPROOT_DOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_door").save(this.output);
        this.trapdoorBuilder(AetherIIBlocks.WISPROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        this.buttonBuilder(AetherIIBlocks.WISPROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_button").save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.WISPROOT_STAIRS, AetherIIBlocks.WISPROOT_PLANKS).group("wooden_stairs").save(this.output);
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.WISPROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.WISPROOT_PLANKS.get()), has(AetherIIBlocks.WISPROOT_PLANKS.get()))
                .save(this.output);
        this.sign(getter, AetherIIBlocks.WISPROOT_SIGN.get(), AetherIIBlocks.WISPROOT_PLANKS);
        this.hangingSign(getter, AetherIIBlocks.WISPROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_WISPROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_STAIRS.get(), AetherIIBlocks.WISPROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.WISPROOT_SLAB.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), 2);
        this.shelf(AetherIIBlocks.WISPROOT_SHELF, AetherIIBlocks.STRIPPED_WISPROOT_LOG);

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

        // Amberoot Planks
        this.planksFromLog(AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIITags.Items.AMBEROOT_LOGS, 4);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AMBEROOT_PLANKS.get())
                .group("planks_from_artisanry")
                .requires(AetherIITags.Items.AMBEROOT_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.AMBEROOT_DECORATIVE_BLOCKS))
                .save(this.output, this.name("amberoot_planks_from_artisanry"));
        this.fence(AetherIIBlocks.AMBEROOT_FENCE, AetherIIBlocks.AMBEROOT_PLANKS).save(this.output);
        this.fenceGate(AetherIIBlocks.AMBEROOT_FENCE_GATE, AetherIIBlocks.AMBEROOT_PLANKS).save(this.output);
        this.doorBuilder(AetherIIBlocks.AMBEROOT_DOOR, Ingredient.of(AetherIIBlocks.AMBEROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.AMBEROOT_PLANKS.get()), has(AetherIIBlocks.AMBEROOT_PLANKS.get())).group("wooden_door").save(this.output);
        this.trapdoorBuilder(AetherIIBlocks.AMBEROOT_TRAPDOOR, Ingredient.of(AetherIIBlocks.AMBEROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.AMBEROOT_PLANKS.get()), has(AetherIIBlocks.AMBEROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        this.buttonBuilder(AetherIIBlocks.AMBEROOT_BUTTON.get(), Ingredient.of(AetherIIBlocks.AMBEROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.AMBEROOT_PLANKS.get()), has(AetherIIBlocks.AMBEROOT_PLANKS.get())).group("wooden_button").save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.AMBEROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.AMBEROOT_PLANKS.get())).unlockedBy(getHasName(AetherIIBlocks.AMBEROOT_PLANKS.get()), has(AetherIIBlocks.AMBEROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.stairs(AetherIIBlocks.AMBEROOT_STAIRS, AetherIIBlocks.AMBEROOT_PLANKS).group("wooden_stairs").save(this.output);
        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AMBEROOT_SLAB.get(), Ingredient.of(AetherIIBlocks.AMBEROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherIIBlocks.AMBEROOT_PLANKS.get()), has(AetherIIBlocks.AMBEROOT_PLANKS.get()))
                .save(this.output);
        this.sign(getter, AetherIIBlocks.AMBEROOT_SIGN.get(), AetherIIBlocks.AMBEROOT_PLANKS);
        this.hangingSign(getter, AetherIIBlocks.AMBEROOT_HANGING_SIGN.get(), AetherIIBlocks.STRIPPED_AMBEROOT_LOG);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_STAIRS.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_SLAB.get(), AetherIIBlocks.AMBEROOT_PLANKS.get(), 2);
        this.shelf(AetherIIBlocks.AMBEROOT_SHELF, AetherIIBlocks.STRIPPED_AMBEROOT_LOG);

        // Amberoot Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_FLOORBOARDS.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_HIGHLIGHT.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_SHINGLES.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_BASE_PLANKS.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_TOP_PLANKS.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_BASE_BEAM.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_TOP_BEAM.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_BEAM.get(), AetherIIBlocks.AMBEROOT_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_AMBEROOT_DOOR.get(), AetherIIBlocks.AMBEROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get(), AetherIIBlocks.AMBEROOT_TRAPDOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_FLOORBOARDS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_HIGHLIGHT.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_SMALL_SHINGLES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_BASE_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_TOP_PLANKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_BASE_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_TOP_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_PLANKS.get(), AetherIIBlocks.AMBEROOT_BEAM.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_DOOR.get(), AetherIIBlocks.SECRET_AMBEROOT_DOOR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBEROOT_TRAPDOOR.get(), AetherIIBlocks.SECRET_AMBEROOT_TRAPDOOR.get());

        // Holystone
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE.get())
                .group("holystone")
                .requires(AetherIIBlocks.MOSSY_HOLYSTONE.get())
                .unlockedBy(getHasName(AetherIIBlocks.MOSSY_HOLYSTONE), has(AetherIIBlocks.MOSSY_HOLYSTONE))
                .save(this.output, this.name("holystone_from_mossy"));
        this.stairs(AetherIIBlocks.HOLYSTONE_STAIRS, AetherIIBlocks.HOLYSTONE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get());
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(this.output);
        this.buttonBuilder(AetherIIBlocks.HOLYSTONE_BUTTON.get(), Ingredient.of(AetherIIBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE.get()), has(AetherIIBlocks.HOLYSTONE.get())).save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_STAIRS.get(), AetherIIBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_SLAB.get(), AetherIIBlocks.HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get());

        // Mossy Holystone
        this.stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, AetherIIBlocks.MOSSY_HOLYSTONE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get());
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
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get());

        // Holystone Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get(), AetherIIBlocks.HOLYSTONE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.HOLYSTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.HOLYSTONE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get());
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.HOLYSTONE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), 0.1F, 200).unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_BRICKS.get()), has(AetherIIBlocks.HOLYSTONE_BRICKS.get())).save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.FADED_HOLYSTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_faded_holystone_blocks", has(AetherIITags.Items.FADED_HOLYSTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("faded_holystone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS, AetherIIBlocks.FADED_HOLYSTONE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
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

        // Undershale
        this.stairs(AetherIIBlocks.UNDERSHALE_STAIRS, AetherIIBlocks.UNDERSHALE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get());

        // Undershale Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.UNDERSHALE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.UNDERSHALE_DECORATIVE_BLOCKS))
                .save(this.output, name("undershale_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS, AetherIIBlocks.UNDERSHALE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE.get(), Ingredient.of(AetherIIBlocks.UNDERSHALE_BRICKS.get())).unlockedBy(getHasName(AetherIIBlocks.UNDERSHALE_BRICKS.get()), has(AetherIIBlocks.UNDERSHALE_BRICKS.get())).save(this.output);
        this.buttonBuilder(AetherIIBlocks.UNDERSHALE_BRICK_BUTTON.get(), Ingredient.of(AetherIIBlocks.UNDERSHALE_BRICKS.get())).unlockedBy(getHasName(AetherIIBlocks.UNDERSHALE_BRICKS.get()), has(AetherIIBlocks.UNDERSHALE_BRICKS.get())).save(this.output);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_BRICKS.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get(), AetherIIBlocks.SENTRY_BRICK_STAIRS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get(), AetherIIBlocks.SENTRY_BRICK_SLAB.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.SENTRY_BRICK_WALL.get());

        // Undershale Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_TILE.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_TILE.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), AetherIIBlocks.SENTRY_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_TILE.get(), AetherIIBlocks.SENTRY_TILE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), AetherIIBlocks.SENTRY_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), AetherIIBlocks.SENTRY_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_PILLAR.get(), AetherIIBlocks.SENTRY_PILLAR.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_PILLAR.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_LIGHTSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_TILE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.UNDERSHALE_BRICKS.get(), AetherIIBlocks.SENTRY_PILLAR.get());

        // Sentry Bricks
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SENTRY_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.SENTRY_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.SENTRY_DECORATIVE_BLOCKS))
                .save(this.output, name("sentry_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.SENTRY_BRICK_STAIRS, AetherIIBlocks.SENTRY_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.buttonBuilder(AetherIIBlocks.SENTRY_BUTTON.get(), Ingredient.of(AetherIIBlocks.SENTRY_BRICKS.get())).unlockedBy(getHasName(AetherIIBlocks.SENTRY_BRICKS.get()), has(AetherIIBlocks.SENTRY_BRICKS.get())).save(this.output);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_STAIRS.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.SENTRY_BRICKS.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.SENTRY_BRICKS.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_STAIRS.get(), AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_SLAB.get(), AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICK_WALL.get());

        // Sentry Decorative Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_LIGHTSTONE.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_FLAGSTONES.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_TILE.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_PILLAR.get(), AetherIIBlocks.UNDERSHALE.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_LIGHTSTONE.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_FLAGSTONES.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_TILE.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_FLAGSTONES.get(), AetherIIBlocks.UNDERSHALE_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_TILE.get(), AetherIIBlocks.UNDERSHALE_TILE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(), AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(), AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_PILLAR.get(), AetherIIBlocks.UNDERSHALE_PILLAR.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_LIGHTSTONE.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_FLAGSTONES.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_TILE.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_BRICKS.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BASE_PILLAR.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get(), AetherIIBlocks.SENTRY_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_PILLAR.get(), AetherIIBlocks.SENTRY_BRICKS.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_LIGHTSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_FLAGSTONES.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_TILE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_BASE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_BASE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_BRICKS.get(), AetherIIBlocks.SENTRY_PILLAR.get());

        // Ichorite
        this.stairs(AetherIIBlocks.ICHORITE_STAIRS, AetherIIBlocks.ICHORITE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_SLAB.get(), AetherIIBlocks.ICHORITE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_WALL.get(), AetherIIBlocks.ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_STAIRS.get(), AetherIIBlocks.ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_SLAB.get(), AetherIIBlocks.ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_WALL.get(), AetherIIBlocks.ICHORITE.get());

        // Smooth Ichorite
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.ICHORITE.get()), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, AetherIIBlocks.SMOOTH_ICHORITE.get(), 0.1F, 200).unlockedBy(getHasName(AetherIIBlocks.ICHORITE.get()), has(AetherIIBlocks.ICHORITE.get())).save(this.output);
        this.stairs(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS, AetherIIBlocks.SMOOTH_ICHORITE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_WALL.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get(), AetherIIBlocks.SMOOTH_ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.SMOOTH_ICHORITE_WALL.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());

        // Ichorite Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICKS.get(), AetherIIBlocks.SMOOTH_ICHORITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.ICHORITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.ICHORITE_DECORATIVE_BLOCKS))
                .save(this.output, name("ichorite_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.ICHORITE_BRICK_STAIRS, AetherIIBlocks.ICHORITE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICHORITE_BRICK_SLAB.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICHORITE_BRICK_WALL.get(), AetherIIBlocks.ICHORITE_BRICKS.get());
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
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_ICHORITE_SLAB.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_WALL.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_SLAB.get(), AetherIIBlocks.MARBLED_ICHORITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_ICHORITE_WALL.get(), AetherIIBlocks.MARBLED_ICHORITE.get());

        // Marbled Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_BRICKS.get(), AetherIIBlocks.MARBLED_ICHORITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_ICHORITE.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.MARBLED_ICHORITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.MARBLED_ICHORITE_DECORATIVE_BLOCKS))
                .save(this.output, name("marbled_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.MARBLED_BRICK_STAIRS, AetherIIBlocks.MARBLED_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.MARBLED_BRICK_SLAB.get(), AetherIIBlocks.MARBLED_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.MARBLED_BRICK_WALL.get(), AetherIIBlocks.MARBLED_BRICKS.get());
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

        // Agiosite
        this.stairs(AetherIIBlocks.AGIOSITE_STAIRS, AetherIIBlocks.AGIOSITE).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_STAIRS.get(), AetherIIBlocks.AGIOSITE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_SLAB.get(), AetherIIBlocks.AGIOSITE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get());

        // Agiosite Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get(), AetherIIBlocks.AGIOSITE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.AGIOSITE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.AGIOSITE_DECORATIVE_BLOCKS))
                .save(this.output, name("agiosite_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, AetherIIBlocks.AGIOSITE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AGIOSITE_BRICK_SLAB.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get());
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
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get());

        // Icestone Bricks
        this.polished(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICKS.get(), AetherIIBlocks.ICESTONE.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICKS.get())
                .group("bricks_from_artisanry")
                .requires(AetherIITags.Items.ICESTONE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.ICESTONE_DECORATIVE_BLOCKS))
                .save(this.output, name("icestone_bricks_from_artisanry"));
        this.stairs(AetherIIBlocks.ICESTONE_BRICK_STAIRS, AetherIIBlocks.ICESTONE_BRICKS).save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ICESTONE_BRICK_SLAB.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE_BRICKS.get());
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
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.BLOCKS, AetherIIBlocks.QUICKSOIL_GLASS, AetherIIBlocks.QUICKSOIL, 1, 0.0F).save(this.output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.CRUDE_SCATTERGLASS.get()), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, AetherIIBlocks.SCATTERGLASS.get(), 0.1F, 200).unlockedBy("has_crude_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS.get())).save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.QUICKSOIL_GLASS.get())
                .group("glass_from_artisanry")
                .requires(AetherIITags.Items.QUICKSOIL_GLASS_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.QUICKSOIL_GLASS_DECORATIVE_BLOCKS))
                .save(this.output, name("quicksoil_glass_from_artisanry"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.CRUDE_SCATTERGLASS.get())
                .group("glass_from_artisanry")
                .requires(AetherIITags.Items.CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.CRUDE_SCATTERGLASS_DECORATIVE_BLOCKS))
                .save(this.output, name("crude_scatterglass_from_artisanry"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SCATTERGLASS.get())
                .group("glass_from_artisanry")
                .requires(AetherIITags.Items.SCATTERGLASS_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.SCATTERGLASS_DECORATIVE_BLOCKS))
                .save(this.output, name("scatterglass_from_artisanry"));
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get()), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, AetherIIBlocks.SCATTERGLASS_PANE.get(), 0.1F, 200).unlockedBy("has_crude_scatterglass_pane", has(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get())).save(this.output, name("scatterglass_pane_from_smelting"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.QUICKSOIL_GLASS_PANE.get())
                .group("glass_pane_from_artisanry")
                .requires(AetherIITags.Items.QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.QUICKSOIL_GLASS_PANE_DECORATIVE_BLOCKS))
                .save(this.output, name("quicksoil_glass_pane_from_artisanry"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get())
                .group("glass_pane_from_artisanry")
                .requires(AetherIITags.Items.CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.CRUDE_SCATTERGLASS_PANE_DECORATIVE_BLOCKS))
                .save(this.output, name("crude_scatterglass_pane_from_artisanry"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.SCATTERGLASS_PANE.get())
                .group("glass_pane_from_artisanry")
                .requires(AetherIITags.Items.SCATTERGLASS_PANE_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.SCATTERGLASS_PANE_DECORATIVE_BLOCKS))
                .save(this.output, name("scatterglass_pane_from_artisanry"));
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

        // Infected Guardian Tree
        this.woodFromLogs(AetherIIBlocks.GUARDIAN_WOOD.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.woodFromLogs(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.woodFromLogs(AetherIIBlocks.INFECTED_WOOD.get(), AetherIIBlocks.INFECTED_LOG.get());
        this.woodFromLogs(AetherIIBlocks.STRIPPED_INFECTED_WOOD.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get());

        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.GUARDIAN_LOG.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.GUARDIAN_WOOD.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.INFECTED_LOG_SLAB.get(), AetherIIBlocks.INFECTED_LOG.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.INFECTED_WOOD_SLAB.get(), AetherIIBlocks.INFECTED_WOOD.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_WOOD.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.GUARDIAN_LOG.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.GUARDIAN_WOOD.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GUARDIAN_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_LOG.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_GUARDIAN_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.INFECTED_LOG_SLAB.get(), AetherIIBlocks.INFECTED_LOG.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.INFECTED_WOOD_SLAB.get(), AetherIIBlocks.INFECTED_WOOD.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_INFECTED_LOG_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_LOG.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.STRIPPED_INFECTED_WOOD_SLAB.get(), AetherIIBlocks.STRIPPED_INFECTED_WOOD.get(), 2);

        this.planksFromLog(AetherIIBlocks.GUARDIAN_ROOTS.get(), AetherIITags.Items.GUARDIAN_LOGS, 4);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.LUCENT_GUARDIAN_ROOTS, 4)
                .define('#', AetherIIBlocks.GUARDIAN_ROOTS)
                .define('A', AetherIITags.Items.GEMS_AMBROSIUM)
                .pattern("#A")
                .pattern("A#")
                .unlockedBy(getHasName(AetherIIBlocks.GUARDIAN_ROOTS), has(AetherIIBlocks.GUARDIAN_ROOTS))
                .save(this.output, this.name(getSimpleRecipeName(AetherIIBlocks.LUCENT_GUARDIAN_ROOTS)));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GUARDIAN_LAMP)
                .define('#', AetherIITags.Items.GUARDIAN_LOGS)
                .define('A', AetherIITags.Items.GEMS_AMBROSIUM)
                .pattern("#")
                .pattern("A")
                .pattern("#")
                .unlockedBy(getHasName(AetherIIBlocks.GUARDIAN_LAMP), has(AetherIITags.Items.GUARDIAN_LOGS))
                .save(this.output, this.name(getSimpleRecipeName(AetherIIBlocks.GUARDIAN_LAMP)));

        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ROTSHROOM_SLAB.get(), AetherIIBlocks.ROTSHROOM_BLOCK.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ROTSHROOM_SLAB.get(), AetherIIBlocks.ROTSHROOM_BLOCK.get(), 2);

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
        this.washDyedBlock(wool, AetherIIBlocks.CLOUDWOOL.asItem(), "wool");

        // Carpet
        this.colorBlockWithDye(dyes, carpet, AetherIIBlocks.CLOUDWOOL_CARPET.asItem(), "carpet");
        this.washDyedBlock(carpet, AetherIIBlocks.CLOUDWOOL_CARPET.asItem(), "carpet");
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

        // Roofing
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.CLOUDWOOL_ROOFING, 8)
                .define('#', AetherIIBlocks.CLOUDWOOL)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherIIBlocks.CLOUDWOOL), has(AetherIIBlocks.CLOUDWOOL))
                .save(this.output);

        // Skyroot Beds
        this.colorBlockWithDye(dyes, bed, AetherIIBlocks.SKYROOT_BED.asItem(), "bed");
        this.washDyedBlock(bed, AetherIIBlocks.SKYROOT_BED.asItem(), "bed");
        this.bed(getter, AetherIIBlocks.SKYROOT_BED, AetherIIBlocks.CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.WHITE_SKYROOT_BED, AetherIIBlocks.WHITE_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.ORANGE_SKYROOT_BED, AetherIIBlocks.ORANGE_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.MAGENTA_SKYROOT_BED, AetherIIBlocks.MAGENTA_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED, AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.YELLOW_SKYROOT_BED, AetherIIBlocks.YELLOW_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.LIME_SKYROOT_BED, AetherIIBlocks.LIME_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.PINK_SKYROOT_BED, AetherIIBlocks.PINK_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.GRAY_SKYROOT_BED, AetherIIBlocks.GRAY_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED, AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.CYAN_SKYROOT_BED, AetherIIBlocks.CYAN_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.PURPLE_SKYROOT_BED, AetherIIBlocks.PURPLE_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.BLUE_SKYROOT_BED, AetherIIBlocks.BLUE_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.BROWN_SKYROOT_BED, AetherIIBlocks.BROWN_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.GREEN_SKYROOT_BED, AetherIIBlocks.GREEN_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.RED_SKYROOT_BED, AetherIIBlocks.RED_CLOUDWOOL);
        this.bed(getter, AetherIIBlocks.BLACK_SKYROOT_BED, AetherIIBlocks.BLACK_CLOUDWOOL);

        // Arkenium Blocks
        this.doorBuilder(AetherIIBlocks.ARKENIUM_DOOR, Ingredient.of(AetherIIItems.ARKENIUM_PLATE.get())).unlockedBy(getHasName(AetherIIItems.ARKENIUM_PLATE.get()), has(AetherIIItems.ARKENIUM_PLATE.get())).save(this.output);
        this.twoByTwoPacker(RecipeCategory.REDSTONE, AetherIIBlocks.ARKENIUM_TRAPDOOR, AetherIIItems.ARKENIUM_PLATE);
        this.shaped(RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_BARS, 32)
                .define('#', AetherIITags.Items.INGOTS_ARKENIUM)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_arkenium_plate", this.has(AetherIITags.Items.INGOTS_ARKENIUM))
                .save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.FLORAL_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.CURVED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_BARS.get(), AetherIIBlocks.FLORAL_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_BARS.get(), AetherIIBlocks.PATTERNED_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_BARS.get(), AetherIIBlocks.CURVED_ARKENIUM_BARS.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARKENIUM_BARS.get())
                .group("bars_from_artisanry")
                .requires(AetherIITags.Items.ARKENIUM_BARS_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.ARKENIUM_BARS_DECORATIVE_BLOCKS))
                .save(this.output, name("arkenium_bars_from_artisanry"));

        // Rustic Arkenium Blocks
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get(), AetherIIBlocks.ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_FLORAL_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_PATTERNED_ARKENIUM_BARS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get(), AetherIIBlocks.RUSTIC_CURVED_ARKENIUM_BARS.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.RUSTIC_ARKENIUM_BARS.get())
                .group("bars_from_artisanry")
                .requires(AetherIITags.Items.RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS)
                .unlockedBy("has_artisanry_blocks", has(AetherIITags.Items.RUSTIC_ARKENIUM_BARS_DECORATIVE_BLOCKS))
                .save(this.output, name("rustic_arkenium_bars_from_artisanry"));

        // Inert Mineral Blocks
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.INERT_ARKENIUM.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.INERT_ARKENIUM_BLOCK, "inert_arkenium_from_inert_arkenium_block", "inert_arkenium");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.INERT_GRAVITITE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.INERT_GRAVITITE_BLOCK, "inert_gravitite_from_inert_gravitite_block", "inert_gravitite");

        // Mineral Blocks
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.AMBROSIUM_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.AMBROSIUM_BLOCK, "ambrosium_shard_from_ambrosium_block", "ambrosium_shard");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.ZANITE_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ZANITE_BLOCK, "zanite_gemstone_from_zanite_block", "zanite_gemstone");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.ARKENIUM_BLOCK, "arkenium_plate_from_arkenium_block", "arkenium_plate");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.GRAVITITE_PLATE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GRAVITITE_BLOCK, "gravitite_plate_from_gravitite_block", "gravitite_plate");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.GLINT_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GLINT_BLOCK, "glint_gemstone_from_glint_block", "glint_gemstone");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.CORROBONITE_CRYSTAL.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.CORROBONITE_BLOCK, "corrobonite_crystal_from_corrobonite_block", "corrobonite_crystal");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.GOLDEN_AMBER.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GOLDEN_AMBER_BLOCK, "golden_amber_from_golden_amber_block", "golden_amber");

        // Storage Blocks
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherIIItems.BRETTL_GRASS.get(), RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.BRETTL_GRASS_BUNDLE, "brettl_grass", "brettl_grass");
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SWET_GEL.get(), 4).requires(AetherIIBlocks.GEL_BLOCK).group("swet_gel").unlockedBy(getHasName(AetherIIBlocks.GEL_BLOCK), this.has(AetherIIBlocks.GEL_BLOCK)).save(output, this.name("swet_gel"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherIIBlocks.GEL_BLOCK).define('#', AetherIIItems.SWET_GEL.get()).pattern("##").pattern("##").unlockedBy(getHasName(AetherIIItems.SWET_GEL.get()), this.has(AetherIIItems.SWET_GEL.get())).save(output, this.name(getSimpleRecipeName(AetherIIBlocks.GEL_BLOCK)));

        // Arilum Lantern
        this.arilumLantern(getter, AetherIIBlocks.WHITE_ARILUM_LANTERN, Items.WHITE_DYE);
        this.arilumLantern(getter, AetherIIBlocks.ORANGE_ARILUM_LANTERN, Items.ORANGE_DYE);
        this.arilumLantern(getter, AetherIIBlocks.MAGENTA_ARILUM_LANTERN, Items.MAGENTA_DYE);
        this.arilumLantern(getter, AetherIIBlocks.LIGHT_BLUE_ARILUM_LANTERN, Items.LIGHT_BLUE_DYE);
        this.arilumLantern(getter, AetherIIBlocks.YELLOW_ARILUM_LANTERN, Items.YELLOW_DYE);
        this.arilumLantern(getter, AetherIIBlocks.LIME_ARILUM_LANTERN, Items.LIME_DYE);
        this.arilumLantern(getter, AetherIIBlocks.PINK_ARILUM_LANTERN, Items.PINK_DYE);
        this.arilumLantern(getter, AetherIIBlocks.GRAY_ARILUM_LANTERN, Items.GRAY_DYE);
        this.arilumLantern(getter, AetherIIBlocks.LIGHT_GRAY_ARILUM_LANTERN, Items.LIGHT_GRAY_DYE);
        this.arilumLantern(getter, AetherIIBlocks.CYAN_ARILUM_LANTERN, Items.CYAN_DYE);
        this.arilumLantern(getter, AetherIIBlocks.PURPLE_ARILUM_LANTERN, Items.PURPLE_DYE);
        this.arilumLantern(getter, AetherIIBlocks.BLUE_ARILUM_LANTERN, Items.BLUE_DYE);
        this.arilumLantern(getter, AetherIIBlocks.BROWN_ARILUM_LANTERN, Items.BROWN_DYE);
        this.arilumLantern(getter, AetherIIBlocks.GREEN_ARILUM_LANTERN, Items.GREEN_DYE);
        this.arilumLantern(getter, AetherIIBlocks.RED_ARILUM_LANTERN, Items.RED_DYE);
        this.arilumLantern(getter, AetherIIBlocks.BLACK_ARILUM_LANTERN, Items.BLACK_DYE);

        // Utility
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBROSIUM_TORCH.get(), 4)
                .define('A', AetherIITags.Items.GEMS_AMBROSIUM)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .pattern("A")
                .pattern("/")
                .unlockedBy("has_ambrosium_shard", has(AetherIITags.Items.GEMS_AMBROSIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_LANTERN.get())
                .define('#', AetherIIItems.ARKENIUM_CHIP.get())
                .define('/', AetherIIBlocks.AMBROSIUM_TORCH)
                .pattern("###")
                .pattern("#/#")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIItems.ARKENIUM_PLATE.get()), has(AetherIIItems.ARKENIUM_PLATE.get()))
                .save(this.output);
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get(), AetherIIBlocks.ARKENIUM_LANTERN.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_LANTERN.get(), AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get());
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_CHAIN.get(), 3)
                .define('#', AetherIIItems.ARKENIUM_PLATE.get())
                .define('/', AetherIIItems.ARKENIUM_CHIP.get())
                .pattern("/")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(AetherIIItems.ARKENIUM_PLATE.get()), has(AetherIIItems.ARKENIUM_PLATE.get()))
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
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_SMOKER)
                .define('#', ItemTags.LOGS)
                .define('F', AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .pattern(" # ")
                .pattern("#F#")
                .pattern(" # ")
                .unlockedBy(getHasName(Blocks.SMOKER), has(ItemTags.LOGS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.BLAST_FURNACE)
                .define('#', Blocks.SMOOTH_STONE)
                .define('I', Items.IRON_INGOT)
                .define('F', AetherIIBlocks.HOLYSTONE_FURNACE.get())
                .pattern("III")
                .pattern("IFI")
                .pattern("###")
                .unlockedBy(getHasName(Blocks.BLAST_FURNACE), has(Blocks.SMOOTH_STONE))
                .save(this.output, this.name("blast_furnace_from_holystone_furnace"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBER_HOURGLASS.get())
                .define('P', AetherIITags.Items.PLANKS_CRAFTING)
                .define('S', AetherIITags.Items.RODS_SKYROOT)
                .define('A', AetherIITags.Items.GEMS_AMBER)
                .pattern("APA")
                .pattern("SAS")
                .pattern("APA")
                .unlockedBy(getHasName(AetherIIBlocks.AMBER_HOURGLASS.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ALTAR.get())
                .define('U', AetherIIBlocks.UNDERSHALE)
                .define('Z', AetherIITags.Items.GEMS_ZANITE)
                .pattern("ZZZ")
                .pattern(" Z ")
                .pattern("UUU")
                .unlockedBy(getHasName(AetherIIBlocks.ALTAR.get()), has(AetherIITags.Items.GEMS_ZANITE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARTISANS_BENCH.get())
                .define('A', AetherIITags.Items.INGOTS_ARKENIUM)
                .define('P', AetherIITags.Items.PLANKS_CRAFTING)
                .define('H', AetherIITags.Items.STONE_CRAFTING)
                .pattern("AAA")
                .pattern("PPP")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ARTISANS_BENCH.get()), has(AetherIITags.Items.INGOTS_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ARKENIUM_FORGE.get())
                .define('H', AetherIITags.Items.STONE_CRAFTING)
                .define('A', AetherIITags.Items.INGOTS_ARKENIUM)
                .pattern("AAA")
                .pattern(" A ")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherIIBlocks.ARKENIUM_FORGE.get()), has(AetherIITags.Items.INGOTS_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.ALKAHEST_PURIFIER.get())
                .define('A', AetherIITags.Items.INGOTS_ARKENIUM)
                .define('S', AetherIIBlocks.SCATTERGLASS.get())
                .pattern("ASA")
                .pattern("ASA")
                .pattern("AAA")
                .unlockedBy(getHasName(AetherIIBlocks.ALKAHEST_PURIFIER.get()), has(AetherIITags.Items.INGOTS_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.AMBROSIUM_CAMPFIRE.get())
                .define('R', AetherIIBlocks.HOLYSTONE_ROCK.get())
                .define('S', AetherIITags.Items.RODS_SKYROOT)
                .define('#', AetherIITags.Items.GEMS_AMBROSIUM)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("RRR")
                .unlockedBy("has_stick", has(AetherIITags.Items.RODS_SKYROOT))
                .unlockedBy("has_ambrosium_shard", has(AetherIITags.Items.GEMS_AMBROSIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_CHEST.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_CHEST.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_BARREL.get())
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .define('/', ItemTags.WOODEN_SLABS)
                .pattern("#/#")
                .pattern("# #")
                .pattern("#/#")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_BARREL.get()), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SENTRY_CRATE.get())
                .define('#', AetherIIBlocks.SENTRY_BRICKS)
                .define('A', AetherIITags.Items.INGOTS_ARKENIUM)
                .pattern("#A#")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(AetherIIBlocks.SENTRY_CRATE.get()), has(AetherIIBlocks.SENTRY_BRICKS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.HOLYSTONE_VASE.get())
                .define('#', AetherIIBlocks.HOLYSTONE)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy(getHasName(AetherIIBlocks.HOLYSTONE_VASE.get()), has(AetherIIBlocks.HOLYSTONE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.VERADEXIAN_VASE.get())
                .define('#', AetherIIBlocks.SMOOTH_ICHORITE)
                .define('Q', Items.QUARTZ)
                .pattern("Q#Q")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy(getHasName(AetherIIBlocks.VERADEXIAN_VASE.get()), has(AetherIIBlocks.SMOOTH_ICHORITE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.BREXALLEN_VASE.get())
                .define('#', AetherIIBlocks.UNDERSHALE)
                .define('A', AetherIIBlocks.AGIOSITE)
                .pattern("A#A")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy(getHasName(AetherIIBlocks.BREXALLEN_VASE.get()), has(AetherIIBlocks.AGIOSITE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.SKYROOT_LADDER.get(), 3)
                .define('#', AetherIITags.Items.RODS_SKYROOT)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(AetherIIBlocks.SKYROOT_LADDER.get()), has(AetherIITags.Items.RODS_SKYROOT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIBlocks.CLOUDWOOL_BEDROLL.get(), 2)
                .define('W', ItemTags.WOOL)
                .define('C', AetherIIItems.CLOUDTWINE)
                .pattern("WCW")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Items.ITEM_FRAME, 1)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .define('#', AetherIIItems.BEAST_PELT)
                .pattern("///")
                .pattern("/#/")
                .pattern("///")
                .unlockedBy("has_leather", has(AetherIIItems.BEAST_PELT))
                .save(this.output, this.name("item_frame_from_beast_pelt"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.JUKEBOX)
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .define('G', AetherIIItems.GRAVITITE_PLATE)
                .pattern("###")
                .pattern("#G#")
                .pattern("###")
                .unlockedBy(getHasName(Blocks.JUKEBOX), has(AetherIITags.Items.PLANKS_CRAFTING))
                .save(this.output, this.name("jukebox_from_gravitite_plate"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, AetherIIBlocks.HOLYSTONE_LEVER)
                .define('#', AetherIIBlocks.HOLYSTONE)
                .define('X', AetherIITags.Items.RODS_SKYROOT)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_holystone", this.has(AetherIIBlocks.HOLYSTONE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Items.ARMOR_STAND)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .define('_', AetherIIBlocks.HOLYSTONE_SLAB)
                .pattern("///")
                .pattern(" / ")
                .pattern("/_/")
                .unlockedBy("has_holystone_slab", this.has(AetherIIBlocks.HOLYSTONE_SLAB))
                .save(this.output, this.name("armor_stand_from_holystone"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.DAYLIGHT_DETECTOR)
                .define('Q', Items.QUARTZ)
                .define('G', AetherIIBlocks.SCATTERGLASS)
                .define('W', ItemTags.WOODEN_SLABS)
                .pattern("GGG")
                .pattern("QQQ")
                .pattern("WWW")
                .unlockedBy("has_quartz", this.has(Items.QUARTZ))
                .save(this.output, this.name("daylight_detector_from_scatterglass"));

        // Bookshelves
        this.bookshelf(getter, AetherIIBlocks.SKYROOT_BOOKSHELF, AetherIIBlocks.SKYROOT_PLANKS);
        this.bookshelf(getter, AetherIIBlocks.GREATROOT_BOOKSHELF, AetherIIBlocks.GREATROOT_PLANKS);
        this.bookshelf(getter, AetherIIBlocks.WISPROOT_BOOKSHELF, AetherIIBlocks.WISPROOT_PLANKS);
        this.bookshelf(getter, AetherIIBlocks.AMBEROOT_BOOKSHELF, AetherIIBlocks.AMBEROOT_PLANKS);
        this.bookshelf(getter, AetherIIBlocks.HOLYSTONE_BOOKSHELF.get(), AetherIIBlocks.HOLYSTONE_BRICKS);

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

        this.makePickaxeWithTag(AetherIIItems.ARKENIUM_PICKAXE, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeAxeWithTag(AetherIIItems.ARKENIUM_AXE, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeShovelWithTag(AetherIIItems.ARKENIUM_SHOVEL, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeHoeWithTag(AetherIIItems.ARKENIUM_TROWEL, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);

        this.makePickaxeWithTag(AetherIIItems.GRAVITITE_PICKAXE, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makeAxeWithTag(AetherIIItems.GRAVITITE_AXE, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makeShovelWithTag(AetherIIItems.GRAVITITE_SHOVEL, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makeHoeWithTag(AetherIIItems.GRAVITITE_TROWEL, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.ZANITE_SHEARS.get())
                .define('#', AetherIITags.Items.GEMS_ZANITE)
                .pattern(" #")
                .pattern("# ")
                .unlockedBy("has_zanite", has(AetherIITags.Items.GEMS_ZANITE))
                .save(this.output);

        // Combat
        this.makeSwordWithTag(AetherIIItems.SKYROOT_SHORTSWORD, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeHammerWithTag(AetherIIItems.SKYROOT_HAMMER, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makePikeWithTag(AetherIIItems.SKYROOT_PIKE, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.SKYROOT_CROSSBOW, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);

        this.makeSwordWithTag(AetherIIItems.HOLYSTONE_SHORTSWORD, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeHammerWithTag(AetherIIItems.HOLYSTONE_HAMMER, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makePikeWithTag(AetherIIItems.HOLYSTONE_PIKE, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.HOLYSTONE_CROSSBOW, AetherIITags.Items.CRAFTS_HOLYSTONE_TOOLS, "has_stone").save(this.output);

        this.makeSwordWithTag(AetherIIItems.ZANITE_SHORTSWORD, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeHammerWithTag(AetherIIItems.ZANITE_HAMMER, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makePikeWithTag(AetherIIItems.ZANITE_PIKE, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.ZANITE_CROSSBOW, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);

        this.makeSwordWithTag(AetherIIItems.ARKENIUM_SHORTSWORD, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeHammerWithTag(AetherIIItems.ARKENIUM_HAMMER, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makePikeWithTag(AetherIIItems.ARKENIUM_PIKE, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.ARKENIUM_CROSSBOW, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);

        this.makeSwordWithTag(AetherIIItems.GRAVITITE_SHORTSWORD, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makeHammerWithTag(AetherIIItems.GRAVITITE_HAMMER, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makePikeWithTag(AetherIIItems.GRAVITITE_PIKE, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);
        this.makeCrossbowWithTag(AetherIIItems.GRAVITITE_CROSSBOW, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);

        this.makeShieldWithTag(AetherIIItems.SKYROOT_SHIELD, AetherIITags.Items.CRAFTS_SKYROOT_TOOLS, "has_planks").save(this.output);
        this.makeShieldWithItem(AetherIIItems.BURRUKAI_PLATE_SHIELD, AetherIIItems.BURRUKAI_PLATE.get(), "has_burrukai_plate").save(this.output);
        this.makeShieldWithTag(AetherIIItems.ZANITE_SHIELD, AetherIITags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeShieldWithTag(AetherIIItems.ARKENIUM_SHIELD, AetherIITags.Items.INGOTS_ARKENIUM, "has_arkenium").save(this.output);
        this.makeShieldWithTag(AetherIIItems.GRAVITITE_SHIELD, AetherIITags.Items.INGOTS_GRAVITITE, "has_gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.DART_SHOOTER)
                .define('A', AetherIIItems.GOLDEN_AMBER)
                .define('S', AetherIITags.Items.CRAFTS_SKYROOT_TOOLS)
                .pattern("A  ")
                .pattern(" SA")
                .pattern("  S")
                .unlockedBy("has_amber", has(AetherIIItems.GOLDEN_AMBER))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.AMBER_DARTS.get(), 4)
                .define('A', AetherIIItems.GOLDEN_AMBER)
                .define('/', AetherIITags.Items.RODS_SKYROOT)
                .pattern("A  ")
                .pattern(" /A")
                .pattern(" A ")
                .unlockedBy("has_amber", has(AetherIIItems.GOLDEN_AMBER))
                .save(this.output);
        this.makeDartsWithEffect(AetherIIItems.AMBER_DARTS, AetherIIItems.AECHOR_PETAL, EffectBuildupPresets.TOXIN);
        this.makeDartsWithEffect(AetherIIItems.AMBER_DARTS, AetherIIItems.COCKATRICE_FEATHER, EffectBuildupPresets.VENOM);
        this.makeDartsWithEffect(AetherIIItems.AMBER_DARTS, AetherIIItems.IRRADIATED_DUST, EffectBuildupPresets.AMBROSIUM_POISONING);

        this.loadDartShooter(AetherIIItems.DART_SHOOTER, AetherIIItems.AMBER_DARTS, EffectBuildupPresets.VULNERABILITY);
        this.loadDartShooter(AetherIIItems.DART_SHOOTER, AetherIIItems.AMBER_DARTS, EffectBuildupPresets.TOXIN);
        this.loadDartShooter(AetherIIItems.DART_SHOOTER, AetherIIItems.AMBER_DARTS, EffectBuildupPresets.VENOM);
        this.loadDartShooter(AetherIIItems.DART_SHOOTER, AetherIIItems.AMBER_DARTS, EffectBuildupPresets.AMBROSIUM_POISONING);

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
        this.makeHelmet(getter, AetherIIItems.BEAST_PELT_HELMET, AetherIIItems.BEAST_PELT).save(this.output);
        this.makeChestplate(getter, AetherIIItems.BEAST_PELT_CHESTPLATE, AetherIIItems.BEAST_PELT).save(this.output);
        this.makeLeggings(getter, AetherIIItems.BEAST_PELT_LEGGINGS, AetherIIItems.BEAST_PELT).save(this.output);
        this.makeBoots(getter, AetherIIItems.BEAST_PELT_BOOTS, AetherIIItems.BEAST_PELT).save(this.output);
        this.makeGloves(getter, AetherIIItems.BEAST_PELT_GLOVES, AetherIIItems.BEAST_PELT).save(this.output);

        this.makeHelmet(getter, AetherIIItems.BURRUKAI_PLATE_HELMET, AetherIIItems.BURRUKAI_PLATE).save(this.output);
        this.makeChestplate(getter, AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, AetherIIItems.BURRUKAI_PLATE).save(this.output);
        this.makeLeggings(getter, AetherIIItems.BURRUKAI_PLATE_LEGGINGS, AetherIIItems.BURRUKAI_PLATE).save(this.output);
        this.makeBoots(getter, AetherIIItems.BURRUKAI_PLATE_BOOTS, AetherIIItems.BURRUKAI_PLATE).save(this.output);
        this.makeGloves(getter, AetherIIItems.BURRUKAI_PLATE_GLOVES, AetherIIItems.BURRUKAI_PLATE).save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.ZANITE_HELMET, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.ZANITE_CHESTPLATE, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.ZANITE_LEGGINGS, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.ZANITE_BOOTS, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.ZANITE_GLOVES, AetherIITags.Items.GEMS_ZANITE, "zanite").save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.ARKENIUM_HELMET, AetherIITags.Items.INGOTS_ARKENIUM, "arkenium").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.ARKENIUM_CHESTPLATE, AetherIITags.Items.INGOTS_ARKENIUM, "arkenium").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.ARKENIUM_LEGGINGS, AetherIITags.Items.INGOTS_ARKENIUM, "arkenium").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.ARKENIUM_BOOTS, AetherIITags.Items.INGOTS_ARKENIUM, "arkenium").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.ARKENIUM_GLOVES, AetherIITags.Items.INGOTS_ARKENIUM, "arkenium").save(this.output);

        this.makeHelmetWithTag(getter, AetherIIItems.GRAVITITE_HELMET, AetherIITags.Items.INGOTS_GRAVITITE, "gravitite").save(this.output);
        this.makeChestplateWithTag(getter, AetherIIItems.GRAVITITE_CHESTPLATE, AetherIITags.Items.INGOTS_GRAVITITE, "gravitite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherIIItems.GRAVITITE_LEGGINGS, AetherIITags.Items.INGOTS_GRAVITITE, "gravitite").save(this.output);
        this.makeBootsWithTag(getter, AetherIIItems.GRAVITITE_BOOTS, AetherIITags.Items.INGOTS_GRAVITITE, "gravitite").save(this.output);
        this.makeGlovesWithTag(getter, AetherIIItems.GRAVITITE_GLOVES, AetherIITags.Items.INGOTS_GRAVITITE, "gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.NEPTUNE_HELMET, 1)
                .define('N', AetherIIItems.NEPTUNE_SCALE)
                .define('#', AetherIIItems.ZANITE_HELMET)
                .pattern("NNN")
                .pattern("N#N")
                .pattern("NNN")
                .unlockedBy("neptune", has(AetherIIItems.NEPTUNE_SCALE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.NEPTUNE_CHESTPLATE, 1)
                .define('N', AetherIIItems.NEPTUNE_SCALE)
                .define('#', AetherIIItems.ZANITE_CHESTPLATE)
                .pattern("NNN")
                .pattern("N#N")
                .pattern("NNN")
                .unlockedBy("neptune", has(AetherIIItems.NEPTUNE_SCALE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.NEPTUNE_LEGGINGS, 1)
                .define('N', AetherIIItems.NEPTUNE_SCALE)
                .define('#', AetherIIItems.ZANITE_LEGGINGS)
                .pattern("NNN")
                .pattern("N#N")
                .pattern("NNN")
                .unlockedBy("neptune", has(AetherIIItems.NEPTUNE_SCALE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.NEPTUNE_BOOTS, 1)
                .define('N', AetherIIItems.NEPTUNE_SCALE)
                .define('#', AetherIIItems.ZANITE_BOOTS)
                .pattern("NNN")
                .pattern("N#N")
                .pattern("NNN")
                .unlockedBy("neptune", has(AetherIIItems.NEPTUNE_SCALE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.NEPTUNE_GLOVES, 1)
                .define('N', AetherIIItems.NEPTUNE_SCALE)
                .define('#', AetherIIItems.ZANITE_GLOVES)
                .pattern("NNN")
                .pattern("N#N")
                .pattern("NNN")
                .unlockedBy("neptune", has(AetherIIItems.NEPTUNE_SCALE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.SENTRY_BOOTS, 1)
                .define('S', AetherIIItems.SENTRY_SERVO)
                .define('#', AetherIIItems.ZANITE_BOOTS)
                .pattern("SSS")
                .pattern("S#S")
                .pattern("SSS")
                .unlockedBy("sentry", has(AetherIIItems.SENTRY_SERVO))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.HAMMER_OF_DEMOLITION, 1)
                .define('S', AetherIIItems.SENTRY_SERVO)
                .define('#', AetherIIItems.ZANITE_HAMMER)
                .pattern("SSS")
                .pattern("S#S")
                .pattern("SSS")
                .unlockedBy("sentry", has(AetherIIItems.SENTRY_SERVO))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherIIItems.KINETIC_THRUSTERS, 1)
                .define('S', AetherIIItems.SENTRY_SERVO)
                .define('#', AetherIIItems.ZANITE_GEMSTONE)
                .pattern("SSS")
                .pattern("S#S")
                .pattern("SSS")
                .unlockedBy("sentry", has(AetherIIItems.SENTRY_SERVO))
                .save(this.output);

        // Accessories
        this.makePendantWithTag(getter, AetherIIItems.ZANITE_PENDANT, AetherIITags.Items.GEMS_ZANITE, Ingredient.of(AetherIIItems.CLOUDTWINE), "zanite").save(this.output);
        this.makePendant(getter, AetherIIItems.ICESTONE_PENDANT, AetherIIBlocks.ICESTONE.asItem(), Ingredient.of(AetherIIItems.CLOUDTWINE)).save(this.output);

        // Foods
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.FOOD, AetherIIItems.ENCHANTED_BLUEBERRY, AetherIIItems.BLUEBERRY, 1, 0.0F).save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.FOOD, AetherIIItems.ENCHANTED_ORANGE, AetherIIItems.ORANGE, 1, 0.0F).save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.FOOD, AetherIIItems.ENCHANTED_WYNDBERRY, AetherIIItems.WYNDBERRY, 2, 0.0F).save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.FOOD, AetherIIItems.ENCHANTED_SWET_JELLY, AetherIIItems.SWET_JELLY, 1, 0.0F).save(this.output);
        this.foodCooking(AetherIIItems.PRISMALLARD_EGG, AetherIIItems.FRIED_PRISMALLARD_EGG, 0.35F, this.output);
        this.foodCooking(AetherIIItems.PRISMALLARD_LEG, AetherIIItems.PRISMALLARD_ROAST, 0.35F, this.output);
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
                .save(this.output, this.name("skyroot_stick_from_planks"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_STICK.get(), 2)
                .group("sticks")
                .requires(AetherIIBlocks.SKYROOT_TWIG)
                .unlockedBy("has_twig", has(AetherIIBlocks.SKYROOT_TWIG))
                .save(this.output, this.name("skyroot_stick_from_twig"));
        this.nineBlockStorageRecipes(
                RecipeCategory.MISC, AetherIIItems.ARKENIUM_CHIP, RecipeCategory.MISC, AetherIIItems.ARKENIUM_PLATE, "aether_ii:arkenium_plate_from_chips", "arkenium_plate", "aether_ii:arkenium_chips_from_plate", "arkenium_chip"
        );
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.MOA_FEED.get(), 3)
                .requires(AetherIIItems.SKYROOT_PINECONE)
                .requires(AetherIIItems.AECHOR_PETAL)
                .unlockedBy("has_skyroot_pinecone", has(AetherIIItems.SKYROOT_PINECONE))
                .unlockedBy("has_aechor_petal", has(AetherIIItems.AECHOR_PETAL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SCATTERGLASS_SHARD.get(), 4)
                .requires(AetherIIBlocks.CRUDE_SCATTERGLASS)
                .unlockedBy("has_scatterglass", has(AetherIIBlocks.CRUDE_SCATTERGLASS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, Items.PAPER, 3)
                .define('#', AetherIIItems.BRETTL_CANE)
                .pattern("###")
                .unlockedBy("has_brettl_cane", has(AetherIIItems.BRETTL_CANE))
                .save(this.output, this.name("paper_from_brettl_cane"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.BRETTL_ROPE.get(), 2)
                .define('#', AetherIIItems.BRETTL_GRASS)
                .pattern("  #")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_brettl_grass", has(AetherIIItems.BRETTL_GRASS))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, Items.BOOK)
                .requires(Items.PAPER, 3)
                .requires(AetherIIItems.BEAST_PELT)
                .unlockedBy("has_paper", this.has(Items.PAPER))
                .save(this.output, this.name("book_from_beast_pelt"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SKYROOT_BUCKET.get(), 1)
                .define('#', AetherIITags.Items.CRAFTS_SKYROOT_TOOLS)
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_planks", has(AetherIITags.Items.CRAFTS_SKYROOT_TOOLS))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.ARKENIUM_CANISTER.get(), 2)
                .define('#', AetherIITags.Items.INGOTS_ARKENIUM)
                .define('S', AetherIIBlocks.SCATTERGLASS.get())
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_arkenium", has(AetherIITags.Items.INGOTS_ARKENIUM))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.SCATTERGLASS_VIAL.get(), 4)
                .define('#', AetherIIBlocks.SCATTERGLASS.get())
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_scatterglass", has(AetherIIBlocks.SCATTERGLASS.get()))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter,RecipeCategory.MISC, Items.GLOWSTONE_DUST, 4)
                .define('#', AetherIITags.Items.GEMS_AMBROSIUM)
                .define('@', Tags.Items.GEMS_QUARTZ)
                .pattern("#@")
                .pattern("@#")
                .unlockedBy("has_ambrosium_shard", has(AetherIITags.Items.GEMS_AMBROSIUM))
                .unlockedBy("has_quartz", has(Tags.Items.GEMS_QUARTZ))
                .save(this.output, this.name("aether_glowstone_dust"));
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.MUSIC_PLAYER.get())
                .define('A', AetherIIItems.GOLDEN_AMBER)
                .define('F', AetherIIBlocks.FERROSITE.get())
                .pattern(" AF")
                .pattern("A F")
                .pattern(" AF")
                .unlockedBy("has_disc", has(AetherIITags.Items.ENGRAVED_DISCS))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, Items.QUARTZ)
                .requires(DataComponentIngredient.of(false, new ItemStackTemplate(Items.QUARTZ, 1, DataComponentPatch.builder().set(DataComponents.ITEM_NAME, Component.translatable("item.aether_ii.aether_quartz")).build())))
                .unlockedBy("has_aether_quartz", has(Items.QUARTZ))
                .save(this.output, this.name("quartz_from_aether_quartz"));

        this.smeltingOreRecipe(Items.QUARTZ, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(), 0.5F).group("quartz").save(this.output, this.name("quartz_from_smelting_holystone_quartz_ore"));
        this.blastingOreRecipe(Items.QUARTZ, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get(), 0.5F).group("quartz").save(this.output, this.name("quartz_from_blasting_holystone_quartz_ore"));
        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_smelting"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_blasting"));
        this.smeltingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_smelting_undershale_ambrosium_ore"));
        this.blastingOreRecipe(AetherIIItems.AMBROSIUM_SHARD.get(), AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get(), 0.1F).group("ambrosium").save(this.output, this.name("ambrosium_shard_from_blasting_undershale_ambrosium_ore"));

        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIItems.FOSSILIZED_ZANITE, 0.0F).group("zanite").save(this.output, this.name("restore_zanite_gemstone"));
        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIBlocks.ZANITE_ORE, 0.0F).group("zanite").save(this.output, this.name("restore_zanite_gemstone_from_ore"));
        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIBlocks.UNDERSHALE_ZANITE_ORE, 0.0F).group("zanite").save(this.output, this.name("restore_zanite_gemstone_from_undershale_ore"));

        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.GLINT_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIItems.FOSSILIZED_GLINT, 0.0F).group("glint").save(this.output, this.name("restore_glint_gemstone"));
        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.GLINT_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIBlocks.GLINT_ORE, 0.0F).group("glint").save(this.output, this.name("restore_glint_gemstone_from_ore"));
        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.GLINT_GEMSTONE, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIBlocks.UNDERSHALE_GLINT_ORE, 0.0F).group("glint").save(this.output, this.name("restore_glint_gemstone_from_undershale_ore"));

        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.CORROBONITE_CRYSTAL, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIItems.FOSSILIZED_CORROBONITE, 0.0F).group("corrobonite").save(this.output, this.name("restore_corrobonite_crystal"));
        this.hourglassRestoring(RecipeCategory.MISC,
                AetherIIItems.CORROBONITE_CRYSTAL, List.of(this.hourglass(1, 85), this.hourglass(2, 10), this.hourglass(3, 5)),
                AetherIIBlocks.CORROBONITE_ORE, 0.0F).group("corrobonite").save(this.output, this.name("restore_corrobonite_crystal_from_ore"));


        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_SHOVEL, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_shovel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_PICKAXE, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_pickaxe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_AXE, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_axe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_TROWEL, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_trowel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_SHORTSWORD, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_shortsword"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_PIKE, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_pike"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_HAMMER, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_hammer"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 100)),
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.SKYROOT_CROSSBOW, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_crossbow"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 25), this.hourglass(1, 75)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_SHIELD, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_skyroot_shield"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 100)),
                AetherIIItems.GOLDEN_AMBER, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.DART_SHOOTER, 0.0F).group("skyroot_tool").save(this.output, this.name("uncraft_dart_shooter"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT_HELMET, 0.0F).group("beast_pelt_armor").save(this.output, this.name("uncraft_beast_pelt_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT_CHESTPLATE, 0.0F).group("beast_pelt_armor").save(this.output, this.name("uncraft_beast_pelt_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT_LEGGINGS, 0.0F).group("beast_pelt_armor").save(this.output, this.name("uncraft_beast_pelt_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT_BOOTS, 0.0F).group("beast_pelt_armor").save(this.output, this.name("uncraft_beast_pelt_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.BEAST_PELT_GLOVES, 0.0F).group("beast_pelt_armor").save(this.output, this.name("uncraft_beast_pelt_gloves"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_SHOVEL, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_shovel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_PICKAXE, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_pickaxe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_AXE, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_axe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_TROWEL, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_trowel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_SHORTSWORD, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_shortsword"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_PIKE, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_pike"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.HOLYSTONE_HAMMER, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_hammer"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 100)),
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.HOLYSTONE_CROSSBOW, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_holystone_crossbow"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                AetherIIItems.AMBROSIUM_SHARD, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.HEALING_STONE, 0.0F).group("holystone_tool").save(this.output, this.name("uncraft_healing_stone"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_HELMET, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukai_plate_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukai_plate_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_LEGGINGS, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukai_plate_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_BOOTS, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukai_plate_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_GLOVES, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukai_plate_gloves"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 25), this.hourglass(1, 75)),
                AetherIIItems.BURRUKAI_PLATE, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BURRUKAI_PLATE_SHIELD, 0.0F).group("burrukai_plate_armor").save(this.output, this.name("uncraft_burrukau_plate_shield"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_SHOVEL, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_shovel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_PICKAXE, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_pickaxe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 15), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_AXE, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_axe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_TROWEL, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_trowel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_SHORTSWORD, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_shortsword"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_PIKE, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_pike"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_HAMMER, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_hammer"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 100)),
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.ZANITE_CROSSBOW, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_crossbow"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_SHEARS, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_shears"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 25), this.hourglass(1, 75)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_SHIELD, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_shield"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 50), this.hourglass(2, 50)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_PENDANT, 0.0F).group("zanite_tool").save(this.output, this.name("uncraft_zanite_pendant"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 50), this.hourglass(2, 50)),
                AetherIIBlocks.ICESTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ICESTONE_PENDANT, 0.0F).save(this.output, this.name("uncraft_icestone_pendant"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_HELMET, 0.0F).group("zanite_armor").save(this.output, this.name("uncraft_zanite_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_CHESTPLATE, 0.0F).group("zanite_armor").save(this.output, this.name("uncraft_zanite_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_LEGGINGS, 0.0F).group("zanite_armor").save(this.output, this.name("uncraft_zanite_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_BOOTS, 0.0F).group("zanite_armor").save(this.output, this.name("uncraft_zanite_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ZANITE_GLOVES, 0.0F).group("zanite_armor").save(this.output, this.name("uncraft_zanite_gloves"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_SHOVEL, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_shovel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 10), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_PICKAXE, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_pickaxe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 10), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_AXE, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_axe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_TROWEL, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_trowel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_SHORTSWORD, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_shortsword"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_PIKE, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_pike"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_HAMMER, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_hammer"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 100)),
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.ARKENIUM_CROSSBOW, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_crossbow"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 25), this.hourglass(1, 75)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(3, 80), this.hourglass(4, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_SHIELD, 0.0F).group("arkenium_tool").save(this.output, this.name("uncraft_arkenium_shield"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(2, 80), this.hourglass(3, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_HELMET, 0.0F).group("arkenium_armor").save(this.output, this.name("uncraft_arkenium_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(4, 80), this.hourglass(5, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_CHESTPLATE, 0.0F).group("arkenium_armor").save(this.output, this.name("uncraft_arkenium_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(3, 80), this.hourglass(4, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_LEGGINGS, 0.0F).group("arkenium_armor").save(this.output, this.name("uncraft_arkenium_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(2, 80), this.hourglass(3, 20)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_BOOTS, 0.0F).group("arkenium_armor").save(this.output, this.name("uncraft_arkenium_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_GLOVES, 0.0F).group("arkenium_armor").save(this.output, this.name("uncraft_arkenium_gloves"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_SHOVEL, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_shovel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 10), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_PICKAXE, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_pickaxe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 10), this.hourglass(3, 5)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_AXE, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_axe"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_TROWEL, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_trowel"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_SHORTSWORD, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_shortsword"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_PIKE, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_pike"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 80), this.hourglass(2, 20)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_HAMMER, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_hammer"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 100)),
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.GRAVITITE_CROSSBOW, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_crossbow"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 25), this.hourglass(1, 75)),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(3, 80), this.hourglass(4, 20)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_SHIELD, 0.0F).group("gravitite_tool").save(this.output, this.name("uncraft_gravitite_shield"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(2, 85), this.hourglass(3, 15)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_HELMET, 0.0F).group("gravitite_armor").save(this.output, this.name("uncraft_gravitite_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(4, 85), this.hourglass(5, 15)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_CHESTPLATE, 0.0F).group("gravitite_armor").save(this.output, this.name("uncraft_gravitite_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(3, 85), this.hourglass(4, 15)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_LEGGINGS, 0.0F).group("gravitite_armor").save(this.output, this.name("uncraft_gravitite_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(2, 85), this.hourglass(3, 15)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_BOOTS, 0.0F).group("gravitite_armor").save(this.output, this.name("uncraft_gravitite_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.INERT_GRAVITITE, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.GRAVITITE_GLOVES, 0.0F).group("gravitite_armor").save(this.output, this.name("uncraft_gravitite_gloves"));

        this.hourglassUncraftingIngredient(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(0, 50), this.hourglass(1, 50)),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                DataComponentIngredient.of(false, DataComponentExactPredicate.expect(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY).asPatch(), AetherIIItems.BEAST_PELT_BUNDLE.get()), 0.0F, this.has(AetherIIItems.BEAST_PELT_BUNDLE)).group("misc_tools").save(this.output, this.name("uncraft_beast_pelt_bundle"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 50), this.hourglass(2, 50)),
                Items.AIR, List.of(),
                AetherIIItems.SKYROOT_BUCKET, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_skyroot_bucket"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIBlocks.BRETTL_ROPE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BRETTL_LASSO, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_brettl_lasso"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 50), this.hourglass(1, 50)),
                AetherIIBlocks.COLD_AERCLOUD, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.COLD_AERCLOUD_GLIDER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_cold_aercloud_glider"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 50), this.hourglass(1, 50)),
                AetherIIBlocks.BLUE_AERCLOUD, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.BLUE_AERCLOUD_GLIDER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_blue_aercloud_glider"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 50), this.hourglass(1, 50)),
                AetherIIBlocks.PURPLE_AERCLOUD, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.PURPLE_AERCLOUD_GLIDER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_purple_aercloud_glider"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(0, 50), this.hourglass(1, 50)),
                AetherIIBlocks.GOLDEN_AERCLOUD, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.GOLDEN_AERCLOUD_GLIDER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_golden_aercloud_glider"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.COLD_AERCLOUD, List.of(this.hourglass(1, 100)),
                Items.AIR, List.of(),
                AetherIIItems.CLOUD_SKIFF, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_cloud_skiff"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.MOA_SADDLE, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_moa_saddle"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                AetherIIItems.MOA_SADDLEBAG, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_moa_saddlebag"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.CLOUDTWINE, List.of(this.hourglass(1, 100)),
                AetherIIItems.BEAST_PELT, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                AetherIIItems.LARGE_MOA_SADDLEBAG, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_large_moa_saddlebag"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SCATTERGLASS_SHARD, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIItems.ARKENIUM_CANISTER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_arkenium_cannister"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.FERROSITE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.GOLDEN_AMBER, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                Items.AIR, List.of(),
                AetherIIItems.MUSIC_PLAYER, 0.0F).group("misc_tools").save(this.output, this.name("uncraft_music_player"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_SCALE, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_HELMET, 0.0F).group("neptune_armor").save(this.output, this.name("uncraft_neptune_helmet"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_SCALE, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_CHESTPLATE, 0.0F).group("neptune_armor").save(this.output, this.name("uncraft_neptune_chestplate"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_SCALE, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_LEGGINGS, 0.0F).group("neptune_armor").save(this.output, this.name("uncraft_neptune_leggings"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_SCALE, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_BOOTS, 0.0F).group("neptune_armor").save(this.output, this.name("uncraft_neptune_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_SCALE, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.NEPTUNE_GLOVES, 0.0F).group("neptune_armor").save(this.output, this.name("uncraft_neptune_gloves"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.SENTRY_SERVO, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.HAMMER_OF_DEMOLITION, 0.0F).group("sentry_loot").save(this.output, this.name("uncraft_hammer_of_demolition"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.SENTRY_SERVO, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.SENTRY_BOOTS, 0.0F).group("sentry_loot").save(this.output, this.name("uncraft_sentry_boots"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                Items.AIR, List.of(),
                AetherIIItems.SENTRY_SERVO, List.of(this.hourglass(2, 60), this.hourglass(3, 35), this.hourglass(4, 5)),
                Items.AIR, List.of(),
                AetherIIItems.KINETIC_THRUSTERS, 0.0F).group("sentry_loot").save(this.output, this.name("uncraft_kinetic_thrusters"));

        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SKYROOT_STICK, List.of(this.hourglass(1, 100)),
                AetherIIItems.GOLDEN_AMBER, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1,100)),
                AetherIIBlocks.AMBER_HOURGLASS, 0.0F).group("utility_blocks").save(this.output, this.name("uncraft_amber_hourglass"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.UNDERSHALE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.ZANITE_GEMSTONE, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIBlocks.ALTAR, 0.0F).group("utility_blocks").save(this.output, this.name("uncraft_altar"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(2, 75), this.hourglass(3, 25)),
                Items.AIR, List.of(),
                AetherIIBlocks.ARKENIUM_FORGE, 0.0F).group("utility_blocks").save(this.output, this.name("uncraft_arkenium_forge"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIBlocks.HOLYSTONE, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(1, 75), this.hourglass(2, 25)),
                AetherIIBlocks.SKYROOT_PLANKS, List.of(this.hourglass(1,75), this.hourglass(2, 25)),
                AetherIIBlocks.ARTISANS_BENCH, 0.0F).group("utility_blocks").save(this.output, this.name("uncraft_artians_bench"));
        this.hourglassUncraftingItem(RecipeCategory.MISC,
                AetherIIItems.SCATTERGLASS_SHARD, List.of(this.hourglass(4, 75), this.hourglass(5, 25)),
                AetherIIItems.INERT_ARKENIUM, List.of(this.hourglass(3, 75), this.hourglass(4, 25)),
                Items.AIR, List.of(),
                AetherIIBlocks.ALKAHEST_PURIFIER, 0.0F).group("utility_blocks").save(this.output, this.name("uncraft_alkahest_purifier"));


        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.ARKENIUM_PLATE, AetherIIItems.INERT_ARKENIUM, 3, 0.0F).group("arkenium").save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.ARKENIUM_PLATE, AetherIIBlocks.ARKENIUM_ORE, 3, 0.0F).group("arkenium").save(this.output, this.name("arkenium_plates_from_arkenium_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.ARKENIUM_PLATE, AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE, 3, 0.0F).group("arkenium").save(this.output, this.name("arkenium_plates_from_undershale_arkenium_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIItems.INERT_GRAVITITE, 4, 0.0F).group("gravitite").save(this.output);
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIBlocks.GRAVITITE_ORE, 4, 0.0F).group("gravitite").save(this.output, this.name("gravitite_plates_from_gravitite_ore"));
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.GRAVITITE_PLATE, AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE, 4, 0.0F).group("gravitite").save(this.output, this.name("gravitite_plates_from_undershale_gravitite_ore"));

        for (Moa.FeatherColor featherColor : Moa.FeatherColor.values()) {
            Item featherDye = dyeMap.get(featherColor.dyeColor);
            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, featherDye, 1)
                    .requires(DataComponentIngredient.of(false, AetherIIDataComponents.FEATHER_COLOR, featherColor, AetherIIItems.MOA_FEATHER))
                    .group(getItemName(featherDye))
                    .unlockedBy(getHasName(AetherIIItems.MOA_FEATHER), has(AetherIIItems.MOA_FEATHER))
                    .save(this.output, this.name(getItemName(featherDye) + "_from_" + featherColor.getSerializedName() + "_moa_feather"));
        }

        this.oneToOneConversionRecipe(Items.CYAN_DYE, AetherIIItems.PRISMALLARD_FEATHER.get(), "cyan_dye");
        this.oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIItems.COCKATRICE_FEATHER.get(), "purple_dye");

        this.oneToOneConversionRecipe(Items.YELLOW_DYE, AetherIIBlocks.BLADE_POA, "yellow_dye");
        this.oneToOneConversionRecipe(Items.WHITE_DYE, AetherIIBlocks.HESPEROSE, "white_dye");
        this.oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIBlocks.TARABLOOM, "purple_dye");
        this.oneToOneConversionRecipe(Items.MAGENTA_DYE, AetherIIItems.SATIVAL_BULB, "magenta_dye");
        this.oneToOneConversionRecipe(Items.WHITE_DYE, AetherIIBlocks.POASPROUT, "white_dye");
        this.oneToOneConversionRecipe(Items.BLUE_DYE, AetherIIBlocks.BRETTL_FLOWER, "blue_dye");
        this.oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, AetherIIBlocks.LILICHIME, "light_blue_dye");
        this.oneToOneConversionRecipe(Items.CYAN_DYE, AetherIIBlocks.PLURACIAN, "cyan_dye");
        this.oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, AetherIIBlocks.SATIVAL_SHOOT, "light_blue_dye");
        this.oneToOneConversionRecipe(Items.PINK_DYE, AetherIIBlocks.BRYALINN_MOSS_FLOWERS, "pink_dye");
        this.oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIBlocks.HOLPUPEA, "purple_dye");
        this.oneToOneConversionRecipe(Items.MAGENTA_DYE, AetherIIBlocks.TARAHESP_FLOWERS, "magenta_dye");
        this.oneToOneConversionRecipe(Items.BROWN_DYE, AetherIIBlocks.SKY_ROOTS, "brown_dye");
        this.oneToOneConversionRecipe(Items.PURPLE_DYE, AetherIIBlocks.AECHOR_CUTTING, "purple_dye");
        this.oneToOneConversionRecipe(Items.LIGHT_BLUE_DYE, AetherIIBlocks.CARRION_CUTTING, "light_blue_dye");

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherIIItems.SWET_JELLY.get(), 1)
                .requires(AetherIIItems.SWET_GEL)
                .requires(AetherIIItems.SWET_SUGAR)
                .unlockedBy("has_gel", has(AetherIIItems.SWET_GEL))
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
        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC, AetherIIItems.ENCHANTED_MOA_FEED, AetherIIItems.BLUEBERRY_MOA_FEED, 1, 0.0F).save(this.output, this.name("enchanted_moa_feed_enchanting"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.AERBUNNY_BELL.get())
                .define('#', AetherIITags.Items.GEMS_ZANITE)
                .define('A', AetherIIItems.RESONANT_STONE)
                .pattern(" # ")
                .pattern("#A#")
                .pattern(" # ")
                .unlockedBy("has_resonant_stone", has(AetherIIItems.RESONANT_STONE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.TOOLS, AetherIIItems.BEAST_PELT_BUNDLE)
                .define('-', AetherIIItems.CLOUDTWINE)
                .define('#', AetherIIItems.BEAST_PELT)
                .pattern("-")
                .pattern("#")
                .unlockedBy("has_cloudtwine", this.has(AetherIIItems.CLOUDTWINE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.BRETTL_LASSO.get())
                .define('#', AetherIIBlocks.BRETTL_ROPE)
                .pattern(" ##")
                .pattern(" ##")
                .pattern("#  ")
                .unlockedBy("has_brettl_rope", has(AetherIIBlocks.BRETTL_ROPE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIBlocks.BRETTL_ROPE_STAKE.get(), 4)
                .define('#', AetherIIBlocks.BRETTL_ROPE)
                .define('/', AetherIIItems.SKYROOT_STICK)
                .pattern("#/")
                .pattern("# ")
                .pattern("# ")
                .unlockedBy("has_brettl_rope", has(AetherIIBlocks.BRETTL_ROPE))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.MOA_SADDLE.get())
                .define('#', AetherIIItems.BEAST_PELT)
                .define('/', AetherIIItems.CLOUDTWINE)
                .pattern("###")
                .pattern("#/#")
                .unlockedBy("has_beast_pelt", has(AetherIIItems.BEAST_PELT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.MOA_SADDLEBAG.get())
                .define('C', AetherIIBlocks.SKYROOT_CHEST)
                .define('#', AetherIIItems.BEAST_PELT)
                .define('/', AetherIIItems.CLOUDTWINE)
                .pattern("###")
                .pattern("/C/")
                .unlockedBy("has_beast_pelt", has(AetherIIItems.BEAST_PELT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.LARGE_MOA_SADDLEBAG.get())
                .define('C', AetherIIBlocks.SKYROOT_CHEST)
                .define('#', AetherIIItems.BEAST_PELT)
                .define('/', AetherIIItems.CLOUDTWINE)
                .pattern("###")
                .pattern("#C#")
                .pattern("/#/")
                .unlockedBy("has_beast_pelt", has(AetherIIItems.BEAST_PELT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherIIItems.CLOUD_SKIFF.get())
                .define('A', AetherIIBlocks.COLD_AERCLOUD)
                .define('S', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("A A")
                .pattern("SSS")
                .unlockedBy("in_aercloud", insideOf(AetherIIBlocks.COLD_AERCLOUD.get()))
                .save(this.output);


        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.SPLINT.get()))
                .requires(AetherIITags.Items.RODS_SKYROOT)
                .requires(AetherIITags.Items.CLOUDWOOL)
                .unlockedBy("has_cloudwool", has(AetherIITags.Items.CLOUDWOOL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.BANDAGE.get()))
                .requires(AetherIIItems.CLOUDTWINE.get())
                .requires(AetherIITags.Items.CLOUDWOOL)
                .unlockedBy("has_cloudwool", has(AetherIITags.Items.CLOUDWOOL))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.ANTIVENOM_VIAL.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.COCKATRICE_FEATHER.get())
                .requires(AetherIIBlocks.HESPEROSE.get())
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.ANTITOXIN_VIAL.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.AECHOR_PETAL.get())
                .requires(AetherIIBlocks.TARABLOOM.get())
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.VALKYRIE_TEA.get()))
                .requires(AetherIIItems.WATER_VIAL.get())
                .requires(AetherIIItems.VALKYRIE_WINGS.get())
                .requires(AetherIITags.Items.GEMS_AMBROSIUM)
                .unlockedBy("has_water_vial", has(AetherIIItems.WATER_VIAL.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 1).build()))
                .define('A', AetherIITags.Items.GEMS_AMBROSIUM)
                .define('H', AetherIITags.Items.STONE_CRAFTING)
                .pattern("HAH")
                .pattern("AAA")
                .pattern("HAH")
                .unlockedBy("has_ambrosium_shard", has(AetherIITags.Items.GEMS_AMBROSIUM))
                .save(this.output);

        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC,
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 0).build()),
                5, 0.0F).group("healing_stone").save(this.output, this.name("healing_stone_recharging_0"));

        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC,
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 1).build()),
                4, 0.0F).group("healing_stone").save(this.output, this.name("healing_stone_recharging_1"));

        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC,
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 2).build()),
                3, 0.0F).group("healing_stone").save(this.output, this.name("healing_stone_recharging_2"));

        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC,
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 3).build()),
                2, 0.0F).group("healing_stone").save(this.output, this.name("healing_stone_recharging_3"));

        this.altarEnchanting(RecipeCategory.MISC, AltarBookCategory.MISC,
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 5).build()),
                new ItemStackTemplate(AetherIIItems.HEALING_STONE, 1, DataComponentPatch.builder().set(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 4).build()),
                1, 0.0F).group("healing_stone").save(this.output, this.name("healing_stone_recharging_4"));

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_PICKAXE, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_pickaxe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_AXE, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_axe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_SHOVEL, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_shovel"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.SKYROOT_TROWEL, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_trowel"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_SHORTSWORD, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_shortsword"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_HAMMER, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_hammer"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_PIKE, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_pike"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_CROSSBOW, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_crossbow"));

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_PICKAXE, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_pickaxe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_AXE, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_axe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_SHOVEL, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_shovel"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.HOLYSTONE_TROWEL, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_trowel"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_SHORTSWORD, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_shortsword"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_HAMMER, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_hammer"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_PIKE, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_pike"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.HOLYSTONE_CROSSBOW, 2).group("repair_holystone").save(this.output, this.name("repair_holystone_crossbow"));

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_PICKAXE, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_pickaxe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_AXE, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_axe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_SHOVEL, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_shovel"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ZANITE_TROWEL, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_trowel"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_SHORTSWORD, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_shortsword"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_HAMMER, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_hammer"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_PIKE, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_pike"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_CROSSBOW, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_crossbow"));

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_PICKAXE, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_pickaxe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_AXE, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_axe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_SHOVEL, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_shovel"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.ARKENIUM_TROWEL, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_trowel"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_SHORTSWORD, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_shortsword"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_HAMMER, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_hammer"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_PIKE, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_pike"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_CROSSBOW, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_crossbow"));

        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_PICKAXE, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_pickaxe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_AXE, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_axe"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_SHOVEL, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_shovel"));
        this.altarRepairing(RecipeCategory.TOOLS, AetherIIItems.GRAVITITE_TROWEL, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_trowel"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_SHORTSWORD, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_shortsword"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_HAMMER, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_hammer"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_PIKE, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_pike"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_CROSSBOW, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_crossbow"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.SKYROOT_SHIELD, 1).group("repair_skyroot").save(this.output, this.name("repair_skyroot_shield"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_SHIELD, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_shield"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_SHIELD, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_shield"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_SHIELD, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_shield"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_SHIELD, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_shield"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BEAST_PELT_HELMET, 1).group("repair_beast_pelt").save(this.output, this.name("repair_beast_pelt_helmet"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BEAST_PELT_CHESTPLATE, 1).group("repair_beast_pelt").save(this.output, this.name("repair_beast_pelt_chestplate"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BEAST_PELT_LEGGINGS, 1).group("repair_beast_pelt").save(this.output, this.name("repair_beast_pelt_leggings"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BEAST_PELT_BOOTS, 1).group("repair_beast_pelt").save(this.output, this.name("repair_beast_pelt_boots"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BEAST_PELT_GLOVES, 1).group("repair_beast_pelt").save(this.output, this.name("repair_beast_pelt_gloves"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_HELMET, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_helmet"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_chestplate"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_LEGGINGS, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_leggings"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_BOOTS, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_boots"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.BURRUKAI_PLATE_GLOVES, 2).group("repair_burrukai_plate").save(this.output, this.name("repair_burrukai_plate_gloves"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_HELMET, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_helmet"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_CHESTPLATE, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_chestplate"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_LEGGINGS, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_leggings"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_BOOTS, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_boots"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_GLOVES, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_gloves"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_HELMET, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_helmet"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_CHESTPLATE, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_chestplate"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_LEGGINGS, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_leggings"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_BOOTS, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_boots"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ARKENIUM_GLOVES, 6).group("repair_arkenium").save(this.output, this.name("repair_arkenium_gloves"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_HELMET, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_helmet"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_CHESTPLATE, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_chestplate"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_LEGGINGS, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_leggings"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_BOOTS, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_boots"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.GRAVITITE_GLOVES, 8).group("repair_gravitite").save(this.output, this.name("repair_gravitite_gloves"));

        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ZANITE_PENDANT, 4).group("repair_zanite").save(this.output, this.name("repair_zanite_pendant"));
        this.altarRepairing(RecipeCategory.COMBAT, AetherIIItems.ICESTONE_PENDANT, 3).save(this.output, this.name("repair_icestone_pendant"));

        this.alkahestCorrosion(AetherIIBlocks.ICHORITE.get(), AetherIIBlocks.UNDERSHALE.get()).save(this.output, this.name("corrode_undershale_to_ichorite"));

        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.HOLYSTONE.get()), AetherIIBlocks.IRRADIATED_HOLYSTONE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.HOLYSTONE_STAIRS.get()), AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.HOLYSTONE_SLAB.get()), AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.HOLYSTONE_WALL.get()), AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, this.output);

        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYROOT_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYPLANE_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYPINE_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.WISPROOT_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.WISPTOP_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATROOT_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATOAK_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATBOA_LEAF_PILE.get()), AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaf_pile", this.output);

        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYROOT_LEAVES.get()), AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYPLANE_LEAVES.get()), AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYBIRCH_LEAVES.get()), AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.SKYPINE_LEAVES.get()), AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.WISPROOT_LEAVES.get()), AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.WISPTOP_LEAVES.get()), AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATROOT_LEAVES.get()), AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATOAK_LEAVES.get()), AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);
        this.alkahestPurification(RecipeCategory.BUILDING_BLOCKS, AlkahestPurifierBookCategory.BLOCKS, new OutputEntry.ItemEntry(AetherIIBlocks.GREATBOA_LEAVES.get()), AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES, this.multiple(AetherIIItems.IRRADIATED_DUST, 1), 1, "irradiated_leaves", this.output);

        this.alkahestPurification(RecipeCategory.COMBAT, AlkahestPurifierBookCategory.ITEMS, new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(new OutputEntry.ItemEntry(AetherIIItems.BEAST_PELT_HELMET.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_HELMET.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_HELMET.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_HELMET.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_HELMET.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BEAST_PELT_CHESTPLATE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_CHESTPLATE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_CHESTPLATE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_CHESTPLATE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BEAST_PELT_LEGGINGS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_LEGGINGS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_LEGGINGS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_LEGGINGS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BEAST_PELT_BOOTS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_BOOTS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_BOOTS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_BOOTS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_BOOTS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BEAST_PELT_GLOVES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_GLOVES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_GLOVES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_GLOVES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_GLOVES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_PENDANT.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ICESTONE_PENDANT.get()), 1)
                .build()), AetherIIItems.IRRADIATED_ARMOR, this.multiple(AetherIIItems.IRRADIATED_DUST, 3), 1, this.output);
        this.alkahestPurification(RecipeCategory.COMBAT, AlkahestPurifierBookCategory.ITEMS, new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_SHORTSWORD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_SHORTSWORD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_SHORTSWORD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_SHORTSWORD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_SHORTSWORD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_PIKE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_PIKE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_PIKE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_PIKE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_PIKE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_HAMMER.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_HAMMER.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_HAMMER.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_HAMMER.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_HAMMER.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_CROSSBOW.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_CROSSBOW.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_CROSSBOW.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_CROSSBOW.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_CROSSBOW.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.DART_SHOOTER.get()), 1)
                .build()), AetherIIItems.IRRADIATED_WEAPON, this.multiple(AetherIIItems.IRRADIATED_DUST, 3), 1, this.output);
        this.alkahestPurification(RecipeCategory.COMBAT, AlkahestPurifierBookCategory.ITEMS, new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_AXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_AXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_AXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_AXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_AXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_PICKAXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_PICKAXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_PICKAXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_PICKAXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_PICKAXE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_SHOVEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_SHOVEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_SHOVEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_SHOVEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_SHOVEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_TROWEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.HOLYSTONE_TROWEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_TROWEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_TROWEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_TROWEL.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_SHEARS.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.SKYROOT_SHIELD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.BURRUKAI_PLATE_SHIELD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ZANITE_SHIELD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ARKENIUM_SHIELD.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.GRAVITITE_SHIELD.get()), 1)
                .build()), AetherIIItems.IRRADIATED_TOOL, this.multiple(AetherIIItems.IRRADIATED_DUST, 3), 1, this.output);

        OutputEntry.ListEntry woodEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(this.multiple(AetherIIBlocks.SKYROOT_LOG.get(), 8, 16, 4, true), 1)
                .add(this.multiple(AetherIIBlocks.GREATROOT_LOG.get(), 8, 16, 4, true), 1)
                .add(this.multiple(AetherIIBlocks.WISPROOT_LOG.get(), 8, 16, 4, true), 1)
                .add(this.multiple(AetherIIBlocks.AMBEROOT_LOG.get(), 8, 16, 4, true), 1)
                .build());
        OutputEntry.ListEntry stoneEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(this.multiple(AetherIIBlocks.HOLYSTONE.get(), 16, 32, 8, true), 1)
                .add(this.multiple(AetherIIBlocks.UNDERSHALE.get(), 16, 32, 8, true), 1)
                .add(this.multiple(AetherIIBlocks.ICHORITE.get(), 16, 32, 8, true), 1)
                .add(this.multiple(AetherIIBlocks.AGIOSITE.get(), 16, 32, 8, true), 1)
                .add(this.multiple(AetherIIBlocks.FERROSITE.get(), 16, 32, 8, true), 1)
                .add(this.multiple(AetherIIBlocks.ICESTONE.get(), 16, 32, 8, true), 1)
                .build());
        OutputEntry.ListEntry oreEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(this.multiple(AetherIIItems.AMBROSIUM_SHARD.get(), 16, 32, 4, false), 1)
                .add(this.multiple(AetherIIItems.GOLDEN_AMBER.get(), 16, 32, 4, false), 1)
                .add(this.multiple(AetherIIItems.ZANITE_GEMSTONE.get(), 8, 16, 4, false), 1)
                .add(this.multiple(AetherIIItems.ARKENIUM_PLATE.get(), 8, 16, 4, false), 1)
                .add(this.multiple(AetherIIItems.GRAVITITE_PLATE.get(), 4, 8, 4, false), 1)
                .add(this.multiple(AetherIIItems.CORROBONITE_CRYSTAL.get(), 4), 1)
                .add(this.multiple(AetherIIItems.GLINT_GEMSTONE.get(), 2, 8, 2, false), 1)
                .build());
        OutputEntry.ListEntry materialsEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(this.multiple(AetherIIItems.SCATTERGLASS_SHARD.get(), 16, 64, 8, false), 1)
                .add(this.multiple(AetherIIItems.CLOUDTWINE.get(), 8, 24, 4, false), 1)
                .add(this.multiple(AetherIIItems.BEAST_PELT.get(), 16, 32, 8, false), 1)
                .add(this.multiple(AetherIIItems.BURRUKAI_PLATE.get(), 16, 32, 8, false), 1)
                .build());
        OutputEntry.ListEntry charmsEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_EFFICIENCY_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_REACH_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_DAMAGE_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_DEXTERITY_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_KNOCKBACK_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_HEALTH_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_DEFENSE_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_TOUGHNESS_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_RESISTANCE_I.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.CHARM_OF_AGILITY_I.get()), 1)
                .build());
        OutputEntry.ListEntry discsEntry = new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_AERWHALE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_APPROACHES.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_DEMISE.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_CHINCHILLA.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_HIGH.get()), 1)
                .add(new OutputEntry.ItemEntry(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS.get()), 1)
                .build());
        this.alkahestPurification(RecipeCategory.COMBAT, AlkahestPurifierBookCategory.ITEMS, new OutputEntry.ListEntry(WeightedList.<OutputEntry.BaseEntry>builder()
                .add(woodEntry, 1)
                .add(stoneEntry, 1)
                .add(oreEntry, 1)
                .add(materialsEntry, 1)
                .add(charmsEntry, 1)
                .add(discsEntry, 1)
                .build()), AetherIIItems.IRRADIATED_CHUNK, this.multiple(AetherIIItems.IRRADIATED_DUST, 3), 1, this.output);

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

        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get(), AetherIIBlocks.SKYROOT_LEAF_PILE.get()).save(this.output, this.name("skyroot_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get(), AetherIIBlocks.SKYPLANE_LEAF_PILE.get()).save(this.output, this.name("skyplane_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get(), AetherIIBlocks.SKYBIRCH_LEAF_PILE.get()).save(this.output, this.name("skybirch_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get(), AetherIIBlocks.SKYPINE_LEAF_PILE.get()).save(this.output, this.name("skypine_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get(), AetherIIBlocks.WISPROOT_LEAF_PILE.get()).save(this.output, this.name("wisproot_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get(), AetherIIBlocks.WISPTOP_LEAF_PILE.get()).save(this.output, this.name("wisptop_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get(), AetherIIBlocks.GREATROOT_LEAF_PILE.get()).save(this.output, this.name("greatroot_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get(), AetherIIBlocks.GREATOAK_LEAF_PILE.get()).save(this.output, this.name("greatoak_leaf_pile_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get(), AetherIIBlocks.GREATBOA_LEAF_PILE.get()).save(this.output, this.name("greatboa_leaf_pile_irradiation"));

        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get(), AetherIIBlocks.SKYROOT_LEAVES.get()).save(this.output, this.name("skyroot_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get(), AetherIIBlocks.SKYPLANE_LEAVES.get()).save(this.output, this.name("skyplane_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get(), AetherIIBlocks.SKYBIRCH_LEAVES.get()).save(this.output, this.name("skybirch_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get(), AetherIIBlocks.SKYPINE_LEAVES.get()).save(this.output, this.name("skypine_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get(), AetherIIBlocks.WISPROOT_LEAVES.get()).save(this.output, this.name("wisproot_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get(), AetherIIBlocks.WISPTOP_LEAVES.get()).save(this.output, this.name("wisptop_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get(), AetherIIBlocks.GREATROOT_LEAVES.get()).save(this.output, this.name("greatroot_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get(), AetherIIBlocks.GREATOAK_LEAVES.get()).save(this.output, this.name("greatoak_leaves_irradiation"));
        this.dustIrradiation(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get(), AetherIIBlocks.GREATBOA_LEAVES.get()).save(this.output, this.name("greatboa_leaves_irradiation"));

        this.icestoneFreezable(Blocks.ICE, Blocks.WATER).save(this.output, this.name("icestone_freeze_water"));
        this.icestoneFreezableTag(AetherIIBlocks.ARCTIC_ICE.get(), Blocks.WATER, AetherIITags.Biomes.ARCTIC_ICE).save(this.output, this.name("icestone_freeze_water_to_arctic_ice"));
        this.icestoneFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(this.output, this.name("icestone_freeze_lava"));

        this.accessoryFreezable(AetherIIBlocks.FROSTED_ICE.get(), Blocks.WATER).save(this.output, this.name("accessory_freeze_water"));
        this.accessoryFreezableTag(AetherIIBlocks.FROSTED_ARCTIC_ICE.get(), Blocks.WATER, AetherIITags.Biomes.ARCTIC_ICE).save(this.output, this.name("accessory_freeze_water_to_arctic_ice"));
        this.accessoryFreezable(AetherIIBlocks.UNSTABLE_OBSIDIAN.get(), Blocks.LAVA).save(this.output, this.name("accessory_freeze_lava"));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new AetherIIRecipeData(output, provider);
        }

        @Override
        public String getName() {
            return "Aether II Recipes";
        }
    }
}
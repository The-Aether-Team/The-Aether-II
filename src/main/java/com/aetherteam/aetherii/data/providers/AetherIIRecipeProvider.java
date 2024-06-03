package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.recipe.builder.BiomeParameterRecipeBuilder;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    public AetherIIRecipeProvider(PackOutput output, String id) {
        super(output, id);
    }

    protected static void leafPile(RecipeOutput recipeOutput, ItemLike carpet, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, carpet, 32)
                .define('#', material)
                .pattern("##")
                .group("leaf_pile")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return this.fence(fence, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return this.fenceGate(fenceGate, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected static void bookshelf(RecipeOutput consumer, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', material)
                .define('B', Items.BOOK)
                .pattern("###")
                .pattern("BBB")
                .pattern("###")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(consumer);
    }

    protected ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return this.makePickaxeWithTag(pickaxe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return this.makeAxeWithTag(axe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return this.makeShovelWithTag(shovel, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return this.makeHoeWithTag(hoe, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return this.makeSwordWithTag(sword, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeHammerWithTag(Supplier<? extends Item> hammer, TagKey<Item> material, String has) {
        return this.makeHammerWithTag(hammer, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeHammerWithTag(Supplier<? extends Item> hammer, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, hammer.get())
                .define('#', material)
                .define('/', sticks)
                .pattern(" # ")
                .pattern(" /#")
                .pattern("/  ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeSpearWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makeSpearWithTag(spear, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeSpearWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, spear.get())
                .define('#', material)
                .define('/', sticks)
                .pattern("#")
                .pattern("/")
                .pattern("#")
                .unlockedBy(has, has(material));
    }

    protected BlockStateRecipeBuilder ambrosiumEnchanting(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AmbrosiumRecipe::new);
    }

    protected BlockStateRecipeBuilder swetGelConversion(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, SwetGelRecipe::new);
    }

    protected BiomeParameterRecipeBuilder swetGelConversionTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, SwetGelRecipe::new);
    }

    protected BlockStateRecipeBuilder icestoneFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, IcestoneFreezableRecipe::new);
    }

    protected BiomeParameterRecipeBuilder icestoneFreezableTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, IcestoneFreezableRecipe::new);
    }
}

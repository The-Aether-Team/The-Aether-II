package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.builder.AltarEnchantingRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.BiomeParameterRecipeBuilder;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    public AetherIIRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String id) {
        super(output, provider, id);
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

    protected void cloudwool(RecipeOutput output, RecipeCategory itemCategory, ItemLike item, RecipeCategory blockCategory, ItemLike block, String itemRecipeName, String itemGroup) {
        ShapelessRecipeBuilder.shapeless(itemCategory, item, 4).requires(block).group(itemGroup).unlockedBy(getHasName(block), has(block)).save(output, this.name(itemRecipeName));
        ShapedRecipeBuilder.shaped(blockCategory, block).define('#', item).pattern("##").pattern("##").unlockedBy(getHasName(item), has(item)).save(output, this.name(getSimpleRecipeName(block)));
    }

    protected void colorBlockWithDye(RecipeOutput consumer, List<Item> dyes, List<Item> dyeableItems, Item extra, String group) {
        for(int i = 0; i < dyes.size(); ++i) {
            Item item = dyes.get(i);
            Item item1 = dyeableItems.get(i);
            List<ItemStack> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(item1)).map(ItemStack::new).collect(Collectors.toList());
            ingredients.add(new ItemStack(extra));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item1)
                    .requires(item)
                    .requires(Ingredient.of(ingredients.toArray(ItemStack[]::new)))
                    .group(group)
                    .unlockedBy("has_needed_dye", has(item))
                    .save(consumer, "dye_" + getItemName(item1));
        }
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

    protected ShapedRecipeBuilder makeCrossbowWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makeCrossbowWithTag(spear, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeCrossbowWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, spear.get())
                .define('#', material)
                .define('/', sticks)
                .define('C', AetherIIItems.CLOUDTWINE)
                .pattern("/#/")
                .pattern("C#C")
                .pattern(" / ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, TagKey<Item> material, String has) {
        return this.makeShieldWithTag(shield, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, shield.get())
                .define('W', material)
                .define('o', sticks)
                .pattern("WoW")
                .pattern("WWW")
                .pattern(" W ")
                .unlockedBy(has, has(material));
    }

    protected final void foodCooking(Supplier<? extends ItemLike> material, Supplier<? extends ItemLike> result, float xp, RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 200).unlockedBy("has_item", has(material.get())).save(consumer, "smelting_" + getHasName(result.get()));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 100).unlockedBy("has_item", has(material.get())).save(consumer, "smoking_" + getHasName(result.get()));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 600).unlockedBy("has_item", has(material.get())).save(consumer, "campfire_cooking_" + getHasName(result.get()));
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, ItemLike result, ItemLike ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(ingredient), category, new ItemStack(result), experience, fuelCount, 200).unlockedBy("has_item", has(ingredient));
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, ItemStack result, ItemStack ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(DataComponentIngredient.of(false, ingredient), category, result, experience, fuelCount, 200).unlockedBy("has_item", has(ingredient.getItem()));
    }

    protected AltarEnchantingRecipeBuilder altarRepairing(RecipeCategory category, ItemLike item, int fuelCount) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(item), category, new ItemStack(item), 0.0F, fuelCount, 200).unlockedBy("has_item", has(item));
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

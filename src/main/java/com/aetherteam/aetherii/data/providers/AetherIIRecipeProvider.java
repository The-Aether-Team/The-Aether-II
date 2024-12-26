package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.builder.AltarEnchantingRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.BiomeParameterRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.IrradiationCleansingRecipeBuilder;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    private final HolderGetter<Item> getter;

    public AetherIIRecipeProvider(RecipeOutput output, HolderLookup.Provider provider, String id) {
        super(provider, output, id);
        this.getter = provider.lookupOrThrow(Registries.ITEM);
    }

    protected void leafPile(HolderGetter<Item> getter, ItemLike carpet, ItemLike material) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, carpet, 8)
                .define('#', material)
                .pattern("##")
                .group("leaf_pile")
                .unlockedBy(getHasName(material), has(material))
                .save(this.output);
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return this.fence(this.getter, fence, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return this.fenceGate(this.getter, fenceGate, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)));
    }

    protected void cloudwool(HolderGetter<Item> getter, RecipeCategory itemCategory, ItemLike item, RecipeCategory blockCategory, ItemLike block, String itemRecipeName, String itemGroup) {
        ShapelessRecipeBuilder.shapeless(getter, itemCategory, item, 4).requires(block).group(itemGroup).unlockedBy(getHasName(block), has(block)).save(this.output, this.name(itemRecipeName));
        ShapedRecipeBuilder.shaped(getter, blockCategory, block).define('#', item).pattern("##").pattern("##").unlockedBy(getHasName(item), has(item)).save(this.output, this.name(getSimpleRecipeName(block)));
    }

    protected void colorBlockWithDye(List<Item> dyes, List<Item> dyeableItems, Item extra, String group) {
        for(int i = 0; i < dyes.size(); ++i) {
            Item item = dyes.get(i);
            Item item1 = dyeableItems.get(i);
            List<ItemLike> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(item1)).map(ItemStack::new).map(ItemStack::getItem).collect(Collectors.toList());
            ingredients.add(extra);
            ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.BUILDING_BLOCKS, item1)
                    .requires(item)
                    .requires(Ingredient.of(ingredients.toArray(ItemLike[]::new)))
                    .group(group)
                    .unlockedBy("has_needed_dye", has(item))
                    .save(this.output, "dye_" + getItemName(item1));
        }
    }

    protected void bookshelf(HolderGetter<Item> getter, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', material)
                .define('B', Items.BOOK)
                .pattern("###")
                .pattern("BBB")
                .pattern("###")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(this.output);
    }

    protected void sign(HolderGetter<Item> getter, ItemLike result, ItemLike block) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, result, 3)
                .group("wooden_sign")
                .define('P', block)
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(block), has(block))
                .save(this.output);
    }

    protected void hangingSign(HolderGetter<Item> getter, ItemLike result, ItemLike block) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, result, 6)
                .group("hanging_sign")
                .define('#', block)
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(block))
                .save(this.output);
    }

    protected void arilumLantern(HolderGetter<Item> getter, ItemLike result, ItemLike gel) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, result, 1)
                .group("arilum_lantern")
                .define('#', gel)
                .define('X', AetherIIItems.ARILUM_BULBS)
                .pattern("#X#")
                .pattern("XXX")
                .pattern("#X#")
                .unlockedBy("has_bulbs", has(AetherIIItems.ARILUM_BULBS))
                .save(this.output);
    }

    protected ShapedRecipeBuilder makePickaxeWithTag(Supplier<? extends Item> pickaxe, TagKey<Item> material, String has) {
        return this.makePickaxeWithTag(this.getter, pickaxe, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeAxeWithTag(Supplier<? extends Item> axe, TagKey<Item> material, String has) {
        return this.makeAxeWithTag(this.getter, axe, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeShovelWithTag(Supplier<? extends Item> shovel, TagKey<Item> material, String has) {
        return this.makeShovelWithTag(this.getter, shovel, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeHoeWithTag(Supplier<? extends Item> hoe, TagKey<Item> material, String has) {
        return this.makeHoeWithTag(this.getter, hoe, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeSwordWithTag(Supplier<? extends Item> sword, TagKey<Item> material, String has) {
        return this.makeSwordWithTag(this.getter, sword, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeHammerWithTag(Supplier<? extends Item> hammer, TagKey<Item> material, String has) {
        return this.makeHammerWithTag(hammer, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeHammerWithTag(Supplier<? extends Item> hammer, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(this.getter, RecipeCategory.COMBAT, hammer.get())
                .define('#', material)
                .define('/', sticks)
                .pattern(" # ")
                .pattern(" /#")
                .pattern("/  ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeSpearWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makeSpearWithTag(spear, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeSpearWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(this.getter, RecipeCategory.COMBAT, spear.get())
                .define('#', material)
                .define('/', sticks)
                .pattern("#")
                .pattern("/")
                .pattern("#")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeCrossbowWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makeCrossbowWithTag(spear, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeCrossbowWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(this.getter, RecipeCategory.COMBAT, spear.get())
                .define('#', material)
                .define('/', sticks)
                .define('C', AetherIIItems.CLOUDTWINE)
                .pattern("/#/")
                .pattern("C#C")
                .pattern(" / ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, TagKey<Item> material, String has) {
        return this.makeShieldWithTag(shield, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, TagKey<Item> material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(this.getter, RecipeCategory.COMBAT, shield.get())
                .define('W', material)
                .define('o', sticks)
                .pattern("WoW")
                .pattern("WWW")
                .pattern(" W ")
                .unlockedBy(has, has(material));
    }

    protected void parachute(HolderGetter<Item> getter, ItemLike result, ItemLike aercloud) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.TOOLS, result, 1)
                .define('#', aercloud)
                .define('X', AetherIITags.Items.RODS_SKYROOT)
                .pattern("###")
                .pattern("# #")
                .pattern("X X")
                .unlockedBy("has_aercloud", has(aercloud))
                .save(this.output);
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

    protected BlockStateRecipeBuilder acidCorrosion(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AcidCorrosionRecipe::new);
    }

    protected void irradiationCleansing(RecipeCategory recipeCategory, SimpleWeightedRandomList<ItemStack> results, ItemLike ingredient, RecipeOutput consumer) {
        IrradiationCleansingRecipeBuilder.recipe(recipeCategory, Ingredient.of(ingredient), results).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, "cleanse_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath());
    }

    protected void irradiationCleansing(RecipeCategory recipeCategory, SimpleWeightedRandomList<ItemStack> results, ItemLike ingredient, String group, RecipeOutput consumer) {
        IrradiationCleansingRecipeBuilder.recipe(recipeCategory, Ingredient.of(ingredient), results).group(group).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, "cleanse_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath());
    }

    protected BlockStateRecipeBuilder dustIrradiation(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, IrradiationRecipe::new);
    }

    protected BlockStateRecipeBuilder dustIrradiation(BlockPropertyPair result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, IrradiationRecipe::new);
    }
}
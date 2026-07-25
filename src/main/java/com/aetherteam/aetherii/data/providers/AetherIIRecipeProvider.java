package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.aetherteam.aetherii.item.equipment.weapons.AmberDartsItem;
import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.builder.AlkahestPurificationRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.AltarEnchantingRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.BiomeParameterRecipeBuilder;
import com.aetherteam.aetherii.recipe.builder.HourglassRestoringRecipeBuilder;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.DyeRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    private final HolderGetter<Item> getter;

    public AetherIIRecipeProvider(RecipeOutput output, HolderLookup.Provider provider, String id) {
        super(provider, output, id);
        this.getter = provider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void dyedItem(Item target, String group) {
        CustomCraftingRecipeBuilder.customCrafting(
                        RecipeCategory.MISC,
                        (commonInfo, bookInfo) -> new DyeRecipe(commonInfo, bookInfo, Ingredient.of(target), this.tag(ItemTags.DYES), new ItemStackTemplate(target))
                )
                .unlockedBy(getHasName(target), this.has(target))
                .group(group)
                .save(this.output, this.name(getItemName(target) + "_dyed"));
    }

    @Override
    protected void oneToOneConversionRecipe(ItemLike result, ItemLike ingredient, @Nullable String group) {
        this.shapeless(RecipeCategory.MISC, result, 1)
                .requires(ingredient)
                .group(group)
                .unlockedBy(getHasName(ingredient), this.has(ingredient))
                .save(this.output, this.name(getConversionRecipeName(result, ingredient)));
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
            List<ItemLike> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(item1)).collect(Collectors.toList());
            ingredients.add(extra);
            ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.BUILDING_BLOCKS, item1)
                    .requires(item)
                    .requires(Ingredient.of(ingredients.toArray(ItemLike[]::new)))
                    .group(group)
                    .unlockedBy("has_needed_dye", has(item))
                    .save(this.output, this.name("dye_" + getItemName(item1)));
        }
    }

    protected void washDyedBlock(List<Item> dyeableItems, Item output, String group) {
        List<ItemLike> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(output)).collect(Collectors.toList());
        ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.BUILDING_BLOCKS, output)
                .requires(AetherIIItems.SKYROOT_WATER_BUCKET)  //todo switch to vial eventually.
                .requires(Ingredient.of(ingredients.toArray(ItemLike[]::new)))
                .group(group)
                .unlockedBy("has_skyroot_water_bucket", has(AetherIIItems.SKYROOT_WATER_BUCKET))
                .save(this.output, this.name("wash_" + getItemName(output)));
    }

    protected void bed(HolderGetter<Item> getter, ItemLike result, ItemLike wool) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, result)
                .group("skyroot_bed")
                .define('W', wool)
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("WWW")
                .pattern("###")
                .unlockedBy("has_cloudwool", has(wool))
                .save(this.output);
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
                .define('X', AetherIIBlocks.ARKENIUM_CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(block))
                .save(this.output);
    }

    protected void arilumLantern(HolderGetter<Item> getter, ItemLike result, ItemLike dye) {
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, result, 4)
                .group("arilum_lantern")
                .define('D', dye)
                .define('#', AetherIIItems.SWET_GEL)
                .define('X', AetherIIItems.ARILUM_BULBS)
                .pattern("#X#")
                .pattern("XDX")
                .pattern("#X#")
                .unlockedBy("has_bulbs", has(AetherIIItems.ARILUM_BULBS))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.BUILDING_BLOCKS, result)
                .group("arilum_lantern")
                .requires(dye)
                .requires(Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.ARILUM_LANTERN)))
                .unlockedBy("has_lantern", has(result))
                .save(this.output, this.name("dyed_" + getItemName(result)));
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

    protected ShapedRecipeBuilder makePikeWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makePikeWithTag(spear, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makePikeWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
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

    protected ShapedRecipeBuilder makeShieldWithItem(Supplier<? extends Item> shield, Item material, String has) {
        return this.makeShieldWithTag(shield, material, Ingredient.of(this.getter.getOrThrow(AetherIITags.Items.RODS_SKYROOT)), has);
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, Item material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(this.getter, RecipeCategory.COMBAT, shield.get())
                .define('W', material)
                .define('o', sticks)
                .pattern("WoW")
                .pattern("WWW")
                .pattern(" W ")
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

    protected void makeDartsWithEffect(Holder<Item> darts, Supplier<? extends Item> ingredient, EffectBuildupPresets.Preset preset) {
        String effect = BuiltInRegistries.MOB_EFFECT.getKey(preset.type().value()).toString().replace(':', '_');
        ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.MISC, new ItemStackTemplate(darts, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset)).build()))
                .group("amber_darts")
                .requires(Ingredient.of(darts.value()))
                .requires(Ingredient.of(ingredient.get()))
                .unlockedBy("has_ingredient", has(ingredient.get()))
                .save(this.output, this.name("amber_darts_" + effect));
    }

    protected void loadDartShooter(Holder<Item> dartShooter, Holder<Item> darts, EffectBuildupPresets.Preset preset) {
        String effect = BuiltInRegistries.MOB_EFFECT.getKey(preset.type().value()).toString().replace(':', '_');
        ItemStackTemplate effectDarts = new ItemStackTemplate(darts, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset)).build());

        DataComponentPatch dartShooterData = DataComponentPatch.builder()
                .set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(Objects.requireNonNull(effectDarts)))
                .set(AetherIIDataComponents.DARTS_LOADED.get(), AmberDartsItem.FULL_AMOUNT)
                .set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset))
                .build();
        ShapelessRecipeBuilder.shapeless(this.getter, RecipeCategory.MISC, new ItemStackTemplate(dartShooter, 1, dartShooterData))
                .group("load_dart_shooter")
                .requires(Ingredient.of(dartShooter.value()))
                .requires(DataComponentIngredient.of(false, effectDarts))
                .unlockedBy("has_darts", has(darts.value()))
                .save(this.output, this.name("dart_shooter_" + effect));
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material.get()), RecipeCategory.FOOD, CookingBookCategory.FOOD, result.get(), xp, 200).unlockedBy("has_item", has(material.get())).save(consumer, this.name("smelting_" + getHasName(result.get())));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 100).unlockedBy("has_item", has(material.get())).save(consumer, this.name("smoking_" + getHasName(result.get())));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 600).unlockedBy("has_item", has(material.get())).save(consumer, this.name("campfire_cooking_" + getHasName(result.get())));
    }

    protected HourglassItemEntry hourglass(ItemLike item, int count, int weight) {
        return new HourglassItemEntry(item, count, weight);
    }

    protected HourglassDataEntry hourglass(int count, int weight) {
        return new HourglassDataEntry(count, weight);
    }

    protected HourglassRestoringRecipeBuilder hourglassRestoring(RecipeCategory category, ItemLike resultItem, List<HourglassDataEntry> resultInfo, ItemLike ingredient, float experience) {
        WeightedList.Builder<OutputEntry.BaseEntry> builder = WeightedList.builder();
        for (HourglassDataEntry entry : resultInfo) {
            builder.add(new OutputEntry.ItemEntry(new ItemStackTemplate(resultItem.asItem(), entry.count())), entry.weight());
        }
        return HourglassRestoringRecipeBuilder.restoring(Ingredient.of(ingredient), category, new HourglassRestoringRecipe.HourglassOutput(new OutputEntry.EmptyEntry(), new OutputEntry.ListEntry(builder.build()), new OutputEntry.EmptyEntry()), experience, 200, false).unlockedBy("has_item", has(ingredient));
    }

    protected HourglassRestoringRecipeBuilder hourglassUncraftingItem(RecipeCategory category, ItemLike resultItem1, List<HourglassDataEntry> resultInfo1, ItemLike resultItem2, List<HourglassDataEntry> resultInfo2, ItemLike resultItem3, List<HourglassDataEntry> resultInfo3, ItemLike ingredient, float experience) {
        return this.hourglassUncraftingIngredient(category, resultItem1, resultInfo1, resultItem2, resultInfo2, resultItem3, resultInfo3, Ingredient.of(ingredient), experience, this.has(ingredient));
    }

    protected HourglassRestoringRecipeBuilder hourglassUncraftingIngredient(RecipeCategory category, ItemLike resultItem1, List<HourglassDataEntry> resultInfo1, ItemLike resultItem2, List<HourglassDataEntry> resultInfo2, ItemLike resultItem3, List<HourglassDataEntry> resultInfo3, Ingredient ingredient, float experience, Criterion<?> has) {
        WeightedList.Builder<OutputEntry.BaseEntry> builder1 = WeightedList.builder();
        for (HourglassDataEntry entry : resultInfo1) {
            if (resultItem1.asItem() == Items.AIR || entry.count() == 0) {
                builder1.add(new OutputEntry.EmptyEntry(), entry.weight());
            } else {
                builder1.add(new OutputEntry.ItemEntry(new ItemStackTemplate(resultItem1.asItem(), entry.count())), entry.weight());
            }
        }
        WeightedList.Builder<OutputEntry.BaseEntry> builder2 = WeightedList.builder();
        for (HourglassDataEntry entry : resultInfo2) {
            if (resultItem2.asItem() == Items.AIR || entry.count() == 0) {
                builder2.add(new OutputEntry.EmptyEntry(), entry.weight());
            } else {
                builder2.add(new OutputEntry.ItemEntry(new ItemStackTemplate(resultItem2.asItem(), entry.count())), entry.weight());
            }
        }
        WeightedList.Builder<OutputEntry.BaseEntry> builder3 = WeightedList.builder();
        for (HourglassDataEntry entry : resultInfo3) {
            if (resultItem3.asItem() == Items.AIR || entry.count() == 0) {
                builder3.add(new OutputEntry.EmptyEntry(), entry.weight());
            } else {
                builder3.add(new OutputEntry.ItemEntry(new ItemStackTemplate(resultItem3.asItem(), entry.count())), entry.weight());
            }
        }
        return HourglassRestoringRecipeBuilder.restoring(ingredient, category, new HourglassRestoringRecipe.HourglassOutput(new OutputEntry.ListEntry(builder1.build()), new OutputEntry.ListEntry(builder2.build()), new OutputEntry.ListEntry(builder3.build())), experience, 200, true).unlockedBy("has_item", has);
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, AltarBookCategory bookCategory, ItemLike result, ItemLike ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(ingredient), category, bookCategory, new ItemStackTemplate(result.asItem()), experience, fuelCount, 200).unlockedBy("has_item", has(ingredient));
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, AltarBookCategory bookCategory, ItemStackTemplate result, ItemStackTemplate ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(DataComponentIngredient.of(false, ingredient), category, bookCategory, result, experience, fuelCount, 200).unlockedBy("has_item", has(ingredient.item().value()));
    }

    protected AltarEnchantingRecipeBuilder altarRepairing(RecipeCategory category, ItemLike item, int fuelCount) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(item), category, AltarBookCategory.REPAIRING, new ItemStackTemplate(item.asItem()), 0.0F, fuelCount, 200).unlockedBy("has_item", has(item));
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

    protected BlockStateRecipeBuilder accessoryFreezable(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AccessoryFreezableRecipe::new);
    }

    protected BiomeParameterRecipeBuilder accessoryFreezableTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, AccessoryFreezableRecipe::new);
    }

    protected BlockStateRecipeBuilder alkahestCorrosion(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, AlkahestCorrosionRecipe::new);
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, ItemLike ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, RecipeOutput consumer) {
        AlkahestPurificationRecipeBuilder.recipe(Ingredient.of(ingredient), recipeCategory, bookCategory, results, byproducts, 0.0F, alkahestUsage, 200).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, this.name("purify_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath()));
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, ItemLike ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, String group, RecipeOutput consumer) {
        AlkahestPurificationRecipeBuilder.recipe(Ingredient.of(ingredient), recipeCategory, bookCategory, results, byproducts, 0.0F, alkahestUsage, 200).group(group).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, this.name("purify_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath()));
    }

    protected BlockStateRecipeBuilder dustIrradiation(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, IrradiationRecipe::new);
    }

    protected BlockStateRecipeBuilder dustIrradiation(BlockPropertyPair result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, IrradiationRecipe::new);
    }

    protected OutputEntry.BaseEntry multiple(ItemLike item, int max) {
        return this.multiple(item, 1, max, 1, false);
    }

    protected OutputEntry.BaseEntry multiple(ItemLike item, int min, int max, int interval, boolean constantWeight) {
        WeightedList.Builder<OutputEntry.BaseEntry> builder = WeightedList.builder();
        for (int i = min; i <= max; i += interval) {
            builder.add(new OutputEntry.ItemEntry(new ItemStackTemplate(item.asItem(), i)), constantWeight ? 1 : (max + 1) - i);
        }
        return new OutputEntry.ListEntry(builder.build());
    }

    public record HourglassItemEntry(ItemLike item, int count, int weight) { }

    public record HourglassDataEntry(int count, int weight) { }
}
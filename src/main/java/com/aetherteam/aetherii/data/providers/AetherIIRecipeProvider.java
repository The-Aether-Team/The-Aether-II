package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.aetherteam.aetherii.item.equipment.weapons.AmberDartsItem;
import com.aetherteam.aetherii.item.components.DataComponentType;
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
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import com.aetherteam.aetherii.item.components.DataComponentPatch;
import com.aetherteam.aetherii.item.components.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import com.aetherteam.aetherii.util.random.WeightedList;
import net.minecraft.world.item.Item;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import net.minecraft.world.item.Items;
import com.aetherteam.aetherii.item.components.ChargedProjectiles;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    protected final HolderLookup.Provider registries;
    protected Consumer<FinishedRecipe> output;
    private final HolderGetter<Item> getter;

    public AetherIIRecipeProvider(net.minecraft.data.PackOutput output, HolderLookup.Provider provider, String id) {
        super(output, id);
        this.registries = provider;
        this.getter = provider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected final void buildRecipes(Consumer<FinishedRecipe> output) {
        this.output = output;
        this.buildRecipes();
    }

    protected abstract void buildRecipes();

    protected void dyedItem(Item target, String group) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, target)
                .requires(target)
                .requires(Tags.Items.DYES)
                .unlockedBy(getHasName(target), this.has(target))
                .group(group)
                .save(this.output, this.name(getItemName(target) + "_dyed"));
    }

    protected void oneToOneConversionRecipe(ItemLike result, ItemLike ingredient, @Nullable String group) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 1)
                .requires(ingredient)
                .group(group)
                .unlockedBy(getHasName(ingredient), this.has(ingredient))
                .save(this.output, this.name(getConversionRecipeName(result, ingredient)));
    }

    protected void oneToOneConversionRecipe(ItemLike result, Supplier<? extends ItemLike> ingredient, @Nullable String group) {
        this.oneToOneConversionRecipe(result, ingredient.get(), group);
    }

    protected void oneToOneConversionRecipe(Supplier<? extends ItemLike> result, ItemLike ingredient, @Nullable String group) {
        this.oneToOneConversionRecipe(result.get(), ingredient, group);
    }

    protected void oneToOneConversionRecipe(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient, @Nullable String group) {
        this.oneToOneConversionRecipe(result.get(), ingredient.get(), group);
    }

    protected static ItemLike itemLike(Supplier<? extends ItemLike> item) {
        return item.get();
    }

    private static ItemLike resolveItemLike(Object item) {
        if (item instanceof ItemLike itemLike) {
            return itemLike;
        } else if (item instanceof Supplier<?> supplier && supplier.get() instanceof ItemLike itemLike) {
            return itemLike;
        }
        throw new IllegalArgumentException("Expected ItemLike or Supplier<ItemLike>, got " + item);
    }

    private static Holder<Item> holder(Supplier<? extends Item> item) {
        return BuiltInRegistries.ITEM.wrapAsHolder(item.get());
    }

    protected static CriterionTriggerInstance has(Supplier<? extends ItemLike> item) {
        return has(item.get());
    }

    protected static String getHasName(Supplier<? extends ItemLike> item) {
        return getHasName(item.get());
    }

    protected static String getItemName(Supplier<? extends ItemLike> item) {
        return getItemName(item.get());
    }

    protected static String getSimpleRecipeName(Supplier<? extends ItemLike> item) {
        return getSimpleRecipeName(item.get());
    }

    protected static String getConversionRecipeName(Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
        return getConversionRecipeName(result.get(), ingredient.get());
    }

    protected static String getConversionRecipeName(ItemLike result, Supplier<? extends ItemLike> ingredient) {
        return getConversionRecipeName(result, ingredient.get());
    }

    protected static String getConversionRecipeName(Supplier<? extends ItemLike> result, ItemLike ingredient) {
        return getConversionRecipeName(result.get(), ingredient);
    }

    protected static Ingredient strictIngredient(ItemStackTemplate template) {
        return StrictNBTIngredient.of(template.create());
    }

    protected static <T> Ingredient componentIngredient(DataComponentType<T> type, T value, ItemLike item) {
        return strictIngredient(new ItemStackTemplate(item, 1, DataComponentPatch.builder().set(type, value).build()));
    }

    protected void leafPile(HolderGetter<Item> getter, ItemLike carpet, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, carpet, 8)
                .define('#', material)
                .pattern("##")
                .group("leaf_pile")
                .unlockedBy(getHasName(material), has(material))
                .save(this.output);
    }

    protected void leafPile(HolderGetter<Item> getter, Supplier<? extends ItemLike> carpet, ItemLike material) {
        this.leafPile(getter, carpet.get(), material);
    }

    protected void leafPile(HolderGetter<Item> getter, Supplier<? extends ItemLike> carpet, Supplier<? extends ItemLike> material) {
        this.leafPile(getter, carpet.get(), material.get());
    }

    protected ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(category, result, count);
    }

    protected ShapedRecipeBuilder shaped(RecipeCategory category, Supplier<? extends ItemLike> result, int count) {
        return this.shaped(category, result.get(), count);
    }

    protected void planksFromLog(ItemLike planks, TagKey<Item> log, int count) {
        planksFromLog(this.output, planks, log, count);
    }

    protected void planksFromLog(Supplier<? extends ItemLike> planks, TagKey<Item> log, int count) {
        this.planksFromLog(planks.get(), log, count);
    }

    protected void woodFromLogs(ItemLike wood, ItemLike log) {
        woodFromLogs(this.output, wood, log);
    }

    protected void woodFromLogs(Supplier<? extends ItemLike> wood, Supplier<? extends ItemLike> log) {
        this.woodFromLogs(wood.get(), log.get());
    }

    protected RecipeBuilder doorBuilder(Supplier<? extends ItemLike> door, Ingredient material) {
        return doorBuilder(door.get(), material);
    }

    protected RecipeBuilder trapdoorBuilder(Supplier<? extends ItemLike> trapdoor, Ingredient material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor.get(), 2).define('#', material).pattern("###").pattern("###");
    }

    protected RecipeBuilder slabBuilder(RecipeCategory category, Supplier<? extends ItemLike> slab, Ingredient material) {
        return slabBuilder(category, slab.get(), material);
    }

    protected void slab(RecipeCategory category, ItemLike slab, ItemLike material) {
        slab(this.output, category, slab, material);
    }

    protected void slab(RecipeCategory category, Supplier<? extends ItemLike> slab, Supplier<? extends ItemLike> material) {
        this.slab(category, slab.get(), material.get());
    }

    protected void polished(RecipeCategory category, ItemLike result, ItemLike material) {
        polished(this.output, category, result, material);
    }

    protected void polished(RecipeCategory category, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        this.polished(category, result.get(), material.get());
    }

    protected void cut(RecipeCategory category, ItemLike result, ItemLike material) {
        cut(this.output, category, result, material);
    }

    protected void chiseled(RecipeCategory category, ItemLike result, ItemLike material) {
        chiseled(this.output, category, result, material);
    }

    protected void shelf(Supplier<? extends ItemLike> shelf, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, shelf.get(), 6)
                .define('#', material.get())
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .group("shelf")
                .unlockedBy(getHasName(material), has(material))
                .save(this.output);
    }

    protected void oreBlockStorageRecipesRecipesWithCustomUnpacking(HolderGetter<Item> getter, Consumer<FinishedRecipe> output, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, Supplier<? extends ItemLike> packed, String unpackingRecipeName, String packingRecipeName) {
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(output, unpackedCategory, unpacked, packedCategory, packed.get(), unpackingRecipeName, packingRecipeName);
    }

    protected void nineBlockStorageRecipes(RecipeCategory unpackedCategory, Supplier<? extends ItemLike> unpacked, RecipeCategory packedCategory, Supplier<? extends ItemLike> packed, String packedRecipeName, @Nullable String packedGroup, String unpackingRecipeName, @Nullable String unpackingGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked.get(), 9)
                .requires(packed.get())
                .group(unpackingGroup)
                .unlockedBy(getHasName(packed), has(packed))
                .save(this.output, new ResourceLocation(unpackingRecipeName));
        ShapedRecipeBuilder.shaped(packedCategory, packed.get())
                .define('#', unpacked.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(packedGroup)
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(this.output, new ResourceLocation(packedRecipeName));
    }

    protected ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return this.fence(fence, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return this.fenceGate(fenceGate, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT));
    }

    protected void cloudwool(HolderGetter<Item> getter, RecipeCategory itemCategory, ItemLike item, RecipeCategory blockCategory, ItemLike block, String itemRecipeName, String itemGroup) {
        ShapelessRecipeBuilder.shapeless(itemCategory, item, 4).requires(block).group(itemGroup).unlockedBy(getHasName(block), has(block)).save(this.output, this.name(itemRecipeName));
        ShapedRecipeBuilder.shaped(blockCategory, block).define('#', item).pattern("##").pattern("##").unlockedBy(getHasName(item), has(item)).save(this.output, this.name(getSimpleRecipeName(block)));
    }

    protected void colorBlockWithDye(List<Item> dyes, List<Item> dyeableItems, Item extra, String group) {
        for(int i = 0; i < dyes.size(); ++i) {
            Item item = dyes.get(i);
            Item item1 = dyeableItems.get(i);
            List<ItemLike> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(item1)).collect(Collectors.toList());
            ingredients.add(extra);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item1)
                    .requires(item)
                    .requires(Ingredient.of(ingredients.toArray(ItemLike[]::new)))
                    .group(group)
                    .unlockedBy("has_needed_dye", has(item))
                    .save(this.output, this.name("dye_" + getItemName(item1)));
        }
    }

    protected void washDyedBlock(List<Item> dyeableItems, Item output, String group) {
        List<ItemLike> ingredients = dyeableItems.stream().filter(itemElement -> !itemElement.equals(output)).collect(Collectors.toList());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output)
                .requires(AetherIIItems.SKYROOT_WATER_BUCKET.get())  //todo switch to vial eventually.
                .requires(Ingredient.of(ingredients.toArray(ItemLike[]::new)))
                .group(group)
                .unlockedBy("has_skyroot_water_bucket", has(AetherIIItems.SKYROOT_WATER_BUCKET.get()))
                .save(this.output, this.name("wash_" + getItemName(output)));
    }

    protected void bed(HolderGetter<Item> getter, ItemLike result, ItemLike wool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .group("skyroot_bed")
                .define('W', wool)
                .define('#', AetherIITags.Items.PLANKS_CRAFTING)
                .pattern("WWW")
                .pattern("###")
                .unlockedBy("has_cloudwool", has(wool))
                .save(this.output);
    }

    protected void bookshelf(HolderGetter<Item> getter, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', material)
                .define('B', Items.BOOK)
                .pattern("###")
                .pattern("BBB")
                .pattern("###")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(this.output);
    }

    protected void sign(HolderGetter<Item> getter, ItemLike result, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 3)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 6)
                .group("hanging_sign")
                .define('#', block)
                .define('X', AetherIIBlocks.ARKENIUM_CHAIN.get())
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(block))
                .save(this.output);
    }

    protected void arilumLantern(HolderGetter<Item> getter, ItemLike result, ItemLike dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 4)
                .group("arilum_lantern")
                .define('D', dye)
                .define('#', AetherIIItems.SWET_GEL.get())
                .define('X', AetherIIItems.ARILUM_BULBS.get())
                .pattern("#X#")
                .pattern("XDX")
                .pattern("#X#")
                .unlockedBy("has_bulbs", has(AetherIIItems.ARILUM_BULBS.get()))
                .save(this.output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
                .group("arilum_lantern")
                .requires(dye)
                .requires(Ingredient.of(AetherIITags.Items.ARILUM_LANTERN))
                .unlockedBy("has_lantern", has(result))
                .save(this.output, this.name("dyed_" + getItemName(result)));
    }

    protected void arilumLantern(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, ItemLike dye) {
        this.arilumLantern(getter, result.get(), dye);
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

    protected ShapedRecipeBuilder makePikeWithTag(Supplier<? extends Item> spear, TagKey<Item> material, String has) {
        return this.makePikeWithTag(spear, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makePikeWithTag(Supplier<? extends Item> spear, TagKey<Item> material, Ingredient sticks, String has) {
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
                .define('C', AetherIIItems.CLOUDTWINE.get())
                .pattern("/#/")
                .pattern("C#C")
                .pattern(" / ")
                .unlockedBy(has, has(material));
    }

    protected ShapedRecipeBuilder makeShieldWithItem(Supplier<? extends Item> shield, Item material, String has) {
        return this.makeShieldWithTag(shield, material, Ingredient.of(AetherIITags.Items.RODS_SKYROOT), has);
    }

    protected ShapedRecipeBuilder makeShieldWithTag(Supplier<? extends Item> shield, Item material, Ingredient sticks, String has) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, shield.get())
                .define('W', material)
                .define('o', sticks)
                .pattern("WoW")
                .pattern("WWW")
                .pattern(" W ")
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

    protected void makeDartsWithEffect(Holder<Item> darts, Supplier<? extends Item> ingredient, EffectBuildupPresets.Preset preset) {
        String effect = BuiltInRegistries.MOB_EFFECT.getKey(preset.type().value()).toString().replace(':', '_');
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStackTemplate(darts, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset)).build()))
                .group("amber_darts")
                .requires(Ingredient.of(darts.value()))
                .requires(Ingredient.of(ingredient.get()))
                .unlockedBy("has_ingredient", has(ingredient.get()))
                .save(this.output, this.name("amber_darts_" + effect));
    }

    protected void makeDartsWithEffect(Supplier<? extends Item> darts, Supplier<? extends Item> ingredient, EffectBuildupPresets.Preset preset) {
        this.makeDartsWithEffect(holder(darts), ingredient, preset);
    }

    protected void loadDartShooter(Holder<Item> dartShooter, Holder<Item> darts, EffectBuildupPresets.Preset preset) {
        String effect = BuiltInRegistries.MOB_EFFECT.getKey(preset.type().value()).toString().replace(':', '_');
        ItemStackTemplate effectDarts = new ItemStackTemplate(darts, 1, DataComponentPatch.builder().set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset)).build());

        DataComponentPatch dartShooterData = DataComponentPatch.builder()
                .set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(Objects.requireNonNull(effectDarts)))
                .set(AetherIIDataComponents.DARTS_LOADED.get(), AmberDartsItem.FULL_AMOUNT)
                .set(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(preset))
                .build();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStackTemplate(dartShooter, 1, dartShooterData))
                .group("load_dart_shooter")
                .requires(Ingredient.of(dartShooter.value()))
                .requires(strictIngredient(effectDarts))
                .unlockedBy("has_darts", has(darts.value()))
                .save(this.output, this.name("dart_shooter_" + effect));
    }

    protected void loadDartShooter(Supplier<? extends Item> dartShooter, Supplier<? extends Item> darts, EffectBuildupPresets.Preset preset) {
        this.loadDartShooter(holder(dartShooter), holder(darts), preset);
    }

    protected void parachute(HolderGetter<Item> getter, ItemLike result, ItemLike aercloud) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result, 1)
                .define('#', aercloud)
                .define('X', AetherIITags.Items.RODS_SKYROOT)
                .pattern("###")
                .pattern("# #")
                .pattern("X X")
                .unlockedBy("has_aercloud", has(aercloud))
                .save(this.output);
    }

    protected final void foodCooking(Supplier<? extends ItemLike> material, Supplier<? extends ItemLike> result, float xp, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material.get()), RecipeCategory.FOOD, result.get(), xp, 200).unlockedBy("has_item", has(material.get())).save(consumer, this.name("smelting_" + getHasName(result.get())));
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

    protected HourglassRestoringRecipeBuilder hourglassRestoring(RecipeCategory category, Object resultItem, List<HourglassDataEntry> resultInfo, Object ingredient, float experience) {
        return this.hourglassRestoring(category, resolveItemLike(resultItem), resultInfo, resolveItemLike(ingredient), experience);
    }

    protected HourglassRestoringRecipeBuilder hourglassUncraftingItem(RecipeCategory category, ItemLike resultItem1, List<HourglassDataEntry> resultInfo1, ItemLike resultItem2, List<HourglassDataEntry> resultInfo2, ItemLike resultItem3, List<HourglassDataEntry> resultInfo3, ItemLike ingredient, float experience) {
        return this.hourglassUncraftingIngredient(category, resultItem1, resultInfo1, resultItem2, resultInfo2, resultItem3, resultInfo3, Ingredient.of(ingredient), experience, this.has(ingredient));
    }

    protected HourglassRestoringRecipeBuilder hourglassUncraftingItem(RecipeCategory category, Object resultItem1, List<HourglassDataEntry> resultInfo1, Object resultItem2, List<HourglassDataEntry> resultInfo2, Object resultItem3, List<HourglassDataEntry> resultInfo3, Object ingredient, float experience) {
        ItemLike resolvedIngredient = resolveItemLike(ingredient);
        return this.hourglassUncraftingIngredient(category, resolveItemLike(resultItem1), resultInfo1, resolveItemLike(resultItem2), resultInfo2, resolveItemLike(resultItem3), resultInfo3, Ingredient.of(resolvedIngredient), experience, this.has(resolvedIngredient));
    }

    protected HourglassRestoringRecipeBuilder hourglassUncraftingIngredient(RecipeCategory category, ItemLike resultItem1, List<HourglassDataEntry> resultInfo1, ItemLike resultItem2, List<HourglassDataEntry> resultInfo2, ItemLike resultItem3, List<HourglassDataEntry> resultInfo3, Ingredient ingredient, float experience, CriterionTriggerInstance has) {
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

    protected HourglassRestoringRecipeBuilder hourglassUncraftingIngredient(RecipeCategory category, Object resultItem1, List<HourglassDataEntry> resultInfo1, Object resultItem2, List<HourglassDataEntry> resultInfo2, Object resultItem3, List<HourglassDataEntry> resultInfo3, Ingredient ingredient, float experience, CriterionTriggerInstance has) {
        return this.hourglassUncraftingIngredient(category, resolveItemLike(resultItem1), resultInfo1, resolveItemLike(resultItem2), resultInfo2, resolveItemLike(resultItem3), resultInfo3, ingredient, experience, has);
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, AltarBookCategory bookCategory, ItemLike result, ItemLike ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(ingredient), category, bookCategory, new ItemStackTemplate(result.asItem()), experience, fuelCount, 200).unlockedBy("has_item", has(ingredient));
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, AltarBookCategory bookCategory, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient, int fuelCount, float experience) {
        return this.altarEnchanting(category, bookCategory, result.get(), ingredient.get(), fuelCount, experience);
    }

    protected AltarEnchantingRecipeBuilder altarEnchanting(RecipeCategory category, AltarBookCategory bookCategory, ItemStackTemplate result, ItemStackTemplate ingredient, int fuelCount, float experience) {
        return AltarEnchantingRecipeBuilder.enchanting(strictIngredient(ingredient), category, bookCategory, result, experience, fuelCount, 200).unlockedBy("has_item", has(ingredient.item()));
    }

    protected AltarEnchantingRecipeBuilder altarRepairing(RecipeCategory category, ItemLike item, int fuelCount) {
        return AltarEnchantingRecipeBuilder.enchanting(Ingredient.of(item), category, AltarBookCategory.REPAIRING, new ItemStackTemplate(item.asItem()), 0.0F, fuelCount, 200).unlockedBy("has_item", has(item));
    }

    protected AltarEnchantingRecipeBuilder altarRepairing(RecipeCategory category, Supplier<? extends ItemLike> item, int fuelCount) {
        return this.altarRepairing(category, item.get(), fuelCount);
    }

    protected BlockStateRecipeBuilder ambrosiumEnchanting(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, blockStateSerializer(AmbrosiumRecipe.SERIALIZER));
    }

    protected BiomeParameterRecipeBuilder swetGelConversion(Block result, Block ingredient) {
        return BiomeParameterRecipeBuilder.recipe(BlockPropertyPair.of(result, Map.of()), BlockStateIngredient.of(ingredient), Optional.empty(), SwetGelRecipe::new);
    }

    protected BiomeParameterRecipeBuilder swetGelConversionTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, SwetGelRecipe::new);
    }

    protected BiomeParameterRecipeBuilder icestoneFreezable(Block result, Block ingredient) {
        return BiomeParameterRecipeBuilder.recipe(BlockPropertyPair.of(result, Map.of()), BlockStateIngredient.of(ingredient), Optional.empty(), IcestoneFreezableRecipe::new);
    }

    protected BiomeParameterRecipeBuilder icestoneFreezableTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, IcestoneFreezableRecipe::new);
    }

    protected BiomeParameterRecipeBuilder accessoryFreezable(Block result, Block ingredient) {
        return BiomeParameterRecipeBuilder.recipe(BlockPropertyPair.of(result, Map.of()), BlockStateIngredient.of(ingredient), Optional.empty(), AccessoryFreezableRecipe::new);
    }

    protected BiomeParameterRecipeBuilder accessoryFreezableTag(Block result, Block ingredient, TagKey<Biome> tagKey) {
        return BiomeParameterRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, tagKey, AccessoryFreezableRecipe::new);
    }

    protected BlockStateRecipeBuilder alkahestCorrosion(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, blockStateSerializer(AlkahestCorrosionRecipe.SERIALIZER));
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, ItemLike ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, Consumer<FinishedRecipe> consumer) {
        AlkahestPurificationRecipeBuilder.recipe(Ingredient.of(ingredient), recipeCategory, bookCategory, results, byproducts, 0.0F, alkahestUsage, 200).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, this.name("purify_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath()));
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, Supplier<? extends ItemLike> ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, Consumer<FinishedRecipe> consumer) {
        this.alkahestPurification(recipeCategory, bookCategory, results, ingredient.get(), byproducts, alkahestUsage, consumer);
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, ItemLike ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, String group, Consumer<FinishedRecipe> consumer) {
        AlkahestPurificationRecipeBuilder.recipe(Ingredient.of(ingredient), recipeCategory, bookCategory, results, byproducts, 0.0F, alkahestUsage, 200).group(group).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, this.name("purify_" + BuiltInRegistries.ITEM.getKey(ingredient.asItem()).getPath()));
    }

    protected void alkahestPurification(RecipeCategory recipeCategory, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, Supplier<? extends ItemLike> ingredient, OutputEntry.BaseEntry byproducts, int alkahestUsage, String group, Consumer<FinishedRecipe> consumer) {
        this.alkahestPurification(recipeCategory, bookCategory, results, ingredient.get(), byproducts, alkahestUsage, group, consumer);
    }

    protected BlockStateRecipeBuilder dustIrradiation(Block result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, blockStateSerializer(IrradiationRecipe.SERIALIZER));
    }

    protected BlockStateRecipeBuilder dustIrradiation(BlockPropertyPair result, Block ingredient) {
        return BlockStateRecipeBuilder.recipe(BlockStateIngredient.of(ingredient), result, blockStateSerializer(IrradiationRecipe.SERIALIZER));
    }

    protected void twoByTwoPacker(RecipeCategory category, Supplier<? extends ItemLike> packed, Supplier<? extends ItemLike> unpacked) {
        twoByTwoPacker(this.output, category, packed.get(), unpacked.get());
    }

    protected void twoByTwoPacker(RecipeCategory category, Supplier<? extends ItemLike> packed, ItemLike unpacked) {
        twoByTwoPacker(this.output, category, packed.get(), unpacked);
    }

    protected void threeByThreePacker(RecipeCategory category, Supplier<? extends ItemLike> packed, Supplier<? extends ItemLike> unpacked) {
        threeByThreePacker(this.output, category, packed.get(), unpacked.get());
    }

    protected void carpet(Supplier<? extends ItemLike> carpet, Supplier<? extends ItemLike> material) {
        carpet(this.output, carpet.get(), material.get());
    }

    protected void carpet(Supplier<? extends ItemLike> carpet, ItemLike material) {
        carpet(this.output, carpet.get(), material);
    }

    protected void wall(RecipeCategory category, Supplier<? extends ItemLike> wall, Supplier<? extends ItemLike> material) {
        wall(this.output, category, wall.get(), material.get());
    }

    protected void wall(RecipeCategory category, ItemLike wall, ItemLike material) {
        wall(this.output, category, wall, material);
    }

    protected void wall(RecipeCategory category, Supplier<? extends ItemLike> wall, ItemLike material) {
        this.wall(category, wall.get(), material);
    }

    protected void wall(RecipeCategory category, ItemLike wall, Supplier<? extends ItemLike> material) {
        this.wall(category, wall, material.get());
    }

    protected void stonecuttingRecipe(Consumer<FinishedRecipe> output, RecipeCategory category, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient) {
        this.stonecuttingRecipe(output, category, result.get(), ingredient.get());
    }

    protected void stonecuttingRecipe(Consumer<FinishedRecipe> output, RecipeCategory category, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> ingredient, int count) {
        this.stonecuttingRecipe(output, category, result.get(), ingredient.get(), count);
    }

    protected void bookshelf(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        this.bookshelf(getter, result.get(), material.get());
    }

    protected void sign(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> block) {
        this.sign(getter, result.get(), block.get());
    }

    protected void sign(HolderGetter<Item> getter, ItemLike result, Supplier<? extends ItemLike> block) {
        this.sign(getter, result, block.get());
    }

    protected void sign(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, ItemLike block) {
        this.sign(getter, result.get(), block);
    }

    protected void hangingSign(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> block) {
        this.hangingSign(getter, result.get(), block.get());
    }

    protected void hangingSign(HolderGetter<Item> getter, ItemLike result, Supplier<? extends ItemLike> block) {
        this.hangingSign(getter, result, block.get());
    }

    protected void hangingSign(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, ItemLike block) {
        this.hangingSign(getter, result.get(), block);
    }

    protected void bed(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> wool) {
        this.bed(getter, result.get(), wool.get());
    }

    protected void bookshelf(HolderGetter<Item> getter, ItemLike result, Supplier<? extends ItemLike> material) {
        this.bookshelf(getter, result, material.get());
    }

    protected void parachute(HolderGetter<Item> getter, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> aercloud) {
        this.parachute(getter, result.get(), aercloud.get());
    }

    protected ShapedRecipeBuilder makeHelmet(HolderGetter<Item> getter, Supplier<? extends Item> armor, Supplier<? extends Item> material) {
        return this.makeHelmet(armor, material);
    }

    protected ShapedRecipeBuilder makeChestplate(HolderGetter<Item> getter, Supplier<? extends Item> armor, Supplier<? extends Item> material) {
        return this.makeChestplate(armor, material);
    }

    protected ShapedRecipeBuilder makeLeggings(HolderGetter<Item> getter, Supplier<? extends Item> armor, Supplier<? extends Item> material) {
        return this.makeLeggings(armor, material);
    }

    protected ShapedRecipeBuilder makeBoots(HolderGetter<Item> getter, Supplier<? extends Item> armor, Supplier<? extends Item> material) {
        return this.makeBoots(armor, material);
    }

    protected ShapedRecipeBuilder makeGloves(HolderGetter<Item> getter, Supplier<? extends Item> armor, Supplier<? extends Item> material) {
        return this.makeGloves(armor, material);
    }

    protected ShapedRecipeBuilder makeHelmetWithTag(HolderGetter<Item> getter, Supplier<? extends Item> armor, TagKey<Item> material, String has) {
        return this.makeHelmetWithTag(armor, material, has);
    }

    protected ShapedRecipeBuilder makeChestplateWithTag(HolderGetter<Item> getter, Supplier<? extends Item> armor, TagKey<Item> material, String has) {
        return this.makeChestplateWithTag(armor, material, has);
    }

    protected ShapedRecipeBuilder makeLeggingsWithTag(HolderGetter<Item> getter, Supplier<? extends Item> armor, TagKey<Item> material, String has) {
        return this.makeLeggingsWithTag(armor, material, has);
    }

    protected ShapedRecipeBuilder makeBootsWithTag(HolderGetter<Item> getter, Supplier<? extends Item> armor, TagKey<Item> material, String has) {
        return this.makeBootsWithTag(armor, material, has);
    }

    protected ShapedRecipeBuilder makeGlovesWithTag(HolderGetter<Item> getter, Supplier<? extends Item> armor, TagKey<Item> material, String has) {
        return this.makeGlovesWithTag(armor, material, has);
    }

    protected ShapedRecipeBuilder makePendantWithTag(HolderGetter<Item> getter, Supplier<? extends Item> pendant, TagKey<Item> material, Ingredient string, String has) {
        return this.makePendantWithTag(pendant, material, string, has);
    }

    protected ShapedRecipeBuilder makePendant(HolderGetter<Item> getter, Supplier<? extends Item> pendant, Item material, Ingredient string) {
        return this.makePendant(pendant, material, string);
    }

    @SuppressWarnings("unchecked")
    private static BlockStateRecipeSerializer<?> blockStateSerializer(RecipeSerializer<?> serializer) {
        return (BlockStateRecipeSerializer<?>) serializer;
    }

    protected OutputEntry.BaseEntry multiple(ItemLike item, int max) {
        return this.multiple(item, 1, max, 1, false);
    }

    protected OutputEntry.BaseEntry multiple(Supplier<? extends ItemLike> item, int max) {
        return this.multiple(item.get(), max);
    }

    protected OutputEntry.BaseEntry multiple(ItemLike item, int min, int max, int interval, boolean constantWeight) {
        WeightedList.Builder<OutputEntry.BaseEntry> builder = WeightedList.builder();
        for (int i = min; i <= max; i += interval) {
            builder.add(new OutputEntry.ItemEntry(new ItemStackTemplate(item.asItem(), i)), constantWeight ? 1 : (max + 1) - i);
        }
        return new OutputEntry.ListEntry(builder.build());
    }

    protected OutputEntry.BaseEntry multiple(Supplier<? extends ItemLike> item, int min, int max, int interval, boolean constantWeight) {
        return this.multiple(item.get(), min, max, interval, constantWeight);
    }

    public record HourglassItemEntry(ItemLike item, int count, int weight) { }

    public record HourglassDataEntry(int count, int weight) { }
}

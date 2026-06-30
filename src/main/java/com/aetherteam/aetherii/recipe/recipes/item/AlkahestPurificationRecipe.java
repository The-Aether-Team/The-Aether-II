package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.DataComponentPatch;
import com.aetherteam.aetherii.item.components.DataComponents;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import com.aetherteam.aetherii.recipe.book.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import com.aetherteam.aetherii.recipe.display.RecipeDisplay;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlkahestPurificationRecipe implements Recipe<SingleRecipeInputWithRandom> {
    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final String group;
    protected final AlkahestPurifierBookCategory category;
    protected final Ingredient ingredient;
    protected final OutputEntry.BaseEntry results;
    protected final OutputEntry.BaseEntry byproducts;
    protected final float experience;
    protected final int alkahestUsage;
    protected final int processingTime;

    public AlkahestPurificationRecipe(ResourceLocation id, String group, AlkahestPurifierBookCategory category, Ingredient ingredient, OutputEntry.BaseEntry results, OutputEntry.BaseEntry byproducts, float experience, int alkahestUsage, int processingTime) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.results = results;
        this.byproducts = byproducts;
        this.experience = experience;
        this.alkahestUsage = alkahestUsage;
        this.processingTime = processingTime;
    }

    public String group() {
        return this.group;
    }

    public AlkahestPurifierBookCategory category() {
        return this.category;
    }

    public Ingredient ingredient() {
        return this.ingredient;
    }

    public OutputEntry.BaseEntry results() {
        return this.results;
    }

    public OutputEntry.BaseEntry byproducts() {
        return this.byproducts;
    }

    public float experience() {
        return this.experience;
    }

    public int alkahestUsage() {
        return this.alkahestUsage;
    }

    public int processingTime() {
        return this.processingTime;
    }

    @Override
    public boolean matches(SingleRecipeInputWithRandom input, Level level) {
        return this.ingredient().test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInputWithRandom input, RegistryAccess registryAccess) {
        return this.results().process(input.randomSource());
    }

    public ItemStack assemble(SingleRecipeInputWithRandom input) {
        return this.results().process(input.randomSource());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.ingredient);
        return ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.irradiatedResultTemplate()
                .orElseGet(() -> this.results.list().stream().findFirst().orElse(new ItemStackTemplate(Items.AIR)))
                .create();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.ALKAHEST_PURIFIER.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeType<AlkahestPurificationRecipe> getType() {
        return AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get();
    }

    @Override
    public RecipeSerializer<AlkahestPurificationRecipe> getSerializer() {
        return SERIALIZER;
    }

    public List<RecipeDisplay> display() {
        SlotDisplay resultDisplay = this.irradiatedResultTemplate()
                .<SlotDisplay>map(SlotDisplay.ItemStackSlotDisplay::new)
                .orElseGet(() -> new SlotDisplay.Composite(this.results().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList())));
        return List.of(new AlkahestPurifierRecipeDisplay(
                ingredientDisplay(this.ingredient()),
                new SlotDisplay.ItemSlotDisplay(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get()),
                resultDisplay,
                new SlotDisplay.Composite(this.byproducts().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList())),
                new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALKAHEST_PURIFIER.get().asItem()),
                this.alkahestUsage,
                this.processingTime,
                this.experience
        ));
    }

    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case ITEMS -> AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_ITEMS;
            case BLOCKS -> AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_BLOCKS;
        };
    }

    public Optional<ItemStackTemplate> irradiatedResultTemplate() {
        return Arrays.stream(this.ingredient().getItems())
                .filter(stack -> stack.is(AetherIITags.Items.IRRADIATED_ITEM))
                .findFirst()
                .map(stack -> {
                    ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem()).withSuffix("_result");
                    return new ItemStackTemplate(stack.getItem(), 1, DataComponentPatch.builder()
                            .set(DataComponents.ITEM_MODEL, location)
                            .set(DataComponents.ITEM_NAME, Component.translatable(Util.makeDescriptionId("item", location)))
                            .build());
                });
    }

    public static JsonObject toJson(AlkahestPurificationRecipe recipe) {
        JsonObject json = new JsonObject();
        json.addProperty("category", recipe.category.getSerializedName());
        if (!recipe.group.isEmpty()) {
            json.addProperty("group", recipe.group);
        }
        json.add("ingredient", recipe.ingredient.toJson());
        json.add("results", OutputEntry.toJson(recipe.results));
        json.add("byproducts", OutputEntry.toJson(recipe.byproducts));
        json.addProperty("experience", recipe.experience);
        json.addProperty("alkahest_usage", recipe.alkahestUsage);
        json.addProperty("processing_time", recipe.processingTime);
        return json;
    }

    private static SlotDisplay ingredientDisplay(Ingredient ingredient) {
        List<SlotDisplay> displays = Arrays.stream(ingredient.getItems())
                .map(ItemStackTemplate::fromNonEmptyStack)
                .map(SlotDisplay.ItemStackSlotDisplay::new)
                .map(SlotDisplay.class::cast)
                .toList();
        return displays.size() == 1 ? displays.get(0) : new SlotDisplay.Composite(displays);
    }

    private static Ingredient ingredientFromJson(JsonElement element) {
        if (element != null && element.isJsonPrimitive()) {
            String value = element.getAsString();
            if (value.startsWith("#")) {
                return Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation(value.substring(1))));
            }
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(value));
            return Ingredient.of(item == null ? Items.AIR : item);
        }
        return Ingredient.fromJson(element, false);
    }

    private static AlkahestPurifierBookCategory categoryFromJson(JsonObject json) {
        String name = GsonHelper.getAsString(json, "category", AlkahestPurifierBookCategory.ITEMS.getSerializedName()).toLowerCase(Locale.ROOT);
        for (AlkahestPurifierBookCategory category : AlkahestPurifierBookCategory.values()) {
            if (category.getSerializedName().equals(name)) {
                return category;
            }
        }
        return AlkahestPurifierBookCategory.ITEMS;
    }

    public static class Serializer implements RecipeSerializer<AlkahestPurificationRecipe> {
        @Override
        public AlkahestPurificationRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            AlkahestPurifierBookCategory category = categoryFromJson(json);
            Ingredient ingredient = ingredientFromJson(json.get("ingredient"));
            OutputEntry.BaseEntry results = OutputEntry.fromJson(json.get("results"));
            OutputEntry.BaseEntry byproducts = OutputEntry.fromJson(json.get("byproducts"));
            float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int alkahestUsage = GsonHelper.getAsInt(json, "alkahest_usage", 1);
            int processingTime = GsonHelper.getAsInt(json, "processing_time", 200);
            return new AlkahestPurificationRecipe(id, group, category, ingredient, results, byproducts, experience, alkahestUsage, processingTime);
        }

        @Override
        public AlkahestPurificationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AlkahestPurifierBookCategory category = buffer.readEnum(AlkahestPurifierBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            OutputEntry.BaseEntry results = OutputEntry.fromNetwork(buffer);
            OutputEntry.BaseEntry byproducts = OutputEntry.fromNetwork(buffer);
            float experience = buffer.readFloat();
            int alkahestUsage = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AlkahestPurificationRecipe(id, group, category, ingredient, results, byproducts, experience, alkahestUsage, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlkahestPurificationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            recipe.ingredient.toNetwork(buffer);
            OutputEntry.toNetwork(buffer, recipe.results);
            OutputEntry.toNetwork(buffer, recipe.byproducts);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.alkahestUsage);
            buffer.writeVarInt(recipe.processingTime);
        }
    }
}

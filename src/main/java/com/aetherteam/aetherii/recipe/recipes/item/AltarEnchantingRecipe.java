package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import com.aetherteam.aetherii.recipe.book.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInput;
import com.aetherteam.aetherii.recipe.display.RecipeDisplay;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AltarEnchantingRecipe implements Recipe<SingleRecipeInput> {
    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final String group;
    protected final AltarBookCategory category;
    protected final Ingredient ingredient;
    protected final ItemStackTemplate result;
    protected final float experience;
    protected final int fuelCount;
    protected final int processingTime;

    public AltarEnchantingRecipe(ResourceLocation id, String group, AltarBookCategory category, Ingredient ingredient, ItemStackTemplate result, float experience, int fuelCount, int processingTime) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    public Ingredient input() {
        return this.ingredient;
    }

    public ItemStackTemplate result() {
        return this.result;
    }

    public float experience() {
        return this.experience;
    }

    public int fuelCount() {
        return this.fuelCount;
    }

    public int processingTime() {
        return this.processingTime;
    }

    public String group() {
        return this.group;
    }

    public AltarBookCategory category() {
        return this.category;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        if (this.isHealingStoneRechargingRecipe()) {
            return input.item().is(AetherIIItems.HEALING_STONE.get())
                    && AetherIIDataComponents.getOrDefault(input.item(), AetherIIDataComponents.HEALING_STONE_CHARGES, 0) == 5 - this.fuelCount;
        }
        return this.ingredient.test(input.item());
    }

    private boolean isHealingStoneRechargingRecipe() {
        return this.result.item() == AetherIIItems.HEALING_STONE.get()
                && AetherIIDataComponents.getOrDefault(this.result.create(), AetherIIDataComponents.HEALING_STONE_CHARGES, 0) == 5
                && this.fuelCount >= 1
                && this.fuelCount <= 5
                && this.group.equals("healing_stone");
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, RegistryAccess registryAccess) {
        return this.result.create();
    }

    public ItemStack assemble(SingleRecipeInput input) {
        return this.result.create();
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
        return this.result.create();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeType<AltarEnchantingRecipe> getType() {
        return AetherIIRecipeTypes.ALTAR_ENCHANTING.get();
    }

    @Override
    public RecipeSerializer<AltarEnchantingRecipe> getSerializer() {
        return SERIALIZER;
    }

    public List<RecipeDisplay> display() {
        return List.of(new AltarRecipeDisplay(
                ingredientDisplay(this.input()),
                new SlotDisplay.TagSlotDisplay(AetherIITags.Items.ALTAR_FUEL),
                new SlotDisplay.ItemStackSlotDisplay(this.result()),
                new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALTAR.get().asItem()),
                this.fuelCount,
                this.processingTime,
                this.experience
        ));
    }

    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> AetherIIRecipeBookCategories.ALTAR_BLOCKS;
            case FOOD -> AetherIIRecipeBookCategories.ALTAR_FOOD;
            case REPAIRING -> AetherIIRecipeBookCategories.ALTAR_REPAIRING;
            case MISC -> AetherIIRecipeBookCategories.ALTAR_MISC;
        };
    }

    public static JsonObject toJson(AltarEnchantingRecipe recipe) {
        JsonObject json = new JsonObject();
        json.addProperty("category", recipe.category.getSerializedName());
        if (!recipe.group.isEmpty()) {
            json.addProperty("group", recipe.group);
        }
        json.add("ingredient", recipe.ingredient.toJson());
        json.add("result", OutputEntry.templateToJson(recipe.result));
        json.addProperty("experience", recipe.experience);
        json.addProperty("fuel_count", recipe.fuelCount);
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

    private static AltarBookCategory categoryFromJson(JsonObject json) {
        String name = GsonHelper.getAsString(json, "category", AltarBookCategory.MISC.getSerializedName()).toLowerCase(Locale.ROOT);
        for (AltarBookCategory category : AltarBookCategory.values()) {
            if (category.getSerializedName().equals(name)) {
                return category;
            }
        }
        return AltarBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<AltarEnchantingRecipe> {
        @Override
        public AltarEnchantingRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            AltarBookCategory category = categoryFromJson(json);
            Ingredient ingredient = ingredientFromJson(json.get("ingredient"));
            ItemStackTemplate result = OutputEntry.templateFromJson(GsonHelper.getAsJsonObject(json, "result"));
            float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int fuelCount = GsonHelper.getAsInt(json, "fuel_count", 1);
            int processingTime = GsonHelper.getAsInt(json, "processing_time", 200);
            return new AltarEnchantingRecipe(id, group, category, ingredient, result, experience, fuelCount, processingTime);
        }

        @Override
        public AltarEnchantingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AltarBookCategory category = buffer.readEnum(AltarBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStackTemplate result = ItemStackTemplate.fromNonEmptyStack(buffer.readItem());
            float experience = buffer.readFloat();
            int fuelCount = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AltarEnchantingRecipe(id, group, category, ingredient, result, experience, fuelCount, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AltarEnchantingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result.create());
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.fuelCount);
            buffer.writeVarInt(recipe.processingTime);
        }
    }
}

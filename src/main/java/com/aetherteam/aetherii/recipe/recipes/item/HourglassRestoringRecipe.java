package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.slot.AmberFuel;
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
import com.aetherteam.aetherii.recipe.display.RecipeDisplay;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class HourglassRestoringRecipe implements Recipe<SingleRecipeInputWithRandom> {
    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final String group;
    protected final AmberHourglassBookCategory category;
    private final Ingredient ingredient;
    private final HourglassOutput results;
    protected final float experience;
    protected final int processingTime;

    public HourglassRestoringRecipe(ResourceLocation id, String group, AmberHourglassBookCategory category, Ingredient ingredient, HourglassOutput results, float experience, int processingTime) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.results = results;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    public String group() {
        return this.group;
    }

    public AmberHourglassBookCategory category() {
        return this.category;
    }

    public Ingredient ingredient() {
        return this.ingredient;
    }

    public HourglassOutput results() {
        return this.results;
    }

    public float experience() {
        return this.experience;
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
        return this.results.output1().process(input.randomSource());
    }

    public ItemStack assemble(SingleRecipeInputWithRandom input) {
        return this.results.output1().process(input.randomSource());
    }

    public List<ItemStack> assembleOutputs(SingleRecipeInputWithRandom input) {
        List<ItemStack> outputs = new ArrayList<>();
        outputs.add(0, this.results.output1().process(input.randomSource()));
        outputs.add(1, this.results.output2().process(input.randomSource()));
        outputs.add(2, this.results.output3().process(input.randomSource()));
        return outputs;
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
        return firstDisplayStack(this.results);
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.AMBER_HOURGLASS.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeType<HourglassRestoringRecipe> getType() {
        return AetherIIRecipeTypes.HOURGLASS_RESTORING.get();
    }

    @Override
    public RecipeSerializer<HourglassRestoringRecipe> getSerializer() {
        return SERIALIZER;
    }

    public List<RecipeDisplay> display() {
        List<SlotDisplay> results1 = outputDisplay(this.results().output1());
        List<SlotDisplay> results2 = outputDisplay(this.results().output2());
        List<SlotDisplay> results3 = outputDisplay(this.results().output3());
        return List.of(new AmberHourglassRecipeDisplay(
                ingredientDisplay(this.ingredient()),
                AmberFuel.INSTANCE,
                new SlotDisplay.Composite(results1),
                new SlotDisplay.Composite(results2),
                new SlotDisplay.Composite(results3),
                new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.AMBER_HOURGLASS.get().asItem()),
                this.processingTime,
                this.experience
        ));
    }

    private static ItemStack firstDisplayStack(HourglassOutput output) {
        ItemStack stack = firstDisplayStack(output.output1());
        if (!stack.isEmpty()) {
            return stack;
        }
        stack = firstDisplayStack(output.output2());
        if (!stack.isEmpty()) {
            return stack;
        }
        return firstDisplayStack(output.output3());
    }

    private static ItemStack firstDisplayStack(OutputEntry.BaseEntry output) {
        return output.list().stream()
                .map(ItemStackTemplate::create)
                .filter(stack -> !stack.isEmpty())
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    private static List<SlotDisplay> outputDisplay(OutputEntry.BaseEntry output) {
        return output.list().stream()
                .filter(template -> !template.create().isEmpty())
                .map(ItemStackTemplate::item)
                .distinct()
                .map(SlotDisplay.ItemSlotDisplay::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case RESTORATION -> AetherIIRecipeBookCategories.AMBER_HOURGLASS_RESTORATION;
            case UNCRAFTING -> AetherIIRecipeBookCategories.AMBER_HOURGLASS_UNCRAFTING;
        };
    }

    public static JsonObject toJson(HourglassRestoringRecipe recipe) {
        JsonObject json = new JsonObject();
        json.addProperty("category", recipe.category.getSerializedName());
        if (!recipe.group.isEmpty()) {
            json.addProperty("group", recipe.group);
        }
        json.add("ingredient", recipe.ingredient.toJson());
        json.add("results", recipe.results.toJson());
        json.addProperty("experience", recipe.experience);
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

    private static AmberHourglassBookCategory categoryFromJson(JsonObject json) {
        String name = GsonHelper.getAsString(json, "category", AmberHourglassBookCategory.RESTORATION.getSerializedName()).toLowerCase(Locale.ROOT);
        for (AmberHourglassBookCategory category : AmberHourglassBookCategory.values()) {
            if (category.getSerializedName().equals(name)) {
                return category;
            }
        }
        return AmberHourglassBookCategory.RESTORATION;
    }

    public record HourglassOutput(OutputEntry.BaseEntry output1, OutputEntry.BaseEntry output2, OutputEntry.BaseEntry output3) {
        public static HourglassOutput fromJson(JsonObject json) {
            return new HourglassOutput(
                    OutputEntry.fromJson(json.get("output_1")),
                    OutputEntry.fromJson(json.get("output_2")),
                    OutputEntry.fromJson(json.get("output_3"))
            );
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.add("output_1", OutputEntry.toJson(this.output1));
            json.add("output_2", OutputEntry.toJson(this.output2));
            json.add("output_3", OutputEntry.toJson(this.output3));
            return json;
        }

        public static HourglassOutput fromNetwork(FriendlyByteBuf buffer) {
            return new HourglassOutput(OutputEntry.fromNetwork(buffer), OutputEntry.fromNetwork(buffer), OutputEntry.fromNetwork(buffer));
        }

        public static void toNetwork(FriendlyByteBuf buffer, HourglassOutput output) {
            OutputEntry.toNetwork(buffer, output.output1);
            OutputEntry.toNetwork(buffer, output.output2);
            OutputEntry.toNetwork(buffer, output.output3);
        }
    }

    public static class Serializer implements RecipeSerializer<HourglassRestoringRecipe> {
        @Override
        public HourglassRestoringRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            AmberHourglassBookCategory category = categoryFromJson(json);
            Ingredient ingredient = ingredientFromJson(json.get("ingredient"));
            HourglassOutput results = HourglassOutput.fromJson(GsonHelper.getAsJsonObject(json, "results"));
            float experience = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int processingTime = GsonHelper.getAsInt(json, "processing_time", 200);
            return new HourglassRestoringRecipe(id, group, category, ingredient, results, experience, processingTime);
        }

        @Override
        public HourglassRestoringRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AmberHourglassBookCategory category = buffer.readEnum(AmberHourglassBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            HourglassOutput results = HourglassOutput.fromNetwork(buffer);
            float experience = buffer.readFloat();
            int processingTime = buffer.readVarInt();
            return new HourglassRestoringRecipe(id, group, category, ingredient, results, experience, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, HourglassRestoringRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            recipe.ingredient.toNetwork(buffer);
            HourglassOutput.toNetwork(buffer, recipe.results);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.processingTime);
        }
    }
}

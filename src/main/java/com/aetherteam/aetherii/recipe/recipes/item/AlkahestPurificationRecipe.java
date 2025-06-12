package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlkahestPurificationRecipe implements Recipe<SingleRecipeInputWithRandom> {
    protected final String group;
    protected final AlkahestPurifierBookCategory category;
    protected final Ingredient ingredient;
    protected final SimpleWeightedRandomList<ItemStack> results;
    protected final ItemStack byproduct;
    protected final float experience;
    protected final int alkahestUsage;
    protected final int processingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public AlkahestPurificationRecipe(String group, AlkahestPurifierBookCategory category, Ingredient ingredient, SimpleWeightedRandomList<ItemStack> results, ItemStack byproduct, float experience, int alkahestUsage, int processingTime) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.results = results;
        this.byproduct = byproduct;
        this.experience = experience;
        this.alkahestUsage = alkahestUsage;
        this.processingTime = processingTime;
    }

    @Override
    public String group() {
        return this.group;
    }

    public Ingredient ingredient() {
        return this.ingredient;
    }

    public SimpleWeightedRandomList<ItemStack> results() {
        return this.results;
    }

    public ItemStack byproduct() {
        return this.byproduct;
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

    public AlkahestPurifierBookCategory category() {
        return this.category;
    }

    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredient);
        }
        return this.placementInfo;
    }

    @Override
    public boolean matches(SingleRecipeInputWithRandom singleRecipeInput, Level level) {
        return this.ingredient().test(singleRecipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInputWithRandom singleRecipeInput, HolderLookup.Provider provider) {
        return this.results().getRandomValue(singleRecipeInput.randomSource()).orElse(ItemStack.EMPTY);
    }

    @Override
    public RecipeType<AlkahestPurificationRecipe> getType() {
        return AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get();
    }

    @Override
    public RecipeSerializer<AlkahestPurificationRecipe> getSerializer() {
        return AetherIIRecipeSerializers.ALKAHEST_PURIFICATION.get();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new AltarRecipeDisplay(
                this.ingredient().display(),
                new SlotDisplay.ItemSlotDisplay(AetherIIItems.ARKENIUM_ACID_CANISTER),
                new SlotDisplay.Composite(this.results().unwrap().stream().map((wrapper) -> new SlotDisplay.ItemStackSlotDisplay(wrapper.data())).collect(Collectors.toUnmodifiableList())),
                new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALKAHEST_PURIFIER.asItem()),
                this.alkahestUsage,
                this.processingTime,
                this.experience
        ));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case ITEMS -> AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_ITEMS.get();
            case BLOCKS -> AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_BLOCKS.get();
        };
    }

    public static class Serializer implements RecipeSerializer<AlkahestPurificationRecipe> {
        private final MapCodec<AlkahestPurificationRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, AlkahestPurificationRecipe> streamCodec;

        public Serializer() {
            this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(AlkahestPurificationRecipe::group),
                    AlkahestPurifierBookCategory.CODEC.fieldOf("category").orElse(AlkahestPurifierBookCategory.ITEMS).forGetter(AlkahestPurificationRecipe::category),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(AlkahestPurificationRecipe::ingredient),
                    SimpleWeightedRandomList.wrappedCodec(ItemStack.CODEC).fieldOf("results").forGetter(AlkahestPurificationRecipe::results),
                    ItemStack.CODEC.fieldOf("byproduct").forGetter(AlkahestPurificationRecipe::byproduct),
                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AlkahestPurificationRecipe::experience),
                    Codec.INT.fieldOf("alkahest_usage").orElse(1).forGetter(AlkahestPurificationRecipe::alkahestUsage),
                    Codec.INT.fieldOf("processing_time").orElse(200).forGetter(AlkahestPurificationRecipe::processingTime)
            ).apply(instance, AlkahestPurificationRecipe::new));
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Override
        public MapCodec<AlkahestPurificationRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlkahestPurificationRecipe> streamCodec() {
            return this.streamCodec;
        }

        public AlkahestPurificationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AlkahestPurifierBookCategory category = buffer.readEnum(AlkahestPurifierBookCategory.class);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Map<ItemStack, Integer> map = buffer.readMap((innerBuf) -> ItemStack.STREAM_CODEC.decode(buffer), FriendlyByteBuf::readInt);
            SimpleWeightedRandomList.Builder<ItemStack> builder = SimpleWeightedRandomList.builder();
            for (Map.Entry<ItemStack, Integer> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            SimpleWeightedRandomList<ItemStack> results = builder.build();
            ItemStack byproduct = ItemStack.STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int alkahestUsage = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AlkahestPurificationRecipe(group, category, ingredient, results, byproduct, experience, alkahestUsage, processingTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, AlkahestPurificationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            Map<ItemStack, Integer> map = recipe.results.unwrap().stream().collect(Collectors.toMap(WeightedEntry.Wrapper::data, (e) -> e.getWeight().asInt()));
            buffer.writeMap(map, (innerBuf, itemStack) -> ItemStack.STREAM_CODEC.encode(buffer, itemStack), FriendlyByteBuf::writeInt);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.byproduct());
            buffer.writeFloat(recipe.experience());
            buffer.writeVarInt(recipe.alkahestUsage());
            buffer.writeVarInt(recipe.processingTime());
        }
    }
}

package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class AlkahestPurificationRecipe implements Recipe<SingleRecipeInputWithRandom> {
    protected final String group;
    protected final AlkahestPurifierBookCategory category;
    protected final Ingredient ingredient;
    protected final OutputEntry.BaseEntry results;
    protected final OutputEntry.BaseEntry byproducts;
    protected final float experience;
    protected final int alkahestUsage;
    protected final int processingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public AlkahestPurificationRecipe(String group, AlkahestPurifierBookCategory category, Ingredient ingredient, OutputEntry.BaseEntry results, OutputEntry.BaseEntry byproducts, float experience, int alkahestUsage, int processingTime) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.results = results;
        this.byproducts = byproducts;
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
        return this.results().process(singleRecipeInput.randomSource());
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
        SlotDisplay resultDisplay = new SlotDisplay.Composite(this.results().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList()));
        HolderSet<Item> ingredients = this.ingredient().getValues();
        Holder<Item> item = ingredients.get(0);
        if (item.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            ResourceLocation location = item.getKey().location().withSuffix("_result");
            resultDisplay = new SlotDisplay.ItemStackSlotDisplay(new ItemStack(item, 1, DataComponentPatch.builder()
                    .set(DataComponents.ITEM_MODEL, location)
                    .set(DataComponents.ITEM_NAME, Component.translatable(Util.makeDescriptionId("item", location)))
                    .build()
            ));
        }
        return List.of(new AlkahestPurifierRecipeDisplay(
                this.ingredient().display(),
                new SlotDisplay.ItemSlotDisplay(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER),
                resultDisplay,
                new SlotDisplay.Composite(this.byproducts().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList())),
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
                    OutputEntry.ENTRY_CODEC.fieldOf("results").forGetter(AlkahestPurificationRecipe::results),
                    OutputEntry.ENTRY_CODEC.fieldOf("byproducts").forGetter(AlkahestPurificationRecipe::byproducts),
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
            OutputEntry.BaseEntry results = OutputEntry.ENTRY_STREAM_CODEC.decode(buffer);
            OutputEntry.BaseEntry byproducts = OutputEntry.ENTRY_STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int alkahestUsage = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AlkahestPurificationRecipe(group, category, ingredient, results, byproducts, experience, alkahestUsage, processingTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, AlkahestPurificationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            OutputEntry.ENTRY_STREAM_CODEC.encode(buffer, recipe.results);
            OutputEntry.ENTRY_STREAM_CODEC.encode(buffer, recipe.byproducts);
            buffer.writeFloat(recipe.experience());
            buffer.writeVarInt(recipe.alkahestUsage());
            buffer.writeVarInt(recipe.processingTime());
        }
    }

}

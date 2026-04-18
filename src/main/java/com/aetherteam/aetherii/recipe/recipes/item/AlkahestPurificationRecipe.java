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
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class AlkahestPurificationRecipe implements Recipe<SingleRecipeInputWithRandom> {
    public static final MapCodec<AlkahestPurificationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
            AlkahestPurifierBookInfo.MAP_CODEC.forGetter(r -> r.bookInfo),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(AlkahestPurificationRecipe::ingredient),
            OutputEntry.ENTRY_CODEC.fieldOf("results").forGetter(AlkahestPurificationRecipe::results),
            OutputEntry.ENTRY_CODEC.fieldOf("byproducts").forGetter(AlkahestPurificationRecipe::byproducts),
            Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AlkahestPurificationRecipe::experience),
            Codec.INT.fieldOf("alkahest_usage").orElse(1).forGetter(AlkahestPurificationRecipe::alkahestUsage),
            Codec.INT.fieldOf("processing_time").orElse(200).forGetter(AlkahestPurificationRecipe::processingTime)
    ).apply(i, AlkahestPurificationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AlkahestPurificationRecipe> STREAM_CODEC = StreamCodec.composite(
            CommonInfo.STREAM_CODEC, r -> r.commonInfo,
            AlkahestPurifierBookInfo.STREAM_CODEC, r -> r.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC, AlkahestPurificationRecipe::ingredient,
            OutputEntry.ENTRY_STREAM_CODEC, AlkahestPurificationRecipe::results,
            OutputEntry.ENTRY_STREAM_CODEC, AlkahestPurificationRecipe::byproducts,
            ByteBufCodecs.FLOAT, AlkahestPurificationRecipe::experience,
            ByteBufCodecs.INT, AlkahestPurificationRecipe::alkahestUsage,
            ByteBufCodecs.INT, AlkahestPurificationRecipe::processingTime,
            AlkahestPurificationRecipe::new
    );
    public static final RecipeSerializer<AlkahestPurificationRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    protected final Recipe.CommonInfo commonInfo;
    protected final AlkahestPurifierBookInfo bookInfo;
    protected final Ingredient ingredient;
    protected final OutputEntry.BaseEntry results;
    protected final OutputEntry.BaseEntry byproducts;
    protected final float experience;
    protected final int alkahestUsage;
    protected final int processingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public AlkahestPurificationRecipe(Recipe.CommonInfo commonInfo, AlkahestPurifierBookInfo bookInfo, Ingredient ingredient, OutputEntry.BaseEntry results, OutputEntry.BaseEntry byproducts, float experience, int alkahestUsage, int processingTime) {
        this.commonInfo = commonInfo;
        this.bookInfo = bookInfo;
        this.ingredient = ingredient;
        this.results = results;
        this.byproducts = byproducts;
        this.experience = experience;
        this.alkahestUsage = alkahestUsage;
        this.processingTime = processingTime;
    }

    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group() {
        return this.bookInfo.group;
    }

    public AlkahestPurifierBookCategory category() {
        return this.bookInfo.category;
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
    public ItemStack assemble(SingleRecipeInputWithRandom singleRecipeInput) {
        return this.results().process(singleRecipeInput.randomSource());
    }

    @Override
    public RecipeType<AlkahestPurificationRecipe> getType() {
        return AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get();
    }

    @Override
    public RecipeSerializer<AlkahestPurificationRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<RecipeDisplay> display() {
        SlotDisplay resultDisplay = new SlotDisplay.Composite(this.results().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList()));
        HolderSet<Item> ingredients = this.ingredient().getValues();
        Holder<Item> item = ingredients.get(0);
        if (item.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            Identifier location = item.getKey().identifier().withSuffix("_result");
            resultDisplay = new SlotDisplay.ItemStackSlotDisplay(new ItemStackTemplate(item, 1, DataComponentPatch.builder()
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

    public record AlkahestPurifierBookInfo(AlkahestPurifierBookCategory category, String group) implements Recipe.BookInfo<AlkahestPurifierBookCategory> {
        public static final MapCodec<AlkahestPurifierBookInfo> MAP_CODEC = BookInfo.mapCodec(AlkahestPurifierBookCategory.CODEC, AlkahestPurifierBookCategory.ITEMS, AlkahestPurifierBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, AlkahestPurifierBookInfo> STREAM_CODEC = BookInfo.streamCodec(AlkahestPurifierBookCategory.STREAM_CODEC, AlkahestPurifierBookInfo::new);
    }
}

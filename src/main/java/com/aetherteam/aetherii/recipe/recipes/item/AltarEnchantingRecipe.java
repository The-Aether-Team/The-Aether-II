package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public class AltarEnchantingRecipe extends SingleItemRecipe {
    public static final MapCodec<AltarEnchantingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
            AltarBookInfo.MAP_CODEC.forGetter(r -> r.bookInfo),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(AltarEnchantingRecipe::input),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(AltarEnchantingRecipe::result),
            Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AltarEnchantingRecipe::experience),
            Codec.INT.fieldOf("fuel_count").orElse(1).forGetter(AltarEnchantingRecipe::fuelCount),
            Codec.INT.fieldOf("processing_time").orElse(200).forGetter(AltarEnchantingRecipe::processingTime)
    ).apply(i, AltarEnchantingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AltarEnchantingRecipe> STREAM_CODEC = StreamCodec.composite(
            CommonInfo.STREAM_CODEC, r -> r.commonInfo,
            AltarBookInfo.STREAM_CODEC, r -> r.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC, AltarEnchantingRecipe::input,
            ItemStackTemplate.STREAM_CODEC, AltarEnchantingRecipe::result,
            ByteBufCodecs.FLOAT, AltarEnchantingRecipe::experience,
            ByteBufCodecs.INT, AltarEnchantingRecipe::fuelCount,
            ByteBufCodecs.INT, AltarEnchantingRecipe::processingTime,
            AltarEnchantingRecipe::new
    );
    public static final RecipeSerializer<AltarEnchantingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    protected final AltarBookInfo bookInfo;
    protected final float experience;
    protected final int fuelCount;
    protected final int processingTime;

    public AltarEnchantingRecipe(Recipe.CommonInfo commonInfo, AltarBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int fuelCount, int processingTime) {
        super(commonInfo, ingredient, result);
        this.bookInfo = bookInfo;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
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

    @Override
    public String group() {
        return this.bookInfo.group;
    }

    public AltarBookCategory category() {
        return this.bookInfo.category;
    }

    //making public
    @Override
    public ItemStackTemplate result() {
        return super.result();
    }

    @Override
    public RecipeType<AltarEnchantingRecipe> getType() {
        return AetherIIRecipeTypes.ALTAR_ENCHANTING.get();
    }

    @Override
    public RecipeSerializer<AltarEnchantingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<RecipeDisplay> display() {
        if (this.input().getCustomIngredient() == null && this.input().getValues().contains(this.result().typeHolder())) {
//            ItemStackTemplate input = this.result().apply(DataComponentPatch.builder().set(DataComponents.DAMAGE, this.result().create().getMaxDamage()).build()); //TODO
            ItemStackTemplate input = this.result();
            return List.of(new AltarRecipeDisplay(
                    new SlotDisplay.ItemStackSlotDisplay(input),
                    new SlotDisplay.TagSlotDisplay(AetherIITags.Items.ALTAR_FUEL),
                    new SlotDisplay.ItemStackSlotDisplay(this.result()),
                    new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALTAR.asItem()),
                    this.fuelCount,
                    this.processingTime,
                    this.experience
            ));
        } else {
            return List.of(new AltarRecipeDisplay(
                    this.input().display(),
                    new SlotDisplay.TagSlotDisplay(AetherIITags.Items.ALTAR_FUEL),
                    new SlotDisplay.ItemStackSlotDisplay(this.result()),
                    new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALTAR.asItem()),
                    this.fuelCount,
                    this.processingTime,
                    this.experience
            ));
        }
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> AetherIIRecipeBookCategories.ALTAR_BLOCKS.get();
            case FOOD -> AetherIIRecipeBookCategories.ALTAR_FOOD.get();
            case REPAIRING -> AetherIIRecipeBookCategories.ALTAR_REPAIRING.get();
            case MISC -> AetherIIRecipeBookCategories.ALTAR_MISC.get();
        };
    }

    public record AltarBookInfo(AltarBookCategory category, String group) implements Recipe.BookInfo<AltarBookCategory> {
        public static final MapCodec<AltarBookInfo> MAP_CODEC = BookInfo.mapCodec(AltarBookCategory.CODEC, AltarBookCategory.MISC, AltarBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, AltarBookInfo> STREAM_CODEC = BookInfo.streamCodec(AltarBookCategory.STREAM_CODEC, AltarBookInfo::new);
    }
}

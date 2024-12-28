package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.FurnaceRecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class AltarEnchantingRecipe extends SingleItemRecipe {
    protected final AltarBookCategory category;
    protected final float experience;
    protected final int fuelCount;
    protected final int processingTime;

    public AltarEnchantingRecipe(String group, AltarBookCategory category, Ingredient ingredient, ItemStack result, float experience, int fuelCount, int processingTime) {
        super(group, ingredient, result);
        this.category = category;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getFuelCount() {
        return this.fuelCount;
    }

    public int getProcessingTime() {
        return this.processingTime;
    }

    public AltarBookCategory category() {
        return this.category;
    }

    @Override
    public RecipeType<AltarEnchantingRecipe> getType() {
        return AetherIIRecipeTypes.ALTAR_ENCHANTING.get();
    }

    @Override
    public RecipeSerializer<AltarEnchantingRecipe> getSerializer() {
        return AetherIIRecipeSerializers.ALTAR_ENCHANTING.get();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new AltarRecipeDisplay(this.input().display(), new SlotDisplay.TagSlotDisplay(AetherIITags.Items.ALTAR_FUEL), new SlotDisplay.ItemStackSlotDisplay(this.result()), new SlotDisplay.ItemSlotDisplay(AetherIIBlocks.ALTAR.asItem()), this.fuelCount, this.processingTime, this.experience));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> AetherIIRecipeBookCategories.ALTAR_BLOCKS.get();
            case FOOD -> AetherIIRecipeBookCategories.ALTAR_FOOD.get();
            case REPAIRING -> AetherIIRecipeBookCategories.ALTAR_REPAIRING.get();
            case MISC -> AetherIIRecipeBookCategories.ALTAR_MISC.get();
            default -> throw new MatchException(null, null);
        };
    }

    public static class Serializer implements RecipeSerializer<AltarEnchantingRecipe> {
        private final MapCodec<AltarEnchantingRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, AltarEnchantingRecipe> streamCodec;

        public Serializer() {
            this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group()),
                    AltarBookCategory.CODEC.fieldOf("category").orElse(AltarBookCategory.MISC).forGetter(p_300828_ -> p_300828_.category),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input()),
                    ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result()),
                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
                    Codec.INT.fieldOf("fuel_count").orElse(1).forGetter(recipe -> recipe.fuelCount),
                    Codec.INT.fieldOf("processing_time").orElse(200).forGetter(recipe -> recipe.processingTime)
            ).apply(instance, AltarEnchantingRecipe::new));
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Override
        public MapCodec<AltarEnchantingRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AltarEnchantingRecipe> streamCodec() {
            return this.streamCodec;
        }

        public AltarEnchantingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AltarBookCategory category = buffer.readEnum(AltarBookCategory.class);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int inputCount = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AltarEnchantingRecipe(group, category, ingredient, result, experience, inputCount, processingTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, AltarEnchantingRecipe recipe) {
            buffer.writeUtf(recipe.group());
            buffer.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input());
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.fuelCount);
            buffer.writeVarInt(recipe.processingTime);
        }
    }
}

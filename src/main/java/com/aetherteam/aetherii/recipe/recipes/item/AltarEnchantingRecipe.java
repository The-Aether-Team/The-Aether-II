package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.builder.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

public class AltarEnchantingRecipe implements Recipe<Container> {
    protected final String group;
    protected final AltarBookCategory category;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final float experience;
    protected final int fuelCount;
    protected final int processingTime;

    public AltarEnchantingRecipe(String group, AltarBookCategory category, Ingredient ingredient, ItemStack result, float experience, int fuelCount, int processingTime) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        return list;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public String getGroup() {
        return this.group;
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
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.ALTAR);
    }

    @Override
    public RecipeType<?> getType() {
        return AetherIIRecipeTypes.ALTAR_ENCHANTING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherIIRecipeSerializers.ALTAR_ENCHANTING.get();
    }

    public static class Serializer implements RecipeSerializer<AltarEnchantingRecipe> {
        private final Codec<AltarEnchantingRecipe> codec;

        public Serializer() {
            this.codec = RecordCodecBuilder.create(instance -> instance.group(
                    ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
                    AltarBookCategory.CODEC.fieldOf("category").orElse(AltarBookCategory.MISC).forGetter(p_300828_ -> p_300828_.category),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                    CraftingHelper.smeltingResultCodec().fieldOf("result").forGetter(recipe -> recipe.result),
                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
                    Codec.INT.fieldOf("fuel_count").orElse(1).forGetter(recipe -> recipe.fuelCount),
                    Codec.INT.fieldOf("processing_time").orElse(200).forGetter(recipe -> recipe.processingTime)
            ).apply(instance, AltarEnchantingRecipe::new));
        }

        @Override
        public Codec<AltarEnchantingRecipe> codec() {
            return this.codec;
        }

        @Override
        public AltarEnchantingRecipe fromNetwork(FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AltarBookCategory category = buffer.readEnum(AltarBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            float experience = buffer.readFloat();
            int inputCount = buffer.readVarInt();
            int processingTime = buffer.readVarInt();
            return new AltarEnchantingRecipe(group, category, ingredient, result, experience, inputCount, processingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AltarEnchantingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category());
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.fuelCount);
            buffer.writeVarInt(recipe.processingTime);
        }
    }
}

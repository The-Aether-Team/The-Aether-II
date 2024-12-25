package com.aetherteam.aetherii.recipe.recipes.item;

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
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.stream.Collectors;

public class IrradiationCleansingRecipe implements Recipe<SingleRecipeInputWithRandom> {
    protected final String group;
    protected final Ingredient ingredient;
    protected final SimpleWeightedRandomList<ItemStack> results;

    public IrradiationCleansingRecipe(String group, Ingredient ingredient, SimpleWeightedRandomList<ItemStack> results) {
        this.group = group;
        this.ingredient = ingredient;
        this.results = results;
    }

    @Override
    public boolean matches(SingleRecipeInputWithRandom singleRecipeInput, Level level) {
        return this.ingredient.test(singleRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInputWithRandom singleRecipeInput, HolderLookup.Provider provider) {
        return this.results.getRandomValue(singleRecipeInput.randomSource()).orElse(ItemStack.EMPTY);
    }

    @Override
    public RecipeType<IrradiationCleansingRecipe> getType() {
        return AetherIIRecipeTypes.IRRADIATION_CLEANSING.get();
    }

    @Override
    public RecipeSerializer<IrradiationCleansingRecipe> getSerializer() {
        return AetherIIRecipeSerializers.IRRADIATION_CLEANSING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<IrradiationCleansingRecipe> {
        private final MapCodec<IrradiationCleansingRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, IrradiationCleansingRecipe> streamCodec;

        public Serializer() {
            this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                    SimpleWeightedRandomList.wrappedCodec(ItemStack.CODEC).fieldOf("results").forGetter(recipe -> recipe.results)
            ).apply(instance, IrradiationCleansingRecipe::new));
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Override
        public MapCodec<IrradiationCleansingRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IrradiationCleansingRecipe> streamCodec() {
            return this.streamCodec;
        }

        public IrradiationCleansingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Map<ItemStack, Integer> map = buffer.readMap((innerBuf) -> ItemStack.STREAM_CODEC.decode(buffer), FriendlyByteBuf::readInt);
            SimpleWeightedRandomList.Builder<ItemStack> builder = SimpleWeightedRandomList.builder();
            for (Map.Entry<ItemStack, Integer> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            SimpleWeightedRandomList<ItemStack> results = builder.build();
            return new IrradiationCleansingRecipe(group, ingredient, results);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, IrradiationCleansingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            Map<ItemStack, Integer> map = recipe.results.unwrap().stream().collect(Collectors.toMap(WeightedEntry.Wrapper::data, (e) -> e.getWeight().asInt()));
            buffer.writeMap(map, (innerBuf, itemStack) -> ItemStack.STREAM_CODEC.encode(buffer, itemStack), FriendlyByteBuf::writeInt);
        }
    }
}

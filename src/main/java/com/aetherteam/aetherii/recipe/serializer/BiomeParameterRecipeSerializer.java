package com.aetherteam.aetherii.recipe.serializer;

import com.aetherteam.aetherii.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public BiomeParameterRecipeSerializer(Factory<T> factory, AbstractBlockStateRecipe.Factory<T> superFactory) {
        super(superFactory);
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(inst -> inst.group(
                BlockStateRecipeUtil.KEY_CODEC.optionalFieldOf("biome").forGetter(AbstractBiomeParameterRecipe::getBiome),
                BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(AbstractBiomeParameterRecipe::getIngredient),
                BlockPropertyPair.CODEC.fieldOf("result").forGetter(AbstractBiomeParameterRecipe::getResult),
                ResourceLocation.CODEC.optionalFieldOf("mcfunction").forGetter(AbstractBiomeParameterRecipe::getFunctionId)
        ).apply(inst, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome = buffer.readOptional((buf) -> buf.readEither((buf1) -> ResourceKey.create(Registries.BIOME, buf1.readResourceLocation()), (buf1) -> TagKey.create(Registries.BIOME, buf1.readResourceLocation())));
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        Optional<ResourceLocation> function = buffer.readOptional(FriendlyByteBuf::readResourceLocation);
        return this.factory.create(biome, ingredient, result, function);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeOptional(recipe.getBiome(), (buf, either) -> buf.writeEither(either, (buf1, left) -> buf1.writeResourceLocation(left.location()), (buf1, right) -> buf1.writeResourceLocation(right.location())));
        super.toNetwork(buffer, recipe);
    }

    public interface Factory<T extends AbstractBiomeParameterRecipe> {
        T create(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function);
    }
}

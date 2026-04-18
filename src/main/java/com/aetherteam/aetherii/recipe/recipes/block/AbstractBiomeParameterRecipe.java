package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractBiomeParameterRecipe extends AbstractBlockStateRecipe {
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;

    public AbstractBiomeParameterRecipe(RecipeType<? extends AbstractBiomeParameterRecipe> type, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        super(type, ingredient, result, function);
        this.biome = biome;
    }

    /**
     * Tests if the given object matches with the recipe.<br><br>
     * Checks if there is a {@link Biome} {@link ResourceKey} or a {@link Biome} {@link TagKey} it will test one of those alongside {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     * Otherwise, it will only test {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     *
     * @param level The {@link Level} the recipe is performed in.
     * @param pos   The {@link BlockPos} the recipe is performed at.
     * @param state The {@link BlockState} being used that is being checked.
     * @return Whether the given {@link BlockState} matches.
     */
    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.biome.isPresent() && this.biome.get().left().isPresent()) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biome.get().left().get());
        } else if (this.biome.isPresent() && this.biome.get().right().isPresent()) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biome.get().right().get());
        } else {
            return super.matches(level, pos, state);
        }
    }

    public Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> getBiome() {
        return this.biome;
    }

    public static <T extends AbstractBiomeParameterRecipe> MapCodec<T> biomeCodec(AbstractBiomeParameterRecipe.Factory<T> factory) {
        return RecordCodecBuilder.mapCodec((i) -> {
            Products.P4<RecordCodecBuilder.Mu<T>, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>, BlockStateIngredient, BlockPropertyPair, Optional<Identifier>> var10000 = i.group(
                    BlockStateRecipeUtil.KEY_CODEC.optionalFieldOf("biome").forGetter(AbstractBiomeParameterRecipe::getBiome),
                    BlockStateIngredient.CODEC.fieldOf("ingredient").forGetter(AbstractBiomeParameterRecipe::getIngredient),
                    BlockPropertyPair.CODEC.fieldOf("result").forGetter(AbstractBiomeParameterRecipe::getResult),
                    Identifier.CODEC.optionalFieldOf("mcfunction").forGetter(AbstractBiomeParameterRecipe::getFunctionId)
            );
            Objects.requireNonNull(factory);
            return var10000.apply(i, factory::create);
        });
    }

    public static <T extends AbstractBiomeParameterRecipe> StreamCodec<RegistryFriendlyByteBuf, T> biomeStreamCodec(AbstractBiomeParameterRecipe.Factory<T> factory) {
        return StreamCodec.composite(
                ByteBufCodecs.optional(BlockStateRecipeUtil.STREAM_CODEC), AbstractBiomeParameterRecipe::getBiome,
                BlockStateIngredient.CONTENTS_STREAM_CODEC, AbstractBiomeParameterRecipe::getIngredient,
                BlockPropertyPair.STREAM_CODEC, AbstractBiomeParameterRecipe::getResult,
                ByteBufCodecs.optional(Identifier.STREAM_CODEC), AbstractBiomeParameterRecipe::getFunctionId,
                factory::create);
    }

    public interface Factory<T extends AbstractBlockStateRecipe> {
        T create(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> functionId);
    }
}

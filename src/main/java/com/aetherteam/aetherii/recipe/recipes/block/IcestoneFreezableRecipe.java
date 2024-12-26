package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class IcestoneFreezableRecipe extends AbstractBiomeParameterRecipe {
    public IcestoneFreezableRecipe(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        super(AetherIIRecipeTypes.ICESTONE_FREEZABLE.get(), biome, ingredient, result, function);
    }

    public IcestoneFreezableRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        this(Optional.empty(), ingredient, result, function);
    }

    @Override
    public RecipeSerializer<IcestoneFreezableRecipe> getSerializer() {
        return AetherIIRecipeSerializers.ICESTONE_FREEZABLE.get();
    }

    public static class Serializer extends BiomeParameterRecipeSerializer<IcestoneFreezableRecipe> {
        public Serializer() {
            super(IcestoneFreezableRecipe::new, IcestoneFreezableRecipe::new);
        }
    }
}

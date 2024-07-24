package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.event.AetherEventDispatch;
import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.*;

public interface FreezingBlock extends FreezingBehavior<BlockState> {
    /**
     * This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function.
     */
    float SQRT_8 = Mth.sqrt(8);

    /**
     * Table of cached {@link AetherIIRecipeTypes#ICESTONE_FREEZABLE} recipes, searchable with associated {@link Block}s and {@link BlockPropertyPair}s.
     */
    Table<Block, Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>>, IcestoneFreezableRecipe> cachedBlocks = HashBasedTable.create();
    List<Block> cachedResults = new ArrayList<>();

    /**
     * Freezes blocks from one block to another using the {@link AetherIIRecipeTypes#ICESTONE_FREEZABLE} recipe type.
     *
     * @param level  The {@link Level} to freeze the blocks in.
     * @param pos    The {@link BlockPos} the freezing occurred at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param flag   The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, BlockPos origin, BlockState source, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            Block oldBlock = oldBlockState.getBlock();
            FluidState fluidState = level.getFluidState(pos);
            if (fluidState.isEmpty() || oldBlockState.is(fluidState.createLegacyBlock().getBlock())) { // Default freezing behavior.
                Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>> pair = matchesCache(oldBlock, oldBlockState, level.getBiome(pos));
                if (pair != null) {
                    IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(oldBlock, pair);
                    if (freezableRecipe != null) {
                        BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                        Optional<CacheableFunction> function = freezableRecipe.getFunction();
                        return this.freezeBlockAt(level, pos, origin, oldBlockState, newBlockState, function, source, flag);
                    }
                }
            } else if (!oldBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) { // Breaks a block before freezing if it has a FluidState attached by default (this is different from waterlogging for blocks like Kelp and Seagrass).
                oldBlockState = fluidState.createLegacyBlock();
                oldBlock = fluidState.createLegacyBlock().getBlock();
                Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>> pair = matchesCache(oldBlock, oldBlockState, level.getBiome(pos));
                if (pair != null) {
                    IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(oldBlock, pair);
                    if (freezableRecipe != null) {
                        level.destroyBlock(pos, true);
                        BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                        Optional<CacheableFunction> function = freezableRecipe.getFunction();
                        return this.freezeBlockAt(level, pos, origin, oldBlockState, newBlockState, function, source, flag);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(level, pos, origin, oldBlockState, newBlockState, source);
    }

    /**
     * Caches the {@link AetherIIRecipeTypes#ICESTONE_FREEZABLE} recipes through the level's {@link net.minecraft.world.item.crafting.RecipeManager}.
     *
     * @param level The {@link Level} that the recipe occurs in.
     */
    static void cacheRecipes(Level level) {
        if (FreezingBlock.cachedBlocks.isEmpty()) {
            for (RecipeHolder<IcestoneFreezableRecipe> recipe : level.getRecipeManager().getAllRecipesFor(AetherIIRecipeTypes.ICESTONE_FREEZABLE.get())) {
                IcestoneFreezableRecipe freezableRecipe = recipe.value();
                BlockPropertyPair[] pairs = freezableRecipe.getIngredient().getPairs();
                Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome = freezableRecipe.getBiome();
                if (pairs != null) {
                    Arrays.stream(pairs).forEach(pair -> cachedBlocks.put(pair.block(), Pair.of(pair, biome), freezableRecipe));
                }
                cachedResults.add(freezableRecipe.getResult().block());
            }
        }
    }

    /**
     * Checks if the given block is cached. If it is, then it continues on to loop through associated {@link BlockPropertyPair}s to check if the BlockState matches with one of them. If it does, then it returns the matching pair.
     *
     * @param block      The {@link Block} to check.
     * @param blockState The {@link BlockState} to check.
     * @return A cached {@link BlockPropertyPair}, or null if there was no matching pair.
     */
    @Nullable
    static Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>> matchesCache(Block block, BlockState blockState, Holder<Biome> biome) {
        if (cachedBlocks.containsRow(block)) {
            Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>> pair = null;
            for (Map.Entry<Pair<BlockPropertyPair, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>>>, IcestoneFreezableRecipe> entry : cachedBlocks.row(block).entrySet()) {
                BlockPropertyPair first = entry.getKey().getFirst();
                Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> second = entry.getKey().getSecond();
                if (first.matches(blockState)) {
                    if (second.isEmpty() || (second.get().left().isEmpty() || biome.is(second.get().left().get()))
                            || (second.get().right().isEmpty() || biome.is(second.get().right().get()))) {
                        pair = entry.getKey();
                    }
                    if (second.isPresent() && ((second.get().left().isPresent() && biome.is(second.get().left().get())) || (second.get().right().isPresent() && biome.is(second.get().right().get())))) {
                        break;
                    }
                }
            }
            return pair;
        }
        return null;
    }
}
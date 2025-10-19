package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.AmbrosiumCampfireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AmbrosiumCampfireBlock extends CampfireBlock {
    public AmbrosiumCampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
        super(spawnParticles, fireDamage, properties);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AmbrosiumCampfireBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!(level instanceof ServerLevel serverlevel)) {
            return state.getValue(LIT) ? createTickerHelper(blockEntityType, AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), AmbrosiumCampfireBlockEntity::particleTick) : null;
        } else {
            RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cache = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
            return state.getValue(LIT) ? createTickerHelper(blockEntityType, AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), (entityLevel, entityPos, entityState, blockEntity) -> AmbrosiumCampfireBlockEntity.cookTick(serverlevel, entityPos, entityState, blockEntity, cache)) : createTickerHelper(blockEntityType, AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), AmbrosiumCampfireBlockEntity::cooldownTick);
        }
    }
}


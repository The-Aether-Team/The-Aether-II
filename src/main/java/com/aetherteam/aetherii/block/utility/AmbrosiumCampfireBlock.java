package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.AmbrosiumCampfireBlockEntity;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AmbrosiumCampfireBlock extends CampfireBlock {
    private static final VoxelShape SHAPE = Block.column(16.0F, 0.0F, 3.0F);

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

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(LIT)) {
            if (stack.is(AetherIIItems.AMBROSIUM_SHARD)) {
                BlockState litState = state.setValue(LIT, true);
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, litState, 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
    

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    public static void makeParticles(Level level, BlockPos pos, boolean isSignalFire, boolean smoking) {
        RandomSource random = level.getRandom();
        SimpleParticleType smokeParticle = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        level.addAlwaysVisibleParticle(smokeParticle, true, pos.getX() + 0.5F + random.nextDouble() / 3.0F * (random.nextBoolean() ? 1 : -1), pos.getY() + random.nextDouble() + random.nextDouble(), pos.getZ() + 0.5F + random.nextDouble() / 3.0F * (random.nextBoolean() ? 1 : -1), 0.0F, 0.07, 0.0F);
        if (smoking) {
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.3F + random.nextDouble() / 4.0F * (random.nextBoolean() ? 1 : -1), pos.getY() + 0.4, pos.getZ() + 0.5F + random.nextDouble() / 4.0F * (random.nextBoolean() ? 1 : -1), 0.0F, 0.005, 0.0F);
        }
    }
}


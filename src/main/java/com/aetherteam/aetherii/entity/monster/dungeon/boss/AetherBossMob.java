package com.aetherteam.aetherii.entity.monster.dungeon.boss;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.block.dungeon.GroundTrapBlock;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * Interface for handling boss-related behavior for mobs.
 *
 * @see BossMob
 */
public interface AetherBossMob<T extends Mob & AetherBossMob<T>> extends BossMob<T> {
    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    /**
     * Handles behavior when closing the boss room, like closing the doors.
     */
    default void closeRoom() {
        this.modifyRoom((level, pos, oldState) -> {
            if (oldState.getBlock() instanceof CopyBlock && !oldState.getValue(CopyBlock.EMPTY)) {
                if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                    return blockEntity.close(level, pos);
                }
            }
            return null;
        });
    }

    /**
     * Handles behavior when opening the boss room, like opening the doors.
     */
    default void openRoom() {
        this.modifyRoom((level, pos, oldState) -> {
            if (oldState.getBlock() instanceof CopyBlock && !oldState.getValue(CopyBlock.EMPTY)) {
                if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                    return blockEntity.open(level, pos);
                }
            }
            if (oldState.getBlock() instanceof GroundTrapBlock && oldState.getValue(GroundTrapBlock.LOCKED) && oldState.getValue(GroundTrapBlock.TRAP_STATE) != AetherIIBlockStateProperties.TrapState.LOADED) {
                return oldState.setValue(GroundTrapBlock.TRAP_STATE, AetherIIBlockStateProperties.TrapState.LOADED);
            }
            return null;
        });
    }

    /**
     * Called when the boss is defeated to change all blocks to unlocked blocks.
     */
    @Override
    default void tearDownRoom() {
        this.modifyRoom(this::convertBlock);
    }

    @Nullable
    default BlockState convertBlock(Level level, BlockPos pos, BlockState oldState) {
        return this.convertBlock(oldState);
    }

    private void modifyRoom(RoomModifier modifier) {
        if (this.getDungeon() != null) {
            AABB bounds = this.getDungeon().roomBounds();
            Level level = this.self().level();
            for (BlockPos pos : BlockPos.betweenClosed((int) bounds.minX, (int) bounds.minY, (int) bounds.minZ, (int) bounds.maxX, (int) bounds.maxY, (int) bounds.maxZ)) {
                BlockState oldState = level.getBlockState(pos);
                BlockState newState = modifier.apply(level, pos, oldState);
                if (newState != null) {
                    level.setBlock(pos, newState, 1 | 2);
                }
            }
        }
    }

    @FunctionalInterface
    interface RoomModifier {
        @Nullable
        BlockState apply(Level level, BlockPos pos, BlockState oldState);
    }

    /**
     * Evaporates a liquid block.
     *
     * @param entity The boss entity.
     * @param min    The minimum {@link BlockPos} bounds corner.
     * @param max    The maximum {@link BlockPos} bounds corner.
     * @param check  An additional check using a {@link BlockState} {@link Predicate}.
     */
    default void evaporate(T entity, BlockPos min, BlockPos max, Predicate<BlockState> check) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            if (ForgeEventFactory.getMobGriefingEvent(serverLevel, entity)) {
                for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                    if (entity.level().getBlockState(pos).getBlock() instanceof LiquidBlock && check.test(entity.level().getBlockState(pos))) {
                        entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        this.evaporateEffects(entity, pos);
                    } else if (!entity.level().getFluidState(pos).isEmpty() && entity.level().getBlockState(pos).hasProperty(BlockStateProperties.WATERLOGGED) && check.test(entity.level().getFluidState(pos).createLegacyBlock())) {
                        entity.level().setBlockAndUpdate(pos, entity.level().getBlockState(pos).setValue(BlockStateProperties.WATERLOGGED, false));
                        this.evaporateEffects(entity, pos);
                    }
                }
            }
        }
    }

    /**
     * Spawns particles and plays sounds for evaporation.
     *
     * @param entity The boss entity.
     * @param pos    The {@link BlockPos} for effects.
     */
    default void evaporateEffects(T entity, BlockPos pos) {
        Level level = entity.level();
        double a = pos.getX() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double b = pos.getY() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double c = pos.getZ() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
        }
        entity.level().playSound(null, pos, AetherIISoundEvents.WATER_EVAPORATE.get(), SoundSource.BLOCKS, 0.5F, 2.6F + (entity.level().getRandom().nextFloat() - entity.level().getRandom().nextFloat()) * 0.8F);
    }

    /**
     * The default minimum and maximum positions for expanded entity bounds.
     *
     * @param entity The boss entity.
     * @return A {@link Pair} of the minimum {@link BlockPos} and the maximum {@link BlockPos}.
     */
    default Pair<BlockPos, BlockPos> getDefaultBounds(T entity) {
        AABB boundingBox = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(boundingBox.minX - 1, boundingBox.minY - 1, boundingBox.minZ - 1);
        BlockPos max = BlockPos.containing(Math.ceil(boundingBox.maxX - 1) + 1, Math.ceil(boundingBox.maxY - 1) + 1, Math.ceil(boundingBox.maxZ - 1) + 1);
        return Pair.of(min, max);
    }

    /**
     * @return The {@link } for this boss's health bar.
     */
    @Nullable
    ResourceLocation getBossBarTexture();

    /**
     * @return The {@link } for this boss's health bar background.
     */
    @Nullable
    ResourceLocation getBossBarBackgroundTexture();

    /**
     * @return The {@link Music} for this boss's fight.
     */
    @Nullable
    default Music getBossMusic() {
        return null;
    }

    @Override
    default int getDeathScore() {
        return 0;
    }
}

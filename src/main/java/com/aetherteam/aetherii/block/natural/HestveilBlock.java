package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.clientbound.HestveilExplosionEffectsPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.aetherteam.aetherii.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class HestveilBlock extends Block implements CanisterPickup {
    public static final int MAX_HORIZONTAL_DISTANCE = 2;
    public static final int MAX_VERTICAL_DISTANCE = 3;
    public static final IntegerProperty HORIZONTAL_DISTANCE = IntegerProperty.create("hestveil_horizontal_distance", 0, MAX_HORIZONTAL_DISTANCE);
    public static final IntegerProperty VERTICAL_DISTANCE = IntegerProperty.create("hestveil_vertical_distance", 0, MAX_VERTICAL_DISTANCE);
    public static final BooleanProperty IGNITED = AetherIIBlockStateProperties.IGNITED;

    public static final List<BlockPos> PLACEMENT_OFFSETS = BlockPos.betweenClosedStream(-1, 0, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) != 0).toList();
    public static final List<BlockPos> AROUND_OFFSETS = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) != 0).toList();
    public static final List<BlockPos> INDIRECT_NEIGHBOR_OFFSETS = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) > 1).toList();

    public HestveilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_DISTANCE, 0).setValue(VERTICAL_DISTANCE, 0).setValue(IGNITED, false));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(IGNITED)) {
            this.explode(level, pos, level.getRandom().nextInt(20) == 0);
            return;
        }
        level.setBlock(pos, updateDistance(state, level, pos), 3);
        if (state.getValue(HORIZONTAL_DISTANCE) < MAX_HORIZONTAL_DISTANCE && state.getValue(VERTICAL_DISTANCE) < MAX_VERTICAL_DISTANCE) {
            for (Vec3i offset : PLACEMENT_OFFSETS) {
                BlockPos offsetPos = pos.offset(offset);
                if (level.getBlockState(offsetPos).isAir()) {
                    level.setBlock(offsetPos, updateDistance(state, level, offsetPos), 3);
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(200) == 0) {
            level.addParticle(AetherIIParticleTypes.HESTVEIL.get(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        int delay = 10;
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            if (this.shouldExplode(level.getBlockState(offsetPos))) {
                level.setBlock(pos, state.setValue(IGNITED, true), 3);
                delay = 1;
            }
        }
        level.scheduleTick(pos, this, delay);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelReader, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        if (this.shouldExplode(neighborState) || (neighborState.getBlock() == AetherIIBlocks.HESTVEIL.get() && neighborState.getValue(IGNITED))) {
            state = state.setValue(IGNITED, true);
            scheduledTickAccess.scheduleTick(pos, this, 1);
        }
        if (direction.getAxis().isHorizontal()) {
            int i = getDistanceAt(neighborState, HORIZONTAL_DISTANCE, MAX_HORIZONTAL_DISTANCE) + 1;
            if (i != 1 || state.getValue(HORIZONTAL_DISTANCE) != i) {
                scheduledTickAccess.scheduleTick(pos, this, 10);
            }
        } else if (direction.getAxis().isVertical()) {
            int j = getDistanceAt(neighborState, VERTICAL_DISTANCE, MAX_VERTICAL_DISTANCE) + 1;
            if (j != 1 || state.getValue(VERTICAL_DISTANCE) != j) {
                scheduledTickAccess.scheduleTick(pos, this, 10);
            }
        }
        return state;
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, int flags, int recursionLeft) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Vec3i offset : INDIRECT_NEIGHBOR_OFFSETS) {
            mutablePos.setWithOffset(pos, offset);
            if (level.getBlockState(mutablePos).is(this)) {
                if (this.shouldExplode(level.getBlockState(mutablePos))) {
                    level.setBlock(pos, state.setValue(IGNITED, true), 3);
                    level.scheduleTick(pos, this, 1);
                }
            }
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == AetherIIBlocks.HESTVEIL.get()) {
            level.setBlock(pos, state.setValue(IGNITED, true), 3);
            level.scheduleTick(pos, this, 1);
        }
    }

    public boolean shouldExplode(BlockState state) {
        if (!state.is(AetherIITags.Blocks.TRIGGERS_HESTVEIL)) {
            return false;
        }
        if (state.hasProperty(BlockStateProperties.LIT)) {
            return state.getValue(BlockStateProperties.LIT);
        } else {
            return true;
        }
    }

    public void explode(Level level, BlockPos pos, boolean playSound) {
        if (level.removeBlock(pos, false)) {
            if (level instanceof ServerLevel serverLevel) {
                if (playSound) {
                    serverLevel.playSound(null,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            AetherIISoundEvents.BLOCK_HESTVEIL_IGNITE.get(),
                            SoundSource.BLOCKS,
                            1.0F,
                            (1.0F + (serverLevel.getRandom().nextFloat() - serverLevel.getRandom().nextFloat()) * 0.2F) * 0.7F);
                }
                for (int i = 0; i <= 5; i++) {
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                            pos.getX() + serverLevel.getRandom().nextDouble(),
                            pos.getY() + serverLevel.getRandom().nextDouble(),
                            pos.getZ() + serverLevel.getRandom().nextDouble(),
                            1, 0, 0, 0, 0);
                }
                PacketDistributor.sendToPlayersInDimension(serverLevel, new HestveilExplosionEffectsPacket(pos));
            }
            for (Entity entity : level.getEntities(null, new AABB(pos))) {
                if (entity instanceof LivingEntity livingEntity) {
                    AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, EffectBuildupPresets.IMMOLATION, 150);
                }
            }
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = pos.relative(direction);
                BlockState offsetState = level.getBlockState(offsetPos);
                if (this.shouldExplode(offsetState)) {
                    level.destroyBlock(offsetPos, true);
                }
            }
        }
    }

    public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int i = MAX_HORIZONTAL_DISTANCE;
        int j = MAX_VERTICAL_DISTANCE;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Vec3i offset : AROUND_OFFSETS) {
            mutablePos.setWithOffset(pos, offset);
            if (offset.getY() == 0) { // Check blocks next to this.
                i = Math.min(i, getDistanceAt(level.getBlockState(mutablePos), HORIZONTAL_DISTANCE, MAX_HORIZONTAL_DISTANCE) + 1);
                j = Math.min(j, getDistanceAt(level.getBlockState(mutablePos), VERTICAL_DISTANCE, MAX_VERTICAL_DISTANCE));
            } else if (offset.getX() == 0 && offset.getZ() == 0) {  // Check blocks above or below this.
                i = Math.min(i, getDistanceAt(level.getBlockState(mutablePos), HORIZONTAL_DISTANCE, MAX_HORIZONTAL_DISTANCE));
                j = Math.min(j, getDistanceAt(level.getBlockState(mutablePos), VERTICAL_DISTANCE, MAX_VERTICAL_DISTANCE) + 1);
            }
        }
        return state.setValue(HORIZONTAL_DISTANCE, i).setValue(VERTICAL_DISTANCE, j);
    }

    private static int getDistanceAt(BlockState neighbor, IntegerProperty property, int max) {
        return getOptionalDistanceAt(neighbor, property).orElse(max);
    }

    public static OptionalInt getOptionalDistanceAt(BlockState state, IntegerProperty property) {
        if (state.is(AetherIIBlocks.ALKAHEST.get())) {
            return OptionalInt.of(0);
        } else {
            return state.hasProperty(property) ? OptionalInt.of(state.getValue(property)) : OptionalInt.empty();
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (context.isHoldingItem(AetherIIItems.ARKENIUM_CANISTER.get()) || context.isHoldingItem(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER.get())) ? Shapes.block() : Shapes.empty();
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        return true;
    }

    @Override
    public ItemStack pickupBlockWithCanister(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
        if (!levelAccessor.isClientSide()) {
            levelAccessor.levelEvent(2001, blockPos, Block.getId(blockState));
        }
        return new ItemStack(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER.get());
    }

    @Override
    public Optional<SoundEvent> getCanisterPickupSound(BlockState state) { //todo
        return Optional.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_DISTANCE, VERTICAL_DISTANCE, IGNITED);
    }
}

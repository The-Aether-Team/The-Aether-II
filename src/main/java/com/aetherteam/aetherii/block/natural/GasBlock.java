package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.clientbound.GasExplosionEffectsPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class GasBlock extends Block implements CanisterPickup {
    public static final int MAX_HORIZONTAL_DISTANCE = 2;
    public static final int MAX_VERTICAL_DISTANCE = 3;
    public static final IntegerProperty HORIZONTAL_DISTANCE = IntegerProperty.create("gas_horizontal_distance", 0, MAX_HORIZONTAL_DISTANCE);
    public static final IntegerProperty VERTICAL_DISTANCE = IntegerProperty.create("gas_vertical_distance", 0, MAX_VERTICAL_DISTANCE);

    public static final List<BlockPos> PLACEMENT_OFFSETS = BlockPos.betweenClosedStream(-1, 0, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) != 0).toList();
    public static final List<BlockPos> AROUND_OFFSETS = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) != 0).toList();
    public static final List<BlockPos> INDIRECT_NEIGHBOR_OFFSETS = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1).map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) > 1).toList();

    public GasBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, updateDistance(state, level, pos), 3);
        if (state.getValue(HORIZONTAL_DISTANCE) < MAX_HORIZONTAL_DISTANCE && state.getValue(VERTICAL_DISTANCE) < MAX_VERTICAL_DISTANCE) {
            for (Vec3i offset : PLACEMENT_OFFSETS) {
                BlockPos offsetPos = pos.offset(offset);
                if (level.getBlockState(offsetPos).isEmpty()) {
                    level.setBlock(offsetPos, updateDistance(state, level, offsetPos), 3);
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(200) == 0) {
            level.addParticle(AetherIIParticleTypes.GAS.get(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
        }
    }

    @Override
    protected void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, int flags, int recursionLeft) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (Vec3i offset : INDIRECT_NEIGHBOR_OFFSETS) {
            blockpos$mutableblockpos.setWithOffset(pos, offset);
            if (level.getBlockState(blockpos$mutableblockpos).is(this)) {
                level.neighborShapeChanged(Direction.getNearest(offset.getX(), offset.getY(), offset.getZ(), null).getOpposite(), blockpos$mutableblockpos, pos, state, flags, recursionLeft);
            }
        }
    }

//    @Override //TODO HOW DOES ORIENTATION WORK
//    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
//        if (level.getBlockState(neighborPos).is(AetherIITags.Blocks.TRIGGERS_GAS) || state.is(AetherIITags.Blocks.TRIGGERS_GAS)) {
//            this.explode(level, pos, true);
//        }
//        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
//    }
//
//    @Override
//    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
//        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
//    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_DISTANCE).add(VERTICAL_DISTANCE);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 10);
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction.getUnitVec3i());
            if (level.getBlockState(offsetPos).is(AetherIITags.Blocks.TRIGGERS_GAS)) {
                this.explode(level, pos, true);
            }
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onExplosionHit(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        this.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        this.explode(level, pos, true);
    }

    public void explode(Level level, BlockPos pos, boolean playSound) {
        if (level.removeBlock(pos, false)) {
            if (level instanceof ServerLevel serverLevel) {
                PacketDistributor.sendToPlayersInDimension(serverLevel, new GasExplosionEffectsPacket(pos, playSound));
            }
            for (Entity entity : level.getEntities(null, AABB.encapsulatingFullBlocks(pos, pos))) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.IMMOLATION, 100);
                }
            }
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = pos.relative(direction);
                if (level.getBlockState(offsetPos).getBlock() instanceof GasBlock gasBlock) {
                    gasBlock.explode(level, offsetPos, level.getRandom().nextInt(20) == 0);
                } else if (level.getBlockState(offsetPos).is(AetherIITags.Blocks.TRIGGERS_GAS)) {
                    level.destroyBlock(offsetPos, true);
                }
            }
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        if (facing.getAxis().isHorizontal()) {
            int i = getDistanceAt(facingState, HORIZONTAL_DISTANCE, MAX_HORIZONTAL_DISTANCE) + 1;
            if (i != 1 || state.getValue(HORIZONTAL_DISTANCE) != i) {
                scheduledTickAccess.scheduleTick(currentPos, this, 10);
            }
        } else if (facing.getAxis().isVertical()) {
            int j = getDistanceAt(facingState, VERTICAL_DISTANCE, MAX_VERTICAL_DISTANCE) + 1;
            if (j != 1 || state.getValue(VERTICAL_DISTANCE) != j) {
                scheduledTickAccess.scheduleTick(currentPos, this, 10);
            }
        }
        return state;
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
        if (state.is(AetherIIBlocks.ACID)) {
            return OptionalInt.of(0);
        } else {
            return state.hasProperty(property) ? OptionalInt.of(state.getValue(property)) : OptionalInt.empty();
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (context.isHoldingItem(AetherIIItems.ARKENIUM_CANISTER.get()) || context.isHoldingItem(AetherIIItems.ARKENIUM_GAS_CANISTER.get())) ? Shapes.block() : Shapes.empty();
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public ItemStack pickupBlockWithCanister(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
        if (!levelAccessor.isClientSide()) {
            levelAccessor.levelEvent(2001, blockPos, Block.getId(blockState));
        }
        return new ItemStack(AetherIIItems.ARKENIUM_GAS_CANISTER.get());
    }

    @Override
    public Optional<SoundEvent> getCanisterPickupSound(BlockState state) { //todo
        return Optional.empty();
    }
}

package com.aetherteam.aetherii.block.portal;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.AetherIIPoi;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Comparator;
import java.util.Optional;

public class AetherPortalForcer { //todo: port to 1.21 portal system
    private final ServerLevel level;
//    private final boolean hasFrame; // Whether to generate a portal frame or not.
//    private final boolean isStartup;

    public AetherPortalForcer(ServerLevel level) {
        this.level = level;
    }

//    @Override
//    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceLevel, ServerLevel destinationLevel) {
//        if (this.hasFrame) {
//            PacketDistributor.sendToPlayer(new PortalTravelSoundPacket(), player);
//        }
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
//        EntityAccessor entityAccessor = (EntityAccessor) entity;
//        boolean isAether = destinationLevel.dimension() == LevelUtil.destinationDimension();
//        if (entity.level().dimension() != LevelUtil.destinationDimension() && !isAether) {
//            return null;
//        } else if (this.isStartup) {
//            return new PortalInfo(this.checkPositionsForInitialSpawn(destinationLevel, entity.blockPosition()).getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
//        } else if (!this.hasFrame) { // For falling out of the Aether.
//            return new PortalInfo(new Vec3(entity.getX(), destinationLevel.getMaxBuildHeight(), entity.getZ()), Vec3.ZERO, entity.getYRot(), entity.getXRot());
//        } else {
//            WorldBorder worldBorder = destinationLevel.getWorldBorder();
//            double scale = DimensionType.getTeleportationScale(this.level.dimensionType(), destinationLevel.dimensionType());
//            BlockPos scaledEntityPos = worldBorder.clampToBounds(entity.getX() * scale, entity.getY(), entity.getZ() * scale);
//            return this.getExitPortal(entity, scaledEntityPos, worldBorder).map((rectangle) -> {
//                BlockState blockState = this.level.getBlockState(entityAccessor.aether_ii$getPortalEntrancePos());
//                Direction.Axis axis;
//                Vec3 vec3;
//                if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
//                    axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
//                    BlockUtil.FoundRectangle foundRectangle = BlockUtil.getLargestRectangleAround(entityAccessor.aether_ii$getPortalEntrancePos(), axis, 21, Direction.Axis.Y, 21, (blockPos) -> this.level.getBlockState(blockPos) == blockState);
//                    vec3 = entityAccessor.callGetRelativePortalPosition(axis, foundRectangle);
//                } else {
//                    axis = Direction.Axis.X;
//                    vec3 = new Vec3(0.5, 0.0, 0.0);
//                }
//                return PortalShape.createPortalInfo(destinationLevel, rectangle, axis, vec3, entity, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
//            }).orElse(null);
//        }
//    }
//
//    private Optional<BlockUtil.FoundRectangle> getExitPortal(Entity entity, BlockPos findFrom, WorldBorder worldBorder) {
//        EntityAccessor entityAccessor = (EntityAccessor) entity;
//        if (entity instanceof ServerPlayer) {
//            Optional<BlockUtil.FoundRectangle> optional = this.findPortalAround(findFrom, worldBorder);
//            if (optional.isPresent()) {
//                return optional;
//            } else {
//                Direction.Axis direction$axis = this.level.getBlockState(entityAccessor.aether_ii$getPortalEntrancePos()).getOptionalValue(AetherPortalBlock.AXIS).orElse(Direction.Axis.X);
//                Optional<BlockUtil.FoundRectangle> portalOptional = this.createPortal(findFrom, direction$axis);
//                if (portalOptional.isEmpty()) {
//                    AetherII.LOGGER.error("Unable to create an Aether Portal, likely target out of worldborder");
//                }
//                return portalOptional;
//            }
//        } else {
//            return this.findPortalAround(findFrom, worldBorder);
//        }
//    }
//
//    /**
//     * Based on {@link net.minecraft.world.level.portal.PortalForcer#findPortalAround(BlockPos, boolean, WorldBorder)}.
//     */
//    private Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos pos, WorldBorder worldBorder) {
//        PoiManager poiManager = this.level.getPoiManager();
//        poiManager.ensureLoadedAndValid(this.level, pos, 128);
//        ResourceLocation portal = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getKey(AetherIIPoi.AETHER_PORTAL.get());
//        if (portal != null) {
//            Optional<PoiRecord> optionalPoi = poiManager.getInSquare((poiType) -> poiType.is(portal), pos, 128, PoiManager.Occupancy.ANY)
//                    .filter((poiRecord) -> worldBorder.isWithinBounds(poiRecord.getPos()))
//                    .sorted(Comparator.<PoiRecord>comparingDouble((poiRecord) -> poiRecord.getPos().distSqr(pos)).thenComparingInt((poiRecord) -> poiRecord.getPos().getY()))
//                    .filter((poiRecord) -> this.level.getBlockState(poiRecord.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
//                    .findFirst();
//            return optionalPoi.map((poiRecord) -> {
//                BlockPos poiPos = poiRecord.getPos();
//                this.level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(poiPos), 3, poiPos);
//                BlockState blockstate = this.level.getBlockState(poiPos);
//                return BlockUtil.getLargestRectangleAround(poiPos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (blockPos) -> this.level.getBlockState(blockPos) == blockstate);
//            });
//        } else {
//            return Optional.empty();
//        }
//    }

    public Optional<BlockPos> findClosestPortalPosition(BlockPos pExitPos, boolean pIsNether, WorldBorder pWorldBorder) {
        PoiManager poimanager = this.level.getPoiManager();
        int i = 128;
        poimanager.ensureLoadedAndValid(this.level, pExitPos, i);
        return poimanager.getInSquare(p_230634_ -> p_230634_.is(AetherIIPoi.AETHER_PORTAL), pExitPos, i, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .filter(pWorldBorder::isWithinBounds)
                .filter(p_352047_ -> this.level.getBlockState(p_352047_).hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                .min(Comparator.<BlockPos>comparingDouble(p_352046_ -> p_352046_.distSqr(pExitPos)).thenComparingInt(Vec3i::getY));
    }

    /**
     * Based on {@link net.minecraft.world.level.portal.PortalForcer#createPortal(BlockPos, Direction.Axis)}.
     */
    public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos pos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0;
        BlockPos blockPos = null;
        double d1 = -1.0;
        BlockPos blockPos1 = null;
        WorldBorder worldBorder = this.level.getWorldBorder();
        int i = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        for (BlockPos.MutableBlockPos mutablePos1 : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, mutablePos1.getX(), mutablePos1.getZ()));
            if (worldBorder.isWithinBounds(mutablePos1) && worldBorder.isWithinBounds(mutablePos1.move(direction, 1))) {
                mutablePos1.move(direction.getOpposite(), 1);

                for (int l = j; l >= this.level.getMinBuildHeight(); --l) {
                    mutablePos1.setY(l);
                    if (this.level.isEmptyBlock(mutablePos1)) {
                        int i1;
                        for (i1 = l; l > this.level.getMinBuildHeight() && this.level.isEmptyBlock(mutablePos1.move(Direction.DOWN)); --l) {
                        }

                        if (l + 4 <= i) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                mutablePos1.setY(l);
                                if (this.canHostFrame(mutablePos1, mutablePos, direction, 0)) {
                                    double d2 = pos.distSqr(mutablePos1);
                                    if (this.canHostFrame(mutablePos1, mutablePos, direction, -1) && this.canHostFrame(mutablePos1, mutablePos, direction, 1) && (d0 == -1.0 || d0 > d2)) {
                                        d0 = d2;
                                        blockPos = mutablePos1.immutable();
                                    }
                                    if (d0 == -1.0 && (d1 == -1.0 || d1 > d2)) {
                                        d1 = d2;
                                        blockPos1 = mutablePos1.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (d0 == -1.0 && d1 != -1.0) {
            blockPos = blockPos1;
            d0 = d1;
        }

        if (d0 == -1.0) {
            int k1 = Math.max(this.level.getMinBuildHeight() + 1, 70);
            int i2 = i - 9;
            if (i2 < k1) {
                return Optional.empty();
            }

            blockPos = new BlockPos(pos.getX(), Mth.clamp(pos.getY(), k1, i2), pos.getZ()).immutable();
            Direction direction1 = direction.getClockWise();
            if (!worldBorder.isWithinBounds(blockPos)) {
                return Optional.empty();
            }

            for (int i3 = -1; i3 < 2; ++i3) {
                for (int j3 = 0; j3 < 2; ++j3) {
                    for (int k3 = -1; k3 < 3; ++k3) {
                        BlockState blockState1 = k3 < 0 ? Blocks.QUARTZ_BLOCK.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        mutablePos.setWithOffset(blockPos, j3 * direction.getStepX() + i3 * direction1.getStepX(), k3, j3 * direction.getStepZ() + i3 * direction1.getStepZ());
                        this.level.setBlockAndUpdate(mutablePos, blockState1);
                    }
                }
            }
        }

        for (int l1 = -1; l1 < 3; ++l1) {
            for (int j2 = -1; j2 < 4; ++j2) {
                if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
                    mutablePos.setWithOffset(blockPos, l1 * direction.getStepX(), j2, l1 * direction.getStepZ());
                    this.level.setBlock(mutablePos, Blocks.QUARTZ_BLOCK.defaultBlockState(), 1 | 2);
                }
            }
        }

        BlockState blockState = AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, axis);
        for (int k2 = 0; k2 < 2; ++k2) {
            for (int l2 = 0; l2 < 3; ++l2) {
                mutablePos.setWithOffset(blockPos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
                this.level.setBlock(mutablePos, blockState, 2 | 16);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(blockPos.immutable(), 2, 3));
    }

    @SuppressWarnings("deprecation")
    private boolean canHostFrame(BlockPos originalPos, BlockPos.MutableBlockPos offsetPos, Direction direction, int offsetScale) {
        Direction clockWiseDirection = direction.getClockWise();
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                offsetPos.setWithOffset(originalPos, direction.getStepX() * i + clockWiseDirection.getStepX() * offsetScale, j, direction.getStepZ() * i + clockWiseDirection.getStepZ() * offsetScale);
                BlockState blockState = this.level.getBlockState(offsetPos);
                if (j < 0 && (!blockState.isSolid()
                        || blockState.is(AetherIITags.Blocks.AETHER_PORTAL_BLACKLIST))) {
                    return false;
                }
                if (j >= 0 && !this.level.isEmptyBlock(offsetPos)) {
                    return false;
                }
            }
        }
        return true;
    }
//
//    private BlockPos checkPositionsForInitialSpawn(Level level, BlockPos origin) {
//        if (!this.isSafe(level, origin)) {
//            for (int i = 0; i <= 750; i += 5) {
//                for (Direction facing : Direction.Plane.HORIZONTAL) {
//                    BlockPos offsetPosition = origin.offset(facing.getNormal().multiply(i));
//                    if (this.isSafeAround(level, offsetPosition)) {
//                        return offsetPosition;
//                    }
//                    BlockPos heightmapPosition = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, offsetPosition);
//                    if (this.isSafeAround(level, heightmapPosition)) {
//                        return heightmapPosition;
//                    }
//                }
//            }
//        }
//        return origin;
//    }
//
//    public boolean isSafeAround(Level level, BlockPos pos) {
//        BlockPos belowPos = pos.below();
//        if (!this.isSafe(level, belowPos)) {
//            return false;
//        }
//        for (Direction facing : Direction.Plane.HORIZONTAL) {
//            if (!this.isSafe(level, belowPos.relative(facing, 2))) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean isSafe(Level level, BlockPos pos) {
//        return level.getWorldBorder().isWithinBounds(pos) && level.getBlockState(pos).is(AetherIITags.Blocks.AETHER_DIRT) && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.above(2)).isAir();
//    }
}
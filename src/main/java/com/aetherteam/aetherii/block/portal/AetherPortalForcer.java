package com.aetherteam.aetherii.block.portal;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.network.packet.clientbound.PortalTravelSoundPacket;
import com.aetherteam.aetherii.world.AetherIIPoi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Comparator;
import java.util.Optional;

public class AetherPortalForcer {
    public static final TeleportTransition.PostTeleportTransition PLAY_PORTAL_SOUND = AetherPortalForcer::playPortalSound;

    private final ServerLevel level;

    public AetherPortalForcer(ServerLevel level) {
        this.level = level;
    }

    private static void playPortalSound(Entity entity) {
        if (entity instanceof ServerPlayer serverplayer) {
            PacketDistributor.sendToPlayer(serverplayer, new PortalTravelSoundPacket());
        }
    }

    public Optional<BlockPos> findClosestPortalPosition(BlockPos exitPos, WorldBorder pWorldBorder) {
        PoiManager poimanager = this.level.getPoiManager();
        int i = 128;
        poimanager.ensureLoadedAndValid(this.level, exitPos, i);
        return poimanager.getInSquare(poi -> poi.is(AetherIIPoi.AETHER_PORTAL), exitPos, i, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .filter(pWorldBorder::isWithinBounds)
                .filter(p_352047_ -> this.level.getBlockState(p_352047_).hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                .min(Comparator.<BlockPos>comparingDouble(p_352046_ -> p_352046_.distSqr(exitPos)).thenComparingInt(Vec3i::getY));
    }

    /**
     * Based on {@link net.minecraft.world.level.portal.PortalForcer#createPortal(BlockPos, Direction.Axis)}.
     */
    public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos origin, Direction.Axis portalAxis, boolean createPortal) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, portalAxis);
        double closestFullDistanceSqr = -1.0;
        BlockPos closestFullPosition = null;
        double closestPartialDistanceSqr = -1.0;
        BlockPos closestPartialPosition = null;
        WorldBorder worldBorder = this.level.getWorldBorder();
        int maxPlaceableY = Math.min(this.level.getMaxY(), this.level.getMinY() + this.level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos mutable = origin.mutable();

        AetherII.LOGGER.info(String.valueOf(origin));
        Pair<BlockPos, Holder<Biome>> nearest = this.level.findClosestBiome3d((biome) -> !biome.is(AetherIITags.Biomes.AERCLOUD_SEA) && !biome.is(AetherIITags.Biomes.UNDERCLOUD), origin, 256, 16, 64);
        if (nearest != null) {
            AetherII.LOGGER.info(origin + " " + nearest);
            for (BlockPos.MutableBlockPos columnPos : BlockPos.spiralAround(nearest.getFirst(), 64, Direction.EAST, Direction.SOUTH)) {
                int height = Math.min(maxPlaceableY, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, columnPos.getX(), columnPos.getZ()));
                if (worldBorder.isWithinBounds(columnPos) && worldBorder.isWithinBounds(columnPos.move(direction, 1))) {
                    columnPos.move(direction.getOpposite(), 1);

                    for (int y = height; y >= this.level.getMinY(); --y) {
                        columnPos.setY(y);
                        if (this.canPortalReplaceBlock(columnPos)) {
                            int firstEmptyY;
                            for (firstEmptyY = y; y > this.level.getMinY() && this.canPortalReplaceBlock(columnPos.move(Direction.DOWN)); --y) {
                            }

                            if (y + 4 <= maxPlaceableY) {
                                int deltaY = firstEmptyY - y;
                                if (deltaY <= 0 || deltaY >= 3) {
                                    columnPos.setY(y);
                                    if (this.canHostFrame(columnPos, mutable, direction, 0)) {
                                        double distance = origin.distSqr(columnPos);
                                        if (this.canHostFrame(columnPos, mutable, direction, -1) && this.canHostFrame(columnPos, mutable, direction, 1) && (closestFullDistanceSqr == -1.0F || closestFullDistanceSqr > distance)) {
                                            closestFullDistanceSqr = distance;
                                            closestFullPosition = columnPos.immutable();
                                        }

                                        if (closestFullDistanceSqr == -1.0F && (closestPartialDistanceSqr == -1.0F || closestPartialDistanceSqr > distance)) {
                                            closestPartialDistanceSqr = distance;
                                            closestPartialPosition = columnPos.immutable();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            AetherII.LOGGER.info("fail");
        }

        if (closestFullDistanceSqr == -1.0F && closestPartialDistanceSqr != -1.0F) {
            closestFullPosition = closestPartialPosition;
            closestFullDistanceSqr = closestPartialDistanceSqr;
        }

        if (closestFullDistanceSqr == -1.0F) {
            int minStartY = Math.max(this.level.getMinY() - -1, 70);
            int maxStartY = maxPlaceableY - 9;
            if (maxStartY < minStartY) {
                return Optional.empty();
            }

            closestFullPosition = new BlockPos(origin.getX() - direction.getStepX(), Mth.clamp(origin.getY(), minStartY, maxStartY), origin.getZ() - direction.getStepZ()).immutable();
            closestFullPosition = worldBorder.clampToBounds(closestFullPosition);
        }

        if (createPortal) {
            if (closestFullDistanceSqr == -1.0F) {
                Direction clockWise = direction.getClockWise();

                for (int box = -1; box < 2; ++box) {
                    for (int width = 0; width < 2; ++width) {
                        for (int height = -1; height < 3; ++height) {
                            BlockState blockState = height < 0 ? Blocks.GLOWSTONE.defaultBlockState() : Blocks.AIR.defaultBlockState();
                            mutable.setWithOffset(closestFullPosition, width * direction.getStepX() + box * clockWise.getStepX(), height, width * direction.getStepZ() + box * clockWise.getStepZ());
                            this.level.setBlockAndUpdate(mutable, blockState);
                        }
                    }
                }
            }

            for (int width = -1; width < 3; ++width) {
                for (int height = -1; height < 4; ++height) {
                    if (width == -1 || width == 2 || height == -1 || height == 3) {
                        mutable.setWithOffset(closestFullPosition, width * direction.getStepX(), height, width * direction.getStepZ());
                        this.level.setBlock(mutable, Blocks.GLOWSTONE.defaultBlockState(), 3);
                    }
                }
            }

            BlockState portalBlockState = AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, portalAxis);

            for (int width = 0; width < 2; ++width) {
                for (int heightx = 0; heightx < 3; ++heightx) {
                    mutable.setWithOffset(closestFullPosition, width * direction.getStepX(), heightx, width * direction.getStepZ());
                    this.level.setBlock(mutable, portalBlockState, 18);
                }
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(closestFullPosition.immutable(), 2, 3));
    }

    private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos pos) {
        BlockState blockState = this.level.getBlockState(pos);
        return blockState.canBeReplaced() && blockState.getFluidState().isEmpty();
    }

    private boolean canHostFrame(BlockPos originalPos, BlockPos.MutableBlockPos offsetPos, Direction direction, int offsetScale) {
        Direction clockWiseDirection = direction.getClockWise();
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                offsetPos.setWithOffset(originalPos, direction.getStepX() * i + clockWiseDirection.getStepX() * offsetScale, j, direction.getStepZ() * i + clockWiseDirection.getStepZ() * offsetScale);
                BlockState blockState = this.level.getBlockState(offsetPos);
                if (j < 0 && (blockState.isAir() || !blockState.is(AetherIITags.Blocks.AETHER_PORTAL_SPAWN_WHITELIST))) {
                    return false;
                }
                if (j >= 0 && !this.isOpenSpace(offsetPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOpenSpace(BlockPos pos) {
        return !AetherPortalBlock.isFaceFull(this.level.getBlockState(pos).getShape(this.level, pos), Direction.UP) && this.level.getFluidState(pos).isEmpty();
    }
}
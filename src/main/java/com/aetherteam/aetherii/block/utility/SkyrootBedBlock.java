package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.SkyrootBedBlockEntity;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;

public class SkyrootBedBlock extends BedBlock {
    private static final Map<Direction, VoxelShape> HEAD_SHAPES = Util.make(() -> {
        VoxelShape headboard1 = Block.box(0.0F, 3.0F, 0.0F, 16.0F, 13.0F, 2.0F);
        VoxelShape headboard2 = Block.box(1.0F, 13.0F, 0.0F, 15.0F, 15.0F, 2.0F);
        VoxelShape side1 = Block.box(0.0F, 0.0F, 0.0F, 2.0F, 3.0F, 16.0F);
        VoxelShape side2 = Block.box(14.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);
        return com.aetherteam.aetherii.block.AetherIIShapes.rotateHorizontal(Shapes.or(Block.box(0, 3.0, 0, 16, 9.0, 16), headboard1, headboard2, side1, side2));
    });
    private static final Map<Direction, VoxelShape> FOOT_SHAPES = Util.make(() -> {
        VoxelShape side1 = Block.box(0.0F, 0.0F, 0.0F, 2.0F, 3.0F, 16.0F);
        VoxelShape side2 = Block.box(14.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F);
        return com.aetherteam.aetherii.block.AetherIIShapes.rotateHorizontal(Shapes.or(Block.box(0, 3.0, 0, 16, 9.0, 16), side1, side2));
    });

    public SkyrootBedBlock(DyeColor dyeColor, Properties properties) {
        super(dyeColor, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootBedBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.dimension() != AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
            return super.use(state, level, pos, player, hand, hitResult);
        }
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }
        if (state.getValue(PART) != BedPart.HEAD) {
            pos = pos.relative(getConnectedDirection(state));
            state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof SkyrootBedBlock)) {
                return InteractionResult.CONSUME;
            }
        }
        if (state.getValue(OCCUPIED)) {
            if (!this.kickVillagerOutOfBed(level, pos)) {
                player.displayClientMessage(Component.translatable("block.minecraft.bed.occupied"), true);
            }
        } else {
            BlockPos sleepPos = pos;
            BlockState sleepState = state;
            player.startSleepInBed(pos).ifLeft(problem -> {
                if (problem.getMessage() != null) {
                    player.displayClientMessage(problem.getMessage(), true);
                }
            }).ifRight(unit -> {
                if (player instanceof ServerPlayer) {
                    level.setBlock(sleepPos, sleepState.setValue(OCCUPIED, true), 3);
                }
            });
        }
        return InteractionResult.SUCCESS;
    }

    private boolean kickVillagerOutOfBed(Level level, BlockPos pos) {
        List<Villager> villagers = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (villagers.isEmpty()) {
            return false;
        }
        villagers.get(0).stopSleeping();
        return true;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(PART) == BedPart.HEAD) {
            return HEAD_SHAPES.get(getConnectedDirection(state).getOpposite());
        } else {
            return FOOT_SHAPES.get(getConnectedDirection(state).getOpposite());
        }
    }
}

package com.aetherteam.aetherii.command;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class DungeonBlockLockCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("dungeon_block_lock").requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("from", BlockPosArgument.blockPos()).then(Commands.argument("to", BlockPosArgument.blockPos())
                        .executes((context) -> makeBlocksLocked(context.getSource(), BoundingBox.fromCorners(BlockPosArgument.getLoadedBlockPos(context, "from"), BlockPosArgument.getLoadedBlockPos(context, "to"))))
                        .then(Commands.argument("block", BlockStateArgument.block(buildContext)).executes((context) -> applyBlockToLocks(context.getSource(), BoundingBox.fromCorners(BlockPosArgument.getLoadedBlockPos(context, "from"), BlockPosArgument.getLoadedBlockPos(context, "to")), BlockStateArgument.getBlock(context, "block")))
        ))));
    }

    public static int makeBlocksLocked(CommandSourceStack source, BoundingBox box) {
        ServerLevel serverLevel = source.getLevel();
        for (BlockPos pos : BlockPos.betweenClosed(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ())) {
            BlockState blockState = serverLevel.getBlockState(pos);
            if (!blockState.isAir()) {
                BlockState newState = AetherIIBlocks.LOCKED_BLOCK.get().defaultBlockState().setValue(CopyBlock.EMPTY, false);
                serverLevel.setBlockAndUpdate(pos, newState);
                if (serverLevel.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity) {
                    lockedBlockEntity.setCopyState(blockState);
                }
            }
        }
        return 0;
    }

    public static int applyBlockToLocks(CommandSourceStack source, BoundingBox box, BlockInput block) {
        ServerLevel serverLevel = source.getLevel();
        BlockState copyState = block.getState();
        for (BlockPos pos : BlockPos.betweenClosed(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ())) {
            if (serverLevel.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity) {
                lockedBlockEntity.setCopyState(copyState);
                serverLevel.setBlockAndUpdate(pos, serverLevel.getBlockState(pos).setValue(CopyBlock.EMPTY, false));
            }
        }
        return 0;
    }
}

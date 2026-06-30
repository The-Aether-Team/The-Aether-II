package com.aetherteam.aetherii.command;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.BossDoorwayBlock;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class AetherIIDebugCommand {
    private static final int DEFAULT_COLUMNS = 24;
    private static final int DEFAULT_SPACING = 3;
    private static final int DEFAULT_STATE_PAGE_SIZE = 1024;
    private static final int PLACE_FLAGS = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_SUPPRESS_DROPS;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether_ii_debug").requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(placeCommand("place_blocks", false))
                .then(placeBlockStatesCommand())
                .then(clearCommand("clear_blocks", false))
                .then(clearBlockStatesCommand()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> placeCommand(String name, boolean allStates) {
        return Commands.literal(name)
                .executes(context -> place(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, allStates))
                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                        .executes(context -> place(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, allStates))
                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                .executes(context -> place(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, allStates))
                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                        .executes(context -> place(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), allStates)))));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> clearCommand(String name, boolean allStates) {
        return Commands.literal(name)
                .executes(context -> clear(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, allStates))
                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                        .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, allStates))
                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, allStates))
                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                        .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), allStates)))));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> placeBlockStatesCommand() {
        return Commands.literal("place_block_states")
                .executes(context -> placeStatePage(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, 0))
                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                        .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, 0))
                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, 0))
                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                        .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), 0)))))
                .then(Commands.literal("page")
                        .then(Commands.argument("page", IntegerArgumentType.integer(0))
                                .executes(context -> placeStatePage(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                                        .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                                .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                                        .executes(context -> placeStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), IntegerArgumentType.getInteger(context, "page"))))))));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> clearBlockStatesCommand() {
        return Commands.literal("clear_block_states")
                .executes(context -> clearStatePage(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, 0))
                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                        .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, 0))
                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, 0))
                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                        .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), 0)))))
                .then(Commands.literal("page")
                        .then(Commands.argument("page", IntegerArgumentType.integer(0))
                                .executes(context -> clearStatePage(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                .then(Commands.argument("origin", BlockPosArgument.blockPos())
                                        .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                        .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                                .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, IntegerArgumentType.getInteger(context, "page")))
                                                .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                                        .executes(context -> clearStatePage(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), IntegerArgumentType.getInteger(context, "page")))))))
                .then(Commands.literal("all")
                        .executes(context -> clear(context.getSource(), defaultOrigin(context.getSource()), DEFAULT_COLUMNS, DEFAULT_SPACING, true))
                        .then(Commands.argument("origin", BlockPosArgument.blockPos())
                                .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), DEFAULT_COLUMNS, DEFAULT_SPACING, true))
                                .then(Commands.argument("columns", IntegerArgumentType.integer(1, 128))
                                        .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), DEFAULT_SPACING, true))
                                        .then(Commands.argument("spacing", IntegerArgumentType.integer(1, 16))
                                                .executes(context -> clear(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "origin"), IntegerArgumentType.getInteger(context, "columns"), IntegerArgumentType.getInteger(context, "spacing"), true)))))));
    }

    private static int place(CommandSourceStack source, BlockPos origin, int columns, int spacing, boolean allStates) {
        ServerLevel level = source.getLevel();
        List<BlockState> states = allStates ? allDisplayStates() : defaultDisplayStates();
        BlockState supportState = Blocks.SMOOTH_STONE.defaultBlockState();

        for (int i = 0; i < states.size(); i++) {
            BlockPos pos = gridPos(origin, i, columns, spacing);
            BlockState state = prepareDisplayState(states.get(i));

            level.setBlock(pos.below(), supportState, PLACE_FLAGS);
            level.setBlock(pos, state, PLACE_FLAGS);
            updateCopyBlock(level, pos, state);
        }

        source.sendSuccess(() -> Component.literal("Placed " + states.size() + " Aether II " + (allStates ? "block states" : "blocks") + " at " + formatPos(origin) + "."), true);
        return states.size();
    }

    private static int placeStatePage(CommandSourceStack source, BlockPos origin, int columns, int spacing, int page) {
        ServerLevel level = source.getLevel();
        List<BlockState> allStates = allDisplayStates();
        StatePage statePage = StatePage.of(allStates.size(), page);
        if (statePage.isEmpty()) {
            source.sendFailure(Component.literal("No Aether II block states on page " + page + ". Total states: " + allStates.size() + "."));
            return 0;
        }

        BlockState supportState = Blocks.SMOOTH_STONE.defaultBlockState();
        for (int i = 0; i < statePage.count(); i++) {
            BlockPos pos = gridPos(origin, i, columns, spacing);
            BlockState state = prepareDisplayState(allStates.get(statePage.start() + i));

            level.setBlock(pos.below(), supportState, PLACE_FLAGS);
            level.setBlock(pos, state, PLACE_FLAGS);
            updateCopyBlock(level, pos, state);
        }

        source.sendSuccess(() -> Component.literal("Placed " + statePage.count() + " of " + allStates.size() + " Aether II block states at " + formatPos(origin) + " (page " + page + ", states " + statePage.start() + "-" + (statePage.end() - 1) + ")."), true);
        return statePage.count();
    }

    private static int clear(CommandSourceStack source, BlockPos origin, int columns, int spacing, boolean allStates) {
        ServerLevel level = source.getLevel();
        int count = allStates ? allDisplayStates().size() : defaultDisplayStates().size();
        BlockState air = Blocks.AIR.defaultBlockState();

        for (int i = 0; i < count; i++) {
            BlockPos pos = gridPos(origin, i, columns, spacing);
            level.setBlock(pos, air, PLACE_FLAGS);
            level.setBlock(pos.below(), air, PLACE_FLAGS);
        }

        source.sendSuccess(() -> Component.literal("Cleared " + count + " Aether II debug slots at " + formatPos(origin) + "."), true);
        return count;
    }

    private static int clearStatePage(CommandSourceStack source, BlockPos origin, int columns, int spacing, int page) {
        ServerLevel level = source.getLevel();
        int totalStates = allDisplayStates().size();
        StatePage statePage = StatePage.of(totalStates, page);
        if (statePage.isEmpty()) {
            source.sendFailure(Component.literal("No Aether II block state debug slots on page " + page + ". Total states: " + totalStates + "."));
            return 0;
        }

        BlockState air = Blocks.AIR.defaultBlockState();
        for (int i = 0; i < statePage.count(); i++) {
            BlockPos pos = gridPos(origin, i, columns, spacing);
            level.setBlock(pos, air, PLACE_FLAGS);
            level.setBlock(pos.below(), air, PLACE_FLAGS);
        }

        source.sendSuccess(() -> Component.literal("Cleared " + statePage.count() + " Aether II block state debug slots at " + formatPos(origin) + " (page " + page + ")."), true);
        return statePage.count();
    }

    private static List<BlockState> defaultDisplayStates() {
        List<BlockState> states = new ArrayList<>();
        for (RegistryObject<Block> registryObject : AetherIIBlocks.BLOCKS.getEntries()) {
            states.add(registryObject.get().defaultBlockState());
        }
        return states;
    }

    private static List<BlockState> allDisplayStates() {
        List<BlockState> states = new ArrayList<>();
        for (RegistryObject<Block> registryObject : AetherIIBlocks.BLOCKS.getEntries()) {
            states.addAll(registryObject.get().getStateDefinition().getPossibleStates());
        }
        return states;
    }

    private static BlockState prepareDisplayState(BlockState state) {
        if (state.hasProperty(CopyBlock.WATERLOGGED)) {
            state = state.setValue(CopyBlock.WATERLOGGED, false);
        }
        if (state.hasProperty(CopyBlock.EMPTY)) {
            state = state.setValue(CopyBlock.EMPTY, false);
        }
        if (state.hasProperty(BossDoorwayBlock.INVISIBLE)) {
            state = state.setValue(BossDoorwayBlock.INVISIBLE, false);
        }
        return state;
    }

    private static void updateCopyBlock(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof CopyBlock && level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
            blockEntity.setCopyState(AetherIIBlocks.HOLYSTONE.get().defaultBlockState());
            blockEntity.setChanged();
            level.blockEvent(pos, state.getBlock(), 1, 0);
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        }
    }

    private static BlockPos gridPos(BlockPos origin, int index, int columns, int spacing) {
        return origin.offset((index % columns) * spacing, 0, (index / columns) * spacing);
    }

    private static BlockPos defaultOrigin(CommandSourceStack source) {
        return BlockPos.containing(source.getPosition());
    }

    private static String formatPos(BlockPos pos) {
        return pos.getX() + " " + pos.getY() + " " + pos.getZ();
    }

    private record StatePage(int start, int end) {
        static StatePage of(int total, int page) {
            long start = (long) page * DEFAULT_STATE_PAGE_SIZE;
            if (start >= total) {
                return new StatePage(total, total);
            }
            int end = (int) Math.min(start + DEFAULT_STATE_PAGE_SIZE, total);
            return new StatePage((int) start, end);
        }

        int count() {
            return this.end - this.start;
        }

        boolean isEmpty() {
            return this.count() <= 0;
        }
    }
}

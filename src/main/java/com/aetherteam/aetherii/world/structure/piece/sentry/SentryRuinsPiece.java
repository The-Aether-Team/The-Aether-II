package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.construction.SentryBlock;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;
import java.util.function.Function;

/**
 * Superclass for all Bronze Dungeon structure pieces. This exists to simplify the code.
 */
public abstract class SentryRuinsPiece extends AetherTemplateStructurePiece {

    public static final RuleProcessor CAVE_REPLACEABLE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.ORANGE_CLOUDWOOL.get()), new BlockMatchTest(Blocks.AIR), Blocks.AIR.defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.ORANGE_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, AetherIIBlocks.UNDERSHALE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get()), new BlockMatchTest(Blocks.AIR), Blocks.AIR.defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.LIME_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, AetherIIBlocks.UNDERSHALE_FLAGSTONES.get().defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.CYAN_CLOUDWOOL.get()), new BlockMatchTest(Blocks.AIR), Blocks.AIR.defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.CYAN_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get().defaultBlockState())
    ));

    public static final RuleProcessor SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState()),

            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get().defaultBlockState(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false))
    ));

    public static final List<ProcessorRule> SENTRY_STONE_LIST_REDUCED = ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState()),

            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockStateMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get().defaultBlockState(), 0.02F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false))
    );

    public static final RuleProcessor SENTRY_STONE_REDUCED = new RuleProcessor(SENTRY_STONE_LIST_REDUCED);

    public static final RuleProcessor ROOM_DECORATION_RANDOMIZATION = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.SENTRY_TRAP.get(), 0.8F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.UNDERSHALE_TILE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.SENTRY_CRATE.get(), 0.65F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
    ));

    public static final RuleProcessor STAIRCASE_EXPOSED = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get()), new BlockMatchTest(Blocks.AIR), Blocks.AIR.defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.BROWN_CLOUDWOOL.get()), new RandomBlockMatchTest(Blocks.AIR, 0.25F), Blocks.AIR.defaultBlockState()),
            new ProcessorRule(new BlockMatchTest(AetherIIBlocks.BROWN_CLOUDWOOL.get()), AlwaysTrueTest.INSTANCE, AetherIIBlocks.UNDERSHALE_BRICKS.get().defaultBlockState())

    ));

    public SentryRuinsPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public SentryRuinsPiece(StructurePieceType type, StructureTemplateManager manager, ResourceLocation name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public SentryRuinsPiece(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    protected static ResourceLocation makeLocation(String name) {
        return new ResourceLocation(AetherII.MODID, "sentry_ruins/" + name);
    }
}
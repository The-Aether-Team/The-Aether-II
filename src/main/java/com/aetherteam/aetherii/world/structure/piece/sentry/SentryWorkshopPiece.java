package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.construction.SentryBlock;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.function.Function;

/**
 * Superclass for all Bronze Dungeon structure pieces. This exists to simplify the code.
 */
public abstract class SentryWorkshopPiece extends AetherTemplateStructurePiece {
    private static final AxisAlignedLinearPosTest ON_FLOOR = new AxisAlignedLinearPosTest(1.0F, 0.0F, 0, 1, Direction.Axis.Y);
    // This helps Bronze Dungeons merge more cleanly when they overlap, and blends the tunnels in with the landscape.
    public static final ProtectedBlockProcessor AVOID_DUNGEONS = new ProtectedBlockProcessor(AetherIITags.Blocks.NON_TUNNEL_REPLACEABLE);

    public static final RuleProcessor SENTRY_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_TILE.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_TILE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState()),

            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_FLAGSTONES.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_TILE.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_TILE.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_BRICKS.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_BRICKS.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_BASE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_BASE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_CAPSTONE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_CAPSTONE_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false)),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.UNDERSHALE_PILLAR.get(), 0.05F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_PILLAR.get().defaultBlockState().setValue(SentryBlock.LIT, false))
    ));

    public static final RuleProcessor ROOM_DECORATION_RANDOMIZATION = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.SENTRY_TRAP.get(), 0.75F), AlwaysTrueTest.INSTANCE, AetherIIBlocks.SENTRY_FLAGSTONES.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherIIBlocks.SENTRY_CRATE.get(), 0.8F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
    ));

    public SentryWorkshopPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public SentryWorkshopPiece(StructurePieceType type, StructureTemplateManager manager, ResourceLocation name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public SentryWorkshopPiece(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    protected static ResourceLocation makeLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_workshop/" + name);
    }
}

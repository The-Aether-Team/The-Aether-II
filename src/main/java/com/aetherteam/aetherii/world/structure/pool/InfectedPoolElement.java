package com.aetherteam.aetherii.world.structure.pool;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import com.aetherteam.aetherii.world.structure.processor.DensityFunctionGradientProcessor;
import com.aetherteam.aetherii.world.structure.type.AetherJigsawStructure;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Based on {@link net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement}
 * Used primarily for deleting Structure Templates that ended up generating in the void in rare edge-case scenarios
 */
public class InfectedPoolElement extends StructurePoolElement {
    private static final Comparator<StructureTemplate.JigsawBlockInfo> HIGHEST_SELECTION_PRIORITY_FIRST = Comparator.comparingInt(StructureTemplate.JigsawBlockInfo::selectionPriority).reversed();
    private static final Codec<Either<Identifier, StructureTemplate>> TEMPLATE_CODEC = Codec.of(
            InfectedPoolElement::encodeTemplate, Identifier.CODEC.map(Either::left)
    );
    public static final MapCodec<InfectedPoolElement> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    templateCodec(),
                    processorsCodec(),
                    projectionCodec(),
                    overrideLiquidSettingsCodec(),
                    Codec.INT.fieldOf("discard_below_y").forGetter(structure -> structure.discardBelowY),
                    Codec.INT.fieldOf("discard_above_y").forGetter(structure -> structure.discardAboveY),
                    Codec.BOOL.fieldOf("replace_air").forGetter(structure -> structure.replaceAir)
            ).apply(instance, InfectedPoolElement::new)
    );
    protected final Either<Identifier, StructureTemplate> template;
    protected final Holder<StructureProcessorList> processors;
    protected final Optional<LiquidSettings> overrideLiquidSettings;
    protected final int discardBelowY;
    protected final int discardAboveY;
    protected final boolean replaceAir;

    private static <T> DataResult<T> encodeTemplate(Either<Identifier, StructureTemplate> template, DynamicOps<T> ops, T prefix) {
        Optional<Identifier> optional = template.left();
        return optional.isEmpty()
                ? DataResult.error(() -> "Can not serialize a runtime pool element")
                : Identifier.CODEC.encode(optional.get(), ops, prefix);
    }

    protected static <E extends InfectedPoolElement> RecordCodecBuilder<E, Holder<StructureProcessorList>> processorsCodec() {
        return StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(codec -> codec.processors);
    }

    protected static <E extends InfectedPoolElement> RecordCodecBuilder<E, Optional<LiquidSettings>> overrideLiquidSettingsCodec() {
        return LiquidSettings.CODEC.optionalFieldOf("override_liquid_settings").forGetter((codec) -> codec.overrideLiquidSettings);
    }

    protected static <E extends InfectedPoolElement> RecordCodecBuilder<E, Either<Identifier, StructureTemplate>> templateCodec() {
        return TEMPLATE_CODEC.fieldOf("location").forGetter(codec -> codec.template);
    }

    public InfectedPoolElement(Either<Identifier, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, int discardBelowY, int discardAboveY, boolean replaceAir) {
        super(projection);
        this.template = template;
        this.processors = processors;
        this.overrideLiquidSettings = overrideLiquidSettings;
        this.discardBelowY = discardBelowY;
        this.discardAboveY = discardAboveY;
        this.replaceAir = replaceAir;
    }

    @Override
    public Vec3i getSize(StructureTemplateManager templateManager, Rotation rotation) {
        StructureTemplate template = this.getTemplate(templateManager);
        return template.getSize(rotation);
    }

    private StructureTemplate getTemplate(StructureTemplateManager templateManager) {
        return this.template.map(templateManager::getOrCreate, Function.identity());
    }

    public List<StructureTemplate.StructureBlockInfo> getDataMarkers(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, boolean relativePosition) {
        StructureTemplate structuretemplate = this.getTemplate(templateManager);
        List<StructureTemplate.StructureBlockInfo> listFilter = structuretemplate.filterBlocks(
                pos, new StructurePlaceSettings().setRotation(rotation), Blocks.STRUCTURE_BLOCK, relativePosition
        );
        List<StructureTemplate.StructureBlockInfo> listInfo = Lists.newArrayList();

        for(StructureTemplate.StructureBlockInfo structureBlockInfo : listFilter) {
            CompoundTag compoundtag = structureBlockInfo.nbt();
            if (compoundtag != null) {
                StructureMode mode = StructureMode.valueOf(compoundtag.getStringOr("mode", StructureMode.SAVE.getSerializedName()));
                if (mode == StructureMode.DATA) {
                    listInfo.add(structureBlockInfo);
                }
            }
        }

        return listInfo;
    }

    @Override
    public List<StructureTemplate.JigsawBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, RandomSource random) {
        List<StructureTemplate.JigsawBlockInfo> list = this.getTemplate(templateManager).getJigsaws(pos, rotation);
        Util.shuffle(list, random);
        sortBySelectionPriority(list);
        return list;
    }

    @VisibleForTesting
    static void sortBySelectionPriority(List<StructureTemplate.JigsawBlockInfo> structureBlockInfos) {
        structureBlockInfos.sort(HIGHEST_SELECTION_PRIORITY_FIRST);
    }

    @Override
    public BoundingBox getBoundingBox(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation) {
        StructureTemplate template = this.getTemplate(templateManager);
        return template.getBoundingBox(new StructurePlaceSettings().setRotation(rotation), pos);
    }

    @Override
    public boolean place(StructureTemplateManager templateManager, WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, BlockPos offset, BlockPos pos, Rotation rotation, BoundingBox box, RandomSource random, LiquidSettings liquidSettings, boolean keepJigsaws) {
        StructureTemplate template = this.getTemplate(templateManager);

        StructurePlaceSettings settings = this.getSettings(level, rotation, box, liquidSettings, keepJigsaws, 100);
        if (offset.getY() > this.discardBelowY && offset.getY() < this.discardAboveY) { // Discards a template above/below a certain y level
            if (!template.placeInWorld(level, offset, pos, settings, random, 18)) {
                return false;
            } else {
                for (StructureTemplate.StructureBlockInfo structureBlockInfo : StructureTemplate.processBlockInfos(
                        level, offset, pos, settings, this.getDataMarkers(templateManager, offset, rotation, false))) {
                    this.handleDataMarker(level, structureBlockInfo, offset, rotation, random, box);
                }
                return true;
            }
        }
        return false;
    }

    protected StructurePlaceSettings getSettings(WorldGenLevel level, Rotation rotation, BoundingBox boundingBox, LiquidSettings liquidSettings, boolean offset, int y) {
        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setBoundingBox(boundingBox);
        settings.setRotation(rotation);
        settings.setKnownShape(true);
        settings.setIgnoreEntities(false);
        settings.addProcessor(new DensityFunctionGradientProcessor(AetherIIBlocks.GUARDIAN_WOOD.get().defaultBlockState(), AetherIIBlocks.INFECTED_WOOD.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(level.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION), AetherIIDensityFunctions.DUNGEONS_INFECTED_BLOCKS), true, 100, -50));
        settings.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        if (replaceAir) { // Vanilla uses two separate Pool Element Types to achieve this, it has been turned into a boolean for code efficiency purposes
            settings.addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }
        settings.setLiquidSettings(this.overrideLiquidSettings.orElse(liquidSettings));
        settings.setFinalizeEntities(true);
        if (!offset) {
            settings.addProcessor(JigsawReplacementProcessor.INSTANCE);
        }

        this.processors.value().list().forEach(settings::addProcessor);
        this.getProjection().getProcessors().forEach(settings::addProcessor);
        return settings;
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return AetherIIPoolElementTypes.INFECTED.get();
    }

    @Override
    public String toString() {
        return "Aether[" + this.template + "]";
    }
}
package com.aetherteam.aetherii.world.structure.pool;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaluton;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Based on {@link net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement}
 * Used primarily for deleting Structure Templates that ended up generating in the void in rare edge-case scenarios
 */
public class AetherPoolElement extends StructurePoolElement {
    private static final Comparator<StructureTemplate.JigsawBlockInfo> HIGHEST_SELECTION_PRIORITY_FIRST = Comparator.comparingInt(StructureTemplate.JigsawBlockInfo::selectionPriority).reversed();
    private static final Codec<Either<Identifier, StructureTemplate>> TEMPLATE_CODEC = Codec.of(
            AetherPoolElement::encodeTemplate, Identifier.CODEC.map(Either::left)
    );
    public static final MapCodec<AetherPoolElement> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    templateCodec(),
                    processorsCodec(),
                    projectionCodec(),
                    overrideLiquidSettingsCodec(),
                    Codec.INT.fieldOf("discard_below_y").forGetter(structure -> structure.discardBelowY),
                    Codec.INT.fieldOf("discard_above_y").forGetter(structure -> structure.discardAboveY),
                    Codec.BOOL.fieldOf("replace_air").forGetter(structure -> structure.replaceAir)
            ).apply(instance, AetherPoolElement::new)
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

    protected static <E extends AetherPoolElement> RecordCodecBuilder<E, Holder<StructureProcessorList>> processorsCodec() {
        return StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(codec -> codec.processors);
    }

    protected static <E extends AetherPoolElement> RecordCodecBuilder<E, Optional<LiquidSettings>> overrideLiquidSettingsCodec() {
        return LiquidSettings.CODEC.optionalFieldOf("override_liquid_settings").forGetter((codec) -> codec.overrideLiquidSettings);
    }

    protected static <E extends AetherPoolElement> RecordCodecBuilder<E, Either<Identifier, StructureTemplate>> templateCodec() {
        return TEMPLATE_CODEC.fieldOf("location").forGetter(codec -> codec.template);
    }

    public AetherPoolElement(Either<Identifier, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, int discardBelowY, int discardAboveY, boolean replaceAir) {
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

    public void handleDataMarker(ServerLevelAccessor level, StructureTemplate.StructureBlockInfo dataMarker, BlockPos pos, Rotation rotation, RandomSource random, BoundingBox chunkBB) {
        assert dataMarker.nbt() != null;
        if (dataMarker.nbt().getStringOr("metadata", "").equals("Cockatrice") && !level.getBlockState(pos).isAir()) {
            Cockatrice cockatrice = new Cockatrice(AetherIIEntityTypes.COCKATRICE.get(), level.getLevel());
            cockatrice.setPos(Vec3.atBottomCenterOf(pos));
            cockatrice.setPersistenceRequired();
            level.addFreshEntity(cockatrice);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
       }
        if (dataMarker.nbt().getStringOr("metadata", "").equals("Library Taluton") && !level.getBlockState(pos).isAir()) {
            ArkeniumTaluton arkeniumTaluton = new ArkeniumTaluton(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), level.getLevel());
            arkeniumTaluton.setPos(Vec3.atBottomCenterOf(pos));
            arkeniumTaluton.setPersistenceRequired();
            level.addFreshEntity(arkeniumTaluton);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
        if (dataMarker.nbt().getStringOr("metadata", "").equals("Mineshaft Taluton") && !level.getBlockState(pos).isAir()) {
            if (random.nextFloat() < 0.5F) {
                ArkeniumTaluton arkeniumTaluton = new ArkeniumTaluton(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), level.getLevel());
                arkeniumTaluton.setPos(Vec3.atBottomCenterOf(pos));
                arkeniumTaluton.setPersistenceRequired();
                level.addFreshEntity(arkeniumTaluton);

            } else {
                GravititeTaluton gravititeTaluton = new GravititeTaluton(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), level.getLevel());
                gravititeTaluton.setPos(Vec3.atBottomCenterOf(pos));
                gravititeTaluton.setPersistenceRequired();
                level.addFreshEntity(gravititeTaluton);
            }
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
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
        StructurePlaceSettings settings = this.getSettings(rotation, box, liquidSettings, keepJigsaws);
        if (offset.getY() > this.discardBelowY && offset.getY() < this.discardAboveY) { // Discards a template above/below a certain y level
            if (!template.placeInWorld(level, offset, pos, settings, random, 18)) {
                return false;
            } else {
                for (StructureTemplate.StructureBlockInfo structureBlockInfo : StructureTemplate.processBlockInfos(
                        level, offset, pos, settings, this.getDataMarkers(templateManager, offset, rotation, false))) {

                    this.handleDataMarker(level, structureBlockInfo, structureBlockInfo.pos(), rotation, random, box);
                }

                return true;
            }
        }
        return false;
    }

    protected StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, LiquidSettings liquidSettings, boolean offset) {
        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setBoundingBox(boundingBox);
        settings.setRotation(rotation);
        settings.setKnownShape(true);
        settings.setIgnoreEntities(false);
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
        return AetherIIPoolElementTypes.AETHER.get();
    }

    @Override
    public String toString() {
        return "Single[" + this.template + "]";
    }
}
package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class MimicContainerProcessor extends StructureProcessor {
    public final double probability;

    public static final MapCodec<MimicContainerProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.DOUBLE.fieldOf("probability").forGetter(codec -> codec.probability)
            ).apply(instance, MimicContainerProcessor::new)
    );

    public MimicContainerProcessor(double probability) {
        this.probability = probability;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = RandomSource.create(Mth.getSeed(blockInfo.pos()));

        if (blockInfo.state().is(AetherIITags.Blocks.MIMIC_CONTAINERS)) {
            if (random.nextDouble() <= this.probability) {
                CompoundTag tag = blockInfo.nbt();
                if (tag != null) {
                    DataComponentMap oldMap = tag.read("components", DataComponentMap.CODEC).orElse(DataComponentMap.EMPTY);
                    DataComponentMap newMap = DataComponentMap.builder().addAll(oldMap).set(AetherIIDataComponents.MIMIC, true).build();
                    blockInfo.nbt().store("components", DataComponentMap.CODEC, newMap);
                }
            }
        }
        return super.process(level, offset, pos, blockInfo, relativeBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.MIMIC_CONTAINER.get();
    }
}
package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class MimicChestProcessor extends StructureProcessor {
    public static final MimicChestProcessor INSTANCE = new MimicChestProcessor();

    public static final MapCodec<MimicChestProcessor> CODEC = MapCodec.unit(MimicChestProcessor.INSTANCE);

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (blockInfo.state().is(AetherIITags.Blocks.MIMIC_CONTAINERS)) {
            if (settings.getRandom(pos).nextDouble() < 0.35) {
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
        return AetherIIStructureProcessorTypes.MIMIC_CHEST.get();
    }
}

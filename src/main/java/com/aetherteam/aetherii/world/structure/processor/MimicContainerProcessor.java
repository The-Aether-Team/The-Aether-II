package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherIITags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
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
    public static final MimicContainerProcessor INSTANCE = new MimicContainerProcessor();

    public static final MapCodec<MimicContainerProcessor> CODEC = MapCodec.unit(MimicContainerProcessor.INSTANCE);

    @SuppressWarnings("deprecation")
    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = RandomSource.create(Mth.getSeed(blockInfo.pos()));

        if (blockInfo.state().is(AetherIITags.Blocks.MIMIC_CONTAINERS)) {
            if (random.nextDouble() <= 0.3) {
                CompoundTag tag = blockInfo.nbt();
                if (tag != null) {
                    CompoundTag forgeData = tag.getCompound("ForgeData");
                    forgeData.putBoolean("aether_ii:mimic", true);
                    tag.put("ForgeData", forgeData);
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

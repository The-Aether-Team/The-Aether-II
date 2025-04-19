package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class TemplateHeightConditionProcessor extends StructureProcessor {
    private final String inputTemplate;
    private final String outputTemplate;
    public final int height;

    public static final MapCodec<TemplateHeightConditionProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("input_template").forGetter(codec -> codec.inputTemplate),
            Codec.STRING.fieldOf("output_template").forGetter(codec -> codec.outputTemplate),
            Codec.INT.fieldOf("height").forGetter(codec -> codec.height)
            ).apply(instance, TemplateHeightConditionProcessor::new)
    );

    public TemplateHeightConditionProcessor(String inputState, String outputState, int height) {
        this.inputTemplate = inputState;
        this.outputTemplate = outputState;
        this.height = height;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        assert originalBlockInfo.nbt() != null;
        if (height < originalBlockInfo.pos().getY() && originalBlockInfo.state().is(Blocks.JIGSAW) && originalBlockInfo.nbt().getString("target").equals(inputTemplate)) {
            assert modifiedBlockInfo.nbt() != null;
            modifiedBlockInfo.nbt().putString("target", this.outputTemplate);
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), modifiedBlockInfo.state(), modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.TEMPLATE_HEIGHT_CONDITION.get();
    }
}
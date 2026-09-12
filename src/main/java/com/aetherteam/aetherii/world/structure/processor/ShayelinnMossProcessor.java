package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.block.natural.MossFlowersBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

@Deprecated
public class ShayelinnMossProcessor extends StructureProcessor {
    public static final ShayelinnMossProcessor INSTANCE = new ShayelinnMossProcessor();

    public static final MapCodec<ShayelinnMossProcessor> CODEC = MapCodec.unit(ShayelinnMossProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (modifiedBlockInfo.state().is(AetherIIBlocks.BRYALINN_MOSS_BLOCK)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get().defaultBlockState(), modifiedBlockInfo.nbt());
        }
        if (modifiedBlockInfo.state().is(AetherIIBlocks.BRYALINN_MOSS_CARPET)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), AetherIIBlocks.SHAYELINN_MOSS_CARPET.get().defaultBlockState(), modifiedBlockInfo.nbt());
        }
        if (modifiedBlockInfo.state().is(AetherIIBlocks.AETHER_BUSH)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), AetherIIBlocks.GREATBOA_LEAVES.get().defaultBlockState(), modifiedBlockInfo.nbt());
        }
        if (modifiedBlockInfo.state().is(AetherIIBlocks.BRYALINN_MOSS_VINES)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), AetherIIBlocks.SHAYELINN_MOSS_VINES.get().defaultBlockState()
                    .setValue(BottomedVineBlock.UP, originalBlockInfo.state().getValue(BottomedVineBlock.UP))
                    .setValue(BottomedVineBlock.NORTH, originalBlockInfo.state().getValue(BottomedVineBlock.NORTH))
                    .setValue(BottomedVineBlock.EAST, originalBlockInfo.state().getValue(BottomedVineBlock.EAST))
                    .setValue(BottomedVineBlock.SOUTH, originalBlockInfo.state().getValue(BottomedVineBlock.SOUTH))
                    .setValue(BottomedVineBlock.WEST, originalBlockInfo.state().getValue(BottomedVineBlock.WEST))
                    .setValue(BottomedVineBlock.AGE, originalBlockInfo.state().getValue(BottomedVineBlock.AGE)), modifiedBlockInfo.nbt());
        }
        if (modifiedBlockInfo.state().is(AetherIIBlocks.BRYALINN_MOSS_FLOWERS)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), AetherIIBlocks.HOLPUPEA.get().defaultBlockState()
                    .setValue(MossFlowersBlock.FACING, originalBlockInfo.state().getValue(MossFlowersBlock.FACING))
                    .setValue(MossFlowersBlock.AMOUNT, originalBlockInfo.state().getValue(MossFlowersBlock.AMOUNT)), modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.SHAYELINN_MOSS.get();
    }
}
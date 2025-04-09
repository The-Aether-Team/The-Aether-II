package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AetherII.MODID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<ReinforceBlocksProcessor>> REINFORCE_BLOCKS = STRUCTURE_PROCESSOR_TYPES.register("reinforce_blocks", () -> () -> ReinforceBlocksProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<ReinforceBlocksProcessor>> NOISE = STRUCTURE_PROCESSOR_TYPES.register("noise", () -> () -> ReinforceBlocksProcessor.CODEC);
}
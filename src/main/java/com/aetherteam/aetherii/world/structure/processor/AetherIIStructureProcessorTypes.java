package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.dungeon.MimicOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AetherII.MODID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<DensityFunctionProcessor>> DENSITY_FUNCTION = STRUCTURE_PROCESSOR_TYPES.register("density_function", () -> () -> DensityFunctionProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CappedGravityProcessor>> GRAVITY_CAPPED = STRUCTURE_PROCESSOR_TYPES.register("gravity_capped", () -> () -> CappedGravityProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<BossRoomProcessor>> BOSS_ROOM = STRUCTURE_PROCESSOR_TYPES.register("boss_room", () -> () -> BossRoomProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<MimicChestProcessor>> MIMIC_CHEST = STRUCTURE_PROCESSOR_TYPES.register("mimic_chest", () -> () -> MimicChestProcessor.CODEC);
}
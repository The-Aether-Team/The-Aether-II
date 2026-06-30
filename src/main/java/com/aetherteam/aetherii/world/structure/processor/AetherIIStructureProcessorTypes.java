package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AetherII.MODID);

    public static final RegistryObject<StructureProcessorType<ShayelinnMossProcessor>> SHAYELINN_MOSS = STRUCTURE_PROCESSOR_TYPES.register("shayelinn_moss", () -> () -> ShayelinnMossProcessor.CODEC.codec());
    public static final RegistryObject<StructureProcessorType<RemoveInAirProcessor>> REMOVE_IN_AIR = STRUCTURE_PROCESSOR_TYPES.register("remove_in_air", () -> () -> RemoveInAirProcessor.CODEC.codec());
    public static final RegistryObject<StructureProcessorType<DensityFunctionProcessor>> DENSITY_FUNCTION = STRUCTURE_PROCESSOR_TYPES.register("density_function", () -> () -> DensityFunctionProcessor.CODEC.codec());
    public static final RegistryObject<StructureProcessorType<CopyRuleProcessor>> COPY_RULE = STRUCTURE_PROCESSOR_TYPES.register("copy_rule", () -> () -> CopyRuleProcessor.CODEC.codec());
    public static final RegistryObject<StructureProcessorType<BossRoomProcessor>> BOSS_ROOM = STRUCTURE_PROCESSOR_TYPES.register("boss_room", () -> () -> BossRoomProcessor.CODEC.codec());
    public static final RegistryObject<StructureProcessorType<MimicContainerProcessor>> MIMIC_CONTAINER = STRUCTURE_PROCESSOR_TYPES.register("mimic_container", () -> () -> MimicContainerProcessor.CODEC.codec());
}

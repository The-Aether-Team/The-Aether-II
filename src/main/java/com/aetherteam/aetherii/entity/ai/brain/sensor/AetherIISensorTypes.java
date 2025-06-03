package com.aetherteam.aetherii.entity.ai.brain.sensor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.ai.brain.BurrukaiAi;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.brain.TaegoreAi;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISensorTypes {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(BuiltInRegistries.SENSOR_TYPE, AetherII.MODID);

    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> TAEGORE_TEMPTATIONS = SENSOR_TYPES.register("taegore_temptations", () -> new SensorType<>(() -> new TemptingSensor(TaegoreAi.getTemptations())));
    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> BURRUKAI_TEMPTATIONS = SENSOR_TYPES.register("burrukai_temptations", () -> new SensorType<>(() -> new TemptingSensor(BurrukaiAi.getTemptations())));
    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> KIRRID_TEMPTATIONS = SENSOR_TYPES.register("kirrid_temptations", () -> new SensorType<>(() -> new TemptingSensor(KirridAi.getTemptations())));
}

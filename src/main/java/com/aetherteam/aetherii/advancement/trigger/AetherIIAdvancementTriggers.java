package com.aetherteam.aetherii.advancement.trigger;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIAdvancementTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, AetherII.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, IncubationTrigger> INCUBATION_TRIGGER = TRIGGERS.register("incubation_trigger", IncubationTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, OutpostCampfireTrigger> OUTPOST_CAMPFIRE_TRIGGER = TRIGGERS.register("outpost_campfire_trigger", OutpostCampfireTrigger::new);
}
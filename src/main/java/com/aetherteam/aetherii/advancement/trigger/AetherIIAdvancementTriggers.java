package com.aetherteam.aetherii.advancement.trigger;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIAdvancementTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, AetherII.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, OutpostCampfireTrigger> OUTPOST_CAMPFIRE = TRIGGERS.register("outpost_campfire", OutpostCampfireTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, CurrencyTrigger> CURRENCY = TRIGGERS.register("currency", CurrencyTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, IncubationTrigger> INCUBATION = TRIGGERS.register("incubation", IncubationTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, EffectBuildupTrigger> EFFECT_BUILDUP = TRIGGERS.register("dart_effect", EffectBuildupTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, ForgingCharmTrigger> FORGING_CHARM = TRIGGERS.register("forging_charm", ForgingCharmTrigger::new);
}
package com.aetherteam.aetherii.advancement.trigger;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

public class AetherIIAdvancementTriggers {
    public static final TriggerHolder<ItemBreakBlockTrigger> ITEM_BREAK_BLOCK = register(new ItemBreakBlockTrigger());
    public static final TriggerHolder<FallOnGroundTrigger> FALL_ON_GROUND = register(new FallOnGroundTrigger());
    public static final TriggerHolder<OutpostCampfireTrigger> OUTPOST_CAMPFIRE = register(new OutpostCampfireTrigger());
    public static final TriggerHolder<CurrencyTrigger> CURRENCY = register(new CurrencyTrigger());
    public static final TriggerHolder<SleptInBedrollTrigger> SLEPT_IN_BEDROLL = register(new SleptInBedrollTrigger());
    public static final TriggerHolder<IncubationTrigger> INCUBATION = register(new IncubationTrigger());
    public static final TriggerHolder<FeedMoaTrigger> FEED_MOA = register(new FeedMoaTrigger());
    public static final TriggerHolder<EffectBuildupTrigger> EFFECT_BUILDUP = register(new EffectBuildupTrigger());
    public static final TriggerHolder<ForgingCharmTrigger> FORGING_CHARM = register(new ForgingCharmTrigger());

    public static ResourceLocation id(String name) {
        return new ResourceLocation(AetherII.MODID, name);
    }

    public static void init() {
    }

    private static <T extends CriterionTrigger<?>> TriggerHolder<T> register(T trigger) {
        return new TriggerHolder<>(CriteriaTriggers.register(trigger));
    }

    public record TriggerHolder<T extends CriterionTrigger<?>>(T get) {
    }
}

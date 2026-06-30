package com.aetherteam.aetherii.entity.attributes;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.RegistryObject;

public class EffectResistanceAttribute extends RangedAttribute {
    private final RegistryObject<MobEffect> effect;

    public EffectResistanceAttribute(RegistryObject<MobEffect> effect, String descriptionId, double defaultValue, double min, double max) {
        super(descriptionId, defaultValue, min, max);
        this.effect = effect;
    }

    public boolean matches(Holder<MobEffect> effect) {
        return this.effect.getHolder().map(holder -> holder.value() == effect.value()).orElse(false);
    }

    public RegistryObject<MobEffect> getEffect() {
        return this.effect;
    }
}

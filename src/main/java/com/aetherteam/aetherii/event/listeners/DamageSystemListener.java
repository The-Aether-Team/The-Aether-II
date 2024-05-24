package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.DamageSystemHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

public class DamageSystemListener {
    public static void listen(IEventBus bus) {
        bus.addListener(DamageSystemListener::hurtWithDamageTypes);
    }

    public static void hurtWithDamageTypes(LivingHurtEvent event) {
        Entity target = event.getEntity();
        DamageSource source = event.getSource();
        float damage = event.getAmount();
        event.setAmount(DamageSystemHooks.getDamageTypeModifiedValue(target, source, damage));
    }
}

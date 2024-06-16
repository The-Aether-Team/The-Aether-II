package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.DamageSystemHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.ShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class DamageSystemListener {
    public static void listen(IEventBus bus) {
        bus.addListener(DamageSystemListener::criticalHitTracking);
        bus.addListener(DamageSystemListener::hurtWithDamageTypes);
        bus.addListener(DamageSystemListener::applyDamageTypeTooltips);
        bus.addListener(DamageSystemListener::blockIncomingAttack);
        bus.addListener(DamageSystemListener::tickPlayer);
    }

    public static void criticalHitTracking(CriticalHitEvent event) {
        Player player = event.getEntity();
        float modifier = event.getDamageModifier();
        DamageSystemHooks.trackCriticalHitValue(player, modifier);
    }

    public static void hurtWithDamageTypes(LivingHurtEvent event) {
        Entity target = event.getEntity();
        DamageSource source = event.getSource();
        float damage = event.getAmount();
        event.setAmount(DamageSystemHooks.getDamageTypeModifiedValue(target, source, damage));
    }

    public static void applyDamageTypeTooltips(ItemTooltipEvent event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        List<Component> itemTooltips = event.getToolTip();
        DamageSystemHooks.addAbilityTooltips(itemStack, itemTooltips);
        DamageSystemHooks.addDamageTypeTooltips(player, itemTooltips, itemStack);
        DamageSystemHooks.addBonusDamageTypeTooltips(player, itemTooltips, itemStack);
    }

    public static void blockIncomingAttack(ShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource source = event.getDamageSource();
        DamageSystemHooks.buildUpShieldStun(livingEntity, source);
    }

    public static void tickPlayer(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        DamageSystemHooks.restoreShieldStamina(player);
    }
}

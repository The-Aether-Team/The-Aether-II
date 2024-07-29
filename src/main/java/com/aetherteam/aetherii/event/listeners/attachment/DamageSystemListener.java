package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.attachment.DamageSystemHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class DamageSystemListener {
    public static void listen(IEventBus bus) {
        bus.addListener(DamageSystemListener::criticalHitTracking);
        bus.addListener(DamageSystemListener::hurtWithDamageTypes);
        bus.addListener(EventPriority.LOW, DamageSystemListener::applyTooltips);
        bus.addListener(DamageSystemListener::blockIncomingAttack);
        bus.addListener(DamageSystemListener::tickPlayer);
    }

    public static void criticalHitTracking(CriticalHitEvent event) {
        Player player = event.getEntity();
        float modifier = event.getDamageMultiplier();
        DamageSystemHooks.trackCriticalHitValue(player, modifier);
    }

    public static void hurtWithDamageTypes(LivingDamageEvent.Pre event) {
        Entity target = event.getEntity();
        DamageSource source = event.getContainer().getSource();
        float damage = event.getContainer().getNewDamage();
        event.getContainer().setNewDamage(DamageSystemHooks.getDamageTypeModifiedValue(target, source, damage));
    }

    public static void applyTooltips(ItemTooltipEvent event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        List<Component> itemTooltips = event.getToolTip();
        DamageSystemHooks.addAbilityTooltips(player, itemStack, itemTooltips);
        DamageSystemHooks.addDamageTypeTooltips(player, itemTooltips, itemStack);
        DamageSystemHooks.addBonusDamageTypeTooltips(player, itemTooltips, itemStack);
        DamageSystemHooks.addShieldTooltips(itemTooltips, itemStack);
        DamageSystemHooks.addGloveTooltips(player, itemTooltips, itemStack);
        DamageSystemHooks.addReinforcingTooltip(itemStack, itemTooltips);
    }

    public static void blockIncomingAttack(LivingShieldBlockEvent event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSource source = event.getDamageSource();
        DamageSystemHooks.buildUpShieldStun(livingEntity, source);
    }

    public static void tickPlayer(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        DamageSystemHooks.restoreShieldStamina(player);
    }
}

package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.DamageSystemHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class DamageSystemListener {
    public static void listen(IEventBus bus) {
        bus.addListener(DamageSystemListener::hurtWithDamageTypes);
        bus.addListener(DamageSystemListener::applyDamageTypeTooltips);
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
        DamageSystemHooks.addDamageTypeTooltips(player, itemTooltips, itemStack);
    }
}

package com.aetherteam.aetherii.api;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public record AdvancementSoundOverride(int priority, OverrideCheck predicate, Supplier<SoundEvent> sound) {
    /**
     * @return Whether the {@link Advancement} matches this {@link AdvancementSoundOverride}'s predicate
     */
    public boolean matches(ClientAdvancements advancements, AdvancementHolder advancement) {
        return this.predicate.canOverride(advancements, advancement);
    }

    @FunctionalInterface
    public interface OverrideCheck {
        boolean canOverride(ClientAdvancements advancements, AdvancementHolder advancementHolder);
    }
}


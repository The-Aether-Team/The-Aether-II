package com.aetherteam.aetherii.api;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.sounds.SoundEvent;

public record AdvancementSoundOverride(int priority, BiPredicate<? super ServerAdvancementManager, ? super AdvancementHolder> predicate, Supplier<? extends SoundEvent> sound) {
    /**
     * @return Whether the {@link Advancement} matches this {@link AdvancementSoundOverride}'s predicate
     */
    public boolean matches(ServerAdvancementManager advancements, AdvancementHolder advancement) {
        return this.predicate.test(advancements, advancement);
    }
}


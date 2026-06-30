package com.aetherteam.aetherii.item.components;

import net.minecraft.world.item.UseAnim;

import java.util.ArrayList;
import java.util.List;

public final class Consumables {
    public static final Consumable MILK_BUCKET = defaultDrink().build();

    private Consumables() {
    }

    public static Builder defaultFood() {
        return new Builder(UseAnim.EAT, true);
    }

    public static Builder defaultDrink() {
        return new Builder(UseAnim.DRINK, false);
    }

    public static class Builder {
        private float consumeSeconds = 1.6F;
        private UseAnim animation;
        private boolean consumeParticles;
        private final List<Object> effects = new ArrayList<>();

        private Builder(UseAnim animation, boolean consumeParticles) {
            this.animation = animation;
            this.consumeParticles = consumeParticles;
        }

        public Builder consumeSeconds(float consumeSeconds) {
            this.consumeSeconds = consumeSeconds;
            return this;
        }

        public Builder animation(UseAnim animation) {
            this.animation = animation;
            return this;
        }

        public Builder consumeParticles(boolean consumeParticles) {
            this.consumeParticles = consumeParticles;
            return this;
        }

        public Builder onConsume(Object effect) {
            this.effects.add(effect);
            return this;
        }

        public Consumable build() {
            return new Consumable(this.consumeSeconds, this.animation, this.consumeParticles, List.copyOf(this.effects));
        }
    }
}

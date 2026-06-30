package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.UseAnim;

import java.util.List;

public record Consumable(float consumeSeconds, UseAnim animation, boolean consumeParticles, List<Object> effects) {
    public static final Codec<Consumable> CODEC = Codec.unit(new Consumable(1.6F, UseAnim.EAT, true, List.of()));
}

package com.aetherteam.aetherii.api.damage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public record DamageInfliction(Item item, int slashValue, int impactValue, int pierceValue) {
    public static final Codec<DamageInfliction> CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(DamageInfliction::item),
                    Codec.INT.fieldOf("slash_value").forGetter(DamageInfliction::slashValue),
                    Codec.INT.fieldOf("impact_value").forGetter(DamageInfliction::impactValue),
                    Codec.INT.fieldOf("pierce_value").forGetter(DamageInfliction::pierceValue)
            ).apply(in, DamageInfliction::new));
}

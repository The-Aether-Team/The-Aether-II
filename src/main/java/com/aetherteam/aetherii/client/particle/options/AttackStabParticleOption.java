package com.aetherteam.aetherii.client.particle.options;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record AttackStabParticleOption(float shade) implements ParticleOptions {
    public static final MapCodec<AttackStabParticleOption> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.FLOAT.fieldOf("shade").forGetter(AttackStabParticleOption::shade)
    ).apply(instance, AttackStabParticleOption::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AttackStabParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, AttackStabParticleOption::shade,
            AttackStabParticleOption::new);

    @Override
    public ParticleType<?> getType() {
        return AetherIIParticleTypes.STAB_ATTACK.get();
    }
}

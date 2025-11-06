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

public record AttackShockParticleOption(float rotation, float shade) implements ParticleOptions {
    public static final MapCodec<AttackShockParticleOption> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.FLOAT.fieldOf("rotation").forGetter(AttackShockParticleOption::rotation),
            Codec.FLOAT.fieldOf("shade").forGetter(AttackShockParticleOption::shade)
    ).apply(instance, AttackShockParticleOption::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AttackShockParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, AttackShockParticleOption::rotation,
            ByteBufCodecs.FLOAT, AttackShockParticleOption::shade,
            AttackShockParticleOption::new);

    @Override
    public ParticleType<?> getType() {
        return AetherIIParticleTypes.SHOCK_ATTACK.get();
    }
}

package com.aetherteam.aetherii.client.particle.options;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record AttackStabParticleOption(float shade) implements ParticleOptions {
    public static final Codec<AttackStabParticleOption> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.FLOAT.fieldOf("shade").forGetter(AttackStabParticleOption::shade)
    ).apply(instance, AttackStabParticleOption::new));
    public static final ParticleOptions.Deserializer<AttackStabParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public AttackStabParticleOption fromCommand(ParticleType<AttackStabParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            return new AttackStabParticleOption(reader.readFloat());
        }

        @Override
        public AttackStabParticleOption fromNetwork(ParticleType<AttackStabParticleOption> particleType, FriendlyByteBuf buffer) {
            return new AttackStabParticleOption(buffer.readFloat());
        }
    };

    @Override
    public ParticleType<?> getType() {
        return AetherIIParticleTypes.STAB_ATTACK.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.shade);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%.2f", this.shade);
    }
}

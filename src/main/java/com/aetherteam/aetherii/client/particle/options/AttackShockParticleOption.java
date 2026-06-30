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

public record AttackShockParticleOption(float rotation, float shade) implements ParticleOptions {
    public static final Codec<AttackShockParticleOption> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.FLOAT.fieldOf("rotation").forGetter(AttackShockParticleOption::rotation),
            Codec.FLOAT.fieldOf("shade").forGetter(AttackShockParticleOption::shade)
    ).apply(instance, AttackShockParticleOption::new));
    public static final ParticleOptions.Deserializer<AttackShockParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public AttackShockParticleOption fromCommand(ParticleType<AttackShockParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float rotation = reader.readFloat();
            reader.expect(' ');
            float shade = reader.readFloat();
            return new AttackShockParticleOption(rotation, shade);
        }

        @Override
        public AttackShockParticleOption fromNetwork(ParticleType<AttackShockParticleOption> particleType, FriendlyByteBuf buffer) {
            return new AttackShockParticleOption(buffer.readFloat(), buffer.readFloat());
        }
    };

    @Override
    public ParticleType<?> getType() {
        return AetherIIParticleTypes.SHOCK_ATTACK.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.rotation);
        buffer.writeFloat(this.shade);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%.2f %.2f", this.rotation, this.shade);
    }
}

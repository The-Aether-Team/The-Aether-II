package com.aetherteam.aetherii.client.particle.options;

import com.aetherteam.aetherii.util.ARGB;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

public record ColorParticleOption(ParticleType<ColorParticleOption> type, int color) implements ParticleOptions {
    public static ColorParticleOption create(ParticleType<ColorParticleOption> type, int color) {
        return new ColorParticleOption(type, ARGB.alpha(color) == 0 ? ARGB.opaque(color) : color);
    }

    public static Codec<ColorParticleOption> codec(ParticleType<ColorParticleOption> type) {
        return Codec.INT.fieldOf("color").codec().xmap(color -> create(type, color), ColorParticleOption::color);
    }

    public static Deserializer<ColorParticleOption> deserializer() {
        return new Deserializer<>() {
            @Override
            public ColorParticleOption fromCommand(ParticleType<ColorParticleOption> type, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                return create(type, reader.readInt());
            }

            @Override
            public ColorParticleOption fromNetwork(ParticleType<ColorParticleOption> type, FriendlyByteBuf buffer) {
                return create(type, buffer.readInt());
            }
        };
    }

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color);
    }

    @Override
    public String writeToString() {
        return BuiltInRegistries.PARTICLE_TYPE.getKey(this.type) + " " + this.color;
    }

    public float getAlpha() {
        return ARGB.alpha(this.color) / 255.0F;
    }

    public float getRed() {
        return ARGB.red(this.color) / 255.0F;
    }

    public float getGreen() {
        return ARGB.green(this.color) / 255.0F;
    }

    public float getBlue() {
        return ARGB.blue(this.color) / 255.0F;
    }
}

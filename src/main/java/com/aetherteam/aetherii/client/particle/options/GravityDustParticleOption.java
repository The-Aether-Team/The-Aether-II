package com.aetherteam.aetherii.client.particle.options;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.*;
import net.minecraft.network.FriendlyByteBuf;
import org.joml.Vector3f;

import java.util.Locale;

public class GravityDustParticleOption extends DustParticleOptionsBase {
    public static final Codec<GravityDustParticleOption> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("color").forGetter((option) -> option.color),
            Codec.FLOAT.fieldOf("gravity").forGetter(GravityDustParticleOption::getGravity),
            Codec.FLOAT.fieldOf("scale").forGetter(GravityDustParticleOption::getScale)
    ).apply(instance, GravityDustParticleOption::new));
    public static final ParticleOptions.Deserializer<GravityDustParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public GravityDustParticleOption fromCommand(ParticleType<GravityDustParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int color = reader.readInt();
            reader.expect(' ');
            float gravity = reader.readFloat();
            reader.expect(' ');
            float scale = reader.readFloat();
            return new GravityDustParticleOption(color, gravity, scale);
        }

        @Override
        public GravityDustParticleOption fromNetwork(ParticleType<GravityDustParticleOption> particleType, FriendlyByteBuf buffer) {
            return new GravityDustParticleOption(buffer.readInt(), buffer.readFloat(), buffer.readFloat());
        }
    };

    private final int color;
    private final float gravity;

    public GravityDustParticleOption(int color, float gravity, float scale) {
        super(colorFromInt(color), scale);
        this.color = color;
        this.gravity = gravity;
    }

    @Override
    public ParticleType<GravityDustParticleOption> getType() {
        return AetherIIParticleTypes.GRAVITY_DUST.get();
    }

    private static Vector3f colorFromInt(int color) {
        return new Vector3f(
                (float) (color >> 16 & 255) / 255.0F,
                (float) (color >> 8 & 255) / 255.0F,
                (float) (color & 255) / 255.0F
        );
    }

    public float getGravity() {
        return this.gravity;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color);
        buffer.writeFloat(this.gravity);
        buffer.writeFloat(this.scale);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%d %.2f %.2f", this.color, this.gravity, this.scale);
    }
}

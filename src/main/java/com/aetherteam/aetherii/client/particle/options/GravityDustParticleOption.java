package com.aetherteam.aetherii.client.particle.options;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ScalableParticleOptionsBase;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class GravityDustParticleOption extends ScalableParticleOptionsBase {
    public static final MapCodec<GravityDustParticleOption> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter((option) -> option.color),
            Codec.FLOAT.fieldOf("gravity").forGetter(GravityDustParticleOption::getGravity),
            ScalableParticleOptionsBase.SCALE.fieldOf("scale").forGetter(ScalableParticleOptionsBase::getScale)
    ).apply(instance, GravityDustParticleOption::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GravityDustParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, (option) -> option.color,
            ByteBufCodecs.FLOAT, GravityDustParticleOption::getGravity,
            ByteBufCodecs.FLOAT, ScalableParticleOptionsBase::getScale,
            GravityDustParticleOption::new);
    private final int color;
    private final float gravity;

    public GravityDustParticleOption(int color, float gravity, float scale) {
        super(scale);
        this.color = color;
        this.gravity = gravity;
    }

    public ParticleType<GravityDustParticleOption> getType() {
        return AetherIIParticleTypes.GRAVITY_DUST.get();
    }

    public Vector3f getColor() {
        return ARGB.vector3fFromRGB24(this.color);
    }

    public float getGravity() {
        return this.gravity;
    }
}

package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class EffectBuildupPresets {
    public static List<Preset> PRESETS = new ArrayList<>();

    public static Preset VULNERABILITY = register(new Preset(AetherIIEffects.VULNERABILITY, 1000, 0, false, true, false, 2));
    public static Preset WOUND = register(new Preset(AetherIIEffects.WOUND, 1, 0, false, true, false, 2));
    public static Preset STUN = register(new Preset(AetherIIEffects.STUN, 1000, 0, false, true, false, 2));
    public static Preset AMBROSIUM_POISONING = register(new Preset(AetherIIEffects.AMBROSIUM_POISONING, 1000, 0, false, true, false, 2));
    public static Preset FRACTURE = register(new Preset(AetherIIEffects.FRACTURE, 1000, 0, false, true, false, 1));
    public static Preset TOXIN = register(new Preset(AetherIIEffects.TOXIN, 1000, 0, false, true, false, 2));
    public static Preset VENOM = register(new Preset(AetherIIEffects.VENOM, 1000, 0, false, true, false, 2));
    public static Preset CHARGED = register(new Preset(AetherIIEffects.CHARGED, 1000, 0, false, true, false, 2));
    public static Preset WEBBED = register(new Preset(AetherIIEffects.WEBBED, 500, 0, false, true, false, 2));
    public static Preset IMMOLATION = register(new Preset(AetherIIEffects.IMMOLATION, 1000, 0, false, true, false, 2));
    public static Preset FROSTBITE = register(new Preset(AetherIIEffects.FROSTBITE, 1000, 0, false, true, false, 2));
    public static Preset FUNGAL_ROT = register(new Preset(AetherIIEffects.FUNGAL_ROT, 1000, 0, false, true, false, 2));
    public static Preset CRYSTALLIZED = register(new Preset(AetherIIEffects.CRYSTALLIZED, 1000, 0, false, true, false, 2));

    private static Preset register(Preset preset) {
        PRESETS.add(preset);
        return preset;
    }

    public record Preset(Holder<MobEffect> type, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, int buildupReductionRate) {
        public static final Codec<EffectBuildupPresets.Preset> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("type").forGetter(Preset::type),
                Codec.INT.fieldOf("duration").forGetter(Preset::duration),
                Codec.INT.fieldOf("amplifier").forGetter(Preset::amplifier),
                Codec.BOOL.fieldOf("ambient").forGetter(Preset::ambient),
                Codec.BOOL.fieldOf("visible").forGetter(Preset::visible),
                Codec.BOOL.fieldOf("show_icon").forGetter(Preset::showIcon),
                Codec.INT.fieldOf("buildup_reduction_rate").forGetter(Preset::buildupReductionRate)
        ).apply(instance, EffectBuildupPresets.Preset::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, EffectBuildupPresets.Preset> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), Preset::type,
                ByteBufCodecs.INT, Preset::duration,
                ByteBufCodecs.INT, Preset::amplifier,
                ByteBufCodecs.BOOL, Preset::ambient,
                ByteBufCodecs.BOOL, Preset::visible,
                ByteBufCodecs.BOOL, Preset::showIcon,
                ByteBufCodecs.INT, Preset::buildupReductionRate,
                EffectBuildupPresets.Preset::new);

        public MobEffectInstance createMobEffectInstance() {
            return new MobEffectInstance(this.type(), this.duration(), this.amplifier(), this.ambient(), this.visible(), this.showIcon());
        }
    }
}

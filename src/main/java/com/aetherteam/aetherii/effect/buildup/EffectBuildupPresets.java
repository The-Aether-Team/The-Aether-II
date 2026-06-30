package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class EffectBuildupPresets {
    public static List<Preset> PRESETS = new ArrayList<>();

    public static Preset VULNERABILITY = register(new Preset(holder(AetherIIMobEffects.VULNERABILITY), 1000, 0, false, true, false, 2));
    public static Preset WOUND = register(new Preset(holder(AetherIIMobEffects.WOUND), 1, 0, false, true, false, 2));
    public static Preset STUN = register(new Preset(holder(AetherIIMobEffects.STUN), 250, 0, false, true, false, 2));
    public static Preset AMBROSIUM_POISONING = register(new Preset(holder(AetherIIMobEffects.AMBROSIUM_POISONING), 3600, 0, false, true, false, 2));
    public static Preset FRACTURE = register(new Preset(holder(AetherIIMobEffects.FRACTURE), 500, 0, false, true, false, 1));
    public static Preset TOXIN = register(new Preset(holder(AetherIIMobEffects.TOXIN), 600, 0, false, true, false, 2));
    public static Preset VENOM = register(new Preset(holder(AetherIIMobEffects.VENOM), 400, 0, false, true, false, 2));
    public static Preset CHARGED = register(new Preset(holder(AetherIIMobEffects.CHARGED), 1000, 0, false, true, false, 2));
    public static Preset WEBBED = register(new Preset(holder(AetherIIMobEffects.WEBBED), 400, 0, false, true, false, 2));
    public static Preset IMMOLATION = register(new Preset(holder(AetherIIMobEffects.IMMOLATION), 100, 0, false, true, false, 2));
    public static Preset FROSTBITE = register(new Preset(holder(AetherIIMobEffects.FROSTBITE), 600, 0, false, true, false, 2));
    public static Preset FUNGAL_ROT = register(new Preset(holder(AetherIIMobEffects.FUNGAL_ROT), 600, 0, false, true, false, 2));
    public static Preset CRYSTALLIZED = register(new Preset(holder(AetherIIMobEffects.CRYSTALLIZED), 600, 0, false, true, false, 2));

    private static Preset register(Preset preset) {
        PRESETS.add(preset);
        return preset;
    }

    private static Holder<MobEffect> holder(RegistryObject<MobEffect> effect) {
        return effect.getHolder().orElseGet(() -> Holder.direct(effect.get()));
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
        public static final StreamCodec<FriendlyByteBuf, EffectBuildupPresets.Preset> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), Preset::type,
                ByteBufCodecs.INT, Preset::duration,
                ByteBufCodecs.INT, Preset::amplifier,
                ByteBufCodecs.BOOL, Preset::ambient,
                ByteBufCodecs.BOOL, Preset::visible,
                ByteBufCodecs.BOOL, Preset::showIcon,
                ByteBufCodecs.INT, Preset::buildupReductionRate,
                EffectBuildupPresets.Preset::new);

        public MobEffectInstance createMobEffectInstance() {
            return new MobEffectInstance(this.type().value(), this.duration(), this.amplifier(), this.ambient(), this.visible(), this.showIcon());
        }
    }
}

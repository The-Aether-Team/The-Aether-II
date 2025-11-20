package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ButterflyVariant;
import com.aetherteam.aetherii.api.SkyrootLizardVariant;
import com.aetherteam.aetherii.api.SwetVariant;
import com.aetherteam.aetherii.entity.monster.BladeShroomHunter;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class AetherIIDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, AetherII.MODID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Sheepuff.SheepuffColor>> SHEEPUFF_COLOR = ENTITY_DATA_SERIALIZERS.register("sheepuff_color", () -> EntityDataSerializer.forValueType(Sheepuff.SheepuffColor.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Kirrid.KirridColor>>> OPTIONAL_KIRRID_COLOR = ENTITY_DATA_SERIALIZERS.register("optional_kirrid_color", () -> EntityDataSerializer.forValueType(Kirrid.KirridColor.STREAM_CODEC.apply(ByteBufCodecs::optional)));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<SwetVariant>>> SWET_VARIANT = ENTITY_DATA_SERIALIZERS.register("swet_variant", () -> EntityDataSerializer.forValueType(SwetVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<SkyrootLizardVariant>>> SKYROOT_LIZARD_VARIANT = ENTITY_DATA_SERIALIZERS.register("skyroot_lizard_variant", () -> EntityDataSerializer.forValueType(SkyrootLizardVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<ButterflyVariant>>> BUTTERFLY_VARIANT = ENTITY_DATA_SERIALIZERS.register("butterfly_variant", () -> EntityDataSerializer.forValueType(ButterflyVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<BladeShroomHunter.State>> BLADE_SHROOM_HUNTER_STATE = ENTITY_DATA_SERIALIZERS.register("blade_shroom_hunter_state", () -> EntityDataSerializer.forValueType(BladeShroomHunter.State.STREAM_CODEC));
}

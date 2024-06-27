package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.entity.MoaFeatherShape;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, AetherII.MODID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<MoaFeatherShape>>> MOA_FEATHER_SHAPE = ENTITY_DATA_SERIALIZERS.register("moa_feather_shape", () -> EntityDataSerializer.forValueType(MoaFeatherShape.STREAM_CODEC));
}

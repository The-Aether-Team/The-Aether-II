package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class AetherIIDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AetherII.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ReinforcementTier>> REINFORCEMENT_TIER = DATA_COMPONENT_TYPES.register("reinforcement_tier", () -> DataComponentType.<ReinforcementTier>builder().persistent(ReinforcementTier.CODEC).networkSynchronized(ReinforcementTier.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ItemStack>>> CHARMS = DATA_COMPONENT_TYPES.register("charms", () -> DataComponentType.<List<ItemStack>>builder().persistent(ItemStack.OPTIONAL_CODEC.listOf()).networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list())).cacheEncoding().build());
}

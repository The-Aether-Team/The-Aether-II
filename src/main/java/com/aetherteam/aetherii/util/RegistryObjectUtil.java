package com.aetherteam.aetherii.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public final class RegistryObjectUtil {
    private RegistryObjectUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Holder<T> holder(RegistryObject<? extends T> object) {
        Holder<?> holder = object.getHolder().orElse(null);
        return holder != null ? (Holder<T>) holder : Holder.direct(object.get());
    }

    public static Holder<EntityType<?>> entity(RegistryObject<? extends EntityType<?>> object) {
        return RegistryObjectUtil.holder(object);
    }

    public static Holder<MobEffect> effect(RegistryObject<? extends MobEffect> object) {
        return RegistryObjectUtil.holder(object);
    }

    public static Holder<Attribute> attribute(RegistryObject<? extends Attribute> object) {
        return RegistryObjectUtil.holder(object);
    }

    public static Holder<Item> item(RegistryObject<? extends Item> object) {
        return RegistryObjectUtil.holder(object);
    }

    public static Holder<Block> block(RegistryObject<? extends Block> object) {
        return RegistryObjectUtil.holder(object);
    }

    public static ResourceKey<Item> itemKey(RegistryObject<? extends Item> object) {
        return ResourceKey.create(Registries.ITEM, object.getId());
    }

    public static ResourceKey<Block> blockKey(RegistryObject<? extends Block> object) {
        return ResourceKey.create(Registries.BLOCK, object.getId());
    }
}

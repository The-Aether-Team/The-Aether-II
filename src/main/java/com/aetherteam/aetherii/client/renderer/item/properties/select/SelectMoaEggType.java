package com.aetherteam.aetherii.client.renderer.item.properties.select;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SelectMoaEggType {
    class FeatherColor {
        public static final MapCodec<SelectMoaEggType.FeatherColor> MAP_CODEC = MapCodec.unit(new SelectMoaEggType.FeatherColor());
        public static final Codec<Moa.FeatherColor> VALUE_CODEC = Moa.FeatherColor.CODEC;

        @Nullable
        public Moa.FeatherColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.featherColor() : null;
        }

        public float getValue(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            Moa.FeatherColor value = this.get(itemStack, clientLevel, livingEntity, i, ItemDisplayContext.NONE);
            return value != null ? value.ordinal() : 0.0F;
        }

        public Codec<Moa.FeatherColor> valueCodec() {
            return VALUE_CODEC;
        }
    }

    class FeatherShape {
        public static final MapCodec<SelectMoaEggType.FeatherShape> MAP_CODEC = MapCodec.unit(new SelectMoaEggType.FeatherShape());
        public static final Codec<Moa.FeatherShape> VALUE_CODEC = Moa.FeatherShape.CODEC;

        @Nullable
        public Moa.FeatherShape get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.featherShape() : null;
        }

        public float getValue(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            Moa.FeatherShape value = this.get(itemStack, clientLevel, livingEntity, i, ItemDisplayContext.NONE);
            return value != null ? value.ordinal() : 0.0F;
        }

        public Codec<Moa.FeatherShape> valueCodec() {
            return VALUE_CODEC;
        }
    }

    class EyeColor {
        public static final MapCodec<SelectMoaEggType.EyeColor> MAP_CODEC = MapCodec.unit(new SelectMoaEggType.EyeColor());
        public static final Codec<Moa.EyeColor> VALUE_CODEC = Moa.EyeColor.CODEC;

        @Nullable
        public Moa.EyeColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.eyeColor() : null;
        }

        public float getValue(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            Moa.EyeColor value = this.get(itemStack, clientLevel, livingEntity, i, ItemDisplayContext.NONE);
            return value != null ? value.ordinal() : 0.0F;
        }

        public Codec<Moa.EyeColor> valueCodec() {
            return VALUE_CODEC;
        }
    }

    class KeratinColor {
        public static final MapCodec<SelectMoaEggType.KeratinColor> MAP_CODEC = MapCodec.unit(new SelectMoaEggType.KeratinColor());
        public static final Codec<Moa.KeratinColor> VALUE_CODEC = Moa.KeratinColor.CODEC;

        @Nullable
        public Moa.KeratinColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.keratinColor() : null;
        }

        public float getValue(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
            Moa.KeratinColor value = this.get(itemStack, clientLevel, livingEntity, i, ItemDisplayContext.NONE);
            return value != null ? value.ordinal() : 0.0F;
        }

        public Codec<Moa.KeratinColor> valueCodec() {
            return VALUE_CODEC;
        }
    }
}

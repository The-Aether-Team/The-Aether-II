package com.aetherteam.aetherii.client.renderer.item.properties.select;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SelectMoaEggType {
    class FeatherColor implements SelectItemModelProperty<Moa.FeatherColor> {
        public static final SelectItemModelProperty.Type<SelectMoaEggType.FeatherColor, Moa.FeatherColor> TYPE = Type.create(MapCodec.unit(new SelectMoaEggType.FeatherColor()), Moa.FeatherColor.CODEC);

        @Nullable
        @Override
        public Moa.FeatherColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.featherColor() : null;
        }

        @Override
        public Type<? extends SelectItemModelProperty<Moa.FeatherColor>, Moa.FeatherColor> type() {
            return TYPE;
        }

        @Override
        public Codec<Moa.FeatherColor> valueCodec() {
            return Moa.FeatherColor.CODEC;
        }
    }

    class FeatherShape implements SelectItemModelProperty<Moa.FeatherShape> {
        public static final SelectItemModelProperty.Type<SelectMoaEggType.FeatherShape, Moa.FeatherShape> TYPE = Type.create(MapCodec.unit(new SelectMoaEggType.FeatherShape()), Moa.FeatherShape.CODEC);

        @Nullable
        @Override
        public Moa.FeatherShape get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.featherShape() : null;
        }

        @Override
        public Type<? extends SelectItemModelProperty<Moa.FeatherShape>, Moa.FeatherShape> type() {
            return TYPE;
        }

        @Override
        public Codec<Moa.FeatherShape> valueCodec() {
            return Moa.FeatherShape.CODEC;
        }
    }

    class EyeColor implements SelectItemModelProperty<Moa.EyeColor> {
        public static final SelectItemModelProperty.Type<SelectMoaEggType.EyeColor, Moa.EyeColor> TYPE = Type.create(MapCodec.unit(new SelectMoaEggType.EyeColor()), Moa.EyeColor.CODEC);

        @Nullable
        @Override
        public Moa.EyeColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.eyeColor() : null;
        }

        @Override
        public Type<? extends SelectItemModelProperty<Moa.EyeColor>, Moa.EyeColor> type() {
            return TYPE;
        }

        @Override
        public Codec<Moa.EyeColor> valueCodec() {
            return Moa.EyeColor.CODEC;
        }
    }

    class KeratinColor implements SelectItemModelProperty<Moa.KeratinColor> {
        public static final SelectItemModelProperty.Type<SelectMoaEggType.KeratinColor, Moa.KeratinColor> TYPE = Type.create(MapCodec.unit(new SelectMoaEggType.KeratinColor()), Moa.KeratinColor.CODEC);

        @Nullable
        @Override
        public Moa.KeratinColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
            MoaEggType moaEggType = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
            return moaEggType != null ? moaEggType.keratinColor() : null;
        }

        @Override
        public Type<? extends SelectItemModelProperty<Moa.KeratinColor>, Moa.KeratinColor> type() {
            return TYPE;
        }

        @Override
        public Codec<Moa.KeratinColor> valueCodec() {
            return Moa.KeratinColor.CODEC;
        }
    }
}

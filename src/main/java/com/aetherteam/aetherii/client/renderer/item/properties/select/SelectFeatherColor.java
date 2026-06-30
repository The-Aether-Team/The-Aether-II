package com.aetherteam.aetherii.client.renderer.item.properties.select;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SelectFeatherColor {
    public static final MapCodec<SelectFeatherColor> MAP_CODEC = MapCodec.unit(new SelectFeatherColor());
    public static final Codec<Moa.FeatherColor> VALUE_CODEC = Moa.FeatherColor.CODEC;

    @Nullable
    public Moa.FeatherColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        Moa.FeatherColor featherColor = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.FEATHER_COLOR);
        return featherColor != null ? featherColor : Moa.FeatherColor.LIGHT_BLUE;
    }

    public float getValue(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        Moa.FeatherColor featherColor = this.get(itemStack, clientLevel, livingEntity, i, ItemDisplayContext.NONE);
        return featherColor != null ? featherColor.ordinal() : 0.0F;
    }

    public Codec<Moa.FeatherColor> valueCodec() {
        return VALUE_CODEC;
    }
}

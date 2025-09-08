package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SelectFeatherColor implements SelectItemModelProperty<Moa.FeatherColor> {
    public static final SelectItemModelProperty.Type<SelectFeatherColor, Moa.FeatherColor> TYPE = Type.create(MapCodec.unit(new SelectFeatherColor()), Moa.FeatherColor.CODEC);

    @Nullable
    @Override
    public Moa.FeatherColor get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        Moa.FeatherColor featherColor = itemStack.get(AetherIIDataComponents.FEATHER_COLOR);
        return featherColor != null ? featherColor : Moa.FeatherColor.LIGHT_BLUE;
    }

    @Override
    public Type<? extends SelectItemModelProperty<Moa.FeatherColor>, Moa.FeatherColor> type() {
        return TYPE;
    }
}

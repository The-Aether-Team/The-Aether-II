package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SelectMoaEggType implements SelectItemModelProperty<MoaEggType> {
    public static final SelectItemModelProperty.Type<SelectMoaEggType, MoaEggType> TYPE = Type.create(MapCodec.unit(new SelectMoaEggType()), MoaEggType.CODEC);

    @Nullable
    @Override
    public MoaEggType get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        MoaEggType moaEggType = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
        return moaEggType != null ? moaEggType : MoaEggType.defaultType();
    }

    @Override
    public Type<? extends SelectItemModelProperty<MoaEggType>, MoaEggType> type() {
        return TYPE;
    }
}

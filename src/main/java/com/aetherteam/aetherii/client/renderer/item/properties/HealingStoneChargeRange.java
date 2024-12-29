package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HealingStoneChargeRange implements RangeSelectItemModelProperty {
    public static final MapCodec<HealingStoneChargeRange> MAP_CODEC = MapCodec.unit(new HealingStoneChargeRange());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        Integer charge = itemStack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        return charge != null ? charge / 10.0F : 0.0F;
    }

    @Override
    public MapCodec<? extends HealingStoneChargeRange> type() {
        return MAP_CODEC;
    }
}

package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TieredCrossbowPullRange implements RangeSelectItemModelProperty {
    public static final MapCodec<TieredCrossbowPullRange> MAP_CODEC = MapCodec.unit(new TieredCrossbowPullRange());

    public TieredCrossbowPullRange() { }

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        if (livingEntity == null) {
            return 0.0F;
        } else if (TieredCrossbowItem.isCharged(itemStack)) {
            return 0.0F;
        } else {
            int charge = TieredCrossbowItem.getChargeDuration(itemStack, livingEntity);
            return (float) UseDuration.useDuration(itemStack, livingEntity) / (float) charge;
        }
    }

    @Override
    public MapCodec<TieredCrossbowPullRange> type() {
        return MAP_CODEC;
    }
}

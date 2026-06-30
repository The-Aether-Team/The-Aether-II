package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HealingStoneChargeRange {
    public static final MapCodec<HealingStoneChargeRange> MAP_CODEC = MapCodec.unit(new HealingStoneChargeRange());

    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity owner, int i) {
        Integer charge = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.HEALING_STONE_CHARGES);
        return charge != null ? charge / 10.0F : 0.0F;
    }

    public MapCodec<? extends HealingStoneChargeRange> type() {
        return MAP_CODEC;
    }
}

package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ReinforcementTierRange implements RangeSelectItemModelProperty {
    public static final MapCodec<ReinforcementTierRange> MAP_CODEC = MapCodec.unit(new ReinforcementTierRange());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        ReinforcementTier tier = itemStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return tier != null ? tier.getTier() * 0.1F : 0.0F;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}

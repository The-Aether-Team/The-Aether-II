package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.aetherteam.aetherii.entity.CooldownEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record BetterCooldown() implements RangeSelectItemModelProperty {
    public static final MapCodec<BetterCooldown> MAP_CODEC = MapCodec.unit(new BetterCooldown());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int i) {
        if (owner instanceof Player player) {
            return player.getCooldowns().getCooldownPercent(itemStack, 0.0F);
        } else if (owner instanceof CooldownEntity entity) {
            return entity.getCooldowns().getCooldownPercent(itemStack, 0.0F);
        }
        return 0.0F;
    }

    @Override
    public MapCodec<BetterCooldown> type() {
        return MAP_CODEC;
    }
}

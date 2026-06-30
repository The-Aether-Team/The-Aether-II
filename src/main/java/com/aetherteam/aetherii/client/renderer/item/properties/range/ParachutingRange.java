package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ParachutingRange {
    public static final MapCodec<ParachutingRange> MAP_CODEC = MapCodec.unit(new ParachutingRange());

    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity entity, int i) {
        return entity == null ? 0.0F : ItemStack.isSameItem(itemStack, entity.getItemInHand(entity.getUsedItemHand())) && entity.isUsingItem() ? 1.0F : 0.0F;
    }

    public MapCodec<ParachutingRange> type() {
        return MAP_CODEC;
    }
}

package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.weapons.AmberDartsItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DartsLoadedRange {
    public static final MapCodec<DartsLoadedRange> MAP_CODEC = MapCodec.unit(new DartsLoadedRange());

    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity owner, int i) {
        Integer amount = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.DARTS_LOADED);
        return amount != null ? ((float) (AmberDartsItem.FULL_AMOUNT - amount) / AmberDartsItem.FULL_AMOUNT) : 0.0F;
    }

    public MapCodec<? extends DartsLoadedRange> type() {
        return MAP_CODEC;
    }
}

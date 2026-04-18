package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DullAbilityRange implements RangeSelectItemModelProperty {
    public static final MapCodec<DullAbilityRange> MAP_CODEC = MapCodec.unit(new DullAbilityRange());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int i) {
        return owner == null ? 0.0F : owner instanceof Player player && !player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelAbilities().get(itemStack.typeHolder()) ? 1.0F : 0.0F;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}

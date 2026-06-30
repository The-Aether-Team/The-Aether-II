package com.aetherteam.aetherii.client.renderer.item.properties.range;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DullAbilityRange {
    public static final MapCodec<DullAbilityRange> MAP_CODEC = MapCodec.unit(new DullAbilityRange());

    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity owner, int i) {
        if (!(owner instanceof Player player)) {
            return 0.0F;
        }
        Holder<Item> item = itemStack.getItemHolder();
        Boolean canRefuel = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).getCanRefuelAbilities().get(item);
        return Boolean.FALSE.equals(canRefuel) ? 1.0F : 0.0F;
    }

    public MapCodec<DullAbilityRange> type() {
        return MAP_CODEC;
    }
}

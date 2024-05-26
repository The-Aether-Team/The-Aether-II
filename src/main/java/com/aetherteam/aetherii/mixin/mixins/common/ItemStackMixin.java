package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.ShortswordItem;
import com.aetherteam.aetherii.item.combat.SpearItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private final List<UUID> defaultAttributeUUIDs = List.of(ShortswordItem.BASE_SLASH_RANGE_UUID, HammerItem.BASE_SHOCK_RANGE_UUID, SpearItem.BASE_STAB_RADIUS_UUID, SpearItem.BASE_STAB_DISTANCE_UUID);

    @ModifyVariable(method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At("STORE"), ordinal = 0)
    private boolean injected(boolean flag, @Local AttributeModifier attributeModifier) {
        if (this.defaultAttributeUUIDs.contains(attributeModifier.getId())) {
            return true;
        }
        return flag;
    }
}

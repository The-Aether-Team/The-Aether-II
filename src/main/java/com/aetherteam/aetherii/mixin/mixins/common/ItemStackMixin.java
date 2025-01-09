package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Inject(method = "getTooltipLines(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;STORED_ENCHANTMENTS:Lnet/minecraft/core/component/DataComponentType;", shift = At.Shift.BEFORE))
    private void addReinforcementTooltip(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local Consumer<Component> consumer) {
        ItemStack itemStack = (ItemStack) (Object) this;
        itemStack.addToTooltip(AetherIIDataComponents.REINFORCEMENT_TIER, tooltipContext, consumer, tooltipFlag);
    }
}

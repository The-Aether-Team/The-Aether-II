package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Inject(method = "addDetailsToTooltip", at = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;STORED_ENCHANTMENTS:Lnet/minecraft/core/component/DataComponentType;", shift = At.Shift.BEFORE))
    private void addMuralSectionToTooltip(Item.TooltipContext context, TooltipDisplay display, Player player, TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        itemStack.addToTooltip(AetherIIDataComponents.MURAL_SECTION, context, display, builder, tooltipFlag);
    }

    @Inject(method = "addDetailsToTooltip", at = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;JUKEBOX_PLAYABLE:Lnet/minecraft/core/component/DataComponentType;", shift = At.Shift.BEFORE))
    private void addEngravedDiscToTooltip(Item.TooltipContext context, TooltipDisplay display, Player player, TooltipFlag tooltipFlag, Consumer<Component> builder, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        itemStack.addToTooltip(AetherIIDataComponents.ENGRAVED_DISC, context, display, builder, tooltipFlag);
    }

    @Inject(method = "applyDamage(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private void applyDamage(int damage, LivingEntity livingEntity, Consumer<Item> itemConsumer, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        MixinHooks.breakLootItem(itemStack, livingEntity);
    }
}

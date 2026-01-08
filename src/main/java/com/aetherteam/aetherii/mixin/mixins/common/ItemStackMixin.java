package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BrokenStack;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
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
    private void aether$addDetailsToTooltip(Item.TooltipContext context, TooltipDisplay tooltipDisplay, Player player, TooltipFlag tooltipFlag, Consumer<Component> tooltipAdder, CallbackInfo ci, @Local Consumer<Component> consumer) {
        ItemStack itemStack = (ItemStack) (Object) this;
        itemStack.addToTooltip(AetherIIDataComponents.REINFORCEMENT_TIER, context, consumer, tooltipFlag);
        itemStack.addToTooltip(AetherIIDataComponents.MURAL_SECTION, context, consumer, tooltipFlag);
    }

    @Inject(method = "applyDamage(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private void applyDamage(int damage, LivingEntity livingEntity, Consumer<Item> itemConsumer, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (itemStack.is(AetherIITags.Items.UNBREAKABLE_LOOT)) {
            EquipmentSlot slot = livingEntity.getEquipmentSlotForItem(itemStack);
            ItemStack brokenItem = new ItemStack(AetherIIItems.BROKEN_ITEM.get());
            brokenItem.set(AetherIIDataComponents.BROKEN_STACK, new BrokenStack(itemStack.copy()));
            brokenItem.set(DataComponents.ITEM_MODEL, itemStack.get(DataComponents.ITEM_MODEL)); //todo
            brokenItem.set(DataComponents.ITEM_NAME, Component.translatable("item.aether_ii.broken_item", itemStack.get(DataComponents.ITEM_NAME)));
            Integer maxDamage = itemStack.get(DataComponents.MAX_DAMAGE);
            if (maxDamage != null) {
                brokenItem.set(DataComponents.MAX_DAMAGE, maxDamage);
                brokenItem.set(DataComponents.DAMAGE, maxDamage - 1);
            }
            livingEntity.setItemSlot(slot, brokenItem);
        }
    }
}

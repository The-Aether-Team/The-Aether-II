package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponents;
import com.aetherteam.aetherii.item.components.EquipmentSlotGroup;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.components.Tool;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;", ordinal = 1))
    private void hurtAndBreak(int damage, LivingEntity livingEntity, Consumer<?> itemConsumer, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        MixinHooks.breakLootItem(itemStack, livingEntity);
    }

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void getHoverName(CallbackInfoReturnable<Component> cir) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (!itemStack.hasCustomHoverName()) {
            Component itemName = AetherIIDataComponents.get(itemStack, DataComponents.ITEM_NAME);
            if (itemName != null) {
                cir.setReturnValue(itemName);
            }
        }
    }

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void getRarity(CallbackInfoReturnable<Rarity> cir) {
        Rarity rarity = AetherIIDataComponents.get((ItemStack) (Object) this, DataComponents.RARITY);
        if (rarity != null) {
            cir.setReturnValue(rarity);
        }
    }

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void getMaxDamage(CallbackInfoReturnable<Integer> cir) {
        Integer maxDamage = AetherIIDataComponents.get((ItemStack) (Object) this, DataComponents.MAX_DAMAGE);
        if (maxDamage != null && maxDamage > 0) {
            cir.setReturnValue(maxDamage);
        }
    }

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        ItemAttributeModifiers modifiers = AetherIIDataComponents.get((ItemStack) (Object) this, DataComponents.ATTRIBUTE_MODIFIERS);
        if (modifiers != null && !modifiers.modifiers().isEmpty()) {
            Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
            modifiers.forEach(EquipmentSlotGroup.bySlot(slot), (attribute, modifier) -> attributes.put(attribute.value(), modifier));
            cir.setReturnValue(attributes);
        }
    }

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void getDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Tool tool = AetherIIDataComponents.get((ItemStack) (Object) this, DataComponents.TOOL);
        if (tool != null && !tool.rules().isEmpty()) {
            for (Tool.Rule rule : tool.rules()) {
                if (matchesRule(state, rule)) {
                    cir.setReturnValue(rule.speed());
                    return;
                }
            }
            cir.setReturnValue(tool.defaultMiningSpeed());
        }
    }

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void isCorrectToolForDrops(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        Tool tool = AetherIIDataComponents.get((ItemStack) (Object) this, DataComponents.TOOL);
        if (tool != null && !tool.rules().isEmpty()) {
            for (Tool.Rule rule : tool.rules()) {
                if (matchesRule(state, rule)) {
                    cir.setReturnValue(rule.correctForDrops());
                    return;
                }
            }
            cir.setReturnValue(false);
        }
    }

    private static boolean matchesRule(BlockState state, Tool.Rule rule) {
        for (HolderSet<Block> blocks : rule.blocks()) {
            if (blocks.contains(state.getBlock().builtInRegistryHolder())) {
                return true;
            }
        }
        return false;
    }
}

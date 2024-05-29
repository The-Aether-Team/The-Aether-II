package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.ShortswordItem;
import com.aetherteam.aetherii.item.combat.SpearItem;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Unique
    private final List<UUID> defaultAttributeUUIDs = List.of(ShortswordItem.BASE_SWEEP_RANGE_UUID, HammerItem.BASE_SHOCK_RANGE_UUID, SpearItem.BASE_STAB_RADIUS_UUID, SpearItem.BASE_STAB_DISTANCE_UUID);

    @ModifyVariable(method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At("STORE"), ordinal = 0)
    private boolean injected(boolean flag, @Local AttributeModifier attributeModifier) {
        if (this.defaultAttributeUUIDs.contains(attributeModifier.getId())) {
            return true;
        }
        return flag;
    }

    @Inject(method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 7))
    private void getTooltipLines(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, @Local List<MutableComponent> list, @Local Map.Entry<Attribute, AttributeModifier> entry, @Local AttributeModifier attributemodifier, @Local(ordinal = 1) double d1) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (itemStack.is(AetherIITags.Items.UNIQUE_DAMAGE_INFLICTIONS)) {
            list.add(
                    CommonComponents.space().append(Component.translatable(
                            "attribute.modifier.equals." + attributemodifier.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                            Component.translatable(entry.getKey().getDescriptionId())
                    )).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)
            );
        }
    }

    @WrapWithCondition(
            method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/CommonComponents;space()Lnet/minecraft/network/chat/MutableComponent;", shift = At.Shift.BEFORE), to = @At(value = "TAIL")))
    private boolean addIfAllowed(List<E> instance, E e) {
        ItemStack itemStack = (ItemStack) (Object) this;
        return !itemStack.is(AetherIITags.Items.UNIQUE_DAMAGE_INFLICTIONS);
    }
}

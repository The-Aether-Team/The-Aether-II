package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Unique
    private final List<ResourceLocation> defaultAttributeUUIDs = List.of(
            TieredShortswordItem.BASE_SWEEP_RANGE_ID,
            TieredHammerItem.BASE_SHOCK_RANGE_ID,
            TieredSpearItem.BASE_STAB_RADIUS_ID,
            TieredSpearItem.BASE_STAB_DISTANCE_ID,
            TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID
    );

    @ModifyVariable(method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", at = @At("STORE"), ordinal = 0)
    private boolean addModifierTooltip(boolean flag, @Local(argsOnly = true) AttributeModifier attributeModifier) {
        if (this.defaultAttributeUUIDs.contains(attributeModifier.id())) {
            return true;
        }
        return flag;
    }

    @Inject(method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private void getTooltipLines(Consumer<Component> tooltipAdder, Player player, Holder<Attribute> attribute, AttributeModifier modfier, CallbackInfo ci, @Local(ordinal = 1) double d1) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (itemStack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR)) {
            tooltipAdder.accept(CommonComponents.space()
                    .append(Component.translatable("attribute.modifier.equals." + modfier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(attribute.value().getDescriptionId())))
                    .withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)
            );
        }
    }

    @WrapWithCondition(
            method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/CommonComponents;space()Lnet/minecraft/network/chat/MutableComponent;", shift = At.Shift.BEFORE), to = @At(value = "TAIL")))
    private boolean acceptIfAllowed(Consumer<E> instance, E e) {
        ItemStack itemStack = (ItemStack) (Object) this;
        return !itemStack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR);
    }
}

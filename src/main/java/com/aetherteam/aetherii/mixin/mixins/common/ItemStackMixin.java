package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import java.util.Map;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin<E> {
    @Unique
    private static final List<ResourceLocation> DEFAULT_ATTRIBUTE_LOCATIONS = List.of(
            AetherIIItems.BASE_SLASH_DAMAGE_ID,
            AetherIIItems.BASE_IMPACT_DAMAGE_ID,
            AetherIIItems.BASE_PIERCE_DAMAGE_ID,
            TieredShortswordItem.BASE_SWEEP_RANGE_ID,
            TieredHammerItem.BASE_SHOCK_RANGE_ID,
            TieredSpearItem.BASE_STAB_RADIUS_ID,
            TieredSpearItem.BASE_STAB_DISTANCE_ID,
            TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID
    );
    @Unique
    private static final Map<ResourceLocation, Holder<Attribute>> DEFAULT_ATTRIBUTES = Map.ofEntries(
            Map.entry(AetherIIItems.BASE_SLASH_DAMAGE_ID, Attributes.ATTACK_DAMAGE),
            Map.entry(AetherIIItems.BASE_IMPACT_DAMAGE_ID, Attributes.ATTACK_DAMAGE),
            Map.entry(AetherIIItems.BASE_PIERCE_DAMAGE_ID, Attributes.ATTACK_DAMAGE),
            Map.entry(TieredShortswordItem.BASE_SWEEP_RANGE_ID, AetherIIAttributes.SWEEP_RANGE),
            Map.entry(TieredHammerItem.BASE_SHOCK_RANGE_ID, AetherIIAttributes.SHOCK_RANGE),
            Map.entry(TieredSpearItem.BASE_STAB_RADIUS_ID, AetherIIAttributes.STAB_RADIUS),
            Map.entry(TieredSpearItem.BASE_STAB_DISTANCE_ID, AetherIIAttributes.STAB_DISTANCE),
            Map.entry(TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID, AetherIIAttributes.SHIELD_STAMINA_REDUCTION)
    );

    @ModifyVariable(method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", at = @At("STORE"), ordinal = 0)
    private double addModifierTooltip(double d0, @Local(argsOnly = true) Player player, @Local(argsOnly = true) AttributeModifier attributeModifier) {
        if (player != null && DEFAULT_ATTRIBUTE_LOCATIONS.contains(attributeModifier.id())) {
            return d0 + player.getAttributeBaseValue(DEFAULT_ATTRIBUTES.get(attributeModifier.id()));
        }
        return d0;
    }

    @ModifyVariable(method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", at = @At("STORE"), ordinal = 0)
    private boolean addModifierTooltip(boolean flag, @Local(argsOnly = true) AttributeModifier attributeModifier) {
        if (DEFAULT_ATTRIBUTE_LOCATIONS.contains(attributeModifier.id())) {
            return true;
        }
        return flag;
    }

    @Inject(method = "addModifierTooltip(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private void getTooltipLines(Consumer<Component> tooltipAdder, Player player, Holder<Attribute> attribute, AttributeModifier modfier, CallbackInfo ci, @Local(ordinal = 0) boolean flag, @Local(ordinal = 1) double d1) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if (flag && itemStack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR)) {
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
    private boolean acceptIfAllowed(Consumer<E> instance, E e, @Local(ordinal = 0) boolean flag) {
        ItemStack itemStack = (ItemStack) (Object) this;
        return !(flag && itemStack.is(AetherIITags.Items.UNIQUE_TOOLTIP_COLOR));
    }
}

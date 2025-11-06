package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class AccessoryItem extends Item {
    private final AccessoryContainer.SlotType slotType;
    private final Set<ConditionalAttribute> attributes;
    private final Multimap<Holder<Attribute>, AttributeModifier> attributesMap;

    public AccessoryItem(Properties properties, AccessoryContainer.SlotType slotType) {
        super(properties);
        this.slotType = slotType;
        this.attributes = this.gatherAttributes(new HashSet<>());
        Multimap<Holder<Attribute>, AttributeModifier> attributesMap = ArrayListMultimap.create();
        for (ConditionalAttribute attribute : this.attributes) {
            attributesMap.put(attribute.attribute(), attribute.modifier());
        }
        this.attributesMap = attributesMap;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return AccessoryUtil.equip(player, player.getItemInHand(hand), this.getSlotType());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), this.attributesMap, this.getSlotType().name().toLowerCase(Locale.ROOT));
    }

    public void tick(ItemStack stack, LivingEntity wearer) {
        for (ConditionalAttribute entry : this.attributes) {
            AttributeInstance attribute = wearer.getAttribute(entry.attribute());
            if (attribute != null && !attribute.hasModifier(entry.modifier().id()) && entry.condition().test(stack, wearer)) {
                attribute.addTransientModifier(entry.modifier());
            } else if (attribute != null && attribute.hasModifier(entry.modifier().id()) && !entry.condition().test(stack, wearer)) {
                attribute.removeModifier(entry.modifier().id());
            }
        }
    }

    public void onEquip(ItemStack stack, LivingEntity wearer) {

    }

    public void onUnequip(ItemStack stack, LivingEntity wearer) {
        for (ConditionalAttribute entry : this.attributes) {
            AttributeInstance attribute = wearer.getAttribute(entry.attribute());
            if (attribute != null && attribute.hasModifier(entry.modifier().id())) {
                attribute.removeModifier(entry.modifier().id());
            }
        }
    }

    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        return attributes;
    }

    public AccessoryContainer.SlotType getSlotType() {
        return this.slotType;
    }

    public record ConditionalAttribute(Holder<Attribute> attribute, AttributeModifier modifier, BiPredicate<ItemStack, LivingEntity> condition) { }
}

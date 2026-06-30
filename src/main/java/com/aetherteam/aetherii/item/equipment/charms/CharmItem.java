package com.aetherteam.aetherii.item.equipment.charms;

import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import com.aetherteam.aetherii.integration.AttributeTooltipContext;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class CharmItem extends Item {
    private final Charms.Type type;
    private final Charms.Tier tier;
    private final ItemAttributeModifiers.Entry[] charmAttributes;

    public CharmItem(Properties properties, Charms.Type type, Charms.Tier tier, ItemAttributeModifiers.Entry... charmAttributes) {
        super(properties.stacksTo(1));
        this.type = type;
        this.tier = tier;
        this.charmAttributes = charmAttributes;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Charms.createCharmTierComponent(this.tier).append(CommonComponents.SPACE).append(Charms.createCharmTypeComponent(this.type)).withStyle(ChatFormatting.GRAY));

        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);
        for (ItemAttributeModifiers.Entry entry : this.getCharmAttributes()) {
            modifiers.put(entry.attribute(), entry.modifier());
        }
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents::add, AttributeTooltipContext.of(null, level, TooltipDisplay.DEFAULT, tooltipFlag), modifiers, "charms");
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

    public Charms.Type getType() {
        return this.type;
    }

    public Charms.Tier getTier() {
        return this.tier;
    }

    public ItemAttributeModifiers.Entry[] getCharmAttributes() {
        return this.charmAttributes;
    }

    public static void updateItemAttributes(ItemAttributeModifierEvent event) {
        List<Charms.CharmHolder> charmHolders = Charms.getCharmsForItem(event.getItemStack());
        if (charmHolders != null) {
            for (int i = 0; i < charmHolders.size(); i++) {
                Charms.CharmHolder charmHolder = charmHolders.get(i);
                if (charmHolder.getStack().getItem() instanceof CharmItem charmItem) {
                    for (ItemAttributeModifiers.Entry entry : charmItem.getCharmAttributes()) {
                        EquipmentSlot slot = event.getSlotType();
                        if (entry.slot().test(slot)) {
                            event.addModifier(entry.attribute().value(), ItemAttributeModifiers.modifier(EquipmentUtil.getSlotModifierId(ItemAttributeModifiers.id(entry.modifier()), event.getItemStack(), i, slot != null ? slot.name() : "default"), entry.modifier().getAmount(), entry.modifier().getOperation()));
                        }
                    }
                }
            }
        }
    }
}

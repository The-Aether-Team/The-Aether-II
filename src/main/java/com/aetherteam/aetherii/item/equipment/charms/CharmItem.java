package com.aetherteam.aetherii.item.equipment.charms;

import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.item.components.Charms;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);
        for (ItemAttributeModifiers.Entry entry : this.getCharmAttributes()) {
            modifiers.put(entry.attribute(), entry.modifier());
        }
        AccessoryUtil.addAttributeTooltips(stack, tooltipComponents, AttributeTooltipContext.of(null, context, tooltipDisplay, tooltipFlag), modifiers, "charms." + this.type.name().toLowerCase(Locale.ROOT));
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
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
            for (Charms.CharmHolder charmHolder : charmHolders) {
                if (charmHolder.getStack().getItem() instanceof CharmItem charmItem) {
                    for (ItemAttributeModifiers.Entry entry : charmItem.getCharmAttributes()) {
                        event.addModifier(entry.attribute(), entry.modifier(), entry.slot());
                    }
                }
            }
        }
    }
}

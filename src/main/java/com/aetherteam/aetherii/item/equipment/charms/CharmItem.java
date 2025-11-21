package com.aetherteam.aetherii.item.equipment.charms;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;
import java.util.function.Consumer;

public class CharmItem extends Item {
    private final TagKey<Item> applyTag;
    private final ItemAttributeModifiers.Entry[] charmAttributes;

    public CharmItem(Properties properties, TagKey<Item> applyTag, ItemAttributeModifiers.Entry... charmAttributes) {
        super(properties.stacksTo(1));
        this.applyTag = applyTag;
        this.charmAttributes = charmAttributes;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }

    public boolean canBeAdded(ItemStack stack) {
        return stack.is(this.applyTag);
    }

    public ItemAttributeModifiers.Entry[] getCharmAttributes() {
        return this.charmAttributes;
    }

    public static void updateItemAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        List<ItemStack> charms = stack.get(AetherIIDataComponents.CHARMS);
        if (charms != null) {
            for (ItemStack charm : charms) {
                if (charm.getItem() instanceof CharmItem charmItem) {
                    for (ItemAttributeModifiers.Entry entry : charmItem.getCharmAttributes()) {
                        event.addModifier(entry.attribute(), entry.modifier(), entry.slot());
                    }
                }
            }
        }
    }
}

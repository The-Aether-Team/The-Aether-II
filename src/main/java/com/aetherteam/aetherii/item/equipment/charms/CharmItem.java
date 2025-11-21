package com.aetherteam.aetherii.item.equipment.charms;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;

public class CharmItem extends Item {
    private final ItemAttributeModifiers.Entry[] charmAttributes;

    public CharmItem(Properties properties, ItemAttributeModifiers.Entry... charmAttributes) {
        super(properties.stacksTo(1));
        this.charmAttributes = charmAttributes;
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

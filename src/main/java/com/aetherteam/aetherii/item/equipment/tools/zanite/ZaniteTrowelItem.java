package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;

public class ZaniteTrowelItem extends HoeItem implements ZaniteTool {
    public ZaniteTrowelItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.ZANITE, -2.0F, -1.0F)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, tieredItem.getTier().getSpeed()));
        }
        return super.isDamaged(stack);
    }
}

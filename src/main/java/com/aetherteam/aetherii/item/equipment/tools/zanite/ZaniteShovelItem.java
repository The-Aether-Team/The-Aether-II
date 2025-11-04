package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;

public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem(Properties properties) {
        super(AetherIIItemTiers.ZANITE, 1.5F, -3.0F, properties);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, 6.0F));
        return super.isDamaged(stack);
    }
}

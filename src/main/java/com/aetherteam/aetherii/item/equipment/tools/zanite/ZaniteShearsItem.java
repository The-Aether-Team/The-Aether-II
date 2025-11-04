package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;

public class ZaniteShearsItem extends ShearsItem implements ZaniteTool {
    public ZaniteShearsItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, 6.0F));
        return super.isDamaged(stack);
    }
}

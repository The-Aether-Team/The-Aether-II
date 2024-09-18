package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;

public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.ZANITE, 1.5F, -3.0F)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, tieredItem.getTier().getSpeed()));
        }
        return super.isDamaged(stack);
    }
}

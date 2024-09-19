package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;

public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.ZANITE, 1.0F, -2.8F)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, tieredItem.getTier().getSpeed()));
        }
        return super.isDamaged(stack);
    }
}

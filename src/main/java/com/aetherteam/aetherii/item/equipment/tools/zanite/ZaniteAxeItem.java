package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem(Properties properties) {
        super(AetherIIItemTiers.ZANITE, 6.0F, -3.1F, properties);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        float speed = this.getDefaultSpeed(stack, BlockTags.MINEABLE_WITH_AXE);
        if (speed >= 0.0F) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, speed));
        }
        return super.isDamaged(stack);
    }
}

package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;

public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem(Properties properties) {
        super(AetherIIItemTiers.ZANITE, 1.0F, -2.8F, properties);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        float speed = this.getDefaultSpeed(stack, BlockTags.MINEABLE_WITH_PICKAXE);
        if (speed >= 0.0F) {
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, speed));
        }
        return super.isDamaged(stack);
    }
}

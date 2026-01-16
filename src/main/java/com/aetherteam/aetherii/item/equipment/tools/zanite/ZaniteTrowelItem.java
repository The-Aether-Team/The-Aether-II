package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ZaniteTrowelItem extends HoeItem implements ZaniteTool {
    public ZaniteTrowelItem(Properties properties) {
        super(AetherIIItemTiers.ZANITE, -2.0F, -1.0F, properties);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseSpeed(stack.getAttributeModifiers(), stack, 6.0F));
        return super.isDamaged(stack);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}

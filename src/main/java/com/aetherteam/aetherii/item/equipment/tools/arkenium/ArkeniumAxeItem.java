package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArkeniumAxeItem extends AxeItem {
    public ArkeniumAxeItem(Properties properties) {
        super(AetherIIToolMaterials.ARKENIUM, 1.5F, -3.2F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

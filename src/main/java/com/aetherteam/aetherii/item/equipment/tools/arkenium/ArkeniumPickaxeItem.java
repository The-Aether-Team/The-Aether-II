package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArkeniumPickaxeItem extends PickaxeItem {
    public ArkeniumPickaxeItem(Properties properties) {
        super(AetherIIToolMaterials.ARKENIUM, 1, -2.8F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

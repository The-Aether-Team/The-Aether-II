package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class HolystoneAxeItem extends AxeItem implements HolystoneTool {
    public HolystoneAxeItem(Properties properties) {
        super(AetherIIToolMaterials.HOLYSTONE, 1.5F, -3.2F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

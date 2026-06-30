package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.TieredTrowelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArkeniumTrowelItem extends TieredTrowelItem {
    public ArkeniumTrowelItem(Properties properties) {
        super(AetherIIToolMaterials.ARKENIUM, 0.5F, -2.5F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

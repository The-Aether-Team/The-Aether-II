package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArkeniumShovelItem extends ShovelItem {
    public ArkeniumShovelItem(Properties properties) {
        super(AetherIIToolMaterials.ARKENIUM, 1.5F, -3.0F, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}

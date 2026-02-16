package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import net.minecraft.core.Holder;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArkeniumTrowelItem extends HoeItem {
    public ArkeniumTrowelItem(Properties properties) {
        super(AetherIIItemTiers.ARKENIUM, 0.5F, -2.5F, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}

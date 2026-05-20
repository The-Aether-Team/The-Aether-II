package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem(Properties properties) {
        super(AetherIIToolMaterials.HOLYSTONE, 1.5F, -3.0F, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}

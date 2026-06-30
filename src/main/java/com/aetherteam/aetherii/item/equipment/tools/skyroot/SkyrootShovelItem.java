package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class SkyrootShovelItem extends ShovelItem implements SkyrootTool {
    public SkyrootShovelItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, 1.5F, -3.0F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
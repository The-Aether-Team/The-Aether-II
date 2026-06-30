package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;

public class SkyrootPickaxeItem extends PickaxeItem implements SkyrootTool {
    public SkyrootPickaxeItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, 1, -2.8F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

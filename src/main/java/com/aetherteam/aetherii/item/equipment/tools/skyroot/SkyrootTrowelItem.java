package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.TieredTrowelItem;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class SkyrootTrowelItem extends TieredTrowelItem implements SkyrootTool {
    public SkyrootTrowelItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, 0.5F, -2.5F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

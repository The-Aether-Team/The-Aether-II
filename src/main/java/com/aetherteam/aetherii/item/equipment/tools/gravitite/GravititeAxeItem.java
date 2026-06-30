package com.aetherteam.aetherii.item.equipment.tools.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;

public class GravititeAxeItem extends AxeItem implements GravititeTool {
    public GravititeAxeItem(Properties properties) {
        super(AetherIIToolMaterials.GRAVITITE, 1.5F, -3.2F, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.levitateBlock(context)) {
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(context);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

package com.aetherteam.aetherii.item.equipment.tools.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class GravititePickaxeItem extends Item implements GravititeTool {
    public GravititePickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherIIItemTiers.GRAVITITE, 1.0F, -2.8F));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.levitateBlock(context)) {
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(context);
        }
    }
}

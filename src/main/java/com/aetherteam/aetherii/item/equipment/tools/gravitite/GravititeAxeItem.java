package com.aetherteam.aetherii.item.equipment.tools.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititeAxeItem extends AxeItem implements GravititeTool {
    public GravititeAxeItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.GRAVITITE, 5.0F, -3.0F)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.levitateBlock(context)) {
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        } else {
            return super.useOn(context);
        }
    }
}

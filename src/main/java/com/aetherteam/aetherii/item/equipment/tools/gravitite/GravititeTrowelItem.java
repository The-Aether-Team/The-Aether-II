package com.aetherteam.aetherii.item.equipment.tools.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititeTrowelItem extends HoeItem implements GravititeTool {
    public GravititeTrowelItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.GRAVITITE, -3.0F, 0.0F)));
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

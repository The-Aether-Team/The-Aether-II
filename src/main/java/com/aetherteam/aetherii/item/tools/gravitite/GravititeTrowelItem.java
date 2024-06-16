package com.aetherteam.aetherii.item.tools.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititeTrowelItem extends HoeItem implements GravititeTool {
    public GravititeTrowelItem() {
        super(AetherIIItemTiers.GRAVITITE, -3, 0, new Properties());
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

package com.aetherteam.aetherii.item.tools.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititeShovelItem extends ShovelItem implements GravititeTool {
    public GravititeShovelItem() {
        super(AetherIIItemTiers.GRAVITITE, 1.5F, -3.0F, new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!this.levitateBlock(context)) {
            return super.useOn(context);
        } else {
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        }
    }
}

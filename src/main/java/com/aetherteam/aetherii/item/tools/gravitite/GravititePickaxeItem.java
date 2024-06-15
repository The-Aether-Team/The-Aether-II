package com.aetherteam.aetherii.item.tools.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.GravititeTool;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;

public class GravititePickaxeItem extends PickaxeItem implements GravititeTool {
    public GravititePickaxeItem() {
        super(AetherIIItemTiers.GRAVITITE, 1, -2.8F, new Properties());
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

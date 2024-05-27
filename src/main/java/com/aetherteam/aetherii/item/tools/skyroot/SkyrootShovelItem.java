package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItemTiers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;

public class SkyrootShovelItem extends ShovelItem {
    public SkyrootShovelItem() {
        super(AetherIIItemTiers.SKYROOT, 1.5F, -3.0F, new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() != null) {
            pContext.getPlayer().getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.TOXIN, 500);
        }
        return super.useOn(pContext);
    }
}

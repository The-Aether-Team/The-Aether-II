package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tiers;

public class SkyrootTrowelItem extends HoeItem implements SkyrootTool {
    public SkyrootTrowelItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.SKYROOT, 0.0F, -3.0F)));
    }
}

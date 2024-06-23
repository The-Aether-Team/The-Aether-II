package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tiers;

public class SkyrootAxeItem extends AxeItem implements SkyrootTool {
    public SkyrootAxeItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.SKYROOT, 6.0F, -3.2F)));
    }
}

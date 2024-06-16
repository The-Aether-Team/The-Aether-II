package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.HoeItem;

public class SkyrootTrowelItem extends HoeItem implements SkyrootTool {
    public SkyrootTrowelItem() {
        super(AetherIIItemTiers.SKYROOT, 0, -3.0F, new Properties());
    }
}

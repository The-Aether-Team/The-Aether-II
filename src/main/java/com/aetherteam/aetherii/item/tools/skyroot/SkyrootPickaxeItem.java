package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.PickaxeItem;

public class SkyrootPickaxeItem extends PickaxeItem implements SkyrootTool {
    public SkyrootPickaxeItem() {
        super(AetherIIItemTiers.SKYROOT, 1, -2.8F, new Properties());
    }
}

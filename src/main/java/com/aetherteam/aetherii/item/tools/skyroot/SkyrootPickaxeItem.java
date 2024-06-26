package com.aetherteam.aetherii.item.tools.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.PickaxeItem;

public class SkyrootPickaxeItem extends PickaxeItem implements SkyrootTool {
    public SkyrootPickaxeItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.SKYROOT, 1.0F, -2.8F)));
    }
}

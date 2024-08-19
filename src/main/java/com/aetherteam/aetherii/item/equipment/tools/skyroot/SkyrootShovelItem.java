package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.item.ShovelItem;

public class SkyrootShovelItem extends ShovelItem implements SkyrootTool {
    public SkyrootShovelItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.SKYROOT, 1.5F, -3.0F)));
    }
}

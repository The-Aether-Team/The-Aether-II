package com.aetherteam.aetherii.item.equipment.tools.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.SkyrootTool;
import net.minecraft.world.item.Item;

public class SkyrootPickaxeItem extends Item implements SkyrootTool {
    public SkyrootPickaxeItem(Properties properties) {
        super(AetherIIItemTiers.SKYROOT, 1.0F, -2.8F, properties);
    }
}

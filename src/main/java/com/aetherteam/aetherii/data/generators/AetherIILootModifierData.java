package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.loot.modifiers.DoubleDropsModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

public class AetherIILootModifierData extends GlobalLootModifierProvider {
    public AetherIILootModifierData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void start() {
        this.add("double_drops", new DoubleDropsModifier(new LootItemCondition[]{}));
    }
}

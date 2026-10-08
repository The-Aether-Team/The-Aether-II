package com.aetherteam.aetherii.block.natural;

import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.FlowerBlock;

import java.util.List;

public class AetherFlowerBlock extends FlowerBlock {
    public AetherFlowerBlock(Properties properties) {
        super(new SuspiciousStewEffects(List.of()), properties);
    }
}

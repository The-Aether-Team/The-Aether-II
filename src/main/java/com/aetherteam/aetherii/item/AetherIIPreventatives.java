package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;

public class AetherIIPreventatives {
    public static final Map<Holder<MobEffect>, Integer> BANDAGE = Map.ofEntries(
            Map.entry(AetherIIEffects.WOUND, 250),
            Map.entry(AetherIIEffects.FRACTURE, 250)
    );
}

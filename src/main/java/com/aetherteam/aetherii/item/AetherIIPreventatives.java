package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;

public class AetherIIPreventatives {
    public static final Map<Holder<MobEffect>, Integer> BANDAGE = Map.ofEntries(
            Map.entry(AetherIIMobEffects.WOUND, 250),
            Map.entry(AetherIIMobEffects.STUN, 250),
            Map.entry(AetherIIMobEffects.FRACTURE, 250)
    );
    public static final Map<Holder<MobEffect>, Integer> WATER_VIAL = Map.ofEntries(
            Map.entry(AetherIIMobEffects.IMMOLATION, 500)
    );
    public static final Map<Holder<MobEffect>, Integer> ANTITOXIN_VIAL = Map.ofEntries(
            Map.entry(AetherIIMobEffects.TOXIN, 250)
    );
    public static final Map<Holder<MobEffect>, Integer> ANTIVENOM_VIAL = Map.ofEntries(
            Map.entry(AetherIIMobEffects.VENOM, 250)
    );
}
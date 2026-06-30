package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.util.RegistryObjectUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.Map;

public class AetherIIPreventatives {
    public static final Map<Holder<MobEffect>, Integer> BANDAGE = Map.ofEntries(
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.WOUND), 250),
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.STUN), 250),
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.FRACTURE), 250)
    );
    public static final Map<Holder<MobEffect>, Integer> WATER_VIAL = Map.ofEntries(
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.IMMOLATION), 500)
    );
    public static final Map<Holder<MobEffect>, Integer> ANTITOXIN_VIAL = Map.ofEntries(
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.TOXIN), 250)
    );
    public static final Map<Holder<MobEffect>, Integer> ANTIVENOM_VIAL = Map.ofEntries(
            Map.entry(RegistryObjectUtil.effect(AetherIIMobEffects.VENOM), 250)
    );
}

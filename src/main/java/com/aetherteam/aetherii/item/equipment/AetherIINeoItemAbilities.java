package com.aetherteam.aetherii.item.equipment;

import com.google.common.collect.Sets;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AetherIINeoItemAbilities {
    public static final ItemAbility SHORTSWORD_SLASH = ItemAbility.get("aether_ii:shortsword_slash");
    public static final ItemAbility SPEAR_JAB = ItemAbility.get("aether_ii:spear_jab");
    public static final ItemAbility PIKE_STAB = ItemAbility.get("aether_ii:pike_stab");
    public static final ItemAbility HAMMER_SHOCK = ItemAbility.get("aether_ii:hammer_shock");
    public static final Set<ItemAbility> DEFAULT_SHORTSWORD_ACTIONS = of(SHORTSWORD_SLASH);
    public static final Set<ItemAbility> DEFAULT_SPEAR_ACTIONS = of(SPEAR_JAB);
    public static final Set<ItemAbility> DEFAULT_PIKE_ACTIONS = of(PIKE_STAB);
    public static final Set<ItemAbility> DEFAULT_HAMMER_ACTIONS = of(HAMMER_SHOCK);

    private static Set<ItemAbility> of(ItemAbility... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
}
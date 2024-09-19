package com.aetherteam.aetherii.item.equipment;

import com.google.common.collect.Sets;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AetherIINeoItemAbilities {
    public static final ItemAbility SPEAR_DIG = ItemAbility.get("aether_ii:spear_dig");
    public static final ItemAbility HAMMER_DIG = ItemAbility.get("aether_ii:hammer_dig");
    public static final ItemAbility SHORTSWORD_SLASH = ItemAbility.get("aether_ii:shortsword_slash");
    public static final ItemAbility SPEAR_STAB = ItemAbility.get("aether_ii:spear_stab");
    public static final ItemAbility HAMMER_SHOCK = ItemAbility.get("aether_ii:hammer_shock");
    public static final Set<ItemAbility> DEFAULT_SHORTSWORD_ACTIONS = of(ItemAbilities.SWORD_DIG, SHORTSWORD_SLASH);
    public static final Set<ItemAbility> DEFAULT_SPEAR_ACTIONS = of(SPEAR_DIG, SPEAR_STAB);
    public static final Set<ItemAbility> DEFAULT_HAMMER_ACTIONS = of(HAMMER_DIG, HAMMER_SHOCK);

    private static Set<ItemAbility> of(ItemAbility... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
}
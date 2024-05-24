package com.aetherteam.aetherii.item;

import com.google.common.collect.Sets;
import net.neoforged.neoforge.common.ToolAction;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AetherIIToolActions {
    public static final ToolAction SPEAR_DIG = ToolAction.get("aether_ii:spear_dig");
    public static final ToolAction HAMMER_DIG = ToolAction.get("aether_ii:hammer_dig");
    public static final ToolAction SPEAR_STAB = ToolAction.get("aether_ii:spear_stab");
    public static final ToolAction HAMMER_SHOCK = ToolAction.get("aether_ii:hammer_shock");
    public static final Set<ToolAction> DEFAULT_SPEAR_ACTIONS = of(SPEAR_DIG, SPEAR_STAB);
    public static final Set<ToolAction> DEFAULT_HAMMER_ACTIONS = of(HAMMER_DIG, HAMMER_SHOCK);

    private static Set<ToolAction> of(ToolAction... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
}

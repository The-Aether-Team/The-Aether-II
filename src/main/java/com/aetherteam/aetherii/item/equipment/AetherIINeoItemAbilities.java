package com.aetherteam.aetherii.item.equipment;

import com.google.common.collect.Sets;
import net.minecraftforge.common.ToolAction;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AetherIINeoItemAbilities {
    public static final ToolAction SHORTSWORD_SLASH = ToolAction.get("aether_ii:shortsword_slash");
    public static final ToolAction PIKE_STAB = ToolAction.get("aether_ii:pike_stab");
    public static final ToolAction HAMMER_SHOCK = ToolAction.get("aether_ii:hammer_shock");
    public static final Set<ToolAction> DEFAULT_SHORTSWORD_ACTIONS = of(SHORTSWORD_SLASH);
    public static final Set<ToolAction> DEFAULT_PIKE_ACTIONS = of(PIKE_STAB);
    public static final Set<ToolAction> DEFAULT_HAMMER_ACTIONS = of(HAMMER_SHOCK);

    private static Set<ToolAction> of(ToolAction... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
}
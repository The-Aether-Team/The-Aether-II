package com.aetherteam.aetherii.loot;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AetherIILoot {
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);


    public static final ResourceLocation KIRRID_FUR = new ResourceLocation(AetherII.MODID, "entities/sheep/kirrid");
}

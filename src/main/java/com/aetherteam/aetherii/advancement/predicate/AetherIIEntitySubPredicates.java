package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.EntitySubPredicateTypesAccessor;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.advancements.critereon.EntitySubPredicate;

public class AetherIIEntitySubPredicates {
    public static final EntitySubPredicate.Type AND = AndPredicate::fromJson;
    public static final EntitySubPredicate.Type ON_GROUND = (json) -> new OnGroundPredicate();
    public static final EntitySubPredicate.Type ALIVE = (json) -> new AlivePredicate();
    public static final EntitySubPredicate.Type ARMOR_SET = ArmorSetPredicate::fromJson;
    public static final EntitySubPredicate.Type EFFECT_BUILDUP = EffectBuildupPredicate::fromJson;
    public static final EntitySubPredicate.Type KIRRID = KirridPredicate::fromJson;
    public static final EntitySubPredicate.Type SHEEPUFF = SheepuffPredicate::fromJson;

    public static void register() {
        if (EntitySubPredicate.Types.TYPES.containsKey(AetherII.MODID + ":and")) {
            return;
        }

        BiMap<String, EntitySubPredicate.Type> types = HashBiMap.create(EntitySubPredicate.Types.TYPES);
        types.put(AetherII.MODID + ":and", AND);
        types.put(AetherII.MODID + ":on_ground", ON_GROUND);
        types.put(AetherII.MODID + ":alive", ALIVE);
        types.put(AetherII.MODID + ":armor_set", ARMOR_SET);
        types.put(AetherII.MODID + ":effect_buildup", EFFECT_BUILDUP);
        types.put(AetherII.MODID + ":kirrid", KIRRID);
        types.put(AetherII.MODID + ":sheepuff", SHEEPUFF);
        EntitySubPredicateTypesAccessor.aether_ii$setTypes(types);
    }
}

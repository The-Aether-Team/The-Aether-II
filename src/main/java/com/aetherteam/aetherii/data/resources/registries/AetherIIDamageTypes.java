package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AetherIIDamageTypes {
    public static final ResourceKey<DamageType> PLAYER_AOE = createKey("player_aoe");
    public static final ResourceKey<DamageType> PLAYER_AOE_NO_KNOCKBACK = createKey("player_aoe_no_knockback");
    public static final ResourceKey<DamageType> WOUND = createKey("wound");
    public static final ResourceKey<DamageType> FRACTURE = createKey("fracture");
    public static final ResourceKey<DamageType> TOXIN = createKey("toxin");
    public static final ResourceKey<DamageType> VENOM = createKey("venom");
    public static final ResourceKey<DamageType> CHARGED = createKey("charged");
    public static final ResourceKey<DamageType> IMMOLATION = createKey("immolation");
    public static final ResourceKey<DamageType> ALKAHEST = createKey("alkahest");
    public static final ResourceKey<DamageType> SHOCK = createKey("shock");
    public static final ResourceKey<DamageType> CARRION_SPROUT = createKey("carrion_sprout");
    public static final ResourceKey<DamageType> CRUSH = createKey("crush");

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(PLAYER_AOE, new DamageType("player", 0.1F));
        context.register(PLAYER_AOE_NO_KNOCKBACK, new DamageType("player", 0.1F));
        context.register(WOUND, new DamageType("aether_ii.effect.wound", 0.1F));
        context.register(FRACTURE, new DamageType("aether_ii.effect.fracture", 0.1F));
        context.register(TOXIN, new DamageType("aether_ii.effect.toxin", 0.1F));
        context.register(VENOM, new DamageType("aether_ii.effect.venom", 0.1F));
        context.register(CHARGED, new DamageType("aether_ii.effect.charged", 0.1F));
        context.register(IMMOLATION, new DamageType("aether_ii.effect.immolation", 0.1F, DamageEffects.BURNING));
        context.register(ALKAHEST, new DamageType("aether_ii.alkahest", 0.1F));
        context.register(SHOCK, new DamageType("aether_ii.shock", 0.1F));
        context.register(CARRION_SPROUT, new DamageType("aether_ii.carrion_sprout", 0.1F));
        context.register(CRUSH, new DamageType("aether_ii.crush", 0.1F));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static DamageSource playerAoe(Level level, Player player) {
        return entityDamageSource(level, PLAYER_AOE, player);
    }

    public static DamageSource playerAoeNoKnockback(Level level, Player player) {
        return entityDamageSource(level, PLAYER_AOE_NO_KNOCKBACK, player);
    }

    public static DamageSource damageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key));
    }

    public static DamageSource entityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity entity) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key), entity);
    }

    public static DamageSource indirectEntityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity trueSource) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key), source, trueSource);
    }
}

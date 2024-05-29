package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AetherIIDamageTypes {
    public static final ResourceKey<DamageType> PLAYER_AOE = createKey("player_aoe");
    public static final ResourceKey<DamageType> PLAYER_AOE_NO_KNOCKBACK = createKey("player_aoe_no_knockback");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(PLAYER_AOE, new DamageType("player", 0.1F));
        context.register(PLAYER_AOE_NO_KNOCKBACK, new DamageType("player", 0.1F));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AetherII.MODID, name));
    }

    public static DamageSource playerAoe(Level level, Player player) {
        return entityDamageSource(level, PLAYER_AOE, player);
    }

    public static DamageSource playerAoeNoKnockback(Level level, Player player) {
        return entityDamageSource(level, PLAYER_AOE_NO_KNOCKBACK, player);
    }

    public static DamageSource damageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

    public static DamageSource entityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity entity) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), entity);
    }

    public static DamageSource indirectEntityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity trueSource) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, trueSource);
    }
}

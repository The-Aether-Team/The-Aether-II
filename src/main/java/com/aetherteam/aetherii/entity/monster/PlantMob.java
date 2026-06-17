package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.Optional;

public abstract class PlantMob extends PathfinderMob {
    private int chatCooldown;

    public PlantMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getChatCooldown() > 0) {
            this.chatCooldown--;
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        Optional<LivingEntity> damageResult = this.canDamage(source);
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || damageResult.isPresent()) {
            return super.hurtServer(serverLevel, source, amount);
        }
        return false;
    }

    private Optional<LivingEntity> canDamage(DamageSource source) {
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
            if (source.getDirectEntity() instanceof LivingEntity attacker) {
                if (attacker.getMainHandItem().is(AetherIITags.Items.PLANT_DAMAGING_ITEMS)) { // Check for correct tool.
                    return Optional.of(attacker);
                } else {
                    return this.sendInvalidToolMessage(attacker);
                }
            } else if (source.getDirectEntity() instanceof Projectile projectile) {
                if (projectile.getOwner() instanceof LivingEntity attacker) {
                    if (projectile.getType().builtInRegistryHolder().is(AetherIITags.EntityTypes.PLANT_DAMAGING_PROJECTILES)) {
                        return Optional.of(attacker);
                    } else {
                        projectile.setDeltaMovement(projectile.getDeltaMovement().scale(-1));
                        return this.sendInvalidToolMessage(attacker);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Optional<LivingEntity> sendInvalidToolMessage(LivingEntity attacker) {
        if (!this.level().isClientSide() && attacker instanceof Player player) {
            if (this.getChatCooldown() <= 0) {
                player.sendOverlayMessage(Component.translatable("gui.aether_ii.plant.message.attack.invalid")); // Invalid tool.
                this.setChatCooldown(15);
            }
        }
        return Optional.empty();
    }

    public int getChatCooldown() {
        return this.chatCooldown;
    }

    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }
}

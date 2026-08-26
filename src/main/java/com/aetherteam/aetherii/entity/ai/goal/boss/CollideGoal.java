package com.aetherteam.aetherii.entity.ai.goal.boss;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CollideGoal extends Goal {
    private final Slider slider;

    public CollideGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        if (!this.slider.isAwake() || this.slider.isDeadOrDying()) {
            return false;
        }
        return this.slider.attackCooldown() <= 0 || this.slider.getDeltaMovement().length() > 0.08;
    }

    @Override
    public void tick() {
        Vec3 min = new Vec3(this.slider.getBoundingBox().minX - 0.1, this.slider.getBoundingBox().minY - 0.1, this.slider.getBoundingBox().minZ - 0.1);
        Vec3 max = new Vec3(this.slider.getBoundingBox().maxX + 0.1, this.slider.getBoundingBox().maxY + 0.1, this.slider.getBoundingBox().maxZ + 0.1);
        AABB collisionBounds = new AABB(min, max);
        if (this.slider.level() instanceof ServerLevel serverLevel) {
            for (Entity entity : this.slider.level().getEntities(this.slider, collisionBounds)) {
                DamageSource damageSource = AetherIIDamageTypes.entityDamageSource(this.slider.level(), AetherIIDamageTypes.CRUSH, this.slider);
                if (entity instanceof LivingEntity livingEntity && !livingEntity.isInvulnerableTo(serverLevel, damageSource) && entity.hurtServer(serverLevel, damageSource, 6)) {
                    if (!livingEntity.isBlocking()) {
                        livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, this.slider, EffectBuildupPresets.STUN, 150);
                    }

                    if (livingEntity instanceof Player player && player.isBlocking()) {
                        this.slider.level().broadcastEntityEvent(player, (byte) 30);
                    }
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));

                    this.slider.setMoveDelay(this.slider.calculateMoveDelay());
                    this.slider.setAttackCooldown(20);
                    this.slider.setMoveDirection(null);

                    // Stop the Slider's movement.
                    this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
                    this.slider.setDeltaMovement(Vec3.ZERO);
                } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                    entity.setDeltaMovement(this.slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}

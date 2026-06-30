package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SentryCrateMimic extends Mimic {
    public SentryCrateMimic(EntityType<? extends SentryCrateMimic> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 8.0);
    }

    /**
     * @return The type of {@link ParticleOptions} to render when a Mimic is hurt.
     */
    public ParticleOptions getHurtParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, AetherIIBlocks.SENTRY_CRATE.get().defaultBlockState());
    }

    /**
     * Handle sounds when a target is hurt.
     *
     * @param entity The hurt {@link Entity}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (entity instanceof LivingEntity livingEntity) { // Choose between attack or kill sound depending on remaining target health.
            SoundEvent sound = livingEntity.getHealth() <= 0.0 ? AetherIISoundEvents.ENTITY_MIMIC_KILL.get() : AetherIISoundEvents.ENTITY_MIMIC_ATTACK.get();
            this.playSound(sound, 1.0F, this.getVoicePitch());
        }
        return result;
    }
}

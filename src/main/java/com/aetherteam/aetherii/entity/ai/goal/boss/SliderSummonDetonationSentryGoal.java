package com.aetherteam.aetherii.entity.ai.goal.boss;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class SliderSummonDetonationSentryGoal extends Goal {
    private final Slider slider;
    private int spawnDelay;

    public SliderSummonDetonationSentryGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        if (this.slider.isAwake()) {
            LivingEntity target = this.slider.getTarget();
            if (target != null && target.isAlive()) {
                return this.slider.level().getDifficulty() != Difficulty.PEACEFUL;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if (this.slider.level().getRandom().nextInt(150) == 1 && this.slider.getTarget() != null) {
            this.spawnDelay = 20;
        }
        this.spawnDelay--;
        if (this.spawnDelay == 0) {
            this.spawnSentry();
            this.spawnDelay = -1;
        }
        super.tick();
    }

    public void spawnSentry() {
        LivingEntity target = this.slider.getTarget();
        if (target != null && target.isAlive()) {
            Vec3 vec3 = caculateSpawnPos(this.slider.getTarget().position(), this.slider.position());
            Vec3 awayFromTarget = LandRandomPos.getPosAway(this.slider, 16, 8, vec3);

            if (awayFromTarget != null) {
                DetonationSentry detonationSentry = new DetonationSentry(AetherIIEntityTypes.DETONATION_SENTRY.get(), this.slider.level());
                detonationSentry.setPos(awayFromTarget.x(), awayFromTarget.y(), awayFromTarget.z());
                detonationSentry.setDeltaMovement(0.0, 1.0, 0.0);
                detonationSentry.fallDistance = -100.0F;
                detonationSentry.setTarget(this.slider.getTarget());
                this.slider.level().addFreshEntity(detonationSentry);
                //TODO Spawn Sound
                this.slider.level().playSound(slider, slider.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.AMBIENT, 2.0F, 1.0F);
            }
        }
    }

    private Vec3 caculateSpawnPos(Vec3 sliderPos, Vec3 targetPos) {
        if (targetPos == null || sliderPos == null) {
            return null;
        }
        double diffX = sliderPos.x - targetPos.x;
        double diffY = sliderPos.y - targetPos.y;
        double diffZ = sliderPos.z - targetPos.z;


        return this.slider.position().add(diffX, diffY, diffZ);
    }
}
package com.aetherteam.aetherii.entity.ai.goal.boss;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.DetonationSentry;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.Position;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

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
        Position pos = this.slider.position();
        DetonationSentry detonationSentry = new DetonationSentry(AetherIIEntityTypes.DETONATION_SENTRY.get(), this.slider.level());
        detonationSentry.setPos(pos.x(), pos.y() -1, pos.z());
        detonationSentry.setDeltaMovement(0.0, 1.0, 0.0);
        detonationSentry.fallDistance = -100.0F;
        detonationSentry.setTarget(this.slider.getTarget());
        this.slider.level().addFreshEntity(detonationSentry);
        //this.slider.level().playSound(slider, slider.blockPosition(), GenesisSoundEvents.ENTITY_SENTRY_GUARDIAN_SUMMON.get(), SoundSource.AMBIENT, 2.0F, 1.0F);
    }
}
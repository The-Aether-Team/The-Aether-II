package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.Optional;
import java.util.function.Function;

public class KirridRamTarget extends Behavior<Kirrid> {
    private final Function<Kirrid, UniformInt> getTimeBetweenRams;
    private final TargetingConditions ramTargeting;
    private final float speed;
    private final Function<Kirrid, SoundEvent> getImpactSound;
    private int ramTick;
    private int soundTick;

    public KirridRamTarget(
            Function<Kirrid, UniformInt> pGetTimeBetweenRams,
            TargetingConditions pRamTargeting,
            float pSpeed,
            Function<Kirrid, SoundEvent> pGetImpactSound
    ) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), MemoryStatus.REGISTERED), 600);
        this.getTimeBetweenRams = pGetTimeBetweenRams;
        this.ramTargeting = pRamTargeting;
        this.speed = pSpeed;
        this.getImpactSound = pGetImpactSound;
    }

    private Optional<? extends Kirrid> findValidTarget(Kirrid kirrid) {
        return kirrid.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get().findClosest(p_311583_ -> {
            if (p_311583_ instanceof Kirrid otherKirrid && !kirrid.getBrain().hasMemoryValue(MemoryModuleType.RAM_COOLDOWN_TICKS) && !otherKirrid.getBrain().hasMemoryValue(MemoryModuleType.RAM_COOLDOWN_TICKS)) {
                if (otherKirrid.hasPlate() && kirrid.hasPlate()) {
                    return !otherKirrid.isBaby() && !kirrid.isBaby();
                }
            }

            return false;
        }).map(Kirrid.class::cast);
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, Kirrid pOwner) {
        return this.findValidTarget(pOwner).isPresent();
    }

    protected boolean canStillUse(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        if (pEntity.hasPlate() && pEntity.getBrain().hasMemoryValue(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get())) {
            Kirrid kirrid = getTarget(pEntity);
            return kirrid != null && kirrid.isAlive() && pEntity.distanceToSqr(kirrid) < 128;
        }
        return false;
    }

    private Kirrid getTarget(Kirrid pAnimal) {
        return pAnimal.getBrain().getMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get()).get();
    }

    protected void start(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        BlockPos blockpos = pEntity.blockPosition();
        Brain<?> brain = pEntity.getBrain();
        Kirrid animal = this.findValidTarget(pEntity).get();
        pEntity.getBrain().setMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), animal);
        animal.getBrain().setMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), pEntity);

        this.ramTick = 0;
        pLevel.broadcastEntityEvent(pEntity, (byte) 61);
    }

    protected void tick(ServerLevel pLevel, Kirrid pOwner, long pGameTime) {
        Brain<?> brain = pOwner.getBrain();
        Optional<Kirrid> animal = brain.getMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get());

        if (++ramTick >= 80) {
            if (animal.isPresent() && animal.get().isAlive()) {
                Kirrid kirridOther = animal.get();
                if (pOwner.distanceToSqr(kirridOther) < 4) {
                    if (kirridOther.dropPlate()) {
                        this.finishRam(pLevel, pOwner);
                    }
                    if (--this.soundTick <= 0) {
                        pLevel.playSound(null, pOwner, this.getImpactSound.apply(pOwner), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        this.soundTick = 10;
                    }
                } else {
                    pOwner.setSpeedModifier(this.speed);
                    brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(kirridOther, this.speed, 1));
                }
            }
        }
        if (animal.isPresent() && animal.get().isAlive()) {
            pOwner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(animal.get(), true));
        }
    }


    protected void finishRam(ServerLevel pLevel, Kirrid pOwner) {
        pLevel.broadcastEntityEvent(pOwner, (byte) 62);
        pOwner.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getTimeBetweenRams.apply(pOwner).sample(pLevel.random));
        pOwner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get());
    }

    @Override
    protected void stop(ServerLevel pLevel, Kirrid pEntity, long pGameTime) {
        super.stop(pLevel, pEntity, pGameTime);
        this.finishRam(pLevel, pEntity);
    }

    @Override
    protected boolean timedOut(long pGameTime) {
        return false;
    }
}

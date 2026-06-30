package com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class KirridRamOther extends Behavior<Kirrid> {
    private final TargetingConditions ramTargeting = TargetingConditions.forCombat().selector(
            livingEntity -> livingEntity instanceof Kirrid kirrid
                    && !kirrid.isBaby()
                    && !kirrid.getBrain().hasMemoryValue(MemoryModuleType.RAM_COOLDOWN_TICKS)
                    && kirrid.hasPlate()
                    && livingEntity.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox())
    );
    private final float speed;
    private int ramTick;
    private int soundTick;

    public KirridRamOther(float speed) {
        super(ImmutableMap.of(
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, 
                MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, 
                AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), MemoryStatus.REGISTERED
        ), 600);
        this.speed = speed;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Kirrid owner) {
        return this.findValidTarget(serverLevel, owner).isPresent();
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Kirrid owner, long gameTime) {
        if (owner.hasPlate() && owner.getBrain().hasMemoryValue(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get())) {
            Kirrid ramTarget = this.getTarget(owner);
            return ramTarget != null && ramTarget.isAlive() && owner.distanceToSqr(ramTarget) < 128;
        }
        return false;
    }

    private Optional<? extends Kirrid> findValidTarget(ServerLevel serverLevel, Kirrid owner) {
        return owner.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                .flatMap(memory -> memory
                        .findClosest(livingEntity -> this.ramTargeting.test(owner, livingEntity) && this.ramTargeting.test(livingEntity, owner))
                ).map(Kirrid.class::cast);
    }

    private Kirrid getTarget(Kirrid owner) {
        return owner.getBrain().getMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get()).orElse(null);
    }

    @Override
    protected void start(ServerLevel serverLevel, Kirrid owner, long gameTime) {
        Kirrid ramTarget = this.findValidTarget(serverLevel, owner).get();
        owner.getBrain().setMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), ramTarget);
        ramTarget.getBrain().setMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(), owner);

        this.ramTick = 0;
        serverLevel.broadcastEntityEvent(owner, (byte) Kirrid.RAM_START_EVENT);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Kirrid owner, long gameTime) {
        super.stop(serverLevel, owner, gameTime);
        this.finishRam(serverLevel, owner);
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return false;
    }

    @Override
    protected void tick(ServerLevel serverLevel, Kirrid owner, long gameTime) {
        Brain<?> brain = owner.getBrain();
        Optional<Kirrid> target = brain.getMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get());

        if (++this.ramTick >= 80) {
            if (target.isPresent() && target.get().isAlive()) {
                Kirrid ramTarget = target.get();
                if (owner.distanceToSqr(ramTarget) < 4) {
                    if (ramTarget.dropPlate()) {
                        this.finishRam(serverLevel, owner);
                    }
                    if (--this.soundTick <= 0) {
                        serverLevel.playSound(null, owner, AetherIISoundEvents.ENTITY_KIRRID_RAM_IMPACT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                        this.soundTick = 10;
                    }
                } else {
                    owner.setSpeedModifier(this.speed);
                    brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(ramTarget, this.speed, 1));
                }
            }
        }
        if (target.isPresent() && target.get().isAlive()) {
            owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target.get(), true));
        }
    }

    protected void finishRam(ServerLevel serverLevel, Kirrid owner) {
        serverLevel.broadcastEntityEvent(owner, (byte) Kirrid.RAM_STOP_EVENT);
        owner.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, KirridAi.TIME_BETWEEN_RAMS.sample(serverLevel.getRandom()));
        owner.getBrain().eraseMemory(AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get());
//        if (owner.hasPlate()) {
//            this.dropPlate(owner); // TODO WIP ALPHA THINGS
//        }
    }

    protected void dropPlate(Kirrid owner) {
        Vec3 vec3 = owner.position();
        ItemStack itemStack = new ItemStack(AetherIIItems.KIRRID_PLATE.get());
        double dX = Mth.randomBetween(owner.getRandom(), -0.2F, 0.2F);
        double dY = Mth.randomBetween(owner.getRandom(), 0.3F, 0.7F);
        double dZ = Mth.randomBetween(owner.getRandom(), -0.2F, 0.2F);
        ItemEntity itemEntity = new ItemEntity(owner.level(), vec3.x(), vec3.y(), vec3.z(), itemStack, dX, dY, dZ);
        owner.level().addFreshEntity(itemEntity);
    }
}

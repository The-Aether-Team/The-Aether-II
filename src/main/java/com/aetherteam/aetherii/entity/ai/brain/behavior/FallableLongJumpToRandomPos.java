package com.aetherteam.aetherii.entity.ai.brain.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class FallableLongJumpToRandomPos<E extends Mob> extends LongJumpToRandomPos<E> {
    private final Function<E, SoundEvent> getJumpSound;

    public FallableLongJumpToRandomPos(UniformInt pTimeBetweenLongJumps, int pMaxLongJumpHeight, int pMaxLongJumpWidth, float pMaxJumpVelocity, Function<E, SoundEvent> pGetJumpSound, BiPredicate<E, BlockPos> pAcceptableLandingSpo) {
        super(pTimeBetweenLongJumps, pMaxLongJumpHeight, pMaxLongJumpWidth, pMaxJumpVelocity, pGetJumpSound);

        this.getJumpSound = pGetJumpSound;
    }

    protected void tick(ServerLevel pLevel, E pOwner, long pGameTime) {
        if (this.chosenJump != null) {
            if (pGameTime - this.prepareJumpStart >= 40L) {
                pOwner.setYRot(pOwner.yBodyRot);
                pOwner.setDiscardFriction(true);
                double d0 = this.chosenJump.length();
                double d1 = d0 + (double) pOwner.getJumpBoostPower();
                AttributeInstance gravity = pOwner.getAttribute(Attributes.GRAVITY);
                double fallSpeed = gravity.getValue() / Math.min(gravity.getValue() * 1.25F, 0.1); // Entity isn't allowed to fall too slowly from gravity.
                pOwner.setDeltaMovement(this.chosenJump.scale(d1 / d0).scale(fallSpeed));
                pOwner.getBrain().setMemory(MemoryModuleType.LONG_JUMP_MID_JUMP, true);
                pLevel.playSound(null, pOwner, this.getJumpSound.apply(pOwner), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        } else {
            --this.findJumpTries;
            this.pickCandidate(pLevel, pOwner, pGameTime);
        }
    }
}

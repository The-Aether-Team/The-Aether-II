package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.AttachedLivingAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;

public class LeapAtTargetAndAttachGoal extends LeapAtTargetGoal {
    public final Mob mob;

    public LeapAtTargetAndAttachGoal(Mob mob, float yd) {
        super(mob, yd);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return this.mob.getData(AetherIIDataAttachments.ATTACH_LIVING).getAttachingTo() == null && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getData(AetherIIDataAttachments.ATTACH_LIVING).getAttachingTo() == null && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            AttachedLivingAttachment attachment = target.getData(AetherIIDataAttachments.ATTACH_LIVING.get());
            if (this.mob.getBoundingBox().intersects(target.getBoundingBox()) && attachment.getAttachedEntity() == null) {
                target.getData(AetherIIDataAttachments.ATTACH_LIVING.get()).attachEntity(target, this.mob);
            }
        }
    }
}

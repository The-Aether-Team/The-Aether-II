package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.aetherteam.aetherii.entity.projectile.LassoLoop;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public final class LassoThrow {
    private LassoThrow() {
    }

    public static boolean get(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        if (level == null) {
            return false;
        }
        return !level.getEntitiesOfClass(LassoLoop.class, player.getBoundingBox().inflate(64.0), loop -> loop.getOwner() == player).isEmpty()
                || !level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(64.0), mob -> mob.getLeashHolder() == player).isEmpty();
    }
}

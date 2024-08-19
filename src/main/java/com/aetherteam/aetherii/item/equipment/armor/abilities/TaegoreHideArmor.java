package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public interface TaegoreHideArmor {
    static void updateEntityTargeting(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob) {
            if (mob.level() instanceof ServerLevel serverLevel) {
                if (mob.getLastDamageSource() != null && mob.getLastDamageSource().getDirectEntity() instanceof LivingEntity attacker) {
                    if (EquipmentUtil.hasArmorAbility(attacker, AetherIIArmorMaterials.TAEGORE_HIDE)) {
                        boolean foundPanic = false;
                        if (mob.getRandom().nextInt(30) == 0) {
                            for (WrappedGoal goal : mob.goalSelector.getAvailableGoals()) {
                                if (!foundPanic) {
                                    if (goal.getGoal() instanceof PanicGoal panicGoal) {
                                        if (panicGoal.isRunning()) {
                                            panicGoal.stop();
                                            mob.getNavigation().stop();
                                            foundPanic = true;
                                        }
                                    } else if (goal.getGoal() instanceof TemptGoal temptGoal) {
                                        if (temptGoal.isRunning()) {
                                            temptGoal.stop();
                                            mob.getNavigation().stop();
                                            foundPanic = true;
                                        }
                                    }
                                }
                            }
                            if (!foundPanic) {
                                if (mob instanceof PathfinderMob pathfinderMob) {
                                    for (BehaviorControl<?> behavior : pathfinderMob.getBrain().getRunningBehaviors()) {
                                        if (behavior instanceof AnimalPanic<?> animalPanic) {
                                            AnimalPanic<PathfinderMob> pathfinderMobAnimalPanic = (AnimalPanic<PathfinderMob>) animalPanic;
                                            pathfinderMobAnimalPanic.doStop(serverLevel, pathfinderMob, serverLevel.getGameTime());
                                            mob.getNavigation().stop();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

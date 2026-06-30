package com.aetherteam.aetherii.entity.companion;

import com.aetherteam.aetherii.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface Companion<T extends Mob & Companion<T>> {
    /**
     * Discards the companion if its owner can't be found.
     * @param companion The companion entity.
     */
    default void tickCompanion(T companion) {
        if (this.getOwner().isPresent()) {
            Player player = companion.level().getPlayerByUUID(this.getOwner().get().getUUID());
            if (player == null || !player.isAlive()) {
                companion.discard();
            }
        } else {
            companion.discard();
        }
    }

    void onEquip(ItemStack itemStack);
    void onUnequip(ItemStack itemStack);

    Optional<EntityReference<LivingEntity>> getOwner();
    void setOwner(Optional<EntityReference<LivingEntity>> owner);

    ItemStack getItem();
    void setItem(ItemStack stack);

    ItemStack getSummonItem();
}

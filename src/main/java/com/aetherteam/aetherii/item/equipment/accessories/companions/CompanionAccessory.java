package com.aetherteam.aetherii.item.equipment.accessories.companions;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.companion.Companion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public interface CompanionAccessory<T extends Entity> {
    default void equip(ItemStack stack, LivingEntity wearer) {
        if (wearer.level() instanceof ServerLevel serverLevel) {
            Entity entity = this.getCompanionType().create(serverLevel, t -> this.applyCompanionInfo(t, wearer.getUUID()), wearer.blockPosition(), EntitySpawnReason.MOB_SUMMONED, false, false);
            if (entity != null && wearer instanceof Player player) {
                if (entity instanceof Companion<?> companion) {
                    companion.onEquip(stack);
                }
                if (stack.has(DataComponents.CUSTOM_NAME)) {
                    entity.setCustomName(stack.getHoverName().plainCopy());
                }
                serverLevel.addFreshEntityWithPassengers(entity);
                player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).addCompanion(player, entity);
            }
        }
    }

    default void unequip(ItemStack itemStack, LivingEntity wearer) {
        if (wearer instanceof Player player) {
            player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).removeCompanion((entity) -> {
                if (entity.getType() == this.getCompanionType()) {
                    if (entity instanceof Companion<?> companion) {
                        companion.onUnequip(itemStack);
                    }
                    entity.discard();
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * @return The companion {@link EntityType}.
     */
    EntityType<T> getCompanionType();

    default void applyCompanionInfo(T t, UUID owner) {
        if (t instanceof Companion<?> companion) {
            companion.setOwner(Optional.of(new EntityReference<>(owner)));
        }
    }
}

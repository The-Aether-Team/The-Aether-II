package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.Equippable;

public class AetherEquippable {
    public static Equippable moaSaddle() {
        return Equippable.builder(EquipmentSlot.SADDLE)
                .setEquipSound(AetherIISoundEvents.ENTITY_MOA_SADDLE)
                .setAllowedEntities(AetherIIEntityTypes.MOA.get())
                .setEquipOnInteract(true)
                .setCanBeSheared(true)
                .setShearingSound(SoundEvents.SADDLE_UNEQUIP)
                .build();
    }

}

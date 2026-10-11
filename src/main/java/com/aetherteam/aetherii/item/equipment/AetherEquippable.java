package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEquipmentAssets;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.Equippable;

public class AetherEquippable {
    public static Equippable moaSaddle() { //todo saddles can be equipped to non-tamed moas rn oops
        return Equippable.builder(EquipmentSlot.SADDLE)
                .setEquipSound(AetherIISoundEvents.ENTITY_MOA_SADDLE)
                .setAsset(AetherIIEquipmentAssets.MOA_SADDLE)
                .setAllowedEntities(AetherIIEntityTypes.MOA.get())
                .setEquipOnInteract(true)
                .setShearingSound(SoundEvents.SADDLE_UNEQUIP)
                .build();
    }

}

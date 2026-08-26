package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.data.resources.registries.AetherIIEquipmentAssets;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.Accessory;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.accessories.abilities.FreezingAccessory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IcestonePendantItem extends AccessoryItem implements FreezingAccessory {
    public IcestonePendantItem(Properties properties) {
        super(properties.durability(250).component(AetherIIDataComponents.ACCESSORY, new Accessory(AetherIIEquipmentAssets.ICESTONE_PENDANT)), AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public void tick(ItemStack stack, LivingEntity wearer, int slot) {
        if (!wearer.isInLiquid()) {
            this.freezeTick(wearer, stack);
        }
    }
}
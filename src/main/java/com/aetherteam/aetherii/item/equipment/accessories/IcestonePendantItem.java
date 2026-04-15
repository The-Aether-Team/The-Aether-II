package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.accessories.abilities.IcestonePendantAbility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IcestonePendantItem extends AccessoryItem implements IcestonePendantAbility {
    public IcestonePendantItem(Properties properties) {
        super(properties.durability(250), AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public void tick(ItemStack stack, LivingEntity wearer, int slot) {
        if (!wearer.isInFluidType()) {
            this.freezeTick(wearer, stack);
        }
    }
}
package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.accessories.abilities.IcePendantAbility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IcePendantItem extends AccessoryItem implements IcePendantAbility {
    public IcePendantItem(Properties properties) {
        super(properties.durability(250), AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public void tick(ItemStack stack, LivingEntity wearer, int slot) {
        if (!wearer.isInFluidType()) {
            this.freezeTick(wearer, stack);
        }
    }
}
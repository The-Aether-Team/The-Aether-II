package com.aetherteam.aetherii.item.equipment.accessories.companions;

import com.aetherteam.aetherii.entity.companion.CompanionMob;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.accessories.AccessoryItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class CompanionItem<T extends CompanionMob> extends AccessoryItem implements CompanionAccessory<T> {
    private final Supplier<EntityType<T>> companionType;

    public CompanionItem(Supplier<EntityType<T>> companionType, Item.Properties properties) {
        super(properties, AccessoryContainer.SlotType.RELIC); //todo slot type?
        this.companionType = companionType;
    }

    @Override
    public void onEquip(ItemStack stack, LivingEntity wearer) {
        CompanionAccessory.super.equip(stack, wearer);
    }

    @Override
    public void onUnequip(ItemStack stack, LivingEntity wearer) {
        CompanionAccessory.super.unequip(stack, wearer);
    }

    /**
     * @return The companion {@link EntityType}.
     */
    public EntityType<T> getCompanionType() {
        return this.companionType.get();
    }
}

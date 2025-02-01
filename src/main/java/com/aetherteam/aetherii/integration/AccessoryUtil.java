package com.aetherteam.aetherii.integration;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccessoryUtil {
    public static Optional<ItemStack> getFirst(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        return get(livingEntity, slot, 0);
    }

    public static Optional<ItemStack> get(LivingEntity livingEntity, AccessoryContainer.SlotType slot, int index) {
        List<ItemStack> itemStacks = get(livingEntity, slot);
        return itemStacks.isEmpty() ? Optional.empty() : Optional.of(itemStacks.get(index));
    }

    public static List<ItemStack> get(LivingEntity livingEntity, AccessoryContainer.SlotType slot) {
        AccessoryContainer container = livingEntity.getData(AetherIIDataAttachments.ACCESSORIES);
        List<ItemStack> items = new ArrayList<>();
        for (int i : slot.getIndex()) {
            ItemStack itemStack = container.getItem(i);
            if (!itemStack.isEmpty()) {
                items.add(itemStack);
            }
        }
        return items;
    }
}

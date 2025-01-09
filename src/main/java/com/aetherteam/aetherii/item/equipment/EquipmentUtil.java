package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.*;

public final class EquipmentUtil {
    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    public static int getArmorCount(LivingEntity entity, TagKey<Item> checkSet) {
        int armorTypeCount = 0;
        List<ItemStack> equipment = getEquipment(entity);
        for (ItemStack itemStack : equipment) {
            TagKey<Item> armorSet = itemStack.get(AetherIIDataComponents.ARMOR_SET);
            if (armorSet == checkSet) {
                armorTypeCount++;
            }
        }
        return armorTypeCount;
    }

    public static List<ItemStack> getEquipment(LivingEntity entity) {
        AccessoriesCapability accessories = AccessoriesCapability.get(entity);
        List<ItemStack> equipment = new ArrayList<>();
        entity.getArmorSlots().forEach(equipment::add);
        if (accessories != null) {
            SlotEntryReference slotEntryReference = accessories.getFirstEquipped((itemStack) -> itemStack.getItem() instanceof GlovesItem);
            if (slotEntryReference != null) {
                equipment.add(slotEntryReference.stack());
            }
        }
        return equipment;
    }

    public static boolean hasArmorAbility(LivingEntity entity, TagKey<Item> armorSet) {
        return getArmorCount(entity, armorSet) >= 3;
    }
}

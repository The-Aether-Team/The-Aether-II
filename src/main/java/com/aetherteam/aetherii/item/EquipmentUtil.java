package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.item.combat.GlovesItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.Map;

public final class EquipmentUtil {
    public static boolean isFullStrength(LivingEntity attacker) {
        boolean combatifyLoaded = ModList.get().isLoaded("combatify");
        return !(attacker instanceof Player player) || (combatifyLoaded ? player.getAttackStrengthScale(1.0F) >= 1.95F : player.getAttackStrengthScale(1.0F) >= 1.0F);
    }

    public static int getArmorCount(LivingEntity entity, Holder<ArmorMaterial> material) {
        Map<Holder<ArmorMaterial>, Integer> armorTypeCount = new HashMap<>();
        for (ItemStack itemStack : entity.getArmorSlots()) {
            if (itemStack.getItem() instanceof ArmorItem armorItem) {
                Holder<ArmorMaterial> materialHolder = armorItem.getMaterial();
                if (armorTypeCount.containsKey(materialHolder)) {
                    armorTypeCount.put(materialHolder, armorTypeCount.get(materialHolder) + 1);
                } else {
                    armorTypeCount.put(materialHolder, 1);
                }
            }
        }
        AccessoriesCapability accessories = AccessoriesCapability.get(entity);
        if (accessories != null) {
            SlotEntryReference slotEntryReference = accessories.getFirstEquipped((itemStack) -> itemStack.getItem() instanceof GlovesItem);
            if (slotEntryReference != null && slotEntryReference.stack().getItem() instanceof GlovesItem glovesItem) {
                Holder<ArmorMaterial> materialHolder = glovesItem.getMaterial();
                if (armorTypeCount.containsKey(materialHolder)) {
                    armorTypeCount.put(materialHolder, armorTypeCount.get(materialHolder) + 1);
                } else {
                    armorTypeCount.put(materialHolder, 1);
                }
            }
        }
        return armorTypeCount.computeIfAbsent(material, i -> 0);
    }

    public static boolean hasArmorAbility(LivingEntity entity, Holder<ArmorMaterial> material) {
        return getArmorCount(entity, material) >= 3;
    }
}

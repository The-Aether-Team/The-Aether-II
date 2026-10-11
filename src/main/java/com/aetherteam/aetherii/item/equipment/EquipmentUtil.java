package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        List<ItemStack> equipment = new ArrayList<>();
        for (EquipmentSlot equipmentSlot : EquipmentSlotGroup.ARMOR) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack armor = entity.getItemBySlot(equipmentSlot);
                equipment.add(armor);
            }
        }
        AccessoryUtil.getFirst(entity, AccessoryContainer.SlotType.HANDWEAR).ifPresent(equipment::add);
        return equipment;
    }

    public static boolean hasArmorAbility(LivingEntity entity, TagKey<Item> armorSet) {
        return getArmorCount(entity, armorSet) >= 3;
    }

    public static Identifier getSlotModifierId(Identifier base, ItemStack itemStack, int number, String slotName) {
        return Identifier.parse(itemStack.typeHolder().getRegisteredName() + "_" + base.getPath() + "_" + number + "_" + slotName.toLowerCase(Locale.ROOT));
    }
}

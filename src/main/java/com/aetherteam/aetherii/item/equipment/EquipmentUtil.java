package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

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
            TagKey<Item> armorSet = AetherIIDataComponents.get(itemStack, AetherIIDataComponents.ARMOR_SET);
            if (armorSet == checkSet) {
                armorTypeCount++;
            }
        }
        return armorTypeCount;
    }

    public static List<ItemStack> getEquipment(LivingEntity entity) {
        List<ItemStack> equipment = new ArrayList<>();
        for (EquipmentSlot equipmentSlot : List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
            if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
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

    public static ResourceLocation getSlotModifierId(ResourceLocation base, ItemStack itemStack, int number, String slotName) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return new ResourceLocation(itemId.getNamespace(), itemId.getPath() + "_" + base.getPath() + "_" + number + "_" + slotName.toLowerCase(Locale.ROOT));
    }
}

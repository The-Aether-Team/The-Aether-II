package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class AetherArmorItem extends ArmorItem {
    public AetherArmorItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String material = this.getMaterial().getName().replace(AetherII.MODID + ":", "");
        String layer = slot == EquipmentSlot.LEGS ? "humanoid_leggings" : "humanoid";
        return String.format("%s:textures/entity/equipment/%s/%s.png", AetherII.MODID, layer, material);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}

package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AetherIIArmorItem extends ArmorItem {
    public AetherIIArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return String.format("%s:textures/models/armor/%s_layer_%s.png", AetherII.MODID, this.getMaterial().getName(), slot == EquipmentSlot.LEGS ? 2 : 1);
    }
}
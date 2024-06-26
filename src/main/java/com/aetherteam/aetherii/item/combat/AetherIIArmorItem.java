package com.aetherteam.aetherii.item.combat;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class AetherIIArmorItem extends ArmorItem {
    public AetherIIArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

//    @Nullable
//    @Override
//    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
//        return String.format("%s:textures/models/armor/%s_layer_%s.png", AetherII.MODID, this.getMaterial().getName(), slot == EquipmentSlot.LEGS ? 2 : 1);
//    }
}
package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.client.models.items.ModelWinterHat;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.MaterialsAether;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemWinterHat extends ItemArmor
{
    public ItemWinterHat()
    {
        super(MaterialsAether.WINTER_ARMOR, 0, EquipmentSlotType.HEAD);
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return AetherCore.getResourcePath("textures/armor/winter_hat.png");
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelBiped getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, ModelBiped _default)
    {
        return new ModelWinterHat();
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EquipmentSlotType equipmentSlot)
    {
        return HashMultimap.create();
    }
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if(repair.isItemEqual(new ItemStack(BlocksAether.cloudwool_block))) return true;
        return super.getIsRepairable(toRepair, repair);
    }

}

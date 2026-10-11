package com.aetherteam.aetherii.client.extensions;

import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public interface SaddlebagClientItemExtensions extends IClientItemExtensions { //todo turn into general EquippableClientItemExtensions class in the future
    ResourceKey<EquipmentAsset> getSaddlebagAsset(ItemStack itemStack);

    EntityModel<? super MoaRenderState> getSaddlebagModel(ItemStack itemStack);
}

package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.item.AetherIIArmorMaterials;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @ModifyArg(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/DyedItemColor;getOrDefault(Lnet/minecraft/world/item/ItemStack;I)I"), index = 1)
    private int getOrDefaultColor(int defaultColor, @Local ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            Holder<ArmorMaterial> materialHolder = armorItem.getMaterial();
            if (materialHolder.is(AetherIIArmorMaterials.TAEGORE_HIDE)) {
                defaultColor = -3150087;
            } else if (materialHolder.is(AetherIIArmorMaterials.BURRUKAI_PELT)) {
                defaultColor = -10380096;
            }
        }
        return defaultColor;
    }
}

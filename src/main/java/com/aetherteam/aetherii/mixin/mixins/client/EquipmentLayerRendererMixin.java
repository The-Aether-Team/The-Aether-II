package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {
    @Unique
    private final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> armorStyleSpriteLookup = Util.memoize((key) -> Minecraft.getInstance().getModelManager().getAtlas(AetherIIAtlases.ARMOR_STYLES_SHEET).getSprite(key.textureId()));

    @Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "TAIL"))
    public void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAsset, Model armorModel, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, ResourceLocation texture, CallbackInfo ci, @Local List<EquipmentClientInfo.Layer> list) {
        if (!list.isEmpty()) {
            ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
            if (style != null && Minecraft.getInstance().level != null) {
                TextureAtlasSprite sprite = this.armorStyleSpriteLookup.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, layerType.getSerializedName()));
                VertexConsumer consumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET)));
                armorModel.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}

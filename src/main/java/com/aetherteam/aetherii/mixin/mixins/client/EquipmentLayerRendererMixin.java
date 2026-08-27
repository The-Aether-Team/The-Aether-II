package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jspecify.annotations.Nullable;
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
    private final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> armorStyleSpriteLookup = Util.memoize((key) -> Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.ARMOR_STYLES_ID).getSprite(key.textureId()));

    @Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(value = "TAIL"))
    public <S> void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAssetId, Model<? super S> model, S state, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, @Nullable Identifier playerTextureOverride, int outlineColor, int order, CallbackInfo ci, @Local(name = "layers") List<EquipmentClientInfo.Layer> layers) {
        if (!layers.isEmpty()) {
            ArmorStyle style = itemStack.get(AetherIIDataComponents.ARMOR_STYLE);
            if (style != null && Minecraft.getInstance().level != null) {
                TextureAtlasSprite sprite = this.armorStyleSpriteLookup.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, layerType.getSerializedName()));
                RenderType renderType = RenderTypes.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET);
                submitNodeCollector.order(order + 1).submitModel(model, state, poseStack, renderType, lightCoords, OverlayTexture.NO_OVERLAY, -1, sprite, outlineColor, null);
            }
        }
    }
}

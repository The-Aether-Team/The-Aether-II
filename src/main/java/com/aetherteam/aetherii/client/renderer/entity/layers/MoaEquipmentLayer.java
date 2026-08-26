package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.Function;

public abstract class MoaEquipmentLayer extends RenderLayer<MoaRenderState, EntityModel<MoaRenderState>> {
    protected final EquipmentAssetManager equipmentAssets;
    protected final Function<EquipmentLayerRenderer.LayerTextureKey, Identifier> layerTextureLookup;

    public MoaEquipmentLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EquipmentAssetManager equipmentAssets) {
        super(renderer);
        this.equipmentAssets = equipmentAssets;
        this.layerTextureLookup = Util.memoize((key) -> key.layer().getTextureLocation(key.layerType()));
    }

    public void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAssetId, Model<? super MoaRenderState> model, MoaRenderState state, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int outlineColor) {
        IClientItemExtensions extensions = IClientItemExtensions.of(itemStack);
        List<EquipmentClientInfo.Layer> layers = this.equipmentAssets.get(equipmentAssetId).getLayers(layerType);
        if (!layers.isEmpty()) {
            int dyeColor = extensions.getDefaultDyeColor(itemStack);
            int nextOrder = 0;
            int idx = 0;
            for (EquipmentClientInfo.Layer layer : layers) {
                Vector4f vectorColor = ARGB.vector4fFromARGB32(extensions.getArmorLayerTintColor(itemStack, layer, idx, dyeColor));
                int color = ARGB.colorFromFloat(state.opacity, vectorColor.x(), vectorColor.y(), vectorColor.z());
                if (color != 0) {
                    Identifier layerTexture = this.layerTextureLookup.apply(new EquipmentLayerRenderer.LayerTextureKey(layerType, layer));
                    layerTexture = ClientHooks.getArmorTexture(itemStack, layerType, layer, layerTexture);
                    submitNodeCollector.order(nextOrder++).submitModel(model, state, poseStack, AetherIIRenderTypes.entityDitherNoCull(layerTexture), lightCoords, OverlayTexture.NO_OVERLAY, color, null, outlineColor, null);
                }
                ++idx;
            }
        }
    }
}

package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.AetherIIEquipmentLayerTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.item.components.Accessory;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.ItemStack;

public class AccessoryLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final HumanoidModel<S> accessoryModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public AccessoryLayer(RenderLayerParent<S, M> renderer, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.accessoryModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.ACCESSORY));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S parentState, float yRot, float xRot) {
        ItemStack itemStack = parentState.getRenderData(AetherIIRenderers.ACCESSORY_EQUIPMENT_KEY);
        if (itemStack != null && !itemStack.isEmpty()) {
            Accessory accessory = itemStack.get(AetherIIDataComponents.ACCESSORY);
            if (accessory != null) {
                this.equipmentRenderer.renderLayers(AetherIIEquipmentLayerTypes.HUMANOID_ACCESSORY, accessory.assetId(), this.accessoryModel, parentState, itemStack, poseStack, submitNodeCollector, lightCoords, parentState.outlineColor);
            }
        }
    }
}
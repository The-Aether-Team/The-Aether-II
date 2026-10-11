package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.AetherIIEquipmentLayerTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddleModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

public class MoaSaddleLayer extends MoaEquipmentLayer {
    private final MoaSaddleModel saddle;

    public MoaSaddleLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EquipmentAssetManager equipmentAssets) {
        super(renderer, equipmentAssets);
        this.saddle = new MoaSaddleModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.MOA_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, MoaRenderState parentState, float yRot, float xRot) {
        ItemStack itemStack = parentState.saddle;
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (!parentState.isInvisible && equippable != null && equippable.assetId().isPresent()) {
            this.renderLayers(AetherIIEquipmentLayerTypes.MOA_SADDLE, equippable.assetId().get(), this.saddle, parentState, itemStack, poseStack, submitNodeCollector, lightCoords, parentState.outlineColor);
        }
    }
}

package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.AetherIIEquipmentLayerTypes;
import com.aetherteam.aetherii.client.extensions.SaddlebagClientItemExtensions;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class MoaSaddlebagLayer extends MoaEquipmentLayer {
    public MoaSaddlebagLayer(RenderLayerParent<MoaRenderState, EntityModel<MoaRenderState>> renderer, EquipmentAssetManager equipmentAssets) {
        super(renderer, equipmentAssets);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, MoaRenderState parentState, float yRot, float xRot) {
        ItemStack itemStack = parentState.saddlebag;
        if (!parentState.isInvisible) {
            IClientItemExtensions extensions = IClientItemExtensions.of(itemStack);
            if (extensions instanceof SaddlebagClientItemExtensions saddlebagExtensions) {
                this.renderLayers(AetherIIEquipmentLayerTypes.MOA_SADDLEBAG, saddlebagExtensions.getSaddlebagAsset(itemStack), saddlebagExtensions.getSaddlebagModel(itemStack), parentState, itemStack, poseStack, submitNodeCollector, lightCoords, parentState.outlineColor);
            }
        }
    }
}

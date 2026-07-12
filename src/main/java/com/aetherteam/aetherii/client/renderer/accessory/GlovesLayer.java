package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.AetherIIEquipmentLayerTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.item.components.Accessory;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GlovesLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends GlovesModel> extends RenderLayer<S, M> implements FirstPersonRendering {
    private final GlovesModelSet<A> glovesModelThirdPerson;
    private final GlovesModelSet<A> glovesModelFirstPerson;
    private final EquipmentLayerRenderer equipmentRenderer;

    public GlovesLayer(RenderLayerParent<S, M> renderer, GlovesModelSet<A> thirdPersonModelSet, GlovesModelSet<A> firstPersonModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.glovesModelThirdPerson = thirdPersonModelSet;
        this.glovesModelFirstPerson = firstPersonModelSet;
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S parentState, float yRot, float xRot) {
        ItemStack itemStack = parentState.getRenderData(AetherIIRenderers.HANDWEAR_EQUIPMENT_KEY);
        if (itemStack != null && !itemStack.isEmpty()) {
            this.submitGlovePiece(poseStack, submitNodeCollector, itemStack, this.glovesModelThirdPerson, HumanoidArm.RIGHT, lightCoords, parentState);
            this.submitGlovePiece(poseStack, submitNodeCollector, itemStack, this.glovesModelThirdPerson, HumanoidArm.LEFT, lightCoords, parentState);
        }
    }

    @Override
    public void submitFirstPerson(HumanoidArm arm, ItemStack itemStack, Player player, PoseStack poseStack, PlayerModel model, SubmitNodeCollector submitNodeCollector, int lightCoords) {
        this.submitGlovePiece(poseStack, submitNodeCollector, itemStack, this.glovesModelFirstPerson, arm, lightCoords, new AvatarRenderState());
    }

    private void submitGlovePiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, GlovesModelSet<A> glovesModelSet, HumanoidArm arm, int lightCoords, HumanoidRenderState parentState) {
        Accessory accessory = itemStack.get(AetherIIDataComponents.ACCESSORY);
        if (accessory != null) {
            this.equipmentRenderer.renderLayers(AetherIIEquipmentLayerTypes.HUMANOID_GLOVES, accessory.assetId(), glovesModelSet.get(arm), parentState, itemStack, poseStack, submitNodeCollector, lightCoords, parentState.outlineColor);
        }
    }
}
package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class AccessoryLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final HumanoidModel<S> accessoryModel;

    public AccessoryLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
        this.accessoryModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.ACCESSORY));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, S s, float v, float v1) {

    }

//    @Override //todo
//    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S state, float netHeadYaw, float headPitch) {
//        if (Minecraft.getInstance().player != null) {
//            AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.ACCESSORY).ifPresent((stack) -> {
//                HumanoidModel<S> model = this.accessoryModel;
//                this.getParentModel().copyPropertiesTo(model);
//
//                Identifier itemLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
//                Identifier texture = Identifier.fromNamespaceAndPath(itemLocation.getNamespace(), "textures/entity/equipment/accessory/" + itemLocation.getPath() + ".png");
//                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
//
//                model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
//            });
//        }
//    }
}

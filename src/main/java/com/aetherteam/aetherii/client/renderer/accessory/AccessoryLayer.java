package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

public class AccessoryLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final HumanoidModel<S> accessoryModel;

    public AccessoryLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
        this.accessoryModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.ACCESSORY));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S s, float v, float v1) {
        if (s instanceof AvatarRenderState avatarRenderState) {
            ItemStack stack = avatarRenderState.getRenderData(AetherIIRenderers.ACCESSORY_EQUIPMENT_KEY);
            if (stack != null && !stack.isEmpty()) {
                HumanoidModel<S> model = this.accessoryModel;

                if (this.getParentModel() instanceof HumanoidModel<S> humanoidModel) {
                    copyPropertiesTo(model, humanoidModel);
                }

                Identifier itemLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
                Identifier texture = Identifier.fromNamespaceAndPath(itemLocation.getNamespace(), "textures/entity/equipment/accessory/" + itemLocation.getPath() + ".png");

                submitNodeCollector.submitModel(
                        model,
                        s,
                        poseStack,
                        RenderTypes.armorCutoutNoCull(texture),
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        -1,
                        null,
                        s.outlineColor,
                        null
                );
            }
        }
    }

    public void copyPropertiesTo(HumanoidModel model, HumanoidModel from) {
        copyFrom(model.head, from.head);
        copyFrom(model.body, from.body);
        copyFrom(model.rightArm, from.rightArm);
        copyFrom(model.leftArm, from.leftArm);
        copyFrom(model.rightLeg, from.rightLeg);
        copyFrom(model.leftLeg, from.leftLeg);
    }

    public void copyFrom(ModelPart model, ModelPart from) {
        model.offsetPos(new Vector3f(from.x, from.y, from.z));
        model.offsetRotation(new Vector3f(from.xRot, from.yRot, from.zRot));
    }
}
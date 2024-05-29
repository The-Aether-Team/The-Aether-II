package com.aetherteam.aetherii.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.TamableAnimal;

public abstract class TamableCollarLayer<T extends TamableAnimal, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation collarLocation;
    private final M model;

    public TamableCollarLayer(RenderLayerParent<T, M> renderer, M model, ResourceLocation collarLocation) {
        super(renderer);
        this.collarLocation = collarLocation;
        this.model = model;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity.isTame()) {
            float[] color = this.getColor(livingEntity);
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, this.collarLocation, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, color[0], color[1], color[2]);
        }
    }

    public abstract float[] getColor(T entity);
}

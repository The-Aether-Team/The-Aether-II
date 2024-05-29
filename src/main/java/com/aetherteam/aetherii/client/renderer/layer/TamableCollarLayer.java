package com.aetherteam.aetherii.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.TamableAnimal;

public abstract class TamableCollarLayer<T extends TamableAnimal, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation collar_location;
    private final M model;

    public TamableCollarLayer(RenderLayerParent<T, M> pRenderer, M model, ResourceLocation collarLocation) {
        super(pRenderer);
        this.collar_location = collarLocation;
        this.model = model;
    }

    public void render(
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight,
            T pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pPartialTicks,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch
    ) {
        if (pLivingEntity.isTame()) {
            float[] afloat = this.getColor(pLivingEntity);
            coloredCutoutModelCopyLayerRender(
                    this.getParentModel(),
                    this.model,
                    this.collar_location,
                    pPoseStack,
                    pBuffer,
                    pPackedLight,
                    pLivingEntity,
                    pLimbSwing,
                    pLimbSwingAmount,
                    pAgeInTicks,
                    pNetHeadYaw,
                    pHeadPitch,
                    pPartialTicks,
                    afloat[0],
                    afloat[1],
                    afloat[2]
            );
        }
    }

    public abstract float[] getColor(T entity);
}

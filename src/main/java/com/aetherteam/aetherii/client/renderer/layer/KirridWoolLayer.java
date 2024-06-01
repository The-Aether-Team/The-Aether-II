package com.aetherteam.aetherii.client.renderer.layer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.model.KirridModel;
import com.aetherteam.aetherii.client.renderer.model.KirridWoolModel;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class KirridWoolLayer<T extends Kirrid> extends RenderLayer<T, EntityModel<T>> {
    private static final ResourceLocation FUR_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields.png");
    private final KirridWoolModel<T> model;

    public KirridWoolLayer(RenderLayerParent<T, EntityModel<T>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new KirridWoolModel<>(pModelSet.bakeLayer(AetherModelLayers.KIRRID_WOOL));
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
        if (pLivingEntity.hasWool()) {
            if (pLivingEntity.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean flag = minecraft.shouldEntityAppearGlowing(pLivingEntity);
                if (flag) {
                    if (this.getParentModel() instanceof KirridModel<T> kirridModel) {
                        kirridModel.body.translateAndRotate(pPoseStack);
                        pPoseStack.translate(0, -(8F / 16F), 0);
                        this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                        this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.outline(FUR_LOCATION));
                        this.model
                                .renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                    }
                }
            } else {
                if (this.getParentModel() instanceof KirridModel<T> kirridModel) {
                    kirridModel.body.translateAndRotate(pPoseStack);
                    pPoseStack.translate(0, -(8F / 16F), 0);
                    this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
                    this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(FUR_LOCATION));
                    this.model
                            .renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}

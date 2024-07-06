package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class MoaKeratinLayer extends RenderLayer<Moa, EntityModel<Moa>> {
    public static ResourceLocation KERATIN_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_keratin.png");

    public MoaKeratinLayer(RenderLayerParent<Moa, EntityModel<Moa>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Moa livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!livingEntity.isInvisible()) {
            int i = livingEntity.getKeratinColor();
            if (!livingEntity.isBaby()) {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), KERATIN_LOCATION, poseStack, bufferSource, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, i);
            }
        }
    }
}

package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class SwetGelLayer extends RenderLayer<Swet, SwetModel<Swet>> {
    public static final ResourceLocation BLUE_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");
    public static final ResourceLocation GOLDEN_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/swet/golden_swet.png");

    private final SwetModel<Swet> model;

    public SwetGelLayer(RenderLayerParent<Swet, SwetModel<Swet>> renderer, EntityModelSet modelSet) {
        this(renderer, modelSet, new SwetModel<>(modelSet.bakeLayer(AetherIIModelLayers.SWET)));
    }

    public SwetGelLayer(RenderLayerParent<Swet, SwetModel<Swet>> renderer, EntityModelSet modelSet, SwetModel<Swet> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Swet livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = minecraft.shouldEntityAppearGlowing(livingEntity) && livingEntity.isInvisible();
        if (!livingEntity.isInvisible() || flag) {
            VertexConsumer vertexconsumer;
            if (flag) {
                vertexconsumer = bufferSource.getBuffer(RenderType.outline(this.getTextureLocation(livingEntity)));
            } else {
                vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(livingEntity)));
            }


            this.getParentModel().copyPropertiesTo(this.model);

            this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.head.visible = false;
            this.model.gel.visible = true;
            this.model.squish.visible = true;
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTextureLocation(Swet swet) {
        return swet.getType() == AetherIIEntityTypes.GOLDEN_SWET.get() ? GOLDEN_LOCATION : BLUE_LOCATION;
    }
}

package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SheepuffRenderState;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<SheepuffRenderState, SheepuffModel<SheepuffRenderState>> {
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");

    public SheepuffWoolLayer(RenderLayerParent<SheepuffRenderState, SheepuffModel<SheepuffRenderState>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SheepuffRenderState sheepuff, float netHeadYaw, float headPitch) {
        if (!sheepuff.isSheared) {
            SheepuffModel<SheepuffRenderState> entitymodel = this.getParentModel();
            if (sheepuff.isInvisible) {
                if (sheepuff.appearsGlowing) {
                    entitymodel.setupAnim(sheepuff);
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.outline(SHEEPUFF_WOOL_TEXTURE));
                    entitymodel.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), -16777216);
                }
            } else {
                int i = Sheepuff.getDecimalColor(sheepuff.woolColor);

                coloredCutoutModelCopyLayerRender(entitymodel, SHEEPUFF_WOOL_TEXTURE, poseStack, buffer, packedLight, sheepuff, i);
            }
        }
    }
}

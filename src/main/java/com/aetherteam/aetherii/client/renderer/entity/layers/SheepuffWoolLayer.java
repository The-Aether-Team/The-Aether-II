package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SheepuffRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<SheepuffRenderState, SheepuffModel<SheepuffRenderState>> {
    private static final Identifier SHEEPUFF_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");

    public SheepuffWoolLayer(RenderLayerParent<SheepuffRenderState, SheepuffModel<SheepuffRenderState>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, SheepuffRenderState sheepuff, float v, float v1) {
        if (!sheepuff.isSheared) {
            if (sheepuff.isInvisible) {
                if (sheepuff.appearsGlowing()) {
                    submitNodeCollector.submitModel(this.getParentModel(), sheepuff, poseStack, RenderTypes.outline(SHEEPUFF_WOOL_TEXTURE), packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), -16777216, null);
                }
            } else {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), SHEEPUFF_WOOL_TEXTURE, poseStack, submitNodeCollector, packedLight, sheepuff, sheepuff.woolColor, 1);
            }
        }
    }
}

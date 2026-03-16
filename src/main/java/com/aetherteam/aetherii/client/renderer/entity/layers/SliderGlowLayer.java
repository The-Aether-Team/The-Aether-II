package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SliderModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SliderRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;

public class SliderGlowLayer extends EyesLayer<SliderRenderState, SliderModel> {
    private static final RenderType SLIDER_AWAKE_EMISSIVE = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_emissive.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_EMISSIVE = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/slider/slider_awake_critical_emissive.png"));

    public SliderGlowLayer(RenderLayerParent<SliderRenderState, SliderModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int p_434650_, SliderRenderState sliderRenderState, float p_433542_, float p_435619_) {
        if (sliderRenderState.awake) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), sliderRenderState, poseStack, this.renderType(sliderRenderState), 15728640, OverlayTexture.NO_OVERLAY, -1, (TextureAtlasSprite) null, sliderRenderState.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
        }
    }

    public RenderType renderType(SliderRenderState slider) {
        if (slider.critical) {
            return SLIDER_AWAKE_CRITICAL_EMISSIVE;
        }
        return this.renderType();
    }

    @Override
    public RenderType renderType() {
        return SLIDER_AWAKE_EMISSIVE;
    }
}

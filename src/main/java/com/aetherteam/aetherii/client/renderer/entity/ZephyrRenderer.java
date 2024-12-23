package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.ZephyrModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ZephyrRenderState;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ZephyrRenderer extends MobRenderer<Zephyr, ZephyrRenderState, ZephyrModel> {
    private static final ResourceLocation ZEPHYR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/zephyr/zephyr.png");

    public ZephyrRenderer(EntityRendererProvider.Context context) {
        super(context, new ZephyrModel(context.bakeLayer(AetherIIModelLayers.ZEPHYR)), 0.5F);
    }

    @Override
    public ZephyrRenderState createRenderState() {
        return new ZephyrRenderState();
    }

    @Override
    public void extractRenderState(Zephyr zephyr, ZephyrRenderState renderState, float p_361157_) {
        super.extractRenderState(zephyr, renderState, p_361157_);
        renderState.blowAnimationState.copyFrom(zephyr.blowAnimationState);
        renderState.webAnimationState.copyFrom(zephyr.webAnimationState);
    }

    /**
     * Scales the Zephyr according to its attack charge progress, as well as dependent on the model it is using.
     *
     * @param zephyr       The {@link Zephyr} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(ZephyrRenderState zephyr, PoseStack poseStack) {
        poseStack.scale(1.8F, 1.8F, 1.8F);
//        poseStack.translate(0.0, 0.15, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(ZephyrRenderState renderState) {
        return ZEPHYR_TEXTURE;
    }
}

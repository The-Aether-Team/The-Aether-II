package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.ZephyrModel;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ZephyrRenderer extends MobRenderer<Zephyr, ZephyrModel> {
    private static final ResourceLocation ZEPHYR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/zephyr/zephyr.png");

    public ZephyrRenderer(EntityRendererProvider.Context context) {
        super(context, new ZephyrModel(context.bakeLayer(AetherIIModelLayers.ZEPHYR)), 0.5F);
    }

    /**
     * Scales the Zephyr according to its attack charge progress, as well as dependent on the model it is using.
     *
     * @param zephyr       The {@link Zephyr} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    @Override
    protected void scale(Zephyr zephyr, PoseStack poseStack, float partialTicks) {
        poseStack.scale(4.0F, 4.0F, 4.0F);
        poseStack.translate(0.0, 0.15, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(Zephyr pEntity) {
        return ZEPHYR_TEXTURE;
    }
}

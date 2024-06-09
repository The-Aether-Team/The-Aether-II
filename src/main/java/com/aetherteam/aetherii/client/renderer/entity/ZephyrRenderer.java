package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ZephyrTransparencyLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ZephyrModel;
import com.aetherteam.aetherii.entity.monster.Zephyr;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ZephyrRenderer extends MobRenderer<Zephyr, ZephyrModel> {
    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/zephyr/zephyr.png");

    public ZephyrRenderer(EntityRendererProvider.Context context) {
        super(context, new ZephyrModel(context.bakeLayer(AetherIIModelLayers.ZEPHYR)), 0.5F);
        this.addLayer(new ZephyrTransparencyLayer(this, new ZephyrModel(context.getModelSet().bakeLayer(AetherIIModelLayers.ZEPHYR_TRANSPARENCY))));
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
        float f = Math.min(Mth.lerp(partialTicks, zephyr.getCloudScale(), zephyr.getCloudScale() + zephyr.getCloudScaleAdd()), 38.0F);
        float f1 = f / 38.0F;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }
        f1 = 1.0F / ((float) Math.pow(f1, 5) * 2.0F + 1.0F);
        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;

        poseStack.scale(f3, f2, f3);
        poseStack.translate(0.0, 0.5, 0.0);
    }

    /**
     * Passes the Zephyr's tail rotation to the model as the "ageInTicks" parameter.
     *
     * @param zephyr       The {@link Zephyr} entity.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @return The {@link Float} for the petal rotation.
     */
    @Override
    protected float getBob(Zephyr zephyr, float partialTicks) {
        return Mth.lerp(partialTicks, zephyr.getTailRot(), zephyr.getTailRot() + zephyr.getTailRotAdd());
    }

    @Override
    public ResourceLocation getTextureLocation(Zephyr pEntity) {
        return ZEPHYR_TEXTURE;
    }
}

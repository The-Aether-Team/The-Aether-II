package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.AerbunnyCollarLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyRenderState, AerbunnyModel> {
    private static final ResourceLocation AERBUNNY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new AerbunnyModel(context.bakeLayer(AetherIIModelLayers.AERBUNNY)), 0.3F);
        this.addLayer(new AerbunnyCollarLayer(this, context.getModelSet()));
    }

    @Override
    public AerbunnyRenderState createRenderState() {
        return new AerbunnyRenderState();
    }

    @Override
    public void extractRenderState(Aerbunny aerbunny, AerbunnyRenderState renderState, float partialTick) {
        super.extractRenderState(aerbunny, renderState, partialTick);
        renderState.puffiness = Mth.lerp(partialTick, aerbunny.getPuffiness(), aerbunny.getPuffiness() - aerbunny.getPuffSubtract()) / 20.0F;
        renderState.collarColor = aerbunny.getCollarColor();
        renderState.isSitting = aerbunny.isInSittingPose();
        renderState.onGround = aerbunny.onGround();
        renderState.deltaMovement = aerbunny.getDeltaMovement();
        renderState.tame = aerbunny.isTame();
        renderState.vehicleUUID = aerbunny.getVehicleUUID();
    }

    /**
     * Scales the Aerbunny if it is a baby.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(AerbunnyRenderState aerbunny, PoseStack poseStack) {
        if (aerbunny.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }

    /**
     * Rotates the Aerbunny back and forth when it is jumping.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param rotationYaw  The {@link Float} for the rotation yaw.
     */
    @Override
    protected void setupRotations(AerbunnyRenderState aerbunny, PoseStack poseStack, float rotationYaw, float scale) {
        super.setupRotations(aerbunny, poseStack, rotationYaw, scale);
        if (!aerbunny.onGround) {
            if (aerbunny.deltaMovement.y() > 0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(aerbunny.partialTick, 0.0F, 15.0F)));
            } else if (aerbunny.deltaMovement.y() < -0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(aerbunny.partialTick, 0.0F, -15.0F)));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float) (aerbunny.deltaMovement.y() * 30.0)));
            }
        }
    }

    @Override
    protected int getModelTint(AerbunnyRenderState renderState) {
        float opacity = this.calculateOpacity(renderState);
        if (opacity < 1.0F) {
            return ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F);
        }
        return super.getModelTint(renderState);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(AerbunnyRenderState renderState, boolean isVisible, boolean renderTranslucent, boolean appearsGlowing) {
        ResourceLocation resourceLocation = this.getTextureLocation(renderState);
        float opacity = this.calculateOpacity(renderState);
        if (opacity < 1.0F) {
            return RenderType.entityTranslucent(resourceLocation);
        }
        return super.getRenderType(renderState, isVisible, renderTranslucent, appearsGlowing);
    }

    @Override
    public ResourceLocation getTextureLocation(AerbunnyRenderState renderState) {
        return AERBUNNY_TEXTURE;
    }

    private float calculateOpacity(AerbunnyRenderState renderState) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            if (renderState.vehicleUUID.isPresent() && renderState.vehicleUUID.get().equals(player.getUUID())) {
                Vec3 lookAngle = player.getLookAngle();
                float calc = (float) Math.min((Math.max(lookAngle.y(), 0.65) - 0.65) * 2.5F, 1.0F);
                return 1.0F - calc;
            }
        }
        return 1.0F;
    }
}
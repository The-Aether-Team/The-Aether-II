package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.AerbunnyCollarLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyRenderState, AerbunnyModel> {
    private static final Identifier AERBUNNY_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");
    private static final Identifier AERBUNNY_BAGUCHI_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny_baguchi.png");

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
        renderState.vehicleReference = aerbunny.getVehicleReference();
        renderState.isBaguchi = "Baguchi".equals(ChatFormatting.stripFormatting(aerbunny.getName().getString()));
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
        float opacity = calculateOpacity(renderState);
        if (opacity < 1.0F) {
            return ARGB.colorFromFloat(opacity, 1.0F, 1.0F, 1.0F);
        }
        return super.getModelTint(renderState);
    }

    @Override
    public Identifier getTextureLocation(AerbunnyRenderState renderState) {
        if (renderState.isBaguchi) {
            return AERBUNNY_BAGUCHI_TEXTURE;
        }

        return AERBUNNY_TEXTURE;
    }

    public static float calculateOpacity(AerbunnyRenderState renderState) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            if (renderState.vehicleReference.isPresent() && renderState.vehicleReference.get().matches(player)) {
                Vec3 lookAngle = player.getLookAngle();
                float calc = (float) Math.min((Math.max(lookAngle.y(), 0.45) - 0.45) * 2.5F, 0.85F);
                return 1.0F - calc;
            }
        }
        return 1.0F;
    }
}
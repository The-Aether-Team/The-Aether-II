package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArrowModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class ProjectilesStuckLayer<M extends PlayerModel> extends RenderLayer<PlayerRenderState, M> {
    private final Model model;

    public ProjectilesStuckLayer(LivingEntityRenderer<?, PlayerRenderState, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, PlayerRenderState renderState, float netHeadYaw, float headPitch) {
        List<EntityType<?>> list = renderState.getRenderData(AetherIIRenderers.STUCK_PROJECTILES_KEY);
        if (list != null && !list.isEmpty()) {
            RandomSource random = RandomSource.create(renderState.id);
            for (EntityType<?> type : list) {
                 key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
                 texture = Identifier.fromNamespaceAndPath(key.getNamespace(), "textures/entity/projectile/" + key.getPath() + ".png");
                 emissive = null;
                if (type.is(AetherIITags.Entities.STICKABLE_PROJECTILES_EMISSIVE)) {
                    emissive = Identifier.fromNamespaceAndPath(key.getNamespace(), "textures/entity/projectile/" + key.getPath() + "_emissive.png");
                }

                poseStack.pushPose();
                ModelPart part = this.getParentModel().getRandomBodyPart(random);
                ModelPart.Cube cube = part.getRandomCube(random);
                part.translateAndRotate(poseStack);
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();

                int k = random.nextInt(3);
                switch (k) {
                    case 0 -> f = snapToFace(f);
                    case 1 -> f1 = snapToFace(f1);
                    default -> f2 = snapToFace(f2);
                }

                poseStack.translate(Mth.lerp(f, cube.minX, cube.maxX) / 16.0F, Mth.lerp(f1, cube.minY, cube.maxY) / 16.0F, Mth.lerp(f2, cube.minZ, cube.maxZ) / 16.0F);
                this.renderStuckItem(poseStack, buffer, packedLight, -(f * 2.0F - 1.0F), -(f1 * 2.0F - 1.0F), -(f2 * 2.0F - 1.0F), texture, emissive);
                poseStack.popPose();
            }
        }
    }

    private void renderStuckItem(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float x, float y, float z, Identifier texture, Identifier emissive) {
        float f = Mth.sqrt(x * x + z * z);
        float f1 = (float) (Math.atan2(x, z) * 180.0F / Math.PI);
        float f2 = (float) (Math.atan2(y, f) * 180.0F / Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(f1 - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f2));
        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(texture)), packedLight, OverlayTexture.NO_OVERLAY);
        if (emissive != null) {
            this.model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.eyes(emissive)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }
    }

    private static float snapToFace(float value) {
        return value > 0.5F ? 1.0F : 0.5F;
    }
}

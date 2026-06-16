package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class ProjectilesStuckLayer<M extends PlayerModel> extends RenderLayer<AvatarRenderState, M> {
    private final ArrowModel model;
    private final ArrowRenderState modelState;

    public ProjectilesStuckLayer(LivingEntityRenderer<?, AvatarRenderState, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
        this.modelState = new ArrowRenderState();
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, AvatarRenderState avatarRenderState, float v, float v1) {

        List<EntityType<?>> list = avatarRenderState.getRenderData(AetherIIRenderers.STUCK_PROJECTILES_KEY);
        if (list != null && !list.isEmpty()) {
            RandomSource random = RandomSource.create(avatarRenderState.id);
            for (EntityType<?> type : list) {
                Identifier key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
                Identifier texture = Identifier.fromNamespaceAndPath(key.getNamespace(), "textures/entity/projectile/" + key.getPath() + ".png");
                Identifier emissive = null;
                if (type.builtInRegistryHolder().is(AetherIITags.EntityTypes.STICKABLE_PROJECTILES_EMISSIVE)) {
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
                this.renderStuckItem(poseStack, submitNodeCollector, avatarRenderState, i, -(f * 2.0F - 1.0F), -(f1 * 2.0F - 1.0F), -(f2 * 2.0F - 1.0F), texture, emissive);
                poseStack.popPose();
            }


        }
    }

    private void renderStuckItem(PoseStack poseStack, @UnknownNullability SubmitNodeCollector submitNodeCollector, AvatarRenderState avatarRenderState, int packedLight, float x, float y, float z, Identifier texture, Identifier emissive) {
        float f = Mth.sqrt(x * x + z * z);
        float f1 = (float) (Math.atan2(x, z) * 180.0F / Math.PI);
        float f2 = (float) (Math.atan2(y, f) * 180.0F / Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(f1 - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f2));
        submitNodeCollector.submitModel(this.model, this.modelState, poseStack, this.model.renderType(texture), packedLight, OverlayTexture.NO_OVERLAY, avatarRenderState.outlineColor, null);
        if (emissive != null) {
            submitNodeCollector.submitModel(this.model, this.modelState, poseStack, RenderTypes.eyes(emissive), packedLight, OverlayTexture.NO_OVERLAY, avatarRenderState.outlineColor, null);
        }
    }

    private static float snapToFace(float value) {
        return value > 0.5F ? 1.0F : 0.5F;
    }
}
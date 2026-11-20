package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class ProjectilesStuckLayer<M extends PlayerModel> extends StuckInBodyLayer<M, ArrowRenderState> {
    private final EntityType<?> entityType;
    private final Identifier emissiveTexture;

    public ProjectilesStuckLayer(LivingEntityRenderer<?, AvatarRenderState, M> renderer, EntityRendererProvider.Context context, EntityType<?> entityType, Identifier texture) {
        this(renderer, context, entityType, texture, null);
    }

    public ProjectilesStuckLayer(LivingEntityRenderer<?, AvatarRenderState, M> renderer, EntityRendererProvider.Context context, EntityType<?> entityType, Identifier texture, Identifier emissiveTexture) {
        super(renderer, new ArrowModel(context.bakeLayer(ModelLayers.ARROW)), new ArrowRenderState(), texture, PlacementStyle.IN_CUBE);
        this.entityType = entityType;
        this.emissiveTexture = emissiveTexture;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        state.id = this.modelState.hashCode();
        super.submit(poseStack, submitNodeCollector, lightCoords, state, yRot, xRot);
    }

    @Override
    public void submitStuckItem(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, float directionX, float directionY, float directionZ, int outlineColor) {
        super.submitStuckItem(poseStack, submitNodeCollector, lightCoords, directionX, directionY, directionZ, outlineColor);
        if (this.emissiveTexture != null) {
            submitNodeCollector.submitModel(this.model, this.modelState, poseStack, RenderTypes.eyes(this.emissiveTexture), lightCoords, OverlayTexture.NO_OVERLAY, outlineColor, null);
        }
    }

    @Override
    protected int numStuck(AvatarRenderState avatarRenderState) {
        List<EntityType<?>> list = avatarRenderState.getRenderData(AetherIIRenderers.STUCK_PROJECTILES_KEY);
        if (list != null && !list.isEmpty()) {
            List<EntityType<?>> filteredList = list.stream().filter(type -> type == this.entityType).toList();
            return filteredList.size();
        }
        return 0;
    }
}
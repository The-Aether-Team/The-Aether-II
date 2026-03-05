package com.aetherteam.aetherii.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

public abstract class MultiBabyModelRenderer<T extends Mob, S extends LivingEntityRenderState, M extends EntityModel<S>, MDefault extends M, MBaby extends M> extends MobRenderer<T, S, M> {
    public MultiBabyModelRenderer(EntityRendererProvider.Context context, MDefault defaultModel, float shadowRadius) {
        super(context, defaultModel, shadowRadius);
    }

    @Override
    public void submit(S entity, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        this.model = this.getModel(entity);
        super.submit(entity, poseStack, submitNodeCollector, cameraRenderState);
    }

    public M getModel(S entity) {
        return entity.isBaby ? this.getBabyModel(entity) : this.getDefaultModel(entity);
    }

    public abstract MDefault getDefaultModel(S entity);

    public abstract MBaby getBabyModel(S entity);

    @Override
    public Identifier getTextureLocation(S entity) {
        return entity.isBaby ? this.getBabyTexture(entity) : this.getDefaultTexture(entity);
    }

    public abstract Identifier getDefaultTexture(S entity);

    public abstract Identifier getBabyTexture(S entity);
}
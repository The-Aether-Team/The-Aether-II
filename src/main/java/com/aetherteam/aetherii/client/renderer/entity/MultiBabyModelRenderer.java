package com.aetherteam.aetherii.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public abstract class MultiBabyModelRenderer<T extends Mob, S extends LivingEntityRenderState, M extends EntityModel<S>, MDefault extends M, MBaby extends M> extends MobRenderer<T, S, M> {
    public MultiBabyModelRenderer(EntityRendererProvider.Context context, MDefault defaultModel, float shadowRadius) {
        super(context, defaultModel, shadowRadius);
    }

    @Override
    public void render(S entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = this.getModel(entity);
        super.render(entity, poseStack, buffer, packedLight);
    }

    public M getModel(S entity) {
        return entity.isBaby ? this.getBabyModel(entity) : this.getDefaultModel(entity);
    }

    public abstract MDefault getDefaultModel(S entity);

    public abstract MBaby getBabyModel(S entity);

    @Override
    public ResourceLocation getTextureLocation(S entity) {
        return entity.isBaby ? this.getBabyTexture(entity) : this.getDefaultTexture(entity);
    }

    public abstract ResourceLocation getDefaultTexture(S entity);

    public abstract ResourceLocation getBabyTexture(S entity);
}
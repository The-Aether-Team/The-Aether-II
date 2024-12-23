package com.aetherteam.aetherii.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public abstract class MultiBabyModelRenderer<T extends Mob, S extends LivingEntityRenderState, M extends EntityModel<S>, N extends M, O extends M> extends MobRenderer<T, S, M> {
    public MultiBabyModelRenderer(EntityRendererProvider.Context context, N defaultModel, float shadowRadius) {
        super(context, defaultModel, shadowRadius);
    }

    @Override
    public void render(S entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = this.getModel(entity);
        super.render(entity, poseStack, buffer, packedLight);
    }

    public M getModel(S entity) {
        return entity.isBaby ? this.getBabyModel() : this.getDefaultModel();
    }

    public abstract N getDefaultModel();

    public abstract O getBabyModel();

    @Override
    public ResourceLocation getTextureLocation(S entity) {
        return entity.isBaby ? this.getBabyTexture() : this.getDefaultTexture();
    }

    public abstract ResourceLocation getDefaultTexture();

    public abstract ResourceLocation getBabyTexture();
}
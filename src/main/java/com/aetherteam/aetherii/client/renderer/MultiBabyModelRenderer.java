package com.aetherteam.aetherii.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public abstract class MultiBabyModelRenderer<T extends Mob, M extends EntityModel<T>, N extends M, O extends M> extends MobRenderer<T, M> {
    public MultiBabyModelRenderer(EntityRendererProvider.Context context, N defaultModel, float shadowRadius) {
        super(context, defaultModel, shadowRadius);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = this.getModel(entity);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public M getModel(T entity) {
        return entity.isBaby() ? this.getBabyModel() : this.getDefaultModel();
    }

    public abstract N getDefaultModel();

    public abstract O getBabyModel();

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.isBaby() ? this.getBabyTexture() : this.getDefaultTexture();
    }

    public abstract ResourceLocation getDefaultTexture();

    public abstract ResourceLocation getBabyTexture();
}
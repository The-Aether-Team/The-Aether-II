package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MoaRenderer<T extends Moa> extends MultiBabyModelRenderer<T, EntityModel<T>, MoaModel<T>, MoaBabyModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa.png");
    private static final ResourceLocation BABY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_baby.png");
    private final MoaModel<T> defaultModel;
    private final MoaBabyModel<T> babyModel;

    public MoaRenderer(EntityRendererProvider.Context context) {
        super(context, new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA)), 0.5F);
        this.defaultModel = new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA));
        this.babyModel = new MoaBabyModel<>(context.bakeLayer(AetherIIModelLayers.MOA_BABY));
    }

    @Override
    public MoaModel<T> getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public MoaBabyModel<T> getBabyModel() {
        return this.babyModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getBabyTexture() {
        return BABY_TEXTURE;
    }
}
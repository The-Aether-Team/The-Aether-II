package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaFeathersLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaKeratinLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MoaRenderer extends MultiBabyModelRenderer<Moa, EntityModel<Moa>, MoaModel<Moa>, MoaBabyModel<Moa>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_base.png");
    private static final ResourceLocation BABY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_baby.png");
    private final MoaModel<Moa> defaultModel;
    private final MoaBabyModel<Moa> babyModel;

    public MoaRenderer(EntityRendererProvider.Context context) {
        super(context, new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA)), 0.5F);
        this.defaultModel = new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA));
        this.babyModel = new MoaBabyModel<>(context.bakeLayer(AetherIIModelLayers.MOA_BABY));
        this.addLayer(new MoaKeratinLayer(this));
        this.addLayer(new MoaFeathersLayer(this));
        this.addLayer(new MoaEyesLayer(this));
    }

    @Override
    public MoaModel<Moa> getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public MoaBabyModel<Moa> getBabyModel() {
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
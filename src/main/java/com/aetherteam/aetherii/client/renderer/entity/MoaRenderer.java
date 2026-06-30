package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaFeathersLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaKeratinLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaSaddleLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaSaddlebagLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MoaRenderer extends MultiBabyModelRenderer<Moa, EntityModel<Moa>, MoaModel<Moa>, MoaBabyModel<Moa>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/moa_base.png");
    private static final ResourceLocation BABY_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/moa/moa_baby_base.png");
    public static final ResourceLocation MOA_FEATHER_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_feather.png");
    public static final ResourceLocation MOA_EYES_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_eyes.png");
    public static final ResourceLocation MOA_KERATIN_SHEET = new ResourceLocation(AetherII.MODID, "textures/atlas/moa_keratin.png");
    private final MoaModel<Moa> defaultModel;
    private final MoaBabyModel<Moa> babyModel;

    public MoaRenderer(EntityRendererProvider.Context context) {
        super(context, new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA)), 0.5F);
        this.defaultModel = new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA));
        this.babyModel = new MoaBabyModel<>(context.bakeLayer(AetherIIModelLayers.MOA_BABY));
        this.addLayer(new MoaKeratinLayer(this));
        this.addLayer(new MoaFeathersLayer(this));
        this.addLayer(new MoaEyesLayer(this));
        this.addLayer(new MoaSaddleLayer(this, context.getModelSet()));
        this.addLayer(new MoaSaddlebagLayer(this, context.getModelSet()));
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

    @Override
    public ResourceLocation getTextureLocation(Moa moa) {
        ResourceLocation fallback = moa.isBaby() ? BABY_TEXTURE : TEXTURE;
        return specialTextureOr(moa, fallback);
    }

    public static boolean hasSpecialTexture(Moa moa) {
        return moa.getSpecialVariant()
                .map(variant -> moa.isBaby() ? variant.getBabyTexture() != null : variant.getDefaultTexture() != null)
                .orElse(false);
    }

    private static ResourceLocation specialTextureOr(Moa moa, ResourceLocation fallback) {
        if (moa == null) {
            return fallback;
        }
        return moa.getSpecialVariant()
                .map(variant -> moa.isBaby() ? variant.getBabyTexture() : variant.getDefaultTexture())
                .filter(texture -> texture != null)
                .orElse(fallback);
    }
}

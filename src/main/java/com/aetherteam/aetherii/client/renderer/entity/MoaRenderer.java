package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaFeathersLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.MoaKeratinLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MoaRenderer extends MultiBabyModelRenderer<Moa, MoaRenderState, EntityModel<MoaRenderState>, MoaModel<MoaRenderState>, MoaBabyModel<MoaRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_base.png");
    private static final ResourceLocation BABY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/moa_baby.png");
    private final MoaModel<MoaRenderState> defaultModel;
    private final MoaBabyModel<MoaRenderState> babyModel;

    public MoaRenderer(EntityRendererProvider.Context context) {
        super(context, new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA)), 0.5F);
        this.defaultModel = new MoaModel<>(context.bakeLayer(AetherIIModelLayers.MOA));
        this.babyModel = new MoaBabyModel<>(context.bakeLayer(AetherIIModelLayers.MOA_BABY));
        this.addLayer(new MoaKeratinLayer(this, context.getModelManager()));
        this.addLayer(new MoaFeathersLayer(this, context.getModelManager()));
        this.addLayer(new MoaEyesLayer(this, context.getModelManager()));
    }

    @Override
    public MoaRenderState createRenderState() {
        return new MoaRenderState();
    }

    @Override
    public void extractRenderState(Moa moa, MoaRenderState renderState, float p_361157_) {
        super.extractRenderState(moa, renderState, p_361157_);
        renderState.sitting = moa.isSitting();
        renderState.saddle = moa.isSaddled();
        renderState.flyAmount = moa.getFlyAmount(p_361157_);
        renderState.featherColor = moa.getFeatherColor();
        renderState.keratinColor = moa.getKeratinColor();
        renderState.eyeColor = moa.getEyeColor();
        renderState.featherShape = moa.getFeatherShape();
    }

    @Override
    public MoaModel<MoaRenderState> getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public MoaBabyModel<MoaRenderState> getBabyModel() {
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
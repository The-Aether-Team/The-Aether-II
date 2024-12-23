package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SheepuffWoolLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SheepuffRenderState;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SheepuffRenderer extends MobRenderer<Sheepuff, SheepuffRenderState, SheepuffModel<SheepuffRenderState>> {
    private static final ResourceLocation SHEEPUFF_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");

    public SheepuffRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepuffModel<>(context.bakeLayer(AetherIIModelLayers.SHEEPUFF)), 0.7F);
        this.addLayer(new SheepuffWoolLayer(this));
    }

    @Override
    public SheepuffRenderState createRenderState() {
        return new SheepuffRenderState();
    }

    @Override
    public void extractRenderState(Sheepuff sheepuff, SheepuffRenderState renderState, float p_361157_) {
        super.extractRenderState(sheepuff, renderState, p_361157_);
        renderState.headEatAngleScale = sheepuff.getHeadEatAngleScale(p_361157_);
        renderState.headEatPositionScale = sheepuff.getHeadEatPositionScale(p_361157_);
        renderState.isSheared = sheepuff.isSheared();
        renderState.woolColor = sheepuff.getColor();
        renderState.id = sheepuff.getId();
    }

    @Override
    public ResourceLocation getTextureLocation(SheepuffRenderState renderState) {
        return SHEEPUFF_TEXTURE;
    }

}

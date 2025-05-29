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
    public void extractRenderState(Sheepuff sheepuff, SheepuffRenderState renderState, float partialTick) {
        super.extractRenderState(sheepuff, renderState, partialTick);
        renderState.headEatAngleScale = sheepuff.getHeadEatAngleScale(partialTick);
        renderState.headEatPositionScale = sheepuff.getHeadEatPositionScale(partialTick);
        renderState.isSheared = sheepuff.isSheared();
        renderState.woolColor = sheepuff.getColor();
        renderState.id = sheepuff.getId();
        renderState.onGround = sheepuff.onGround();
        renderState.puff = sheepuff.getPuffed();
    }

    @Override
    public ResourceLocation getTextureLocation(SheepuffRenderState renderState) {
        return SHEEPUFF_TEXTURE;
    }

}

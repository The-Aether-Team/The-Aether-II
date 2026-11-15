package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.LykonScarfLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.LykonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.LykonRenderState;
import com.aetherteam.aetherii.entity.passive.Lykon;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class LykonRenderer extends AgeableMobRenderer<Lykon, LykonRenderState, LykonModel<LykonRenderState>> {
    private static final Identifier LYKON_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/lykon/lykon.png");
    private static final Identifier LYKON_BABY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/lykon/lykon_baby.png");

    public LykonRenderer(EntityRendererProvider.Context context) {
        super(context, new LykonModel<>(context.bakeLayer(AetherIIModelLayers.LYKON)), new LykonModel(context.bakeLayer(AetherIIModelLayers.LYKON)), 0.5F);
        this.addLayer(new LykonScarfLayer(this, context.getModelSet()));
    }

    @Override
    public LykonRenderState createRenderState() {
        return new LykonRenderState();
    }

    @Override
    public void extractRenderState(Lykon lykon, LykonRenderState renderState, float partialTick) {
        super.extractRenderState(lykon, renderState, partialTick);
        renderState.tame = lykon.isTame();
        renderState.collarColor = lykon.getCollarColor();
    }


    @Override
    public Identifier getTextureLocation(LykonRenderState renderState) {
        return LYKON_LOCATION;
    }
}
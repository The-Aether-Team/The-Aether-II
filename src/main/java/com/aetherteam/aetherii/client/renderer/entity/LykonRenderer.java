package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.LykonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.LykonRenderState;
import com.aetherteam.aetherii.entity.passive.Lykon;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class LykonRenderer extends AgeableMobRenderer<Lykon, LykonRenderState, EntityModel<LykonRenderState>> {
    private static final Identifier LYKON_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/lykon/lykon.png");
    private static final Identifier LYKON_BABY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/lykon/lykon_baby.png");

    public LykonRenderer(EntityRendererProvider.Context context) {
        super(context, new LykonModel<>(context.bakeLayer(AetherIIModelLayers.LYKON)), new LykonModel(context.bakeLayer(AetherIIModelLayers.LYKON)), 0.5F);
    }

    @Override
    public LykonRenderState createRenderState() {
        return new LykonRenderState();
    }

    @Override
    public void extractRenderState(Lykon lykon, LykonRenderState renderState, float partialTick) {
        super.extractRenderState(lykon, renderState, partialTick);
    }


    @Override
    public Identifier getTextureLocation(LykonRenderState renderState) {
        return LYKON_LOCATION;
    }
}
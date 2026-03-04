package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.EdwardModel;
import com.aetherteam.aetherii.client.renderer.entity.state.EdwardRenderState;
import com.aetherteam.aetherii.entity.npc.outpost.Edward;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class EdwardRenderer extends MobRenderer<Edward, EdwardRenderState, EdwardModel> {
    private static final Identifier EDWARD_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/npcs/edward/edward.png");

    public EdwardRenderer(EntityRendererProvider.Context context) {
        super(context, new EdwardModel(context.bakeLayer(AetherIIModelLayers.EDWARD)), 0.5F);
    }

    @Override
    public EdwardRenderState createRenderState() {
        return new EdwardRenderState();
    }

    @Override
    public void extractRenderState(Edward edward, EdwardRenderState renderState, float partialTicks) {
        super.extractRenderState(edward, renderState, partialTicks);
        renderState.isSitting = edward.isSitting();
    }

    @Override
    public Identifier getTextureLocation(EdwardRenderState state) {
        return EDWARD_TEXTURE;
    }
}
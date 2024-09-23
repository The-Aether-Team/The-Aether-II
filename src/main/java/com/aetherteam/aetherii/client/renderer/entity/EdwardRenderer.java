package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.EdwardModel;
import com.aetherteam.aetherii.entity.npc.outpost.Edward;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EdwardRenderer extends MobRenderer<Edward, EdwardModel> {
    private static final ResourceLocation EDWARD_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/npcs/edward/edward.png");

    public EdwardRenderer(EntityRendererProvider.Context context) {
        super(context, new EdwardModel(context.bakeLayer(AetherIIModelLayers.EDWARD)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Edward entity) {
        return EDWARD_TEXTURE;
    }
}

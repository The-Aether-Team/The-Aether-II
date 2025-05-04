package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalotonModel;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalotonModel;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaloton;
import com.aetherteam.aetherii.entity.monster.GravititeTaloton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class GravititeTalotonRenderer extends MobRenderer<GravititeTaloton, LivingEntityRenderState, GravititeTalotonModel> {
    private static final ResourceLocation GRAVITITE_TALOTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/gravitite_taloton/gravitite_taloton.png");

    public GravititeTalotonRenderer(EntityRendererProvider.Context context) {
        super(context, new GravititeTalotonModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_TALOTON)), 0.5F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return GRAVITITE_TALOTON_TEXTURE;
    }
}

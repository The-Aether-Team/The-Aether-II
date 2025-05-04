package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ArkeniumTalotonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.CockatriceEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalotonModel;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaloton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class ArkeniumTalotonRenderer extends MobRenderer<ArkeniumTaloton, LivingEntityRenderState, ArkeniumTalotonModel> {
    private static final ResourceLocation ARKENIUM_TALOTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/arkenium_taloton/arkenium_taloton.png");

    public ArkeniumTalotonRenderer(EntityRendererProvider.Context context) {
        super(context, new ArkeniumTalotonModel(context.bakeLayer(AetherIIModelLayers.ARKENIUM_TALOTON)), 0.5F);
        this.addLayer(new ArkeniumTalotonEyesLayer(this));
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return ARKENIUM_TALOTON_TEXTURE;
    }
}

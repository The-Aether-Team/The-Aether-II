package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SheepuffWoolLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SheepuffRenderer<T extends Sheepuff> extends MobRenderer<T, SheepuffModel<T>> {
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");

    public SheepuffRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepuffModel(context.bakeLayer(AetherModelLayers.SHEEPUFF)), 0.7F);
        this.addLayer(new SheepuffWoolLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T sheepuff) {
        return SHEEPUFF_TEXTURE;
    }
}

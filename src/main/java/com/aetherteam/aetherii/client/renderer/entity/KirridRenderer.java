package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.model.KirridBabyModel;
import com.aetherteam.aetherii.client.renderer.model.KirridModel;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KirridRenderer<T extends Kirrid> extends MultiBabyModelRenderer<T, EntityModel<T>, KirridModel<T>, KirridBabyModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields.png");
    private static final ResourceLocation BABY_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby.png");
    private final KirridModel<T> defaultModel;
    private final KirridBabyModel<T> babyModel;


    public KirridRenderer(EntityRendererProvider.Context context) {
        super(context, new KirridModel<>(context.bakeLayer(AetherModelLayers.KIRRID)), 0.5F);
        this.defaultModel = new KirridModel<>(context.bakeLayer(AetherModelLayers.KIRRID));
        this.babyModel = new KirridBabyModel<>(context.bakeLayer(AetherModelLayers.KIRRID_BABY));
    }

    @Override
    public KirridModel<T> getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public KirridBabyModel<T> getBabyModel() {
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
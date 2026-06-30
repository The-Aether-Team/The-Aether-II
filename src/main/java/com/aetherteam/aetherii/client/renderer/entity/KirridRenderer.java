package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.layers.KirridWoolLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.AbstractKirridModel;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KirridRenderer extends MultiBabyModelRenderer<Kirrid, EntityModel<Kirrid>, AbstractKirridModel, AbstractKirridBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final AbstractKirridModel defaultModel;
    private final AbstractKirridBabyModel babyModel;

    public KirridRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractKirridModel) preset.getDefaultModel(context), 0.5F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (AbstractKirridModel) preset.getDefaultModel(context);
        this.babyModel = (AbstractKirridBabyModel) preset.getBabyModel(context);
        this.addLayer(new KirridWoolLayer(this));
    }

    @Override
    public AbstractKirridModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public AbstractKirridBabyModel getBabyModel() {
        return this.babyModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return this.defaultTexture;
    }

    @Override
    public ResourceLocation getBabyTexture() {
        return this.babyTexture;
    }
}
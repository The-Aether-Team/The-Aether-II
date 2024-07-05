package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiModel;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurrukaiRenderer extends MultiBabyModelRenderer<Burrukai, EntityModel<Burrukai>, AbstractBurrukaiModel, AbstractBurrukaiBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final AbstractBurrukaiModel defaultModel;
    private final AbstractBurrukaiBabyModel babyModel;

    public BurrukaiRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (AbstractBurrukaiModel) preset.getDefaultModel(context), 0.75F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (AbstractBurrukaiModel) preset.getDefaultModel(context);
        this.babyModel = (AbstractBurrukaiBabyModel) preset.getBabyModel(context);
    }

    @Override
    public AbstractBurrukaiModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public AbstractBurrukaiBabyModel getBabyModel() {
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
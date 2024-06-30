package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurrukaiRenderer extends MultiBabyModelRenderer<Burrukai, EntityModel<Burrukai>, BurrukaiModel, BurrukaiBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final BurrukaiModel defaultModel;
    private final BurrukaiBabyModel babyModel;

    public BurrukaiRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (BurrukaiModel) preset.getDefaultModel(context), 0.75F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (BurrukaiModel) preset.getDefaultModel(context);
        this.babyModel = (BurrukaiBabyModel) preset.getBabyModel(context);
    }

    @Override
    public BurrukaiModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public BurrukaiBabyModel getBabyModel() {
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
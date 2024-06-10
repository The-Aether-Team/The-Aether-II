package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.KirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.KirridModel;
import com.aetherteam.aetherii.entity.passive.kirrid.Kirrid;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KirridRenderer extends MultiBabyModelRenderer<Kirrid, EntityModel<Kirrid>, KirridModel, KirridBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final KirridModel defaultModel;
    private final KirridBabyModel babyModel;

    public KirridRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (KirridModel) preset.getDefaultModel(context), 0.5F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (KirridModel) preset.getDefaultModel(context);
        this.babyModel = (KirridBabyModel) preset.getBabyModel(context);
    }

    @Override
    public KirridModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public KirridBabyModel getBabyModel() {
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
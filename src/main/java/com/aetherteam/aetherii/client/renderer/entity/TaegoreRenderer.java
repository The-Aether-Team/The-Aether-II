package com.aetherteam.aetherii.client.renderer.entity;


import com.aetherteam.aetherii.client.renderer.BiomeVariantPresets;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.AbstractBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.entity.passive.Burrukai;
import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TaegoreRenderer extends MultiBabyModelRenderer<Taegore, EntityModel<Taegore>, TaegoreModel, TaegoreBabyModel> {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation babyTexture;
    private final TaegoreModel defaultModel;
    private final TaegoreBabyModel babyModel;

    public TaegoreRenderer(EntityRendererProvider.Context context, BiomeVariantPresets preset) {
        super(context, (TaegoreModel) preset.getDefaultModel(context), 0.55F);
        this.defaultTexture = preset.getDefaultTexture();
        this.babyTexture = preset.getBabyTexture();
        this.defaultModel = (TaegoreModel) preset.getDefaultModel(context);
        this.babyModel = (TaegoreBabyModel) preset.getBabyModel(context);
    }

    @Override
    public TaegoreModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public TaegoreBabyModel getBabyModel() {
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
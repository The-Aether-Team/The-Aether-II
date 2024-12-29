package com.aetherteam.aetherii.data.resources.builders;

import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class AetherIIModelTemplates {

    public AetherIIModelTemplates() {
    }

    public static ModelTemplate create(TextureSlot... textures) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), textures);
    }

    public static ModelTemplate create(String p_386521_, TextureSlot... textures) {
        return new ModelTemplate(Optional.of(ModelLocationUtils.decorateBlockModelLocation(p_386521_)), Optional.empty(), textures);
    }

    public static ModelTemplate createItem(String p_388248_, TextureSlot... textures) {
        return new ModelTemplate(Optional.of(ModelLocationUtils.decorateItemModelLocation(p_388248_)), Optional.empty(), textures);
    }

    public static ModelTemplate createItem(String p_386727_, String p_387707_, TextureSlot... textures) {
        return new ModelTemplate(Optional.of(ModelLocationUtils.decorateItemModelLocation(p_386727_)), Optional.of(p_387707_), textures);
    }

    public static ModelTemplate create(String p_386833_, String p_386662_, TextureSlot... textures) {
        return new ModelTemplate(Optional.of(ModelLocationUtils.decorateBlockModelLocation(p_386833_)), Optional.of(p_386662_), textures);
    }
}
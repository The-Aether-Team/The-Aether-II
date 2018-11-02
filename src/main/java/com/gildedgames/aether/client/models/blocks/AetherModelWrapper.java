package com.gildedgames.aether.client.models.blocks;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.function.Function;

public class AetherModelWrapper implements IModel
{
    private IModel original;

    public AetherModelWrapper(IModel original)
    {
        this.original = original;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        IBakedModel parent = original.bake(state, format, bakedTextureGetter::apply);
        return new ModelBakedAether(this, parent);
    }
}

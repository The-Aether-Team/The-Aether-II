package com.gildedgames.aether.client.models.blocks;

import com.gildedgames.aether.client.ClientProxy;
import com.gildedgames.aether.client.texture.IMetadataSectionAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelBakedAether implements IBakedModel
{
    private AetherModelWrapper wrapper;
    private IBakedModel parent;

    public ModelBakedAether(AetherModelWrapper wrapper, IBakedModel parent)
    {
        this.wrapper = wrapper;
        this.parent = parent;
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
    {
        List<BakedQuad> parentQuads = parent.getQuads(state, side, rand);
        //TODO: Cache quads to improve performance
        List<BakedQuad> customQuads = new ArrayList<>();
        parentQuads.forEach(quad ->
        {
            IMetadataSectionAether meta = null;
            try
            {
                meta = ClientProxy.getMetadata(quad.getSprite());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            if (meta != null && meta.getGlowAmount() != 0)
            {
                CustomQuad customQuad = CustomQuad.from(quad);
                customQuad.setLight(meta.getGlowAmount());
                customQuads.add(customQuad.build());
            } else
            {
                customQuads.add(quad);
            }
        });
        return customQuads;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return parent.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return parent.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return parent.isBuiltInRenderer();
    }

    @Override
    @Nonnull
    public TextureAtlasSprite getParticleTexture()
    {
        return parent.getParticleTexture();
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides()
    {
        return ItemOverrideList.NONE;
    }
}

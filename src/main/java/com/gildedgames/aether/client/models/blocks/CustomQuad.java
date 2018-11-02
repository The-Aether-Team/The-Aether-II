package com.gildedgames.aether.client.models.blocks;


import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUsage;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CustomQuad
{
    private final Builder builder;
    private final Vector3f[] vertPos;
    private final Vector2f[] vertUv;
    private int light;

    private CustomQuad(Vector3f[] verts, Vector2f[] uvs, Builder builder, TextureAtlasSprite sprite)
    {
        this.vertPos = verts;
        this.vertUv = uvs;
        this.builder = builder;
    }

    public static CustomQuad from(BakedQuad quad)
    {
        Builder builder = new Builder(quad.getFormat(), quad.getSprite());
        quad.pipe(builder);
        return builder.build();
    }

    public CustomQuad setLight(int light)
    {
        this.light = light;
        return this;
    }

    public BakedQuad build()
    {
        @Nonnull VertexFormat format = this.builder.vertexFormat;
        boolean hasLightmap = (this.light > 0) && !FMLClientHandler.instance().hasOptifine();
        if (hasLightmap)
        {
            if (format == DefaultVertexFormats.ITEM)
            {
                format = DefaultVertexFormats.BLOCK;
            } else if (!format.getElements().contains(DefaultVertexFormats.TEX_2S))
            {
                format = new VertexFormat(format).addElement(DefaultVertexFormats.TEX_2S);
            }
        }

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setQuadOrientation(this.builder.quadOrientation);
        builder.setQuadTint(this.builder.quadTint);
        builder.setApplyDiffuseLighting(this.builder.applyDiffuseLighting);
        builder.setTexture(this.builder.sprite);

        for (int v = 0; v < 4; v++)
        {
            for (int i = 0; i < format.getElementCount(); i++)
            {
                VertexFormatElement ele = format.getElement(i);
                switch (ele.getUsage())
                {
                    case UV:
                        if (ele.getIndex() == 1)
                        {
                            builder.put(i, ((float) light * 0x20) / 0xFFFF, ((float) light * 0x20) / 0xFFFF);
                        } else if (ele.getIndex() == 0)
                        {
                            Vector2f uv = vertUv[v];
                            builder.put(i, uv.x, uv.y, 0, 1);
                        }
                        break;
                    case POSITION:
                        Vector3f p = vertPos[v];
                        builder.put(i, p.x, p.y, p.z, 1);
                        break;
                    default:
                        builder.put(i, this.builder.data.get(ele.getUsage()).get(v));
                }
            }
        }

        return builder.build();
    }

    public static class Builder implements IVertexConsumer
    {

        private final VertexFormat vertexFormat;
        private final TextureAtlasSprite sprite;

        private int quadTint = -1;

        private EnumFacing quadOrientation;

        private boolean applyDiffuseLighting;
        private ListMultimap<EnumUsage, float[]> data = MultimapBuilder.enumKeys(EnumUsage.class).arrayListValues().build();

        public Builder(VertexFormat vertexFormat, TextureAtlasSprite sprite)
        {
            this.vertexFormat = vertexFormat;
            this.sprite = sprite;
        }

        @Override
        public VertexFormat getVertexFormat()
        {
            return vertexFormat;
        }

        @Override
        public void setQuadTint(int tint)
        {
            quadTint = tint;
        }

        @Override
        public void setQuadOrientation(EnumFacing orientation)
        {
            quadOrientation = orientation;
        }

        @Override
        public void setApplyDiffuseLighting(boolean diffuse)
        {
            applyDiffuseLighting = diffuse;
        }

        @Override
        public void put(int element, @Nullable float... data)
        {
            if (data == null) return;
            float[] copy = new float[data.length];
            System.arraycopy(data, 0, copy, 0, data.length);
            VertexFormatElement ele = vertexFormat.getElement(element);
            this.data.put(ele.getUsage(), copy);
        }

        public CustomQuad build()
        {
            Vector3f[] verts = fromData(data.get(EnumUsage.POSITION), 3);
            Vector2f[] uvs = fromData(data.get(EnumUsage.UV), 2);
            return new CustomQuad(verts, uvs, this, sprite);
        }

        @SuppressWarnings("unchecked")
        private <T extends Vector> T[] fromData(List<float[]> data, int size)
        {
            Vector[] ret = size == 2 ? new Vector2f[data.size()] : new Vector3f[data.size()];
            for (int i = 0; i < data.size(); i++)
            {
                ret[i] = size == 2 ? new Vector2f(data.get(i)[0], data.get(i)[1]) : new Vector3f(data.get(i)[0], data.get(i)[1], data.get(i)[2]);
            }
            return (T[]) ret;
        }

        @Override
        public void setTexture(TextureAtlasSprite texture)
        {
            //ignore
        }
    }

}

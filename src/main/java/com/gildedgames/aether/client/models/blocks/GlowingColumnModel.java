package com.gildedgames.aether.client.models.blocks;

import com.gildedgames.aether.client.models.blocks.baked.GlowingColumnBakedModel;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GlowingColumnModel implements IModel
{

	private final VertexFormat format = new VertexFormat();

	public ResourceLocation top, side, top_overlay, side_overlay;

	public List<ResourceLocation> textures;

	public GlowingColumnModel(ResourceLocation top, ResourceLocation side, ResourceLocation top_overlay, ResourceLocation side_overlay)
	{
		this.top = top;
		this.side = side;
		this.top_overlay = top_overlay;
		this.side_overlay = side_overlay;

		this.textures = Lists.newArrayList();

		this.textures.add(this.top);
		this.textures.add(this.side);
		this.textures.add(this.top_overlay);
		this.textures.add(this.side_overlay);
	}

	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return Collections.emptyList();
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return this.textures;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		for (VertexFormatElement e : format.getElements())
		{
			this.format.addElement(e);
		}

		this.format.addElement(DefaultVertexFormats.TEX_2S);

		return new GlowingColumnBakedModel(this.format, bakedTextureGetter, top, side, top_overlay, side_overlay);
	}

	@Override
	public IModelState getDefaultState()
	{
		return TRSRTransformation.identity();
	}
}

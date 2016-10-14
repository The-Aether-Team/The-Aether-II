package com.gildedgames.aether.client.models.blocks;

import com.gildedgames.aether.client.models.blocks.baked.GlowingBlockBakedModel;
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

public class GlowingBlockModel implements IModel
{

	private final VertexFormat format = new VertexFormat();

	public ResourceLocation base, overlay;

	public List<ResourceLocation> textures;

	public GlowingBlockModel(ResourceLocation base, ResourceLocation overlay)
	{
		this.base = base;
		this.overlay = overlay;

		this.textures = Lists.newArrayList();

		this.textures.add(this.base);
		this.textures.add(this.overlay);
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

		return new GlowingBlockBakedModel(this.format, bakedTextureGetter, base, overlay);
	}

	@Override
	public IModelState getDefaultState()
	{
		return TRSRTransformation.identity();
	}
}

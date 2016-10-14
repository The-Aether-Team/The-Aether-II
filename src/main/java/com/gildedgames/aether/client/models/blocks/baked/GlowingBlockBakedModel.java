package com.gildedgames.aether.client.models.blocks.baked;

import com.google.common.base.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlowingBlockBakedModel implements IBakedModel
{

	private VertexFormat format;
	private TextureAtlasSprite overlay, base;

	public GlowingBlockBakedModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, ResourceLocation base, ResourceLocation overlay)
	{
		this.format = format;

		this.base = bakedTextureGetter.apply(base);
		this.overlay = bakedTextureGetter.apply(overlay);
	}

	private void putVertex(UnpackedBakedQuad.Builder builder, IBlockState state, Vec3d normal, EnumFacing side, double x, double y, double z, TextureAtlasSprite sprite, float u, float v, boolean hasBrightness, int brightness)
	{
		for (int e = 0; e < format.getElementCount(); e++)
		{
			switch (format.getElement(e).getUsage())
			{
			case POSITION:
				builder.put(e, (float) x, (float) y, (float) z);
				break;
			case COLOR:
				builder.put(e, 1.0F, 1.0F, 1.0F, 1.0F);
				break;
			case UV:
				if (format.getElement(e).getIndex() == 1)
				{
					if (hasBrightness)
					{
						float blockLight = ((float) ((brightness) & 15) * 32) / 65535; //Found this in Forge
						float skyLight = ((float) ((brightness >> 20) & 15) * 32) / 65535; //Found this in Forge
						builder.put(e, blockLight, skyLight);
					}
					else
					{
						builder.put(e, 0, 1f);
					}
				}
				else
				{
					u = sprite.getInterpolatedU(u);
					v = sprite.getInterpolatedV(v);
					builder.put(e, u, v);
				}
				break;
			case NORMAL:
				builder.put(e, (float)side.getFrontOffsetX(), (float)side.getFrontOffsetY(), (float)side.getFrontOffsetZ(), 0f);
				break;
			default:
				builder.put(e);
				break;
			}
		}
	}

	private BakedQuad createQuad(IBlockState state, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, EnumFacing side, TextureAtlasSprite sprite, boolean hasBrightness, int brightness)
	{
		Vec3d normal = v1.subtract(v2).crossProduct(v3.subtract(v2));

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

		builder.setTexture(sprite);

		putVertex(builder, state, normal, side, v1.xCoord, v1.yCoord, v1.zCoord, sprite, 16, 16, hasBrightness, brightness);
		putVertex(builder, state, normal, side, v2.xCoord, v2.yCoord, v2.zCoord, sprite, 16, 0, hasBrightness, brightness);
		putVertex(builder, state, normal, side, v3.xCoord, v3.yCoord, v3.zCoord, sprite, 0, 0, hasBrightness, brightness);
		putVertex(builder, state, normal, side, v4.xCoord, v4.yCoord, v4.zCoord, sprite, 0, 16, hasBrightness, brightness);

		return builder.build();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		if (side != null)
		{
			return Collections.emptyList();
		}

		List<BakedQuad> quads = new ArrayList<>();

		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 0.0D), EnumFacing.DOWN, this.base, true, 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 0.0D), EnumFacing.UP, this.base, true, 0));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), EnumFacing.SOUTH, this.base, true, 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), EnumFacing.NORTH, this.base, true, 0));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), EnumFacing.WEST, this.base, true, 0));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 0.0D, 1.0D), EnumFacing.EAST, this.base, true, 0));

		int brightness = 235;

		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 0.0D), EnumFacing.DOWN, this.overlay, true, brightness));
		quads.add(createQuad(state, new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 0.0D), EnumFacing.UP, this.overlay, true, brightness));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 0.0D, 1.0D), EnumFacing.SOUTH, this.overlay, true, brightness));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), EnumFacing.NORTH, this.overlay, true, brightness));
		quads.add(createQuad(state, new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), EnumFacing.WEST, this.overlay, true, brightness));
		quads.add(createQuad(state, new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 0.0D, 1.0D), EnumFacing.EAST, this.overlay, true, brightness));

		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return true;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.base;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}

}

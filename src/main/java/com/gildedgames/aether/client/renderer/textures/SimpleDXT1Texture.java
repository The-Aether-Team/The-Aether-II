package com.gildedgames.aether.client.renderer.textures;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.EXTTextureCompressionS3TC;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.image.BufferedImage;
import java.io.IOException;

// Uses S3TC DXT1 compression for simple monochrome textures with cutout alpha
public class SimpleDXT1Texture extends AbstractTexture
{
	protected final ResourceLocation textureLocation;

	public SimpleDXT1Texture(ResourceLocation textureResourceLocation)
	{
		this.textureLocation = textureResourceLocation;
	}

	@Override
	public void loadTexture(IResourceManager resourceManager) throws IOException
	{
		this.deleteGlTexture();

		IResource res = null;

		try
		{
			res = resourceManager.getResource(this.textureLocation);

			BufferedImage image = TextureUtil.readBufferedImage(res.getInputStream());

			int textureId = this.getGlTextureId();

			GlStateManager.bindTexture(textureId);
			GlStateManager.glTexImage2D(GL11.GL_TEXTURE_2D, 0, EXTTextureCompressionS3TC.GL_COMPRESSED_RGBA_S3TC_DXT1_EXT, image.getWidth(), image.getHeight(), 0, GL12.GL_BGRA,
					GL12.GL_UNSIGNED_INT_8_8_8_8_REV, null);

			TextureUtil.uploadTextureImageSub(textureId, image, 0, 0, false, false);
		}
		finally
		{
			IOUtils.closeQuietly(res);
		}
	}
}

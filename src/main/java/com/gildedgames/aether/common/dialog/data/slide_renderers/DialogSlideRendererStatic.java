package com.gildedgames.aether.common.dialog.data.slide_renderers;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Draws static images.
 *
 * Slide Data is interpreted as follows:
 *
 * - ResourceLocation of texture
 * - (optional) Rendering scale
 */
public class DialogSlideRendererStatic implements IDialogSlideRenderer
{
	private ResourceLocation slideTexture;

	private double scale = 1.0;

	private int width, height;

	@Override
	public void setup(IDialogSlide slide)
	{
		if (!slide.getSlideData().isPresent())
		{
			return;
		}

		Map<String, String> data = slide.getSlideData().get();

		if (data.containsKey("resource"))
		{
			this.slideTexture = new ResourceLocation(data.get("resource"));
		}

		if (data.containsKey("scale"))
		{
			this.scale = Double.valueOf(data.get("scale"));
		}

		if (data.containsKey("width"))
		{
			this.width = Integer.valueOf(data.get("width"));
		}

		if (data.containsKey("height"))
		{
			this.height = Integer.valueOf(data.get("height"));
		}
	}

	@Override
	public void draw(IDialogSlide slide, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks)
	{
		if (this.slideTexture == null)
		{
			return;
		}

		TextureManager textureManager = Minecraft.getMinecraft().renderEngine;

		double scaledWidth = this.width * this.scale;
		double scaledHeight = this.height * this.scale;

		GlStateManager.pushMatrix();

		GlStateManager.translate((screenWidth / 2) - (scaledWidth / 2), screenHeight - 90 - scaledHeight, 0);
		GlStateManager.scale(this.scale, this.scale, this.scale);

		textureManager.bindTexture(this.slideTexture);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		GlStateManager.popMatrix();
	}

	public static class Deserializer implements JsonDeserializer<DialogSlideRendererStatic>
	{
		@Override
		public DialogSlideRendererStatic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogSlideRendererStatic();
		}
	}
}

package com.gildedgames.aether.common.dialog.data.slide_renderers;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Draws static images.
 *
 * Slide Data is interpreted as follows:
 *
 * - ResourceLocation of texture
 * - (optional) Rendering scale
 */
public class DialogSlideRendererNOOP implements IDialogSlideRenderer
{
	@Override
	public void setup(IDialogSlide slide)
	{

	}

	@Override
	public void draw(IDialogSlide slide, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks)
	{

	}

	public static class Deserializer implements JsonDeserializer<DialogSlideRendererNOOP>
	{
		@Override
		public DialogSlideRendererNOOP deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogSlideRendererNOOP();
		}
	}
}

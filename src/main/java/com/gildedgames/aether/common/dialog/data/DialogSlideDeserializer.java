package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.common.dialog.data.slide_renderers.DialogSlideRendererStatic;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogSlideDeserializer implements JsonDeserializer<IDialogSlide>
{
	@Override
	public IDialogSlide deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return context.deserialize(json, DialogSlide.class);
	}
}

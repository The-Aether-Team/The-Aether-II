package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogSlideDeserializer implements JsonDeserializer<IDialogSlide>
{
	@Override
	public IDialogSlide deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return context.deserialize(json, DialogSlide.class);
	}
}

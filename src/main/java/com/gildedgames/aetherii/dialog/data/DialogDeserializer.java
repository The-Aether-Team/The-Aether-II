package com.gildedgames.aetherii.dialog.data;

import com.gildedgames.aetherii.api.dialog.IDialog;
import com.gildedgames.aetherii.dialog.Dialog;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogDeserializer implements JsonDeserializer<IDialog> {
	@Override
	public IDialog deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return context.deserialize(json, Dialog.class);
	}
}
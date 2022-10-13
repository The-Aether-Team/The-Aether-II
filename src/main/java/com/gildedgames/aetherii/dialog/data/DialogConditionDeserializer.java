package com.gildedgames.aetherii.dialog.data;

import com.gildedgames.aetherii.api.dialog.IDialogCondition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogConditionDeserializer implements JsonDeserializer<IDialogCondition> {
	private final HashMap<String, Class<? extends IDialogCondition>> conditions = new HashMap<>();

	public DialogConditionDeserializer() {
	}

	@Override
	public IDialogCondition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type")) {
			throw new JsonParseException("Missing required field 'type' for action");
		}

		final String type = root.get("type").getAsString();

		if (!this.conditions.containsKey(type)) {
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.conditions.get(type));
	}
}
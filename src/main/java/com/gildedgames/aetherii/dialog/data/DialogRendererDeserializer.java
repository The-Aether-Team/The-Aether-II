package com.gildedgames.aetherii.dialog.data;

import com.gildedgames.aetherii.api.dialog.IDialogRenderer;
import com.gildedgames.aetherii.dialog.data.render.DialogRendererStatic;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogRendererDeserializer implements JsonDeserializer<IDialogRenderer> {
	private final HashMap<String, Class<? extends IDialogRenderer>> renderers = new HashMap<>();

	public DialogRendererDeserializer() {
		this.renderers.put("static", DialogRendererStatic.class);
	}

	@Override
	public IDialogRenderer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject root = json.getAsJsonObject();

		if (!root.has("type")) {
			throw new JsonParseException("Missing required field 'type' for action");
		}

		String type = root.get("type").getAsString();

		if (!this.renderers.containsKey(type)) {
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.renderers.get(type));
	}
}
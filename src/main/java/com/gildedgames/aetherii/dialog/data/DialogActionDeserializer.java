package com.gildedgames.aetherii.dialog.data;

import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.gildedgames.aetherii.dialog.data.actions.DialogActionExit;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogActionDeserializer implements JsonDeserializer<IDialogAction> {
	private final HashMap<String, Class<? extends IDialogAction>> actions = new HashMap<>();

	public DialogActionDeserializer() {
		this.actions.put("exit", DialogActionExit.class);
	}

	@Override
	public IDialogAction deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type")) {
			throw new JsonParseException("Missing required field 'type' for action");
		}

		final String type = root.get("type").getAsString();

		if (!this.actions.containsKey(type)) {
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.actions.get(type));
	}
}
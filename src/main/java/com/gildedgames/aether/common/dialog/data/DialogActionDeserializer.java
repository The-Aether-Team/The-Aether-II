package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.common.dialog.data.actions.DialogActionExit;
import com.gildedgames.aether.common.dialog.data.actions.DialogActionNavigate;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogActionDeserializer implements JsonDeserializer<IDialogAction>
{
	private final HashMap<String, Class<? extends IDialogAction>> actions = new HashMap<>();

	public DialogActionDeserializer()
	{
		this.actions.put("navigate", DialogActionNavigate.class);
		this.actions.put("exit", DialogActionExit.class);
	}

	@Override
	public IDialogAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for action");
		}

		String type = root.get("type").getAsString();

		if (!this.actions.containsKey(type))
		{
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.actions.get(type));
	}
}

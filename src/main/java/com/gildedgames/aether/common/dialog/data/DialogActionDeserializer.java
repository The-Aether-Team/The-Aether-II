package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.common.dialog.data.actions.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogActionDeserializer implements JsonDeserializer<IDialogAction>
{
	private final HashMap<String, Class<? extends IDialogAction>> actions = new HashMap<>();

	public DialogActionDeserializer()
	{
		this.actions.put("back", DialogActionNavigateBack.class);
		this.actions.put("navigate", DialogActionNavigate.class);
		this.actions.put("navigate_start", DialogActionNavigateToStart.class);
		this.actions.put("navigate_scene", DialogActionNavigateScene.class);
		this.actions.put("exit", DialogActionExit.class);
		this.actions.put("go_up_tower", DialogActionNecromancerGoUpTower.class);
	}

	@Override
	public IDialogAction deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for action");
		}

		final String type = root.get("type").getAsString();

		if (!this.actions.containsKey(type))
		{
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.actions.get(type));
	}
}

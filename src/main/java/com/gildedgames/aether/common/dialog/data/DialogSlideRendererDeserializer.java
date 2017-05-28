package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.common.dialog.data.actions.DialogActionExit;
import com.gildedgames.aether.common.dialog.data.slide_renderers.DialogSlideRendererStatic;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogSlideRendererDeserializer implements JsonDeserializer<IDialogSlideRenderer>
{
	private final HashMap<String, Class<? extends IDialogSlideRenderer>> renderers = new HashMap<>();

	public DialogSlideRendererDeserializer()
	{
		this.renderers.put("static", DialogSlideRendererStatic.class);
	}

	@Override
	public IDialogSlideRenderer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for action");
		}

		String type = root.get("type").getAsString();

		if (!this.renderers.containsKey(type))
		{
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.renderers.get(type));
	}
}

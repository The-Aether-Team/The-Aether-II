package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogCondition;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionHasSleptInBed;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionIsChristmasEvent;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionIsNewYearsEvent;
import com.gildedgames.aether.common.dialog.data.conditions.DialogConditionReturningToOutpost;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogConditionDeserializer implements JsonDeserializer<IDialogCondition>
{
	private final HashMap<String, Class<? extends IDialogCondition>> conditions = new HashMap<>();

	public DialogConditionDeserializer()
	{
		this.conditions.put("returningToOutpost", DialogConditionReturningToOutpost.class);
		this.conditions.put("hasSleptInBed", DialogConditionHasSleptInBed.class);
		this.conditions.put("isChristmasEvent", DialogConditionIsChristmasEvent.class);
		this.conditions.put("isNewYearsEvent", DialogConditionIsNewYearsEvent.class);
	}

	@Override
	public IDialogCondition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for action");
		}

		final String type = root.get("type").getAsString();

		if (!this.conditions.containsKey(type))
		{
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.conditions.get(type));
	}
}

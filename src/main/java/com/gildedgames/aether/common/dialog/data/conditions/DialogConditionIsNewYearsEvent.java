package com.gildedgames.aether.common.dialog.data.conditions;

import com.gildedgames.aether.api.dialog.IDialogCondition;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.AetherCelebrations;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogConditionIsNewYearsEvent implements IDialogCondition
{
	private final boolean validate;

	private DialogConditionIsNewYearsEvent(boolean validate)
	{
		this.validate = validate;
	}

	@Override
	public boolean isMet(IDialogController controller)
	{
		if (controller.getDialogPlayer().world.isRemote())
		{
			return false;
		}

		return AetherCelebrations.isNewYearsEvent() == this.validate;
	}

	public static class Deserializer implements JsonDeserializer<DialogConditionIsNewYearsEvent>
	{
		@Override
		public DialogConditionIsNewYearsEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogConditionIsNewYearsEvent(json.getAsJsonObject().get("validate").getAsBoolean());
		}
	}
}

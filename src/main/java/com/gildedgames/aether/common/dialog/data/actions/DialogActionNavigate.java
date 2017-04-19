package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;

public class DialogActionNavigate implements IDialogAction
{
	private final String target;

	private DialogActionNavigate(String target)
	{
		Validate.notNull(target, "Target cannot be null");

		this.target = target;
	}

	@Override
	public void performAction(IDialogController controller)
	{
		controller.navigateNode(this.target);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionNavigate>
	{
		@Override
		public DialogActionNavigate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionNavigate(json.getAsJsonObject().get("target").getAsString());
		}
	}
}

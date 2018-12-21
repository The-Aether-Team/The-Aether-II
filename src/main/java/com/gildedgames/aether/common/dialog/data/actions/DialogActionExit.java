package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogActionExit implements IDialogAction
{
	private DialogActionExit()
	{
	}

	@Override
	public void performAction(IDialogController controller)
	{
		controller.closeScene(true);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionExit>
	{
		@Override
		public DialogActionExit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionExit();
		}
	}
}

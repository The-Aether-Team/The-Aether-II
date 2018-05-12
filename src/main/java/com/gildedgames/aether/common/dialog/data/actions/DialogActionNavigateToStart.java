package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogActionNavigateToStart implements IDialogAction
{
	private DialogActionNavigateToStart()
	{

	}

	@Override
	public void performAction(IDialogController controller)
	{
		controller.navigateNode(controller.getCurrentScene().getStartingNode().getIdentifier());
	}

	public static class Deserializer implements JsonDeserializer<DialogActionNavigateToStart>
	{
		@Override
		public DialogActionNavigateToStart deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionNavigateToStart();
		}
	}
}

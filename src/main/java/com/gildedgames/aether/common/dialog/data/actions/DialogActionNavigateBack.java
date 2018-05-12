package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;

public class DialogActionNavigateBack implements IDialogAction
{
	private DialogActionNavigateBack()
	{

	}

	@Override
	public void performAction(IDialogController controller)
	{
		controller.navigateBack();
	}

	public static class Deserializer implements JsonDeserializer<DialogActionNavigateBack>
	{
		@Override
		public DialogActionNavigateBack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionNavigateBack();
		}
	}
}

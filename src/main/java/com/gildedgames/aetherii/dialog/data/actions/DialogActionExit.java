package com.gildedgames.aetherii.dialog.data.actions;

import com.gildedgames.aetherii.api.TalkableController;
import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Type;

public class DialogActionExit implements IDialogAction {
	private DialogActionExit() {
	}

	@Override
	public void performAction(TalkableController controller) {
		Minecraft.getInstance().setScreen(null);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionExit> {
		@Override
		public DialogActionExit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return new DialogActionExit();
		}
	}
}
package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;

public class DialogActionNavigateScene implements IDialogAction
{
	private final ResourceLocation loc;

	private final String target;

	private DialogActionNavigateScene(ResourceLocation loc, String target)
	{
		Validate.notNull(loc, "Location cannot be null");
		Validate.notNull(target, "Target cannot be null");

		this.loc = loc;
		this.target = target;
	}

	@Override
	public void performAction(IDialogController controller)
	{
		controller.openScene(this.loc, this.target);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionNavigateScene>
	{
		@Override
		public DialogActionNavigateScene deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionNavigateScene(new ResourceLocation(json.getAsJsonObject().get("loc").getAsString()), json.getAsJsonObject().get("target").getAsString());
		}
	}
}

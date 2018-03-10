package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.entities.living.npc.EntityNecromancer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DialogActionNecromancerGoUpTower implements IDialogAction
{
	private DialogActionNecromancerGoUpTower()
	{

	}

	@Override
	public void performAction(final IDialogController controller)
	{
		if (controller.getTalkingEntity() instanceof EntityNecromancer)
		{
			final EntityNecromancer necromancer = (EntityNecromancer) controller.getTalkingEntity();

			if (necromancer.getUpTowerTask() != null)
			{
				necromancer.getUpTowerTask().setShouldExecute(true);
			}
		}
	}

	public static class Deserializer implements JsonDeserializer<DialogActionNecromancerGoUpTower>
	{
		@Override
		public DialogActionNecromancerGoUpTower deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
				throws JsonParseException
		{
			return new DialogActionNecromancerGoUpTower();
		}
	}
}

package com.gildedgames.aether.common.dialog.data.conditions;

import com.gildedgames.aether.api.dialog.IDialogCondition;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class DialogConditionReturningToOutpost implements IDialogCondition
{
	private final boolean validate;

	private DialogConditionReturningToOutpost(boolean validate)
	{
		this.validate = validate;
	}

	@Override
	public boolean isMet(IDialogController controller)
	{
		if (controller.getDialogPlayer().world.isRemote)
		{
			return false;
		}

		EntityPlayer player = controller.getDialogPlayer();
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		BlockPos bedPos = player.getBedLocation(((EntityPlayerMP) player).dimension);
		final EntityPlayerMP mp = (EntityPlayerMP) player;

		if (bedPos != null)
		{
			bedPos = EntityPlayer.getBedSpawnLocation(mp.getServerWorld(), bedPos, mp.isSpawnForced(mp.dimension));
		}

		if (bedPos == null && this.validate)
		{
			return false;
		}

		return playerAether.getProgressModule().hasReturnedToBed() == this.validate;
	}

	public static class Deserializer implements JsonDeserializer<DialogConditionReturningToOutpost>
	{
		@Override
		public DialogConditionReturningToOutpost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogConditionReturningToOutpost(json.getAsJsonObject().get("validate").getAsBoolean());
		}
	}
}

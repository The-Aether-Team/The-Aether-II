package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
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

public class DialogActionTravelToBed implements IDialogAction
{
	private DialogActionTravelToBed()
	{
	}

	@Override
	public void performAction(IDialogController controller)
	{
		if (controller.getDialogPlayer().world.isRemote)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(controller.getDialogPlayer());

			playerAether.getDialogController().closeScene(true);

			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) controller.getDialogPlayer();
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		BlockPos bedPos = player.getBedLocation(player.dimension);

		if (bedPos != null)
		{
			bedPos = EntityPlayer.getBedSpawnLocation(player.getServerWorld(), bedPos, player.isSpawnForced(player.dimension));
		}

		if (bedPos != null)
		{
			playerAether.getProgressModule().setBeforeReturnToBed(player.getPosition());

			player.connection.setPlayerLocation(bedPos.getX(), bedPos.getY(), bedPos.getZ(), 0, 0);

			playerAether.getProgressModule().setHasReturnedToBed(true);
		}
	}

	public static class Deserializer implements JsonDeserializer<DialogActionTravelToBed>
	{
		@Override
		public DialogActionTravelToBed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionTravelToBed();
		}
	}
}

package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class DialogActionTravelToLastOutpost implements IDialogAction
{
	private DialogActionTravelToLastOutpost()
	{
	}

	@Override
	public void performAction(IDialogController controller)
	{
		if (controller.getDialogPlayer().world.isRemote)
		{
			AetherCore.PROXY.turnOffScreen();

			return;
		}

		ServerPlayerEntity player = (ServerPlayerEntity) controller.getDialogPlayer();
		PlayerAether playerAether = PlayerAether.getPlayer(player);
		PlayerProgressModule progressModule = playerAether.getModule(PlayerProgressModule.class);

		BlockPos p = progressModule.getBeforeReturnToBed();

		player.connection.setPlayerLocation(p.getX(), p.getY(), p.getZ(), 0, 0);

		progressModule.setBeforeReturnToBed(null);
		progressModule.setHasReturnedToBed(false);
	}

	public static class Deserializer implements JsonDeserializer<DialogActionTravelToLastOutpost>
	{
		@Override
		public DialogActionTravelToLastOutpost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionTravelToLastOutpost();
		}
	}
}

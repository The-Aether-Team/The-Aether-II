package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class DialogActionOpenShop implements IDialogAction
{
	private DialogActionOpenShop()
	{

	}

	@Override
	public void performAction(IDialogController controller)
	{
		EntityPlayer player = controller.getDialogPlayer();
		BlockPos pos = player.getPosition();

		if (!player.world.isRemote && controller.getTalkingNPC() != null && controller.getTalkingNPC().getShopInstance() != null)
		{
			player.openGui(AetherCore.MOD_ID, AetherGuiHandler.DIALOG_SHOP_ID, player.world, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public static class Deserializer implements JsonDeserializer<DialogActionOpenShop>
	{
		@Override
		public DialogActionOpenShop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionOpenShop();
		}
	}
}

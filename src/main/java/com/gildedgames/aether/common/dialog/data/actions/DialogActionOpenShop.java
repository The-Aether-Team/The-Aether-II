package com.gildedgames.aether.common.dialog.data.actions;

import com.gildedgames.aether.api.dialog.IDialogAction;
import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.Type;

public class DialogActionOpenShop implements IDialogAction
{
	private final int shopIndex;

	private DialogActionOpenShop(int shopIndex)
	{
		this.shopIndex = shopIndex;
	}

	@Override
	public void performAction(IDialogController controller)
	{
		EntityPlayer player = controller.getDialogPlayer();

		if (!player.world.isRemote && controller.getTalkingCharacter() != null && controller.getTalkingCharacter().getShopInstanceGroup() != null)
		{
			IShopInstance shopInstance = controller.getTalkingCharacter().getShopInstanceGroup().getShopInstance(this.shopIndex);

			if (shopInstance != null)
			{
				player.openGui(AetherCore.MOD_ID, AetherGuiHandler.DIALOG_SHOP_ID, player.world, this.shopIndex, 0, 0);
			}
		}
	}

	public static class Deserializer implements JsonDeserializer<DialogActionOpenShop>
	{
		@Override
		public DialogActionOpenShop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionOpenShop(json.getAsJsonObject().get("shopIndex").getAsInt());
		}
	}
}

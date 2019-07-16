package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketChangeCoinAmount implements IMessage
{
	private double coinCount;

	public PacketChangeCoinAmount(double coinCount)
	{
		this.coinCount = coinCount;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.coinCount = buf.readDouble();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeDouble(this.coinCount);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketChangeCoinAmount>
	{
		@Override
		protected void onMessage(PacketChangeCoinAmount message, ServerPlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);
			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);

			double val = aePlayer.getModule(PlayerCurrencyModule.class).getCurrencyValue();

			if (message.coinCount <= val && tradeModule.isTrading() && !tradeModule.isLockedIn())
			{
				tradeModule.setCoinAmount(message.coinCount);

				NetworkingAether.sendPacketToPlayer(new PacketChangeCoinAmount(message.coinCount), (ServerPlayerEntity) tradeModule.getTarget().getEntity());
			}
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketChangeCoinAmount>
	{
		@Override
		protected void onMessage(PacketChangeCoinAmount message, ClientPlayerEntity player)
		{
			Screen screen = Minecraft.getInstance().currentScreen;

			if (screen instanceof GuiTrade)
			{
				((GuiTrade) screen).setCoinCount(message.coinCount);
			}
		}
	}
}

package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketChangeCoinAmount implements IMessage
{

	private double coinCount;

	public PacketChangeCoinAmount() {
	}

	public PacketChangeCoinAmount(double coinCount)
	{
		this.coinCount = coinCount;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.coinCount = buf.readDouble();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeDouble(this.coinCount);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketChangeCoinAmount, IMessage>
	{
		@Override
		public IMessage onMessage(PacketChangeCoinAmount message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);
			PlayerTradeModule tradeModule = aePlayer.getTradingModule();

			double val = aePlayer.getCurrencyModule().getCurrencyValue();

			if (message.coinCount <= val && tradeModule.isTrading())
			{
				tradeModule.setCoinAmount(message.coinCount);
				NetworkingAether.sendPacketToPlayer(new PacketChangeCoinAmount(message.coinCount), (EntityPlayerMP) tradeModule.getTarget().getEntity());
			}

			return null;
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketChangeCoinAmount, IMessage>
	{
		@Override
		public IMessage onMessage(PacketChangeCoinAmount message, EntityPlayer player)
		{
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;

			if (screen instanceof GuiTrade)
			{
				((GuiTrade) screen).setCoinCount(message.coinCount);
			}

			return null;
		}
	}
}

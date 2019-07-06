package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketTradeMessage implements IMessage
{
	private String text;

	private int entityId;

	public PacketTradeMessage()
	{

	}

	public PacketTradeMessage(String message, EntityPlayer player)
	{
		this.text = message;
		this.entityId = player.getEntityId();
	}

	public PacketTradeMessage(String message)
	{
		this.text = message;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		ByteBufUtils.writeUTF8String(buf, this.text);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.text = ByteBufUtils.readUTF8String(buf);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeMessage, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeMessage message, EntityPlayer player)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.currentScreen instanceof GuiTrade)
			{
				GuiTrade tradeGui = (GuiTrade) mc.currentScreen;
				Entity entity = player.world.getEntityByID(message.entityId);

				if (message.entityId != 0 && entity instanceof EntityPlayer)
				{
					tradeGui.sendTradeMessage((EntityPlayer) entity, new TextComponentString(message.text));
				}
				else
				{
					ITextComponent comp = new TextComponentTranslation(message.text, tradeGui.getTrader());

					comp.setStyle(new Style().setColor(message.text.endsWith("warn") ? TextFormatting.RED : message.text.endsWith("safe") ? TextFormatting.YELLOW : TextFormatting.AQUA));

					tradeGui.sendMessage(comp);
				}
			}

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketTradeMessage, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeMessage message, EntityPlayer player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			if (aePlayer != null)
			{
				PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);

				if(tradeModule.isTrading() && message.text.length() <= 256)
				{
					NetworkingAether.sendPacketToPlayer(new PacketTradeMessage(message.text, player), tradeModule.getTargetMP());
				}
			}

			return null;
		}
	}
}

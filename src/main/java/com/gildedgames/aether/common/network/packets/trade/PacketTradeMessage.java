package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketTradeMessage implements IMessage
{
	private String text;

	private int entityId;

	public PacketTradeMessage(String message, PlayerEntity player)
	{
		this.text = message;
		this.entityId = player.getEntityId();
	}

	public PacketTradeMessage(String message)
	{
		this.text = message;
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		buf.writeInt(this.entityId);
		buf.writeString(this.text);
	}

	@Override
	public void fromBytes(PacketBuffer buf)
	{
		this.entityId = buf.readInt();
		this.text = buf.readString();
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeMessage>
	{
		@Override
		protected void onMessage(PacketTradeMessage message, ClientPlayerEntity player)
		{
			Minecraft mc = Minecraft.getInstance();

			if (mc.currentScreen instanceof GuiTrade)
			{
				GuiTrade tradeGui = (GuiTrade) mc.currentScreen;
				Entity entity = player.world.getEntityByID(message.entityId);

				if (message.entityId != 0 && entity instanceof PlayerEntity)
				{
					tradeGui.sendTradeMessage((PlayerEntity) entity, new StringTextComponent(message.text));
				}
				else
				{
					ITextComponent comp = new TranslationTextComponent(message.text, tradeGui.getTrader());

					comp.setStyle(new Style().setColor(message.text.endsWith("warn") ? TextFormatting.RED : message.text.endsWith("safe") ? TextFormatting.YELLOW : TextFormatting.AQUA));

					tradeGui.sendMessage(comp);
				}
			}

		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketTradeMessage>
	{
		@Override
		protected void onMessage(PacketTradeMessage message, ServerPlayerEntity player)
		{
			PlayerAether aePlayer = PlayerAether.getPlayer(player);

			PlayerTradeModule tradeModule = aePlayer.getModule(PlayerTradeModule.class);

			if(tradeModule.isTrading() && message.text.length() <= 256)
			{
				NetworkingAether.sendPacketToPlayer(new PacketTradeMessage(message.text, player), tradeModule.getTargetMP());
			}
		}
	}
}

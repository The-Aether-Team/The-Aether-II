package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.api.registry.tab.ITabGroupHandler;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Map;

public class PacketOpenTab implements IMessage
{

	private int tabGroupIndex, tabIndex;

	public PacketOpenTab()
	{
	}

	public PacketOpenTab(final ITab tab)
	{
		for (final Map.Entry<Integer, ITabGroupHandler> entry : AetherAPI.content().tabs().getRegisteredTabGroups().entrySet())
		{
			final int groupIndex = entry.getKey();

			final ITabGroupHandler handler = entry.getValue();

			for (final ITab groupTab : handler.getClientGroup().getTabs())
			{
				if (tab == groupTab)
				{
					this.tabGroupIndex = groupIndex;

					this.tabIndex = handler.getDiscriminant(tab);

					return;
				}
			}
		}
	}

	public PacketOpenTab(final int tabGroupIndex, final int tabIndex)
	{
		this.tabGroupIndex = tabGroupIndex;
		this.tabIndex = tabIndex;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.tabGroupIndex = buf.readInt();
		this.tabIndex = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.tabGroupIndex);
		buf.writeInt(this.tabIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOpenTab, PacketOpenTab>
	{
		@Override
		public PacketOpenTab onMessage(final PacketOpenTab message, final PlayerEntity player)
		{
			if (player instanceof ServerPlayerEntity)
			{
				if (message.tabGroupIndex < AetherAPI.content().tabs().getRegisteredTabGroups().size())
				{
					final ITabGroupHandler tabGroupHandler = AetherAPI.content().tabs().getRegisteredTabGroups().get(message.tabGroupIndex);

					if (tabGroupHandler == null)
					{
						return null;
					}

					final ITabGroup<ITab> tabGroup = tabGroupHandler.getServerGroup();

					if (message.tabIndex < tabGroup.getTabs().size())
					{
						final ITab tab = tabGroup.getTabs().get(message.tabIndex);
						tab.onOpen(player);
					}
				}
			}

			return null;
		}
	}
}

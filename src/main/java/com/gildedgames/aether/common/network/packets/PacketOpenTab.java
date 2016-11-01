package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.api.registry.tab.ITabGroupHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Map;

public class PacketOpenTab implements IMessage
{

	private int tabGroupIndex, tabIndex;

	public PacketOpenTab()
	{
	}

	public PacketOpenTab(ITab tab)
	{
		for (Map.Entry<Integer, ITabGroupHandler> entry : AetherAPI.tabs().getRegisteredTabGroups().entrySet())
		{
			int groupIndex = entry.getKey();

			ITabGroupHandler handler = entry.getValue();

			for (ITab groupTab : handler.getClientGroup().getTabs())
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

	public PacketOpenTab(int tabGroupIndex, int tabIndex)
	{
		this.tabGroupIndex = tabGroupIndex;
		this.tabIndex = tabIndex;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tabGroupIndex = buf.readInt();
		this.tabIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.tabGroupIndex);
		buf.writeInt(this.tabIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOpenTab, PacketOpenTab>
	{
		@Override
		public PacketOpenTab onMessage(PacketOpenTab message, EntityPlayer player)
		{
			if (player instanceof EntityPlayerMP)
			{
				if (message.tabGroupIndex < AetherAPI.tabs().getRegisteredTabGroups().size())
				{
					ITabGroupHandler tabGroupHandler = AetherAPI.tabs().getRegisteredTabGroups().get(message.tabGroupIndex);

					if (tabGroupHandler == null)
					{
						return null;
					}

					ITabGroup<ITab> tabGroup = tabGroupHandler.getServerGroup();

					if (message.tabIndex < tabGroup.getTabs().size())
					{
						ITab tab = tabGroup.getTabs().get(message.tabIndex);
						tab.onOpen(player);
					}
				}
			}

			return null;
		}
	}
}

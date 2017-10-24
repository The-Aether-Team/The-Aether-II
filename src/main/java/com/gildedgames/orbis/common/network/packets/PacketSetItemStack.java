package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetItemStack implements IMessage
{

	private ItemStack stack;

	public PacketSetItemStack()
	{

	}

	public PacketSetItemStack(final ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, this.stack);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetItemStack, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetItemStack message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.isCreative())
			{
				player.inventory.setItemStack(message.stack);
			}

			return null;
		}
	}
}

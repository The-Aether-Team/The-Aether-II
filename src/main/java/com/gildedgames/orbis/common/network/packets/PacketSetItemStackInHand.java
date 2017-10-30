package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetItemStackInHand implements IMessage
{

	private ItemStack stack;

	public PacketSetItemStackInHand()
	{

	}

	public PacketSetItemStackInHand(final ItemStack stack)
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

	public static class HandlerServer extends MessageHandlerServer<PacketSetItemStackInHand, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetItemStackInHand message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.isCreative())
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, message.stack);
			}

			return null;
		}
	}
}

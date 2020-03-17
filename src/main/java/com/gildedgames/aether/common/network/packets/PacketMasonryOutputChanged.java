package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMasonryOutputChanged implements IMessage
{
	private ItemStack outputStack;

	public PacketMasonryOutputChanged()
	{

	}

	public PacketMasonryOutputChanged(ItemStack stack)
	{
		this.outputStack = stack;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.outputStack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, this.outputStack);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketMasonryOutputChanged, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMasonryOutputChanged message, EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerMasonryBench)
			{
				ContainerMasonryBench container = (ContainerMasonryBench) player.openContainer;

				container.setOutput(message.outputStack);
			}

			return null;
		}
	}
}

package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PacketTradeInventory implements IMessage
{

	private final List<Pair<Integer, ItemStack>> changes = new ArrayList<>();

	private int decSlots;

	public PacketTradeInventory()
	{

	}

	public PacketTradeInventory(IInventory inventory)
	{
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack.isEmpty())
			{
				break;
			}

			this.changes.add(Pair.of(i, stack));
		}
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.decSlots = buf.readInt();

		final int count = buf.readByte();

		for (int i = 0; i < count; i++)
		{
			final int slot = buf.readByte();

			final ItemStack stack = ByteBufUtils.readItemStack(buf);

			this.changes.add(Pair.of(slot, stack));
		}
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.decSlots);
		buf.writeByte(this.changes.size());

		for (final Pair<Integer, ItemStack> pair : this.changes)
		{
			buf.writeByte(pair.getKey());

			ByteBufUtils.writeItemStack(buf, pair.getValue());
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeInventory, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTradeInventory message, EntityPlayer player)
		{
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;

			if (screen instanceof GuiTrade)
			{
				GuiTrade trade = (GuiTrade) screen;

				trade.setTradeOffer(message.changes);
			}

			return null;
		}
	}

}

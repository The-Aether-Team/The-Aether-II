package com.gildedgames.aether.common.network.packets.trade;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PacketTradeInventory implements IMessage
{

	private final List<Pair<Integer, ItemStack>> changes = new ArrayList<>();

	private int decSlots;

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
	public void fromBytes(final PacketBuffer buf)
	{
		this.decSlots = buf.readInt();

		final int count = buf.readByte();

		for (int i = 0; i < count; i++)
		{
			final int slot = buf.readByte();

			final ItemStack stack = buf.readItemStack();

			this.changes.add(Pair.of(slot, stack));
		}
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.decSlots);
		buf.writeByte(this.changes.size());

		for (final Pair<Integer, ItemStack> pair : this.changes)
		{
			buf.writeByte(pair.getKey());
			buf.writeItemStack(pair.getValue());
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketTradeInventory>
	{
		@Override
		protected void onMessage(PacketTradeInventory message, ClientPlayerEntity player)
		{
			Screen screen = Minecraft.getInstance().currentScreen;

			if (screen instanceof GuiTrade)
			{
				GuiTrade trade = (GuiTrade) screen;

				trade.setTradeOffer(message.changes);
			}
		}
	}

}

package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketEquipment implements IMessage
{
	private final List<Pair<Integer, ItemStack>> changes = new ArrayList<>();

	private int entityId;

	public PacketEquipment(final PlayerAether aePlayer)
	{
		this.entityId = aePlayer.getEntity().getEntityId();

		final IInventory inventory = aePlayer.getModule(PlayerEquipmentModule.class).getInventory();

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			this.changes.add(Pair.of(i, inventory.getStackInSlot(i)));
		}
	}

	public PacketEquipment(final Entity entity, final List<Pair<Integer, ItemStack>> changes)
	{
		this.entityId = entity.getEntityId();

		this.changes.addAll(changes);
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.entityId = buf.readInt();

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
		buf.writeInt(this.entityId);
		buf.writeByte(this.changes.size());

		for (final Pair<Integer, ItemStack> pair : this.changes)
		{
			buf.writeByte(pair.getKey());
			buf.writeItemStack(pair.getValue());
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketEquipment>
	{
		@Override
		protected void onMessage(PacketEquipment message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final Entity entity = player.world.getEntityByID(message.entityId);

			if (entity != null && PlayerAether.hasCapability(entity))
			{
				final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

				final IInventory inventory = aePlayer.getModule(PlayerEquipmentModule.class).getInventory();

				for (final Pair<Integer, ItemStack> pair : message.changes)
				{
					inventory.setInventorySlotContents(pair.getKey(), pair.getValue());
				}
			}
		}
	}
}

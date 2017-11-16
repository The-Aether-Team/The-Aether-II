package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.common.containers.util.StagedInventory;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PacketStagedInventoryChanged implements IMessage
{
	private final List<Pair<Integer, ItemStack>> changes = new ArrayList<>();

	private String locatorId;

	private int entityId;

	public PacketStagedInventoryChanged()
	{

	}

	public PacketStagedInventoryChanged(final PlayerAether aePlayer, final StagedInventory inventory)
	{
		this.entityId = aePlayer.getEntity().getEntityId();

		for (int i = 0; i < inventory.get().getSizeInventory(); i++)
		{
			this.changes.add(Pair.of(i, inventory.get().getStackInSlot(i)));
		}

		this.locatorId = inventory.getLocatorId();
	}

	public PacketStagedInventoryChanged(final Entity entity, final List<Pair<Integer, ItemStack>> changes, final String locatorId)
	{
		this.entityId = entity.getEntityId();

		this.changes.addAll(changes);
		this.locatorId = locatorId;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.entityId = buf.readInt();

		final int count = buf.readByte();

		for (int i = 0; i < count; i++)
		{
			final int slot = buf.readByte();

			final ItemStack stack = ByteBufUtils.readItemStack(buf);

			this.changes.add(Pair.of(slot, stack));
		}

		this.locatorId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeByte(this.changes.size());

		for (final Pair<Integer, ItemStack> pair : this.changes)
		{
			buf.writeByte(pair.getKey());

			ByteBufUtils.writeItemStack(buf, pair.getValue());
		}

		ByteBufUtils.writeUTF8String(buf, this.locatorId);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketStagedInventoryChanged, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketStagedInventoryChanged message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final Entity entity = player.world.getEntityByID(message.entityId);

			if (entity != null && PlayerAether.hasCapability(entity))
			{
				final PlayerAether aePlayer = PlayerAether.getPlayer(player);

				final IInventory inventory = StagedInventory.getLocator(message.locatorId).locate(aePlayer.getOrbisModule()).get();

				for (final Pair<Integer, ItemStack> pair : message.changes)
				{
					inventory.setInventorySlotContents(pair.getKey(), pair.getValue());
				}
			}

			return null;
		}
	}
}

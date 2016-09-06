package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.util.core.io.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentChangedPacket implements IMessage
{
	private final List<Pair<Integer, ItemStack>> changes = new ArrayList<>();

	private int entityId;

	public EquipmentChangedPacket()
	{

	}

	public EquipmentChangedPacket(Entity entity, int slot, ItemStack stack)
	{
		this.entityId = entity.getEntityId();

		this.changes.add(Pair.of(slot, stack));
	}

	public EquipmentChangedPacket(Entity entity, List<Pair<Integer, ItemStack>> changes)
	{
		this.entityId = entity.getEntityId();
		
		this.changes.addAll(changes);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		this.entityId = pBuf.readInt();

		int count = pBuf.readByte();

		for (int i = 0; i < count; i++)
		{
			int slot = pBuf.readByte();

			try
			{
				ItemStack stack = pBuf.readItemStackFromBuffer();

				this.changes.add(Pair.of(slot, stack));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		pBuf.writeInt(this.entityId);

		pBuf.writeByte(this.changes.size());

		for (Pair<Integer, ItemStack> pair : this.changes)
		{
			pBuf.writeByte(pair.getKey());
			pBuf.writeItemStackToBuffer(pair.getValue());
		}
	}

	public static class Handler extends MessageHandlerClient<EquipmentChangedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(EquipmentChangedPacket message, EntityPlayer player)
		{
			if (player == null || player.worldObj == null)
			{
				return null;
			}

			Entity entity = player.worldObj.getEntityByID(message.entityId);

			if (entity != null)
			{
				IPlayerAetherCapability aePlayer = PlayerAetherImpl.getPlayer(player);

				for (Pair<Integer, ItemStack> pair : message.changes)
				{
					aePlayer.getEquipmentInventory().setInventorySlotContents(pair.getKey(), pair.getValue());
				}
			}

			return null;
		}
	}
}

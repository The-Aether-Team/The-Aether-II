package com.gildedgames.aether.common.network.packets.flying;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.util.flying.advanced.EntityFlyingAdvanced;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetEntityPathRamps implements IMessage
{
	private int entityId;

	private FloatList speed, time;

	private boolean future;

	public PacketSetEntityPathRamps()
	{

	}

	public PacketSetEntityPathRamps(Entity entity, FloatList speed, FloatList time, boolean future)
	{
		this.entityId = entity.getEntityId();
		this.speed = speed;
		this.time = time;
		this.future = future;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer pb = new PacketBuffer(buf);

		this.entityId = pb.readInt();
		this.future = pb.readBoolean();

		this.speed = this.deserializeFloatList(buf);
		this.time = this.deserializeFloatList(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeBoolean(this.future);

		this.serializeFloatList(buf, this.speed);
		this.serializeFloatList(buf, this.time);
	}

	private void serializeFloatList(ByteBuf buf, FloatList list)
	{
		buf.writeByte(list.size());

		for (int i = 0; i < list.size(); i++)
		{
			buf.writeFloat(list.getFloat(i));
		}
	}

	private FloatList deserializeFloatList(ByteBuf buf)
	{
		int count = buf.readByte();

		FloatList list = new FloatArrayList(count);

		for (int i = 0; i < count; i++)
		{
			list.add(buf.readFloat());
		}

		return list;
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSetEntityPathRamps, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSetEntityPathRamps message, EntityPlayer player)
		{
			Entity entity = player.world.getEntityByID(message.entityId);

			if (entity == null)
			{
				AetherCore.LOGGER.warn("Tried to update flying path for entity {} but it does not exist in the world", message.entityId);

				return null;
			}

			if (!(entity instanceof EntityFlyingAdvanced))
			{
				AetherCore.LOGGER.warn("Tried to update flying path for entity {} but it doesn't subclass {} (entity class: {})", message.entityId,
						EntityFlyingAdvanced.class, entity.getClass());

				return null;
			}

			EntityFlyingAdvanced flyingEntity = (EntityFlyingAdvanced) entity;

			if (message.future)
			{
				flyingEntity.updateFutureSpeedMap(message.speed, message.time);
			}
			else
			{
				flyingEntity.updateSpeedMap(message.speed, message.time);
			}

			return null;
		}
	}
}

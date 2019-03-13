package com.gildedgames.aether.common.network.packets.flying;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.util.flying.advanced.EntityFlyingAdvanced;
import com.gildedgames.aether.common.entities.util.flying.advanced.FlightPath;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.vecmath.Point3d;

public class PacketSetEntityPath implements IMessage
{
	private int entityId;

	private FlightPath path;

	private boolean future;

	public PacketSetEntityPath()
	{

	}

	public PacketSetEntityPath(Entity entity, FlightPath path, boolean future)
	{
		this.entityId = entity.getEntityId();
		this.path = path;
		this.future = future;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer pb = new PacketBuffer(buf);

		this.entityId = pb.readInt();
		this.future = pb.readBoolean();

		boolean exists = pb.readBoolean();

		if (exists)
		{
			BlockPos startPos = pb.readBlockPos();

			Point3d startHandle = new Point3d(pb.readDouble(), pb.readDouble(), pb.readDouble());
			Point3d endHandle = new Point3d(pb.readDouble(), pb.readDouble(), pb.readDouble());

			BlockPos endPos = pb.readBlockPos();

			this.path = new FlightPath(startPos, startHandle, endHandle, endPos);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		boolean exists = this.path != null;

		PacketBuffer pb = new PacketBuffer(buf);

		pb.writeInt(this.entityId);
		pb.writeBoolean(this.future);

		pb.writeBoolean(exists);

		if (exists)
		{
			pb.writeBlockPos(this.path.getStartPos());

			Point3d startHandle = this.path.getStartHandle();

			pb.writeDouble(startHandle.getX());
			pb.writeDouble(startHandle.getY());
			pb.writeDouble(startHandle.getZ());

			Point3d endHandle = this.path.getEndHandle();

			pb.writeDouble(endHandle.getX());
			pb.writeDouble(endHandle.getY());
			pb.writeDouble(endHandle.getZ());

			pb.writeBlockPos(this.path.getEndPos());
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSetEntityPath, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSetEntityPath message, EntityPlayer player)
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
				flyingEntity.updateFutureFlyNav(message.path);
			}
			else
			{
				flyingEntity.updateFlyNav(message.path);
			}

			return null;
		}
	}
}

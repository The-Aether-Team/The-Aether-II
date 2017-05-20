package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSaveStructure implements IMessage
{
	private BlockPos pos;

	public PacketSaveStructure()
	{
	}

	public PacketSaveStructure(BlockPos pos)
	{
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSaveStructure, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSaveStructure message, EntityPlayer player)
		{
			if (!player.canUseCommand(2, ""))
			{
				AetherCore.LOGGER.warn("Player {} tried to send PacketStructureBuilder, but is not an operator. Ignoring...", player.getDisplayNameString());

				return null;
			}

			World world = player.getEntityWorld();

			TileEntityStructureBuilder te = (TileEntityStructureBuilder) world.getTileEntity(message.pos);

			if (te == null)
			{
				AetherCore.LOGGER.warn("Player {} tried to send PacketStructureBuilder, but the world coordinates {} are invalid. Ignoring...", player.getDisplayNameString(), message.pos);

				return null;
			}

			te.saveStructure(player);

			return null;
		}
	}
}

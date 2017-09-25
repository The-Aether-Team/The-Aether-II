package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.PacketUtilAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateStructure implements IMessage
{
	private BlockPos pos;

	private TileEntityStructureBuilder.Data data;

	public PacketUpdateStructure()
	{
	}

	public PacketUpdateStructure(final BlockPos pos, final TileEntityStructureBuilder.Data data)
	{
		this.data = data;
		this.pos = pos;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		this.data = new TileEntityStructureBuilder.Data();
		this.data.read(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());

		this.data.write(buf);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketUpdateStructure, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateStructure message, final EntityPlayer player)
		{
			if (!player.canUseCommand(2, ""))
			{
				AetherCore.LOGGER
						.warn("Player {} tried to send PacketStructureBuilder, but is not an operator. Ignoring...", player.getDisplayNameString());

				return null;
			}

			final World world = player.getEntityWorld();

			final TileEntityStructureBuilder te = (TileEntityStructureBuilder) world.getTileEntity(message.pos);

			if (te == null)
			{
				AetherCore.LOGGER.warn("Player {} tried to send PacketStructureBuilder, but the world coordinates {} are invalid. Ignoring...",
						player.getDisplayNameString(), message.pos);

				return null;
			}

			te.setStructureData(message.data);
			te.sendUpdatesToClients();

			return null;
		}
	}

	public static class HandlerClient extends MessageHandlerClient<PacketUpdateStructure, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateStructure message, final EntityPlayer player)
		{
			PacketUtilAether.displayStructureBuilderGui(player, message.pos, message.data);

			return null;
		}
	}
}

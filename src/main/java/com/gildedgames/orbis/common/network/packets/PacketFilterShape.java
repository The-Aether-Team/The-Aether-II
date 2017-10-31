package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockFilter;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketFilterShape implements IMessage
{
	private IShape shape;

	private BlockFilter filter;

	private NBTFunnel funnel;

	public PacketFilterShape()
	{

	}

	public PacketFilterShape(final IShape shape, final BlockFilter filter)
	{
		this.shape = shape;
		this.filter = filter;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.funnel = OrbisCore.io().createFunnel(tag);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.set("shape", this.shape);
		funnel.set("filter", this.filter);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketFilterShape, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketFilterShape message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IShape shape = message.funnel.get(player.world, "shape");
			final BlockFilter filter = message.funnel.get("filter");

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			filter.apply(shape, player.world, new CreationData(player.world, player));

			return null;
		}
	}
}

package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Set;

public class PacketCampfires implements IMessage
{

	private Set<BlockPosDimension> campfires;

	private NBTFunnel funnel;

	public PacketCampfires()
	{

	}

	public PacketCampfires(Set<BlockPosDimension> campfires)
	{
		this.campfires = campfires;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.funnel = new NBTFunnel(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		NBTFunnel funnel = new NBTFunnel(new NBTTagCompound());

		funnel.setSet("c", this.campfires);

		ByteBufUtils.writeTag(buf, funnel.getTag());
	}

	public static class HandlerClient extends MessageHandlerClient<PacketCampfires, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketCampfires message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			Set<BlockPosDimension> campfires = message.funnel.getSet("c");

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getCampfiresModule().setCampfiresActivated(campfires);

			return null;
		}
	}
}

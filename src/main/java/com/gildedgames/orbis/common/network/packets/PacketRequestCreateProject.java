package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.common.OrbisCore;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRequestCreateProject extends PacketMultipleParts
{

	private String name;

	private IProjectIdentifier projectIdentifier;

	public PacketRequestCreateProject()
	{

	}

	private PacketRequestCreateProject(final byte[] data)
	{
		super(data);
	}

	public PacketRequestCreateProject(final String name, final IProjectIdentifier projectIdentifier)
	{
		this.name = name;
		this.projectIdentifier = projectIdentifier;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.name = tag.getString("name");
		this.projectIdentifier = funnel.get("projectIdentifier");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setString("name", this.name);
		funnel.set("projectIdentifier", this.projectIdentifier);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketRequestCreateProject(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketRequestCreateProject, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketRequestCreateProject message, final EntityPlayer player)
		{
			if (player == null || player.world == null || !PlayerOrbisModule.get(player).inDeveloperMode())
			{
				return null;
			}

			if (!OrbisCore.getProjectManager().projectNameExists(message.name) && !OrbisCore.getProjectManager().projectExists(message.projectIdentifier))
			{
				OrbisCore.getProjectManager().createAndSaveProject(message.name, message.projectIdentifier);

				NetworkingAether.sendPacketToPlayer(new PacketOrbisSendProjectListing(), (EntityPlayerMP) player);
			}

			return null;
		}
	}
}

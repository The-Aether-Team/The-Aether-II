package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.common.OrbisCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOrbisRequestProject extends PacketMultipleParts
{

	private IProjectIdentifier project;

	public PacketOrbisRequestProject()
	{

	}

	private PacketOrbisRequestProject(final byte[] data)
	{
		super(data);
	}

	public PacketOrbisRequestProject(final IProjectIdentifier project)
	{
		this.project = project;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.project = funnel.get("project");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("project", this.project);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketOrbisRequestProject(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOrbisRequestProject, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisRequestProject message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IProject project = OrbisCore.getProjectManager().findProject(message.project);

			NetworkingAether.sendPacketToPlayer(new PacketOrbisSendProject(project), (EntityPlayerMP) player);

			return null;
		}
	}
}

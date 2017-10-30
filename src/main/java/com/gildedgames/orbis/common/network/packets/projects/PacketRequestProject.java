package com.gildedgames.orbis.common.network.packets.projects;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
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

public class PacketRequestProject extends PacketMultipleParts
{

	private IProjectIdentifier project;

	public PacketRequestProject()
	{

	}

	private PacketRequestProject(final byte[] data)
	{
		super(data);
	}

	public PacketRequestProject(final IProjectIdentifier project)
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
		return new PacketRequestProject(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketRequestProject, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketRequestProject message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			try
			{
				final IProject project = OrbisCore.getProjectManager().findProject(message.project);

				NetworkingAether.sendPacketToPlayer(new PacketSendProject(project), (EntityPlayerMP) player);
			}
			catch (OrbisMissingDataException | OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}

			return null;
		}
	}
}

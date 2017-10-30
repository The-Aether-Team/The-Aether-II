package com.gildedgames.orbis.common.network.packets.projects;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.client.gui.GuiViewProjects;
import com.gildedgames.orbis.common.OrbisCore;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;

public class PacketSendProjectListing extends PacketMultipleParts
{

	private List<String> projectNames;

	private List<IProject> projects;

	public PacketSendProjectListing()
	{
		OrbisCore.getProjectManager().refreshCache();

		this.projects = new ArrayList<>(OrbisCore.getProjectManager().getCachedProjects());
		this.projectNames = Lists.newArrayList();

		this.projects.forEach(p -> this.projectNames.add(p.getLocation().getName()));
	}

	private PacketSendProjectListing(final byte[] data)
	{
		super(data);
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.projectNames = funnel.getStringList("projectNames");
		this.projects = funnel.getList("projects");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.setStringList("projectNames", this.projectNames);
		funnel.setList("projects", this.projects);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketSendProjectListing(data);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketSendProjectListing, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSendProjectListing message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			/**
			 * Creates empty projects from the provided listings.
			 * This is so that the player can view the project
			 */
			for (int i = 0; i < message.projectNames.size(); i++)
			{
				final String name = message.projectNames.get(i);
				final IProject project = message.projects.get(i);

				final IProject existing = OrbisCore.getProjectManager().saveProjectIfDoesntExist(name, project);

				/**
				 * If the last date that the server project was changed is different to the
				 * local one, it will request to download that project from the server.
				 */
				if (existing != null && !existing.getMetadata().getLastChanged().equals(project.getMetadata().getLastChanged()) && existing.getMetadata()
						.isDownloaded())
				{
					project.getMetadata().setDownloading(true);
					NetworkingAether.sendPacketToServer(new PacketRequestProject(project.getProjectIdentifier()));
				}
			}

			OrbisCore.getProjectManager().refreshCache();

			if (Minecraft.getMinecraft().currentScreen instanceof GuiViewProjects)
			{
				final GuiViewProjects viewProjects = (GuiViewProjects) Minecraft.getMinecraft().currentScreen;

				viewProjects.refreshNavigator();
			}

			return null;
		}
	}
}

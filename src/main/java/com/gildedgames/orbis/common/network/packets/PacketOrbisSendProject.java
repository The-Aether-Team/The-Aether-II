package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis.management.IData;
import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.aether.api.orbis.management.IProjectCache;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.client.gui.GuiViewProjects;
import com.gildedgames.orbis.common.OrbisCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class PacketOrbisSendProject extends PacketMultipleParts
{

	private IProjectIdentifier project;

	private IProjectCache cache;

	private LocalDateTime lastChanged;

	public PacketOrbisSendProject()
	{

	}

	private PacketOrbisSendProject(final byte[] data)
	{
		super(data);
	}

	public PacketOrbisSendProject(final IProject project)
	{
		this.project = project.getProjectIdentifier();
		this.cache = project.getCache();
		this.lastChanged = project.getMetadata().getLastChanged();
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.project = funnel.get("project");
		this.cache = funnel.get("cache");
		this.lastChanged = funnel.getDate("lastChanged");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("project", this.project);
		funnel.set("cache", this.cache);
		funnel.setDate("lastChanged", this.lastChanged);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketOrbisSendProject(data);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOrbisSendProject, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketOrbisSendProject message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			try
			{
				final IProject project = OrbisCore.getProjectManager().findProject(message.project);

				if (project != null)
				{
					project.setCache(message.cache);

					/**
					 * Save all data to disk.
					 */
					for (final IData data : message.cache.getAllData())
					{
						final File file = new File(project.getLocation(), message.cache.getDataLocation(data.getMetadata().getIdentifier().getDataId()));

						try (FileOutputStream out = new FileOutputStream(file))
						{
							final NBTTagCompound tag = new NBTTagCompound();
							final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

							funnel.set("data", data);

							CompressedStreamTools.writeCompressed(tag, out);
						}
						catch (final IOException e)
						{
							AetherCore.LOGGER.error("Failed to save project data to disk", e);
						}
					}

					project.getMetadata().setDownloaded(true);
					project.getMetadata().setDownloading(false);

					project.getMetadata().setLastChanged(message.lastChanged);
				}

				AetherCore.LOGGER.debug("Project downloaded! " + project.getLocation().getName());
			}
			catch (final OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}

			if (Minecraft.getMinecraft().currentScreen instanceof GuiViewProjects)
			{
				final GuiViewProjects viewProjects = (GuiViewProjects) Minecraft.getMinecraft().currentScreen;

				viewProjects.refreshNavigator();
			}

			return null;
		}
	}
}

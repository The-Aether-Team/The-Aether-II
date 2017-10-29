package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.util.PacketMultipleParts;
import com.gildedgames.orbis.common.OrbisCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PacketSaveWorldObjectToProject extends PacketMultipleParts
{

	private IProjectIdentifier project;

	private int worldObjectId;

	private String location;

	public PacketSaveWorldObjectToProject()
	{

	}

	private PacketSaveWorldObjectToProject(final byte[] data)
	{
		super(data);
	}

	/**
	 *
	 * @param project The project that you want to save the object to.
	 * @param object The object we'll be fetching on the server and getting its data to save.
	 * @param location The location relative to the project directory.
	 */
	public PacketSaveWorldObjectToProject(final IProject project, final IWorldObject object, final String location)
	{
		this.project = project.getProjectIdentifier();
		this.worldObjectId = WorldObjectManager.get(object.getWorld()).getGroup(0).getID(object);
		this.location = location;
	}

	@Override
	public void read(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.project = funnel.get("project");
		this.worldObjectId = tag.getInteger("worldObjectId");
		this.location = tag.getString("location");
	}

	@Override
	public void write(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		funnel.set("project", this.project);
		tag.setInteger("worldObjectId", this.worldObjectId);
		tag.setString("location", this.location);

		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public PacketMultipleParts createPart(final byte[] data)
	{
		return new PacketSaveWorldObjectToProject(data);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSaveWorldObjectToProject, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSaveWorldObjectToProject message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			try
			{
				final IProject project = OrbisCore.getProjectManager().findProject(message.project);
				final IWorldObject worldObject = WorldObjectManager.get(player.world).getGroup(0).getObject(message.worldObjectId);

				if (project != null && worldObject.getData() != null)
				{
					worldObject.getData().preSaveToDisk(worldObject);

					final File file = new File(project.getLocation(), message.location);

					project.getCache().setData(worldObject.getData(), message.location);

					try (FileOutputStream out = new FileOutputStream(file))
					{
						final NBTTagCompound tag = new NBTTagCompound();
						final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

						funnel.set("data", worldObject.getData());

						CompressedStreamTools.writeCompressed(tag, out);
					}
					catch (final IOException e)
					{
						AetherCore.LOGGER.error("Failed to save project data to disk", e);
					}

					NetworkingAether.sendPacketToPlayer(new PacketOrbisSendProjectListing(), (EntityPlayerMP) player);
				}
			}
			catch (final OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}

			return null;
		}
	}
}

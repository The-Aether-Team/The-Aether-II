package com.gildedgames.aether.common.capabilities.instances;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.instances.IInstanceFactory;
import com.gildedgames.aether.api.capabilites.instances.IInstanceHandler;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.capabilites.instances.Instance;
import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketRegisterDimension;
import com.gildedgames.aether.common.network.packets.PacketUnregisterDimension;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.gildedgames.aether.common.world.util.TeleporterGeneric;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;

public class InstanceHandler<T extends Instance> implements IInstanceHandler<T>
{

	private final BiMap<Integer, T> instances = HashBiMap.create();

	private IInstanceFactory<T> factory;

	public InstanceHandler(IInstanceFactory<T> factory)
	{
		this.factory = factory;
	}

	@Override
	public T getInstance(int id)
	{
		return this.instances.get(id);
	}

	@Override
	public T createNew()
	{
		int dimensionId = this.getFreeDimID();

		DimensionManager.registerDimension(dimensionId, this.factory.dimensionType());

		if (AetherCore.isServer())
		{
			NetworkingAether.sendPacketToAllPlayers(new PacketRegisterDimension(this.factory.dimensionType(), dimensionId));
		}

		T instance = this.factory.createInstance(dimensionId, this);
		this.instances.put(dimensionId, instance);

		return instance;
	}

	@Override
	public void sendUnregisterInstancesPacket(EntityPlayerMP player)
	{
		if (!AetherCore.isServer())
		{
			return;
		}

		for (Entry<Integer, T> entry : this.instances.entrySet())
		{
			int dimId = entry.getKey();

			NetworkingAether.sendPacketToPlayer(new PacketUnregisterDimension(dimId), player);
		}
	}

	@Override
	public void unregisterInstances()
	{
		for (Entry<Integer, T> entry : this.instances.entrySet())
		{
			int dimId = entry.getKey();

			DimensionManager.unregisterDimension(dimId);
		}

		this.instances.clear();
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("hasWrittenInstances", this.instances.size() > 0);

		NBTTagList tagList = new NBTTagList();

		for (Entry<Integer, T> entry : this.instances.entrySet())
		{
			T instance = entry.getValue();
			NBTTagCompound newTag = new NBTTagCompound();
			newTag.setInteger("dimension", entry.getKey());

			instance.write(newTag);
			tagList.appendTag(newTag);
		}

		output.setTag("instances", tagList);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		boolean hasWrittenInstances = input.getBoolean("hasWrittenInstances");

		if (!hasWrittenInstances)
		{
			return;
		}

		for (NBTTagCompound tag : NBTHelper.getIterator(input, "instances"))
		{
			int id = tag.getInteger("dimension");

			if (DimensionManager.isDimensionRegistered(id))
			{
				final int oldId = id;

				File oldDimFolder = new File(AetherCore.getWorldDirectory(), "//DIM" + oldId);

				id = this.getFreeDimID();

				File newDimFolder = new File(AetherCore.getWorldDirectory(), "//DIM" + id);

				if (oldDimFolder.isDirectory())
				{
					File[] content = oldDimFolder.listFiles();

					for (int i = 0; i < content.length; i++)
					{
						try
						{
							FileUtils.moveFileToDirectory(content[i], newDimFolder, true);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}

			T instance = this.factory.createInstance(id, this);
			instance.read(tag);
			DimensionManager.registerDimension(id, this.factory.dimensionType());

			this.instances.put(id, instance);
		}
	}

	@Override
	public T getInstanceForDimension(int id)
	{
		return this.instances.get(id);
	}

	@Override
	public int getDimensionForInstance(Instance instance)
	{
		return this.instances.inverse().get(instance);
	}

	@Override
	public World getWorldForInstance(Instance instance)
	{
		int dim = this.getDimensionForInstance(instance);

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		WorldServer world = server.worldServerForDimension(dim);

		return world;
	}

	@Override
	public int getInstancesSize()
	{
		return this.instances.size();
	}

	@Override
	public Collection<T> getInstances()
	{
		return this.instances.values();
	}

	@Override
	public BiMap<Integer, T> getInstancesMap()
	{
		return this.instances;
	}

	@Override
	public World teleportPlayerToDimension(T instance, EntityPlayerMP player)
	{
		if (this.instances.containsValue(instance))
		{
			IPlayerInstances hook = AetherAPI.instances().getPlayer(player);

			if (hook.getInstance() == null)
			{
				hook.setOutside(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));
			}

			int dimId = this.instances.inverse().get(instance);

			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			WorldServer world = server.worldServerForDimension(dimId);

			Teleporter teleporter = this.factory.getTeleporter(world);

			PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(player, this.instances.inverse().get(instance), teleporter);

			player.timeUntilPortal = player.getPortalCooldown();

			hook.setInstance(instance);

			return world;
		}

		return player.worldObj;
	}

	@Override
	public void teleportPlayerOutsideInstance(EntityPlayerMP player)
	{
		IPlayerInstances hook = AetherAPI.instances().getPlayer(player);

		if (hook.getInstance() != null && hook.outside() != null)
		{
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			BlockPosDimension pos = hook.outside();
			Teleporter teleporter = new TeleporterGeneric(server.worldServerForDimension(player.dimension));
			PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(player, pos.dimId(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();
			hook.setOutside(null);
			hook.setInstance(null);

			player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		}
	}

	public int getFreeDimID()
	{
		int next = -1;

		while (true)
		{
			next--;

			if (!DimensionManager.isDimensionRegistered(next))
			{
				return next;
			}
		}
	}

}

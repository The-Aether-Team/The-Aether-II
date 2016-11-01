package com.gildedgames.aether.common.capabilities.instances;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.instances.IInstanceHandler;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class InstanceEvents
{

	private InstanceEvents()
	{

	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		for (IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
		{
			handler.sendUnregisterInstancesPacket((EntityPlayerMP) event.player);
		}
	}

	public static void unregisterAllInstances()
	{
		AetherAPI.instances().getInstanceHandlers().forEach(IInstanceHandler::unregisterInstances);
	}

	public static void loadAllInstancesFromDisk()
	{
		NBTTagCompound tag = NBTHelper.readNBTFromFile("//data//instances.dat");

		if (tag == null)
		{
			return;
		}

		int i = 0;

		for (IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
		{
			NBTTagCompound subTag = tag.getCompoundTag(String.valueOf(i++));

			handler.read(subTag);
		}
	}

	public static void saveAllInstancesToDisk()
	{
		NBTTagCompound tag = new NBTTagCompound();

		int i = 0;

		tag.setInteger("size", AetherAPI.instances().getInstanceHandlers().size());

		for (IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
		{
			NBTTagCompound subTag = new NBTTagCompound();
			handler.write(subTag);

			tag.setTag(String.valueOf(i++), subTag);
		}

		NBTHelper.writeNBTToFile(tag, "//data//instances.dat");
	}

}

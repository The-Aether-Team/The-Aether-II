package com.gildedgames.aether.common.capabilities.world.instances;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.world.instances.IInstanceHandler;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber()
public class InstanceEvents
{

	private InstanceEvents()
	{

	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event)
	{
		for (final IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
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
		final NBTTagCompound tag = NBTHelper.readNBTFromFile("//data//instances.dat");

		if (tag == null)
		{
			return;
		}

		int i = 0;

		for (final IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
		{
			final NBTTagCompound subTag = tag.getCompoundTag(String.valueOf(i++));

			handler.read(subTag);
		}
	}

	public static void saveAllInstancesToDisk()
	{
		final NBTTagCompound tag = new NBTTagCompound();

		int i = 0;

		tag.setInteger("size", AetherAPI.instances().getInstanceHandlers().size());

		for (final IInstanceHandler<?> handler : AetherAPI.instances().getInstanceHandlers())
		{
			final NBTTagCompound subTag = new NBTTagCompound();
			handler.write(subTag);

			tag.setTag(String.valueOf(i++), subTag);
		}

		NBTHelper.writeNBTToFile(tag, "//data//instances.dat");
	}

}

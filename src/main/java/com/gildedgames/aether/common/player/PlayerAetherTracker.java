package com.gildedgames.aether.common.player;

import com.gildedgames.aether.common.network.NetworkingAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerAetherTracker
{
	@SubscribeEvent
	public void onConstructEntity(EntityEvent.EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer && !(event.entity instanceof FakePlayer))
		{
			PlayerAether.register(event.entity);
		}
	}

	@SubscribeEvent
	public void onPlayerCloneEvent(PlayerEvent.Clone event)
	{
		if (event.wasDeath && event.entityPlayer instanceof EntityPlayerMP)
		{
			PlayerAether orig = PlayerAether.get(event.original);

			NBTTagCompound compound = new NBTTagCompound();
			orig.saveNBTData(compound);

			PlayerAether next = PlayerAether.get(event.entityPlayer);
			next.loadNBTData(compound);

			NetworkingAether.syncPlayerToClient(orig, (EntityPlayerMP) event.entityPlayer);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		// Sends player data to the client upon joining the world.

		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP)
		{
			PlayerAether aePlayer = PlayerAether.get(event.entity);

			if (aePlayer != null)
			{
				NetworkingAether.syncPlayerToClient(aePlayer, (EntityPlayerMP) event.entity);
			}
		}
	}
}

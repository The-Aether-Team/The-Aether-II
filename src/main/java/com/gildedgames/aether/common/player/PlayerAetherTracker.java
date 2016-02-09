package com.gildedgames.aether.common.player;
	
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.ItemAccessory;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.util.PlayerUtil;

public class PlayerAetherTracker
{
	@SubscribeEvent
	public void onConstructEntity(EntityEvent.EntityConstructing event)
	{
		EntityEffects.register(event.entity);
		
		if (event.entity instanceof EntityPlayer && !(event.entity instanceof FakePlayer))
		{
			PlayerAether.register(event.entity);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerLoggedOutEvent event)
	{
		if (event.player instanceof EntityPlayerMP)
		{
			PlayerAether aePlayer = PlayerAether.get(event.player);

			EntityEffects<EntityPlayer> effects = EntityEffects.get(aePlayer.getPlayer());

			for (ItemStack stack : aePlayer.getInventoryAccessories().getInventory())
			{
				if (stack != null && stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory acc = (ItemAccessory)stack.getItem();

					for (EntityEffect<EntityPlayer> effect : acc.getEffects())
					{
						effects.removeEffect(effect);
					}
				}
			}
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
			
			EntityEffects<EntityPlayer> effects = EntityEffects.get(aePlayer.getPlayer());
			
			effects.clearEffects();
			
			for (ItemStack stack : aePlayer.getInventoryAccessories().getInventory())
			{
				if (stack != null && stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory acc = (ItemAccessory)stack.getItem();

					for (EntityEffect<EntityPlayer> effect : acc.getEffects())
					{
						if (!effects.addEffect(effect))
						{
							int count = PlayerUtil.getEffectCount(aePlayer.getPlayer(), effect);
							
							effect.getAttributes().setInteger("modifier", count);
						}
					}
				}
			}

			if (aePlayer != null)
			{
				NetworkingAether.syncPlayerToClient(aePlayer, (EntityPlayerMP) event.entity);
			}
		}
	}
}

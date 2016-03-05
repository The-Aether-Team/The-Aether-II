package com.gildedgames.aether.common.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.ItemAccessory;
import com.gildedgames.aether.common.util.PlayerUtil;

public class PlayerAetherTracker
{
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		// Sends player data to the client upon joining the world.

		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP)
		{
			PlayerAether aePlayer = PlayerAether.get(event.entity);

			EntityEffects<EntityPlayer> effects = EntityEffects.get(aePlayer.getEntity());

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
							int count = PlayerUtil.getEffectCount(aePlayer.getEntity(), effect);

							effect.getAttributes().setInteger("modifier", count);
						}
					}
				}
			}
		}
	}
}

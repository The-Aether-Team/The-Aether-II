package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.entities.item.EntityPhoenixItem;
import com.gildedgames.aether.common.capabilities.item.properties.IPhoenixChillable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityItemWatcher
{
	@SubscribeEvent
	public static void onEntityJoinedWorld(EntityJoinWorldEvent event)
	{
		if (!event.getWorld().isRemote && event.getEntity().getClass() == EntityItem.class)
		{
			EntityItem entity = (EntityItem) event.getEntity();

			ItemStack stack = entity.getEntityItem();

			if (stack.getItem() instanceof IPhoenixChillable)
			{
				IPhoenixChillable chillable = (IPhoenixChillable) stack.getItem();

				if (chillable.canChillItemstack(stack))
				{
					EntityPhoenixItem phoenixItem = new EntityPhoenixItem(entity);
					event.getWorld().spawnEntityInWorld(phoenixItem);

					// We replace the entity in the world
					event.setCanceled(true);
				}
			}
		}
	}
}

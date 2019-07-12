package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class ItemSkyrootSwordListener
{
	@SubscribeEvent
	public static void dropLoot(final LivingDropsEvent event)
	{
		if (event.getSource().getTrueSource() instanceof PlayerEntity)
		{
			final PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();

			final ItemStack held = player.getHeldItem(Hand.MAIN_HAND);

			boolean providesDrops = false;

			if (held.getItem() == ItemsAether.skyroot_sword)
			{
				providesDrops = true;
			}
			else if (held.getItem() instanceof ToolItem)
			{
				final String material = ((ToolItem) held.getItem()).getToolMaterialName();

				providesDrops = Objects.equals(material, MaterialsAether.SKYROOT_TOOL.name());
			}

			if (providesDrops)
			{
				final List<ItemStack> stacks = new ArrayList<>();

				for (final ItemEntity item : event.getDrops())
				{
					stacks.add(item.getItem());
				}

				Entity origin = event.getEntity();

				for (final ItemStack stack : stacks)
				{
					final ItemEntity item = new ItemEntity(player.getEntityWorld(), origin.posX, origin.posY, origin.posZ, stack);

					player.getEntityWorld().spawnEntity(item);
				}
			}
		}
	}

}

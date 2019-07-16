package com.gildedgames.aether.common.init;

import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ContainersAether
{
	@SubscribeEvent
	public void onContainerInit(RegistryEvent.Register<ContainerType<?>> event)
	{
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

		registry.register(new ContainerType<>(ContainerIcestoneCooler::new));
	}
}

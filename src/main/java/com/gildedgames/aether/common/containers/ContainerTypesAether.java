package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.registrar.AbstractRegistrar;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("aether")
public class ContainerTypesAether extends AbstractRegistrar
{
	public static final ContainerType<? extends ContainerIcestoneCooler> ICESTONE_COOLER = getDefault();

	public static final ContainerType<? extends ContainerIncubator> INCUBATOR = getDefault();

	public static final ContainerType<? extends ContainerMasonryBench> MASONRY_BENCH = getDefault();

}

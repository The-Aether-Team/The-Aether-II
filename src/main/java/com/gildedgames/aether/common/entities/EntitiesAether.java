package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntitiesAether
{
	private static int NEXT_ID = 0;

	public static void preInit()
	{
		EntityRegistry.registerModEntity(EntityFloatingBlock.class, "aether.floating_block", NEXT_ID++, Aether.INSTANCE, 80, 3, true);
	}
}

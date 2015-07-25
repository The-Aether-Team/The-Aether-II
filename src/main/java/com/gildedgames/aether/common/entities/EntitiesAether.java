package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntitiesAether
{
	private static int NEXT_ID = 0;

	public static void preInit()
	{
		EntityRegistry.registerModEntity(EntityFloatingBlock.class, "aether.floating_block", NEXT_ID++, Aether.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDart.class, "aether.dart", NEXT_ID++, Aether.INSTANCE, 80, 3, true);
	}
}

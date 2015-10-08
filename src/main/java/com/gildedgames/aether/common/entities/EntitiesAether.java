package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.living.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.EntityPhyg;
import com.gildedgames.aether.common.entities.living.EntitySheepuff;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntitiesAether
{
	private static int NEXT_ID = 0;

	public static void preInit()
	{
		registerLivingEntity(EntityPhyg.class, "phyg");
		registerLivingEntity(EntityFlyingCow.class, "flying_cow");
		registerLivingEntity(EntitySheepuff.class, "sheepuff");

		EntityRegistry.registerModEntity(EntityFloatingBlock.class, "floating_block", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDart.class, "dart", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
	}

	private static void registerLivingEntity(Class<? extends Entity> entity, String id)
	{
		registerEntity(entity, id, 80, 3, true);
	}

	private static void registerEntity(Class<? extends Entity> entity, String id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(entity, id, NEXT_ID++, AetherCore.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}

package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.companions.EntityFrostpineTotem;
import com.gildedgames.aether.common.entities.item.EntityPhoenixItem;
import com.gildedgames.aether.common.entities.living.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.EntityAerbunny;
import com.gildedgames.aether.common.entities.living.EntityCarrionSprout;
import com.gildedgames.aether.common.entities.living.EntitySheepuff;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.util.modules.spawning.SpawnEntry;
import com.gildedgames.util.modules.spawning.SpawnManager;
import com.gildedgames.util.modules.spawning.SpawningModule;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntitiesAether
{
	private static int NEXT_ID = 0;

	public static void preInit()
	{
		registerLivingEntity(EntityPhyg.class, "phyg", 0xCCFFFF, 0xFF9999);
		registerLivingEntity(EntityFlyingCow.class, "flying_cow", 0xCCFFFF, 0x0066CC);
		registerLivingEntity(EntitySheepuff.class, "sheepuff", 0xCCFFFF, 0xCCCCFF);
		registerLivingEntity(EntityAechorPlant.class, "aechor_plant", 0xCCFFFF, 0xC88FE3);
		registerLivingEntity(EntityAerbunny.class, "aerbunny", 0xCCFFFF, 0x0066CC);
		registerLivingEntity(EntityCarrionSprout.class, "carrion_sprout", 0xCCFFFF, 0x5D8BBB);

		registerLivingEntity(EntityFrostpineTotem.class, "frostpine_totem");

		EntityRegistry.registerModEntity(EntityFloatingBlock.class, "floating_block", NEXT_ID++, AetherCore.INSTANCE, 80, 1, true);
		EntityRegistry.registerModEntity(EntityMovingBlock.class, "moving_block", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDart.class, "dart", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDaggerfrostSnowball.class, "daggerfrost_snowball", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityPhoenixItem.class, "special_item", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityBolt.class, "bolt", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);

		SpawnManager manager = SpawningModule.createAndRegisterSpawnManager(AetherCore.PROXY.getAetherDimensionType().getId());
		manager.registerPerTickEntry(new SpawnEntry(EntityAerbunny.class, 2, 5, 0.3f));
		manager.registerPerTickEntry(new SpawnEntry(EntityFlyingCow.class, 1, 4, 0.65f));
		manager.registerPerTickEntry(new SpawnEntry(EntityPhyg.class, 1, 4, 0.35f));
		manager.registerPerTickEntry(new SpawnEntry(EntityCarrionSprout.class, 2, 3, 0.4f));
		manager.registerPerTickEntry(new SpawnEntry(EntitySheepuff.class, 3, 5, 0.5f));

		manager.registerPerTickEntry(new SpawnEntry(EntityAechorPlant.class, 2, 4, 0.4f));
	}

	private static void registerLivingEntity(Class<? extends Entity> entity, String id, int eggPrimaryColor, int eggSecondaryColor)
	{
		registerLivingEntity(entity, id);

		EntityRegistry.registerEgg(entity, eggPrimaryColor, eggSecondaryColor);
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

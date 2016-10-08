package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.*;
import com.gildedgames.aether.common.entities.dungeon.util.EntityGenerator;
import com.gildedgames.aether.common.entities.item.EntityPhoenixItem;
import com.gildedgames.aether.common.entities.living.*;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import com.gildedgames.aether.common.entities.living.enemies.EntityTempest;
import com.gildedgames.aether.common.entities.living.enemies.EntityZephyr;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.entities.projectiles.EntityBattleBomb;
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
		registerLivingEntity(EntityPhyg.class, "phyg", 0xfcb6bd, 0xfde27a);
		registerLivingEntity(EntityFlyingCow.class, "flying_cow", 0xbbbbbb, 0xf5db72);
		registerLivingEntity(EntityAechorPlant.class, "aechor_plant", 0x735190, 0xaed28f);
		registerLivingEntity(EntityAerbunny.class, "aerbunny", 0xcbe1e6, 0xe08c8c);
		registerLivingEntity(EntityCarrionSprout.class, "carrion_sprout", 0xcad9e9, 0x93b1d1);
		registerLivingEntity(EntityCockatrice.class, "cockatrice", 0x6b519c, 0x8ae390);
		registerLivingEntity(EntityKirrid.class, "kirrid", 0xf3efd8, 0x50575d);
		registerLivingEntity(EntityMoa.class, "moa");
		registerLivingEntity(EntityAerwhale.class, "aerwhale", 0x86b1c9, 0x8095a1);
		registerLivingEntity(EntityZephyr.class, "zephyr", 0xb8b5ab, 0x988c65);
		registerLivingEntity(EntityTempest.class, "tempest", 0x3c464c, 0xc3e6f0);
		registerLivingEntity(EntitySwet.class, "swet", 0xCCFFFF, 0xCCCCFF);
		//registerLivingEntity(EntityTaegore.class, "taegore", 0x607075, 0xbda54a);
		registerLivingEntity(EntityDetonationSentry.class, "detonation_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityBattleSentry.class, "battle_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityTrackingSentry.class, "tracking_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityChestMimic.class, "chest_mimic", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityBattleGolem.class, "battle_golem", 0x414141, 0x4e9af7);
		registerLivingEntity(EntitySentryGuardian.class, "sentry_guardian", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityProductionLine.class, "production_line", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityRepairSentry.class, "repair_sentry", 0x414141, 0x4e9af7);

		registerLivingEntity(EntityFrostpineTotem.class, "frostpine_totem");
		registerLivingEntity(EntityKraisith.class, "kraisith");
		registerLivingEntity(EntityShadeOfArkenzus.class, "shade_of_arkenzus");
		registerLivingEntity(EntityEtheralWisp.class, "ethereal_wisp");
		registerLivingEntity(EntityFleetingWisp.class, "fleeting_wisp");
		registerLivingEntity(EntitySoaringWisp.class, "soaring_wisp");
		registerLivingEntity(EntityFangrin.class, "fangrin");
		registerLivingEntity(EntityNexSpirit.class, "nex_spirit");
		registerLivingEntity(EntityPinkBabySwet.class, "pink_baby_swet");
		registerLivingEntity(EntityGenerator.class, "generator");

		EntityRegistry.registerModEntity(EntityFloatingBlock.class, "floating_block", NEXT_ID++, AetherCore.INSTANCE, 80, 1, true);
		EntityRegistry.registerModEntity(EntityMovingBlock.class, "moving_block", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDart.class, "dart", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDaggerfrostSnowball.class, "daggerfrost_snowball", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityPhoenixItem.class, "special_item", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityBolt.class, "bolt", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityBattleBomb.class, "battle_bomb", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityParachute.class, "parachute", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
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

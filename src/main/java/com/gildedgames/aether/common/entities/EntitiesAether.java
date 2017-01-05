package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.entities.item.EntityRewardItemStack;
import com.gildedgames.aether.common.entities.living.boss.slider.EntitySlider;
import com.gildedgames.aether.common.entities.living.companions.*;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.*;
import com.gildedgames.aether.common.entities.living.dungeon.util.EntityGenerator;
import com.gildedgames.aether.common.entities.living.mobs.*;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.entities.projectiles.*;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntitiesAether
{

	public static HashMap<String, AetherSpawnEggInfo> entityEggs = new LinkedHashMap<>();

	public static Map<String, Class<? extends Entity>> stringToClassMapping = new HashMap<>();

	public static Map<Class<? extends Entity>, String> classToStringMapping = new HashMap<>();

	private static int NEXT_ID = 0;

	public static void preInit()
	{
		registerLivingEntity(EntityAechorPlant.class, "aechor_plant", 0x735190, 0xaed28f);
		registerLivingEntity(EntityAerbunny.class, "aerbunny", 0xcbe1e6, 0xe08c8c);
		registerLivingEntity(EntityCarrionSprout.class, "carrion_sprout", 0xcad9e9, 0x93b1d1);
		registerLivingEntity(EntityCockatrice.class, "cockatrice", 0x6b519c, 0x8ae390);
		registerLivingEntity(EntityKirrid.class, "kirrid", 0xf3efd8, 0x50575d);
		registerLivingEntity(EntityAerwhale.class, "aerwhale", 0x86b1c9, 0x8095a1);
		registerLivingEntity(EntityZephyr.class, "zephyr", 0xb8b5ab, 0x988c65);
		registerLivingEntity(EntityTempest.class, "tempest", 0x3c464c, 0xc3e6f0);
		registerLivingEntity(EntitySwet.class, "swet", 0xCCFFFF, 0xCCCCFF);
		registerLivingEntity(EntityTaegore.class, "taegore", 0x607075, 0xbda54a);
		registerLivingEntity(EntityDetonationSentry.class, "detonation_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityBattleSentry.class, "battle_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityTrackingSentry.class, "tracking_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityChestMimic.class, "chest_mimic", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityBattleGolem.class, "battle_golem", 0x414141, 0x4e9af7);
		registerLivingEntity(EntitySentryGuardian.class, "sentry_guardian", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityProductionLine.class, "production_line", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityRepairSentry.class, "repair_sentry", 0x414141, 0x4e9af7);
		registerLivingEntity(EntitySlider.class, "slider", 0x414141, 0x4e9af7);
		registerLivingEntity(EntityGlitterwing.class, "glitterwing", 0x5368a9, 0x1a2341);
		registerLivingEntity(EntityEdison.class, "edison", 0xedc5b1, 0x788b7e);
		registerLivingEntity(EntityBurrukai.class, "burrukai", 0x435258, 0x548193);

		registerLivingEntity(EntityFrostpineTotem.class, "frostpine_totem");
		registerLivingEntity(EntityKraisith.class, "kraisith");
		registerLivingEntity(EntityShadeOfArkenzus.class, "shade_of_arkenzus");
		registerLivingEntity(EntityEtherealWisp.class, "ethereal_wisp");
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
		EntityRegistry.registerModEntity(EntityBolt.class, "bolt", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityBattleBomb.class, "battle_bomb", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityParachute.class, "parachute", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityRewardItemStack.class, "reward_itemstack", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntitySentryVaultbox.class, "sentry_vaultbox", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityTNTPresent.class, "tnt_present", NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityMoa.class, "moa", NEXT_ID++, AetherCore.INSTANCE, 80, 1, true);
	}

	private static void registerLivingEntity(Class<? extends Entity> entity, String id, int eggPrimaryColor, int eggSecondaryColor)
	{
		registerLivingEntity(entity, id);

		entityEggs.put(id, new AetherSpawnEggInfo(id, eggPrimaryColor, eggSecondaryColor));
		classToStringMapping.put(entity, id);
		stringToClassMapping.put(id, entity);
	}

	private static void registerLivingEntity(Class<? extends Entity> entity, String id)
	{
		registerEntity(entity, id, 80, 3, true);
	}

	private static void registerEntity(Class<? extends Entity> entity, String id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(entity, id, NEXT_ID++, AetherCore.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public static Class getClassFromID(String id)
	{
		return stringToClassMapping.get(id);
	}

	public static String getStringFromClass(Class clazz)
	{
		return clazz != null ? classToStringMapping.get(clazz) : null;
	}

}

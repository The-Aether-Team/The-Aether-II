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
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntitiesAether
{
	private static int NEXT_ID = 0;

	public static void preInit()
	{
		registerLivingEntityWithEgg(EntityAechorPlant.class, "aechor_plant", 0x735190, 0xaed28f);
		registerLivingEntityWithEgg(EntityAerbunny.class, "aerbunny", 0xcbe1e6, 0xe08c8c);
		registerLivingEntityWithEgg(EntityCarrionSprout.class, "carrion_sprout", 0xcad9e9, 0x93b1d1);
		registerLivingEntityWithEgg(EntityCockatrice.class, "cockatrice", 0x6b519c, 0x8ae390);
		registerLivingEntityWithEgg(EntityKirrid.class, "kirrid", 0xf3efd8, 0x50575d);
		registerLivingEntityWithEgg(EntityAerwhale.class, "aerwhale", 0x86b1c9, 0x8095a1);
		registerLivingEntityWithEgg(EntityZephyr.class, "zephyr", 0xb8b5ab, 0x988c65);
		registerLivingEntityWithEgg(EntityTempest.class, "tempest", 0x3c464c, 0xc3e6f0);
		registerLivingEntityWithEgg(EntitySwet.class, "swet", 0xCCFFFF, 0xCCCCFF);
		registerLivingEntityWithEgg(EntityTaegore.class, "taegore", 0x607075, 0xbda54a);
		registerLivingEntityWithEgg(EntityDetonationSentry.class, "detonation_sentry", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityBattleSentry.class, "battle_sentry", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityTrackingSentry.class, "tracking_sentry", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityChestMimic.class, "chest_mimic", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityBattleGolem.class, "battle_golem", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntitySentryGuardian.class, "sentry_guardian", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityProductionLine.class, "production_line", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityRepairSentry.class, "repair_sentry", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntitySlider.class, "slider", 0x414141, 0x4e9af7);
		registerLivingEntityWithEgg(EntityGlitterwing.class, "glitterwing", 0x5368a9, 0x1a2341);
		registerLivingEntityWithEgg(EntityEdison.class, "edison", 0xedc5b1, 0x788b7e);
		registerLivingEntityWithEgg(EntityBurrukai.class, "burrukai", 0x435258, 0x548193);

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

		registerEntity(EntityFloatingBlock.class, "floating_block", 80, 1, true);
		registerEntity(EntityMovingBlock.class, "moving_block", 80, 3, true);
		registerEntity(EntityDart.class, "dart", 80, 3, true);
		registerEntity(EntityDaggerfrostSnowball.class, "daggerfrost_snowball", 80, 3, true);
		registerEntity(EntityBolt.class, "bolt", 80, 3, true);
		registerEntity(EntityBattleBomb.class, "battle_bomb", 80, 3, true);
		registerEntity(EntityParachute.class, "parachute", 80, 3, true);
		registerEntity(EntityRewardItemStack.class, "reward_itemstack", 80, 3, true);
		registerEntity(EntitySentryVaultbox.class, "sentry_vaultbox", 80, 3, true);
		registerEntity(EntityTNTPresent.class, "tnt_present", 80, 3, true);
		registerEntity(EntityMoa.class, "moa", 80, 1, true);
	}

	private static void registerLivingEntityWithEgg(Class<? extends Entity> entity, String id, int eggPrimaryColor, int eggSecondaryColor)
	{
		EntityRegistry.registerModEntity(AetherCore.getResource(id), entity, id, NEXT_ID++, AetherCore.INSTANCE, 80, 3, true, eggPrimaryColor, eggSecondaryColor);
	}

	private static void registerLivingEntity(Class<? extends Entity> entity, String id)
	{
		EntityRegistry.registerModEntity(AetherCore.getResource(id), entity, id, NEXT_ID++, AetherCore.INSTANCE, 80, 3, true);
	}

	private static void registerEntity(Class<? extends Entity> entity, String id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(AetherCore.getResource(id), entity, id, NEXT_ID++, AetherCore.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}

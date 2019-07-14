package com.gildedgames.aether.common.init;

import com.gildedgames.aether.common.entities.animals.*;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.characters.*;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.entities.monsters.*;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.entities.projectiles.EntityTNTPresent;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber
public class EntitiesAether
{
	private static final Collection<ResourceLocation> registeredEggs = new ArrayList<>();

	private static int NEXT_ID = 0;

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		IForgeRegistry<EntityType<?>> registry = event.getRegistry();
		registry.register(EntityType.Builder.create(EntityAechorPlant::new, EntityClassification.CREATURE).size(0.8F, 0.6F).build("aechor_plant"));
		registry.register(EntityType.Builder.create(EntityAerbunny::new, EntityClassification.CREATURE).size(0.65F, 0.65F).build("aerbunny"));
		registry.register(EntityType.Builder.create(EntityAerwhale::new, EntityClassification.CREATURE).size(3.0F, 3.0F).build("aerwhale"));
		registry.register(EntityType.Builder.create(EntityBurrukai::new, EntityClassification.CREATURE).size(1.5F, 1.9F).build("burrukai"));
		registry.register(EntityType.Builder.create(EntityCarrionSprout::new, EntityClassification.CREATURE).size(0.5F, 1.5F).build("carrion_sprout"));
		registry.register(EntityType.Builder.create(EntityCockatrice::new, EntityClassification.MONSTER).size(1.0F, 2.0F).build("cockatrice"));
		registry.register(EntityType.Builder.create(EntityEdison::new, EntityClassification.MISC).size(0.9F, 1.35F).build("edison"));
		registry.register(EntityType.Builder.create(EntityGlactrix::new, EntityClassification.CREATURE).size(0.65f, 0.5f).build("glactrix"));
		registry.register(EntityType.Builder.create(EntityGlitterwing::new, EntityClassification.AMBIENT).size(0.3F, 0.3F).build("glitterwing"));
		registry.register(EntityType.Builder.create(EntityJosediya::new, EntityClassification.MISC).size(0.7F, 2.0F).build("josediya"));
		registry.register(EntityType.Builder.create(EntityKirrid::new, EntityClassification.CREATURE).size(1.0F, 1.5F).build("kirrid"));
		registry.register(EntityType.Builder.create(EntityMysteriousFigure::new, EntityClassification.MISC).size(1.0F, 2.0F).build("mysterious_figure"));
		registry.register(EntityType.Builder.create(EntityNecromancer::new, EntityClassification.MISC).size(1.0F, 2.5F).build("necromancer"));
		registry.register(EntityType.Builder.create(EntitySheepuff::new, EntityClassification.CREATURE).size(1.1F, 1.4F).build("sheepuff"));
		registry.register(EntityType.Builder.create(EntitySkyrootLizard::new, EntityClassification.CREATURE).size(0.8f, 0.3f).build("skyroot_lizard"));
		registry.register(EntityType.Builder.create(EntitySwet::new, EntityClassification.MONSTER).size(1.0F, 1.0F).build("swet"));
		registry.register(EntityType.Builder.create(EntityTaegore::new, EntityClassification.CREATURE).size(1.25F, 1.25F).build("taegore"));
		registry.register(EntityType.Builder.create(EntityTempest::new, EntityClassification.MONSTER).size(1.0F, 1.0F).build("tempest"));
		registry.register(EntityType.Builder.create(EntityTivalier::new, EntityClassification.MISC).size(1.6F, 1.6F).build("tivalier"));
		registry.register(EntityType.Builder.create(EntityVaranys::new, EntityClassification.MONSTER).size(1.4F, 1F).build("varanys"));
		registry.register(EntityType.Builder.create(EntityZephyr::new, EntityClassification.MONSTER).size(1.0F, 1.0F).build("zephyr"));

		registry.register(EntityType.Builder.create(EntityEtherealWisp::new, EntityClassification.MISC).size(0.75f, 2.0f).disableSerialization().build("etheral_wisp"));
		registry.register(EntityType.Builder.create(EntityFangrin::new, EntityClassification.MISC).size(1.0f, 0.9f).disableSerialization().build("fangrin"));
		registry.register(EntityType.Builder.create(EntityFleetingWisp::new, EntityClassification.MISC).size(0.75f, 2.0f).disableSerialization().build("fleeting_wisp"));
		registry.register(EntityType.Builder.create(EntityFrostpineTotem::new, EntityClassification.MISC).size(0.9f, 2.0f).disableSerialization().build("frostpine_totem"));
		registry.register(EntityType.Builder.create(EntityKraisith::new, EntityClassification.MISC).size(0.9f, 2.0f).disableSerialization().build("kraisith"));
		registry.register(EntityType.Builder.create(EntityNexSpirit::new, EntityClassification.MISC).size(0.6f, 1.85f).disableSerialization().build("nex_spirit"));
		registry.register(EntityType.Builder.create(EntityPinkBabySwet::new, EntityClassification.MISC).size(0.75F, 0.75F).disableSerialization().build("pink_baby_swet"));
		registry.register(EntityType.Builder.create(EntityShadeOfArkenzus::new, EntityClassification.MISC).size(0.5f, 1.0f).disableSerialization().build("shade_of_arkenzus"));
		registry.register(EntityType.Builder.create(EntitySoaringWisp::new, EntityClassification.MISC).size(0.75f, 2.0f).disableSerialization().build("soaring_wisp"));

		registry.register(EntityType.Builder.<EntityFloatingBlock>create(EntityFloatingBlock::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true)
				.size(0.98F, 0.98F).build("floating_block"));
		registry.register(EntityType.Builder.<EntityMovingBlock>create(EntityMovingBlock::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true)
				.size(0.9f, 0.9f).build("moving_block"));
		registry.register(EntityType.Builder.<EntityDart>create(EntityDart::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true)
				.size(0.5F, 0.5F).build("dart"));
		registry.register(EntityType.Builder.<EntityDaggerfrostSnowball>create(EntityDaggerfrostSnowball::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true)
				.size(0.25F, 0.25F).build("daggerfrost_snowball"));
		registry.register(EntityType.Builder.<EntityBolt>create(EntityBolt::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true)
				.size(0.5F, 0.5F).build("bolt"));
		registry.register(EntityType.Builder.<EntityTNTPresent>create(EntityTNTPresent::new, EntityClassification.MISC).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true)
				.size(0.98F, 0.98F).build("tnt_present"));

		registry.register(EntityType.Builder.<EntityMoa>create(EntityMoa::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true)
				.size(1.0F, 2.0F).build("tnt_present"));
	}

	@OnlyIn(Dist.CLIENT)
	public static Collection<ResourceLocation> getRegisteredSpawnEggs()
	{
		return Collections.unmodifiableCollection(registeredEggs);
	}
}

package com.gildedgames.aetherii.register;

import com.gildedgames.aetherii.AetherII;
import com.gildedgames.aetherii.entity.TestEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = AetherII.MODID)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AetherII.MODID);

	public static final RegistryObject<EntityType<TestEntity>> TEST = ENTITIES.register("test", () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 1.85F).clientTrackingRange(10).build("aether:test"));

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(TEST.get(), TestEntity.createAttributes().build());
	}
}

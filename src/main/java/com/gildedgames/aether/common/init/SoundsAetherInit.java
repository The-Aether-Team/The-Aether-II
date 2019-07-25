package com.gildedgames.aether.common.init;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class SoundsAetherInit
{
	@SubscribeEvent
	public static void onRegisterSounds(final RegistryEvent.Register<SoundEvent> event)
	{
		final SoundRegistryHelper r = new SoundRegistryHelper(event.getRegistry());

		r.register("portal.glowstone.hum", new SoundEvent(AetherCore.getResource("portal.glowstone.hum")));
		r.register("portal.glowstone.trigger", new SoundEvent(AetherCore.getResource("portal.glowstone.trigger")));
		r.register("portal.glowstone.travel", new SoundEvent(AetherCore.getResource("portal.glowstone.travel")));

		r.register("portal.labyrinth_totem.drone", new SoundEvent(AetherCore.getResource("portal.labyrinth_totem.drone")));

		r.register("block.aercloud.bounce", new SoundEvent(AetherCore.getResource("block.aercloud.bounce")));

		r.register("mob.aerbunny.ambient", new SoundEvent(AetherCore.getResource("mob.aerbunny.ambient")));
		r.register("mob.aerbunny.hurt", new SoundEvent(AetherCore.getResource("mob.aerbunny.hurt")));
		r.register("mob.aerbunny.death", new SoundEvent(AetherCore.getResource("mob.aerbunny.death")));
		r.register("mob.aerbunny.lift", new SoundEvent(AetherCore.getResource("mob.aerbunny.lift")));

		r.register("mob.aerwhale.ambient", new SoundEvent(AetherCore.getResource("mob.aerwhale.ambient")));
		r.register("mob.aerwhale.death", new SoundEvent(AetherCore.getResource("mob.aerwhale.death")));

		r.register("mob.cockatrice.ambient", new SoundEvent(AetherCore.getResource("mob.cockatrice.ambient")));
		r.register("mob.cockatrice.hurt", new SoundEvent(AetherCore.getResource("mob.cockatrice.hurt")));
		r.register("mob.cockatrice.death", new SoundEvent(AetherCore.getResource("mob.cockatrice.death")));

		r.register("mob.tempest.ambient", new SoundEvent(AetherCore.getResource("mob.tempest.ambient")));
		r.register("mob.tempest.hurt", new SoundEvent(AetherCore.getResource("mob.tempest.hurt")));
		r.register("mob.tempest.death", new SoundEvent(AetherCore.getResource("mob.tempest.death")));
		r.register("mob.tempest.angry", new SoundEvent(AetherCore.getResource("mob.tempest.angry")));
		r.register("mob.tempest.electric_shock", new SoundEvent(AetherCore.getResource("mob.tempest.electric_shock")));

		r.register("mob.zephyr.ambient", new SoundEvent(AetherCore.getResource("mob.zephyr.ambient")));
		r.register("mob.zephyr.puff", new SoundEvent(AetherCore.getResource("mob.zephyr.puff")));

		r.register("record.aerwhale", new SoundEvent(AetherCore.getResource("records.aerwhale")));
		r.register("record.labyrinth", new SoundEvent(AetherCore.getResource("records.labyrinth")));
		r.register("record.moa", new SoundEvent(AetherCore.getResource("records.moa")));
		r.register("record.recording_892", new SoundEvent(AetherCore.getResource("records.recording_892")));
		r.register("record.valkyrie", new SoundEvent(AetherCore.getResource("records.valkyrie")));

		r.register("random.dart_shooter.fire", new SoundEvent(AetherCore.getResource("random.dart_shooter.fire")));

		r.register("mob.moa.ambient", new SoundEvent(AetherCore.getResource("mob.moa.ambient")));
		r.register("mob.moa.hurt", new SoundEvent(AetherCore.getResource("mob.moa.hurt")));

		r.register("random.present_unwrap", new SoundEvent(AetherCore.getResource("random.present_unwrap")));
		r.register("random.dungeon.container.smash", new SoundEvent(AetherCore.getResource("random.dungeon.container.smash")));

		r.register("mob.generic.wings.flap", new SoundEvent(AetherCore.getResource("mob.generic.wings.flap")));

		r.register("mob.kirrid.ambient", new SoundEvent(AetherCore.getResource("mob.kirrid.ambient")));
		r.register("mob.kirrid.hurt", new SoundEvent(AetherCore.getResource("mob.kirrid.hurt")));
		r.register("mob.kirrid.death", new SoundEvent(AetherCore.getResource("mob.kirrid.death")));

		r.register("mob.taegore.ambient", new SoundEvent(AetherCore.getResource("mob.taegore.ambient")));
		r.register("mob.taegore.hurt", new SoundEvent(AetherCore.getResource("mob.taegore.hurt")));
		r.register("mob.taegore.death", new SoundEvent(AetherCore.getResource("mob.taegore.death")));
		r.register("mob.taegore.attack", new SoundEvent(AetherCore.getResource("mob.taegore.attack")));

		r.register("mob.burrukai.ambient", new SoundEvent(AetherCore.getResource("mob.burrukai.ambient")));
		r.register("mob.burrukai.hurt", new SoundEvent(AetherCore.getResource("mob.burrukai.hurt")));
		r.register("mob.burrukai.death", new SoundEvent(AetherCore.getResource("mob.burrukai.death")));
		r.register("mob.burrukai.attack", new SoundEvent(AetherCore.getResource("mob.burrukai.attack")));

		r.register("environment.rain.light", new SoundEvent(AetherCore.getResource("environment.rain.light")));
		r.register("environment.rain.heavy", new SoundEvent(AetherCore.getResource("environment.rain.heavy")));
		r.register("environment.snow.wind", new SoundEvent(AetherCore.getResource("environment.snow.wind")));
	}

	private static class SoundRegistryHelper
	{
		private final IForgeRegistry<SoundEvent> registry;

		SoundRegistryHelper(final IForgeRegistry<SoundEvent> registry)
		{
			this.registry = registry;
		}

		private void register(final String registryName, final SoundEvent soundEvent)
		{
			soundEvent.setRegistryName(AetherCore.MOD_ID, registryName);
			this.registry.register(soundEvent);
		}
	}

}

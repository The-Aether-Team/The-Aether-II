package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber()
public class SoundsAether
{
	public static final SoundEvent glowstone_portal_hum = new SoundEvent(AetherCore.getResource("portal.glowstone.hum"));

	public static final SoundEvent glowstone_portal_trigger = new SoundEvent(AetherCore.getResource("portal.glowstone.trigger"));

	public static final SoundEvent glowstone_portal_travel = new SoundEvent(AetherCore.getResource("portal.glowstone.travel"));

	public static final SoundEvent labyrinth_totem_drone = new SoundEvent(AetherCore.getResource("portal.labyrinth_totem.drone"));

	public static final SoundEvent aercloud_bounce = new SoundEvent(AetherCore.getResource("block.aercloud.bounce"));

	public static final SoundEvent aerbunny_ambient = new SoundEvent(AetherCore.getResource("mob.aerbunny.ambient"));

	public static final SoundEvent aerbunny_hurt = new SoundEvent(AetherCore.getResource("mob.aerbunny.hurt"));

	public static final SoundEvent aerbunny_death = new SoundEvent(AetherCore.getResource("mob.aerbunny.death"));

	public static final SoundEvent aerbunny_lift = new SoundEvent(AetherCore.getResource("mob.aerbunny.lift"));

	public static final SoundEvent record_aerwhale = new SoundEvent(AetherCore.getResource("records.aerwhale"));

	public static final SoundEvent record_moa = new SoundEvent(AetherCore.getResource("records.moa"));

	public static final SoundEvent record_valkyrie = new SoundEvent(AetherCore.getResource("records.valkyrie"));

	public static final SoundEvent record_labyrinth = new SoundEvent(AetherCore.getResource("records.labyrinth"));

	public static final SoundEvent record_recording_892 = new SoundEvent(AetherCore.getResource("records.recording_892"));

	public static final SoundEvent dart_shooter_fire = new SoundEvent(AetherCore.getResource("random.dart_shooter.fire"));

	public static final SoundEvent cockatrice_hurt = new SoundEvent(AetherCore.getResource("mob.cockatrice.hurt"));

	public static final SoundEvent cockatrice_death = new SoundEvent(AetherCore.getResource("mob.cockatrice.death"));

	public static final SoundEvent cockatrice_ambient = new SoundEvent(AetherCore.getResource("mob.cockatrice.ambient"));

	public static final SoundEvent zephyr_ambient = new SoundEvent(AetherCore.getResource("mob.zephyr.ambient"));

	public static final SoundEvent tempest_ambient = new SoundEvent(AetherCore.getResource("mob.tempest.ambient"));

	public static final SoundEvent tempest_hurt = new SoundEvent(AetherCore.getResource("mob.tempest.hurt"));

	public static final SoundEvent tempest_death = new SoundEvent(AetherCore.getResource("mob.tempest.death"));

	public static final SoundEvent aerwhale_ambient = new SoundEvent(AetherCore.getResource("mob.aerwhale.ambient"));

	public static final SoundEvent aerwhale_death = new SoundEvent(AetherCore.getResource("mob.aerwhale.death"));

	public static final SoundEvent tempest_electric_shock = new SoundEvent(AetherCore.getResource("mob.tempest.electric_shock"));

	public static final SoundEvent tempest_angry = new SoundEvent(AetherCore.getResource("mob.tempest.angry"));

	public static final SoundEvent zephyr_puff = new SoundEvent(AetherCore.getResource("mob.zephyr.puff"));

	public static final SoundEvent moa_ambient = new SoundEvent(AetherCore.getResource("mob.moa.ambient"));

	public static final SoundEvent moa_hurt = new SoundEvent(AetherCore.getResource("mob.moa.hurt"));

	public static final SoundEvent present_unwrap = new SoundEvent(AetherCore.getResource("random.present_unwrap"));

	public static final SoundEvent break_labyrinth_container = new SoundEvent(AetherCore.getResource("random.dungeon.container.smash"));

	public static final SoundEvent generic_wing_flap = new SoundEvent(AetherCore.getResource("mob.generic.wings.flap"));

	public static final SoundEvent kirrid_ambient = new SoundEvent(AetherCore.getResource("mob.kirrid.ambient"));

	public static final SoundEvent kirrid_hurt = new SoundEvent(AetherCore.getResource("mob.kirrid.hurt"));

	public static final SoundEvent kirrid_death = new SoundEvent(AetherCore.getResource("mob.kirrid.death"));

	public static final SoundEvent taegore_ambient = new SoundEvent(AetherCore.getResource("mob.taegore.ambient"));

	public static final SoundEvent taegore_hurt = new SoundEvent(AetherCore.getResource("mob.taegore.hurt"));

	public static final SoundEvent taegore_death = new SoundEvent(AetherCore.getResource("mob.taegore.death"));

	public static final SoundEvent taegore_attack = new SoundEvent(AetherCore.getResource("mob.taegore.attack"));

	public static final SoundEvent burrukai_ambient = new SoundEvent(AetherCore.getResource("mob.burrukai.ambient"));

	public static final SoundEvent burrukai_hurt = new SoundEvent(AetherCore.getResource("mob.burrukai.hurt"));

	public static final SoundEvent burrukai_death = new SoundEvent(AetherCore.getResource("mob.burrukai.death"));

	public static final SoundEvent burrukai_attack = new SoundEvent(AetherCore.getResource("mob.burrukai.attack"));

	public static final SoundEvent environment_rain_light = new SoundEvent(AetherCore.getResource("environment.rain.light"));

	public static final SoundEvent environment_rain_heavy = new SoundEvent(AetherCore.getResource("environment.rain.heavy"));

	public static final SoundEvent environment_snow_wind = new SoundEvent(AetherCore.getResource("environment.snow.wind"));

	@SubscribeEvent
	public static void onRegisterSounds(final RegistryEvent.Register<SoundEvent> event)
	{
		final SoundRegistryHelper r = new SoundRegistryHelper(event.getRegistry());

		r.register("portal.glowstone.hum", glowstone_portal_hum);
		r.register("portal.glowstone.trigger", glowstone_portal_trigger);
		r.register("portal.glowstone.travel", glowstone_portal_travel);

		r.register("portal.labyrinth_totem.drone", labyrinth_totem_drone);

		r.register("block.aercloud.bounce", aercloud_bounce);

		r.register("mob.aerbunny.ambient", aerbunny_ambient);
		r.register("mob.aerbunny.hurt", aerbunny_hurt);
		r.register("mob.aerbunny.death", aerbunny_death);
		r.register("mob.aerbunny.lift", aerbunny_lift);

		r.register("mob.aerwhale.ambient", aerwhale_ambient);
		r.register("mob.aerwhale.death", aerwhale_death);

		r.register("mob.cockatrice.ambient", cockatrice_ambient);
		r.register("mob.cockatrice.hurt", cockatrice_hurt);
		r.register("mob.cockatrice.death", cockatrice_death);

		r.register("mob.tempest.ambient", tempest_ambient);
		r.register("mob.tempest.hurt", tempest_hurt);
		r.register("mob.tempest.death", tempest_death);
		r.register("mob.tempest.angry", tempest_angry);
		r.register("mob.tempest.electric_shock", tempest_electric_shock);

		r.register("mob.zephyr.ambient", zephyr_ambient);
		r.register("mob.zephyr.puff", zephyr_puff);

		r.register("record.aerwhale", record_aerwhale);
		r.register("record.labyrinth", record_labyrinth);
		r.register("record.moa", record_moa);
		r.register("record.recording_892", record_recording_892);
		r.register("record.valkyrie", record_valkyrie);

		r.register("random.dart_shooter.fire", dart_shooter_fire);

		r.register("mob.moa.ambient", moa_ambient);
		r.register("mob.moa.hurt", moa_hurt);

		r.register("random.present_unwrap", present_unwrap);
		r.register("random.dungeon.container.smash", break_labyrinth_container);

		r.register("mob.generic.wings.flap", generic_wing_flap);

		r.register("mob.kirrid.ambient", kirrid_ambient);
		r.register("mob.kirrid.hurt", kirrid_hurt);
		r.register("mob.kirrid.death", kirrid_death);

		r.register("mob.taegore.ambient", taegore_ambient);
		r.register("mob.taegore.hurt", taegore_hurt);
		r.register("mob.taegore.death", taegore_death);
		r.register("mob.taegore.attack", taegore_attack);

		r.register("mob.burrukai.ambient", burrukai_ambient);
		r.register("mob.burrukai.hurt", burrukai_hurt);
		r.register("mob.burrukai.death", burrukai_death);
		r.register("mob.burrukai.attack", burrukai_attack);

		r.register("environment.rain.light", environment_rain_light);
		r.register("environment.rain.heavy", environment_rain_heavy);
		r.register("environment.snow.wind", environment_snow_wind);
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

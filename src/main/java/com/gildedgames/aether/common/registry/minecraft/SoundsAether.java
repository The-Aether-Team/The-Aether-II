package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

	public static final SoundEvent detonating = new SoundEvent(AetherCore.getResource("random.detonating"));

	public static final SoundEvent detonate = new SoundEvent(AetherCore.getResource("random.detonate"));

	public static final SoundEvent sentry_ambient = new SoundEvent(AetherCore.getResource("mob.sentry.ambient"));

	public static final SoundEvent sentry_hurt = new SoundEvent(AetherCore.getResource("mob.sentry.hurt"));

	public static final SoundEvent sentry_death = new SoundEvent(AetherCore.getResource("mob.sentry.death"));

	public static final SoundEvent stone_thud = new SoundEvent(AetherCore.getResource("random.stone_thud"));

	public static final SoundEvent battle_golem_ambient = new SoundEvent(AetherCore.getResource("mob.battle_golem.ambient"));

	public static final SoundEvent battle_golem_hurt = new SoundEvent(AetherCore.getResource("mob.battle_golem.hurt"));

	public static final SoundEvent tracking_sentry_alarm = new SoundEvent(AetherCore.getResource("mob.tracking_sentry.alarm"));

	public static final SoundEvent battle_sentry_pounce = new SoundEvent(AetherCore.getResource("mob.battle_sentry.pounce"));

	public static final SoundEvent moa_ambient = new SoundEvent(AetherCore.getResource("mob.moa.ambient"));

	public static final SoundEvent moa_hurt = new SoundEvent(AetherCore.getResource("mob.moa.hurt"));

	public static final SoundEvent chest_mimic_awake = new SoundEvent(AetherCore.getResource("mob.chest_mimic.awake"));

	public static void preInit() {
		registerSound("portal.glowstone.hum", glowstone_portal_hum);
		registerSound("portal.glowstone.trigger", glowstone_portal_trigger);
		registerSound("portal.glowstone.travel", glowstone_portal_travel);

		registerSound("portal.labyrinth_totem.hum", labyrinth_totem_drone);

		registerSound("block.aercloud.bounce", aercloud_bounce);

		registerSound("mob.aerbunny.ambient", aerbunny_ambient);
		registerSound("mob.aerbunny.hurt", aerbunny_hurt);
		registerSound("mob.aerbunny.death", aerbunny_death);
		registerSound("mob.aerbunny.lift", aerbunny_lift);

		registerSound("mob.aerwhale.ambient", aerwhale_ambient);
		registerSound("mob.aerwhale.death", aerwhale_death);

		registerSound("mob.cockatrice.ambient", cockatrice_ambient);
		registerSound("mob.cockatrice.hurt", cockatrice_hurt);
		registerSound("mob.cockatrice.death", cockatrice_death);

		registerSound("mob.tempest.ambient", tempest_ambient);
		registerSound("mob.tempest.hurt", tempest_hurt);
		registerSound("mob.tempest.death", tempest_death);
		registerSound("mob.tempest.angry", tempest_angry);
		registerSound("mob.tempest.electric_shock", tempest_electric_shock);

		registerSound("mob.zephyr.ambient", zephyr_ambient);
		registerSound("mob.zephyr.puff", zephyr_puff);

		registerSound("mob.battle_golem.ambient", battle_golem_ambient);
		registerSound("mob.battle_golem.hurt", battle_golem_hurt);

		registerSound("mob.tracking_sentry.alarm", tracking_sentry_alarm);

		registerSound("mob.sentry.ambient", sentry_ambient);
		registerSound("mob.sentry.hurt", sentry_hurt);
		registerSound("mob.sentry.death", sentry_death);

		registerSound("mob.battle_sentry.pounce", battle_sentry_pounce);

		registerSound("random.stone.thud", stone_thud);
		registerSound("random.detonating", detonating);
		registerSound("random.detonate", detonate);

		registerSound("record.aerwhale", record_aerwhale);
		registerSound("record.labyrinth", record_labyrinth);
		registerSound("record.moa", record_moa);
		registerSound("record.recording_892", record_recording_892);
		registerSound("record.valkyrie", record_valkyrie);

		registerSound("random.dart_shooter.fire", dart_shooter_fire);

		registerSound("mob.moa.ambient", moa_ambient);
		registerSound("mob.moa.hurt", moa_hurt);

		registerSound("mob.chest_mimic.awake", chest_mimic_awake);
	}

	private static void registerSound(String resource, SoundEvent event) {
		GameRegistry.register(event, AetherCore.getResource(resource));
	}
}

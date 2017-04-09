package com.gildedgames.aether.common.registry.content;

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

	public static final SoundEvent moa_ambient = new SoundEvent(AetherCore.getResource("mob.moa.ambient"));

	public static final SoundEvent moa_hurt = new SoundEvent(AetherCore.getResource("mob.moa.hurt"));

	public static final SoundEvent present_unwrap = new SoundEvent(AetherCore.getResource("random.present_unwrap"));

	public static final SoundEvent break_labyrinth_container = new SoundEvent(AetherCore.getResource("random.dungeon.container.smash"));

	public static final SoundEvent generic_wing_flap = new SoundEvent(AetherCore.getResource("mob.generic.wings.flap"));

	public static final SoundEvent taegore_ambient = new SoundEvent(AetherCore.getResource("mob.taegore.ambient"));

	public static final SoundEvent taegore_hurt = new SoundEvent(AetherCore.getResource("mob.taegore.hurt"));

	public static final SoundEvent taegore_death = new SoundEvent(AetherCore.getResource("mob.taegore.death"));

	public static final SoundEvent burrukai_ambient = new SoundEvent(AetherCore.getResource("mob.burrukai.ambient"));

	public static final SoundEvent burrukai_hurt = new SoundEvent(AetherCore.getResource("mob.burrukai.hurt"));

	public static final SoundEvent burrukai_death = new SoundEvent(AetherCore.getResource("mob.burrukai.death"));

	public static void preInit()
	{
		registerSound("portal.glowstone.hum", glowstone_portal_hum);
		registerSound("portal.glowstone.trigger", glowstone_portal_trigger);
		registerSound("portal.glowstone.travel", glowstone_portal_travel);

		registerSound("portal.labyrinth_totem.drone", labyrinth_totem_drone);

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

		registerSound("record.aerwhale", record_aerwhale);
		registerSound("record.labyrinth", record_labyrinth);
		registerSound("record.moa", record_moa);
		registerSound("record.recording_892", record_recording_892);
		registerSound("record.valkyrie", record_valkyrie);

		registerSound("random.dart_shooter.fire", dart_shooter_fire);

		registerSound("mob.moa.ambient", moa_ambient);
		registerSound("mob.moa.hurt", moa_hurt);

		registerSound("random.present_unwrap", present_unwrap);
		registerSound("random.dungeon.container.smash", break_labyrinth_container);

		registerSound("mob.generic.wings.flap", generic_wing_flap);

		registerSound("mob.taegore.ambient", taegore_ambient);
		registerSound("mob.taegore.hurt", taegore_hurt);
		registerSound("mob.taegore.death", taegore_death);

		registerSound("mob.burrukai.ambient", burrukai_ambient);
		registerSound("mob.burrukai.hurt", burrukai_hurt);
		registerSound("mob.burrukai.death", burrukai_death);
	}

	private static void registerSound(String resource, SoundEvent event)
	{
		GameRegistry.register(event, AetherCore.getResource(resource));
	}
}

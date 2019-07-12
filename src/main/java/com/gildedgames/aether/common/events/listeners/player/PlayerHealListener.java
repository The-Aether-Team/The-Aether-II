package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerHealListener
{
	@SubscribeEvent
	public static void onLivingHeal(final LivingHealEvent event)
	{
		if (event.getEntityLiving() != null)
		{
			if (event.getAmount() <= 1.5)
			{
				IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

				if (statusEffectPool != null)
				{
					if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING))
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}
}

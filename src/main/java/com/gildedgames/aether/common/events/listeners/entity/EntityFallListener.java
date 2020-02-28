package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityFallListener
{
    @SubscribeEvent
    public static void onLivingFall(final LivingFallEvent event)
    {
        if (event.getEntityLiving() != null)
        {
            IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (statusEffectPool != null)
            {
                if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.FRACTURE))
                {
                    event.setDistance(event.getDistance() + 1.0f);
                    event.setDamageMultiplier(2.0f);
                }
            }
        }
    }
}

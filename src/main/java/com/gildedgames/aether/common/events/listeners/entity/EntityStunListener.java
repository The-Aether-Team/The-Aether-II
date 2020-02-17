package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityStunListener
{
    @SubscribeEvent
    public static void onEntityStunned(final LivingAttackEvent event)
    {
        if (event.getEntityLiving() != null)
        {
            if (event.getAmount() > 0)
            {
                IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

                if (statusEffectPool != null)
                {
                    if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.STUN))
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerStunned(final AttackEntityEvent event)
    {
        if (event.getEntityLiving() != null)
        {
            IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (statusEffectPool != null)
            {
                if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.STUN))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}
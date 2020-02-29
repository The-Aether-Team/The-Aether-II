package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityJumpListener
{
    @SubscribeEvent
    public static void onLivingJump(final LivingEvent.LivingJumpEvent event)
    {
        if (event.getEntity() != null)
        {
            if (event.getEntity() instanceof EntityPlayer)
            {
                final PlayerAether playerAether = PlayerAether.getPlayer((EntityPlayer) event.getEntity());

                playerAether.setJumping(true);
            }

            IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (statusEffectPool != null)
            {
                if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.FREEZE))
                {
                    event.getEntity().motionY *= 0.5;
                }
            }
        }
    }
}

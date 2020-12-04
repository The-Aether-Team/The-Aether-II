package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class WorldWeatherListener
{
    @SubscribeEvent
    public static void onWeatherEvent(final LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;

        IPrecipitationManager precipitation = world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

        if (world.getBiome(entity.getPosition()) == BiomesAether.ARCTIC_PEAKS)
        {
            if (world.canSeeSky(entity.getPosition()))
            {
                if (precipitation != null)
                {
                    if (precipitation.getStrength() == PrecipitationStrength.HEAVY
                            || precipitation.getStrength() == PrecipitationStrength.STORM)
                    {
                        if (entity instanceof EntityPlayer)
                        {
                            EntityPlayer entityPlayer = (EntityPlayer) entity;
                            IAetherStatusEffectPool statusEffectPool = entityPlayer.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

                            if (statusEffectPool != null)
                            {
                                boolean tick = world.getTotalWorldTime() % 100 == 0;

                                if (tick)
                                {
                                    if (!statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.FREEZE))
                                    {
                                        statusEffectPool.applyStatusEffect(IAetherStatusEffects.effectTypes.FREEZE, 10);
                                    }
                                    else
                                    {
                                        statusEffectPool.modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes.FREEZE,
                                                statusEffectPool.getBuildupFromEffect(IAetherStatusEffects.effectTypes.FREEZE) + 10);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.gildedgames.aether.common.events.listeners.world;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BiomesAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class WorldWeatherListener
{
    @SubscribeEvent
    public static void onWeatherEvent(final TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            EntityPlayer player = event.player;
            World world = player.world;
            BlockPos pos = player.getPosition();

            IPrecipitationManager precipitation = world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);
            IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (world.isRaining() && world.canSeeSky(pos))
            {
                if (precipitation != null && statusEffectPool != null)
                {
                    boolean tick = world.getTotalWorldTime() % 500 == 0;

                    int effectStrength = 5;

                    if (precipitation.getStrength() == PrecipitationStrength.HEAVY)
                    {
                        effectStrength = 10;
                    }
                    else if (precipitation.getStrength() == PrecipitationStrength.STORM)
                    {
                        effectStrength = 15;
                    }

                    if (tick)
                    {
                        if (world.getBiome(pos) == BiomesAether.ARCTIC_PEAKS)
                        {
                            if (!statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.FREEZE))
                            {
                                statusEffectPool.applyStatusEffect(IAetherStatusEffects.effectTypes.FREEZE, effectStrength);
                            }
                            else
                            {
                                statusEffectPool.modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes.FREEZE,
                                        statusEffectPool.getBuildupFromEffect(IAetherStatusEffects.effectTypes.FREEZE) + effectStrength);
                            }
                        }
                    }
                }
            }
        }
    }
}

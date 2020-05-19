package com.gildedgames.aether.common.events.listeners.player;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Objects;

@Mod.EventBusSubscriber
public class PlayerEatListener
{
    public static final Field saturationLevel = ObfuscationReflectionHelper.findField(FoodStats.class, "foodSaturationLevel");

    @SubscribeEvent
    public static void onPlayerFinishFood(final LivingEntityUseItemEvent.Finish event)
    {
        if (event.getEntityLiving() != null)
        {
            IAetherStatusEffectPool statusEffectPool = event.getEntityLiving().getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (statusEffectPool != null)
            {
                if (statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.SATURATION_BOOST))
                {
                    if (event.getItem().getItem() instanceof ItemFood)
                    {
                        ItemStack stack = event.getItem();
                        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                        FoodStats foodStats = player.getFoodStats();
                        ItemFood food = (ItemFood) stack.getItem();

                        float saturationValue = Math.min(foodStats.getSaturationLevel() + (float)food.getHealAmount(stack) * food.getSaturationModifier(stack) * 2.0F, (float)foodStats.getFoodLevel());

                        try
                        {
                            saturationLevel.set(foodStats, saturationValue);
                        }
                        catch (IllegalAccessException ignored) { }
                    }
                }
            }
        }

        System.out.println(((EntityPlayer) Objects.requireNonNull(event.getEntityLiving())).getFoodStats().getSaturationLevel());
    }
}
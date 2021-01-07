package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import com.gildedgames.aether.common.entities.effects.StatusEffectFreeze;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerEffectsEquipmentModule extends PlayerAetherModule
{
    private NonNullList<ItemStack> stagingInv;

    private NonNullList<ItemStack> recordedInv;

    private Map<ItemStack, Boolean> applicationTracker = new HashMap<>();

    public PlayerEffectsEquipmentModule(PlayerAether player)
    {
        super(player);
    }

    @Override
    public void tickStart(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;

        IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

        if (statusEffectPool == null)
        {
            return;
        }

        if (this.stagingInv == null)
        {
            this.stagingInv = player.inventory.armorInventory;
        }

        if (this.recordedInv == null)
        {
            this.recordedInv = NonNullList.from(ItemStack.EMPTY, player.inventory.armorInventory.get(0).copy(),
                    player.inventory.armorInventory.get(1).copy(),
                    player.inventory.armorInventory.get(2).copy(),
                    player.inventory.armorInventory.get(3).copy());
        }

        for (int i = 0; i < this.stagingInv.size(); i++)
        {
            ItemStack oldStack = this.recordedInv.get(i);
            ItemStack newStack = this.stagingInv.get(i);

            if (!ItemStack.areItemStacksEqual(oldStack, newStack))
            {
                if (!oldStack.isEmpty())
                {
                    statusEffectPool.resetAllResistances();
                }

                this.recordedInv = NonNullList.from(ItemStack.EMPTY, player.inventory.armorInventory.get(0).copy(),
                        player.inventory.armorInventory.get(1).copy(),
                        player.inventory.armorInventory.get(2).copy(),
                        player.inventory.armorInventory.get(3).copy());
            }

            if (!newStack.isEmpty())
            {
                if (newStack.getItem() instanceof ItemAetherArmor)
                {
                    ItemAetherArmor armor = (ItemAetherArmor) newStack.getItem();

                    if (!armor.getStatusEffects().isEmpty())
                    {
                        for (Map.Entry<StatusEffect, Double> effect : armor.getStatusEffects().entrySet())
                        {
                            applicationTracker.putIfAbsent(newStack, false);

                            IAetherStatusEffects actualEffect = statusEffectPool.createEffect(effect.getKey().getEffectType().name, event.player);

                            if (!applicationTracker.get(newStack))
                            {
                                statusEffectPool.addResistanceToEffect(actualEffect.getEffectType(), effect.getValue());

                                if (statusEffectPool.getResistanceToEffect(actualEffect.getEffectType()) != 1.0D)
                                {
                                    applicationTracker.put(newStack, true);
                                }
                            }
                            else
                            {
                                if (statusEffectPool.getResistanceToEffect(actualEffect.getEffectType()) == 1.0D)
                                {
                                    applicationTracker.put(newStack, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

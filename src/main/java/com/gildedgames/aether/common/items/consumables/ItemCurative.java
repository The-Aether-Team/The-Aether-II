package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.ItemDropOnDeath;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Objects;

public class ItemCurative extends ItemDropOnDeath
{
    private IAetherStatusEffects.effectTypes effect;
    private boolean returnItem;
    private int duration;
    private EnumAction action;

    public ItemCurative(IAetherStatusEffects.effectTypes effect, boolean returnItem, int duration, EnumAction action)
    {
        this.effect = effect;
        this.returnItem = returnItem;
        this.duration = duration;
        this.action = action;

        this.maxStackSize = 4;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        EntityPlayer entityPlayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityPlayer instanceof EntityPlayerMP)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityPlayer, stack);
        }

        if (entityPlayer != null)
        {
            entityPlayer.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
        }

        if (entityPlayer == null || !entityPlayer.capabilities.isCreativeMode)
        {
            stack.shrink(1);

            IAetherStatusEffectPool statusEffectPool = (entityPlayer != null)
                    ? entityPlayer.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null)
                    : entityLiving.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (statusEffectPool != null)
            {
                if (this.effect == IAetherStatusEffects.effectTypes.BLEED)
                {
                    statusEffectPool.modifyActiveEffectBuildup(this.effect,
                            statusEffectPool.getBuildupFromEffect(this.effect) - 55);
                }
                else if (this.effect == IAetherStatusEffects.effectTypes.FRACTURE)
                {
                    if (statusEffectPool.isEffectApplied(this.effect))
                    {
                        statusEffectPool.modifyActiveEffectTime(this.effect, 0.2);
                    }
                    else
                    {
                        statusEffectPool.modifyActiveEffectBuildup(this.effect,
                                statusEffectPool.getBuildupFromEffect(this.effect) - 55);
                    }
                }
                else if (this.effect == IAetherStatusEffects.effectTypes.TOXIN)
                {
                    if (statusEffectPool.isEffectApplied(this.effect))
                    {
                        statusEffectPool.modifyActiveEffectApplication(this.effect, false);
                    }

                    statusEffectPool.modifyActiveEffectBuildup(this.effect,
                            statusEffectPool.getBuildupFromEffect(this.effect) - 55);
                }
                else if (this.effect == IAetherStatusEffects.effectTypes.COCKATRICE_VENOM)
                {
                    if (statusEffectPool.isEffectApplied(this.effect))
                    {
                        statusEffectPool.modifyActiveEffectApplication(this.effect, false);
                    }

                    statusEffectPool.modifyActiveEffectBuildup(this.effect,
                            statusEffectPool.getBuildupFromEffect(this.effect) - 55);
                }
            }

            if (this.returnItem)
            {
                if (stack.isEmpty())
                {
                    return new ItemStack(ItemsAether.scatterglass_vial);
                }

                if (entityPlayer != null)
                {
                    entityPlayer.inventory.addItemStackToInventory(new ItemStack(ItemsAether.scatterglass_vial));
                }
            }
        }

        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return this.duration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return this.action;
    }

}

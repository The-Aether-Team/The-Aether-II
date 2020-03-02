package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.items.ItemDropOnDeath;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemTea extends ItemDropOnDeath
{
    private IAetherStatusEffects.effectTypes effect;

    private boolean hasExtraInfo;

    public ItemTea(IAetherStatusEffects.effectTypes effect, boolean hasExtraInfo)
    {
        this.effect = effect;

        this.hasExtraInfo = hasExtraInfo;

        this.maxStackSize = 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
    {
        String time = getEffectTimeInfo(effect.activeEffectTime);

        tooltip.add(String.format("%s (%s)",
                TextFormatting.BLUE + I18n.format(effect.name),
                TextFormatting.BLUE + time));

        if (this.hasExtraInfo)
        {
            tooltip.add("");

            tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("item.aether.tea.applied.desc"));
            tooltip.add(TextFormatting.BLUE + I18n.format(this.getTranslationKey() + ".number.desc") + "% "
                    + I18n.format(this.getTranslationKey() + ".effect.desc"));
        }
    }

    private static String getEffectTimeInfo(int time)
    {
        int i = time;
        int j = time / 60;
        i = i % 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
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
                statusEffectPool.applyStatusEffect(this.effect, 100);
            }

            if (stack.isEmpty())
            {
                return new ItemStack(ItemsAether.scatterglass_vial);
            }

            if (entityPlayer != null)
            {
                entityPlayer.inventory.addItemStackToInventory(new ItemStack(ItemsAether.scatterglass_vial));
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
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
}

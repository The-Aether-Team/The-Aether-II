package com.gildedgames.aether.common.items.accessories;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDamageCharm extends ItemAccessory
{
    private float slashDamageLevel, pierceDamageLevel, impactDamageLevel;

    public ItemDamageCharm()
    {
        super();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (getSlashDamageLevel() > 0)
        {
            String slashValue;

            if (getSlashDamageLevel() % 1 == 0)
            {
                int n = Math.round(getSlashDamageLevel());
                slashValue = String.valueOf(n);
            }
            else
            {
                float n = getSlashDamageLevel();
                slashValue = String.valueOf(n);
            }

            tooltip.add(String.format(" %s %s",
                    TextFormatting.BLUE + "+" + slashValue,
                    String.format("%s %s",
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.slash"),
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.damageLevel"))));
        }

        if (getPierceDamageLevel() > 0)
        {
            String pierceValue;

            if (getPierceDamageLevel() % 1 == 0)
            {
                int n = Math.round(getPierceDamageLevel());
                pierceValue = String.valueOf(n);
            }
            else
            {
                float n = getPierceDamageLevel();
                pierceValue = String.valueOf(n);
            }

            tooltip.add(String.format(" %s %s",
                    TextFormatting.BLUE + "+" + pierceValue,
                    String.format("%s %s",
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.pierce"),
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.damageLevel"))));
        }

        if (getImpactDamageLevel() > 0)
        {
            String impactValue;

            if (getImpactDamageLevel() % 1 == 0)
            {
                int n = Math.round(getImpactDamageLevel());
                impactValue = String.valueOf(n);
            }
            else
            {
                float n = getImpactDamageLevel();
                impactValue = String.valueOf(n);
            }

            tooltip.add(String.format(" %s %s",
                    TextFormatting.BLUE + "+" + impactValue,
                    String.format("%s %s",
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.impact"),
                            TextFormatting.BLUE + I18n.format("attribute.name.aether.damageLevel"))));
        }
    }

    public ItemDamageCharm setSlashDamageLevel(float slashDamageLevel)
    {
        this.slashDamageLevel = slashDamageLevel;
        return this;
    }

    public ItemDamageCharm setPierceDamageLevel(float pierceDamageLevel)
    {
        this.pierceDamageLevel = pierceDamageLevel;
        return this;
    }

    public ItemDamageCharm setImpactDamageLevel(float impactDamageLevel)
    {
        this.impactDamageLevel = impactDamageLevel;
        return this;
    }

    public float getSlashDamageLevel()
    {
        return this.slashDamageLevel;
    }

    public float getPierceDamageLevel()
    {
        return this.pierceDamageLevel;
    }

    public float getImpactDamageLevel()
    {
        return this.impactDamageLevel;
    }
}

package com.gildedgames.aether.client.gui.container.guidebook.status.info;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiLibHelper;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class GuiResistance extends GuiElement
{
    public static final ResourceLocation BACKING = AetherCore.getResource("textures/gui/guidebook/icons/backing.png");

    private static final ResourceLocation AMBROSIUM_ICON = AetherCore.getResource("textures/gui/overlay/effects/ambrosium_poisoning.png");
    private static final ResourceLocation BLEED_ICON = AetherCore.getResource("textures/gui/overlay/effects/bleed.png");
    private static final ResourceLocation COCKATRICE_VENOM_ICON = AetherCore.getResource("textures/gui/overlay/effects/cockatrice_venom.png");
    private static final ResourceLocation FRACTURE_ICON = AetherCore.getResource("textures/gui/overlay/effects/fracture.png");
    private static final ResourceLocation FUNGAL_ROT_ICON = AetherCore.getResource("textures/gui/overlay/effects/fungal_rot.png");
    private static final ResourceLocation STUN_ICON = AetherCore.getResource("textures/gui/overlay/effects/stun.png");
    private static final ResourceLocation TOXIN_ICON = AetherCore.getResource("textures/gui/overlay/effects/toxin.png");
    private static final ResourceLocation FREEZE_ICON = AetherCore.getResource("textures/gui/overlay/effects/freeze.png");
    private static final ResourceLocation WEBBING_ICON = AetherCore.getResource("textures/gui/overlay/effects/webbing.png");
    private static final ResourceLocation IRRADIATION_ICON = AetherCore.getResource("textures/gui/overlay/effects/irradiation.png");

    private final IAetherStatusEffects effect;

    private final GuiTexture effectIcon;

    private GuiText textElement;

    public GuiResistance(final IAetherStatusEffects effect)
    {
        super(Dim2D.flush(), true);

        this.dim().mod().width(52).height(20).flush();

        this.effect = effect;
        this.effectIcon = new GuiTexture(Dim2D.build().width(16).height(16).x(2).y(2).flush(),
                this.getEffectIconFromType(this.effect.getEffectType()));
    }

    @Override
    public void build()
    {
        this.textElement = new GuiText(Dim2D.build().addX(19).addY(7).flush(), getResistanceAmount());

        GuiTexture backing = new GuiTexture(Dim2D.build().width(52).height(20).x(0).y(0).flush(), BACKING);

        this.context().addChildren(backing, this.effectIcon, this.textElement);

        GuiLibHelper.assembleMinMaxArea(this);
    }

    private Text getResistanceAmount()
    {
        float effectResistance = (float) this.effect.getResistance();
        int trueValue;

        if (effectResistance >= 0.0D && effectResistance < 2.0D && effectResistance != 1.0D)
        {
            if (effectResistance > 1.0)
            {
                trueValue = (int) ((effectResistance - 1) * 100);

                return new Text(new TextComponentString(I18n.format("gui.guidebook.status.res")
                        + String.format(" +%s%s", trueValue, "%")), 0.675F);
            }
            else if (effectResistance < 1.0)
            {
                trueValue = (int) ((effectResistance - 1) * -100);

                return new Text(new TextComponentString(I18n.format("gui.guidebook.status.weak")
                        + String.format(" +%s%s", trueValue, "%")), 0.675F);
            }
        }
        else if (effectResistance >= 2.0D)
        {
            return new Text(new TextComponentString(I18n.format("gui.guidebook.status.res")
                    + " " + I18n.format("gui.guidebook.status.full")), 0.675F);
        }

        return new Text(new TextComponentString(""), 0.675F);
    }

    public IAetherStatusEffects getEffect()
    {
        return this.effect;
    }

    private ResourceLocation getEffectIconFromType(IAetherStatusEffects.effectTypes effectType)
    {
        switch(effectType)
        {
            case AMBROSIUM_POISONING:
                return AMBROSIUM_ICON;
            case BLEED:
                return BLEED_ICON;
            case FRACTURE:
                return FRACTURE_ICON;
            case COCKATRICE_VENOM:
                return COCKATRICE_VENOM_ICON;
            case FUNGAL_ROT:
                return FUNGAL_ROT_ICON;
            case STUN:
                return STUN_ICON;
            case TOXIN:
                return TOXIN_ICON;
            case FREEZE:
                return FREEZE_ICON;
            case WEBBING:
                return WEBBING_ICON;
            case IRRADIATION:
                return IRRADIATION_ICON;
        }

        return STUN_ICON;
    }
}

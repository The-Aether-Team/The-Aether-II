package com.gildedgames.aether.client.gui.container.guidebook.status;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.client.gui.EffectSystemOverlay;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiLibHelper;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class GuiEffectBar extends GuiElement
{
    public static final ResourceLocation BACKING = AetherCore.getResource("textures/gui/guidebook/icons/backing.png");

    private static final ResourceLocation BAR_OUTLINE = AetherCore.getResource("textures/gui/guidebook/icons/bar_outline.png");
    private static final ResourceLocation BAR_BUILDUP = AetherCore.getResource("textures/gui/guidebook/icons/buildup_bar.png");

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

    private static final ResourceLocation SATURATION_BOOST_ICON = AetherCore.getResource("textures/gui/overlay/effects/teas/saturation_boost.png");

    private static final ResourceLocation GUARD_BREAK_ICON = AetherCore.getResource("textures/gui/overlay/effects/guard_break.png");

    private final IAetherStatusEffects effect;

    private final GuiTexture effectIcon;

    private final GuiBuildupBar buildupBar;

    private GuiText textElement;

    public GuiEffectBar(IAetherStatusEffects effect)
    {
        super(Dim2D.flush(), true);

        this.dim().mod().width(52).height(18).flush();

        this.effect = effect;
        this.effectIcon = new GuiTexture(Dim2D.build().width(16).height(16).x(1).y(1).flush(),
                this.getEffectIconFromType(this.effect.getEffectType()));
        this.buildupBar = new GuiBuildupBar(Dim2D.build().width(28).height(3).x(20).y(4).flush(), BAR_BUILDUP,
                EffectSystemOverlay.Color.DEFAULT_COLOR);
    }

    @Override
    public void build()
    {
        this.textElement = new GuiText(Dim2D.build().addY(10).flush(),
                new Text(new TextComponentString(""), 0.75F));

        GuiTexture backing = new GuiTexture(Dim2D.build().width(52).height(18).x(0).y(0).flush(), BACKING);

        GuiTexture effectBar = new GuiTexture(Dim2D.build().width(30).height(5).x(19).y(3).flush(), BAR_OUTLINE);

        this.context().addChildren(backing, this.effectIcon, this.textElement, effectBar, this.buildupBar);

        GuiLibHelper.assembleMinMaxArea(this);
    }

    @Override
    public void onDraw(GuiElement element)
    {
        IAetherStatusEffectPool statusEffectPool = Minecraft.getMinecraft().player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

        if (statusEffectPool != null)
        {
            int buildup = this.effect.getBuildup();

            this.buildupBar.setColor(EffectSystemOverlay.Color.getColorFromEffect(this.effect.getEffectType()));
            int width = (int) (28F * (buildup / 100F));
            this.buildupBar.dim().mod().width(width).flush();

            this.textElement.setText(new Text(new TextComponentString(buildup + "/" + 100), 0.75F));
            this.textElement.dim().mod().x(24 - ((float) this.viewer().fontRenderer().getStringWidth(String.valueOf(buildup)) / 4)).flush();
        }
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
            case SATURATION_BOOST:
                return SATURATION_BOOST_ICON;
            case GUARD_BREAK:
                return GUARD_BREAK_ICON;
            case IRRADIATION:
                return IRRADIATION_ICON;
        }

        return STUN_ICON;
    }
}

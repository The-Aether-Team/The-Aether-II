package com.gildedgames.aether.client.gui.container.guidebook.status.info;

import com.gildedgames.aether.client.gui.EffectSystemOverlay;
import com.gildedgames.orbis.lib.client.gui.util.GuiFrameUtils;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.rect.Rect;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiBuildupBar extends GuiTexture
{
    private final ResourceLocation texture;

    private EffectSystemOverlay.Color color;

    public GuiBuildupBar(final Rect rect, final ResourceLocation texture, EffectSystemOverlay.Color color)
    {
        super(rect, texture);

        this.texture = texture;
        this.color = color;
    }

    @Override
    public void onDraw(GuiElement element)
    {
        GlStateManager.pushMatrix();

        GuiFrameUtils.applyAlpha(this.state());

        float r, g, b, a;
        r = this.color.r / 255.F;
        g = this.color.g / 255.F;
        b = this.color.b / 255.F;
        a = 1.0f;

        GlStateManager.color(r,g,b,a);

        this.viewer().mc().getTextureManager().bindTexture(this.texture);

        drawModalRectWithCustomSizedTexture(this.dim().x(), this.dim().y(), 0, 0, this.dim().width(),
                this.dim().height(),
                this.dim().width(), this.dim().height());

        GlStateManager.color(1,1,1, 1);
        GlStateManager.popMatrix();
    }

    public void setColor(EffectSystemOverlay.Color color)
    {
        this.color = color;
    }
}

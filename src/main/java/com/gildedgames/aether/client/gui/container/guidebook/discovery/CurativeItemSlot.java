package com.gildedgames.aether.client.gui.container.guidebook.discovery;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.gui.util.vanilla.GuiItemStackRender;
import com.gildedgames.orbis.lib.client.rect.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;

public class CurativeItemSlot extends GuiItemStackRender
{
    private static final ResourceLocation DARK_OVERLAY = AetherCore.getResource("textures/gui/inventory/dark_slot_overlay.png");

    public CurativeItemSlot(Rect rect)
    {
        super(rect);
    }

    @Override
    public void build()
    {
        super.build();
    }

    @Override
    public void onDraw(final GuiElement element)
    {
        if (!this.getItemStack().isEmpty())
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(DARK_OVERLAY);
            GuiTexture.drawModalRectWithCustomSizedTexture(this.dim().x(), this.dim().y(), 0, 0, 17, 17, 16, 16);
        }

        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();

        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.getItemStack(), (int) this.dim().x() + 1, (int) this.dim().y() + 1);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
}

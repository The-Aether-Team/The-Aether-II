package com.gildedgames.aether.client.gui.container.masonry_bench;

import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiMasonryCraftingOption extends GuiButton
{
    private static final ResourceLocation OPTION = AetherCore.getResource("textures/gui/inventory/crafting_option.png");

    private static final ResourceLocation OPTION_HOVER = AetherCore.getResource("textures/gui/inventory/crafting_option_hover.png");

    private ItemStack itemStack;

    public GuiMasonryCraftingOption(int buttonId, int x, int y, ItemStack itemStack)
    {
        super(buttonId, x, y, 18, 18, "");

        this.itemStack = itemStack;
    }

    public void setOutputItemStack(ItemStack stack)
    {
        this.itemStack = stack;
    }

    public ItemStack getOutputItemStack()
    {
        return this.itemStack;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (!this.itemStack.isEmpty())
        {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            if (!this.hovered)
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(OPTION);
            }
            else
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture(OPTION_HOVER);
            }
            drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, this.width, this.height, 18, 18);

            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.itemStack, this.x + 1, this.y + 1);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();

            if (this.hovered)
            {
                GuiScreen gui = Minecraft.getMinecraft().currentScreen;

                if (gui instanceof IExtendedContainer)
                {
                    IExtendedContainer extendedGui = (IExtendedContainer) gui;

                    extendedGui.setHoveredDescription(this.itemStack,
                            this.itemStack.getTooltip(Minecraft.getMinecraft().player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips ?
                                    ITooltipFlag.TooltipFlags.ADVANCED :
                                    ITooltipFlag.TooltipFlags.NORMAL));
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 300.0F);
            GlStateManager.popMatrix();
        }
    }
}

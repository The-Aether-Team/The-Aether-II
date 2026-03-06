package com.aetherteam.aetherii.client.gui.component.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.menu.AetherIITitleScreen;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.ButtonAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public class AetherIIMenuButton extends Button {
    private static final WidgetSprites AETHER_WIDGETS = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "title/button"), Identifier.fromNamespaceAndPath(AetherII.MODID, "title/button_highlighted"));
    private static final WidgetSprites AETHER_WIDGETS_SMALL = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "title/button"), Identifier.fromNamespaceAndPath(AetherII.MODID, "title/button_highlighted_small"));
    public final int originalX;
    public final int originalY;
    public int hoverOffset;
    public int buttonCountOffset;
    public boolean serverButton;

    public AetherIIMenuButton(AetherIITitleScreen screen, Builder builder) {
        super(builder);
        this.originalX = this.getX();
        this.originalY = this.getY();
        this.hoverOffset = 0;
    }

    public AetherIIMenuButton(AetherIITitleScreen screen, Button oldButton) { //todo onPress
        this(screen, new Builder(oldButton.getMessage(), oldButton.onPress).bounds(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight()).createNarration((button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage()));
        oldButton.visible = false;
        oldButton.active = false;
    }

    @Override
    public void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        Identifier location = this.getWidth() < 100 ? AETHER_WIDGETS_SMALL.get(this.isActive(), this.isHoveredOrFocused()) : AETHER_WIDGETS.get(this.isActive(), this.isHoveredOrFocused());

       // RenderSystem.enableBlend();
       // guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, 200, 20, 0, 0, this.getX() + this.hoverOffset, this.getY(), 200, 20, ARGB.white(this.alpha));
       // RenderSystem.disableBlend();

        poseStack.pushMatrix();
        float textX = this.getX() + 35 + this.hoverOffset;
        float textY = this.getY() + (this.height - 8) / 2.0F;
        poseStack.translate(textX, textY);
        guiGraphics.drawString(font, this.getMessage(), 0, 0, this.getTextColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
        poseStack.popMatrix();
    }

    /**
     * Determines the color for the button text depending on if its hovered over.
     *
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @return The decimal {@link Integer} for the color.
     */
    public int getTextColor(int mouseX, int mouseY) {
        if (!this.serverButton) {
            return this.isMouseOver(mouseX, mouseY) ? 11391231 : 13948116;
        } else {
            return this.isMouseOver(mouseX, mouseY) ? 4407144 : 15457113;
        }
    }
}

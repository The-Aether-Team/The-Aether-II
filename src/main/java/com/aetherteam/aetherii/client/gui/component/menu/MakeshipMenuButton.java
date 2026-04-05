package com.aetherteam.aetherii.client.gui.component.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.menu.AetherIITitleScreen;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.ButtonAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

import java.net.URI;

public class MakeshipMenuButton extends Button {
    private static final WidgetSprites MAKESHIP_WIDGET = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "title/makeship_button"), Identifier.fromNamespaceAndPath(AetherII.MODID, "title/makeship_button_highlighted"));
    public final int originalX;
    public final int originalY;
    public int hoverOffset;

    public MakeshipMenuButton(AetherIITitleScreen screen, Builder builder) {
        super(builder);
        this.originalX = this.getX();
        this.originalY = this.getY();
        this.hoverOffset = 0;
    }

    public MakeshipMenuButton(AetherIITitleScreen screen, Button oldButton) {
        this(screen, new Builder(oldButton.getMessage(), ((ButtonAccessor) oldButton).aether_ii$getOnPress()).bounds(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight()).createNarration((button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage()));
        oldButton.visible = false;
        oldButton.active = false;
    }

    @Override
    public void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int buttonX = (int) ((guiGraphics.guiWidth() / 2.0F + (218.0F / 2.0F)));
        int buttonY = 16;

        Identifier location = MAKESHIP_WIDGET.get(this.isActive(), this.isHoveredOrFocused());
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, 900, 524, 0, 0, buttonX, buttonY, 900, 524, ARGB.white(this.alpha));
    }

    public void onPress(InputWithModifiers p_446034_) {
        this.onPress.onPress(this);
        new ClickEvent.OpenUrl(URI.create("https://www.makeship.com/products/aerwhale-jumbo-plushie"));
    }
}

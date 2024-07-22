package com.aetherteam.aetherii.client.gui.component.toast;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public class GuidebookToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "toast/guidebook");
    private final GuidebookToast.Icons icon;
    private final Component title;
    private final Component description;
    private long lastChanged;
    private boolean changed;

    public GuidebookToast(GuidebookToast.Icons icon, Component title, Component description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        if (this.changed) {
            this.lastChanged = timeSinceLastVisible;
            this.changed = false;
        }
        guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
        guiGraphics.drawString(toastComponent.getMinecraft().font, this.title, 32, 7, -13423317, false);
        guiGraphics.drawString(toastComponent.getMinecraft().font, this.description, 32, 18, -724497, false);
        this.icon.render(guiGraphics, 6, 8);

        return (double) (timeSinceLastVisible - this.lastChanged) >= 5000.0 * toastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    public enum Icons implements StringRepresentable {
        BESTIARY(0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_bestiary")),
        EFFECTS(1, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_effects")),
        EXPLORATION(2, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_exploration"));

        public static final IntFunction<Icons> BY_ID = ByIdMap.continuous(Icons::id, Icons.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Icons> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Icons::id);

        private final int id;
        private final ResourceLocation sprite;

        Icons(int id, ResourceLocation sprite) {
            this.id = id;
            this.sprite = sprite;
        }

        public void render(GuiGraphics guiGraphics, int x, int y) {
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(this.sprite, x, y, 22, 16);
        }

        public int id() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}

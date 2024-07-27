package com.aetherteam.aetherii.client.gui.component.toast;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
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
    private final GuidebookToast.Type type;
    private final GuidebookToast.Icons icon;
    private long lastChanged;
    private boolean changed;

    public GuidebookToast(GuidebookToast.Type type, GuidebookToast.Icons icon) {
        this.type = type;
        this.icon = icon;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        if (this.changed) {
            this.lastChanged = timeSinceLastVisible;
            this.changed = false;
        }
        guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
        this.type.render(guiGraphics, this.width(), this.height());
        this.icon.render(guiGraphics, 6, 8);
        guiGraphics.drawString(toastComponent.getMinecraft().font, Component.translatable("gui.aether_ii.toast.guidebook.description"), 32, 18, -724497, false);

        return (double) (timeSinceLastVisible - this.lastChanged) >= 5000.0 * toastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    public enum Type implements StringRepresentable {
        DEFAULT(0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "toast/guidebook")),
        DISCOVERY(1, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "toast/guidebook_discovery")),
        JOURNAL(2, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "toast/guidebook_journal"));

        public static final IntFunction<Type> BY_ID = ByIdMap.continuous(Type::id, Type.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Type::id);

        private final int id;
        private final ResourceLocation sprite;

        Type(int id, ResourceLocation sprite) {
            this.id = id;
            this.sprite = sprite;
        }

        public void render(GuiGraphics guiGraphics, int width, int height) {
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(this.sprite, 0, 0, width, height);
        }

        public int id() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum Icons implements StringRepresentable {
        BESTIARY(0, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_bestiary"), "gui.aether_ii.toast.guidebook.bestiary"),
        EFFECTS(1, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_effects"), "gui.aether_ii.toast.guidebook.effects"),
        EXPLORATION(2, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_exploration"), "gui.aether_ii.toast.guidebook.exploration");

        public static final IntFunction<Icons> BY_ID = ByIdMap.continuous(Icons::id, Icons.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Icons> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Icons::id);

        private final int id;
        private final ResourceLocation sprite;
        private final String title;

        Icons(int id, ResourceLocation sprite, String title) {
            this.id = id;
            this.sprite = sprite;
            this.title = title;
        }

        public void render(GuiGraphics guiGraphics, int x, int y) {
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(this.sprite, x, y, 22, 16);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(this.title), 32, 7, -13423317, false);
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

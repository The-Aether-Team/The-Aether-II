package com.aetherteam.aetherii.client.gui.component.toast;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import io.netty.buffer.ByteBuf;
import java.util.Locale;
import java.util.function.IntFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

public class GuidebookToast implements Toast {
    private final GuidebookToast.Type type;
    private final GuidebookToast.Icons icon;

    public GuidebookToast(GuidebookToast.Type type, GuidebookToast.Icons icon) {
        this.type = type;
        this.icon = icon;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        this.type.render(guiGraphics, this.width(), this.height());
        this.icon.render(guiGraphics, 6, 8);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(this.icon.title), 32, 7, -13423317, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("gui.aether_ii.toast.guidebook.description"), 32, 18, -724497, false);
        return timeSinceLastVisible >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }

    public enum Type implements StringRepresentable {
        DEFAULT(0, new ResourceLocation(AetherII.MODID, "toast/guidebook")),
        DISCOVERY(1, new ResourceLocation(AetherII.MODID, "toast/guidebook_discovery")),
        JOURNAL(2, new ResourceLocation(AetherII.MODID, "toast/guidebook_journal"));

        public static final IntFunction<Type> BY_ID = ByIdMap.continuous(Type::id, Type.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Type::id);

        private final int id;
        private final ResourceLocation sprite;

        Type(int id, ResourceLocation sprite) {
            this.id = id;
            this.sprite = sprite;
        }

        public int id() {
            return this.id;
        }

        public ResourceLocation sprite() {
            return this.sprite;
        }

        public void render(GuiGraphics guiGraphics, int width, int height) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, this.sprite, 0, 0, width, height);
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum Icons implements StringRepresentable {
        BESTIARY(0, new ResourceLocation(AetherII.MODID, "guidebook/icon_bestiary"), "gui.aether_ii.toast.guidebook.bestiary"),
        EFFECTS(1, new ResourceLocation(AetherII.MODID, "guidebook/icon_effects"), "gui.aether_ii.toast.guidebook.effects"),
        EXPLORATION(2, new ResourceLocation(AetherII.MODID, "guidebook/icon_exploration"), "gui.aether_ii.toast.guidebook.exploration");

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

        public int id() {
            return this.id;
        }

        public ResourceLocation sprite() {
            return this.sprite;
        }

        public void render(GuiGraphics guiGraphics, int x, int y) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, this.sprite, x, y, 22, 16);
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}



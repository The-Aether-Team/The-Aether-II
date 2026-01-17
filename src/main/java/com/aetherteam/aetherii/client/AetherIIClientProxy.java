package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AetherIIClientProxy {
    public static void sendClientPassengerMessage() {
        Component component = Component.translatable("aether_ii.message.passenger.onboard", AetherIIKeyMappings.ALLOW_DISMOUNTING_PASSENGER.getTranslatedKeyMessage(), Minecraft.getInstance().options.keyUse.getTranslatedKeyMessage());
        Minecraft.getInstance().gui.setOverlayMessage(component, false);
        Minecraft.getInstance().getNarrator().saySystemNow(component);
    }

    public static boolean isHighlandsSpecialEffects(Level level) {
        return level instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects;
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Nullable
    public static <T> T getClientPlayerData(Supplier<AttachmentType<T>> holder) {
        if (Minecraft.getInstance().player != null) {
            return Minecraft.getInstance().player.getData(holder);
        } else {
            return null;
        }
    }
}

package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.DeathScreenAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.OutpostRespawnPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class PlayerRespawnClientListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(PlayerRespawnClientListeners::postScreenInitialization);
    }

    public static void postScreenInitialization(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof DeathScreen deathScreen) {
            if (!Minecraft.getInstance().player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).getCampfirePositions().isEmpty()) {
                Button testButton = Button.builder(Component.translatable("gui.aether_ii.deathScreen.outpost_respawn"), (button) -> {
                    PacketDistributor.sendToServer(new OutpostRespawnPacket());
                    Minecraft.getInstance().player.respawn();
                    button.active = false;
                }).bounds(deathScreen.width / 2 - 100, deathScreen.height / 4 + 96, 200, 20).build();
                event.addListener(testButton);
                testButton.active = false;
                ((DeathScreenAccessor) deathScreen).aether$getExitButtons().add(testButton);
                for (GuiEventListener listener : event.getListenersList()) {
                    if (listener instanceof Button button) {
                        if (button.getMessage().equals(Component.translatable("deathScreen.titleScreen"))) {
                            button.setPosition(button.getX(), button.getY() + 24);
                        }
                    }
                }
            }
        }
    }
}
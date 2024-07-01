package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class GuiExtensionHooks {
    @Nullable
    public static Button setupAccessoryButton(Screen screen) {
        Screen containerScreen = canCreateAccessoryButtonForScreen(screen);
        if (containerScreen != null) {
            return Button.builder(Component.literal("Guidebook"), (button) -> {
                Minecraft minecraft = Minecraft.getInstance();
                Player player = minecraft.player;
                if (player != null) {
                    ItemStack stack = player.containerMenu.getCarried();
                    player.containerMenu.setCarried(ItemStack.EMPTY);

                    if (containerScreen instanceof Guidebook) {
                        InventoryScreen inventory = new InventoryScreen(player);
                        minecraft.setScreen(inventory);
                        player.inventoryMenu.setCarried(stack);
                        PacketDistributor.sendToServer(new OpenInventoryPacket(stack));
                    } else {
                        PacketDistributor.sendToServer(new OpenGuidebookPacket(stack));
                    }
                }
            }).pos((screen.width / 2) - 50, screen.height - 35).width(100).build();
        }
        return null;
    }

    @Nullable
    private static Screen canCreateAccessoryButtonForScreen(Screen screen) {
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen || screen instanceof Guidebook) {
            return screen;
        }
        return null;
    }
}

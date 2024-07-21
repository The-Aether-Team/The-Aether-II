package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class GuiExtensionHooks {
    public static Screen lastGuidebookScreen = null;
    public static boolean forceCloseGuidebook = false;

    public static Screen openStoredGuidebookScreen(Screen screen) {
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            if (lastGuidebookScreen != null && !forceCloseGuidebook) {
                return lastGuidebookScreen;
            }
        } else if (screen instanceof Guidebook) {
            forceCloseGuidebook = false;
        }
        return null;
    }

    @Nullable
    public static Button setupAccessoryButton(Screen screen) {
        Screen containerScreen = canCreateAccessoryButtonForScreen(screen);
        if (containerScreen != null) {
            Component message;
            ItemLike renderItem;
            if (containerScreen instanceof Guidebook) {
                message = Component.translatable("gui.aether_ii.guidebook.button.close");
                renderItem = Blocks.GRASS_BLOCK;
            } else {
                message = Component.translatable("gui.aether_ii.guidebook.button.open");
                renderItem = AetherIIBlocks.AETHER_GRASS_BLOCK;
            }
            return new GuidebookButton(renderItem, Button.builder(message, (button) -> {
                Minecraft minecraft = Minecraft.getInstance();
                Player player = minecraft.player;
                if (player != null) {
                    ItemStack stack = player.containerMenu.getCarried();
                    player.containerMenu.setCarried(ItemStack.EMPTY);

                    if (containerScreen instanceof Guidebook) {
                        forceCloseGuidebook = true;
                        InventoryScreen inventory = new InventoryScreen(player);
                        minecraft.setScreen(inventory);
                        player.inventoryMenu.setCarried(stack);
                        PacketDistributor.sendToServer(new OpenInventoryPacket(stack));
                    } else {
                        PacketDistributor.sendToServer(new OpenGuidebookPacket(stack));
                    }
                }
            }).pos((screen.width / 2) - 50, (screen.height / 2) + 101).size(100, 22));
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

    public static void storeGuidebookScreen(Screen screen) {
        if (screen instanceof Guidebook) {
            lastGuidebookScreen = screen;
        } else if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            lastGuidebookScreen = null;
        }
    }
}

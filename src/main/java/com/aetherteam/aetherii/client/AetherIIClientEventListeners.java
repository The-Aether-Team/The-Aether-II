package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.event.hooks.MusicHooks;
import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.Input;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class AetherIIClientEventListeners {
    public static void listen(IEventBus bus) {
        // Screen
        bus.addListener(AetherIIClientEventListeners::onGuiOpen);
        bus.addListener(AetherIIClientEventListeners::onGuiInitializePost);
        bus.addListener(AetherIIClientEventListeners::onGuiClose);

        // Tooltip
        bus.addListener(AetherIIClientEventListeners::onGatherTooltipComponents);

        // World
        bus.addListener(AetherIIClientEventListeners::onComputeFogColor);

        // Audio
        bus.addListener(AetherIIClientEventListeners::onMusicSelected);

        // Input
        bus.addListener(AetherIIClientEventListeners::onMovementInputUpdate);
    }

    public static void onGuiOpen(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();

        Screen storedScreen = RenderHooks.openStoredGuidebookScreen(screen);
        if (storedScreen != null) {
            event.setNewScreen(storedScreen);
        }
    }

    public static void onGuiInitializePost(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        List<GuiEventListener> listeners = event.getListenersList();

        Button inventoryAccessoryButton = RenderHooks.setupAccessoryButton(screen);
        if (inventoryAccessoryButton != null) {
            event.addListener(inventoryAccessoryButton);
        }

        Button outpostRespawnButton = RenderHooks.setupOutpostRespawnButton(screen, listeners);
        if (outpostRespawnButton != null) {
            event.addListener(outpostRespawnButton);
        }
    }

    public static void onGuiClose(ScreenEvent.Closing event) {
        Screen screen = event.getScreen();

        RenderHooks.storeGuidebookScreen(screen);
    }

    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack itemStack = event.getItemStack();
        List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();

        RenderHooks.addCharmTooltip(itemStack, tooltipElements);
    }

    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        float red = event.getRed();
        float green = event.getGreen();
        float blue = event.getBlue();

        Triple<Float, Float, Float> renderFogColors = RenderHooks.adjustHeightBasedFogColors(camera, red, green, blue);
        if (renderFogColors != null) {
            red = renderFogColors.getLeft();
            green = renderFogColors.getMiddle();
            blue = renderFogColors.getRight();
        }
        Triple<Float, Float, Float> adjustWeatherFogColors = RenderHooks.adjustWeatherFogColors(camera, red, green, blue);
        if (adjustWeatherFogColors != null) {
            red = adjustWeatherFogColors.getLeft();
            green = adjustWeatherFogColors.getMiddle();
            blue = adjustWeatherFogColors.getRight();
        }

        if (event.getRed() != red) {
            event.setRed(red);
        }
        if (event.getGreen() != green) {
            event.setGreen(green);
        }
        if (event.getBlue() != blue) {
            event.setBlue(blue);
        }
    }

    public static void onMusicSelected(SelectMusicEvent event) {
        Music music = MusicHooks.getSituationalMusic();
        if (music != null) {
            event.setMusic(music);
        }
    }

    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();

        player.getData(AetherIIDataAttachments.PLAYER).movementInput(player, input);
    }
}

package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import com.aetherteam.aetherii.client.gui.component.AetherIIImageButton;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import com.aetherteam.aetherii.client.gui.screen.AlphaInfoScreen;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.renderer.level.DungeonBlockOverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

public class AetherIIClientEventListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(AetherIIClientEventListeners::onGuiOpen);
        bus.addListener(AetherIIClientEventListeners::onGuiInitializePost);
        bus.addListener(AetherIIClientEventListeners::onGuiClose);
        bus.addListener(EventPriority.LOWEST, AetherIIClientEventListeners::onAddTooltipsLowest);
        bus.addListener(AetherIIClientEventListeners::onGatherTooltipComponents);
        bus.addListener(AetherIIClientEventListeners::onPlaySound);
        bus.addListener(AetherIIClientEventListeners::onRenderLevelStage);
        bus.addListener(AetherIIClientEventListeners::onMouseInputPost);
        bus.addListener(AetherIIClientEventListeners::onMovementInputUpdate);
    }

    public static void onGuiOpen(ScreenEvent.Opening event) {
        Screen storedScreen = RenderHooks.openStoredGuidebookScreen(event.getScreen());
        if (storedScreen != null) {
            event.setNewScreen(storedScreen);
        }
    }

    public static void onGuiInitializePost(ScreenEvent.Init.Post event) {
        Button guidebookButton = RenderHooks.setupGuidebookButton(event.getScreen());
        if (guidebookButton != null) {
            event.addListener(guidebookButton);
        }
        if (event.getScreen() instanceof Guidebook) {
            String spriteName = AetherIIConfig.COMMON.yellow_alpha_button.get() ? "alpha_info_yellow" : "alpha_info";
            AetherIIWidgetSprites sprites = new AetherIIWidgetSprites(
                    new ResourceLocation(AetherII.MODID, "icon/" + spriteName),
                    new ResourceLocation(AetherII.MODID, "icon/" + spriteName));
            Button alphaInfoButton = new AetherIIImageButton((event.getScreen().width / 2) + 54, (event.getScreen().height / 2) + 101, 22, 22, sprites, (button) -> {
                Minecraft.getInstance().setScreen(new AlphaInfoScreen(event.getScreen()));
                AetherIIConfig.COMMON.yellow_alpha_button.set(false);
            });
            alphaInfoButton.setTooltip(Tooltip.create(Component.literal("Alpha Info")));
            event.addListener(alphaInfoButton);
        }
    }

    public static void onGuiClose(ScreenEvent.Closing event) {
        RenderHooks.storeGuidebookScreen(event.getScreen());
    }

    public static void onAddTooltipsLowest(ItemTooltipEvent event) {
        RenderHooks.addReinforcementTooltip(event.getItemStack(), event.getToolTip(), event.getFlags());
    }

    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        RenderHooks.addCharmTooltip(event.getItemStack(), event.getTooltipElements());
    }

    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        SoundInstance sound = event.getOriginalSound();
        if (AudioHooks.preventAmbientPortalSound(soundEngine, sound) || AudioHooks.preventMusicDuringPortal(soundEngine, sound)) {
            event.setSound(null);
            return;
        }
        AudioHooks.overrideActivatedPortalSound(soundEngine, sound);
    }

    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        DungeonBlockOverlayRenderer.renderDungeonBlockOverlays(event);
    }

    public static void onMouseInputPost(InputEvent.MouseButton.Post event) {
        Player player = Minecraft.getInstance().player;
        int button = event.getButton();
        int action = event.getAction();
        boolean isUseItem = button == Minecraft.getInstance().options.keyUse.getKey().getValue();

        if (player != null) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).mouseInput(player, isUseItem, action);
        }
    }

    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).movementInput(player, event.getInput());
    }
}

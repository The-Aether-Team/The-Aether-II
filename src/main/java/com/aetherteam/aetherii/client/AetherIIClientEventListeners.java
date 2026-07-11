package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import com.aetherteam.aetherii.client.gui.screen.AlphaInfoScreen;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.DialogScreenAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.dialog.DialogScreen;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AetherIIClientEventListeners {
    public static void listen(IEventBus bus) {
        // Screen
        bus.addListener(AetherIIClientEventListeners::onGuiOpen);
        bus.addListener(AetherIIClientEventListeners::onGuiInitializePost);
        bus.addListener(AetherIIClientEventListeners::onGuiClose);
        bus.addListener(AetherIIClientEventListeners::onRenderBossBar);

        // Tooltip
        bus.addListener(EventPriority.LOWEST, AetherIIClientEventListeners::onAddTooltipsLowest);
        bus.addListener(AetherIIClientEventListeners::onAddAttributeTooltips);
        bus.addListener(AetherIIClientEventListeners::onGatherTooltipComponents);

        // Entity
        bus.addListener(AetherIIClientEventListeners::doRenderNameTag);

        // Audio
        bus.addListener(AetherIIClientEventListeners::onPlaySound);
        bus.addListener(AetherIIClientEventListeners::onMusicSelected);

        // Input
        bus.addListener(AetherIIClientEventListeners::onMouseInputPost);
        bus.addListener(AetherIIClientEventListeners::onMovementInputUpdate);

        // Datapacks
        bus.addListener(AetherIIClientEventListeners::onDatapackSync);
        bus.addListener(AetherIIClientEventListeners::onReceiveRecipes);
    }

    public static void onGuiOpen(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();

        Screen storedScreen = RenderHooks.openStoredGuidebookScreen(screen);
        if (storedScreen != null) {
            event.setNewScreen(storedScreen);
        }

        if (screen instanceof DialogScreen<?> dialogScreen) {
            Dialog dialog = ((DialogScreenAccessor<?>) dialogScreen).aether_ii$getDialog();
            if (dialog.common().title().equals(AetherIIPlayerAttachment.getDialog().common().title())) {
                event.setNewScreen(new AlphaInfoScreen(null));
            }
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

        if (screen instanceof Guidebook) {
            String spriteName = AetherIIConfig.COMMON.yellow_alpha_button.get() ? "alpha_info_yellow" : "alpha_info";
            Button button = SpriteIconButton.builder(Component.literal("Alpha Info"),  (b) -> {
                Minecraft.getInstance().setScreen(new AlphaInfoScreen(screen));
                AetherIIConfig.COMMON.yellow_alpha_button.set(false);
            }, true).size(22, 22).sprite(Identifier.fromNamespaceAndPath(AetherII.MODID, "icon/" + spriteName), 14, 14).build();
            button.setPosition((screen.width / 2) + 54, (screen.height / 2) + 101);
            button.setTooltip(Tooltip.create(Component.literal("Alpha Info")));
            event.addListener(button);
        }
    }

    public static void onGuiClose(ScreenEvent.Closing event) {
        Screen screen = event.getScreen();

        RenderHooks.storeGuidebookScreen(screen);
    }

    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
        LerpingBossEvent bossEvent = event.getBossEvent();
        UUID bossUUID = bossEvent.getId();
        if (RenderHooks.isAetherBossBar(bossUUID)) {
            RenderHooks.drawBossHealthBar(guiGraphics, event.getX(), event.getY(), bossEvent);
            event.setIncrement(event.getIncrement() + 13);
        }
    }

    public static void onAddTooltipsLowest(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        List<Component> itemTooltips = event.getToolTip();
        Item.TooltipContext context = event.getContext();
        TooltipFlag flag = event.getFlags();

        RenderHooks.addReinforcementTooltip(itemStack, itemTooltips, context, flag);
    }

    public static void onAddAttributeTooltips(AddAttributeTooltipsEvent event) {
        ItemStack itemStack = event.getStack();
        AttributeTooltipContext context = event.getContext();
        List<Component> tooltipLines = new ArrayList<>();

        RenderHooks.addAbilityAttributeTooltip(itemStack, tooltipLines, context);

        event.addTooltipLines(tooltipLines.toArray(Component[]::new));
    }

    public static void onGatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack itemStack = event.getItemStack();
        List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();

        RenderHooks.addCharmTooltip(itemStack, tooltipElements);
    }

    public static void doRenderNameTag(RenderNameTagEvent.DoRender event) {
        EntityRenderState renderState = event.getEntityRenderState();
        PoseStack poseStack = event.getPoseStack();

        RenderHooks.offsetNameTag(renderState, poseStack);
    }

    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        SoundInstance sound = event.getOriginalSound();
        if (AudioHooks.preventAmbientPortalSound(soundEngine, sound) || AudioHooks.preventMusicDuringPortal(soundEngine, sound)) {
            event.setSound(null);
        }
        AudioHooks.overrideActivatedPortalSound(soundEngine, sound);
    }

    public static void onMusicSelected(SelectMusicEvent event) {
        Music music = AudioHooks.getSituationalMusic();
        if (music != null) {
            event.setMusic(music);
        }
    }

    public static void onMouseInputPost(InputEvent.MouseButton.Post event) {
        Player player = Minecraft.getInstance().player;
        int button = event.getButton();
        int action = event.getAction();
        boolean isUseItem = button == Minecraft.getInstance().options.keyUse.getKey().getValue();

        if (player != null) {
            player.getData(AetherIIDataAttachments.PLAYER).mouseInput(player, isUseItem, action);
        }
    }

    public static void onMovementInputUpdate(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        ClientInput input = event.getInput();

        player.getData(AetherIIDataAttachments.PLAYER).movementInput(player, input);
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        AetherIIClientCaches.onDatapackSync(event);
    }

    public static void onReceiveRecipes(RecipesReceivedEvent event) {
        AetherIIClientCaches.onReceiveRecipes(event);
    }
}

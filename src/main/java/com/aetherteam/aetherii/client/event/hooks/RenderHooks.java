package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.renderer.item.tooltip.ClientCharmTooltip;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.InventoryScreenAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.MouseHandlerAccessor;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RenderHooks {
    public static final Map<UUID, Integer> BOSS_EVENTS = new ConcurrentHashMap<>();
    public static Screen lastGuidebookScreen = null;
    public static boolean forceCloseGuidebook = false;

    public static Screen openStoredGuidebookScreen(Screen screen) {
        Screen newScreen = null;
        if (Minecraft.getInstance().player != null) {
            if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
                if (!forceCloseGuidebook && lastGuidebookScreen instanceof Guidebook guidebook) {
                    for (Guidebook.Tab tab : Guidebook.Tab.values()) {
                        Screen screenToOpen = tab.getScreen().apply(guidebook.getEquipmentMenu(), guidebook.getPlayerInventory());
                        if (lastGuidebookScreen.getClass() == screenToOpen.getClass()) {
                            newScreen = screenToOpen;
                            break;
                        }
                    }
                }
            } else if (screen instanceof Guidebook) {
                forceCloseGuidebook = false;
            }
        }
        if (newScreen instanceof GuidebookEquipmentScreen) {
            ClientPacketDistributor.sendToServer(new OpenGuidebookPacket(ItemStack.EMPTY));
        }
        return newScreen;
    }

    public static Button setupGuidebookButton(Screen screen) {
        Screen containerScreen = canCreateGuidebookButtonForScreen(screen);
        if (containerScreen != null) {
            Component message;
            ItemLike renderItem;
            if (containerScreen instanceof Guidebook) {
                message = Component.translatable("gui.aether_ii.guidebook.button.close");
                renderItem = Blocks.GRASS_BLOCK;
            } else {
                message = Component.translatable("gui.aether_ii.guidebook.button.open");
                renderItem = AetherIIBlocks.AETHER_GRASS_BLOCK.get();
            }
            return new GuidebookButton(renderItem, message, (screen.width / 2) - 50, guidebookButtonY(screen), 100, 22, (button) -> {
                Minecraft minecraft = Minecraft.getInstance();
                LocalPlayer player = minecraft.player;
                if (player != null) {
                    ItemStack stack = player.containerMenu.getCarried();
                    player.containerMenu.setCarried(ItemStack.EMPTY);

                    if (containerScreen instanceof Guidebook guidebook) {
                        forceCloseGuidebook = true;
                        MouseHandlerAccessor handlerAccessor = (MouseHandlerAccessor) minecraft.mouseHandler;
                        handlerAccessor.aether_ii$setMouseGrabbed(true);
                        player.clientSideCloseContainer();
                        InventoryScreen inventory = new InventoryScreen(player);
                        InventoryScreenAccessor inventoryAccessor = (InventoryScreenAccessor) inventory;
                        handlerAccessor.aether_ii$setMouseGrabbed(false);
                        minecraft.setScreen(inventory);
                        inventoryAccessor.aether_ii$setXMouse(guidebook.getMouseX());
                        inventoryAccessor.aether_ii$setYMouse(guidebook.getMouseY());
                        player.inventoryMenu.setCarried(stack);
                        ClientPacketDistributor.sendToServer(new OpenInventoryPacket(stack));
                    } else {
                        ClientPacketDistributor.sendToServer(new OpenGuidebookPacket(stack));
                    }
                }
            });
        }
        return null;
    }

    private static int guidebookButtonY(Screen screen) {
        return Math.max(0, Math.min((screen.height / 2) + 101, screen.height - 24));
    }

    private static Screen canCreateGuidebookButtonForScreen(Screen screen) {
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

    public static void addReinforcementTooltip(ItemStack stack, List<Component> components, TooltipFlag flag) {
        ReinforcementTier tier = AetherIIDataComponents.get(stack, AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            components.add(Math.min(1, components.size()), ReinforcementTier.createReinforcementComponent(tier.getTierNumber()));
        }
    }

    public static void addCharmTooltip(ItemStack itemStack, List<Either<FormattedText, TooltipComponent>> tooltipElements) {
        Component id = Component.literal(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY);
        int componentIndex = tooltipElements.size();

        for (int i = 0; i < tooltipElements.size(); i++) {
            Either<FormattedText, TooltipComponent> tooltips = tooltipElements.get(i);
            Optional<FormattedText> text = tooltips.left();
            if (text.isPresent() && text.get().getString().equals(id.getString())) {
                componentIndex = i;
            }
        }

        List<Charms.CharmHolder> charmHolders = Charms.getCharmsForItem(itemStack);
        if (charmHolders != null) {
            tooltipElements.add(componentIndex, Either.right(new ClientCharmTooltip.CharmTooltip(itemStack, charmHolders)));
        }
    }

    public static boolean drawBossHealthBar(GuiGraphics guiGraphics, int x, int y, LerpingBossEvent bossEvent) {
        Integer entityID = BOSS_EVENTS.get(bossEvent.getId());
        if (entityID != null && Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(entityID) instanceof AetherBossMob<?> aetherBossMob) {
            drawBar(guiGraphics, x + 2, y + 2, bossEvent, aetherBossMob);
            Component component = aetherBossMob.getBossName();
            int nameLength = Minecraft.getInstance().font.width(component);
            int nameX = guiGraphics.guiWidth() / 2 - nameLength / 2;
            int nameY = y - 9;
            guiGraphics.drawString(Minecraft.getInstance().font, component, nameX, nameY, -1);
            return true;
        }
        return false;
    }

    private static void drawBar(GuiGraphics guiGraphics, int x, int y, LerpingBossEvent bossEvent, AetherBossMob<?> aetherBossMob) {
        ResourceLocation background = aetherBossMob.getBossBarBackgroundTexture();
        ResourceLocation foreground = aetherBossMob.getBossBarTexture();
        if (background != null && foreground != null) {
            x -= 37;
            guiGraphics.blit(guiTexture(background), x, y, 0.0F, 0.0F, 256, 16, 256, 16);
            int health = (int) (bossEvent.getProgress() * 256.0F);
            if (health > 0) {
                guiGraphics.blit(guiTexture(foreground), x, y, 0.0F, 0.0F, health, 16, 256, 16);
            }
        }
    }

    private static ResourceLocation guiTexture(ResourceLocation sprite) {
        return new ResourceLocation(sprite.getNamespace(), "textures/gui/sprites/" + sprite.getPath() + ".png");
    }
}

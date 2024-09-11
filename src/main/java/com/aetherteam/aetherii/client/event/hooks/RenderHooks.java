package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.renderer.item.tooltip.ClientCharmTooltip;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.DeathScreenAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OutpostRespawnPacket;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class RenderHooks {
    public static Screen lastGuidebookScreen = null;
    public static boolean forceCloseGuidebook = false;

    public static Screen openStoredGuidebookScreen(Screen screen) {
        Screen newScreen = null;
        if (Minecraft.getInstance().player != null && (Minecraft.getInstance().player.portalProcess == null || !Minecraft.getInstance().player.portalProcess.isInsidePortalThisTick())) {
            if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
                if (!forceCloseGuidebook && lastGuidebookScreen instanceof Guidebook guidebook) {
                    for (Guidebook.Tab tab : Guidebook.Tab.values()) {
                        Screen screenToOpen = tab.getScreen().apply(guidebook.getEquipmentMenu(), guidebook.getPlayerInventory());
                        if (lastGuidebookScreen.getClass() == screenToOpen.getClass()) {
                            newScreen = screenToOpen;
                        }
                    }
                }
            } else if (screen instanceof Guidebook) {
                forceCloseGuidebook = false;
            }
        }
        if (newScreen instanceof GuidebookEquipmentScreen) {
            PacketDistributor.sendToServer(new OpenGuidebookPacket(ItemStack.EMPTY));
        }
        return newScreen;
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

    public static Button setupOutpostRespawnButton(Screen screen, List<GuiEventListener> listeners) {
        if (screen instanceof DeathScreen deathScreen) {
            if (!Minecraft.getInstance().player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).getCampfirePositions().isEmpty()) {
                Button outpostRespawnButton = Button.builder(Component.translatable("gui.aether_ii.deathScreen.outpost_respawn"), (button) -> {
                    PacketDistributor.sendToServer(new OutpostRespawnPacket());
                    Minecraft.getInstance().player.respawn();
                    button.active = false;
                }).bounds(deathScreen.width / 2 - 100, deathScreen.height / 4 + 96, 200, 20).build();
                outpostRespawnButton.active = false;
                ((DeathScreenAccessor) deathScreen).aether$getExitButtons().add(outpostRespawnButton);
                for (GuiEventListener listener : listeners) {
                    if (listener instanceof Button button) {
                        if (button.getMessage().equals(Component.translatable("deathScreen.titleScreen"))) {
                            button.setPosition(button.getX(), button.getY() + 24);
                        }
                    }
                }
                return outpostRespawnButton;
            }
        }
        return null;
    }

    public static void addCharmTooltip(ItemStack itemStack, List<Either<FormattedText, TooltipComponent>> tooltipElements) {
        Component id = Component.literal(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY);
        int componentIndex = tooltipElements.size();

        for (int i = 0; i < tooltipElements.size(); i++) {
            Either<FormattedText, TooltipComponent> tooltips = tooltipElements.get(i);
            Optional<FormattedText> text = tooltips.left();
            if (text.isPresent() && text.get().getString().equals(id.getString())) {
                componentIndex = i - 1;
            }
        }
        List<ItemStack> charms = itemStack.get(AetherIIDataComponents.CHARMS);
        if (charms != null) {
            tooltipElements.add(componentIndex, Either.right(new ClientCharmTooltip.CharmTooltip(itemStack, charms)));
        }
    }

    @Nullable
    public static Triple<Float, Float, Float> adjustHeightBasedFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
                ClientLevel.ClientLevelData worldInfo = clientLevel.getLevelData();
                FogType type = camera.getFluidInCamera();

                double f = (camera.getPosition().y() - 64) * worldInfo.getClearColorScale();
                if (f < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
                    if (f < 0.0F) {
                        f = 0.0F;
                    }
                    f *= f;
                    red *= (float) Math.clamp(f, 0.2F, 1.0F);
                    green *= (float) Math.clamp(f, 0.2F, 1.0F);
                    blue *= (float) Math.clamp(f * 1.25F, 0.2F * 1.25F, 1.0F);
                }

                double d0 = (camera.getPosition().y() - (double) clientLevel.getMinBuildHeight()) * worldInfo.getClearColorScale();
                if (d0 < 1.0 && type != FogType.LAVA && type != FogType.POWDER_SNOW) {
                    if (d0 < 0.0) {
                        d0 = 0.0;
                    }
                    d0 *= d0;
                    if (d0 != 0.0) {
                        red /= (float) d0;
                        green /= (float) d0;
                        blue /= (float) d0;
                    }
                }

                return Triple.of(red, green, blue);
            }
        }
        return null;
    }

    @Nullable
    public static Triple<Float, Float, Float> adjustWeatherFogColors(Camera camera, float red, float green, float blue) {
        if (camera.getEntity().level() instanceof ClientLevel clientLevel) {
            if (clientLevel.effects() instanceof HighlandsSpecialEffects) {
                FogType fluidState = camera.getFluidInCamera();
                if (fluidState == FogType.NONE) {
                    Vec3 defaultSky = Vec3.fromRGB24(clientLevel.getBiome(camera.getBlockPosition()).value().getModifiedSpecialEffects().getFogColor());
                    if (clientLevel.rainLevel > 0.0) { // Check for rain.
                        float f14 = 1.0F + clientLevel.rainLevel * 0.8F;
                        float f17 = 1.0F + clientLevel.rainLevel * 0.56F;
                        red *= f14;
                        green *= f14;
                        blue *= f17;
                    }
                    if (clientLevel.thunderLevel > 0.0) { // Check for thunder.
                        float f18 = 1.0F + clientLevel.thunderLevel * 0.66F;
                        float f19 = 1.0F + clientLevel.thunderLevel * 0.76F;
                        red *= f18;
                        green *= f18;
                        blue *= f19;
                    }
                    red = (float) Math.min(red, defaultSky.x());
                    green = (float) Math.min(green, defaultSky.y());
                    blue = (float) Math.min(blue, defaultSky.z());
                    return Triple.of(red, green, blue);
                }
            }
        }
        return null;
    }
}

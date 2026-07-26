package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.item.tooltip.ClientCharmTooltip;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.DeathScreenAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.InventoryScreenAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.MouseHandlerAccessor;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AttributeMapAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OpenInventoryPacket;
import com.aetherteam.aetherii.network.packet.serverbound.OutpostRespawnPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.tags.TagKey;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;

import javax.annotation.Nullable;
import java.util.*;

public class RenderHooks {
    public static final Map<UUID, Integer> BOSS_EVENTS = new HashMap<>();
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
            ClientPacketDistributor.sendToServer(new OpenGuidebookPacket(ItemStack.EMPTY));
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
                    ClientPacketDistributor.sendToServer(new OutpostRespawnPacket());
                    Minecraft.getInstance().player.respawn();
                    button.active = false;
                }).bounds(deathScreen.width / 2 - 100, deathScreen.height / 4 + 96, 200, 20).build();
                outpostRespawnButton.active = false;
                ((DeathScreenAccessor) deathScreen).aether$getExitButtons().add(outpostRespawnButton);
                for (GuiEventListener listener : listeners) {
                    if (listener instanceof Button button) {
                        if (button.getMessage().plainCopy().equals(Component.translatable("deathScreen.titleScreen"))) {
                            button.setPosition(button.getX(), button.getY() + 24);
                        }
                    }
                }
                return outpostRespawnButton;
            }
        }
        return null;
    }

    public static void addReinforcementTooltip(ItemStack stack, List<Component> components, Item.TooltipContext context, TooltipFlag flag) {
        stack.addToTooltip(AetherIIDataComponents.REINFORCEMENT_TIER, context, TooltipDisplay.DEFAULT, (component) -> components.add(1, component), flag);
    }

    public static void addAbilityAttributeTooltip(ItemStack itemStack, List<Component> tooltipLines, AttributeTooltipContext context) {
        TagKey<Item> armorSet = itemStack.get(AetherIIDataComponents.ARMOR_SET);
        if (armorSet != null) {
            Player player = Minecraft.getInstance().player;
            if (player != null && EquipmentUtil.getEquipment(player).stream().map(ItemStack::getItem).toList().contains(itemStack.getItem())) {
                for (Map.Entry<Holder<Attribute>, AttributeInstance> entry : ((AttributeMapAccessor) player.getAttributes()).aether_ii$getAttributes().entrySet()) {
                    for (AttributeModifier modifier : entry.getValue().getModifiers()) {
                        if (modifier.id().getPath().startsWith("armor_set.ability.") && modifier.id().getPath().contains(armorSet.location().getPath().substring(armorSet.location().getPath().lastIndexOf('/') + 1))) {
                            tooltipLines.add(entry.getKey().value().toComponent(modifier, context.flag()));
                        }
                    }
                }
            }
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

    public static void drawBossHealthBar(GuiGraphicsExtractor guiGraphics, int x, int y, LerpingBossEvent bossEvent) {
        int entityID = BOSS_EVENTS.get(bossEvent.getId());
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(entityID) instanceof AetherBossMob<?> aetherBossMob) {
            drawBar(guiGraphics, x + 2, y + 2, bossEvent, aetherBossMob);
            Component component = aetherBossMob.getBossName();
            int nameLength = Minecraft.getInstance().font.width(component);
            int nameX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - nameLength / 2;
            int nameY = y - 9;
            guiGraphics.text(Minecraft.getInstance().font, component, nameX, nameY, -1);
        }
    }

    public static void drawBar(GuiGraphicsExtractor guiGraphics, int x, int y, BossEvent bossEvent, AetherBossMob<?> aetherBossMob) {
        if (aetherBossMob.getBossBarBackgroundTexture() != null && aetherBossMob.getBossBarTexture() != null) {
            x -= 37; // The default boss health bar is offset by -91. We need -128.
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, aetherBossMob.getBossBarBackgroundTexture(), 256, 16, 0, 0, x, y, 256, 16);
            int health = (int) (bossEvent.getProgress() * 256.0F);
            if (health > 0) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, aetherBossMob.getBossBarTexture(), 256, 16, 0, 0, x, y, health, 16);
            }
        }
    }

    public static boolean isAetherBossBar(UUID uuid) {
        return BOSS_EVENTS.containsKey(uuid);
    }

    public static void offsetNameTag(EntityRenderState entityRenderState, PoseStack poseStack) {
        if (Boolean.TRUE.equals(entityRenderState.getRenderData(AetherIIRenderers.HAS_AERBUNNY))) {
            poseStack.translate(0.0, 0.3, 0.0);
        }
    }
}

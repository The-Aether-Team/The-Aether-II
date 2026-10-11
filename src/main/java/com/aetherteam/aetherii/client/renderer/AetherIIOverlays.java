package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.attachment.player.SwetLatchAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.InventoryScreenAccessor;
import com.google.common.collect.Ordering;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AetherIIOverlays {
    public static final Identifier AETHER_PORTAL_OVERLAY = Identifier.fromNamespaceAndPath(AetherII.MODID, "aether_portal_overlay");
    public static final Identifier SWET_OVERLAY = Identifier.fromNamespaceAndPath(AetherII.MODID, "swet_overlay");
    public static final Identifier SHIELD_BLOCK_CROSSHAIR = Identifier.fromNamespaceAndPath(AetherII.MODID, "shield_block_crosshair");
    public static final Identifier SHIELD_BLOCK_HOTBAR = Identifier.fromNamespaceAndPath(AetherII.MODID, "shield_block_hotbar");
    public static final Identifier AERBUNNY_HEALTH = Identifier.fromNamespaceAndPath(AetherII.MODID, "aerbunny_health");
    public static final Identifier EFFECT_BUILDUPS = Identifier.fromNamespaceAndPath(AetherII.MODID, "effect_buildups");
    public static final Identifier MOA_JUMPS = Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_jumps");

    protected static final Identifier BUILDUP_BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background");
    protected static final Identifier BUILDUP_BACKGROUND_BACKING_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_backing");
    protected static final Identifier BUILDUP_BACKGROUND_OUTLINE_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_outline");
    protected static final Identifier BUILDUP_BACKGROUND_OVERLAY_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_overlay");
    protected static final Identifier CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/crosshair_block_indicator_background");
    protected static final Identifier CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/crosshair_block_indicator_progress");
    protected static final Identifier CROSSHAIR_BLOCK_INDICATOR_BROKEN_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/crosshair_block_indicator_broken");
    protected static final Identifier HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/hotbar_block_indicator_background");
    protected static final Identifier HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/hotbar_block_indicator_progress");
    protected static final Identifier HOTBAR_BLOCK_INDICATOR_BROKEN_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/hotbar_block_indicator_broken");
    protected static final Identifier HEART_AERBUNNY_CONTAINER_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/heart/aerbunny_container");
    protected static final Identifier HEART_AERBUNNY_FULL_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/heart/aerbunny_full");
    protected static final Identifier HEART_AERBUNNY_HALF_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/heart/aerbunny_half");
    protected static final Identifier DEFAULT_JUMPS_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "hud/jumps");


    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, SWET_OVERLAY, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderSwetOverlay(guiGraphics, player);
                }
            }
        });
        event.registerAbove(SWET_OVERLAY, AETHER_PORTAL_OVERLAY, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderAetherPortalOverlay(guiGraphics, minecraft, player.getData(AetherIIDataAttachments.PLAYER.get()), partialTicks);
                }
            }
        });
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, SHIELD_BLOCK_CROSSHAIR, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderBlockIndicatorCrosshair(minecraft, guiGraphics, player, partialTicks);
                }
            }
        });
        event.registerAbove(VanillaGuiLayers.HOTBAR, SHIELD_BLOCK_HOTBAR, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderBlockIndicatorHotbar(minecraft, guiGraphics, player, partialTicks);
                }
            }
        });
        event.registerAbove(VanillaGuiLayers.VEHICLE_HEALTH, AERBUNNY_HEALTH, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                extractAerbunnyHealth(guiGraphics, minecraft.gui, player);
            }
        });
        event.registerAbove(VanillaGuiLayers.EFFECTS, EFFECT_BUILDUPS, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderEffects(minecraft, player, guiGraphics);
                }
            }
        });
        event.registerBelow(VanillaGuiLayers.BOSS_OVERLAY, MOA_JUMPS, (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(guiGraphics, player);
            }
        });
    }

    private static void renderSwetOverlay(GuiGraphicsExtractor guiGraphics, LocalPlayer player) {
        SwetLatchAttachment attachment = player.getData(AetherIIDataAttachments.SWET_LATCH);
        List<SwetLatchAttachment.LatchedSwetData> latchedSwetData = attachment.getLatchedSwetData();
        if (!latchedSwetData.isEmpty()) {
            SwetLatchAttachment.LatchedSwetData data = latchedSwetData.getFirst();
            if (data != null) {
                Identifier left1Sprite = data.overlay.left1();
                Identifier left2Sprite = data.overlay.left2();
                Identifier right1Sprite = data.overlay.right1();
                Identifier right2Sprite = data.overlay.right2();

                drawCorner(guiGraphics, left1Sprite, left2Sprite, 0, guiGraphics.guiHeight() - 128);
                drawCorner(guiGraphics, right1Sprite, right2Sprite, guiGraphics.guiWidth() - 128, guiGraphics.guiHeight() - 128);
            }
        }
    }

    private static void drawCorner(GuiGraphicsExtractor guiGraphics, Identifier sprite, Identifier sprite2, int x, int y) {
        final float startRange = 0.1F;
        final float endRange = 0.7F;

        final float oscilationRange = (endRange - startRange) / 2;
        final float oscilationOffset = oscilationRange + startRange;

        drawSingle(guiGraphics, sprite, x, y, oscilationOffset + (float) Math.sin(System.currentTimeMillis() / 200.0) * oscilationRange);
        drawSingle(guiGraphics, sprite2, x, y, oscilationOffset + (float) Math.sin((System.currentTimeMillis() / 200.0) + 60.0) * oscilationRange);
    }

    private static void drawSingle(GuiGraphicsExtractor guiGraphics, Identifier sprite, int x, int y, float alpha) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, 128, 128, ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F));
    }

    private static void renderAetherPortalOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, AetherIIPlayerAttachment handler, DeltaTracker partialTicks) {
        float timeInPortal = Mth.lerp(partialTicks.getGameTimeDeltaPartialTick(false), handler.getOldPortalIntensity(), handler.getPortalIntensity());
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal *= timeInPortal;
                timeInPortal *= timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            int i = ARGB.white(timeInPortal);
            TextureAtlasSprite textureatlassprite = minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState()).sprite();
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureatlassprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
        }
    }

    private static void renderBlockIndicatorCrosshair(Minecraft minecraft, GuiGraphicsExtractor guiGraphics, LocalPlayer player, DeltaTracker partialTicks) {
        Options options = minecraft.options;
        if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            boolean missingEndurance = attachment.getShieldEndurance() < AetherIIAttributes.getMaxEndurance(player);
            boolean displayIndicator = player.isBlocking() || missingEndurance;
            if (displayIndicator) {
                double f = attachment.getShieldEndurance() / AetherIIAttributes.getMaxEndurance(player);
                if (options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    if (options.getCameraType().isFirstPerson()) {
                        if (!minecraft.getDebugOverlay().showDebugScreen() || player.isReducedDebugInfo() || options.reducedDebugInfo().get()) {
                            guiGraphics.nextStratum();

                            int k = guiGraphics.guiWidth() / 2 - 19;
                            int j = guiGraphics.guiHeight() / 2 - 5;

                            guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k, j, 10, 10);

                            if (attachment.getShieldEndurance() == 0) {
                                int l = Mth.clamp((int) (player.getCooldowns().getCooldownPercent(AetherIIItems.SKYROOT_SHIELD.toStack(), partialTicks.getGameTimeDeltaPartialTick(false)) * 10.0F), 0, 10);
                                guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_BLOCK_INDICATOR_BROKEN_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);
                            } else {
                                int l = Mth.clamp((int) (f * 10.0F), 0, 10);
                                guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void renderBlockIndicatorHotbar(Minecraft minecraft, GuiGraphicsExtractor guiGraphics, LocalPlayer player, DeltaTracker partialTicks) {
        Options options = minecraft.options;
        if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            boolean missingEndurance = attachment.getShieldEndurance() < AetherIIAttributes.getMaxEndurance(player);
            boolean displayIndicator = player.isBlocking() || missingEndurance;
            if (displayIndicator) {
                double f = attachment.getShieldEndurance() / AetherIIAttributes.getMaxEndurance(player);
                if (options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                    HumanoidArm humanoidarm = player.getMainArm().getOpposite();
                    boolean flag = player.getOffhandItem().isEmpty();
                    int j2 = guiGraphics.guiHeight() - 20;
                    int i = guiGraphics.guiWidth() / 2;
                    int k2 = i - 91 - 22 - (!flag ? 31 : 3);
                    if (humanoidarm == HumanoidArm.RIGHT) {
                        k2 = i + 91 + 1 + (!flag ? 31 : 3);
                    }

                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k2, j2, 18, 18);

                    if (attachment.getShieldEndurance() == 0) {
                        int l1 = (int) (player.getCooldowns().getCooldownPercent(AetherIIItems.SKYROOT_SHIELD.toStack(), partialTicks.getGameTimeDeltaPartialTick(false)) * 18.0F);
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_BLOCK_INDICATOR_BROKEN_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                    } else {
                        int l1 = (int) (f * 18.0F);
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                    }
                }
            }
        }
    }

    private static void extractAerbunnyHealth(GuiGraphicsExtractor graphics, Gui gui, LocalPlayer player) {
        if (!Minecraft.getInstance().options.hideGui && player.getFirstPassenger() instanceof Aerbunny aerbunny) {
            float maxHealth = aerbunny.getMaxHealth();
            int hearts = (int) (maxHealth + 0.5F) / 2;
            if (hearts > 30) {
                hearts = 30;
            }
            if (hearts != 0) {
                int currentHealth = (int) Math.ceil(aerbunny.getHealth());
                Profiler.get().popPush("mountHealth");
                int yLine1 = graphics.guiHeight() - gui.rightHeight;
                int xRight = graphics.guiWidth() / 2 + 91;
                int yo = yLine1;

                for (int baseHealth = 0; hearts > 0; baseHealth += 20) {
                    int rowHearts = Math.min(hearts, 10);
                    hearts -= rowHearts;

                    for (int i = 0; i < rowHearts; i++) {
                        int xo = xRight - i * 8 - 9;
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_AERBUNNY_CONTAINER_SPRITE, xo, yo, 9, 9);
                        if (i * 2 + 1 + baseHealth < currentHealth) {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_AERBUNNY_FULL_SPRITE, xo, yo, 9, 9);
                        }

                        if (i * 2 + 1 + baseHealth == currentHealth) {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HEART_AERBUNNY_HALF_SPRITE, xo, yo, 9, 9);
                        }
                    }

                    yo -= 10;
                    gui.rightHeight += 10;
                }
            }
        }
    }

    private static void renderEffects(Minecraft minecraft, LocalPlayer player, GuiGraphicsExtractor guiGraphics) {
        Collection<EffectBuildupInstance> collection = minecraft.player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().values();
        if (!collection.isEmpty()) {
            Screen $$4 = minecraft.screen;
            if ($$4 instanceof InventoryScreen inventoryScreen && ((InventoryScreenAccessor) inventoryScreen).aether_ii$getEffects() != null && ((InventoryScreenAccessor) inventoryScreen).aether_ii$getEffects().canSeeEffects()) {
                return;
            }

            int j1 = 0;
            int k1 = 0;

            for (EffectBuildupInstance buildup : Ordering.natural().reverse().sortedCopy(collection)) {
                Holder<MobEffect> effect = buildup.getType();
                int i = guiGraphics.guiWidth();
                int j = 27;
                if (minecraft.isDemo()) {
                    j += 15;
                }

                if (effect.value().isBeneficial()) {
                    ++j1;
                    i -= 25 * j1;
                } else {
                    ++k1;
                    i -= 25 * k1;
                    j += 26;
                }

                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24);

                Color color = new Color(effect.value().getColor());

                int buildupScaledValue = Math.min(buildup.getBuildup() / (EffectsSystemAttachment.BUILDUP_CAP / 24), 24);

                guiGraphics.enableScissor(i, j + 24 - buildupScaledValue, i + 24, (j + 24 - buildupScaledValue) + buildupScaledValue);
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUILDUP_BACKGROUND_OVERLAY_SPRITE, i, (j + 24 - buildupScaledValue) - (24 - buildupScaledValue), 24, 24, ARGB.opaque(color.getRGB()));
                guiGraphics.disableScissor();

                if (buildup.isBuildupFull()) {
                    MobEffectInstance instance = player.getEffect(buildup.getType());
                    if (instance != null) {
                        int durationValueScaled = Math.min(instance.getDuration() / Math.max(1, (buildup.getInitialInstanceDuration() / 24)), 24);
                        int textureWidth = 24;
                        int textureHeight = 24;
                        int uPosition = 0;
                        int vPosition = 24 - durationValueScaled;
                        int x = i;
                        int y = j + 24 - durationValueScaled;
                        int uWidth = 24;
                        int vHeight = durationValueScaled;
                        guiGraphics.enableScissor(x, y, x + uWidth, y + vHeight);
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUILDUP_BACKGROUND_BACKING_SPRITE, x - uPosition, y - vPosition, textureWidth, textureHeight, ARGB.opaque(color.getRGB()));
                        guiGraphics.disableScissor();
                    }

                    float flashInterval = (Mth.cos((0.5F * player.tickCount) - Mth.PI) / 2.0F) + 0.5F;
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUILDUP_BACKGROUND_OUTLINE_SPRITE, i, j, 24, 24, ARGB.white(flashInterval));
                }

                Identifier location = Gui.getMobEffectSprite(effect);
                int i1 = j;
                int i_f = i;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, i_f + 3, i1 + 3, 18, 18);
            }
        }
    }

    private static void renderMoaJumps(GuiGraphicsExtractor guiGraphics, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa && !Minecraft.getInstance().options.hideGui) {
            for (int jumpCount = 0; jumpCount < moa.getMaxStamina(); jumpCount++) {
                int xPos = ((guiGraphics.guiWidth() / 2) + (jumpCount * 8)) - (moa.getMaxStamina() * 8) / 2;
                int yPos = 18;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, appendBackground(jumpCount >= moa.getRemainingStamina(), getMoaJumpTexture(moa, jumpCount)), xPos, yPos, 9, 11);
            }
        }
    }

    private static Identifier getMoaJumpTexture(Moa moa, double count) {
        AttributeInstance instance = moa.getAttribute(AetherIIAttributes.MOA_STAMINA);
        if (instance != null) {
            if (count < instance.getBaseValue()) {
                return DEFAULT_JUMPS_SPRITE;
            } else {
                Set<AttributeModifier> modifiers = instance.getModifiers();
                double currentCount = instance.getBaseValue();

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                        currentCount += (instance.getBaseValue() * modifier.amount());
                    } else {
                        currentCount += modifier.amount();
                    }

                    /*if (currentCount >= count) {
                        return moa.getOverlayTexture(modifier.id());
                    }*/
                }
            }
        }
        return DEFAULT_JUMPS_SPRITE;
    }

    private static Identifier appendBackground(boolean background, Identifier location) {
        return background ? location.withSuffix("_background") : location;
    }
}

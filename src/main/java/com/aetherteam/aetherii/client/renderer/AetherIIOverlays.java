package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.DamageSystemAttachment;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.common.Tags;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class AetherIIOverlays {
    protected static final ResourceLocation BUILDUP_BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background");
    protected static final ResourceLocation BUILDUP_BACKGROUND_BACKING_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_backing");
    protected static final ResourceLocation BUILDUP_BACKGROUND_OUTLINE_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_outline");
    protected static final ResourceLocation BUILDUP_BACKGROUND_OVERLAY_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/buildup_background_overlay");
    protected static final ResourceLocation CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/crosshair_block_indicator_background");
    protected static final ResourceLocation CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/crosshair_block_indicator_progress");
    protected static final ResourceLocation HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/hotbar_block_indicator_background");
    protected static final ResourceLocation HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hud/hotbar_block_indicator_progress");

    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildups"), (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderEffects(minecraft, player, guiGraphics, screenWidth);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "shield_blocking"), (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderBlockIndicator(minecraft, guiGraphics, player, gui, screenWidth, screenHeight);
            }
        });
    }

    private static void renderEffects(Minecraft minecraft, LocalPlayer player, GuiGraphics guiGraphics, int screenWidth) {
        Collection<EffectBuildupInstance> collection = minecraft.player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().values();
        if (!collection.isEmpty()) {
            Screen $$4 = minecraft.screen;
            if ($$4 instanceof EffectRenderingInventoryScreen effectrenderinginventoryscreen && effectrenderinginventoryscreen.canSeeEffects()) {
                return;
            }

            RenderSystem.enableBlend();
            int j1 = 0;
            int k1 = 0;
            MobEffectTextureManager mobeffecttexturemanager = minecraft.getMobEffectTextures();
            List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());

            for (EffectBuildupInstance buildup : Ordering.natural().reverse().sortedCopy(collection)) {
                MobEffect effect = buildup.getType();
                int i = screenWidth;
                int j = 27;
                if (minecraft.isDemo()) {
                    j += 15;
                }

                if (effect.isBeneficial()) {
                    ++j1;
                    i -= 25 * j1;
                } else {
                    ++k1;
                    i -= 25 * k1;
                    j += 26;
                }

                Color color = new Color(effect.getColor());
                guiGraphics.setColor((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 1.0F);

                int buildupScaledValue = Math.min(buildup.getBuildup() / (buildup.getBuildupCap() / 24), 24);

                guiGraphics.blitSprite(BUILDUP_BACKGROUND_OVERLAY_SPRITE, 24, 24, 0, 24 - buildupScaledValue, i, j + 24 - buildupScaledValue, 24, buildupScaledValue);

                guiGraphics.blitSprite(BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24);

                if (buildup.isBuildupFull()) {
                    MobEffectInstance instance = player.getEffect(buildup.getType());
                    if (instance != null) {
                        int durationValueScaled = Math.min(instance.getDuration() / (buildup.getInitialInstanceDuration() / 24), 24);
                        guiGraphics.blitSprite(BUILDUP_BACKGROUND_BACKING_SPRITE, 24, 24, 0, 24 - durationValueScaled, i, j + 24 - durationValueScaled, 24, durationValueScaled);
                    }

                    float flashInterval = (Mth.cos((0.5F * player.tickCount) - Mth.PI) / 2.0F) + 0.5F;
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, flashInterval);
                    guiGraphics.blitSprite(BUILDUP_BACKGROUND_OUTLINE_SPRITE, i, j, 24, 24);
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                }

                TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(effect);
                int i1 = j;
                int i_f = i;
                list.add(() -> guiGraphics.blit(i_f + 3, i1 + 3, 0, 18, 18, textureatlassprite));
            }

            list.forEach(Runnable::run);
        }
    }

    private static void renderBlockIndicator(Minecraft minecraft, GuiGraphics guiGraphics, LocalPlayer player, ExtendedGui gui, int screenWidth, int screenHeight) {
        Options options = minecraft.options; //todo visual for broken shield restoring to full shield using cooldown counter.
        if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            boolean missingStamina = attachment.getShieldStamina() < DamageSystemAttachment.MAX_SHIELD_STAMINA;
            boolean displayIndicator = player.isBlocking() || missingStamina;
            if (displayIndicator) {
                float f = attachment.getShieldStamina() / (float) DamageSystemAttachment.MAX_SHIELD_STAMINA;
                if (options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    if (options.getCameraType().isFirstPerson()) {
                        if (!gui.getDebugOverlay().showDebugScreen() || player.isReducedDebugInfo() || options.reducedDebugInfo().get()) {
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                            int j = screenHeight / 2 - 5;
                            int k = screenWidth / 2 - 19;
                            int l = (int) (f * 10.0F);
                            guiGraphics.blitSprite(CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k, j, 10, 10);
                            guiGraphics.blitSprite(CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);

                            RenderSystem.defaultBlendFunc();
                        }
                    }
                } else if (options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                    HumanoidArm humanoidarm = player.getMainArm().getOpposite();
                    boolean flag = player.getOffhandItem().isEmpty();
                    int j2 = screenHeight - 20;
                    int i = screenWidth / 2;
                    int k2 = i - 91 - 22 - (!flag ? 31 : 3);
                    if (humanoidarm == HumanoidArm.RIGHT) {
                        k2 = i + 91 + 1 + (!flag ? 31 : 3);
                    }

                    int l1 = (int) (f * 18.0F);
                    guiGraphics.blitSprite(HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k2, j2, 18, 18);
                    guiGraphics.blitSprite(HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                }
            }
        }
    }
}

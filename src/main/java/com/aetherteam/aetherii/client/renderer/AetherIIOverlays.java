package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.InventoryScreenAccessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

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

    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aether_portal_overlay"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderAetherPortalOverlay(guiGraphics, minecraft, player.getData(AetherIIDataAttachments.PLAYER.get()), partialTicks);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effect_buildups"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderEffects(minecraft, player, guiGraphics);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "shield_blocking"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderBlockIndicator(minecraft, guiGraphics, player);
            }
        });
    }

    private static void renderAetherPortalOverlay(GuiGraphics guiGraphics, Minecraft minecraft, AetherIIPlayerAttachment handler, DeltaTracker partialTicks) {
        float timeInPortal = Mth.lerp(partialTicks.getGameTimeDeltaPartialTick(false), handler.getOldPortalIntensity(), handler.getPortalIntensity());
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal *= timeInPortal;
                timeInPortal *= timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            int i = ARGB.white(timeInPortal);
            TextureAtlasSprite textureatlassprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState());
            guiGraphics.blitSprite(RenderType::guiTexturedOverlay, textureatlassprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
        }
    }

    private static void renderEffects(Minecraft minecraft, LocalPlayer player, GuiGraphics guiGraphics) {
        Collection<EffectBuildupInstance> collection = minecraft.player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().values();
        if (!collection.isEmpty()) {
            Screen $$4 = minecraft.screen;
            if ($$4 instanceof InventoryScreen inventoryScreen && ((InventoryScreenAccessor) inventoryScreen).aether_ii$getEffects() != null && ((InventoryScreenAccessor) inventoryScreen).aether_ii$getEffects().canSeeEffects()) {
                return;
            }

            RenderSystem.enableBlend();
            int j1 = 0;
            int k1 = 0;
            MobEffectTextureManager mobeffecttexturemanager = minecraft.getMobEffectTextures();

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

                Color color = new Color(effect.value().getColor());

                int buildupScaledValue = Math.min(buildup.getBuildup() / (buildup.getBuildupCap() / 24), 24);

                guiGraphics.enableScissor(i, j + 24 - buildupScaledValue, i + 24, (j + 24 - buildupScaledValue) + buildupScaledValue);
                guiGraphics.blitSprite(RenderType::guiTextured, BUILDUP_BACKGROUND_OVERLAY_SPRITE, i, (j + 24 - buildupScaledValue) - (24 - buildupScaledValue), 24, 24, ARGB.opaque(color.getRGB()));
                guiGraphics.disableScissor();

                guiGraphics.blitSprite(RenderType::guiTextured, BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24, ARGB.opaque(color.getRGB()));

                if (buildup.isBuildupFull()) {
                    MobEffectInstance instance = player.getEffect(buildup.getType());
                    if (instance != null) {
                        int durationValueScaled = Math.min(instance.getDuration() / Math.max(1, (buildup.getInitialInstanceDuration() / 24)), 24);
                        guiGraphics.blitSprite(RenderType::guiTextured, BUILDUP_BACKGROUND_BACKING_SPRITE, 24, 24, 0, 24 - durationValueScaled, i, j + 24 - durationValueScaled, 24, durationValueScaled);
                    }

                    float flashInterval = (Mth.cos((0.5F * player.tickCount) - Mth.PI) / 2.0F) + 0.5F;
                    guiGraphics.blitSprite(RenderType::guiTextured, BUILDUP_BACKGROUND_OUTLINE_SPRITE, i, j, 24, 24, ARGB.white(flashInterval));
                }

                TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(effect);
                int i1 = j;
                int i_f = i;
                guiGraphics.blitSprite(RenderType::guiTextured, textureatlassprite, i_f + 3, i1 + 3, 18, 18);
            }

            RenderSystem.disableBlend();
        }
    }

    private static void renderBlockIndicator(Minecraft minecraft, GuiGraphics guiGraphics, LocalPlayer player) {
        Options options = minecraft.options; //todo visual for broken shield restoring to full shield using cooldown counter.
        if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            boolean missingStamina = attachment.getShieldStamina() < DamageSystemAttachment.MAX_SHIELD_STAMINA;
            boolean displayIndicator = player.isBlocking() || missingStamina;
            if (displayIndicator) {
                float f = attachment.getShieldStamina() / (float) DamageSystemAttachment.MAX_SHIELD_STAMINA;
                if (options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    if (options.getCameraType().isFirstPerson()) {
                        if (!minecraft.getDebugOverlay().showDebugScreen() || player.isReducedDebugInfo() || options.reducedDebugInfo().get()) {
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                            int j = guiGraphics.guiHeight() / 2 - 5;
                            int k = guiGraphics.guiWidth() / 2 - 19;
                            int l = (int) (f * 10.0F);
                            guiGraphics.blitSprite(RenderType::guiTextured, CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k, j, 10, 10);
                            guiGraphics.blitSprite(RenderType::guiTextured, CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);

                            RenderSystem.defaultBlendFunc();
                        }
                    }
                } else if (options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                    HumanoidArm humanoidarm = player.getMainArm().getOpposite();
                    boolean flag = player.getOffhandItem().isEmpty();
                    int j2 = guiGraphics.guiHeight() - 20;
                    int i = guiGraphics.guiWidth() / 2;
                    int k2 = i - 91 - 22 - (!flag ? 31 : 3);
                    if (humanoidarm == HumanoidArm.RIGHT) {
                        k2 = i + 91 + 1 + (!flag ? 31 : 3);
                    }

                    int l1 = (int) (f * 18.0F);
                    guiGraphics.blitSprite(RenderType::guiTextured, HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k2, j2, 18, 18);
                    guiGraphics.blitSprite(RenderType::guiTextured, HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                }
            }
        }
    }
}

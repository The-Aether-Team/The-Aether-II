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
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.google.common.collect.Ordering;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AetherIIOverlays {
    protected static final ResourceLocation BUILDUP_BACKGROUND_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background");
    protected static final ResourceLocation BUILDUP_BACKGROUND_BACKING_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background_backing");
    protected static final ResourceLocation BUILDUP_BACKGROUND_OUTLINE_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background_outline");
    protected static final ResourceLocation BUILDUP_BACKGROUND_OVERLAY_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background_overlay");
    protected static final ResourceLocation CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE = new ResourceLocation(AetherII.MODID, "hud/crosshair_block_indicator_background");
    protected static final ResourceLocation CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE = new ResourceLocation(AetherII.MODID, "hud/crosshair_block_indicator_progress");
    protected static final ResourceLocation CROSSHAIR_BLOCK_INDICATOR_BROKEN_SPRITE = new ResourceLocation(AetherII.MODID, "hud/crosshair_block_indicator_broken");
    protected static final ResourceLocation HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE = new ResourceLocation(AetherII.MODID, "hud/hotbar_block_indicator_background");
    protected static final ResourceLocation HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE = new ResourceLocation(AetherII.MODID, "hud/hotbar_block_indicator_progress");
    protected static final ResourceLocation HOTBAR_BLOCK_INDICATOR_BROKEN_SPRITE = new ResourceLocation(AetherII.MODID, "hud/hotbar_block_indicator_broken");
    protected static final ResourceLocation HEART_AERBUNNY_CONTAINER_SPRITE = new ResourceLocation(AetherII.MODID, "hud/heart/aerbunny_container");
    protected static final ResourceLocation HEART_AERBUNNY_FULL_SPRITE = new ResourceLocation(AetherII.MODID, "hud/heart/aerbunny_full");
    protected static final ResourceLocation HEART_AERBUNNY_HALF_SPRITE = new ResourceLocation(AetherII.MODID, "hud/heart/aerbunny_half");
    protected static final ResourceLocation TEXTURE_DEFAULT_JUMPS = new ResourceLocation(AetherII.MODID, "hud/jumps");


    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("aether_portal_overlay", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderAetherPortalOverlay(guiGraphics, minecraft, AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER), partialTicks);
                }
            }
        });
        event.registerAboveAll("effect_buildups", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderEffects(minecraft, player, guiGraphics);
                }
            }
        });
        event.registerAboveAll("shield_blocking", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderBlockIndicator(minecraft, guiGraphics, player, partialTicks);
                }
            }
        });
        event.registerBelowAll("swet_overlay", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                if (!minecraft.options.hideGui) {
                    renderSwetOverlay(guiGraphics, player);
                }
            }
        });
        event.registerAboveAll("moa_jumps", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(guiGraphics, player);
            }
        });
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), "aerbunny_health", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                extractAerbunnyHealth(guiGraphics, gui, player);
            }
        });
    }

    private static void extractAerbunnyHealth(GuiGraphics graphics, ForgeGui gui, LocalPlayer player) {
        if (!Minecraft.getInstance().options.hideGui && player.getFirstPassenger() instanceof Aerbunny aerbunny) {
            float maxHealth = aerbunny.getMaxHealth();
            int hearts = (int) (maxHealth + 0.5F) / 2;
            if (hearts > 30) {
                hearts = 30;
            }
            if (hearts != 0) {
                int currentHealth = (int) Math.ceil(aerbunny.getHealth());
                int yLine1 = graphics.guiHeight() - gui.rightHeight;
                int xRight = graphics.guiWidth() / 2 + 91;
                int yo = yLine1;

                for (int baseHealth = 0; hearts > 0; baseHealth += 20) {
                    int rowHearts = Math.min(hearts, 10);
                    hearts -= rowHearts;

                    for (int i = 0; i < rowHearts; i++) {
                        int xo = xRight - i * 8 - 9;
                        AetherIIGuiGraphics.blitSprite(graphics, HEART_AERBUNNY_CONTAINER_SPRITE, xo, yo, 9, 9);
                        if (i * 2 + 1 + baseHealth < currentHealth) {
                            AetherIIGuiGraphics.blitSprite(graphics, HEART_AERBUNNY_FULL_SPRITE, xo, yo, 9, 9);
                        }

                        if (i * 2 + 1 + baseHealth == currentHealth) {
                            AetherIIGuiGraphics.blitSprite(graphics, HEART_AERBUNNY_HALF_SPRITE, xo, yo, 9, 9);
                        }
                    }

                    yo -= 10;
                    gui.rightHeight += 10;
                }
            }
        }
    }

    private static void renderMoaJumps(GuiGraphics guiGraphics, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa && !Minecraft.getInstance().options.hideGui) {
            for (int jumpCount = 0; jumpCount < moa.getMaxStamina(); jumpCount++) {
                int xPos = ((guiGraphics.guiWidth() / 2) + (jumpCount * 8)) - (moa.getMaxStamina() * 8) / 2;
                int yPos = 18;
                AetherIIGuiGraphics.blitSprite(guiGraphics, appendBackground(jumpCount >= moa.getRemainingStamina(), getMoaJumpTexture(moa, jumpCount)), xPos, yPos, 9, 11);
            }
        }
    }

    private static ResourceLocation getMoaJumpTexture(Moa moa, double count) {
        AttributeInstance instance = moa.getAttribute(AetherIIAttributes.MOA_STAMINA.get());
        if (instance != null) {
            if (count < instance.getBaseValue()) {
                return TEXTURE_DEFAULT_JUMPS;
            } else {
                Set<AttributeModifier> modifiers = instance.getModifiers();
                double currentCount = instance.getBaseValue();

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                        currentCount += (instance.getBaseValue() * modifier.getAmount());
                    } else {
                        currentCount += modifier.getAmount();
                    }

                    /*if (currentCount >= count) {
                        return moa.getOverlayTexture(modifier.id());
                    }*/
                }
            }
        }
        return TEXTURE_DEFAULT_JUMPS;
    }

    private static ResourceLocation appendBackground(boolean background, ResourceLocation location) {
        if (background) {
            return location.withSuffix("_background");
        } else return location;
    }

    private static void renderAetherPortalOverlay(GuiGraphics guiGraphics, Minecraft minecraft, AetherIIPlayerAttachment handler, float partialTicks) {
        float timeInPortal = Mth.lerp(partialTicks, handler.getOldPortalIntensity(), handler.getPortalIntensity());
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal *= timeInPortal;
                timeInPortal *= timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            int i = ARGB.white(timeInPortal);
            TextureAtlasSprite textureatlassprite = minecraft.getBlockRenderer().getBlockModel(AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState()).getParticleIcon();
            AetherIIGuiGraphics.blitSprite(guiGraphics, textureatlassprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
        }
    }

    private static void renderEffects(Minecraft minecraft, LocalPlayer player, GuiGraphics guiGraphics) {
        Collection<EffectBuildupInstance> collection = AetherIIDataAttachments.get(player, AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().values();
        if (!collection.isEmpty()) {
            Screen $$4 = minecraft.screen;
            if ($$4 instanceof InventoryScreen inventoryScreen && inventoryScreen.canSeeEffects()) {
                return;
            }

            //RenderSystem.enableBlend();
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

                AetherIIGuiGraphics.blitSprite(guiGraphics, BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24);

                Color color = new Color(effect.value().getColor());

                int buildupScaledValue = Math.min(buildup.getBuildup() / (EffectsSystemAttachment.BUILDUP_CAP / 24), 24);

                guiGraphics.enableScissor(i, j + 24 - buildupScaledValue, i + 24, (j + 24 - buildupScaledValue) + buildupScaledValue);
                AetherIIGuiGraphics.blitSprite(guiGraphics, BUILDUP_BACKGROUND_OVERLAY_SPRITE, i, (j + 24 - buildupScaledValue) - (24 - buildupScaledValue), 24, 24, ARGB.opaque(color.getRGB()));
                guiGraphics.disableScissor();

                if (buildup.isBuildupFull()) {
                    MobEffectInstance instance = player.getEffect(buildup.getType().value());
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
                        AetherIIGuiGraphics.blitSprite(guiGraphics, BUILDUP_BACKGROUND_BACKING_SPRITE, x - uPosition, y - vPosition, textureWidth, textureHeight, ARGB.opaque(color.getRGB()));
                        guiGraphics.disableScissor();
                    }

                    float flashInterval = (Mth.cos((0.5F * player.tickCount) - Mth.PI) / 2.0F) + 0.5F;
                    AetherIIGuiGraphics.blitSprite(guiGraphics, BUILDUP_BACKGROUND_OUTLINE_SPRITE, i, j, 24, 24, ARGB.white(flashInterval));
                }

                TextureAtlasSprite location = minecraft.getMobEffectTextures().get(effect.value());
                int i1 = j;
                int i_f = i;
                AetherIIGuiGraphics.blitSprite(guiGraphics, location, i_f + 3, i1 + 3, 18, 18);
            }

            //RenderSystem.disableBlend();
        }
    }

    private static void renderBlockIndicator(Minecraft minecraft, GuiGraphics guiGraphics, LocalPlayer player, float partialTicks) {
        Options options = minecraft.options;
        if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            DamageSystemAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
            boolean missingEndurance = attachment.getShieldEndurance() < AetherIIAttributes.getMaxEndurance(player);
            boolean displayIndicator = player.isBlocking() || missingEndurance;
            if (displayIndicator) {
                double f = attachment.getShieldEndurance() / AetherIIAttributes.getMaxEndurance(player);
                if (options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    if (options.getCameraType().isFirstPerson()) {
                        if (!options.renderDebug || player.isReducedDebugInfo() || options.reducedDebugInfo().get()) {
                            int k = guiGraphics.guiWidth() / 2 - 19;
                            int j = guiGraphics.guiHeight() / 2 - 5;

                            AetherIIGuiGraphics.blitSprite(guiGraphics, CROSSHAIR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k, j, 10, 10);

                            if (attachment.getShieldEndurance() == 0) {
                                int l = Mth.clamp((int) (player.getCooldowns().getCooldownPercent(AetherIIItems.SKYROOT_SHIELD.get(), partialTicks) * 10.0F), 0, 10);
                                AetherIIGuiGraphics.blitSprite(guiGraphics, CROSSHAIR_BLOCK_INDICATOR_BROKEN_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);
                            } else {
                                int l = Mth.clamp((int) (f * 10.0F), 0, 10);
                                AetherIIGuiGraphics.blitSprite(guiGraphics, CROSSHAIR_BLOCK_INDICATOR_PROGRESS_SPRITE, 10, 10, 0, 10 - l, k, j + 10 - l, 10, l);
                            }
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

                    AetherIIGuiGraphics.blitSprite(guiGraphics, HOTBAR_BLOCK_INDICATOR_BACKGROUND_SPRITE, k2, j2, 18, 18);

                    if (attachment.getShieldEndurance() == 0) {
                        int l1 = (int) (player.getCooldowns().getCooldownPercent(AetherIIItems.SKYROOT_SHIELD.get(), partialTicks) * 18.0F);
                        AetherIIGuiGraphics.blitSprite(guiGraphics, HOTBAR_BLOCK_INDICATOR_BROKEN_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                    } else {
                        int l1 = (int) (f * 18.0F);
                        AetherIIGuiGraphics.blitSprite(guiGraphics, HOTBAR_BLOCK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
                    }
                }
            }
        }
    }

    private static void renderSwetOverlay(GuiGraphics guiGraphics, LocalPlayer player) {
        SwetLatchAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.SWET_LATCH);
        List<Swet> swets = attachment.getLatchedSwets();
        if (!swets.isEmpty()) {
            Swet swet = attachment.getLatchedSwets().get(0);
            if (swet != null) {
                ResourceLocation left1Sprite = swet.overlay.left1();
                ResourceLocation left2Sprite = swet.overlay.left2();
                ResourceLocation right1Sprite = swet.overlay.right1();
                ResourceLocation right2Sprite = swet.overlay.right2();

                drawCorner(guiGraphics, left1Sprite, left2Sprite, 0, guiGraphics.guiHeight() - 128);
                drawCorner(guiGraphics, right1Sprite, right2Sprite, guiGraphics.guiWidth() - 128, guiGraphics.guiHeight() - 128);
            }
        }
    }

    private static void drawCorner(GuiGraphics guiGraphics, ResourceLocation sprite, ResourceLocation sprite2, int x, int y) {
        final float startRange = 0.1F;
        final float endRange = 0.7F;

        final float oscilationRange = (endRange - startRange) / 2;
        final float oscilationOffset = oscilationRange + startRange;

        drawSingle(guiGraphics, sprite, x, y, oscilationOffset + (float) Math.sin(System.currentTimeMillis() / 200.0) * oscilationRange);
        drawSingle(guiGraphics, sprite2, x, y, oscilationOffset + (float) Math.sin((System.currentTimeMillis() / 200.0) + 60.0) * oscilationRange);
    }

    private static void drawSingle(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, float alpha) {
        //RenderSystem.enableBlend();
        AetherIIGuiGraphics.blitSprite(guiGraphics, sprite, x, y, 128, 128, ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F));
        //RenderSystem.disableBlend();
    }
}

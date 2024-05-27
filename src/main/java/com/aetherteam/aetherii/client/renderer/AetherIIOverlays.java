package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.data.AetherIIData;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
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
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class AetherIIOverlays {
    protected static final ResourceLocation BUILDUP_BACKGROUND_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background");
    protected static final ResourceLocation BUILDUP_BACKGROUND_OVERLAY_SPRITE = new ResourceLocation(AetherII.MODID, "hud/buildup_background_overlay");

    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(new ResourceLocation(AetherII.MODID, "aether_ii_effect_buildups"), (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderEffects(minecraft, gui, guiGraphics, screenWidth);
            }
        });
    }

    private static void renderEffects(Minecraft minecraft, ExtendedGui gui, GuiGraphics guiGraphics, int screenWidth) {
        Collection<EffectBuildupInstance> collection = minecraft.player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().values();
        if (!collection.isEmpty()) {
            Screen $$4 = minecraft.screen;
            if ($$4 instanceof EffectRenderingInventoryScreen effectrenderinginventoryscreen && effectrenderinginventoryscreen.canSeeEffects()) {
                return;
            }

//            RenderSystem.enableBlend();
            int j1 = 0;
            int k1 = 0;
            MobEffectTextureManager mobeffecttexturemanager = minecraft.getMobEffectTextures();
            List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());

            for (EffectBuildupInstance buildup : Ordering.natural().reverse().sortedCopy(collection)) {
                MobEffectInstance instance = buildup.getEffect();
                MobEffect mobeffect = instance.getEffect();
                var renderer = IClientMobEffectExtensions.of(instance);
                if (!renderer.isVisibleInGui(instance)) continue;
                if (instance.showIcon()) {
                    int i = screenWidth;
                    int j = 50;
                    if (minecraft.isDemo()) {
                        j += 15;
                    }

                    if (mobeffect.isBeneficial()) {
                        ++j1;
                        i -= 25 * j1;
                    } else {
                        ++k1;
                        i -= 25 * k1;
                        j += 26;
                    }


//                    AetherII.LOGGER.info(String.valueOf(new Color(instance.getEffect().getColor())));

                    Color color = new Color(instance.getEffect().getColor());
//
//                    RenderSystem.disableDepthTest();
//                    RenderSystem.depthMask(false);
//                    RenderSystem.disableBlend();
//                    RenderSystem.blendFuncSeparate(
//                            GlStateManager.SourceFactor.CONSTANT_COLOR, GlStateManager.DestFactor.CONSTANT_COLOR, GlStateManager.SourceFactor.CONSTANT_COLOR, GlStateManager.DestFactor.CONSTANT_COLOR
//                    );
                    guiGraphics.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1.0F);

                    int scaled = buildup.getBuildup() / (buildup.getBuildupCap() / 24);

                    guiGraphics.blitSprite(BUILDUP_BACKGROUND_OVERLAY_SPRITE, i, j, 24, 24);
//                    guiGraphics.blitSprite(BUILDUP_BACKGROUND_OVERLAY_SPRITE, 24, 24, 24, 24, i, j, 24, 24);
//                    RenderSystem.depthMask(true);
//                    RenderSystem.enableDepthTest();
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
//                    RenderSystem.defaultBlendFunc();
//                    RenderSystem.enableBlend();

                    float f = 1.0F;
//                    guiGraphics.blitSprite(BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24);

//                    if (renderer.renderGuiIcon(instance, gui, guiGraphics, i, j, 0, f)) continue;
//                    TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(mobeffect);
//                    int i1 = j;
//                    float f1 = f;
//                    int i_f = i;
//                    list.add(() -> {
//                        guiGraphics.setColor(1.0F, 1.0F, 1.0F, f1);
//                        guiGraphics.blit(i_f + 3, i1 + 3, 0, 18, 18, textureatlassprite);
//                        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
//                    });
                }
            }

            list.forEach(Runnable::run);
        }
    }
}

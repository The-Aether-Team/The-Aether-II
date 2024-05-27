package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;

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
                renderEffects(minecraft, guiGraphics, screenWidth);
            }
        });
    }

    private static void renderEffects(Minecraft minecraft, GuiGraphics guiGraphics, int screenWidth) {
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
                int j = 53;
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

                int color = effect.getColor();
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                guiGraphics.setColor((float) red / 255, (float) green / 255, (float) blue / 255, 1.0F);

                int scaled = Math.min(buildup.getBuildup() / (buildup.getBuildupCap() / 24), 24);

                guiGraphics.blitSprite(BUILDUP_BACKGROUND_OVERLAY_SPRITE, 24, 24, 0, 24 - scaled, i, j + 24 - scaled, 24, scaled);
                guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

                guiGraphics.blitSprite(BUILDUP_BACKGROUND_SPRITE, i, j, 24, 24);

                TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(effect);
                int i1 = j;
                int i_f = i;
                list.add(() -> guiGraphics.blit(i_f + 3, i1 + 3, 0, 18, 18, textureatlassprite));
            }

            list.forEach(Runnable::run);
        }
    }
}

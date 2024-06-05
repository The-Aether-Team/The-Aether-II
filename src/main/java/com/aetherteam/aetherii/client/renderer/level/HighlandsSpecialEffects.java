package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIShaders;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class HighlandsSpecialEffects extends DimensionSpecialEffects {
    private final DimensionSpecialEffects OVERWORLD = new DimensionSpecialEffects.OverworldEffects();

    private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation RAIN_LOCATION = new ResourceLocation(AetherII.MODID, "textures/environment/rain_light.png");
    private static final ResourceLocation SNOW_LOCATION = new ResourceLocation(AetherII.MODID, "textures/environment/snow_light.png");

    @Nullable
    private VertexBuffer cloudCoverBuffer;

    public HighlandsSpecialEffects() {
        super(256.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
        this.createCloudCoverBuffer();
    }

    private void createCloudCoverBuffer() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        if (this.cloudCoverBuffer != null) {
            this.cloudCoverBuffer.close();
        }
        this.cloudCoverBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildCloudCoverDisc(bufferbuilder, -16.0F);
        this.cloudCoverBuffer.bind();
        this.cloudCoverBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private static BufferBuilder.RenderedBuffer buildCloudCoverDisc(BufferBuilder builder, float y) {
        float f = Math.signum(y) * 512.0F;
        RenderSystem.setShader(AetherIIShaders::getPositionColorCloudCoverShader);
        builder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        builder.vertex(0.0, y, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        for (int i = -180; i <= 180; i += 9) {
            builder.vertex(f * Mth.cos((float) i * (float) (Math.PI / 180.0)), y, 512.0F * Mth.sin((float) i * (float) (Math.PI / 180.0))).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
        }
        return builder.end();
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float brightness) {
        return OVERWORLD.getBrightnessDependentFogColor(color, brightness);
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return OVERWORLD.isFoggyAt(x, z);
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        setupFog.run();
        if (!isFoggy) {
            FogType fogtype = camera.getFluidInCamera();
            if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(camera)) {
                LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
                Vec3 vec3 = this.getSkyColor(level, Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), partialTick);
                float f = (float) vec3.x;
                float f1 = (float) vec3.y;
                float f2 = (float) vec3.z;
                FogRenderer.levelFogColor();
                BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                RenderSystem.depthMask(false);
                RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                ShaderInstance shaderinstance = RenderSystem.getShader();
                ((LevelRendererAccessor) levelRenderer).aether_ii$getSkyBuffer().bind();
                ((LevelRendererAccessor) levelRenderer).aether_ii$getSkyBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
                VertexBuffer.unbind();
                RenderSystem.enableBlend();
                float[] sunriseColor = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
                if (sunriseColor != null) {
                    RenderSystem.setShader(GameRenderer::getPositionColorShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.pushPose();
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    float f3 = Mth.sin(level.getSunAngle(partialTick)) < 0.0F ? 180.0F : 0.0F;
                    poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                    float f4 = sunriseColor[0];
                    float f5 = sunriseColor[1];
                    float f6 = sunriseColor[2];
                    Matrix4f matrix4f = poseStack.last().pose();
                    bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                    bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, sunriseColor[3]).endVertex();

                    for (int j = 0; j <= 16; ++j) {
                        float f7 = (float) j * Mth.TWO_PI / 16.0F;
                        float f8 = Mth.sin(f7);
                        float f9 = Mth.cos(f7);
                        bufferBuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * sunriseColor[3]).color(sunriseColor[0], sunriseColor[1], sunriseColor[2], 0.0F).endVertex();
                    }

                    BufferUploader.drawWithShader(bufferBuilder.end());
                    poseStack.popPose();
                }

                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                poseStack.pushPose();

                float f11 = 1.0F - level.getRainLevel(partialTick);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);

                poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
                Matrix4f matrix4f1 = poseStack.last().pose();
                float f12 = 30.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SUN_LOCATION);
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferBuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                bufferBuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                BufferUploader.drawWithShader(bufferBuilder.end());
                f12 = 20.0F;
                RenderSystem.setShaderTexture(0, MOON_LOCATION);
                int k = level.getMoonPhase();
                int l = k % 4;
                int i1 = k / 4 % 2;
                float f13 = (float) (l) / 4.0F;
                float f14 = (float) (i1) / 2.0F;
                float f15 = (float) (l + 1) / 4.0F;
                float f16 = (float) (i1 + 1) / 2.0F;
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferBuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
                bufferBuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferBuilder.end());

                float f10 = level.getStarBrightness(partialTick) * f11;
                if (f10 > 0.0F) {
                    RenderSystem.setShaderColor(f10, f10, f10, f10);
                    FogRenderer.setupNoFog();
                    ((LevelRendererAccessor) levelRenderer).aether_ii$getStarBuffer().bind();
                    ((LevelRendererAccessor) levelRenderer).aether_ii$getStarBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
                    VertexBuffer.unbind();
                    setupFog.run();
                }

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
                poseStack.popPose();

                float color = Math.min(((float) (vec3.x + vec3.y + vec3.z) / 3.0F) * 1.25F, 1.0F);
                RenderSystem.setShaderColor(color, color, color, 1.0F);

                poseStack.pushPose();
                RenderSystem.enableBlend();
                this.cloudCoverBuffer.bind();
                this.cloudCoverBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, AetherIIShaders.getPositionColorCloudCoverShader());
                VertexBuffer.unbind();
                RenderSystem.disableBlend();
                poseStack.popPose();

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.depthMask(true);
            }
        }

        return true;
    }

    private boolean doesMobEffectBlockSky(Camera pCamera) {
        Entity entity = pCamera.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return false;
        } else {
            LivingEntity livingentity = (LivingEntity)entity;
            return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS);
        }
    }

    /**
     * [CODE COPY] - {@link ClientLevel#getSkyColor(Vec3, float)}.<br><br>
     * Modified to have lighter sky coloration than the Overworld during weather.
     */
    public Vec3 getSkyColor(ClientLevel level, Vec3 pos, float partialTick) {
        float f = level.getTimeOfDay(partialTick);
        Vec3 vec3 = pos.subtract(2.0, 2.0, 2.0).scale(0.25);
        BiomeManager biomeManager = level.getBiomeManager();
        Vec3 vec31 = CubicSampler.gaussianSampleVec3(vec3, (x, y, z) -> Vec3.fromRGB24(biomeManager.getNoiseBiomeAtQuart(x, y, z).value().getSkyColor()));
        float f1 = Mth.cos(f * Mth.TWO_PI) * 2.0F + 0.5F;
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        float f2 = (float) vec31.x() * f1;
        float f3 = (float) vec31.y() * f1;
        float f4 = (float) vec31.z() * f1;
        float f5 = level.getRainLevel(partialTick);
        if (f5 > 0.0F) {
            float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.61F; // Modified darkening.
            float f7 = 1.0F - f5 * 0.2F;
            f2 = f2 * f7 + f6 * (1.0F - f7);
            f3 = f3 * f7 + f6 * (1.0F - f7);
            f4 = f4 * f7 + f6 * (1.0F - f7);
        }

        float f9 = level.getThunderLevel(partialTick);
        if (f9 > 0.0F) {
            float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.48F; // Modified darkening.
            float f8 = 1.0F - f9 * 0.21F;
            f2 = f2 * f8 + f10 * (1.0F - f8);
            f3 = f3 * f8 + f10 * (1.0F - f8);
            f4 = f4 * f8 + f10 * (1.0F - f8);
        }

        if (!Minecraft.getInstance().options.hideLightningFlash().get() && level.getSkyFlashTime() > 0) {
            float f11 = (float) level.getSkyFlashTime() - partialTick;
            if (f11 > 1.0F) {
                f11 = 1.0F;
            }

            f11 *= 0.45F;
            f2 = f2 * (1.0F - f11) + 0.8F * f11;
            f3 = f3 * (1.0F - f11) + 0.8F * f11;
            f4 = f4 * (1.0F - f11) + f11;
        }

        return new Vec3(f2, f3, f4);
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        float f = level.getRainLevel(partialTick);
        if (!(f <= 0.0F)) {
            lightTexture.turnOnLightLayer();
            int i = Mth.floor(camX);
            int j = Mth.floor(camY);
            int k = Mth.floor(camZ);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Minecraft.useFancyGraphics()) {
                l = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            int i1 = -1;
            float f1 = (float) levelRenderer.getTicks() + partialTick;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int j1 = k - l; j1 <= k + l; ++j1) {
                for (int k1 = i - l; k1 <= i + l; ++k1) {
                    int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
                    double d0 = (double) ((LevelRendererAccessor) levelRenderer).aether_ii$getRainSizeX()[l1] * 0.25;
                    double d1 = (double) ((LevelRendererAccessor) levelRenderer).aether_ii$getRainSizeZ()[l1] * 0.25;
                    blockpos$mutableblockpos.set(k1, camY, j1);
                    Biome biome = level.getBiome(blockpos$mutableblockpos).value();
                    if (biome.hasPrecipitation()) {
                        int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
                        int j2 = j - l;
                        int k2 = j + l;
                        if (j2 < i2) {
                            j2 = i2;
                        }

                        if (k2 < i2) {
                            k2 = i2;
                        }

                        int l2 = i2;
                        if (i2 < j) {
                            l2 = j;
                        }

                        if (j2 != k2) {
                            RandomSource randomsource = RandomSource.create((long) k1 * k1 * 3121 + k1 * 45238971L ^ (long) j1 * j1 * 418711 + j1 * 13761L);
                            blockpos$mutableblockpos.set(k1, j2, j1);
                            Biome.Precipitation biome$precipitation = biome.getPrecipitationAt(blockpos$mutableblockpos);
                            if (biome$precipitation == Biome.Precipitation.RAIN) {
                                if (i1 != 0) {
                                    if (i1 >= 0) {
                                        tesselator.end();
                                    }

                                    i1 = 0;
                                    RenderSystem.setShaderTexture(0, RAIN_LOCATION);
                                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                }

                                int i3 = levelRenderer.getTicks() & 131071;
                                int j3 = k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 0xFF;
                                float f2 = 3.0F + randomsource.nextFloat();
                                float f3 = -((float) (i3 + j3) + partialTick) / 32.0F * f2;
                                float f4 = f3 % 32.0F;
                                double d2 = (double) k1 + 0.5 - camX;
                                double d3 = (double) j1 + 0.5 - camZ;
                                float f6 = (float) Math.sqrt(d2 * d2 + d3 * d3) / (float) l;
                                float f7 = ((1.0F - f6 * f6) * 0.5F + 0.5F) * f * 0.5F;
                                blockpos$mutableblockpos.set(k1, l2, j1);
                                int k3 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
                                bufferBuilder.vertex((double) k1 - camX - d0 + 0.5, (double) k2 - camY, (double) j1 - camZ - d1 + 0.5)
                                        .uv(0.0F, (float) j2 * 0.75F + f4)
                                        .color(1.0F, 1.0F, 1.0F, f7)
                                        .uv2(k3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX + d0 + 0.5, (double) k2 - camY, (double) j1 - camZ + d1 + 0.5)
                                        .uv(1.0F, (float) j2 * 0.75F + f4)
                                        .color(1.0F, 1.0F, 1.0F, f7)
                                        .uv2(k3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX + d0 + 0.5, (double) j2 - camY, (double) j1 - camZ + d1 + 0.5)
                                        .uv(1.0F, (float) k2 * 0.75F + f4)
                                        .color(1.0F, 1.0F, 1.0F, f7)
                                        .uv2(k3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX - d0 + 0.5, (double) j2 - camY, (double) j1 - camZ - d1 + 0.5)
                                        .uv(0.0F, (float) k2 * 0.75F + f4)
                                        .color(1.0F, 1.0F, 1.0F, f7)
                                        .uv2(k3)
                                        .endVertex();
                            } else if (biome$precipitation == Biome.Precipitation.SNOW) {
                                if (i1 != 1) {
                                    if (i1 >= 0) {
                                        tesselator.end();
                                    }

                                    i1 = 1;
                                    RenderSystem.setShaderTexture(0, SNOW_LOCATION);
                                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                }

                                float f8 = -((float) (levelRenderer.getTicks() & 511) + partialTick) / 512.0F;
                                float f9 = (float) (randomsource.nextDouble() + (double) f1 * 0.01 * (double) ((float) randomsource.nextGaussian()));
                                float f10 = (float) (randomsource.nextDouble() + (double) (f1 * (float) randomsource.nextGaussian()) * 0.001);
                                double d4 = (double) k1 + 0.5 - camX;
                                double d5 = (double) j1 + 0.5 - camZ;
                                float f11 = (float) Math.sqrt(d4 * d4 + d5 * d5) / (float)l;
                                float f5 = ((1.0F - f11 * f11) * 0.3F + 0.5F) * f;
                                blockpos$mutableblockpos.set(k1, l2, j1);
                                int j4 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
                                int k4 = j4 >> 16 & 65535;
                                int l4 = j4 & 65535;
                                int l3 = (k4 * 3 + 240) / 4;
                                int i4 = (l4 * 3 + 240) / 4;
                                bufferBuilder.vertex((double) k1 - camX - d0 + 0.5, (double) k2 - camY, (double) j1 - camZ - d1 + 0.5)
                                        .uv(0.0F + f9, (float) j2 * 0.25F + f8 + f10)
                                        .color(1.0F, 1.0F, 1.0F, f5)
                                        .uv2(i4, l3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX + d0 + 0.5, (double) k2 - camY, (double) j1 - camZ + d1 + 0.5)
                                        .uv(1.0F + f9, (float) j2 * 0.25F + f8 + f10)
                                        .color(1.0F, 1.0F, 1.0F, f5)
                                        .uv2(i4, l3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX + d0 + 0.5, (double) j2 - camY, (double) j1 - camZ + d1 + 0.5)
                                        .uv(1.0F + f9, (float)k2 * 0.25F + f8 + f10)
                                        .color(1.0F, 1.0F, 1.0F, f5)
                                        .uv2(i4, l3)
                                        .endVertex();
                                bufferBuilder.vertex((double) k1 - camX - d0 + 0.5, (double) j2 - camY, (double) j1 - camZ - d1 + 0.5)
                                        .uv(0.0F + f9, (float)k2 * 0.25F + f8 + f10)
                                        .color(1.0F, 1.0F, 1.0F, f5)
                                        .uv2(i4, l3)
                                        .endVertex();
                            }
                        }
                    }
                }
            }

            if (i1 >= 0) {
                tesselator.end();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            lightTexture.turnOffLightLayer();
        }
        
        return true;
    }
}

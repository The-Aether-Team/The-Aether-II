package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.WeatherEffectRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.awt.*;

public class HighlandsSpecialEffects extends DimensionSpecialEffects {
    private final DimensionSpecialEffects OVERWORLD = new OverworldEffects();

    private static final ResourceLocation RAIN_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain.png");
    private static final ResourceLocation RAIN_STORMY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain_stormy.png");
    private static final ResourceLocation SNOW_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow.png");
    private static final ResourceLocation SNOW_STORMY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow_stormy.png");

    public HighlandsSpecialEffects() {
        super(256.0F, true, SkyType.OVERWORLD, false, false);
    }

    @Override
    public int getSunriseOrSunsetColor(float timeOfDay) {
        float f1 = Mth.cos(timeOfDay * Mth.TWO_PI) - 0.0F;
        float f3 = (f1 + 0.0F) / 0.4F * 0.5F + 0.5F;
        float f4 = 1.0F - (1.0F - Mth.sin(f3 * Mth.PI)) * 0.99F;
        return ARGB.colorFromFloat(f4 * f4, f3 * 0.3F + 0.65F, f3 * f3 * 0.7F + 0.25F, f3 * f3 * 0.0F + 0.4F);
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
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
        Minecraft.getInstance().levelRenderer.getCloudRenderer().render(this.getCloudColor(level, partialTick), Minecraft.getInstance().options.getCloudsType(), level.effects().getCloudHeight() + 0.33F, modelViewMatrix, projectionMatrix, new Vec3(camX, camY, camZ), partialTick);
        return true;
    }

    /**
     * [CODE COPY] - {@link ClientLevel#getCloudColor(float)}.<br><br>
     * Modified to have lighter cloud coloration than the Overworld during weather.
     */
    public int getCloudColor(ClientLevel level, float partialTick) {
        int i = -1;
        float f = level.getRainLevel(partialTick);
        if (f > 0.0F) {
            int j = ARGB.scaleRGB(ARGB.greyscale(i), 0.6F);
            i = ARGB.lerp(f * 0.5F, i, j); //reduced darkening
        }

        float f3 = level.getTimeOfDay(partialTick);
        float f1 = Mth.cos(f3 * 6.2831855F) * 2.0F + 0.5F;
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        i = ARGB.multiply(i, ARGB.colorFromFloat(1.0F, f1 * 0.9F + 0.1F, f1 * 0.9F + 0.1F, f1 * 0.85F + 0.15F));
        float f2 = level.getThunderLevel(partialTick);
        if (f2 > 0.0F) {
            int k = ARGB.scaleRGB(ARGB.greyscale(i), 0.2F);
            i = ARGB.lerp(f2 * 0.5F, i, k); //reduced darkening
        }

        return i;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, Runnable setupFog) {
        RenderBuffers renderBuffers = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getRenderBuffers();
        SkyRenderer skyRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getSkyRenderer();
        float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        Vec3 cameraPosition = camera.getPosition();
        double cameraX = cameraPosition.x();
        double cameraY = cameraPosition.y();
        boolean isFoggy = Minecraft.getInstance().level.effects().isFoggyAt(Mth.floor(cameraX), Mth.floor(cameraY)) || Minecraft.getInstance().gui.getBossOverlay().shouldCreateWorldFog();
        Vector4f fogColor = FogRenderer.computeFogColor(camera, partialTick, Minecraft.getInstance().level, Minecraft.getInstance().options.getEffectiveRenderDistance(), Minecraft.getInstance().gameRenderer.getDarkenWorldAmount(partialTick));
        FogParameters fogParameters = FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, fogColor, renderDistance, isFoggy, partialTick);

        setupFog.run();
        PoseStack poseStack = new PoseStack();
        float sunAngle = level.getSunAngle(partialTick);
        float timeOfDay = level.getTimeOfDay(partialTick);
        float rainLevel = 1.0F - level.getRainLevel(partialTick);
        float starBrightness = level.getStarBrightness(partialTick) * rainLevel;
        int sunColor = this.getSunriseOrSunsetColor(timeOfDay);
        int moonPhase = level.getMoonPhase();
        int skyColor = this.getSkyColor(level, camera.getPosition(), partialTick);
        float r = ARGB.redFloat(skyColor);
        float g = ARGB.greenFloat(skyColor);
        float b = ARGB.blueFloat(skyColor);
        skyRenderer.renderSkyDisc(r, g, b);
        MultiBufferSource.BufferSource multiBufferSource = renderBuffers.bufferSource();
        if (this.isSunriseOrSunset(timeOfDay)) {
            skyRenderer.renderSunriseAndSunset(poseStack, multiBufferSource, sunAngle, sunColor);
        }
        this.renderCloudCoverDisc(level, partialTick, poseStack, multiBufferSource, skyColor);
        skyRenderer.renderSunMoonAndStars(poseStack, multiBufferSource, timeOfDay, moonPhase, rainLevel, starBrightness, fogParameters);
        multiBufferSource.endBatch();
        return true;
    }

    public void renderCloudCoverDisc(ClientLevel level, float partialTick, PoseStack poseStack, MultiBufferSource.BufferSource multiBufferSource, int skyColor) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
        Matrix4f matrix4f = poseStack.last().pose();

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(AetherIIRenderTypes.cloudCover());

        float r = ARGB.redFloat(skyColor);
        float g = ARGB.greenFloat(skyColor);
        float b = ARGB.blueFloat(skyColor);
        Color color = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255)).brighter();
        float weatherMultiplier = Math.max(1.0F - (((level.getRainLevel(partialTick) + level.getThunderLevel(partialTick)) * 0.5F) * 0.275F), 0.175F);
        float bluePower =  Math.min(0.5F / weatherMultiplier, 0.85F);
        r = (Math.min(color.getRed() + 20, 255.0F) / 255.0F) * weatherMultiplier;
        g = (Math.min(color.getGreen() + 20, 255.0F) / 255.0F) * weatherMultiplier;
        b = (Math.min(color.getBlue() + 35, 255.0F) / 255.0F) * (float) Math.pow(weatherMultiplier, bluePower);

        vertexconsumer.addVertex(matrix4f, 0.0F, -16.0F, 0.0F).setColor(ARGB.colorFromFloat(1.0F, r, g, b));
        for (int i = -180; i <= 180; i += 9) {
            vertexconsumer.addVertex(matrix4f, Math.signum(-16.0F) * 512.0F * Mth.cos((float) i * (float) (Math.PI / 180.0)), -16.0F, 512.0F * Mth.sin((float) i * (float) (Math.PI / 180.0))).setColor(ARGB.colorFromFloat(0.0F, r, g, b));
        }

        poseStack.popPose();
    }

    /**
     * [CODE COPY] - {@link ClientLevel#getSkyColor(Vec3, float)}.<br><br>
     * Modified to have lighter sky coloration than the Overworld during weather.
     */
    public int getSkyColor(ClientLevel level, Vec3 pos, float partialTick) {
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

        return ARGB.colorFromFloat(1.0F, f2, f3, f4);
    }
//
//    @Override
//    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
//        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
//        float rain = level.getRainLevel(partialTick);
//        float thunder = level.getThunderLevel(partialTick);
//        boolean isThundering = (!(thunder <= 0.0F));
//
//        if (!(rain <= 0.0F)) {
//            lightTexture.turnOnLightLayer();
//            int i = Mth.floor(camX);
//            int j = Mth.floor(camY);
//            int k = Mth.floor(camZ);
//            Tesselator tesselator = Tesselator.getInstance();
//            BufferBuilder bufferBuilder = null;
//            RenderSystem.disableCull();
//            RenderSystem.enableBlend();
//            RenderSystem.enableDepthTest();
//            int l = 3;
//            if (Minecraft.useFancyGraphics()) {
//                l = 6;
//            }
//            if (isThundering) {
//                 l = (int) (l * 1.25F);
//            }
//
//            RenderSystem.depthMask(Minecraft.useShaderTransparency());
//            int i1 = -1;
//            float f1 = (float) levelRenderer.getTicks() + partialTick;
//            RenderSystem.setShader(GameRenderer::getParticleShader);
//            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//
//            for (int j1 = k - l; j1 <= k + l; ++j1) {
//                for (int k1 = i - l; k1 <= i + l; ++k1) {
//                    int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
//                    double d0 = (double) ((LevelRendererAccessor) levelRenderer).aether_ii$getRainSizeX()[l1] * 0.5;
//                    double d1 = (double) ((LevelRendererAccessor) levelRenderer).aether_ii$getRainSizeZ()[l1] * 0.5;
//                    blockpos$mutableblockpos.set(k1, camY, j1);
//                    Biome biome = level.getBiome(blockpos$mutableblockpos).value();
//                    if (biome.hasPrecipitation()) {
//                        int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
//                        int j2 = j - l;
//                        int k2 = j + l;
//                        if (j2 < i2) {
//                            j2 = i2;
//                        }
//
//                        if (k2 < i2) {
//                            k2 = i2;
//                        }
//
//                        int l2 = Math.max(i2, j);
//
//                        if (j2 != k2) {
//                            RandomSource randomsource = RandomSource.create((long) k1 * k1 * 3121 + k1 * 45238971L ^ (long) j1 * j1 * 418711 + j1 * 13761L);
//                            blockpos$mutableblockpos.set(k1, j2, j1);
//                            Biome.Precipitation biome$precipitation = biome.getPrecipitationAt(blockpos$mutableblockpos, level.getSeaLevel());
//
//                            if (biome$precipitation == Biome.Precipitation.RAIN) {
//                                if (i1 != 0) {
//                                    if (i1 >= 0) {
//                                        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
//                                    }
//                                    bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
//                                }
//                                i1 = this.renderRain(RAIN_LOCATION, bufferBuilder, levelRenderer, level, randomsource, partialTick, camX, camY, camZ, 1.0F, 0.85F, 0.75F, d0, d1, i1, j1, k1, blockpos$mutableblockpos, l, l2, k2, j2, rain);
//                                i1 = this.renderRain(RAIN_STORMY_LOCATION, bufferBuilder, levelRenderer, level, randomsource, partialTick, camX, camY, camZ, 1.0F, 0.85F, 0.75F, d0, d1, i1, j1, k1, blockpos$mutableblockpos, l, l2, k2, j2, thunder);
//                            } else if (biome$precipitation == Biome.Precipitation.SNOW) {
//                                if (i1 != 1) {
//                                    if (i1 == 0) {
//                                        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
//                                    }
//                                    bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
//                                }
//                                i1 = this.renderSnow(SNOW_LOCATION, bufferBuilder, levelRenderer, level, randomsource, partialTick, camX, camY, camZ, 0.75F, 0.25F, d0, d1, f1, i1, j1, k1, blockpos$mutableblockpos, l, l2, k2, j2, rain);
//                                i1 = this.renderSnow(SNOW_STORMY_LOCATION, bufferBuilder, levelRenderer, level, randomsource, partialTick, camX, camY, camZ, 0.75F, 0.25F, d0, d1, f1, i1, j1, k1, blockpos$mutableblockpos, l, l2, k2, j2, thunder);
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (i1 >= 0) {
//                BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
//            }
//
//            RenderSystem.enableCull();
//            RenderSystem.disableBlend();
//            lightTexture.turnOffLightLayer();
//        }
//
//        return true;
//    }
//
//    private int renderRain(ResourceLocation location, BufferBuilder bufferBuilder, LevelRenderer levelRenderer, ClientLevel level, RandomSource randomsource,
//                           float partialTick, double camX, double camY, double camZ, float size, float opacityStrength, float stretchStrength,
//                           double d0, double d1, int i1, int j1, int k1, BlockPos.MutableBlockPos blockpos$mutableblockpos, int l, int l2, int k2, int j2, float rain) {
//        d0 *= size;
//        d1 *= size;
//
//        if (i1 != 0) {
//            i1 = 0;
//            RenderSystem.setShaderTexture(0, location);
//        }
//
//        int i3 = levelRenderer.getTicks() & 131071;
//        int j3 = k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 0xFF;
//        float f2 = 3.0F + randomsource.nextFloat();
//        float f3 = -((float) (i3 + j3) + partialTick) / 32.0F * f2;
//        float f4 = f3 % 32.0F;
//        double d2 = (double) k1 + 0.5 - camX;
//        double d3 = (double) j1 + 0.5 - camZ;
//        float f6 = (float) Math.sqrt(d2 * d2 + d3 * d3) / (float) l;
//        float f7 = ((1.0F - f6 * f6) * 0.5F + 0.5F) * rain * opacityStrength;
//        blockpos$mutableblockpos.set(k1, l2, j1);
//        int k3 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
//        bufferBuilder.addVertex((float) ((double) k1 - camX - d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ - d1 + 0.5))
//                .setUv(0.0F, (float) j2 * stretchStrength + f4)
//                .setColor(1.0F, 1.0F, 1.0F, f7)
//                .setLight(k3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX + d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ + d1 + 0.5))
//                .setUv(1.0F, (float) j2 * stretchStrength + f4)
//                .setColor(1.0F, 1.0F, 1.0F, f7)
//                .setLight(k3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX + d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ + d1 + 0.5))
//                .setUv(1.0F, (float) k2 * stretchStrength + f4)
//                .setColor(1.0F, 1.0F, 1.0F, f7)
//                .setLight(k3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX - d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ - d1 + 0.5))
//                .setUv(0.0F, (float) k2 * stretchStrength + f4)
//                .setColor(1.0F, 1.0F, 1.0F, f7)
//                .setLight(k3)
//                ;
//
//        return i1;
//    }
//
//    private int renderSnow(ResourceLocation location, BufferBuilder bufferBuilder, LevelRenderer levelRenderer, ClientLevel level, RandomSource randomsource,
//                           float partialTick, double camX, double camY, double camZ, float opacityStrength, float stretchStrength,
//                           double d0, double d1, float f1, int i1, int j1, int k1, BlockPos.MutableBlockPos blockpos$mutableblockpos, int l, int l2, int k2, int j2, float rain) {
//        if (i1 != 1) {
//            i1 = 1;
//            RenderSystem.setShaderTexture(0, location);
//        }
//
//        float f8 = -((float) (levelRenderer.getTicks() & 511) + partialTick) / 256.0F;
//        float f9 = (float) (randomsource.nextDouble() + (double) f1 * 0.01 * (double) ((float) randomsource.nextGaussian()));
//        float f10 = (float) (randomsource.nextDouble() + (double) (f1 * (float) randomsource.nextGaussian()) * 0.001);
//        double d4 = (double) k1 + 0.5 - camX;
//        double d5 = (double) j1 + 0.5 - camZ;
//        float f11 = (float) Math.sqrt(d4 * d4 + d5 * d5) / (float)l;
//        float f5 = ((1.0F - f11 * f11) * 0.3F + 0.5F) * rain * opacityStrength;
//        blockpos$mutableblockpos.set(k1, l2, j1);
//        int j4 = LevelRenderer.getLightColor(level, blockpos$mutableblockpos);
//        int k4 = j4 >> 16 & 65535;
//        int l4 = j4 & 65535;
//        int l3 = (k4 * 3 + 240) / 4;
//        int i4 = (l4 * 3 + 240) / 4;
//        bufferBuilder.addVertex((float) ((double) k1 - camX - d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ - d1 + 0.5))
//                .setUv(0.0F + f9, (float) j2 * stretchStrength + f8 + f10)
//                .setColor(1.0F, 1.0F, 1.0F, f5)
//                .setUv2(i4, l3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX + d0 + 0.5), (float) ((double) k2 - camY), (float) ((double) j1 - camZ + d1 + 0.5))
//                .setUv(1.0F + f9, (float) j2 * stretchStrength + f8 + f10)
//                .setColor(1.0F, 1.0F, 1.0F, f5)
//                .setUv2(i4, l3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX + d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ + d1 + 0.5))
//                .setUv(1.0F + f9, (float) k2 * stretchStrength + f8 + f10)
//                .setColor(1.0F, 1.0F, 1.0F, f5)
//                .setUv2(i4, l3)
//                ;
//        bufferBuilder.addVertex((float) ((double) k1 - camX - d0 + 0.5), (float) ((double) j2 - camY), (float) ((double) j1 - camZ - d1 + 0.5))
//                .setUv(0.0F + f9, (float) k2 * stretchStrength + f8 + f10)
//                .setColor(1.0F, 1.0F, 1.0F, f5)
//                .setUv2(i4, l3)
//                ;
//
//        return i1;
//    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        float f = level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.5F : 3.0F);
        float thunder = level.getThunderLevel(1.0F);
        boolean isThundering = (!(thunder <= 0.0F));
        if (isThundering) {
            f = level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 0.75F : 1.5F);
        }

        if (!(f <= 0.0F)) {
            RandomSource randomsource = RandomSource.create((long) ticks * 312987231L);
            BlockPos blockpos = BlockPos.containing(camera.getPosition());
            BlockPos blockpos1 = null;
            int i = (int) (100.0F * f * f) / (Minecraft.getInstance().options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

            for (int j = 0; j < i; ++j) {
                int k = randomsource.nextInt(21) - 10;
                int l = randomsource.nextInt(21) - 10;
                BlockPos blockpos2 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos.offset(k, 0, l));
                if (blockpos2.getY() > level.getMinY() && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10) {
                    Biome biome = level.getBiome(blockpos2).value();
                    if (biome.getPrecipitationAt(blockpos2, level.getSeaLevel()) == Biome.Precipitation.RAIN) {
                        blockpos1 = blockpos2.below();
                        if (Minecraft.getInstance().options.particles().get() == ParticleStatus.MINIMAL) {
                            break;
                        }

                        double d0 = randomsource.nextDouble();
                        double d1 = randomsource.nextDouble();
                        BlockState blockstate = level.getBlockState(blockpos1);
                        FluidState fluidstate = level.getFluidState(blockpos1);
                        VoxelShape voxelshape = blockstate.getCollisionShape(level, blockpos1);
                        double d2 = voxelshape.max(Direction.Axis.Y, d0, d1);
                        double d3 = fluidstate.getHeight(level, blockpos1);
                        double d4 = Math.max(d2, d3);
                        ParticleOptions particleoptions = !fluidstate.is(FluidTags.LAVA) && !blockstate.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockstate) ? AetherIIParticleTypes.RAIN.get() : ParticleTypes.SMOKE;
                        level.addParticle(particleoptions, (double) blockpos1.getX() + d0, (double) blockpos1.getY() + d4, (double) blockpos1.getZ() + d1, 0.0, 0.0, 0.0);
                    }
                }
            }

            if (blockpos1 != null) {
                WeatherEffectRenderer weatherEffectRenderer = ((LevelRendererAccessor) levelRenderer).aether_ii$getWeatherEffectRenderer();
                ((WeatherEffectRendererAccessor) weatherEffectRenderer).aether_ii$setRainSoundTime(((WeatherEffectRendererAccessor) weatherEffectRenderer).aether_ii$getRainSoundTime() + 1);
                if (randomsource.nextInt(3) < ((WeatherEffectRendererAccessor) weatherEffectRenderer).aether_ii$getRainSoundTime()) {
                    ((WeatherEffectRendererAccessor) weatherEffectRenderer).aether_ii$setRainSoundTime(0);
                    if (blockpos1.getY() > blockpos.getY() + 1
                            && level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() > Mth.floor((float) blockpos.getY())) {
                        level.playLocalSound(blockpos1, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                    } else {
                        level.playLocalSound(blockpos1, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                    }
                }
            }
        }
        return true;
    }
}

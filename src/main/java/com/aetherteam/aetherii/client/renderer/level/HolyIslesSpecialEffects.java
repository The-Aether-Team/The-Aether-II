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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HolyIslesSpecialEffects extends DimensionSpecialEffects {
    private final DimensionSpecialEffects OVERWORLD = new OverworldEffects();

    private static final ResourceLocation RAIN_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain.png");
    private static final ResourceLocation RAIN_STORMY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain_stormy.png");
    private static final ResourceLocation SNOW_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow.png");
    private static final ResourceLocation SNOW_STORMY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow_stormy.png");

    public HolyIslesSpecialEffects() {
        super(SkyType.OVERWORLD, false, false);
    }

    @Override
    public boolean isSunriseOrSunset(float timeOfDay) {
        float f = Mth.cos(timeOfDay * Mth.TWO_PI);
        return f >= -0.4F && f <= 0.4F;
    }

    @Override
    public int getSunriseOrSunsetColor(float timeOfDay) {
        float f = Mth.cos(timeOfDay * Mth.TWO_PI);
        float f1 = f / 0.4F * 0.5F + 0.5F;
        float f2 = Mth.square(1.0F - (1.0F - Mth.sin(f1 * Mth.PI)) * 0.99F);
        return ARGB.colorFromFloat(f2, f1 * 0.3F + 0.65F, f1 * f1 * 0.7F + 0.25F, 0.4F);
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
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ, Matrix4f modelViewMatrix) {
        if (level.dimensionType().cloudHeight().isPresent()) {
            Minecraft.getInstance().levelRenderer.getCloudRenderer().render(this.getCloudColor(level, partialTick), Minecraft.getInstance().options.getCloudsType(), level.dimensionType().cloudHeight().get() + 0.33F, new Vec3(camX, camY, camZ), partialTick);
        }
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
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Runnable setupFog) {
        RenderBuffers renderBuffers = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getRenderBuffers();
        SkyRenderer skyRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).aether_ii$getSkyRenderer();
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
        skyRenderer.renderSunMoonAndStars(poseStack, multiBufferSource, timeOfDay, moonPhase, rainLevel, starBrightness);
        this.renderCloudCoverDisc(level, partialTick, poseStack, multiBufferSource, timeOfDay, skyColor, sunColor);
        multiBufferSource.endBatch();
        return true;
    }


    public void renderCloudCoverDisc(ClientLevel level, float partialTick, PoseStack poseStack, MultiBufferSource.BufferSource multiBufferSource, float timeOfDay, int skyColor, int sunColor) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
        Matrix4f matrix4f = poseStack.last().pose();

        VertexConsumer cloudCoverBuffer = multiBufferSource.getBuffer(AetherIIRenderTypes.cloudCover());

        float r = ARGB.redFloat(skyColor);
        float g = ARGB.greenFloat(skyColor);
        float b = ARGB.blueFloat(skyColor);
        Color color = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255)).brighter();
        float weatherMultiplier = Math.max(1.0F - (((level.getRainLevel(partialTick) + level.getThunderLevel(partialTick)) * 0.5F) * 0.275F), 0.175F);
        float bluePower =  Math.min(0.5F / weatherMultiplier, 0.85F);
        r = (Math.min(color.getRed() + 20, 255.0F) / 255.0F) * weatherMultiplier;
        g = (Math.min(color.getGreen() + 20, 255.0F) / 255.0F) * weatherMultiplier;
        b = (Math.min(color.getBlue() + 35, 255.0F) / 255.0F) * (float) Math.pow(weatherMultiplier, bluePower);

        if (this.isSunriseOrSunset(timeOfDay)) {
            float cosTime = Mth.cos(timeOfDay * Mth.TWO_PI);
            float alpha;
            if (cosTime > 0) {
                alpha = Math.clamp(20.0F * (float) Math.pow(0.4F - Mth.abs(cosTime), 2.5F), 0.0F, 0.6F);
            } else {
                alpha = (1.5F * (float) Math.pow(0.4F - Mth.abs(cosTime), 1.0F));
            }
            r = Mth.clamp(((ARGB.redFloat(sunColor)) * alpha + r * (1.0F - alpha)), 0.0F, 1.0F);
            g = Mth.clamp(((ARGB.greenFloat(sunColor)) * alpha + g * (1.0F - alpha)), 0.0F, 1.0F);
            b = Mth.clamp(((ARGB.blueFloat(sunColor)) * alpha + b * (1.0F - alpha)), 0.0F, 1.0F);
        }

        double cameraHeight = (Minecraft.getInstance().player.getEyePosition(partialTick).y - 66) * 0.03125F;
        if (cameraHeight < 1.0) {
            if (cameraHeight < 0.0) {
                cameraHeight = 0.0;
            }
            cameraHeight *= cameraHeight;
            r *= (float) Math.clamp(cameraHeight, 0.15F, 1.0F);
            g *= (float) Math.clamp(cameraHeight, 0.15F, 1.0F);
            b *= (float) Math.clamp(cameraHeight * 1.25F, 0.15F * 1.25F, 1.0F);
        }

        cloudCoverBuffer.addVertex(matrix4f, 0.0F, -16.0F, 0.0F).setColor(ARGB.colorFromFloat(1.0F, r, g, b));
        for (int i = -180; i <= 180; i += 9) {
            cloudCoverBuffer.addVertex(matrix4f, Math.signum(-16.0F) * 512.0F * Mth.cos((float) i * (float) (Math.PI / 180.0)), -16.0F, 512.0F * Mth.sin((float) i * (float) (Math.PI / 180.0))).setColor(ARGB.colorFromFloat(0.0F, r, g, b));
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

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, double camX, double camY, double camZ) {
        LevelRendererAccessor levelRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer);
        WeatherEffectRendererAccessor weatherEffectRenderer = ((WeatherEffectRendererAccessor) levelRenderer.aether_ii$getWeatherEffectRenderer());
        MultiBufferSource.BufferSource bufferSource = levelRenderer.aether_ii$getRenderBuffers().bufferSource();
        Vec3 cameraPosition = new Vec3(camX, camY, camZ);
        float rain = level.getRainLevel(partialTick);
        float thunder = level.getThunderLevel(partialTick);

        if (!(rain <= 0.0F)) {
            int i = Minecraft.useFancyGraphics() ? 10 : 5;
            java.util.List<WeatherEffectRenderer.ColumnInstance> list = new ArrayList<>();
            List<WeatherEffectRenderer.ColumnInstance> list1 = new ArrayList<>();
            weatherEffectRenderer.callCollectColumnInstances(level, ticks, partialTick, cameraPosition, i, list, list1);
            if (!list.isEmpty() || !list1.isEmpty()) {
                this.renderWeather(weatherEffectRenderer, bufferSource, cameraPosition, i, rain, thunder, list, list1);
            }
        }
        return true;
    }

    private void renderWeather(WeatherEffectRendererAccessor weatherEffectRenderer, MultiBufferSource bufferSource, Vec3 cameraPosition, int radius, float rainLevel, float thunderLevel, List<WeatherEffectRenderer.ColumnInstance> rainColumnInstances, List<WeatherEffectRenderer.ColumnInstance> snowColumnInstances) {
        boolean isThundering = thunderLevel > 0.0F;
        if (!rainColumnInstances.isEmpty()) {
            RenderType rainType = RenderType.weather(isThundering ? RAIN_STORMY_LOCATION : RAIN_LOCATION, Minecraft.useShaderTransparency());
            weatherEffectRenderer.callRenderInstances(bufferSource.getBuffer(rainType), rainColumnInstances, cameraPosition, 0.75F, radius, rainLevel);
        }
        if (!snowColumnInstances.isEmpty()) {
            RenderType snowType = RenderType.weather(isThundering ? SNOW_STORMY_LOCATION : SNOW_LOCATION, Minecraft.useShaderTransparency());
            weatherEffectRenderer.callRenderInstances(bufferSource.getBuffer(snowType), snowColumnInstances, cameraPosition, 0.8F, radius, rainLevel);
        }
    }

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

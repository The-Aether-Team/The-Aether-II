package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.WeatherEffectRendererAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.CustomWeatherEffectRenderer;

import java.util.List;

public class AetherWeatherEffectRenderer implements CustomWeatherEffectRenderer {

    private static final Identifier RAIN_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain.png");
    private static final Identifier RAIN_STORMY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain_stormy.png");
    private static final Identifier SNOW_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow.png");
    private static final Identifier SNOW_STORMY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow_stormy.png");

    @Override
    public boolean renderSnowAndRain(LevelRenderState levelRenderState, WeatherRenderState weatherRenderState, MultiBufferSource bufferSource, Vec3 camPos) {
        LevelRendererAccessor levelRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer);
        WeatherEffectRendererAccessor weatherEffectRenderer = ((WeatherEffectRendererAccessor) levelRenderer.aether_ii$getWeatherEffectRenderer());
        Vec3 cameraPosition = camPos;
        float rain = weatherRenderState.intensity;
        float thunder = levelRenderState.getRenderDataOrDefault(AetherIIDimensionRenderers.DATA_THUNDER_KEY, 0.0F);

        if (!(rain <= 0.0F)) {
            int i = Minecraft.useShaderTransparency() ? 10 : 5;
            this.renderWeather(levelRenderState, weatherRenderState, weatherEffectRenderer, bufferSource, cameraPosition, i, rain, thunder, weatherRenderState.rainColumns, weatherRenderState.snowColumns);
        }
        return true;
    }

    private void renderWeather(LevelRenderState levelRenderState, WeatherRenderState weatherRenderState, WeatherEffectRendererAccessor weatherEffectRenderer, MultiBufferSource bufferSource, Vec3 cameraPosition, int radius, float rainLevel, float thunderLevel, List<WeatherEffectRenderer.ColumnInstance> rainColumnInstances, List<WeatherEffectRenderer.ColumnInstance> snowColumnInstances) {
        boolean isThundering = thunderLevel > 0.0F;
        if (!rainColumnInstances.isEmpty()) {
            RenderType rainType = RenderTypes.weather(isThundering ? RAIN_STORMY_LOCATION : RAIN_LOCATION, Minecraft.useShaderTransparency());
            weatherEffectRenderer.callRenderInstances(bufferSource.getBuffer(rainType), rainColumnInstances, cameraPosition, 0.75F, radius, rainLevel);
        }
        if (!snowColumnInstances.isEmpty()) {
            RenderType snowType = RenderTypes.weather(isThundering ? SNOW_STORMY_LOCATION : SNOW_LOCATION, Minecraft.useShaderTransparency());
            weatherEffectRenderer.callRenderInstances(bufferSource.getBuffer(snowType), snowColumnInstances, cameraPosition, 0.8F, radius, rainLevel);
        }
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        float f = level.getRainLevel(1.0F) / (Minecraft.useShaderTransparency() ? 1.5F : 3.0F);
        float thunder = level.getThunderLevel(1.0F);
        boolean isThundering = (!(thunder <= 0.0F));
        if (isThundering) {
            f = level.getRainLevel(1.0F) / (Minecraft.useShaderTransparency() ? 0.75F : 1.5F);
        }

        if (!(f <= 0.0F)) {
            RandomSource randomsource = RandomSource.create((long) ticks * 312987231L);
            BlockPos blockpos = BlockPos.containing(camera.position());
            BlockPos blockpos1 = null;
            int i = (int) (100.0F * f * f) / (Minecraft.getInstance().options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

            for (int j = 0; j < i; ++j) {
                int k = randomsource.nextInt(21) - 10;
                int l = randomsource.nextInt(21) - 10;
                BlockPos blockpos2 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos.offset(k, 0, l));
                if (blockpos2.getY() > level.getMinY() && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10) {
                    Biome biome = level.getBiome(blockpos2).value();
                    if (getPrecipitationAt(level, blockpos2) == Biome.Precipitation.RAIN) {
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

    private Biome.Precipitation getPrecipitationAt(Level p_362885_, BlockPos p_362817_) {
        if (!p_362885_.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(p_362817_.getX()), SectionPos.blockToSectionCoord(p_362817_.getZ()))) {
            return Biome.Precipitation.NONE;
        } else {
            Biome biome = p_362885_.getBiome(p_362817_).value();
            return biome.getPrecipitationAt(p_362817_, p_362885_.getSeaLevel());
        }
    }

}

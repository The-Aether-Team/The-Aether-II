package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.WeatherEffectRendererAccessor;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
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
import net.minecraft.world.attribute.WeatherAttributes;
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
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class HolyIslesWeatherEffectRenderer implements CustomWeatherEffectRenderer { //todo rewrite
    private static final Identifier RAIN_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain.png");
    private static final Identifier RAIN_STORMY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/rain_stormy.png");
    private static final Identifier SNOW_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow.png");
    private static final Identifier SNOW_STORMY_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/environment/snow_stormy.png");

    @Override
    public boolean renderSnowAndRain(LevelRenderState levelRenderState, WeatherRenderState weatherRenderState, MultiBufferSource bufferSource, Vec3 cameraPos) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            WeatherAttributes.WeatherAccess weatherAccess = WeatherAttributes.WeatherAccess.from(Minecraft.getInstance().level);

            LevelRendererAccessor levelRenderer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer);
            WeatherEffectRendererAccessor weatherEffectRenderer = ((WeatherEffectRendererAccessor) levelRenderer.aether_ii$getWeatherEffectRenderer());
            float rain = weatherRenderState.intensity;
            float thunder = weatherAccess.thunderLevel();
            boolean isThundering = thunder > 0.0F;

            if (!(rain <= 0.0F)) {
                int i = Minecraft.useShaderTransparency() ? 10 : 5;

                int columnCount = weatherRenderState.rainColumns.size() + weatherRenderState.snowColumns.size();
                if (columnCount != 0) {
                    TextureManager textureManager = Minecraft.getInstance().getTextureManager();
                    AbstractTexture rainTexture = isThundering ? textureManager.getTexture(RAIN_STORMY_LOCATION) : textureManager.getTexture(RAIN_LOCATION);
                    AbstractTexture snowTexture = isThundering ? textureManager.getTexture(SNOW_STORMY_LOCATION) : textureManager.getTexture(SNOW_LOCATION);
                    RenderTarget weatherRenderTarget = OutputTarget.WEATHER_TARGET.getRenderTarget();
                    GpuTextureView colorTexture = weatherRenderTarget.getColorTextureView();
                    GpuTextureView depthTexture = weatherRenderTarget.getDepthTextureView();
                    RenderPipeline renderPipeline = Minecraft.useShaderTransparency() ? RenderPipelines.WEATHER_DEPTH_WRITE : RenderPipelines.WEATHER_NO_DEPTH_WRITE;

                    GpuBuffer vertexBuffer;
                    GpuBuffer indexBuffer;
                    VertexFormat.IndexType indexType;
                    try (ByteBufferBuilder builder = ByteBufferBuilder.exactlySized(columnCount * DefaultVertexFormat.PARTICLE.getVertexSize() * 4)) {
                        BufferBuilder bufferBuilder = new BufferBuilder(builder, VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                        weatherEffectRenderer.callRenderInstances(bufferBuilder, weatherRenderState.rainColumns, cameraPos, 1.0F, weatherRenderState.radius, weatherRenderState.intensity);
                        weatherEffectRenderer.callRenderInstances(bufferBuilder, weatherRenderState.snowColumns, cameraPos, 0.8F, weatherRenderState.radius, weatherRenderState.intensity);

                        try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                            vertexBuffer = renderPipeline.getVertexFormat().uploadImmediateVertexBuffer(mesh.vertexBuffer());
                            RenderSystem.AutoStorageIndexBuffer autoIndices = RenderSystem.getSequentialBuffer(mesh.drawState().mode());
                            indexBuffer = autoIndices.getBuffer(mesh.drawState().indexCount());
                            indexType = autoIndices.type();
                        }
                    }

                    GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), new Vector3f(), new Matrix4f());

                    try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder()
                            .createRenderPass(() -> "Weather Effect", colorTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
                        renderPass.setPipeline(renderPipeline);
                        RenderSystem.bindDefaultUniforms(renderPass);
                        renderPass.setUniform("DynamicTransforms", dynamicTransforms);
                        renderPass.bindTexture("Sampler2", Minecraft.getInstance().gameRenderer.lightmap(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
                        renderPass.setIndexBuffer(indexBuffer, indexType);
                        renderPass.setVertexBuffer(0, vertexBuffer);
                        weatherEffectRenderer.callRenderWeather(renderPass, rainTexture, 0, weatherRenderState.rainColumns.size());
                        weatherEffectRenderer.callRenderWeather(renderPass, snowTexture, weatherRenderState.rainColumns.size(), weatherRenderState.snowColumns.size());
                    }
                }
            }
            return true;
        }
        return false;
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

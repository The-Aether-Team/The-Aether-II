package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.shapes.VoxelShape;

public class HolyIslesWeatherEffectRenderer {
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        float rain = level.getRainLevel(1.0F);
        float thunder = level.getThunderLevel(1.0F);
        float particleScale = thunder > 0.0F ? rain / 1.5F : rain / 3.0F;
        if (particleScale <= 0.0F) {
            return true;
        }

        RandomSource random = RandomSource.create((long) ticks * 312987231L);
        BlockPos cameraPos = BlockPos.containing(camera.getPosition());
        BlockPos soundPos = null;
        int particleCount = (int) (100.0F * particleScale * particleScale) / (Minecraft.getInstance().options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

        for (int i = 0; i < particleCount; ++i) {
            int xOffset = random.nextInt(21) - 10;
            int zOffset = random.nextInt(21) - 10;
            BlockPos heightPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, cameraPos.offset(xOffset, 0, zOffset));
            if (heightPos.getY() > level.getMinBuildHeight() && heightPos.getY() <= cameraPos.getY() + 10 && heightPos.getY() >= cameraPos.getY() - 10) {
                if (this.getPrecipitationAt(level, heightPos) == Biome.Precipitation.RAIN) {
                    soundPos = heightPos.below();
                    if (Minecraft.getInstance().options.particles().get() == ParticleStatus.MINIMAL) {
                        break;
                    }

                    double x = random.nextDouble();
                    double z = random.nextDouble();
                    BlockState blockState = level.getBlockState(soundPos);
                    FluidState fluidState = level.getFluidState(soundPos);
                    VoxelShape voxelShape = blockState.getCollisionShape(level, soundPos);
                    double collisionHeight = voxelShape.max(Direction.Axis.Y, x, z);
                    double fluidHeight = fluidState.getHeight(level, soundPos);
                    double y = Math.max(collisionHeight, fluidHeight);
                    ParticleOptions particle = !fluidState.is(FluidTags.LAVA) && !blockState.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockState) ? AetherIIParticleTypes.RAIN.get() : ParticleTypes.SMOKE;
                    level.addParticle(particle, (double) soundPos.getX() + x, (double) soundPos.getY() + y, (double) soundPos.getZ() + z, 0.0, 0.0, 0.0);
                }
            }
        }

        if (soundPos != null && random.nextInt(3) == 0) {
            if (soundPos.getY() > cameraPos.getY() + 1 && level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, cameraPos).getY() > Mth.floor((float) cameraPos.getY())) {
                level.playLocalSound(soundPos, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
            } else {
                level.playLocalSound(soundPos, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
            }
        }
        return true;
    }

    private Biome.Precipitation getPrecipitationAt(Level level, BlockPos pos) {
        if (!level.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()))) {
            return Biome.Precipitation.NONE;
        }
        Biome biome = level.getBiome(pos).value();
        return biome.getPrecipitationAt(pos);
    }
}

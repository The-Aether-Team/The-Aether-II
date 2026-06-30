package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.SentrySpawnerBlock;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Optional;

public class SentrySpawnerBlockEntity extends CustomSpawnerBlockEntity {
    private final SentrySpawner spawner = new SentrySpawner();
    private float pistonScale;
    private float pistonScaleOld;

    public SentrySpawnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), pos, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        blockEntity.getSpawner().clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        if (blockEntity.firstTick) {
            blockEntity.getSpawner().setEntityId(AetherIIEntityTypes.DETONATION_SENTRY.get(), level, level.getRandom(), pos);
            blockEntity.getSpawner().maxSpawnDelay = 250;
            blockEntity.getSpawner().minSpawnDelay = 150;
            blockEntity.firstTick = false;
        }
        blockEntity.getSpawner().serverTick((ServerLevel) level, pos);

        AetherIIBlockStateProperties.SentrySpawnerState spawnerState = state.getValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE);
        if (spawnerState != AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE) {
            if (blockEntity.pistonScaleOld != blockEntity.pistonScale) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
            blockEntity.pistonScaleOld = blockEntity.pistonScale;
            if (spawnerState == AetherIIBlockStateProperties.SentrySpawnerState.OPENING) {
                blockEntity.pistonScale = Mth.clamp(blockEntity.pistonScale + 0.1F, 0.0F, 1.0F);
            } else if (spawnerState == AetherIIBlockStateProperties.SentrySpawnerState.CLOSING) {
                blockEntity.pistonScale = Mth.clamp(blockEntity.pistonScale - 0.1F, 0.0F, 1.0F);
                if (blockEntity.pistonScale == 0.0F) {
                    BlockState newState = state.setValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE, AetherIIBlockStateProperties.SentrySpawnerState.TRIGGERED);
                    blockEntity.updateSpawnerState((ServerLevel) level, pos, state, newState);
                }
            }
        }
    }

    public void updateSpawnerState(ServerLevel serverLevel, BlockPos pos, BlockState oldState, BlockState newState) {
        serverLevel.setBlock(pos, newState, 3);
        serverLevel.sendBlockUpdated(pos, oldState, newState, 3);
        this.setChanged();
    }

    public float getPistonAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.pistonScaleOld, this.pistonScale);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.pistonScaleOld = tag.getFloat("piston_scale_old");
        this.pistonScale = tag.getFloat("piston_scale");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("piston_scale_old", this.pistonScaleOld);
        tag.putFloat("piston_scale", this.pistonScale);
    }

    @Override
    public SentrySpawner getSpawner() {
        return this.spawner;
    }

    public class SentrySpawner extends BaseSpawner {
        private Mob delayedEntity = null;

        @Override
        public void serverTick(ServerLevel serverLevel, BlockPos pos) {
            BlockState state = serverLevel.getBlockState(pos);
            RandomSource random = serverLevel.getRandom();

            if ((state.hasProperty(SentrySpawnerBlock.SENTRY_SPAWNER_STATE) ? state.getValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE) : AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE) != AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE) {
                if (this.isNearPlayer(serverLevel, pos)) {
                    if (this.delayedEntity == null) {
                        if (this.spawnDelay == -1) {
                            this.delay(serverLevel, pos);
                        }

                        if (this.spawnDelay <= 0) {
                            SpawnData spawnData = this.getOrCreateNextSpawnData(serverLevel, random, pos);
                            CompoundTag entityTag = spawnData.getEntityToSpawn();
                            Optional<EntityType<?>> optional = EntityType.by(entityTag);
                            if (optional.isPresent()) {
                                Vec3 vec3 = Vec3.atBottomCenterOf(pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random)));
                                if (serverLevel.noCollision(optional.get().getAABB(vec3.x, vec3.y, vec3.z))) {
                                    BlockPos vecPos = BlockPos.containing(vec3);
                                    if (spawnData.getCustomSpawnRules().isPresent()) {
                                        if (!(optional.get()).getCategory().isFriendly() && serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                                            return;
                                        }
                                        SpawnData.CustomSpawnRules spawnRules = spawnData.getCustomSpawnRules().get();
                                        if (!spawnRules.blockLightLimit().isValueInRange(serverLevel.getBrightness(LightLayer.BLOCK, vecPos)) || !spawnRules.skyLightLimit().isValueInRange(serverLevel.getBrightness(LightLayer.SKY, vecPos))) {
                                            return;
                                        }
                                    } else if (!SpawnPlacements.checkSpawnRules(optional.get(), serverLevel, MobSpawnType.SPAWNER, vecPos, serverLevel.getRandom())) {
                                        return;
                                    }

                                    Entity entity = EntityType.loadEntityRecursive(entityTag, serverLevel, (loadedEntity) -> {
                                        loadedEntity.moveTo(vec3.x, vec3.y, vec3.z, loadedEntity.getYRot(), loadedEntity.getXRot());
                                        return loadedEntity;
                                    });
                                    if (entity != null) {
                                        int nearby = serverLevel.getEntitiesOfClass(entity.getClass(), new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(this.spawnRange)).size();
                                        if (nearby < this.maxNearbyEntities) {
                                            if (entity instanceof Mob mob) {
                                                entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), random.nextFloat() * 360.0F, 0.0F);
                                                if (ForgeEventFactory.checkSpawnPositionSpawner(mob, serverLevel, MobSpawnType.SPAWNER, spawnData, this)) {
                                                    var event = ForgeEventFactory.onFinalizeSpawnSpawner(mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), null, entityTag, this);
                                                    if (event != null && entityTag.size() == 1 && entityTag.contains("id", 8)) {
                                                        mob.finalizeSpawn(serverLevel, event.getDifficulty(), event.getSpawnType(), event.getSpawnData(), event.getSpawnTag());
                                                    }

                                                    this.delayedEntity = mob;

                                                    BlockState openingState = state.setValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE, AetherIIBlockStateProperties.SentrySpawnerState.OPENING);
                                                    SentrySpawnerBlockEntity.this.updateSpawnerState(serverLevel, pos, state, openingState);
                                                    serverLevel.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.25F, 1.5F);

                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            this.delay(serverLevel, pos);
                        }
                    }

                    if (this.delayedEntity != null && SentrySpawnerBlockEntity.this.pistonScale == 1.0F) {
                        if (serverLevel.tryAddFreshEntityWithPassengers(this.delayedEntity)) {
                            for (int l = 0; l < 20; l++) {
                                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                                serverLevel.sendParticles(ParticleTypes.POOF, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                            }
                            serverLevel.gameEvent(this.delayedEntity, GameEvent.ENTITY_PLACE, this.delayedEntity.position());
                            this.delayedEntity.spawnAnim();

                            BlockState closingState = state.setValue(SentrySpawnerBlock.SENTRY_SPAWNER_STATE, AetherIIBlockStateProperties.SentrySpawnerState.CLOSING);
                            SentrySpawnerBlockEntity.this.updateSpawnerState(serverLevel, pos, state, closingState);
                            serverLevel.playSound(null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1.25F, 1.5F);

                            this.delayedEntity = null;

                            this.delay(serverLevel, pos);
                        }
                    }

                    this.spawnDelay--;
                }
            }
        }

        @Override
        public void broadcastEvent(Level level, BlockPos pos, int id) {
            BlockState state = level.getBlockState(pos);
            level.blockEvent(pos, state.getBlock(), id, 0);
        }
    }
}

package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.dungeon.GroundTrapBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Optional;

public abstract class GroundTrapBlockEntity extends CustomSpawnerBlockEntity {
    private final GroundTrapSpawner spawner = new GroundTrapSpawner();

    public GroundTrapBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, GroundTrapBlockEntity blockEntity) {
        blockEntity.getSpawner().clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GroundTrapBlockEntity blockEntity) {
        blockEntity.getSpawner().serverTick((ServerLevel) level, pos);
    }

    @Override
    public GroundTrapSpawner getSpawner() {
        return this.spawner;
    }

    public class GroundTrapSpawner extends BaseSpawner {
        @Override
        public void serverTick(ServerLevel serverLevel, BlockPos pos) {
            BlockState state = serverLevel.getBlockState(pos);
            if ((state.hasProperty(GroundTrapBlock.TRAP_STATE) ? state.getValue(GroundTrapBlock.TRAP_STATE) : AetherIIBlockStateProperties.TrapState.LOADED) == AetherIIBlockStateProperties.TrapState.TRIGGERED) {
                RandomSource random = serverLevel.getRandom();
                SpawnData spawnData = this.getOrCreateNextSpawnData(serverLevel, random, pos);

                CompoundTag entityTag = spawnData.getEntityToSpawn();
                Optional<EntityType<?>> optional = EntityType.by(entityTag);
                if (optional.isPresent()) {
                    Vec3 vec3 = Vec3.atBottomCenterOf(pos.above());
                    if (serverLevel.noCollision(optional.get().getAABB(vec3.x, vec3.y, vec3.z))) {
                        BlockPos vecPos = BlockPos.containing(vec3);
                        Entity entity = EntityType.loadEntityRecursive(entityTag, serverLevel, (loadedEntity) -> {
                            loadedEntity.moveTo(vec3.x, vec3.y, vec3.z, loadedEntity.getYRot(), loadedEntity.getXRot());
                            return loadedEntity;
                        });
                        if (entity instanceof Mob mob) {
                            mob.setTarget(serverLevel.getNearestPlayer(mob, 20));
                            entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), random.nextFloat() * 360.0F, 0.0F);
                            var event = ForgeEventFactory.onFinalizeSpawnSpawner(mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), null, entityTag, this);
                            if (event != null && entityTag.size() == 1 && entityTag.contains("id", 8)) {
                                mob.finalizeSpawn(serverLevel, event.getDifficulty(), event.getSpawnType(), event.getSpawnData(), event.getSpawnTag());
                            }

                            BlockState spawnedState = state.setValue(GroundTrapBlock.TRAP_STATE, AetherIIBlockStateProperties.TrapState.SPAWNED);
                            serverLevel.setBlock(pos, spawnedState, 3);
                            serverLevel.sendBlockUpdated(pos, state, state, 3);
                            GroundTrapBlockEntity.this.setChanged();

                            if (serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                                for (int l = 0; l < 20; l++) {
                                    double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                    double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                    double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                                    serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                                    serverLevel.sendParticles(ParticleTypes.POOF, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                                }
                                serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, vecPos);
                                mob.spawnAnim();
                            }
                        }
                    }
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

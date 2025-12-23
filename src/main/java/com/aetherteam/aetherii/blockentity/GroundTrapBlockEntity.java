package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.dungeon.GroundTrapBlock;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BaseSpawnerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public abstract class GroundTrapBlockEntity extends CustomSpawnerBlockEntity {
    private final GroundTrapSpawner spawner = new GroundTrapSpawner();

    public class GroundTrapSpawner extends BaseSpawner {
        private boolean spawnedEntity = false;

        @Override
        public void serverTick(ServerLevel serverLevel, BlockPos pos) {
            BlockState state = serverLevel.getBlockState(pos);
            if (state.getValueOrElse(GroundTrapBlock.TRIGGERED, false)) {
                if (!this.hasSpawnedEntity()) {
                    BaseSpawnerAccessor spawnerAccessor = (BaseSpawnerAccessor) this;
                    RandomSource random = serverLevel.getRandom();
                    SpawnData spawnData = spawnerAccessor.callGetOrCreateNextSpawnData(serverLevel, random, pos);

                    try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(this::toString, AetherII.LOGGER)) {
                        ValueInput valueInput = TagValueInput.create(problemreporter$scopedcollector, serverLevel.registryAccess(), spawnData.getEntityToSpawn());
                        Optional<EntityType<?>> optional = EntityType.by(valueInput);
                        if (optional.isPresent()) {
                            Vec3 vec3 = pos.above().getBottomCenter();
                            if (serverLevel.noBlockCollision(null, optional.get().getSpawnAABB(vec3.x, vec3.y, vec3.z))) { //todo this is the actual gameplay check to worry about for animation staging

                                //todo may need a block renderer for more advanced check if a mob is gonna spawn not related to the blockstates.


                                BlockPos vecPos = BlockPos.containing(vec3);
                                Entity entity = EntityType.loadEntityRecursive(valueInput, serverLevel, EntitySpawnReason.SPAWNER, (loadedEntity) -> {
                                    loadedEntity.snapTo(vec3.x, vec3.y, vec3.z, loadedEntity.getYRot(), loadedEntity.getXRot());
                                    return loadedEntity;
                                });
                                if (entity instanceof Mob mob) {
                                    entity.snapTo(entity.getX(), entity.getY(), entity.getZ(), random.nextFloat() * 360.0F, 0.0F);
                                    boolean def = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().getString("id").isPresent();
                                    EventHooks.finalizeMobSpawnSpawner(mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.SPAWNER, null, this, def);
                                    Optional<EquipmentTable> equipment = spawnData.getEquipment();
                                    Objects.requireNonNull(mob);
                                    equipment.ifPresent(mob::equip);

                                    if (serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                                        serverLevel.levelEvent(2004, pos, 0);
                                        serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, vecPos);
                                        mob.spawnAnim();
                                        this.setSpawnedEntity(true);
                                        serverLevel.sendBlockUpdated(pos, state, state, 3);
                                    }
                                }
                            }
                        }



//                this.delay(serverLevel, pos); //todo how to handle delay when block is covered up by collisions
                    }
                }
            }
        }

        @Override
        public void broadcastEvent(Level level, BlockPos pos, int id) {
            BlockState state = level.getBlockState(pos);
            level.blockEvent(pos, state.getBlock(), id, 0);
        }

        @Override
        public void load(@Nullable Level level, BlockPos pos, ValueInput input) {
            super.load(level, pos, input);
            this.setSpawnedEntity(input.getBooleanOr("SpawnedEntity", false));
        }

        @Override
        public void save(ValueOutput output) {
            super.save(output);
            output.putBoolean("SpawnedEntity", this.hasSpawnedEntity());
        }

        public boolean hasSpawnedEntity() {
            return this.spawnedEntity;
        }

        public void setSpawnedEntity(boolean spawnedEntity) {
            this.spawnedEntity = spawnedEntity;
        }
    }

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
}

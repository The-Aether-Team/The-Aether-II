package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.data.resources.maps.MimicTransform;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public interface MimicOption {
    static void spawnMimic(BlockState state, ServerLevel level, BlockPos pos, ResourceKey<LootTable> lootTable) {
        Entity entity = AetherIIEntityTypes.SENTRY_CRATE_MIMIC.get().create(level, EntitySpawnReason.TRIGGERED);
        MimicTransform mimicTransform = BuiltInRegistries.BLOCK.wrapAsHolder(state.getBlock()).getData(AetherIIDataMaps.MIMIC_TRANSFORM);
        if (mimicTransform != null) {
            EntityType<?> transformEntityType = BuiltInRegistries.ENTITY_TYPE.getValue(mimicTransform.entityType());
            entity = transformEntityType.create(level, EntitySpawnReason.TRIGGERED);
        }
        if (entity != null) {
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            float angle = direction.toYRot();
            if (entity instanceof Mimic mimic) {
                mimic.setLootTableFromChest(Optional.ofNullable(lootTable));
            }
            entity.snapTo(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), angle, 0.0F);
            entity.setYHeadRot(angle);
            level.addFreshEntity(entity);
            if (entity instanceof Mimic mimic) {
                mimic.spawnAnim();
            }
        }
    }
}

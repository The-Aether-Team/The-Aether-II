package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.stream.Stream;

public class AetherIIEntityLoot extends EntityLootSubProvider {
    public AetherIIEntityLoot() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(AetherIIEntityTypes.AERBUNNY.get(), LootTable.lootTable());
    }

    @Override
    public Stream<EntityType<?>> getKnownEntityTypes() {
        return AetherIIEntityTypes.ENTITY_TYPES.getEntries().stream().flatMap(entityType -> Stream.of(entityType.get()));
    }
}

package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class BossRoomProcessor extends StructureProcessor {
    public static final BossRoomProcessor INSTANCE = new BossRoomProcessor();

    public static final MapCodec<BossRoomProcessor> CODEC = MapCodec.unit(BossRoomProcessor.INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader level, BlockPos seedPos, StructureTemplate.StructureEntityInfo rawEntityInfo, StructureTemplate.StructureEntityInfo entityInfo, StructurePlaceSettings placementSettings, StructureTemplate template) {
        BoundingBox boundingBox = template.getBoundingBox(placementSettings, seedPos);
        BossRoomTracker tracker = new BossRoomTracker(
                entityInfo.pos,
                new Vec3(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ()),
                new Vec3(boundingBox.maxX() + 1, boundingBox.maxY() + 1, boundingBox.maxZ() + 1),
                new ArrayList<>());
        entityInfo.nbt.store("Dungeon", BossRoomTracker.CODEC, tracker);
        return super.processEntity(level, seedPos, rawEntityInfo, entityInfo, placementSettings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.BOSS_ROOM.get();
    }
}

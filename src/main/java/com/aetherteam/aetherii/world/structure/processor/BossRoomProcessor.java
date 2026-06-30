package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
public class BossRoomProcessor extends StructureProcessor {
    public static final BossRoomProcessor INSTANCE = new BossRoomProcessor();

    public static final MapCodec<BossRoomProcessor> CODEC = MapCodec.unit(BossRoomProcessor.INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader level, BlockPos seedPos, StructureTemplate.StructureEntityInfo rawEntityInfo, StructureTemplate.StructureEntityInfo entityInfo, StructurePlaceSettings placementSettings, StructureTemplate template) {
        BoundingBox boundingBox = template.getBoundingBox(placementSettings, seedPos);
        CompoundTag dungeon = new CompoundTag();
        dungeon.putDouble("OriginX", entityInfo.pos.x());
        dungeon.putDouble("OriginY", entityInfo.pos.y());
        dungeon.putDouble("OriginZ", entityInfo.pos.z());
        dungeon.putDouble("RoomBoundsMinX", boundingBox.minX());
        dungeon.putDouble("RoomBoundsMinY", boundingBox.minY());
        dungeon.putDouble("RoomBoundsMinZ", boundingBox.minZ());
        dungeon.putDouble("RoomBoundsMaxX", boundingBox.maxX() + 1);
        dungeon.putDouble("RoomBoundsMaxY", boundingBox.maxY() + 1);
        dungeon.putDouble("RoomBoundsMaxZ", boundingBox.maxZ() + 1);
        dungeon.putInt("DungeonPlayersSize", 0);
        entityInfo.nbt.put("Dungeon", dungeon);
        return super.processEntity(level, seedPos, rawEntityInfo, entityInfo, placementSettings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.BOSS_ROOM.get();
    }
}

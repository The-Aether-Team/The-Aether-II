package com.aetherteam.aetherii.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

public final class AetherIIShapes {
    private AetherIIShapes() {
    }

    public static VoxelShape boxZ(double sizeXY, double minZ, double maxZ) {
        return boxZ(sizeXY, sizeXY, minZ, maxZ);
    }

    public static VoxelShape boxZ(double sizeX, double sizeY, double minZ, double maxZ) {
        double halfY = sizeY / 2.0;
        return boxZ(sizeX, 8.0 - halfY, 8.0 + halfY, minZ, maxZ);
    }

    public static VoxelShape boxZ(double sizeX, double minY, double maxY, double minZ, double maxZ) {
        double halfX = sizeX / 2.0;
        return Block.box(8.0 - halfX, minY, minZ, 8.0 + halfX, maxY, maxZ);
    }

    public static Map<Direction, VoxelShape> rotateHorizontal(VoxelShape north) {
        EnumMap<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, north);
        map.put(Direction.EAST, rotateY90(north));
        map.put(Direction.SOUTH, rotateY90(map.get(Direction.EAST)));
        map.put(Direction.WEST, rotateY90(map.get(Direction.SOUTH)));
        return map;
    }

    private static VoxelShape rotateY90(VoxelShape shape) {
        final VoxelShape[] rotated = {Shapes.empty()};
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> rotated[0] = Shapes.or(rotated[0], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX)));
        return rotated[0].optimize();
    }
}

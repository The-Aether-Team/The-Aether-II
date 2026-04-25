package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonBlockOverlayRenderer {
    private static final HashMap<Integer, List<BlockPos>> positionsForTypes = new HashMap<>();

    public static void submitDungeonBlockOverlays(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Vec3 cameraPos, Frustum frustum, Minecraft minecraft) {
        if (minecraft.level != null) {
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            RenderBuffers renderBuffers = minecraft.renderBuffers();
            int range = 32; // Range for how far the overlays can be rendered at.
            if (player != null && player.isCreative()) {
                BlockPos playerPos = player.blockPosition();
                ItemStack stack = player.getMainHandItem();
                int type = idForItem(stack); // Get an ID for the currently held dungeon block item.
                if (type != -1) {
                    updatePositions(playerPos, level, stack, range, type, false); // Check to add overlays to the map.
                }
                for (int i = 0; i < positionsForTypes.size(); i++) {
                    submitOverlays(poseStack, submitNodeCollector, level, cameraPos, frustum, i); // Render any overlays at positions in the map.
                    updatePositions(playerPos, level, stack, range, i, true); // Check to remove overlays from the map.
                }
            }
        }
    }

    private static void updatePositions(BlockPos playerPos, ClientLevel level, ItemStack stack, int range, int type, boolean depopulate) {
        // Initial setup of the different IDs in the map.
        positionsForTypes.putIfAbsent(0, new ArrayList<>());
        positionsForTypes.putIfAbsent(1, new ArrayList<>());
        positionsForTypes.putIfAbsent(2, new ArrayList<>());
        positionsForTypes.putIfAbsent(3, new ArrayList<>());
        // Loop and select random positions to check and see whether an overlay should be rendered there.
        for (int c = 0; c < 667; ++c) {
            int x = playerPos.getX() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            int y = playerPos.getY() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            int z = playerPos.getZ() + level.getRandom().nextInt(range) - level.getRandom().nextInt(range);
            if (!depopulate) { // For checking to add overlays to the world.
                BlockPos pos = new BlockPos(x, y, z);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (stack.is(level.getBlockState(pos).getBlock().asItem()) && blockEntity != null && blockEntity.collectComponents().has(AetherIIDataComponents.BLOCK_STATE)) { // Add an overlay if the corresponding dungeon block item is held.
                    if (!positionsForTypes.get(type).contains(pos)) {
                        positionsForTypes.get(type).add(pos);
                    }
                }
            } else { // For checking to remove overlays from the world.
                List<BlockPos> positions = positionsForTypes.get(type);
                if (!positions.isEmpty() && level.getRandom().nextInt(100) == 0) {
                    BlockPos pos = positions.get(level.getRandom().nextInt(positions.size()));
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (!stack.is(level.getBlockState(pos).getBlock().asItem()) || blockEntity == null || !blockEntity.collectComponents().has(AetherIIDataComponents.BLOCK_STATE)) { // Remove an overlay if the corresponding dungeon block item is not held.
                        positions.remove(pos);
                        positionsForTypes.put(type, positions);
                    }
                }
            }
        }
    }

    private static void submitOverlays(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ClientLevel level, Vec3 cameraPos, Frustum frustum, int type) {
        for (BlockPos blockPos : positionsForTypes.get(type)) {
            if (frustum.isVisible(new AABB(blockPos)) && level.getBlockState(blockPos).getRenderShape() != RenderShape.INVISIBLE) {
                submitNodeCollector.submitCustomGeometry(poseStack, Sheets.cutoutBlockSheet(), submitSurfaces(cameraPos, blockPos,
                        (float) (blockPos.getX() - cameraPos.x()) - 0.001F,
                        (float) (blockPos.getZ() - cameraPos.z()) - 0.001F,
                        (float) (blockPos.getX() - cameraPos.x()) + 1.001F,
                        (float) (blockPos.getZ() - cameraPos.z()) + 1.001F,
                        (float) (blockPos.getY() - cameraPos.y()) - 0.001F,
                        (float) (blockPos.getY() - cameraPos.y()) + 1.001F,
                        type));
            }
        }
    }

    private static SubmitNodeCollector.CustomGeometryRenderer submitSurfaces(Vec3 cameraPos, BlockPos blockPos, float startX, float startZ, float endX, float endZ, float botY, float topY, int type) {
        return new SubmitNodeCollector.CustomGeometryRenderer() {
            @Override
            public void render(PoseStack.Pose pose, VertexConsumer vertexConsumer) {
                TextureAtlasSprite sprite = spriteForId(type);

                float minU = sprite.getU1();
                float maxU = sprite.getU0();
                float minV = sprite.getV1();
                float maxV = sprite.getV0();

                if (sprite != null) {
                    // Renders an overlay on the bottom face of a block if the camera is below it, i.e. the camera can see the block face.
                    if (cameraPos.y() < blockPos.getY() + botY) {
                        buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
                        buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
                        buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
                        buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);
                    }

                    // Renders an overlay on the top face of a block if the camera is above it, i.e. the camera can see the block face.
                    if (cameraPos.y() > blockPos.getY() + topY) {
                        buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
                        buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
                        buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
                        buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);
                    }

                    // Renders an overlay on the north face of a block if the camera's z-coordinate is less than the block's z-coordinate, i.e. the camera can see the block face.
                    if (cameraPos.z() < blockPos.getZ() + startZ) {
                        buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
                        buildVertex(vertexConsumer, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
                        buildVertex(vertexConsumer, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
                        buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);
                    }

                    // Renders an overlay on the south face of a block if the camera's z-coordinate is greater than the block's z-coordinate, i.e. the camera can see the block face.
                    if (cameraPos.z() > blockPos.getZ() + endZ) {
                        buildVertex(vertexConsumer, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
                        buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
                        buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
                        buildVertex(vertexConsumer, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);
                    }

                    // Renders an overlay on the west face of a block if the camera's x-coordinate is less than the block's x-coordinate, i.e. the camera can see the block face.
                    if (cameraPos.x() < blockPos.getX() + startX) {
                        buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
                        buildVertex(vertexConsumer, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
                        buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
                        buildVertex(vertexConsumer, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);
                    }

                    // Renders an overlay on the east face of a block if the camera's x-coordinate is greater than the block's x-coordinate, i.e. the camera can see the block face.
                    if (cameraPos.x() > blockPos.getX() + endX) {
                        buildVertex(vertexConsumer, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
                        buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
                        buildVertex(vertexConsumer, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
                        buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
                    }
                }
            }
        };
    }

    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.addVertex(pose, x, y, z).setColor(0xFF, 0xFF, 0xFF, 0xAA).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose, normalX, normalY, normalZ);
    }

    @Nullable
    private static TextureAtlasSprite spriteForId(int id) {
        switch (id) {
            case 0 -> {
                return Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_lock")));
            }
            case 1 -> {
                return Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_doorway")));
            }
            case 2 -> {
                return Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(Identifier.fromNamespaceAndPath(AetherII.MODID, "dungeon_treasure")));
            }
            default -> {
                return null;
            }
        }
    }

    private static int idForItem(ItemStack stack) {
        if (stack.is(AetherIIBlocks.LOCKED_BLOCK.asItem())) {
            return 0;
        } else if (stack.is(AetherIIBlocks.BOSS_DOORWAY_BLOCK.asItem())) {
            return 1;
        } else if (stack.is(AetherIIBlocks.TREASURE_DOORWAY_BLOCK.asItem())) {
            return 2;
        } else {
            return -1;
        }
    }
}
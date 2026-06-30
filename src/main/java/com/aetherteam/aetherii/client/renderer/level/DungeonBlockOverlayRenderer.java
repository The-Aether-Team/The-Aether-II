package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonBlockOverlayRenderer {
    private static final HashMap<Integer, List<BlockPos>> positionsForTypes = new HashMap<>();

    public static void renderDungeonBlockOverlays(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            renderDungeonBlockOverlays(event.getPoseStack(), event.getCamera().getPosition(), event.getFrustum(), Minecraft.getInstance());
        }
    }

    public static void renderDungeonBlockOverlays(PoseStack poseStack, Vec3 cameraPos, Frustum frustum, Minecraft minecraft) {
        if (minecraft.level != null) {
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            int range = 32; // Range for how far the overlays can be rendered at.
            if (player != null && player.isCreative()) {
                BlockPos playerPos = player.blockPosition();
                ItemStack stack = player.getMainHandItem();
                int type = idForItem(stack); // Get an ID for the currently held dungeon block item.
                if (type != -1) {
                    updatePositions(playerPos, level, stack, range, type, false); // Check to add overlays to the map.
                }
                for (int i = 0; i < 3; i++) {
                    renderOverlays(poseStack, level, cameraPos, frustum, i); // Render any overlays at positions in the map.
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
                if (hasCopiedState(level, pos, stack)) { // Add an overlay if the corresponding dungeon block item is held.
                    if (!positionsForTypes.get(type).contains(pos)) {
                        positionsForTypes.get(type).add(pos);
                    }
                }
            } else { // For checking to remove overlays from the world.
                List<BlockPos> positions = positionsForTypes.get(type);
                if (!positions.isEmpty() && level.getRandom().nextInt(100) == 0) {
                    BlockPos pos = positions.get(level.getRandom().nextInt(positions.size()));
                    if (!hasCopiedState(level, pos, stack)) { // Remove an overlay if the corresponding dungeon block item is not held.
                        positions.remove(pos);
                        positionsForTypes.put(type, positions);
                    }
                }
            }
        }
    }

    private static boolean hasCopiedState(ClientLevel level, BlockPos pos, ItemStack stack) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return stack.is(level.getBlockState(pos).getBlock().asItem())
                && blockEntity instanceof CopyBlockEntity copyBlockEntity
                && copyBlockEntity.getCopyState() != null;
    }

    private static void renderOverlays(PoseStack poseStack, ClientLevel level, Vec3 cameraPos, Frustum frustum, int type) {
        List<BlockPos> positions = positionsForTypes.get(type);
        if (positions == null || positions.isEmpty()) {
            return;
        }
        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(Sheets.cutoutBlockSheet());
        for (BlockPos blockPos : positionsForTypes.get(type)) {
            if (frustum.isVisible(new AABB(blockPos)) && level.getBlockState(blockPos).getRenderShape() != RenderShape.INVISIBLE) {
                renderSurfaces(poseStack.last(), vertexConsumer, cameraPos, blockPos,
                        (float) (blockPos.getX() - cameraPos.x()) - 0.001F,
                        (float) (blockPos.getZ() - cameraPos.z()) - 0.001F,
                        (float) (blockPos.getX() - cameraPos.x()) + 1.001F,
                        (float) (blockPos.getZ() - cameraPos.z()) + 1.001F,
                        (float) (blockPos.getY() - cameraPos.y()) - 0.001F,
                        (float) (blockPos.getY() - cameraPos.y()) + 1.001F,
                        type);
            }
        }
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch(Sheets.cutoutBlockSheet());
    }

    private static void renderSurfaces(PoseStack.Pose pose, VertexConsumer vertexConsumer, Vec3 cameraPos, BlockPos blockPos, float startX, float startZ, float endX, float endZ, float botY, float topY, int type) {
        TextureAtlasSprite sprite = spriteForId(type);
        if (sprite == null) {
            return;
        }

        float minU = sprite.getU1();
        float maxU = sprite.getU0();
        float minV = sprite.getV1();
        float maxV = sprite.getV0();

        if (cameraPos.y() < blockPos.getY()) {
            buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
            buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
            buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
            buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);
        }

        if (cameraPos.y() > blockPos.getY() + 1.0) {
            buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
            buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
            buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
            buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);
        }

        if (cameraPos.z() < blockPos.getZ()) {
            buildVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
            buildVertex(vertexConsumer, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
            buildVertex(vertexConsumer, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
            buildVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);
        }

        if (cameraPos.z() > blockPos.getZ() + 1.0) {
            buildVertex(vertexConsumer, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
            buildVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
            buildVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
            buildVertex(vertexConsumer, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);
        }

        if (cameraPos.x() < blockPos.getX()) {
            buildVertex(vertexConsumer, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
            buildVertex(vertexConsumer, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
            buildVertex(vertexConsumer, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
            buildVertex(vertexConsumer, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);
        }

        if (cameraPos.x() > blockPos.getX() + 1.0) {
            buildVertex(vertexConsumer, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
            buildVertex(vertexConsumer, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
            buildVertex(vertexConsumer, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
            buildVertex(vertexConsumer, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
        }
    }

    private static void buildVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.vertex(pose.pose(), x, y, z).color(0xFF, 0xFF, 0xFF, 0xAA).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(pose.normal(), normalX, normalY, normalZ).endVertex();
    }

    @Nullable
    private static TextureAtlasSprite spriteForId(int id) {
        switch (id) {
            case 0 -> {
                return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(AetherII.MODID, "block/dungeon_lock"));
            }
            case 1 -> {
                return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(AetherII.MODID, "block/dungeon_doorway"));
            }
            case 2 -> {
                return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(AetherII.MODID, "block/dungeon_treasure"));
            }
            default -> {
                return null;
            }
        }
    }

    private static int idForItem(ItemStack stack) {
        if (stack.is(AetherIIBlocks.LOCKED_BLOCK.get().asItem())) {
            return 0;
        } else if (stack.is(AetherIIBlocks.BOSS_DOORWAY_BLOCK.get().asItem())) {
            return 1;
        } else if (stack.is(AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get().asItem())) {
            return 2;
        } else {
            return -1;
        }
    }
}

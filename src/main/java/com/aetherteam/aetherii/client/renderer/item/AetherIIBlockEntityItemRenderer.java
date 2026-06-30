package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.SkyrootChestBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.AbandonedBagRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.FungalCacheRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SageChestRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AbandonedBagModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.FungalCacheModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SageChestModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class AetherIIBlockEntityItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation ALKAHEST_PURIFIER = new ResourceLocation(AetherII.MODID, "textures/entity/alkahest_purifier/alkahest_purifier_0.png");
    private static final ResourceLocation SENTRY_CRATE = new ResourceLocation(AetherII.MODID, "textures/entity/sentry_crate/single/sentry_crate_0.png");
    private static final ResourceLocation SENTRY_SPAWNER_BASE = new ResourceLocation(AetherII.MODID, "textures/entity/sentry_spawner/sentry_spawner_base_0.png");
    private static final ResourceLocation SENTRY_SPAWNER_PISTON = new ResourceLocation(AetherII.MODID, "textures/entity/sentry_spawner/piston_off.png");
    private static final ResourceLocation DUNGEON_LOCK = new ResourceLocation(AetherII.MODID, "dungeon_lock");
    private static final ResourceLocation DUNGEON_DOORWAY = new ResourceLocation(AetherII.MODID, "dungeon_doorway");
    private static final ResourceLocation DUNGEON_TREASURE = new ResourceLocation(AetherII.MODID, "dungeon_treasure");

    private static AetherIIBlockEntityItemRenderer instance;

    private final BlockEntityRenderDispatcher dispatcher;
    private final EntityModelSet modelSet;
    private final SkyrootChestBlockEntity skyrootChest = new SkyrootChestBlockEntity();
    private AlkahestPurifierModel alkahestPurifierModel;
    private SentryCrateModel sentryCrateModel;
    private SentrySpawnerModel sentrySpawnerModel;
    private SentrySpawnerPistonModel sentrySpawnerPistonModel;
    private VaseModel vaseModel;
    private AbandonedBagModel abandonedBagModel;
    private FungalCacheModel fungalCacheModel;
    private SageChestModel sageChestModel;
    private SkyrootBedRenderer skyrootBedRenderer;

    private AetherIIBlockEntityItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.dispatcher = dispatcher;
        this.modelSet = modelSet;
        this.bakeModels();
    }

    public static AetherIIBlockEntityItemRenderer getInstance() {
        if (instance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            instance = new AetherIIBlockEntityItemRenderer(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
        }
        return instance;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        super.onResourceManagerReload(resourceManager);
        this.bakeModels();
    }

    private void bakeModels() {
        this.alkahestPurifierModel = new AlkahestPurifierModel(this.modelSet.bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
        this.sentryCrateModel = new SentryCrateModel(this.modelSet.bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
        this.sentrySpawnerModel = new SentrySpawnerModel(this.modelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER));
        this.sentrySpawnerPistonModel = new SentrySpawnerPistonModel(this.modelSet.bakeLayer(AetherIIModelLayers.SENTRY_SPAWNER_PISTON));
        this.vaseModel = new VaseModel(this.modelSet.bakeLayer(AetherIIModelLayers.VASE));
        this.abandonedBagModel = new AbandonedBagModel(this.modelSet.bakeLayer(AetherIIModelLayers.ABANDONED_BAG));
        this.fungalCacheModel = new FungalCacheModel(this.modelSet.bakeLayer(AetherIIModelLayers.FUNGAL_CACHE));
        this.sageChestModel = new SageChestModel(this.modelSet.bakeLayer(AetherIIModelLayers.SAGE_CHEST));
        this.skyrootBedRenderer = new SkyrootBedRenderer(this.modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = stack.getItem();
        if (item == AetherIIBlocks.ALKAHEST_PURIFIER.get().asItem()) {
            this.renderAlkahestPurifier(stack, poseStack, buffer, packedLight);
        } else if (item == AetherIIBlocks.LOCKED_BLOCK.get().asItem()) {
            this.renderCopyBlock(stack, poseStack, buffer, packedLight, packedOverlay, DUNGEON_LOCK);
        } else if (item == AetherIIBlocks.BOSS_DOORWAY_BLOCK.get().asItem()) {
            this.renderCopyBlock(stack, poseStack, buffer, packedLight, packedOverlay, DUNGEON_DOORWAY);
        } else if (item == AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get().asItem()) {
            this.renderCopyBlock(stack, poseStack, buffer, packedLight, packedOverlay, DUNGEON_TREASURE);
        } else if (item == AetherIIBlocks.SKYROOT_CHEST.get().asItem()) {
            this.renderSkyrootChest(poseStack, buffer, packedLight, packedOverlay);
        } else if (item == AetherIIBlocks.SENTRY_CRATE.get().asItem()) {
            this.renderModel(stack, this.sentryCrateModel, poseStack, buffer, SENTRY_CRATE, packedLight, RenderType.entityCutout(SENTRY_CRATE));
        } else if (item == AetherIIBlocks.SENTRY_SPAWNER.get().asItem()) {
            this.renderSentrySpawner(stack, poseStack, buffer, packedLight);
        } else if (item == AetherIIBlocks.HOLYSTONE_VASE.get().asItem()) {
            this.renderVase(stack, poseStack, buffer, packedLight, "holystone_vase");
        } else if (item == AetherIIBlocks.VERADEXIAN_VASE.get().asItem()) {
            this.renderVase(stack, poseStack, buffer, packedLight, "veradexian_vase");
        } else if (item == AetherIIBlocks.BREXALLEN_VASE.get().asItem()) {
            this.renderVase(stack, poseStack, buffer, packedLight, "brexallen_vase");
        } else if (item == AetherIIBlocks.ABANDONED_BAG.get().asItem()) {
            AbandonedBagRenderer.renderModel(this.abandonedBagModel, 0.0F, 0.0F, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        } else if (item == AetherIIBlocks.FUNGAL_CACHE.get().asItem()) {
            FungalCacheRenderer.renderModel(this.fungalCacheModel, 0.0F, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        } else if (item == AetherIIBlocks.SAGE_CHEST.get().asItem()) {
            SageChestRenderer.renderModel(this.sageChestModel, SageChestRenderer.texture(ChestType.SINGLE), 0.0F, 0.0F, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof BedBlock bedBlock) {
            this.renderSkyrootBed(bedBlock, poseStack, buffer, packedLight, packedOverlay);
        }
    }

    private void renderAlkahestPurifier(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.alkahestPurifierModel.setupAnim(0.0F);
        this.renderModel(stack, this.alkahestPurifierModel, poseStack, buffer, ALKAHEST_PURIFIER, packedLight, RenderType.entityCutout(ALKAHEST_PURIFIER));
        poseStack.popPose();
    }

    private void renderSkyrootChest(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (AetherIIAtlases.SKYROOT_CHEST_MATERIAL == null) {
            AetherIIAtlases.registerSkyrootChestAtlases();
        }
        this.dispatcher.renderItem(this.skyrootChest, poseStack, buffer, packedLight, packedOverlay);
    }

    private void renderSentrySpawner(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.renderModel(stack, this.sentrySpawnerModel, poseStack, buffer, SENTRY_SPAWNER_BASE, packedLight, RenderType.entityCutout(SENTRY_SPAWNER_BASE));
        this.renderModel(stack, this.sentrySpawnerPistonModel, poseStack, buffer, SENTRY_SPAWNER_PISTON, packedLight, RenderType.entityCutout(SENTRY_SPAWNER_PISTON));
        poseStack.popPose();
    }

    private void renderVase(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, String name) {
        ResourceLocation texture = new ResourceLocation(AetherII.MODID, "textures/entity/vases/" + name + ".png");
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.renderModel(stack, this.vaseModel, poseStack, buffer, texture, packedLight, RenderType.entityCutout(texture));
        poseStack.popPose();
    }

    private void renderCopyBlock(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ResourceLocation overlay) {
        BlockState copiedState = AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE);
        if (copiedState == null) {
            return;
        }
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(copiedState, poseStack, buffer, packedLight, packedOverlay);
        this.renderCopyBlockOverlay(poseStack, buffer, overlay);
    }

    private void renderCopyBlockOverlay(PoseStack poseStack, MultiBufferSource buffer, ResourceLocation overlay) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(overlay.getNamespace(), "block/" + overlay.getPath()));
        VertexConsumer vertexConsumer = buffer.getBuffer(Sheets.cutoutBlockSheet());
        this.submitCopyBlockOverlaySurfaces(vertexConsumer, poseStack.last(), sprite, -0.001F, -0.001F, 1.001F, 1.001F, -0.001F, 1.001F);
    }

    private void submitCopyBlockOverlaySurfaces(VertexConsumer vertexConsumer, PoseStack.Pose pose, TextureAtlasSprite sprite, float startX, float startZ, float endX, float endZ, float botY, float topY) {
        float minU = sprite.getU1();
        float maxU = sprite.getU0();
        float minV = sprite.getV1();
        float maxV = sprite.getV0();

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, -1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, -1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, endZ, maxU, maxV, 0, -1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, endZ, minU, maxV, 0, -1, 0);

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, startZ, minU, minV, 0, 1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, startZ, maxU, minV, 0, 1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 1, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 1, 0);

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, startZ, minU, minV, 0, 0, -1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, startZ, minU, maxV, 0, 0, -1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, startZ, maxU, maxV, 0, 0, -1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, startZ, maxU, minV, 0, 0, -1);

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, endZ, minU, minV, 0, 0, 1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, endZ, minU, maxV, 0, 0, 1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, endZ, maxU, maxV, 0, 0, 1);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, endZ, maxU, minV, 0, 0, 1);

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, endZ, minU, minV, -1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, endZ, minU, maxV, -1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, topY, startZ, maxU, maxV, -1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, startX, botY, startZ, maxU, minV, -1, 0, 0);

        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, startZ, minU, minV, 1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, startZ, minU, maxV, 1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, topY, endZ, maxU, maxV, 1, 0, 0);
        this.buildCopyBlockOverlayVertex(vertexConsumer, pose, endX, botY, endZ, maxU, minV, 1, 0, 0);
    }

    private void buildCopyBlockOverlayVertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float normalX, float normalY, float normalZ) {
        builder.vertex(pose.pose(), x, y, z)
                .color(0xFF, 0xFF, 0xFF, 0xAA)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(pose.normal(), normalX, normalY, normalZ)
                .endVertex();
    }

    private void renderSkyrootBed(BedBlock bedBlock, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        this.skyrootBedRenderer.renderInHand(poseStack, buffer, packedLight, packedOverlay, SkyrootBedRenderer.texture(bedBlock, bedBlock.getColor()));
    }

    private void renderModel(ItemStack stack, Model model, PoseStack poseStack, MultiBufferSource buffer, ResourceLocation texture, int packedLight, RenderType renderType) {
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, renderType, false, stack.hasFoil());
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}

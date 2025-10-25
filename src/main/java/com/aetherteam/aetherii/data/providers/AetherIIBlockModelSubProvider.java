package com.aetherteam.aetherii.data.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.aetherteam.aetherii.client.renderer.block.model.builder.TrunkModelBuilder;
import com.aetherteam.aetherii.client.renderer.item.color.AetherGrassColorSource;
import com.aetherteam.aetherii.client.renderer.item.model.AlkahestPurifierSpecialRenderer;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureMappings;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureSlots;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITexturedModels;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.block.model.multipart.CombinedCondition;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.BedSpecialRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;

public class AetherIIBlockModelSubProvider extends BlockModelGenerators {
    public AetherIIBlockModelSubProvider(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void createCrossBlock(Block block, PlantType type, TextureMapping mapping) {
        MultiVariant crossBlock = plainVariant(type.getCross().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(block, mapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(block, crossBlock));
    }

    @Override
    public void createPlant(Block plant, Block pot, PlantType type) {
        this.createCrossBlock(plant, type);
        TextureMapping textureMapping = type.getPlantTextureMapping(plant);
        MultiVariant crossPlant = plainVariant(type.getCrossPot().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(pot, textureMapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(pot, crossPlant));
    }

    public void createTrunk(Block trunk, Block log) {
        TextureMapping mapping = TextureMapping.cube(log).copyForced(TextureSlot.ALL, TextureSlot.PARTICLE);
        MultiVariant side = plainVariant(AetherIIModelTemplates.TRUNK_SIDE.create(trunk, mapping, this.modelOutput));
        MultiVariant sideTall = plainVariant(AetherIIModelTemplates.TRUNK_SIDE_TALL.create(trunk, mapping, this.modelOutput));
        ResourceLocation corner = AetherIIModelTemplates.TRUNK_CORNER.create(trunk, mapping, this.modelOutput);
        ResourceLocation cornerTall = AetherIIModelTemplates.TRUNK_CORNER_TALL.create(trunk, mapping, this.modelOutput);
        ResourceLocation inventory = AetherIIModelTemplates.TRUNK_INVENTORY.create(trunk, mapping, this.modelOutput);

        MultiVariant center = plainVariant(AetherIIModelTemplates.TRUNK_CENTER.extend().build().create(trunk, mapping, this.modelOutput));
        MultiVariant centerTall = plainVariant(AetherIIModelTemplates.TRUNK_CENTER_TALL.extend().build().create(trunk, mapping, this.modelOutput));

        MultiPartGenerator model = MultiPartGenerator.multiPart(trunk)
                .with(MultiVariant.of(new TrunkModelBuilder(corner, cornerTall)))
                .with(condition().term(TrunkBlock.TALL, false), center)
                .with(condition().term(TrunkBlock.TALL, true), centerTall)
                .with(condition().term(TrunkBlock.NORTH_CONNECTION, WallSide.LOW), side.with(UV_LOCK))
                .with(condition().term(TrunkBlock.EAST_CONNECTION, WallSide.LOW), side.with(Y_ROT_90).with(UV_LOCK))
                .with(condition().term(TrunkBlock.SOUTH_CONNECTION, WallSide.LOW), side.with(Y_ROT_180).with(UV_LOCK))
                .with(condition().term(TrunkBlock.WEST_CONNECTION, WallSide.LOW), side.with(Y_ROT_270).with(UV_LOCK))
                .with(condition().term(TrunkBlock.NORTH_CONNECTION, WallSide.TALL), sideTall.with(UV_LOCK))
                .with(condition().term(TrunkBlock.EAST_CONNECTION, WallSide.TALL), sideTall.with(Y_ROT_90).with(UV_LOCK))
                .with(condition().term(TrunkBlock.SOUTH_CONNECTION, WallSide.TALL), sideTall.with(Y_ROT_180).with(UV_LOCK))
                .with(condition().term(TrunkBlock.WEST_CONNECTION, WallSide.TALL), sideTall.with(Y_ROT_270).with(UV_LOCK));

        this.blockStateOutput.accept(model);
        this.registerSimpleItemModel(trunk, inventory);
    }

    public static Condition and(ConditionBuilder... condition) {
        return new CombinedCondition(CombinedCondition.Operation.AND, Stream.of(condition).map(ConditionBuilder::build).toList());
    }

    @Override
    public void createDoor(Block block) {
        TextureMapping bottomMapping = AetherIITextureMappings.doorBottom(block);
        TextureMapping topMapping = AetherIITextureMappings.doorTop(block);
        MultiVariant left = plainVariant(AetherIIModelTemplates.DOOR_BOTTOM_LEFT.create(block, bottomMapping, this.modelOutput));
        MultiVariant bottomLeftOpen = plainVariant(AetherIIModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, bottomMapping, this.modelOutput));
        MultiVariant bottomRight = plainVariant(AetherIIModelTemplates.DOOR_BOTTOM_RIGHT.create(block, bottomMapping, this.modelOutput));
        MultiVariant bottomRightOpen = plainVariant(AetherIIModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, bottomMapping, this.modelOutput));
        MultiVariant topLeft = plainVariant(AetherIIModelTemplates.DOOR_TOP_LEFT.create(block, topMapping, this.modelOutput));
        MultiVariant topLeftOpen = plainVariant(AetherIIModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, topMapping, this.modelOutput));
        MultiVariant topRight = plainVariant(AetherIIModelTemplates.DOOR_TOP_RIGHT.create(block, topMapping, this.modelOutput));
        MultiVariant topRightOpen = plainVariant(AetherIIModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, topMapping, this.modelOutput));
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(createDoor(block, left, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
    }

    @Override
    public void createOrientableTrapdoor(Block block) {
        TextureMapping mapping = TextureMapping.defaultTexture(block);
        ResourceLocation location = ModelTemplates.TRAPDOOR_TOP.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        ResourceLocation locationBottom = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        ResourceLocation locationOpen = ModelTemplates.TRAPDOOR_OPEN.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(block, plainVariant(location), plainVariant(locationBottom), plainVariant(locationOpen)));
        this.registerSimpleItemModel(block, locationBottom);
    }

    public void createCutoutMippedCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_MIPPED_CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public void createTranslucentCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(AetherIIModelTemplates.TEMPLATE_TRANSLUCENT_CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public void createTranslucentCubeInnerFaces(Block block) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(block, mapping, this.modelOutput))));
    }

    public ResourceLocation createTranslucentItemModelWithBlockTexture(Item item, Block block) {
        return AetherIIModelTemplates.TRANSLUCENT_FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), this.modelOutput);
    }

    public void createCubeColumn(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        MultiVariant variant = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, variant));
    }

    public void createFacingColumnWithHorizontalVariant(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        MultiVariant vertical = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput));
        MultiVariant horizontal = plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(side, mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, vertical, horizontal));
    }

    public void createFacingTopBottomColumnWithHorizontalVariant(Block side, Block top, Block bottom) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(side))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bottom, "_top"));
        MultiVariant verticalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(side, mapping, this.modelOutput));
        MultiVariant horizontalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(side, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public void createFacingTopBottomColumnWithHorizontalVariantGeneric(Block side, Block top, Block bottom) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(side))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bottom));
        MultiVariant verticalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(side, mapping, this.modelOutput));
        MultiVariant horizontalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(side, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public void createFacingTopBottomColumnWithHorizontalVariantGeneric(Block side, Block top, String suffix, Block bottom) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(side))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top, suffix))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bottom));
        MultiVariant verticalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(side, mapping, this.modelOutput));
        MultiVariant horizontalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(side, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public void createFacingTopBottomColumnWithHorizontalVariantGeneric(Block side, Block top, Block bottom, String suffix) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(side))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bottom, suffix));
        MultiVariant verticalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(side, mapping, this.modelOutput));
        MultiVariant horizontalLocation = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(side, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public static BlockModelDefinitionGenerator createFacingColumnWithHorizontalVariant(Block block, MultiVariant vertical, MultiVariant horizontal) {
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FacingPillarBlock.FACING)
                        .select(Direction.UP, vertical)
                        .select(Direction.DOWN, vertical.with(X_ROT_180))
                        .select(Direction.NORTH, horizontal.with(X_ROT_90))
                        .select(Direction.SOUTH, horizontal.with(X_ROT_90).with(Y_ROT_180))
                        .select(Direction.EAST, horizontal.with(X_ROT_90).with(Y_ROT_90))
                        .select(Direction.WEST, horizontal.with(X_ROT_90).with(Y_ROT_270))
        );
    }

    public void createAetherPortalBlock() {
        MultiVariant locationNS = plainVariant(AetherIIModelTemplates.PORTAL_NS.create(AetherIIBlocks.AETHER_PORTAL.get(), AetherIITextureMappings.portal(AetherIIBlocks.AETHER_PORTAL.get()), this.modelOutput));
        MultiVariant locationEW = plainVariant(AetherIIModelTemplates.PORTAL_EW.create(AetherIIBlocks.AETHER_PORTAL.get(), AetherIITextureMappings.portal(AetherIIBlocks.AETHER_PORTAL.get()), this.modelOutput));

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AETHER_PORTAL.get()).with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS)
                .select(Direction.Axis.X, locationNS)
                .select(Direction.Axis.Z, locationEW)));
    }

    public void createAetherGrassBlocks() {
        TextureMapping snowMapping = AetherIITextureMappings.snowyGrass(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get());
        MultiVariant snow = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), "_snow", snowMapping, this.modelOutput));
        this.createTintedGrassBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), snow);

        MultiVariant enchantedGrass = plainVariant(TexturedModel.CUBE_TOP_BOTTOM.get(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())
                .updateTextures((mapping) -> mapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_DIRT.get()))).create(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), this.modelOutput));
        this.createGrassLikeBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), enchantedGrass, snow);

        Variant dirtPath = plainModel(AetherIIModelTemplates.DIRT_PATH.create(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIITextureMappings.dirtPath(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get()), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AETHER_DIRT_PATH.get(), createRotatedVariants(dirtPath)));
    }

    public void createTintedGrassBlock(Block block, MultiVariant snowyVariant) {
        ResourceLocation model = AetherIIModelTemplates.TINTED_GRASS.create(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIITextureMappings.tintedGrass(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get()),
                this.modelOutput
        );
        MultiVariant variant = BlockModelGenerators.createRotatedVariants(plainModel(model));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(BlockStateProperties.SNOWY).select(true, snowyVariant).select(false, variant))
        );
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(model,
                new AetherGrassColorSource(0, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F),
                new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F),
                new AetherGrassColorSource(2, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F)
        ));
    }

    public void createAetherFarmland() {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_DIRT.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_FARMLAND.get()));
        TextureMapping mappingMoist = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_DIRT.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_FARMLAND.get(), "_moist"));
        MultiVariant farmland = plainVariant(ModelTemplates.FARMLAND.create(AetherIIBlocks.AETHER_FARMLAND.get(), mapping, this.modelOutput));
        MultiVariant farmlandMoist = plainVariant(ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_FARMLAND.get(), "_moist"), mappingMoist, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AETHER_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, farmlandMoist, farmland)));
    }

    public void createGlassBlocks(Block glass, Block pane) {
        this.createTranslucentCube(glass);
        TextureMapping mapping = TextureMapping.pane(glass, pane);
        MultiVariant post = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput));
        MultiVariant noSide = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput));
        MultiVariant noSideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput));
        Item item = pane.asItem();
        this.registerSimpleItemModel(item, this.createTranslucentItemModelWithBlockTexture(item, glass));
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(pane)
                .with(post).with(condition().term(BlockStateProperties.NORTH, true), side)
                .with(condition().term(BlockStateProperties.EAST, true), side.with(Y_ROT_90))
                .with(condition().term(BlockStateProperties.SOUTH, true), sideAlt)
                .with(condition().term(BlockStateProperties.WEST, true), sideAlt.with(Y_ROT_90))
                .with(condition().term(BlockStateProperties.NORTH, false), noSide)
                .with(condition().term(BlockStateProperties.EAST, false), noSideAlt)
                .with(condition().term(BlockStateProperties.SOUTH, false), noSideAlt.with(Y_ROT_90))
                .with(condition().term(BlockStateProperties.WEST, false), noSide.with(Y_ROT_270)));
    }

    public void createPointedStone(Block block) {
        PropertyDispatch.C2<MultiVariant, Direction, DripstoneThickness> properties = PropertyDispatch.initial(BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS);

        for (DripstoneThickness thicknessUp : DripstoneThickness.values()) {
            properties.select(Direction.UP, thicknessUp, this.createPointedStoneVariant(block, Direction.UP, thicknessUp));
        }
        for (DripstoneThickness thicknessDown : DripstoneThickness.values()) {
            properties.select(Direction.DOWN, thicknessDown, this.createPointedStoneVariant(block, Direction.DOWN, thicknessDown));
        }

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(properties));
        this.registerSimpleItemModel(block.asItem(), AetherIIModelTemplates.POINTED_STONE.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    public MultiVariant createPointedStoneVariant(Block block, Direction direction, DripstoneThickness thickness) {
        String name = "_" + direction.getSerializedName() + "_" + thickness.getSerializedName();
        TextureMapping mapping = TextureMapping.cross(TextureMapping.getBlockTexture(block, name));
        return plainVariant(AetherIIModelTemplates.POINTED_STONE_BLOCK.createWithSuffix(block, name, mapping, this.modelOutput));
    }

    public void createVine(Block block, ModelTemplate template) {
        MultiVariant normal = plainVariant(template.create(block, AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block)), this.modelOutput));
        MultiVariant bottom = plainVariant(template.create(ModelLocationUtils.getModelLocation(block, "_bottom"), AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block, "_bottom")), this.modelOutput));
        Map<Property<Boolean>, VariantMutator> map = selectMultifaceProperties(block.defaultBlockState(), MultifaceBlock::getFaceProperty);
        ConditionBuilder builder = condition();
        map.forEach((bool, mutator) -> builder.term(bool, false));
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        map.forEach((bool, mutator) -> {
            generator
                    .with(condition().term(BottomedVineBlock.AGE, 0, ArrayUtils.toObject(IntStream.range(1, 25).toArray())).term(bool, true), normal.with(mutator))
                    .with(condition().term(BottomedVineBlock.AGE, 25).term(bool, true), bottom.with(mutator));
            generator.with(builder, normal.with(mutator));
        });
        this.blockStateOutput.accept(generator);
        this.registerSimpleFlatItemModel(block);
    }

    public void createCrystal(Block block, ModelTemplate itemModel) {
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput))).with(ROTATIONS_COLUMN_WITH_FACING));
        this.registerSimpleItemModel(block.asItem(), itemModel.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    public void createCorroboniteCluster(Block block, ModelTemplate itemModel) {
        MultiVariant multivariant = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
        MultiPartGenerator multipartgenerator = MultiPartGenerator.multiPart(block)
                .with(condition().term(BlockStateProperties.UP, true), multivariant.with(X_ROT_180))
                .with(condition().term(BlockStateProperties.DOWN, true), multivariant.with(NOP))
                .with(condition().term(BlockStateProperties.SOUTH, true), multivariant.with(X_ROT_90))
                .with(condition().term(BlockStateProperties.NORTH, true), multivariant.with(X_ROT_90.then(Y_ROT_180)))
                .with(condition().term(BlockStateProperties.EAST, true), multivariant.with(X_ROT_90.then(Y_ROT_270)))
                .with(condition().term(BlockStateProperties.WEST, true), multivariant.with(X_ROT_90.then(Y_ROT_90)));
        this.blockStateOutput.accept(multipartgenerator);
        this.registerSimpleItemModel(block.asItem(), itemModel.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    protected void createArcticSnowBlocks() {
        TextureMapping mapping = TextureMapping.cube(AetherIIBlocks.ARCTIC_SNOW.get());
        ResourceLocation snowBlockLocation = ModelTemplates.CUBE_ALL.create(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.ARCTIC_SNOW.get()).with(PropertyDispatch.initial(BlockStateProperties.LAYERS).generate((i) -> {
            ResourceLocation location;
            if (i < 8) {
                Block block = AetherIIBlocks.ARCTIC_SNOW.get();
                int layers = i * 2;
                location = ModelLocationUtils.getModelLocation(block, "_height" + layers);
                AetherIIModelTemplates.THIN.extend()
                        .element(elementBuilder -> elementBuilder.from(0.0F, 0.0F, 0.0F).to(16.0F, (float) layers, 16.0F)
                                .face(Direction.DOWN, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.UP, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.ALL)))
                        .build().create(location, AetherIITextureMappings.particle(mapping), this.modelOutput);
            } else {
                location = snowBlockLocation;
            }
            return plainVariant(location);
        })));
        this.registerSimpleItemModel(AetherIIBlocks.ARCTIC_SNOW.get(), ModelLocationUtils.getModelLocation(AetherIIBlocks.ARCTIC_SNOW.get(), "_height2"));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), plainVariant(snowBlockLocation)));
    }

    public WoodProvider woodProviderColumn(Block side, Block top) {
        return new WoodProvider(TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top, "_top")));
    }

    public void createAercloud(Block block) {
        this.createTranslucentCubeInnerFaces(block);
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(AetherIIModelTemplates.TEMPLATE_TRANSLUCENT_CUBE_ALL.create(block.asItem(), TextureMapping.cube(block), this.modelOutput)));
    }

    public void createPurpleAercloud(Block block) {
        TextureMapping leftMapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_back"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_front"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_back"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_left"));
        TextureMapping rightMapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_back"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_front"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_back"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_right"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_right"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_right"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_right"));

        MultiVariant left = plainVariant(AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(ModelLocationUtils.getModelLocation(block, "_left"), leftMapping, this.modelOutput));
        MultiVariant right = plainVariant(AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(ModelLocationUtils.getModelLocation(block, "_right"), rightMapping, this.modelOutput));

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(PurpleAercloudBlock.FACING).generate((direction) -> 
            switch(direction) {
                case NORTH -> left.with(X_ROT_90);
                case SOUTH -> right.with(X_ROT_270);
                case WEST -> left.with(X_ROT_270).with(Y_ROT_90);
                case EAST -> right.with(X_ROT_90).with(Y_ROT_90);
                default -> left;
            }
        )));
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(AetherIIModelTemplates.TEMPLATE_TRANSLUCENT_CUBE.create(block.asItem(), rightMapping, this.modelOutput)));
    }

    public void createCustomFlowerBed(Block block, ResourceLocation flowerbed1, ResourceLocation flowerbed2, ResourceLocation flowerbed3, ResourceLocation flowerbed4) {
        MultiVariant multivariant = plainVariant(flowerbed1);
        MultiVariant multivariant1 = plainVariant(flowerbed2);
        MultiVariant multivariant2 = plainVariant(flowerbed3);
        MultiVariant multivariant3 = plainVariant(flowerbed4);
        this.registerSimpleFlatItemModel(block.asItem());
        this.createSegmentedBlock(
                block,
                multivariant,
                FLOWER_BED_MODEL_1_SEGMENT_CONDITION,
                multivariant1,
                FLOWER_BED_MODEL_2_SEGMENT_CONDITION,
                multivariant2,
                FLOWER_BED_MODEL_3_SEGMENT_CONDITION,
                multivariant3,
                FLOWER_BED_MODEL_4_SEGMENT_CONDITION
        );
    }

    public void createWovenSticks(Block sticks) {
        MultiVariant cube = plainVariant(TexturedModel.CUBE.create(sticks, this.modelOutput));
        MultiVariant bryalinn = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(sticks, "_bryalinn"), AetherIITextureMappings.mossyTopped(sticks, AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), "bryalinn"), this.modelOutput));
        MultiVariant shayelinn = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(sticks, "_shayelinn"), AetherIITextureMappings.mossyTopped(sticks, AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), "shayelinn"), this.modelOutput));
        MultiVariant ambrelinn = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(sticks, "_ambrelinn"), AetherIITextureMappings.mossyTopped(sticks, AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), "ambrelinn"), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(sticks).with(PropertyDispatch.initial(AetherLeavesBlock.MOSSY).generate((mossy) -> {
            switch(mossy) {
                case BRYALINN -> {
                    return bryalinn;
                }
                case SHAYELINN -> {
                    return shayelinn;
                }
                case AMBRELINN -> {
                    return ambrelinn;
                }
                default -> {
                    return cube;
                }
            }
        })));
    }

    public void createLeavesWithPiles(Block leaves, Block piles) {
        MultiVariant cube = plainVariant(AetherIITexturedModels.LEAVES.create(leaves, this.modelOutput));
        MultiVariant snowy = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(leaves, "_snowy"), AetherIITextureMappings.snowyLeaves(leaves), this.modelOutput));
        MultiVariant bryalinn = plainVariant(AetherIIModelTemplates.CUBE_TOP_BOTTOM_INNER_TOP.create(ModelLocationUtils.getModelLocation(leaves, "_bryalinn"), AetherIITextureMappings.mossyTopped(leaves, AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), "bryalinn"), this.modelOutput));
        MultiVariant shayelinn = plainVariant(AetherIIModelTemplates.CUBE_TOP_BOTTOM_INNER_TOP.create(ModelLocationUtils.getModelLocation(leaves, "_shayelinn"), AetherIITextureMappings.mossyTopped(leaves, AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), "shayelinn"), this.modelOutput));
        MultiVariant ambrelinn = plainVariant(AetherIIModelTemplates.CUBE_TOP_BOTTOM_INNER_TOP.create(ModelLocationUtils.getModelLocation(leaves, "_ambrelinn"), AetherIITextureMappings.mossyTopped(leaves, AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), "ambrelinn"), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(leaves)
                .with(PropertyDispatch.initial(AetherLeavesBlock.SNOWY, AetherLeavesBlock.MOSSY).generate((snowyState, mossyState) -> {
                    if (snowyState) {
                        return snowy;
                    } else {
                        switch(mossyState) {
                            case BRYALINN -> {
                                return bryalinn;
                            }
                            case SHAYELINN -> {
                                return shayelinn;
                            }
                            case AMBRELINN -> {
                                return ambrelinn;
                            }
                            default -> {
                                return cube;
                            }
                        }
                    }
                }))
        );
        this.createPiles(piles, leaves);
    }

    public void createTintedLeavesWithPiles(Block leaves, Block piles) {
        this.createTrivialBlock(leaves, AetherIITexturedModels.TINTED_LEAVES);
        this.createPiles(piles, leaves);
    }

    public void createPiles(Block piles, Block leaves) {
        TextureMapping textureMapping = AetherIITextureMappings.particle(TextureMapping.cube(leaves));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(piles).with(PropertyDispatch.initial(AetherLeafPileBlock.PILES).generate((i) -> {
            ResourceLocation location;
            if (i < 16) {
                int layers = i;
                location = ModelLocationUtils.getModelLocation(piles, "_height" + layers);
                AetherIIModelTemplates.THIN.extend()
                        .ambientOcclusion(layers == 1)
                        .renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
                        .element(elementBuilder -> elementBuilder.from(0.0F, 0.0F, 0.0F).to(16.0F, (float) layers, 16.0F)
                                .face(Direction.DOWN, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.UP, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.ALL))
                                .face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.ALL)))
                        .build().create(location, textureMapping, this.modelOutput);
            } else {
                location = ModelLocationUtils.getModelLocation(leaves);
            }
            return plainVariant(location);
        })));
        this.registerSimpleItemModel(piles, ModelLocationUtils.getModelLocation(piles, "_height1"));
    }

    public void createCrossWithDefaultItem(Block block, PlantType type) {
        this.registerSimpleItemModel(block.asItem(), type.createItemModel(this, block));
        this.createCrossBlock(block, type);
    }

    public void createSnowyPlantWithDefaultItem(Block plant, Block pot) {
        this.createSnowyCross(plant);

        TextureMapping plantMapping = TextureMapping.plant(plant);
        MultiVariant crossPot = plainVariant(ModelTemplates.FLOWER_POT_CROSS.extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(pot, plantMapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));
    }

    public void createSnowyCross(Block block) {
        this.registerSimpleFlatItemModel(block);
        MultiVariant cross = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(block, "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block) //, plainVariant(ModelLocationUtils.getModelLocation(block.asItem()))
                .with(createBooleanModelDispatch(BlockStateProperties.SNOWY, snowy, cross)));
    }

    public void createAsymmetricalPlantWithDefaultItem(Block block, TexturedModel.Provider provider, TexturedModel.Provider mirroredProvider, Block pot, ModelTemplate potTemplate) {
        this.registerSimpleFlatItemModel(block);

        Variant normal = plainModel(provider.create(block, this.modelOutput));
        Variant mirrored = plainModel(mirroredProvider.create(block, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, createRotatedVariants(normal, mirrored)));

        MultiVariant crossPot = plainVariant(potTemplate.create(pot, AetherIITextureMappings.asymmetricalCross(block), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));
    }

    public void createUniquePlantWithDefaultItem(Block block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate) {
        createUniquePlantWithDefaultItem(block, provider, pot, potTemplate, TextureMapping::plant);
    }

    public <B extends Block> void createUniquePlantWithDefaultItem(B block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate, Function<? super B, ? extends TextureMapping> potTextureMappingCreator) {
        this.registerSimpleFlatItemModel(block.asItem());

        Variant normal = plainModel(provider.create(block, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, createRotatedVariants(normal)));

        MultiVariant crossPot = plainVariant(potTemplate.create(pot, potTextureMappingCreator.apply(block), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));
    }

    public void createFacingPlantWithDefaultItem(Block block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate) {
        this.createFacingPlantWithDefaultItem(block, provider, pot, potTemplate, TextureMapping::plant);
    }

    public <B extends Block> void createFacingPlantWithDefaultItem(B block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate, Function<? super B, ? extends TextureMapping> potTextureMappingCreator) {
        this.registerSimpleFlatItemModel(block.asItem());

        MultiVariant normal = plainVariant(provider.create(block, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, normal).with(ROTATION_HORIZONTAL_FACING));

        MultiVariant crossPot = plainVariant(potTemplate.create(pot, potTextureMappingCreator.apply(block), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));
    }

    public void createTintedTallGrass(Block block) {
        MultiVariant plant = plainVariant(AetherIIModelTemplates.TINTED_TALL_GRASS.create(block, AetherIITextureMappings.tintedTallGrass(block), this.modelOutput));
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(block, "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross));
        MultiVariant enchanted = plainVariant(this.createSuffixedVariant(block, "_enchanted", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> plant;
            case SNOWY -> snowy;
            case ENCHANTED -> enchanted;
        })));

        ResourceLocation itemLocation = this.createFlatItemModelWithBlockTexture(block.asItem(), block);
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(itemLocation,
                new AetherGrassColorSource(0, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(2, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F)
        ));
    }

    public void createHighlandFern() {
        MultiVariant plant = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_TINTED_CROSS.create(AetherIIBlocks.HIGHLAND_FERN.get(), TextureMapping.cross(AetherIIBlocks.HIGHLAND_FERN.get()), this.modelOutput));
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(AetherIIBlocks.HIGHLAND_FERN.get(), "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross));
        MultiVariant enchanted = plainVariant(this.createSuffixedVariant(AetherIIBlocks.HIGHLAND_FERN.get(), "_enchanted", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.HIGHLAND_FERN.get()).with(PropertyDispatch.initial(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> plant;
            case SNOWY -> snowy;
            case ENCHANTED -> enchanted;
        })));

        MultiVariant crossPot = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_TINTED_FLOWERPOT_CROSS.create(AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), TextureMapping.plant(AetherIIBlocks.HIGHLAND_FERN.get()), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), crossPot));

        ResourceLocation itemLocation = this.createFlatItemModelWithBlockTexture(AetherIIBlocks.HIGHLAND_FERN.asItem(), AetherIIBlocks.HIGHLAND_FERN.get());
        this.registerSimpleTintedItemModel(AetherIIBlocks.HIGHLAND_FERN.get(), itemLocation, new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F));
    }

    public void createBush(Block block, Block pot) {
        MultiVariant bush = plainVariant(AetherIIModelTemplates.BUSH_BLOCK.create(block, AetherIITextureMappings.bushBlock(block), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, bush));

        MultiVariant bushPot = plainVariant(AetherIIModelTemplates.POTTED_BUSH_BLOCK.create(pot, AetherIITextureMappings.pottedBushBlock(pot), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, bushPot));
    }

    public void createOrangeTree(Block block, Block pot) {
        List<ResourceLocation> existing = new ArrayList<>();
        PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(OrangeTreeBlock.HALF, OrangeTreeBlock.AGE).generate((half, age) -> {
            boolean lower = half == DoubleBlockHalf.LOWER;
            int bottomAge = age == 3 ? 2 : age;
            int topAge = Math.max(age, 2);
            String halfString = lower ? "_bottom_" : "_top_";
            ResourceLocation location = lower ? ModelLocationUtils.getModelLocation(block, halfString + bottomAge) : ModelLocationUtils.getModelLocation(block, halfString + topAge);
            if (!existing.contains(location)) {
                ResourceLocation model = AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(location, TextureMapping.cross(location), this.modelOutput);
                existing.add(location);
                return plainVariant(model);
            } else {
                return plainVariant(location);
            }
        });
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertyDispatch));

        MultiVariant crossPot = plainVariant(ModelTemplates.FLOWER_POT_CROSS.extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build()
                .create(pot, TextureMapping.plant(TextureMapping.getBlockTexture(block, "_bottom_0")), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));

        this.registerSimpleFlatItemModel(block, "_bottom_0");
    }

    public void createValkyrieSprout() {
        PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(ValkyrieSproutBlock.AGE).generate(age -> {
            ResourceLocation location = this.createSuffixedVariant(AetherIIBlocks.VALKYRIE_SPROUT.get(), "_stage" + age, AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
            return plainVariant(location);
        });
        this.registerSimpleFlatItemModel(AetherIIBlocks.VALKYRIE_SPROUT.get(),"_stage0");
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.VALKYRIE_SPROUT.get()).with(propertyDispatch));
    }

    public void createBrettlPlant(Block block) {
        MultiVariant normal = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
        MultiVariant grown = plainVariant(AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(ModelLocationUtils.getModelLocation(block, "_grown"), TextureMapping.cross(TextureMapping.getBlockTexture(block, "_grown")), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(BrettlPlantBlock.GROWN, grown, normal)));
    }

    public void createTwig(Block twig, Block base) {
        TextureMapping mapping = TextureMapping.logColumn(base);
        MultiVariant twigs1 = plainVariant(AetherIIModelTemplates.TWIG_1.create(twig, mapping, this.modelOutput));
        MultiVariant twigs2 = plainVariant(AetherIIModelTemplates.TWIG_2.create(twig, mapping, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(twig).with(PropertyDispatch.initial(TwigBlock.AMOUNT).generate((amount) -> {
            if (amount == 2) {
                return twigs2;
            } else {
                return twigs1;
            }
        })).with(ROTATION_HORIZONTAL_FACING));
        this.registerSimpleFlatItemModel(twig.asItem());
    }

    public void createRock(Block rock, Block base) {
        TextureMapping mapping = TextureMapping.cube(base);
        MultiVariant rock1 = plainVariant(AetherIIModelTemplates.ROCK_1.create(rock, mapping, this.modelOutput));
        MultiVariant rock2 = plainVariant(AetherIIModelTemplates.ROCK_2.create(rock, mapping, this.modelOutput));
        MultiVariant rock3 = plainVariant(AetherIIModelTemplates.ROCK_3.create(rock, mapping, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(rock).with(PropertyDispatch.initial(RockBlock.AMOUNT).generate((amount) -> {
            if (amount == 3) {
                return rock3;
            } else if (amount == 2) {
                return rock2;
            } else {
                return rock1;
            }
        })).with(ROTATION_HORIZONTAL_FACING));
        this.registerSimpleFlatItemModel(rock.asItem());
    }

    public void createLockedDungeonBlock(Block baseBlock, Block block) {
        MultiVariant dungeonBlock = plainVariant(ModelLocationUtils.getModelLocation(baseBlock));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, dungeonBlock));
        this.registerSimpleItemModel(block.asItem(), AetherIIModelTemplates.LOCKED_BLOCK_INVENTORY.create(block.asItem(), AetherIITextureMappings.lockedBlockInventory(baseBlock), this.modelOutput));
    }

    public void createLockedDungeonBlock(Block baseBlock, Block itemBlock, Block block) {
        MultiVariant dungeonBlock = plainVariant(ModelLocationUtils.getModelLocation(baseBlock));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, dungeonBlock));
        this.registerSimpleItemModel(block.asItem(), AetherIIModelTemplates.LOCKED_BLOCK_INVENTORY.create(block.asItem(), AetherIITextureMappings.lockedBlockInventory(itemBlock), this.modelOutput));
    }
    
    public void createCornerLog(Block baseBlock, Block block) {
        TextureMapping mapping = (new TextureMapping())
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(baseBlock))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(baseBlock))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_right"));
        MultiVariant vertical = plainVariant(ModelTemplates.CUBE.create(block, mapping, this.modelOutput));
        MultiVariant horizontal = plainVariant(ModelTemplates.CUBE.create(ModelLocationUtils.getModelLocation(block, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(block, vertical, horizontal));
    }

    public void createCornerLog(Block baseBlock, Block top, Block block) {
        TextureMapping mapping = (new TextureMapping())
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(baseBlock))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(top, "_top"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(top, "_top"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_left"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(baseBlock))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_right"));
        MultiVariant vertical = plainVariant(ModelTemplates.CUBE.create(block, mapping, this.modelOutput));
        MultiVariant horizontal = plainVariant(ModelTemplates.CUBE.create(ModelLocationUtils.getModelLocation(block, "_horizontal"), mapping, this.modelOutput));
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(block, vertical, horizontal));
    }

    public void createUndergrowthVines(Block block) {
        MultiVariant vines = plainVariant(AetherIIModelTemplates.UNDERGROWTH_VINES.create(block, AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, vines));
    }

    public void createRotshroomCluster(Block block) {
        MultiVariant shroom = plainVariant(AetherIIModelTemplates.ROTSHROOM_CLUSTER.create(block, TextureMapping.cube(block).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem())), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom));
        this.registerSimpleFlatItemModel(block.asItem());
    }

    public void createRotshroomToadstoolCluster(Block block) {
        MultiVariant shroom = plainVariant(AetherIIModelTemplates.ROTSHROOM_TOADSTOOL_CLUSTER.create(block, TextureMapping.cubeBottomTop(block).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem())), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom).with(ROTATION_HORIZONTAL_FACING));
        this.registerSimpleFlatItemModel(block.asItem());
    }

    public void createRotshroomToadstool(Block block) {
        MultiVariant shroom = plainVariant(AetherIIModelTemplates.ROTSHROOM_TOADSTOOL.create(block, TextureMapping.cubeBottomTop(block).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(block.asItem())), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom));
        this.registerSimpleFlatItemModel(block.asItem());
    }

    public void createShelfRotshroom(Block block, Item particle) {
        MultiVariant shroom = plainVariant(AetherIIModelTemplates.SHELF_ROTSHROOM.create(block, TextureMapping.cube(block).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom).with(ROTATION_HORIZONTAL_FACING));
    }

    public void createShelfRotshroomBlock(Block block, Item particle) {
        MultiVariant shroom = plainVariant(AetherIIModelTemplates.SHELF_ROTSHROOM_BLOCK.create(block, TextureMapping.cubeBottomTop(block).put(TextureSlot.PARTICLE, TextureMapping.getItemTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom));
    }

    @Override
    public void createMossyCarpet(Block block) {
        MultiVariant normal = plainVariant(AetherIITexturedModels.CARPET_CUTOUT.create(block, this.modelOutput));
        MultiVariant tall = plainVariant(AetherIITexturedModels.MOSSY_CARPET_SIDE_CUTOUT.get(block).updateTextures((mapping) -> mapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_tall"))).createWithSuffix(block, "_side_tall", this.modelOutput));
        MultiVariant small = plainVariant(AetherIITexturedModels.MOSSY_CARPET_SIDE_CUTOUT.get(block).updateTextures((mapping) -> mapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_small"))).createWithSuffix(block, "_side_small", this.modelOutput));

        Map<Property<WallSide>, VariantMutator> map = selectMultifaceProperties(block.defaultBlockState(), MossyCarpetBlock::getPropertyForFace);
        ConditionBuilder builder = condition().term(MossyCarpetBlock.BASE, false);
        map.forEach((wallSide, mutator) -> builder.term(wallSide, WallSide.NONE));
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        generator.with(condition().term(MossyCarpetBlock.BASE, true), normal);
        generator.with(builder, normal);
        map.forEach((wallSide, mutator) -> {
            generator.with(condition().term(wallSide, WallSide.TALL), tall.with(mutator));
            generator.with(condition().term(wallSide, WallSide.LOW), small.with(mutator));
            generator.with(builder, tall.with(mutator));
        });

        this.blockStateOutput.accept(generator);
    }

    public void createSecretDoor(Block block, Block base) {
        TextureMapping mapping = TextureMapping.door(TextureMapping.getBlockTexture(base), TextureMapping.getBlockTexture(base));
        MultiVariant bottomLeft = plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(block, mapping, this.modelOutput));
        MultiVariant bottomLeftOpen = plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, mapping, this.modelOutput));
        MultiVariant right = plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(block, mapping, this.modelOutput));
        MultiVariant bottomRightOpen = plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, mapping, this.modelOutput));
        MultiVariant topLeft = plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(block, mapping, this.modelOutput));
        MultiVariant topLeftOpen = plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, mapping, this.modelOutput));
        MultiVariant topRight = plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(block, mapping, this.modelOutput));
        MultiVariant topRightOpen = plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, mapping, this.modelOutput));
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(createDoor(block, bottomLeft, bottomLeftOpen, right, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
    }

    public void createOrientableSecretTrapdoor(Block block, Block base) {
        TextureMapping mapping = TextureMapping.defaultTexture(base);
        ResourceLocation top = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_TOP.create(block, mapping, this.modelOutput);
        ResourceLocation bottom = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_BOTTOM.create(block, mapping, this.modelOutput);
        ResourceLocation open = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_OPEN.create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(block, plainVariant(top), plainVariant(bottom), plainVariant(open)));
        this.registerSimpleItemModel(block, bottom);
    }

    public void createAmbrosiumTorch() {
        TextureMapping mapping = TextureMapping.torch(AetherIIBlocks.AMBROSIUM_TORCH.get());
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.AMBROSIUM_TORCH.get(), plainVariant(AetherIIModelTemplates.TALL_TORCH.create(AetherIIBlocks.AMBROSIUM_TORCH.get(), mapping, this.modelOutput))));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AMBROSIUM_WALL_TORCH.get(), plainVariant(AetherIIModelTemplates.TALL_WALL_TORCH.create(AetherIIBlocks.AMBROSIUM_WALL_TORCH.get(), mapping, this.modelOutput))).with(ROTATION_TORCH));
        this.registerSimpleFlatItemModel(AetherIIBlocks.AMBROSIUM_TORCH.get());
    }

    public void createArkeniumLantern() {
        MultiVariant lantern = plainVariant(AetherIITexturedModels.ARKENIUM_LANTERN.create(AetherIIBlocks.ARKENIUM_LANTERN.get(), this.modelOutput));
        MultiVariant hangingLantern = plainVariant(AetherIITexturedModels.HANGING_ARKENIUM_LANTERN.create(AetherIIBlocks.ARKENIUM_LANTERN.get(), this.modelOutput));
        this.registerSimpleFlatItemModel(AetherIIBlocks.ARKENIUM_LANTERN.get().asItem());
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.ARKENIUM_LANTERN.get()).with(createBooleanModelDispatch(BlockStateProperties.HANGING, hangingLantern, lantern)));
    }

    public void createRusticArkeniumLantern() {
        MultiVariant lantern = plainVariant(AetherIITexturedModels.RUSTIC_ARKENIUM_LANTERN.create(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get(), this.modelOutput));
        MultiVariant hangingLantern = plainVariant(AetherIITexturedModels.HANGING_RUSTIC_ARKENIUM_LANTERN.create(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get(), this.modelOutput));
        this.registerSimpleFlatItemModel(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get().asItem());
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.RUSTIC_ARKENIUM_LANTERN.get()).with(createBooleanModelDispatch(BlockStateProperties.HANGING, hangingLantern, lantern)));
    }

    public void createAltar(Block block, Block particle) {
        ResourceLocation location = AetherIIModelTemplates.ALTAR.create(block, AetherIITextureMappings.altar(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        ResourceLocation chargingLocation = AetherIIModelTemplates.ALTAR.create(ModelLocationUtils.getModelLocation(block, "_charging"), AetherIITextureMappings.altar(block, "_charging").put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        ResourceLocation blastingLocation = AetherIIModelTemplates.ALTAR.create(ModelLocationUtils.getModelLocation(block, "_blasting"), AetherIITextureMappings.altar(block, "_blasting").put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AltarBlock.BLASTING, AltarBlock.CHARGING)
                        .select(true, true, plainVariant(blastingLocation))
                        .select(true, false, plainVariant(blastingLocation))
                        .select(false, true, plainVariant(chargingLocation))
                        .select(false, false, plainVariant(location))
                ).with(ROTATION_HORIZONTAL_FACING)
        );
    }

    public void createArtisansBench(Block block, Block particle) {
        MultiVariant crafter = plainVariant(AetherIIModelTemplates.ARTISANS_BENCH.create(block, AetherIITextureMappings.artisansBench(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, crafter)
                .with(ROTATION_HORIZONTAL_FACING));
    }

    public void createArkeniumForge(Block block, Block particle) {
        MultiVariant normal = plainVariant(AetherIIModelTemplates.ARKENIUM_FORGE.create(block, AetherIITextureMappings.arkeniumForge(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        MultiVariant charged = plainVariant(AetherIIModelTemplates.ARKENIUM_FORGE.create(ModelLocationUtils.getModelLocation(block, "_charged"), AetherIITextureMappings.arkeniumForge(block, "_charged").put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(BlockModelGenerators.createBooleanModelDispatch(ArkeniumForgeBlock.CHARGED, charged, normal))
                .with(ROTATION_HORIZONTAL_FACING));
    }

    public void createAlkahestPurifier(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        ResourceLocation resourceLocation = AetherIIModelTemplates.ALKAHEST_PURIFIER_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new AlkahestPurifierSpecialRenderer.Unbaked());
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createLadder(Block block) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block)).copySlot(TextureSlot.TEXTURE, TextureSlot.PARTICLE);
        MultiVariant ladder = plainVariant(AetherIIModelTemplates.LADDER.create(block, mapping, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, ladder).with(ROTATION_HORIZONTAL_FACING));
        this.registerSimpleFlatItemModel(block);
    }

    public void createBed(Block block, Block particle, ResourceLocation location) {
        MultiVariant bed = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("skyroot_bed"));
        this.blockStateOutput.accept(createSimpleBlock(block, bed));
        Item item = block.asItem();
        ResourceLocation inventoryLocation = ModelTemplates.BED_INVENTORY.create(ModelLocationUtils.getModelLocation(item), TextureMapping.particle(particle), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.specialModel(inventoryLocation, new BedSpecialRenderer.Unbaked(location)));
    }

    public void createArilumLantern(Block block) {
        MultiVariant lantern = plainVariant(AetherIIModelTemplates.ARILUM_LANTERN.create(block, TextureMapping.cube(block).put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(block, "_inside")), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, lantern));
    }

    public void createMoaEgg(Block block) {
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(AetherIIModelTemplates.EMPTY.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get())), this.modelOutput))));
    }

    public void createOutpostCampfire() {
        final TextureSlot[] textureSlots = {AetherIITextureSlots.LOGS, AetherIITextureSlots.BRICKS, AetherIITextureSlots.ASH};
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.OUTPOST_CAMPFIRE.get()).with(PropertyDispatch.initial(OutpostCampfireBlock.PART_FACING).generate(facing -> {
            ResourceLocation model = AetherIIModelTemplates.create("template_outpost_campfire_" + facing.name().toLowerCase(Locale.ROOT), "_" + facing.name().toLowerCase(Locale.ROOT), textureSlots)
                    .extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build()
                    .create(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), new TextureMapping()
                            .put(AetherIITextureSlots.LOGS, TextureMapping.getBlockTexture(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "_logs"))
                            .put(AetherIITextureSlots.BRICKS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/large_holystone_bricks"))
                            .put(AetherIITextureSlots.ASH, TextureMapping.getBlockTexture(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "_ash"))
                            .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.HOLYSTONE_BRICKS.get())),
                            this.modelOutput);
            return plainVariant(model);
        })));
        this.registerSimpleFlatItemModel(AetherIIBlocks.OUTPOST_CAMPFIRE.asItem());
    }
}
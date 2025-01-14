package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.aetherteam.aetherii.client.renderer.item.color.AetherGrassColorSource;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureMappings;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITexturedModels;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.special.BedSpecialRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class AetherIIBlockModelSubProvider extends BlockModelGenerators {
    public AetherIIBlockModelSubProvider(Consumer<BlockStateGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void createCrossBlock(Block block, PlantType type, TextureMapping mapping) {
        ResourceLocation resourcelocation = type.getCross().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    @Override
    public void createPlant(Block plant, Block pot, PlantType type) {
        this.createCrossBlock(plant, type);
        TextureMapping texturemapping = type.getPlantTextureMapping(plant);
        ResourceLocation resourcelocation = type.getCrossPot().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(pot, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(pot, resourcelocation));
    }

    @Override
    public void createDoor(Block block) {
        TextureMapping bottomMapping = AetherIITextureMappings.doorBottom(block);
        TextureMapping topMapping = AetherIITextureMappings.doorTop(block);
        ResourceLocation left = AetherIIModelTemplates.DOOR_BOTTOM_LEFT.create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomLeftOpen = AetherIIModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomRight = AetherIIModelTemplates.DOOR_BOTTOM_RIGHT.create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomRightOpen = AetherIIModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, bottomMapping, this.modelOutput);
        ResourceLocation topLeft = AetherIIModelTemplates.DOOR_TOP_LEFT.create(block, topMapping, this.modelOutput);
        ResourceLocation topLeftOpen = AetherIIModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, topMapping, this.modelOutput);
        ResourceLocation topRight = AetherIIModelTemplates.DOOR_TOP_RIGHT.create(block, topMapping, this.modelOutput);
        ResourceLocation topRightOpen = AetherIIModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, topMapping, this.modelOutput);
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(createDoor(block, left, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
    }
    @Override
    public void createOrientableTrapdoor(Block block) {
        TextureMapping mapping = TextureMapping.defaultTexture(block);
        ResourceLocation location = ModelTemplates.TRAPDOOR_TOP.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        ResourceLocation locationBottom = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        ResourceLocation locationOpen = ModelTemplates.TRAPDOOR_OPEN.extend().renderType("cutout").build().create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(block, location, locationBottom, locationOpen));
        this.registerSimpleItemModel(block, locationBottom);
    }

    public void createCutoutMippedCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, AetherIIModelTemplates.TEMPLATE_CUTOUT_MIPPED_CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput)));
    }

    public void createTranslucentCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, AetherIIModelTemplates.TEMPLATE_TRANSLUCENT_CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput)));
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
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(block, mapping, this.modelOutput)));
    }

    public ResourceLocation createTranslucentItemModelWithBlockTexture(Item item, Block block) {
        return AetherIIModelTemplates.TRANSLUCENT_FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), this.modelOutput);
    }

    public void createCubeColumn(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        ResourceLocation location = ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, location));
    }

    public void createFacingColumnWithHorizontalVariant(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        ResourceLocation verticalLocation = ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput);
        ResourceLocation horizontalLocation = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(side, mapping, this.modelOutput);
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public void createFacingTopBottomColumnWithHorizontalVariant(Block side, Block top, Block bottom) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(side))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(bottom, "_top"));
        ResourceLocation verticalLocation = ModelTemplates.CUBE_BOTTOM_TOP.create(side, mapping, this.modelOutput);
        ResourceLocation horizontalLocation = ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(side, "_horizontal"), mapping, this.modelOutput);
        this.blockStateOutput.accept(createFacingColumnWithHorizontalVariant(side, verticalLocation, horizontalLocation));
    }

    public static BlockStateGenerator createFacingColumnWithHorizontalVariant(Block block, ResourceLocation vertical, ResourceLocation horizontal) {
        return MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(FacingPillarBlock.FACING)
                        .select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, vertical))
                        .select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, vertical).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, horizontal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, horizontal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, horizontal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, horizontal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
        );
    }

    public void createAetherPortalBlock() {
        ResourceLocation locationNS = AetherIIModelTemplates.PORTAL_NS.create(AetherIIBlocks.AETHER_PORTAL.get(), AetherIITextureMappings.portal(AetherIIBlocks.AETHER_PORTAL.get()), this.modelOutput);
        ResourceLocation locationEW = AetherIIModelTemplates.PORTAL_EW.create(AetherIIBlocks.AETHER_PORTAL.get(), AetherIITextureMappings.portal(AetherIIBlocks.AETHER_PORTAL.get()), this.modelOutput);

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.AETHER_PORTAL.get()).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_AXIS)
                .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, locationNS))
                .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, locationEW))));
    }

    public void createAetherGrassBlocks() {
        TextureMapping snowMapping = AetherIITextureMappings.snowyGrass(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get());
        Variant snowVariant = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), "_snow", snowMapping, this.modelOutput));
        this.createTintedGrassBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), snowVariant);

        ResourceLocation enchantedGrassLocation = TexturedModel.CUBE_TOP_BOTTOM.get(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())
                .updateTextures((mapping) -> mapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_DIRT.get()))).create(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), this.modelOutput);
        this.createGrassLikeBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), enchantedGrassLocation, snowVariant);

        ResourceLocation dirtPathLocation = AetherIIModelTemplates.DIRT_PATH.create(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIITextureMappings.dirtPath(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_DIRT.get()), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(AetherIIBlocks.AETHER_DIRT_PATH.get(), dirtPathLocation));
    }

    public void createTintedGrassBlock(Block block, Variant snowyVariant) {
        ResourceLocation model = AetherIIModelTemplates.TINTED_GRASS.create(
                AetherIIBlocks.AETHER_GRASS_BLOCK.get(),
                AetherIITextureMappings.tintedGrass(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT.get()),
                this.modelOutput
        );
        List<Variant> list = Arrays.asList(BlockModelGenerators.createRotatedVariants(model));
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.SNOWY).select(true, snowyVariant).select(false, list))
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
        ResourceLocation location = ModelTemplates.FARMLAND.create(AetherIIBlocks.AETHER_FARMLAND.get(), mapping, this.modelOutput);
        ResourceLocation locationMoist = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_FARMLAND.get(), "_moist"), mappingMoist, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.AETHER_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, locationMoist, location)));
    }

    public void createGlassBlocks(Block glass, Block pane) {
        this.createTranslucentCube(glass);
        TextureMapping mapping = TextureMapping.pane(glass, pane);
        ResourceLocation post = ModelTemplates.STAINED_GLASS_PANE_POST.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput);
        ResourceLocation side = ModelTemplates.STAINED_GLASS_PANE_SIDE.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput);
        ResourceLocation sideAlt = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput);
        ResourceLocation noSide = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput);
        ResourceLocation noSideAlt = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.extend().renderType("translucent").build().create(pane, mapping, this.modelOutput);
        Item item = pane.asItem();
        this.registerSimpleItemModel(item, this.createTranslucentItemModelWithBlockTexture(item, glass));
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(pane).with(Variant.variant().with(VariantProperties.MODEL, post)).with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, side)).with(Condition.condition().term(BlockStateProperties.EAST, true), Variant.variant().with(VariantProperties.MODEL, side).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, sideAlt)).with(Condition.condition().term(BlockStateProperties.WEST, true), Variant.variant().with(VariantProperties.MODEL, sideAlt).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(Condition.condition().term(BlockStateProperties.NORTH, false), Variant.variant().with(VariantProperties.MODEL, noSide)).with(Condition.condition().term(BlockStateProperties.EAST, false), Variant.variant().with(VariantProperties.MODEL, noSideAlt)).with(Condition.condition().term(BlockStateProperties.SOUTH, false), Variant.variant().with(VariantProperties.MODEL, noSideAlt).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(Condition.condition().term(BlockStateProperties.WEST, false), Variant.variant().with(VariantProperties.MODEL, noSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
    }

    public void createPointedStone(Block block) {
        PropertyDispatch.C2<Direction, DripstoneThickness> properties = PropertyDispatch.properties(BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS);

        for (DripstoneThickness thicknessUp : DripstoneThickness.values()) {
            properties.select(Direction.UP, thicknessUp, this.createPointedStoneVariant(block, Direction.UP, thicknessUp));
        }
        for (DripstoneThickness thicknessDown : DripstoneThickness.values()) {
            properties.select(Direction.DOWN, thicknessDown, this.createPointedStoneVariant(block, Direction.DOWN, thicknessDown));
        }

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(properties));
        this.registerSimpleItemModel(block.asItem(), AetherIIModelTemplates.POINTED_STONE.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    public Variant createPointedStoneVariant(Block block, Direction direction, DripstoneThickness thickness) {
        String name = "_" + direction.getSerializedName() + "_" + thickness.getSerializedName();
        TextureMapping mapping = TextureMapping.cross(TextureMapping.getBlockTexture(block, name));
        return Variant.variant().with(VariantProperties.MODEL, AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.createWithSuffix(block, name, mapping, this.modelOutput));
    }

    public void createVine(Block block, ModelTemplate template) {
        ResourceLocation normal = template.create(block, AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block)), this.modelOutput);
        ResourceLocation bottom = template.create(ModelLocationUtils.getModelLocation(block, "_bottom"), AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block, "_bottom")), this.modelOutput);
        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(block);
        Condition.TerminalCondition condition = Util.make(Condition.condition(), (terminalCondition) -> MULTIFACE_GENERATOR.stream().map(Pair::getFirst).map(MultifaceBlock::getFaceProperty).forEach((bool) -> {
            if (block.defaultBlockState().hasProperty(bool)) {
                terminalCondition.term(bool, false);
            }
        }));
        for (Pair<Direction, Function<ResourceLocation, Variant>> directionFunctionPair : MULTIFACE_GENERATOR) {
            BooleanProperty bool = MultifaceBlock.getFaceProperty(directionFunctionPair.getFirst());
            Function<ResourceLocation, Variant> function = directionFunctionPair.getSecond();
            if (block.defaultBlockState().hasProperty(bool)) {
                multiPart
                        .with(Condition.condition().term(BottomedVineBlock.AGE, 0, ArrayUtils.toObject(IntStream.range(1, 25).toArray())).term(bool, true), function.apply(normal))
                        .with(Condition.condition().term(BottomedVineBlock.AGE, 25).term(bool, true), function.apply(bottom));
                multiPart.with(condition, function.apply(normal));
            }
        }
        this.blockStateOutput.accept(multiPart);
        this.registerSimpleFlatItemModel(block);
    }

    public void createCrystal(Block block, ModelTemplate itemModel) {
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput))).with(this.createColumnWithFacing()));
        this.registerSimpleItemModel(block.asItem(), itemModel.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    protected void createArcticSnowBlocks() {
        TextureMapping mapping = TextureMapping.cube(AetherIIBlocks.ARCTIC_SNOW.get());
        ResourceLocation snowBlockLocation = ModelTemplates.CUBE_ALL.create(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.ARCTIC_SNOW.get()).with(PropertyDispatch.property(BlockStateProperties.LAYERS).generate((i) -> {
            Variant variant = Variant.variant();
            VariantProperty<ResourceLocation> property = VariantProperties.MODEL;
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
            return variant.with(property, location);
        })));
        this.registerSimpleItemModel(AetherIIBlocks.ARCTIC_SNOW.get(), ModelLocationUtils.getModelLocation(AetherIIBlocks.ARCTIC_SNOW.get(), "_height2"));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), snowBlockLocation));
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

        ResourceLocation left = AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(ModelLocationUtils.getModelLocation(block, "_left"), leftMapping, this.modelOutput);
        ResourceLocation right = AetherIIModelTemplates.TRANSLUCENT_INNER_FACES.create(ModelLocationUtils.getModelLocation(block, "_right"), rightMapping, this.modelOutput);

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(PurpleAercloudBlock.FACING).generate((direction) -> {
            Variant variant = Variant.variant();
            VariantProperty<ResourceLocation> property = VariantProperties.MODEL;
            switch(direction) {
                case NORTH -> {
                    return variant.with(property, left).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90);
                }
                case SOUTH -> {
                    return variant.with(property, right).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270);
                }
                case WEST -> {
                    return variant.with(property, left).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                }
                case EAST -> {
                    return variant.with(property, right).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                }
            }
            return variant.with(property, left);
        })));
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(AetherIIModelTemplates.TEMPLATE_TRANSLUCENT_CUBE.create(block.asItem(), rightMapping, this.modelOutput)));
    }

    public void createCustomFlowerBed(Block block, ResourceLocation flowerbed1, ResourceLocation flowerbed2, ResourceLocation flowerbed3, ResourceLocation flowerbed4) {
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), Variant.variant().with(VariantProperties.MODEL, flowerbed1))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Variant.variant().with(VariantProperties.MODEL, flowerbed1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Variant.variant().with(VariantProperties.MODEL, flowerbed1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Variant.variant().with(VariantProperties.MODEL, flowerbed1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), Variant.variant().with(VariantProperties.MODEL, flowerbed2))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Variant.variant().with(VariantProperties.MODEL, flowerbed2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Variant.variant().with(VariantProperties.MODEL, flowerbed2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Variant.variant().with(VariantProperties.MODEL, flowerbed2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), Variant.variant().with(VariantProperties.MODEL, flowerbed3))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Variant.variant().with(VariantProperties.MODEL, flowerbed3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Variant.variant().with(VariantProperties.MODEL, flowerbed3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 3, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Variant.variant().with(VariantProperties.MODEL, flowerbed3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), Variant.variant().with(VariantProperties.MODEL, flowerbed4))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Variant.variant().with(VariantProperties.MODEL, flowerbed4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Variant.variant().with(VariantProperties.MODEL, flowerbed4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .with(Condition.condition().term(BlockStateProperties.FLOWER_AMOUNT, 4).term(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Variant.variant().with(VariantProperties.MODEL, flowerbed4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
    }

    public void createLeavesWithPiles(Block leaves, Block piles) {
        TextureMapping snowMapping = AetherIITextureMappings.snowyLeaves(leaves);
        ResourceLocation defaultLocation = AetherIITexturedModels.LEAVES.create(leaves, this.modelOutput);
        ResourceLocation snowyLocation = ModelTemplates.CUBE_BOTTOM_TOP.create(ModelLocationUtils.getModelLocation(leaves, "_snowy"), snowMapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(leaves).with(BlockModelGenerators.createBooleanModelDispatch(AetherLeavesBlock.SNOWY, snowyLocation, defaultLocation)));
        this.createPiles(piles, leaves);
    }

    public void createTintedLeavesWithPiles(Block leaves, Block piles) {
        this.createTrivialBlock(leaves, AetherIITexturedModels.TINTED_LEAVES);
        this.createPiles(piles, leaves);
    }

    public void createPiles(Block piles, Block leaves) {
        TextureMapping textureMapping = AetherIITextureMappings.particle(TextureMapping.cube(leaves));
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(piles).with(PropertyDispatch.property(AetherLeafPileBlock.PILES).generate((i) -> {
            Variant variant = Variant.variant();
            VariantProperty<ResourceLocation> property = VariantProperties.MODEL;
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
            return variant.with(property, location);
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
        ResourceLocation potLocation = ModelTemplates.FLOWER_POT_CROSS.extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(pot, plantMapping, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));
    }

    public void createSnowyCross(Block block) {
        this.registerSimpleFlatItemModel(block);
        ResourceLocation defaultLocation = AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput);
        ResourceLocation snowyLocation = this.createSuffixedVariant(block, "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(block.asItem())))
                .with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.SNOWY, snowyLocation, defaultLocation)));
    }

    public void createAsymmetricalPlantWithDefaultItem(Block block, TexturedModel.Provider provider, TexturedModel.Provider mirroredProvider, Block pot, ModelTemplate potTemplate) {
        this.registerSimpleFlatItemModel(block);

        ResourceLocation location = provider.create(block, this.modelOutput);
        ResourceLocation mirroredLocation = mirroredProvider.create(block, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(block, location, mirroredLocation));

        ResourceLocation potLocation = potTemplate.create(pot, AetherIITextureMappings.asymmetricalCross(block), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));
    }

    public void createUniquePlantWithDefaultItem(Block block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate) {
        this.registerSimpleFlatItemModel(block.asItem());

        ResourceLocation location = provider.create(block, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(block, location));

        ResourceLocation potLocation = potTemplate.create(pot, TextureMapping.plant(block), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));
    }

    public void createFacingPlantWithDefaultItem(Block block, TexturedModel.Provider provider, Block pot, ModelTemplate potTemplate) {
        this.registerSimpleFlatItemModel(block.asItem());

        ResourceLocation location = provider.create(block, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, location)).with(createHorizontalFacingDispatch()));

        ResourceLocation potLocation = potTemplate.create(pot, TextureMapping.plant(block), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));
    }

    public void createTintedTallGrass(Block block) {
        ResourceLocation defaultLocation = AetherIIModelTemplates.TINTED_TALL_GRASS.create(block, AetherIITextureMappings.tintedTallGrass(block), this.modelOutput);
        ResourceLocation snowyLocation = this.createSuffixedVariant(block, "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
        ResourceLocation enchantedLocation = this.createSuffixedVariant(block, "_enchanted", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> Variant.variant().with(VariantProperties.MODEL, defaultLocation);
            case SNOWY -> Variant.variant().with(VariantProperties.MODEL, snowyLocation);
            case ENCHANTED -> Variant.variant().with(VariantProperties.MODEL, enchantedLocation);
        })));

        ResourceLocation itemLocation = this.createFlatItemModelWithBlockTexture(block.asItem(), block);
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(itemLocation,
                new AetherGrassColorSource(0, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(2, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F)
        ));
    }

    public void createHighlandFern() {
        ResourceLocation defaultLocation = AetherIIModelTemplates.TEMPLATE_CUTOUT_TINTED_CROSS.create(AetherIIBlocks.HIGHLAND_FERN.get(), TextureMapping.cross(AetherIIBlocks.HIGHLAND_FERN.get()), this.modelOutput);
        ResourceLocation snowyLocation = this.createSuffixedVariant(AetherIIBlocks.HIGHLAND_FERN.get(), "_snowy", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
        ResourceLocation enchantedLocation = this.createSuffixedVariant(AetherIIBlocks.HIGHLAND_FERN.get(), "_enchanted", AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.HIGHLAND_FERN.get()).with(PropertyDispatch.property(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> Variant.variant().with(VariantProperties.MODEL, defaultLocation);
            case SNOWY -> Variant.variant().with(VariantProperties.MODEL, snowyLocation);
            case ENCHANTED -> Variant.variant().with(VariantProperties.MODEL, enchantedLocation);
        })));

        ResourceLocation potLocation = AetherIIModelTemplates.TEMPLATE_CUTOUT_TINTED_FLOWERPOT_CROSS.create(AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), TextureMapping.plant(defaultLocation), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.POTTED_HIGHLAND_FERN.get(), potLocation));

        ResourceLocation itemLocation = this.createFlatItemModelWithBlockTexture(AetherIIBlocks.HIGHLAND_FERN.asItem(), AetherIIBlocks.HIGHLAND_FERN.get());
        this.registerSimpleTintedItemModel(AetherIIBlocks.HIGHLAND_FERN.get(), itemLocation, new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F));
    }

    public void createBush(Block block, Block pot) {
        ResourceLocation location = AetherIIModelTemplates.BUSH_BLOCK.create(block, AetherIITextureMappings.bushBlock(block), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, location));

        ResourceLocation potLocation = AetherIIModelTemplates.POTTED_BUSH_BLOCK.create(pot, AetherIITextureMappings.pottedBushBlock(pot), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));
    }

    public void createOrangeTree(Block block, Block pot) {
        List<ResourceLocation> existing = new ArrayList<>();
        PropertyDispatch propertyDispatch = PropertyDispatch.properties(OrangeTreeBlock.HALF, OrangeTreeBlock.AGE).generate((half, age) -> {
            boolean lower = half == DoubleBlockHalf.LOWER;
            int bottomAge = age == 3 ? 2 : age;
            int topAge = Math.max(age, 2);
            String halfString = lower ? "_bottom_" : "_top_";
            ResourceLocation location = lower ? ModelLocationUtils.getModelLocation(block, halfString + bottomAge) : ModelLocationUtils.getModelLocation(block, halfString + topAge);
            if (!existing.contains(location)) {
                ResourceLocation model = AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(location, TextureMapping.cross(location), this.modelOutput);
                existing.add(location);
                return Variant.variant().with(VariantProperties.MODEL, model);
            } else {
                return Variant.variant().with(VariantProperties.MODEL, location);
            }
        });
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertyDispatch));

        ResourceLocation potLocation = ModelTemplates.FLOWER_POT_CROSS.extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build()
                .create(pot, TextureMapping.plant(TextureMapping.getBlockTexture(block, "_bottom_0")), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, potLocation));

        this.registerSimpleFlatItemModel(block, "_bottom_0");
    }

    public void createValkyrieSprout() {
        PropertyDispatch propertyDispatch = PropertyDispatch.property(ValkyrieSproutBlock.AGE).generate(age -> {
            ResourceLocation location = this.createSuffixedVariant(AetherIIBlocks.VALKYRIE_SPROUT.get(), "_stage" + age, AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS, TextureMapping::cross);
            return Variant.variant().with(VariantProperties.MODEL, location);
        });
        this.registerSimpleFlatItemModel(AetherIIBlocks.VALKYRIE_SPROUT.get(),"_stage0");
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.VALKYRIE_SPROUT.get()).with(propertyDispatch));
    }

    public void createBrettlPlant(Block block) {
        ResourceLocation normal = AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(block, TextureMapping.cross(block), this.modelOutput);
        ResourceLocation grown = AetherIIModelTemplates.TEMPLATE_CUTOUT_CROSS.create(ModelLocationUtils.getModelLocation(block, "_grown"), TextureMapping.cross(TextureMapping.getBlockTexture(block, "_grown")), this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(BlockModelGenerators.createBooleanModelDispatch(BrettlPlantBlock.GROWN, grown, normal)));
    }

    public void createTwig(Block twig, Block base) {
        TextureMapping mapping = TextureMapping.logColumn(base);
        ResourceLocation twigs1 = AetherIIModelTemplates.TWIG_1.create(twig, mapping, this.modelOutput);
        ResourceLocation twigs2 = AetherIIModelTemplates.TWIG_2.create(twig, mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(twig).with(BlockModelGenerators.createHorizontalFacingDispatch()).with(PropertyDispatch.property(TwigBlock.AMOUNT).generate((amount) -> {
            if (amount == 2) {
                return Variant.variant().with(VariantProperties.MODEL, twigs2);
            } else {
                return Variant.variant().with(VariantProperties.MODEL, twigs1);
            }
        })));
        this.registerSimpleFlatItemModel(twig.asItem());
    }

    public void createRock(Block rock, Block base) {
        TextureMapping mapping = TextureMapping.cube(base);
        ResourceLocation rock1 = AetherIIModelTemplates.ROCK_1.create(rock, mapping, this.modelOutput);
        ResourceLocation rock2 = AetherIIModelTemplates.ROCK_2.create(rock, mapping, this.modelOutput);
        ResourceLocation rock3 = AetherIIModelTemplates.ROCK_3.create(rock, mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(rock).with(BlockModelGenerators.createHorizontalFacingDispatch()).with(PropertyDispatch.property(RockBlock.AMOUNT).generate((amount) -> {
            if (amount == 3) {
                return Variant.variant().with(VariantProperties.MODEL, rock3);
            } else if (amount == 2) {
                return Variant.variant().with(VariantProperties.MODEL, rock2);
            } else {
                return Variant.variant().with(VariantProperties.MODEL, rock1);
            }
        })));
        this.registerSimpleFlatItemModel(rock.asItem());
    }

    public void createSecretDoor(Block block, Block base) {
        TextureMapping mapping = TextureMapping.door(TextureMapping.getBlockTexture(base), TextureMapping.getBlockTexture(base));
        ResourceLocation bottomLeft = ModelTemplates.DOOR_BOTTOM_LEFT.create(block, mapping, this.modelOutput);
        ResourceLocation bottomLeftOpen = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, mapping, this.modelOutput);
        ResourceLocation right = ModelTemplates.DOOR_BOTTOM_RIGHT.create(block, mapping, this.modelOutput);
        ResourceLocation bottomRightOpen = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, mapping, this.modelOutput);
        ResourceLocation topLeft = ModelTemplates.DOOR_TOP_LEFT.create(block, mapping, this.modelOutput);
        ResourceLocation topLeftOpen = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, mapping, this.modelOutput);
        ResourceLocation topRight = ModelTemplates.DOOR_TOP_RIGHT.create(block, mapping, this.modelOutput);
        ResourceLocation topRightOpen = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, mapping, this.modelOutput);
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(createDoor(block, bottomLeft, bottomLeftOpen, right, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
    }

    public void createOrientableSecretTrapdoor(Block block, Block base) {
        TextureMapping mapping = TextureMapping.defaultTexture(base);
        ResourceLocation top = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_TOP.create(block, mapping, this.modelOutput);
        ResourceLocation bottom = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_BOTTOM.create(block, mapping, this.modelOutput);
        ResourceLocation open = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_OPEN.create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(block, top, bottom, open));
        this.registerSimpleItemModel(block, bottom);
    }

    public void createAmbrosiumTorch() {
        TextureMapping mapping = TextureMapping.torch(AetherIIBlocks.AMBROSIUM_TORCH.get());
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.AMBROSIUM_TORCH.get(), AetherIIModelTemplates.TALL_TORCH.create(AetherIIBlocks.AMBROSIUM_TORCH.get(), mapping, this.modelOutput)));
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.AMBROSIUM_WALL_TORCH.get(), Variant.variant().with(VariantProperties.MODEL, AetherIIModelTemplates.TALL_WALL_TORCH.create(AetherIIBlocks.AMBROSIUM_WALL_TORCH.get(), mapping, this.modelOutput)))
                .with(BlockModelGenerators.createTorchHorizontalDispatch()));
        this.registerSimpleFlatItemModel(AetherIIBlocks.AMBROSIUM_TORCH.get());
    }

    public void createAltar(Block block, Block particle) {
        ResourceLocation location = AetherIIModelTemplates.ALTAR.create(block, TextureMapping.cube(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        ResourceLocation chargingLocation = AetherIIModelTemplates.ALTAR.create(ModelLocationUtils.getModelLocation(block, "_charging"), TextureMapping.cube(TextureMapping.getBlockTexture(block, "_charging")).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        ResourceLocation blastingLocation = AetherIIModelTemplates.ALTAR.create(ModelLocationUtils.getModelLocation(block, "_blasting"), TextureMapping.cube(TextureMapping.getBlockTexture(block, "_blasting")).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, location))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()).with(PropertyDispatch.properties(AltarBlock.BLASTING, AltarBlock.CHARGING)
                        .select(true, true, Variant.variant().with(VariantProperties.MODEL, blastingLocation))
                        .select(true, false, Variant.variant().with(VariantProperties.MODEL, blastingLocation))
                        .select(false, true, Variant.variant().with(VariantProperties.MODEL, chargingLocation))
                        .select(false, false, Variant.variant().with(VariantProperties.MODEL, location)))
        );
    }

    public void createArtisansBench(Block block, Block particle) {
        ResourceLocation location = AetherIIModelTemplates.ARTISANS_BENCH.create(block, TextureMapping.cube(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant()
                .with(VariantProperties.MODEL, location)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    public void createArkeniumForge(Block block, Block particle) {
        ResourceLocation location = AetherIIModelTemplates.ARKENIUM_FORGE.create(block, TextureMapping.cube(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        ResourceLocation chargedLocation = AetherIIModelTemplates.ARKENIUM_FORGE.create(ModelLocationUtils.getModelLocation(block, "_charged"), TextureMapping.cube(TextureMapping.getBlockTexture(block, "_charged")).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, location))
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(BlockModelGenerators.createBooleanModelDispatch(ArkeniumForgeBlock.CHARGED, chargedLocation, location)));
    }

    public void createLadder(Block block) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block)).copySlot(TextureSlot.TEXTURE, TextureSlot.PARTICLE);
        ResourceLocation location = AetherIIModelTemplates.LADDER.create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, location))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
        this.registerSimpleFlatItemModel(block);
    }

    public void createBed(Block bed, Block particle, ResourceLocation location) {
        ResourceLocation modelLocation = AetherIIModelTemplates.decorateBlockModelLocation("skyroot_bed");
        this.blockStateOutput.accept(createSimpleBlock(bed, modelLocation));
        Item item = bed.asItem();
        ResourceLocation inventoryLocation = ModelTemplates.BED_INVENTORY.create(ModelLocationUtils.getModelLocation(item), TextureMapping.particle(particle), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.specialModel(inventoryLocation, new BedSpecialRenderer.Unbaked(location)));
    }

    public void createMoaEgg(Block block) {
        this.blockStateOutput.accept(createSimpleBlock(block, AetherIIModelTemplates.EMPTY.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get())), this.modelOutput)));
    }

    public void createOutpostCampfire() {
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AetherIIBlocks.OUTPOST_CAMPFIRE.get()).with(PropertyDispatch.property(OutpostCampfireBlock.PART_FACING).generate(facing -> {
            ResourceLocation model = AetherIIModelTemplates.create("template_outpost_campfire_" + facing.name().toLowerCase(Locale.ROOT), "_" + facing.name().toLowerCase(Locale.ROOT), TextureSlot.TEXTURE)
                    .extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build()
                    .create(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), new TextureMapping()
                            .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(AetherIIBlocks.OUTPOST_CAMPFIRE.get()))
                            .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.HOLYSTONE_BRICKS.get())),
                            this.modelOutput);
            return Variant.variant().with(VariantProperties.MODEL, model);
        })));
        this.registerSimpleFlatItemModel(AetherIIBlocks.OUTPOST_CAMPFIRE.asItem());
    }
}
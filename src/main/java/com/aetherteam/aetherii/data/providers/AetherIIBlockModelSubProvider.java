package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.utility.*;
import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.aetherteam.aetherii.client.renderer.block.model.builder.TrunkModelBuilder;
import com.aetherteam.aetherii.client.renderer.item.color.AetherGrassColorSource;
import com.aetherteam.aetherii.client.renderer.item.model.*;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.HasBlockState;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIIModelTemplates;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureMappings;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITextureSlots;
import com.aetherteam.aetherii.data.resources.builders.models.AetherIITexturedModels;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.block.dispatch.multipart.CombinedCondition;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AetherIIBlockModelSubProvider extends BlockModelGenerators {
    public AetherIIBlockModelSubProvider(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void createCrossBlock(Block block, PlantType type, TextureMapping mapping) {
        MultiVariant crossBlock = plainVariant(type.getCross().create(block, mapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(block, crossBlock));
    }

    @Override
    public void createPlant(Block plant, Block pot, PlantType type) {
        this.createCrossBlock(plant, type);
        TextureMapping textureMapping = type.getPlantTextureMapping(plant);
        MultiVariant crossPlant = plainVariant(type.getCrossPot().create(pot, textureMapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(pot, crossPlant));
    }

    public void createTrunk(Block trunk, Block log) {
        TextureMapping mapping = TextureMapping.cube(log).copyForced(TextureSlot.ALL, TextureSlot.PARTICLE);
        MultiVariant side = plainVariant(AetherIIModelTemplates.TRUNK_SIDE.create(trunk, mapping, this.modelOutput));
        MultiVariant sideTall = plainVariant(AetherIIModelTemplates.TRUNK_SIDE_TALL.create(trunk, mapping, this.modelOutput));
        Identifier corner = AetherIIModelTemplates.TRUNK_CORNER.create(trunk, mapping, this.modelOutput);
        Identifier cornerTall = AetherIIModelTemplates.TRUNK_CORNER_TALL.create(trunk, mapping, this.modelOutput);
        Identifier inventory = AetherIIModelTemplates.TRUNK_INVENTORY.create(trunk, mapping, this.modelOutput);

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
        Identifier location = ModelTemplates.TRAPDOOR_TOP.create(block, mapping, this.modelOutput);
        Identifier locationBottom = ModelTemplates.TRAPDOOR_BOTTOM.create(block, mapping, this.modelOutput);
        Identifier locationOpen = ModelTemplates.TRAPDOOR_OPEN.create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(block, plainVariant(location), plainVariant(locationBottom), plainVariant(locationOpen)));
        this.registerSimpleItemModel(block, locationBottom);
    }

    public void createBarsWithDifferentEdge(Block block, Block edgeBlock, String suffix) {
        TextureMapping mapping = AetherIITextureMappings.barsWithDifferentEdge(block, edgeBlock, suffix);
        this.createBars(block,
                ModelTemplates.BARS_POST_ENDS.create(block, mapping, this.modelOutput),
                ModelTemplates.BARS_POST.create(block, mapping, this.modelOutput),
                ModelTemplates.BARS_CAP.create(block, mapping, this.modelOutput),
                ModelTemplates.BARS_CAP_ALT.create(block, mapping, this.modelOutput),
                ModelTemplates.BARS_POST_SIDE.create(block, mapping, this.modelOutput),
                ModelTemplates.BARS_POST_SIDE_ALT.create(block, mapping, this.modelOutput));
        this.registerSimpleFlatItemModel(block);
    }

    public void createCutoutMippedCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public void createTranslucentCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(block), this.modelOutput))));
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

    public Identifier createTranslucentItemModelWithBlockTexture(Item item, Block block) {
        return AetherIIModelTemplates.TRANSLUCENT_FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), this.modelOutput);
    }

    public void createCubeColumn(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        MultiVariant variant = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, variant));
    }

    public void createCubeBottom(Block block) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"));
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(block, mapping, this.modelOutput))));
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

    public void createLitBlock(Block block) {
        Material location = TextureMapping.getBlockTexture(block);
        MultiVariant on = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_CUBE_ALL.create(block, AetherIITextureMappings.cubeEmissive(location), this.modelOutput));
        MultiVariant off = plainVariant(ModelTemplates.CUBE_ALL.createWithSuffix(block, "_off", TextureMapping.cube(location), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, on, off)));
        this.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public void createLitStairs(Block block, Block base) {
        Material baseLocation = TextureMapping.getBlockTexture(base);

        Identifier straightLocation = AetherIIModelTemplates.TEMPLATE_EMISSIVE_STAIRS_STRAIGHT.create(block, AetherIITextureMappings.cubeEmissive(baseLocation), this.modelOutput);
        Identifier straightOffLocation = ModelTemplates.STAIRS_STRAIGHT.createWithSuffix(block, "_off", TextureMapping.cube(baseLocation), this.modelOutput);
        MultiVariant inner = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_STAIRS_INNER.create(block, AetherIITextureMappings.cubeEmissive(baseLocation), this.modelOutput));
        MultiVariant straight = plainVariant(straightLocation);
        MultiVariant outer = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_STAIRS_OUTER.create(block, AetherIITextureMappings.cubeEmissive(baseLocation), this.modelOutput));
        MultiVariant innerOff = plainVariant(ModelTemplates.STAIRS_INNER.createWithSuffix(block, "_off", TextureMapping.cube(baseLocation), this.modelOutput));
        MultiVariant straightOff = plainVariant(straightOffLocation);
        MultiVariant outerOff = plainVariant(ModelTemplates.STAIRS_OUTER.createWithSuffix(block, "_off", TextureMapping.cube(baseLocation), this.modelOutput));

        PropertyDispatch.C4<MultiVariant, Direction, Half, StairsShape, Boolean> dispatch = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE, BlockStateProperties.LIT);
        for (boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
            inner = lit ? inner : innerOff;
            straight = lit ? straight : straightOff;
            outer = lit ? outer : outerOff;
            dispatch = dispatch
                    .select(Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, lit, straight)
                    .select(Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT, lit, straight.with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT, lit, straight.with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT, lit, straight.with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, lit, outer)
                    .select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT, lit, outer.with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, lit, outer.with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, lit, outer.with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT, lit, outer.with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT, lit, outer.with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, lit, outer)
                    .select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT, lit, outer.with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, lit, inner)
                    .select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT, lit, inner.with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT, lit, inner.with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT, lit, inner.with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT, lit, inner.with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT, lit, inner.with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, lit, inner)
                    .select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT, lit, inner.with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.EAST, Half.TOP, StairsShape.STRAIGHT, lit, straight.with(X_ROT_180).with(UV_LOCK))
                    .select(Direction.WEST, Half.TOP, StairsShape.STRAIGHT, lit, straight.with(X_ROT_180).with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT, lit, straight.with(X_ROT_180).with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.TOP, StairsShape.STRAIGHT, lit, straight.with(X_ROT_180).with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT, lit, outer.with(X_ROT_180).with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT, lit, outer.with(X_ROT_180).with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT, lit, outer.with(X_ROT_180).with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT, lit, outer.with(X_ROT_180).with(UV_LOCK))
                    .select(Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT, lit, outer.with(X_ROT_180).with(UV_LOCK))
                    .select(Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT, lit, outer.with(X_ROT_180).with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT, lit, outer.with(X_ROT_180).with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT, lit, outer.with(X_ROT_180).with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT, lit, inner.with(X_ROT_180).with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT, lit, inner.with(X_ROT_180).with(Y_ROT_270).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT, lit, inner.with(X_ROT_180).with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT, lit, inner.with(X_ROT_180).with(UV_LOCK))
                    .select(Direction.EAST, Half.TOP, StairsShape.INNER_LEFT, lit, inner.with(X_ROT_180).with(UV_LOCK))
                    .select(Direction.WEST, Half.TOP, StairsShape.INNER_LEFT, lit, inner.with(X_ROT_180).with(Y_ROT_180).with(UV_LOCK))
                    .select(Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT, lit, inner.with(X_ROT_180).with(Y_ROT_90).with(UV_LOCK))
                    .select(Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT, lit, inner.with(X_ROT_180).with(Y_ROT_270).with(UV_LOCK));
        }
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
        this.registerSimpleItemModel(block, straightLocation);
    }

    public void createLitSlab(Block block, Block base) {
        Material baseLocation = TextureMapping.getBlockTexture(base);

        Identifier bottomLocation = AetherIIModelTemplates.TEMPLATE_EMISSIVE_SLAB_BOTTOM.create(block, AetherIITextureMappings.cubeEmissive(baseLocation), this.modelOutput);
        Identifier bottomOffLocation = ModelTemplates.SLAB_BOTTOM.createWithSuffix(block, "_off", TextureMapping.cube(baseLocation), this.modelOutput);
        MultiVariant bottom = plainVariant(bottomLocation);
        MultiVariant bottomOff = plainVariant(bottomOffLocation);
        MultiVariant top = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_SLAB_TOP.create(block, AetherIITextureMappings.cubeEmissive(baseLocation), this.modelOutput));
        MultiVariant topOff = plainVariant(ModelTemplates.SLAB_TOP.createWithSuffix(block, "_off", TextureMapping.cube(baseLocation), this.modelOutput));

        PropertyDispatch.C2<MultiVariant, SlabType, Boolean> dispatch = PropertyDispatch.initial(BlockStateProperties.SLAB_TYPE, BlockStateProperties.LIT);
        for (boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
            bottom = lit ? bottom : bottomOff;
            top = lit ? top : topOff;
            dispatch = dispatch
                    .select(SlabType.BOTTOM, lit, bottom)
                    .select(SlabType.TOP, lit, top)
                    .select(SlabType.DOUBLE, lit, plainVariant(ModelLocationUtils.getModelLocation(base)));
        }
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
        this.registerSimpleItemModel(block, bottomLocation);
    }

    public void createLitWall(Block block, Block base, Block blank) {
        Material blockLocation = TextureMapping.getBlockTexture(block);
        Material baseLocation = TextureMapping.getBlockTexture(base);
        Material blankLocation = TextureMapping.getBlockTexture(blank);

        MultiVariant post = BlockModelGenerators.plainVariant(AetherIIModelTemplates.EMISSIVE_COLUMN_WALL_POST.create(block, AetherIITextureMappings.cubeColumnEmissive(blockLocation.sprite(), blankLocation.sprite()), this.modelOutput));
        MultiVariant low = BlockModelGenerators.plainVariant(AetherIIModelTemplates.EMISSIVE_COLUMN_WALL_LOW_SIDE.create(block, AetherIITextureMappings.cubeColumnEmissive(baseLocation.sprite(), blankLocation.sprite()), this.modelOutput));
        MultiVariant tall = BlockModelGenerators.plainVariant(AetherIIModelTemplates.EMISSIVE_COLUMN_WALL_TALL_SIDE.create(block, AetherIITextureMappings.cubeColumnEmissive(baseLocation.sprite(), blankLocation.sprite()), this.modelOutput));
        MultiVariant postOff = BlockModelGenerators.plainVariant(AetherIIModelTemplates.COLUMN_WALL_POST.createWithSuffix(block, "_off", TextureMapping.column(blockLocation, blankLocation), this.modelOutput));
        MultiVariant lowOff = BlockModelGenerators.plainVariant(AetherIIModelTemplates.COLUMN_WALL_LOW_SIDE.createWithSuffix(block, "_off", TextureMapping.column(baseLocation, blankLocation), this.modelOutput));
        MultiVariant tallOff = BlockModelGenerators.plainVariant(AetherIIModelTemplates.COLUMN_WALL_TALL_SIDE.createWithSuffix(block, "_off", TextureMapping.column(baseLocation, blankLocation), this.modelOutput));

        MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);
        for (boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
            post = lit ? post : postOff;
            low = lit ? low : lowOff;
            tall = lit ? tall : tallOff;
            multiPartGenerator = multiPartGenerator
                    .with(condition().term(BlockStateProperties.UP, true).term(BlockStateProperties.LIT, lit), post)
                    .with(condition().term(BlockStateProperties.NORTH_WALL, WallSide.LOW).term(BlockStateProperties.LIT, lit), low.with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.EAST_WALL, WallSide.LOW).term(BlockStateProperties.LIT, lit), low.with(Y_ROT_90).with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.SOUTH_WALL, WallSide.LOW).term(BlockStateProperties.LIT, lit), low.with(Y_ROT_180).with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.WEST_WALL, WallSide.LOW).term(BlockStateProperties.LIT, lit), low.with(Y_ROT_270).with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.NORTH_WALL, WallSide.TALL).term(BlockStateProperties.LIT, lit), tall.with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.EAST_WALL, WallSide.TALL).term(BlockStateProperties.LIT, lit), tall.with(Y_ROT_90).with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.SOUTH_WALL, WallSide.TALL).term(BlockStateProperties.LIT, lit), tall.with(Y_ROT_180).with(UV_LOCK))
                    .with(condition().term(BlockStateProperties.WEST_WALL, WallSide.TALL).term(BlockStateProperties.LIT, lit), tall.with(Y_ROT_270).with(UV_LOCK));
        }

        this.blockStateOutput.accept(multiPartGenerator);
        TextureMapping inventoryMapping = new TextureMapping()
                .put(TextureSlot.END, blankLocation)
                .put(TextureSlot.SIDE, baseLocation)
                .put(TextureSlot.WALL, blockLocation)
                .put(AetherIITextureSlots.EMISSIVE_END, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/blank")))
                .put(AetherIITextureSlots.EMISSIVE_SIDE, TextureMapping.getBlockTexture(base, "_emissive"))
                .put(AetherIITextureSlots.EMISSIVE_WALL, TextureMapping.getBlockTexture(block, "_emissive"));
        Identifier resourcelocation = AetherIIModelTemplates.EMISSIVE_COLUMN_WALL_INVENTORY.create(block, inventoryMapping, this.modelOutput);
        this.registerSimpleItemModel(block, resourcelocation);
    }

    public void createLitCubeColumn(Block side, Block top) {
        Material sideLocation = TextureMapping.getBlockTexture(side);
        Material topLocation = TextureMapping.getBlockTexture(top);
        TextureMapping mapping = AetherIITextureMappings.cubeColumnEmissive(sideLocation.sprite(), TextureMapping.getBlockTexture(top).sprite());
        TextureMapping mappingOff = TextureMapping.column(sideLocation, topLocation);
        Identifier on = AetherIIModelTemplates.TEMPLATE_EMISSIVE_CUBE_COLUMN.create(side, mapping, this.modelOutput);
        Identifier off = ModelTemplates.CUBE_COLUMN.createWithSuffix(side, "_off", mappingOff, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(side).with(createBooleanModelDispatch(BlockStateProperties.LIT, plainVariant(on), plainVariant(off))));
        this.registerSimpleItemModel(side, on);
    }

    public void createLitFacingColumnWithHorizontalVariant(Block side, Block top) {
        Material sideLocation = TextureMapping.getBlockTexture(side);
        Material topLocation = TextureMapping.getBlockTexture(top);

        TextureMapping mapping = AetherIITextureMappings.cubeColumnEmissive(sideLocation.sprite(), topLocation.sprite());
        TextureMapping mappingOff = TextureMapping.column(sideLocation, topLocation);

        Identifier vertical = AetherIIModelTemplates.TEMPLATE_EMISSIVE_CUBE_COLUMN.create(side, mapping, this.modelOutput);
        Identifier verticalOff = ModelTemplates.CUBE_COLUMN.createWithSuffix(side, "_off", mappingOff, this.modelOutput);
        Identifier horizontal = AetherIIModelTemplates.TEMPLATE_EMISSIVE_CUBE_COLUMN_HORIZONTAL.create(side, mapping, this.modelOutput);
        Identifier horizontalOff = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(side, "_off", mappingOff, this.modelOutput);

        PropertyDispatch.C2<MultiVariant, Direction, Boolean> dispatch = PropertyDispatch.initial(FacingPillarBlock.FACING, BlockStateProperties.LIT);
        for (boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
            vertical = lit ? vertical : verticalOff;
            horizontal = lit ? horizontal : horizontalOff;
            dispatch = dispatch
                    .select(Direction.UP, lit, plainVariant(vertical))
                    .select(Direction.DOWN, lit, plainVariant(vertical).with(X_ROT_180))
                    .select(Direction.NORTH, lit, plainVariant(horizontal).with(X_ROT_90))
                    .select(Direction.SOUTH, lit, plainVariant(horizontal).with(X_ROT_90).with(Y_ROT_180))
                    .select(Direction.EAST, lit, plainVariant(horizontal).with(X_ROT_90).with(Y_ROT_90))
                    .select(Direction.WEST, lit, plainVariant(horizontal).with(X_ROT_90).with(Y_ROT_270));
        }
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(side).with(dispatch));
        this.registerSimpleItemModel(side, ModelLocationUtils.getModelLocation(side));
    }

    public void pressurePlate(Block pressurePlateBlock) {
        TextureMapping mapping = TextureMapping.cube(pressurePlateBlock);
        MultiVariant multivariant = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlateBlock, mapping, this.modelOutput));
        MultiVariant multivariant1 = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlateBlock, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlateBlock, multivariant, multivariant1));
    }

    public void button(Block buttonBlock) {
        TextureMapping mapping = TextureMapping.cube(buttonBlock);
        MultiVariant multivariant = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(buttonBlock, mapping, this.modelOutput));
        MultiVariant multivariant1 = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(buttonBlock, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createButton(buttonBlock, multivariant, multivariant1));
        Identifier resourcelocation = ModelTemplates.BUTTON_INVENTORY.create(buttonBlock, mapping, this.modelOutput);
        this.registerSimpleItemModel(buttonBlock, resourcelocation);
    }

    public void litButton(Block buttonBlock) {
        TextureMapping mapping = AetherIITextureMappings.cubeEmissive(TextureMapping.getBlockTexture(buttonBlock));
        MultiVariant multivariant = BlockModelGenerators.plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_BUTTON.create(buttonBlock, mapping, this.modelOutput));
        MultiVariant multivariant1 = BlockModelGenerators.plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_BUTTON_PRESSED.create(buttonBlock, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createButton(buttonBlock, multivariant, multivariant1));
        Identifier resourcelocation = AetherIIModelTemplates.TEMPLATE_EMISSIVE_BUTTON_INVENTORY.create(buttonBlock, mapping, this.modelOutput);
        this.registerSimpleItemModel(buttonBlock, resourcelocation);
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
        Identifier model = AetherIIModelTemplates.TINTED_GRASS.create(
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
        MultiVariant farmlandMoist = plainVariant(ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(AetherIIBlocks.AETHER_FARMLAND.get(), "_moist").sprite(), mappingMoist, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AETHER_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, farmlandMoist, farmland)));
    }

    public void createGlassBlocks(Block glass, Block pane) {
        this.createTranslucentCube(glass);
        TextureMapping mapping = TextureMapping.pane(glass, pane);
        MultiVariant post = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.create(pane, mapping, this.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.create(pane, mapping, this.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(pane, mapping, this.modelOutput));
        MultiVariant noSide = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(pane, mapping, this.modelOutput));
        MultiVariant noSideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(pane, mapping, this.modelOutput));
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
        MultiVariant normal = plainVariant(template.create(block, AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block).sprite()), this.modelOutput));
        MultiVariant bottom = plainVariant(template.create(ModelLocationUtils.getModelLocation(block, "_bottom"), AetherIITextureMappings.vine(TextureMapping.getBlockTexture(block, "_bottom").sprite()), this.modelOutput));
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

    public void createRoofing(Block block) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"));
        Identifier location = ModelTemplates.CUBE_BOTTOM_TOP.create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(location)));
    }

    public void createCrystal(Block block, ModelTemplate itemModel) {
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput))).with(ROTATIONS_COLUMN_WITH_FACING));
        this.registerSimpleItemModel(block.asItem(), itemModel.create(block.asItem(), TextureMapping.layer0(block), this.modelOutput));
    }

    public void createCorroboniteCluster(Block block, ModelTemplate itemModel) {
        MultiVariant multivariant = plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
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
        Identifier snowBlockLocation = ModelTemplates.CUBE_ALL.create(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get(), mapping, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.ARCTIC_SNOW.get()).with(PropertyDispatch.initial(BlockStateProperties.LAYERS).generate((i) -> {
            Identifier location;
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
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelTemplates.CUBE_ALL.create(block.asItem(), TextureMapping.cube(block), this.modelOutput)));
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
                switch (direction) {
                    case NORTH -> left.with(X_ROT_90);
                    case SOUTH -> right.with(X_ROT_270);
                    case WEST -> left.with(X_ROT_270).with(Y_ROT_90);
                    case EAST -> right.with(X_ROT_90).with(Y_ROT_90);
                    default -> left;
                }
        )));
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelTemplates.CUBE.create(block.asItem(), rightMapping, this.modelOutput)));
    }

    public void createCustomFlowerBed(Block block, Identifier flowerbed1, Identifier flowerbed2, Identifier flowerbed3, Identifier flowerbed4) {
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
            switch (mossy) {
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

    public void createLeavesWithPiles(Block leaves, Block piles, TexturedModel.Provider regularProvider, ModelTemplate baseTemplate) {
        Identifier cube = regularProvider.create(leaves, this.modelOutput);
        MultiVariant snowy = plainVariant(this.createOverlaidLeaves(leaves, AetherIIBlocks.ARCTIC_SNOW.get(), "snowy", cube, baseTemplate));
        MultiVariant bryalinn = plainVariant(this.createOverlaidLeaves(leaves, AetherIIBlocks.BRYALINN_MOSS_BLOCK.get(), "bryalinn", cube, baseTemplate));
        MultiVariant shayelinn = plainVariant(this.createOverlaidLeaves(leaves, AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get(), "shayelinn", cube, baseTemplate));
        MultiVariant ambrelinn = plainVariant(this.createOverlaidLeaves(leaves, AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get(), "ambrelinn", cube, baseTemplate));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(leaves)
                .with(PropertyDispatch.initial(AetherLeavesBlock.SNOWY, AetherLeavesBlock.MOSSY).generate((snowyState, mossyState) -> {
                    if (snowyState) {
                        return snowy;
                    } else {
                        switch (mossyState) {
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
                                return plainVariant(cube);
                            }
                        }
                    }
                }))
        );
        this.createPiles(piles, leaves);
    }

    public Identifier createOverlaidLeaves(Block block, Block top, String suffix, Identifier regular, ModelTemplate baseTemplate) {
        Identifier base = baseTemplate.createWithSuffix(
                block,
                "_" + suffix + "_base",
                new TextureMapping()
                        .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block))
                        .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_" + suffix)),
                this.modelOutput);
        Identifier overlay = AetherIIModelTemplates.OVERLAY.createWithSuffix(
                block,
                "_" + suffix + "_overlay",
                new TextureMapping()
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block))
                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(top))
                        .put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/" + suffix + "_overlay"))),
                this.modelOutput);
        return AetherIIModelTemplates.EMPTY.extend()
                .customLoader(CompositeModelBuilder::new, (builder) -> builder.child("overlay", overlay).child("base", base).child("default", regular))
                .build()
                .createWithSuffix(
                        block,
                        "_" + suffix,
                        new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
                        this.modelOutput);
    }

    public void createPiles(Block piles, Block leaves) {
        TextureMapping textureMapping = AetherIITextureMappings.particle(TextureMapping.cube(leaves));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(piles).with(PropertyDispatch.initial(AetherLeafPileBlock.PILES).generate((i) -> {
            Identifier location;
            if (i < 16) {
                int layers = i;
                location = ModelLocationUtils.getModelLocation(piles, "_height" + layers);
                AetherIIModelTemplates.THIN.extend()
                        .ambientOcclusion(layers == 1)
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
        MultiVariant crossPot = plainVariant(ModelTemplates.FLOWER_POT_CROSS.create(pot, plantMapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));
    }

    public void createSnowyCross(Block block) {
        this.registerSimpleFlatItemModel(block);
        MultiVariant cross = plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(block, "_snowy", ModelTemplates.CROSS, TextureMapping::cross));
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
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(block, "_snowy", ModelTemplates.CROSS, TextureMapping::cross));
        MultiVariant enchanted = plainVariant(this.createSuffixedVariant(block, "_enchanted", ModelTemplates.CROSS, TextureMapping::cross));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> plant;
            case SNOWY -> snowy;
            case ENCHANTED -> enchanted;
        })));

        Identifier itemLocation = this.createFlatItemModelWithBlockTexture(block.asItem(), block);
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(itemLocation,
                new AetherGrassColorSource(0, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F),
                new AetherGrassColorSource(2, AetherIIColorResolvers.AETHER_GRASS_COLOR, 2.0F, 10.0F)
        ));
    }

    public void createAetherFern() {
        MultiVariant plant = plainVariant(ModelTemplates.TINTED_CROSS.create(AetherIIBlocks.AETHER_FERN.get(), TextureMapping.cross(AetherIIBlocks.AETHER_FERN.get()), this.modelOutput));
        MultiVariant snowy = plainVariant(this.createSuffixedVariant(AetherIIBlocks.AETHER_FERN.get(), "_snowy", ModelTemplates.CROSS, TextureMapping::cross));
        MultiVariant enchanted = plainVariant(this.createSuffixedVariant(AetherIIBlocks.AETHER_FERN.get(), "_enchanted", ModelTemplates.CROSS, TextureMapping::cross));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.AETHER_FERN.get()).with(PropertyDispatch.initial(AetherTallGrassBlock.TYPE).generate((property) -> switch (property) {
            case DEFAULT -> plant;
            case SNOWY -> snowy;
            case ENCHANTED -> enchanted;
        })));

        MultiVariant crossPot = plainVariant(ModelTemplates.TINTED_FLOWER_POT_CROSS.create(AetherIIBlocks.POTTED_AETHER_FERN.get(), TextureMapping.plant(AetherIIBlocks.AETHER_FERN.get()), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(AetherIIBlocks.POTTED_AETHER_FERN.get(), crossPot));

        Identifier itemLocation = this.createFlatItemModelWithBlockTexture(AetherIIBlocks.AETHER_FERN.asItem(), AetherIIBlocks.AETHER_FERN.get());
        this.registerSimpleTintedItemModel(AetherIIBlocks.AETHER_FERN.get(), itemLocation, new AetherGrassColorSource(1, AetherIIColorResolvers.AETHER_GRASS_COLOR, 5.0F, 6.0F));
    }

    public void createBush(Block block, Block pot) {
        MultiVariant bush = plainVariant(AetherIIModelTemplates.BUSH_BLOCK.create(block, AetherIITextureMappings.bushBlock(block), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, bush));

        MultiVariant bushPot = plainVariant(AetherIIModelTemplates.POTTED_BUSH_BLOCK.create(pot, AetherIITextureMappings.pottedBushBlock(pot), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, bushPot));
    }

    public void createOrangeTree(Block block, Block pot) {
        List<Identifier> existing = new ArrayList<>();
        PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(OrangeTreeBlock.HALF, OrangeTreeBlock.AGE).generate((half, age) -> {
            boolean lower = half == DoubleBlockHalf.LOWER;
            int bottomAge = age == 3 ? 2 : age;
            int topAge = Math.max(age, 2);
            String halfString = lower ? "_bottom_" : "_top_";
            Identifier location = lower ? ModelLocationUtils.getModelLocation(block, halfString + bottomAge) : ModelLocationUtils.getModelLocation(block, halfString + topAge);
            if (!existing.contains(location)) {
                Identifier model = ModelTemplates.CROSS.create(location, TextureMapping.cross(new Material(location)), this.modelOutput);
                existing.add(location);
                return plainVariant(model);
            } else {
                return plainVariant(location);
            }
        });
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertyDispatch));

        MultiVariant crossPot = plainVariant(ModelTemplates.FLOWER_POT_CROSS
                .create(pot, TextureMapping.plant(TextureMapping.getBlockTexture(block, "_bottom_0")), this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pot, crossPot));

        this.registerSimpleFlatItemModel(block, "_bottom_0");
    }

    public void createValkyrieSprout() {
        PropertyDispatch<MultiVariant> propertyDispatch = PropertyDispatch.initial(ValkyrieSproutBlock.AGE).generate(age -> {
            Identifier location = this.createSuffixedVariant(AetherIIBlocks.VALKYRIE_SPROUT.get(), "_stage" + age, ModelTemplates.CROSS, TextureMapping::cross);
            return plainVariant(location);
        });
        this.registerSimpleFlatItemModel(AetherIIBlocks.VALKYRIE_SPROUT.get(), "_stage0");
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.VALKYRIE_SPROUT.get()).with(propertyDispatch));
    }

    public void createBrettlPlant(Block block) {
        MultiVariant normal = plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput));
        MultiVariant grown = plainVariant(ModelTemplates.CROSS.create(ModelLocationUtils.getModelLocation(block, "_grown"), TextureMapping.cross(TextureMapping.getBlockTexture(block, "_grown")), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(BrettlPlantBlock.GROWN, grown, normal)));
    }

    public void createMagneticShroom(Block standAlone, Block potted) {
        this.registerSimpleItemModel(standAlone.asItem(), this.createFlatItemModelWithBlockTextureAndOverlay(standAlone.asItem(), standAlone, "_emissive"));

        TextureMapping textures = TextureMapping.crossEmissive(standAlone);
        MultiVariant model = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_CROSS.create(standAlone, textures, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(standAlone, model));

        TextureMapping potTextures = TextureMapping.plantEmissive(standAlone);
        MultiVariant potModel = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_FLOWER_POT_CROSS.create(potted, potTextures, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(potted, potModel));
    }

    public void createMagneticShroomBlock(Block mushroomBlock) {
        MultiVariant multivariant = plainVariant(ModelTemplates.SINGLE_FACE.create(mushroomBlock, TextureMapping.defaultTexture(mushroomBlock), this.modelOutput));
        MultiVariant multivariant1 = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get(), "_inside"));
        this.createMagneticShroomBlockOutput(mushroomBlock, multivariant, multivariant1);
    }

    public void createMagneticShroomBlockEmissive(Block mushroomBlock) {
        TextureMapping textureMapping = new TextureMapping()
                .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(mushroomBlock))
                .put(AetherIITextureSlots.EMISSIVE, TextureMapping.getBlockTexture(mushroomBlock, "_emissive"));
        MultiVariant multivariant = plainVariant(AetherIIModelTemplates.TEMPLATE_EMISSIVE_SINGLE_FACE.create(mushroomBlock, textureMapping, this.modelOutput));
        MultiVariant multivariant1 = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get(), "_inside"));
        this.createMagneticShroomBlockOutput(mushroomBlock, multivariant, multivariant1);
    }

    public void createMagneticShroomBlockOutput(Block mushroomBlock, MultiVariant outside, MultiVariant inside) {
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(mushroomBlock)
                .with(condition().term(BlockStateProperties.NORTH, true), outside)
                .with(condition().term(BlockStateProperties.EAST, true), outside.with(Y_ROT_90).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.SOUTH, true), outside.with(Y_ROT_180).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.WEST, true), outside.with(Y_ROT_270).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.UP, true), outside.with(X_ROT_270).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.DOWN, true), outside.with(X_ROT_90).with(UV_LOCK))
                .with(condition().term(BlockStateProperties.NORTH, false), inside).with(condition().term(BlockStateProperties.EAST, false), inside.with(Y_ROT_90))
                .with(condition().term(BlockStateProperties.SOUTH, false), inside.with(Y_ROT_180)).with(condition().term(BlockStateProperties.WEST, false), inside.with(Y_ROT_270))
                .with(condition().term(BlockStateProperties.UP, false), inside.with(X_ROT_270)).with(condition().term(BlockStateProperties.DOWN, false), inside.with(X_ROT_90)));
        this.registerSimpleItemModel(mushroomBlock, TexturedModel.CUBE.createWithSuffix(mushroomBlock, "_inventory", this.modelOutput));
    }

    public void createMagneticShroomBlocksInside() {
        Material material = TextureMapping.getBlockTexture(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get(), "_inside");
        TextureMapping textureMapping = new TextureMapping()
                .put(TextureSlot.TEXTURE, material)
                .put(AetherIITextureSlots.EMISSIVE, material);
        AetherIIModelTemplates.TEMPLATE_EMISSIVE_SINGLE_FACE.create(ModelLocationUtils.getModelLocation(AetherIIBlocks.MAGNETIC_SHROOM_BLOCK.get(), "_inside"), textureMapping, this.modelOutput);
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

    public void createLockedDungeonBlock(Block block) {
        MultiVariant dungeonBlock = plainVariant(ModelLocationUtils.getModelLocation(block));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, dungeonBlock));
        this.registerSimpleItemModel(block.asItem(), AetherIIModelTemplates.LOCKED_BLOCK_INVENTORY.create(block.asItem(), AetherIITextureMappings.lockedBlockInventory(block), this.modelOutput));
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

    public void createLogSlab(SlabBlock block, Block baseBlock) {
        this.createBaseCustomSlab(block, baseBlock, baseBlock, "_top");
    }

    public void createWoodSlab(SlabBlock block, Block baseBlock, Block textureBlock) {
        this.createBaseCustomSlab(block, baseBlock, textureBlock, "");
    }

    public void createMushroomSlab(SlabBlock block, Block baseBlock) {
        this.createBaseCustomSlab(block, baseBlock, baseBlock, "");
    }

    public void createBaseCustomSlab(SlabBlock block, Block baseBlock, Block textureBlock, String suffix) {
        TextureMapping column = TextureMapping.column(TextureMapping.getBlockTexture(textureBlock), TextureMapping.getBlockTexture(textureBlock, suffix));
        MultiVariant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(block, column, this.modelOutput));
        MultiVariant top = plainVariant(ModelTemplates.SLAB_TOP.create(block, column, this.modelOutput));
        MultiVariant full = plainVariant(ModelLocationUtils.getModelLocation(baseBlock));
        this.blockStateOutput.accept(createSlab(block, bottom, top, full));
    }

    public void createHangingUndergrowth(Block block) { //todo
        MultiVariant growth = plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(TextureMapping.getBlockTexture(block)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, growth));
    }

    public void createRotshroomToadstool(Block block) {
        MultiVariant shroom = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.ROTSHROOM_TOADSTOOL.get()));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom));
    }

    public void createRotshroomCluster(Block block) {
        MultiVariant shroom = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.ROTSHROOM_CLUSTER.get()));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom).with(ROTATION_HORIZONTAL_FACING));
    }

    public void createShelfRotshroom(Block block) {
        MultiVariant shroom = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.SHELF_ROTSHROOM.get()));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, shroom).with(ROTATION_HORIZONTAL_FACING));
    }

    public void createShelfRotshroomSlab(Block block) {
        MultiVariant shroom = plainVariant(ModelLocationUtils.getModelLocation(AetherIIBlocks.SHELF_ROTSHROOM_SLAB.get()));
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
        Identifier top = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_TOP.create(block, mapping, this.modelOutput);
        Identifier bottom = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_BOTTOM.create(block, mapping, this.modelOutput);
        Identifier open = AetherIIModelTemplates.ORIENTABLE_SECRET_TRAPDOOR_OPEN.create(block, mapping, this.modelOutput);
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

    public void createAmberHourglass(Block block) {
        MultiVariant closed = plainVariant(AetherIIModelTemplates.AMBER_HOURGLASS.create(block, AetherIITextureMappings.amberHourglass(block), this.modelOutput));
        MultiVariant open = plainVariant(AetherIIModelTemplates.AMBER_HOURGLASS.createWithSuffix(block, "_open", AetherIITextureMappings.amberHourglass(block, "_open"), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(BlockModelGenerators.createBooleanModelDispatch(AmberHourglassBlock.OPEN, open, closed)));
    }

    public void createAltar(Block block, Block particle) {
        MultiVariant location = plainVariant(AetherIIModelTemplates.ALTAR.create(block, AetherIITextureMappings.altar(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, location)
                .with(ROTATION_HORIZONTAL_FACING));
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
        Identifier resourceLocation = AetherIIModelTemplates.ALKAHEST_PURIFIER_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new AlkahestPurifierSpecialRenderer.Unbaked());
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createCampfire(Block block) {
        MultiVariant campfire = plainVariant(AetherIIModelTemplates.AMBROSIUM_CAMPFIRE.create(block, AetherIITextureMappings.campfire(block), this.modelOutput));
        MultiVariant campfireOff = plainVariant(AetherIIModelTemplates.AMBROSIUM_CAMPFIRE_OFF.createWithSuffix(block, "_off", AetherIITextureMappings.campfireOff(block), this.modelOutput));
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, campfire, campfireOff)).with(ROTATION_HORIZONTAL_FACING_ALT));
    }

    public void createClimbingRopeStake(Block block) {
        MultiVariant stake = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_stake"));
        MultiVariant stakeShort = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_stake_short"));
        MultiVariant knot = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_knot"));
        MultiVariant connection = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_connection_lower"));
        MultiVariant connectionSpoolFloor = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_connection_spool_floor"));
        MultiVariant spoolMiddle = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_spool_middle"));
        MultiVariant spoolFloor = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_spool_floor"));

        MultiPartGenerator model = MultiPartGenerator.multiPart(block)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.UP), stakeShort.with(X_ROT_180))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.DOWN), stake)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.NORTH), stake.with(X_ROT_90).with(Y_ROT_180))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.EAST), stake.with(X_ROT_90).with(Y_ROT_270))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.SOUTH), stake.with(X_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.WEST), stake.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.UP), knot)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.DOWN), knot)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.NORTH), knot.with(X_ROT_90).with(Y_ROT_180))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.EAST), knot.with(X_ROT_90).with(Y_ROT_270))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.SOUTH), knot.with(X_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.WEST), knot.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.UP).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.DOWN).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.NORTH).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle.with(X_ROT_90).with(Y_ROT_180))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.EAST).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle.with(X_ROT_90).with(Y_ROT_270))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.SOUTH).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle.with(X_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.WEST).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER), spoolMiddle.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.UP).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED), connection)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.DOWN).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR), connectionSpoolFloor)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.NORTH).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED), connection)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.EAST).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED), connection)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.SOUTH).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED), connection)
                .with(condition().term(RopeStakeBlock.CONNECTION, Direction.WEST).term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED), connection)
                .with(condition().term(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR), spoolFloor);

        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(model);
    }

    public void createClimbingRope(Block block) {
        MultiVariant connectionLower = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_connection_lower"));
        MultiVariant capLower = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_cap_lower"));
        MultiVariant connectionUpper = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_connection_upper"));
        MultiVariant capUpper = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_cap_upper"));
        MultiVariant knot = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_knot"));
        MultiVariant spoolFloor = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_spool_floor"));
        MultiVariant fray = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("brettl_rope_fray"));

        MultiPartGenerator model = MultiPartGenerator.multiPart(block)
                .with(condition().term(RopeBlock.KNOT, true), knot)
                .with(condition().term(RopeBlock.END, AetherIIBlockStateProperties.RopeEndState.SPOOLED), spoolFloor)
                .with(condition().term(RopeBlock.END, AetherIIBlockStateProperties.RopeEndState.FRAYED), fray)
                .with(condition().term(RopeBlock.UP, true), connectionUpper)
                .with(condition().term(RopeBlock.UP, true).term(RopeBlock.DOWN, false), capUpper)
                .with(condition().term(RopeBlock.DOWN, true), connectionLower)
                .with(condition().term(RopeBlock.DOWN, true).term(RopeBlock.UP, false), capLower)
                .with(condition().term(RopeBlock.NORTH, true), connectionUpper.with(X_ROT_90))
                .with(condition().term(RopeBlock.NORTH, true).term(RopeBlock.SOUTH, false), capUpper.with(X_ROT_90))
                .with(condition().term(RopeBlock.EAST, true), connectionUpper.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeBlock.EAST, true).term(RopeBlock.WEST, false), capUpper.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeBlock.SOUTH, true), connectionLower.with(X_ROT_90))
                .with(condition().term(RopeBlock.SOUTH, true).term(RopeBlock.NORTH, false), capLower.with(X_ROT_90))
                .with(condition().term(RopeBlock.WEST, true), connectionLower.with(X_ROT_90).with(Y_ROT_90))
                .with(condition().term(RopeBlock.WEST, true).term(RopeBlock.EAST, false), capLower.with(X_ROT_90).with(Y_ROT_90));

        this.registerSimpleFlatItemModel(block.asItem()); //todo rotate item model 90 degrees in hand
        this.blockStateOutput.accept(model);
    }

    public void createVase(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Identifier location = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/vases/" + block.builtInRegistryHolder().getKey().identifier().getPath() + ".png");
        Item item = block.asItem();
        Identifier resourceLocation = AetherIIModelTemplates.VASE_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new VaseSpecialRenderer.Unbaked(location));
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createBarrel(Block block) {
        Material topOpen = TextureMapping.getBlockTexture(block, "_top_open");
        MultiVariant barrel = plainVariant(TexturedModel.CUBE_TOP_BOTTOM.create(block, this.modelOutput));
        MultiVariant barrelOpen = plainVariant(
                TexturedModel.CUBE_TOP_BOTTOM
                        .get(block)
                        .updateTextures(mapping -> mapping.put(TextureSlot.TOP, topOpen))
                        .createWithSuffix(block, "_open", this.modelOutput)
        );
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.OPEN).select(false, barrel).select(true, barrelOpen)).with(ROTATIONS_COLUMN_WITH_FACING));
    }

    public void createSentryCrate(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        Identifier model = ModelTemplates.CHEST_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(model, new SentryCrateSpecialRenderer.Unbaked(Identifier.fromNamespaceAndPath(AetherII.MODID, "single/sentry_crate_0")));
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createSentrySpawner(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        Identifier resourceLocation = AetherIIModelTemplates.SENTRY_SPAWNER_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new SentrySpawnerSpecialRenderer.Unbaked());
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createSentryTrap(Block block, Block tile) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(tile))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(tile))
                .put(AetherIITextureSlots.EMISSIVE_TOP, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/blank")));
        TextureMapping mappingSpawned = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_spawned"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(tile))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(tile))
                .put(AetherIITextureSlots.EMISSIVE_TOP, TextureMapping.getBlockTexture(block, "_emissive"));
        MultiVariant trap = plainVariant(AetherIIModelTemplates.SENTRY_TRAP.create(block, mapping, this.modelOutput));
        MultiVariant trapSpawned = plainVariant(AetherIIModelTemplates.SENTRY_TRAP.createWithSuffix(block, "_spawned", mappingSpawned, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AetherIIBlockStateProperties.TRAP_STATE)
                .select(AetherIIBlockStateProperties.TrapState.LOADED, trap)
                .select(AetherIIBlockStateProperties.TrapState.TRIGGERED, trap)
                .select(AetherIIBlockStateProperties.TrapState.SPAWNED, trapSpawned)
        ));
    }

    public void createPrayerCandle(Block block, Block particle) {
        MultiVariant candle = plainVariant(AetherIIModelTemplates.PRAYER_CANDLE.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, candle)
                .with(ROTATION_HORIZONTAL_FACING));
    }

    public void createGuardianPew(Block block, Block particle) {
        MultiVariant candle = plainVariant(AetherIIModelTemplates.GUARDIAN_PEW.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, candle)
                .with(ROTATION_HORIZONTAL_FACING));
    }

    public void createGuardianDonationBox(Block block, Block particle) {
        MultiVariant candle = plainVariant(AetherIIModelTemplates.GUARDIAN_DONATION_BOX.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, candle)
                .with(ROTATION_HORIZONTAL_FACING));
    }

    public void createAbandonedBag(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        Identifier resourceLocation = AetherIIModelTemplates.ABANDONED_BAG_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new AbandonedBagSpecialRenderer.Unbaked());
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createFungalCache(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        Identifier resourceLocation = AetherIIModelTemplates.FUNGAL_CACHE_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new FungalCacheSpecialRenderer.Unbaked());
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createSageChest(Block block, Block particle) {
        this.createParticleOnlyBlock(block, particle);
        Item item = block.asItem();
        Identifier model = ModelTemplates.CHEST_INVENTORY.create(item, TextureMapping.particle(particle), this.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(model, new SageChestSpecialRenderer.Unbaked(Identifier.fromNamespaceAndPath(AetherII.MODID, "sage_chest")));
        this.itemModelOutput.accept(item, unbaked);
    }

    public void createCopyBlock(Holder<Block> block, String overlay) {
        Identifier icon = Identifier.fromNamespaceAndPath(AetherII.MODID, overlay).withPrefix("block/");
        MultiVariant multivariant = plainVariant(ModelTemplates.PARTICLE_ONLY.create(block.value(), TextureMapping.particle(new Material(icon)), this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(block.value(), multivariant));

        CopyBlockSpecialRenderer.Unbaked unbaked = new CopyBlockSpecialRenderer.Unbaked(block, Identifier.fromNamespaceAndPath(AetherII.MODID, overlay));
        Identifier base = ModelTemplates.CHEST_INVENTORY.create(block.value().asItem(), TextureMapping.particle(new Material(icon)), this.modelOutput);
        Identifier baseFlat = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.value().asItem(), "_flat"), TextureMapping.layer0(new Material(icon)), this.modelOutput);
        this.itemModelOutput.accept(block.value().asItem(), ItemModelUtils.conditional(new HasBlockState(), ItemModelUtils.specialModel(base, unbaked), ItemModelUtils.plainModel(baseFlat)));
    }

    public void createLadder(Block block) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block)).copySlot(TextureSlot.TEXTURE, TextureSlot.PARTICLE);
        MultiVariant ladder = plainVariant(AetherIIModelTemplates.LADDER.create(block, mapping, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, ladder).with(ROTATION_HORIZONTAL_FACING));
        this.registerSimpleFlatItemModel(block);
    }

    public void createBedroll(Block block) {
        MultiVariant foot = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("cloudwool_bedroll_foot"));
        MultiVariant head = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("cloudwool_bedroll_head"));

        MultiVariantGenerator generator = MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BedrollBlock.PART).select(BedPart.FOOT, foot).select(BedPart.HEAD, head)).with(ROTATION_HORIZONTAL_FACING_ALT);

        this.registerSimpleFlatItemModel(block.asItem());

        this.blockStateOutput.accept(generator);
    }

    public void createBed(Block block, Block particle, String name) {
        Identifier location = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/bed/skyroot/" + name + ".png");
        MultiVariant bed = plainVariant(AetherIIModelTemplates.decorateBlockModelLocation("skyroot_bed"));
        this.blockStateOutput.accept(createSimpleBlock(block, bed));
        Item item = block.asItem();
        Identifier inventoryLocation = ModelTemplates.BED_INVENTORY.create(ModelLocationUtils.getModelLocation(item), TextureMapping.particle(particle), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.specialModel(inventoryLocation, new SkyrootBedSpecialRenderer.Unbaked(location)));
    }

    public void createLever(Block block) {
        MultiVariant lever = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant leverOn = plainVariant(ModelLocationUtils.getModelLocation(block, "_on"));
        this.registerSimpleFlatItemModel(block);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(createBooleanModelDispatch(BlockStateProperties.POWERED, lever, leverOn))
                .with(PropertyDispatch.modify(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING)
                        .select(AttachFace.CEILING, Direction.NORTH, X_ROT_180.then(Y_ROT_180))
                        .select(AttachFace.CEILING, Direction.EAST, X_ROT_180.then(Y_ROT_270))
                        .select(AttachFace.CEILING, Direction.SOUTH, X_ROT_180)
                        .select(AttachFace.CEILING, Direction.WEST, X_ROT_180.then(Y_ROT_90))
                        .select(AttachFace.FLOOR, Direction.NORTH, NOP)
                        .select(AttachFace.FLOOR, Direction.EAST, Y_ROT_90)
                        .select(AttachFace.FLOOR, Direction.SOUTH, Y_ROT_180)
                        .select(AttachFace.FLOOR, Direction.WEST, Y_ROT_270)
                        .select(AttachFace.WALL, Direction.NORTH, X_ROT_90)
                        .select(AttachFace.WALL, Direction.EAST, X_ROT_90.then(Y_ROT_90))
                        .select(AttachFace.WALL, Direction.SOUTH, X_ROT_90.then(Y_ROT_180))
                        .select(AttachFace.WALL, Direction.WEST, X_ROT_90.then(Y_ROT_270))));
    }

    public void createArilumLantern(Block block) {
        MultiVariant lantern = plainVariant(AetherIIModelTemplates.ARILUM_LANTERN.create(block, TextureMapping.cube(block).put(TextureSlot.INSIDE, TextureMapping.getBlockTexture(block, "_inside")), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, lantern));
    }

    public void createAnimalStash(Block block, Block particle) {
        MultiVariant normal = plainVariant(AetherIIModelTemplates.ANIMAL_STASH.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        MultiVariant open = plainVariant(AetherIIModelTemplates.ANIMAL_STASH_OPEN.createWithSuffix(block, "_open", TextureMapping.defaultTexture(block).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle)), this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(BlockModelGenerators.createBooleanModelDispatch(AnimalStashBlock.OPEN, open, normal)));
    }

    public void createMoaEgg(Block block) {
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(AetherIIModelTemplates.EMPTY.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get())), this.modelOutput))));
    }

    public void createOutpostCampfire() {
        final TextureSlot[] textureSlots = {AetherIITextureSlots.LOGS, AetherIITextureSlots.BRICKS, AetherIITextureSlots.ASH};
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.OUTPOST_CAMPFIRE.get()).with(PropertyDispatch.initial(OutpostCampfireBlock.PART_FACING).generate(facing -> {
            Identifier model = AetherIIModelTemplates.create("template_outpost_campfire_" + facing.name().toLowerCase(Locale.ROOT), "_" + facing.name().toLowerCase(Locale.ROOT), textureSlots)
                    
                    .create(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), new TextureMapping()
                                    .put(AetherIITextureSlots.LOGS, TextureMapping.getBlockTexture(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "_logs"))
                                    .put(AetherIITextureSlots.BRICKS, new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "block/large_holystone_bricks")))
                                    .put(AetherIITextureSlots.ASH, TextureMapping.getBlockTexture(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "_ash"))
                                    .putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AetherIIBlocks.HOLYSTONE_BRICKS.get())),
                            this.modelOutput);
            return plainVariant(model);
        })));
        this.registerSimpleFlatItemModel(AetherIIBlocks.OUTPOST_CAMPFIRE.asItem());
    }

    public void createMural() {
//        Identifier modelLocation = ModelLocationUtils.getModelLocation(AetherIIBlocks.MURAL.get());
//        MultiVariant mural = plainVariant(modelLocation);
//        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(AetherIIBlocks.MURAL.get(), mural).with(ROTATION_HORIZONTAL_FACING));
//        this.itemModelOutput.accept(AetherIIBlocks.MURAL.get().asItem(), new MuralItemModel.Unbaked(modelLocation));
    }

    public void createMeltingBlock(Block block, Block textureBlock, ModelTemplate modelTemplate) {
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(BlockStateProperties.AGE_3)
                        .select(0, plainVariant(this.createSuffixedVariant(textureBlock, "_0", modelTemplate, TextureMapping::cube)))
                        .select(1, plainVariant(this.createSuffixedVariant(textureBlock, "_1", modelTemplate, TextureMapping::cube)))
                        .select(2, plainVariant(this.createSuffixedVariant(textureBlock, "_2", modelTemplate, TextureMapping::cube)))
                        .select(3, plainVariant(this.createSuffixedVariant(textureBlock, "_3", modelTemplate, TextureMapping::cube)))
                )
        );
    }
}
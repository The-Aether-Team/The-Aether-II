package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.construction.AetherFarmBlock;
import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.block.utility.ArtisansBenchBlock;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.stream.IntStream;

public abstract class AetherIIBlockStateProvider extends NitrogenBlockStateProvider {
    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void cutoutBlock(Block block, String location) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/cube_all")).renderType(ResourceLocation.withDefaultNamespace("cutout")).texture("all", this.texture(this.name(block), location));
        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    public void grass(Block block, Block dirtBlock) {
        this.grassBlock(block, block, dirtBlock, this.grassBlockTinted(block, dirtBlock));
    }

    public void enchantedGrass(Block block, Block grassBlock, Block dirtBlock) {
        this.grassBlock(block, grassBlock, dirtBlock, this.grassBlock(block, dirtBlock));
    }

    public void grassBlock(Block baseBlock, Block blockForSnow, Block blockForDirt, ModelFile grass) {
        ModelFile grassSnowed = this.cubeBottomTop(this.name(blockForSnow) + "_snow",
                this.extend(this.texture(this.name(blockForSnow), "natural/"), "_snow"),
                this.texture(this.name(blockForDirt), "natural/"),
                this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
        this.getVariantBuilder(baseBlock).forAllStates(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        });
    }

    public ModelFile grassBlock(Block block, Block dirtBlock) {
        return this.cubeBottomTop(this.name(block),
                this.extend(this.texture(this.name(block), "natural/"), "_side"),
                this.texture(this.name(dirtBlock), "natural/"),
                this.extend(this.texture(this.name(block), "natural/"), "_top"));
    }

    public ModelFile grassBlockTinted(Block block, Block dirtBlock) {
        return this.models().withExistingParent(this.name(block), this.mcLoc("block/block")).renderType(ResourceLocation.withDefaultNamespace("cutout"))
                .texture("particle", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("bottom", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/natural/" + this.name(block) + "_top"))
                .texture("top_1", this.modLoc("block/natural/" + this.name(block) + "_top_1"))
                .texture("top_2", this.modLoc("block/natural/" + this.name(block) + "_top_2"))
                .texture("top_3", this.modLoc("block/natural/" + this.name(block) + "_top_3"))
                .texture("side", this.modLoc("block/natural/" + this.name(block) + "_side"))
                .texture("overlay_1", this.modLoc("block/natural/" + this.name(block) + "_side_overlay_1"))
                .texture("overlay_2", this.modLoc("block/natural/" + this.name(block) + "_side_overlay_2"))
                .texture("overlay_3", this.modLoc("block/natural/" + this.name(block) + "_side_overlay_3"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").cullface(Direction.UP).end()
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.EAST).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top_1").cullface(Direction.UP).tintindex(0).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top_2").cullface(Direction.UP).tintindex(1).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top_3").cullface(Direction.UP).tintindex(2).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(0).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(0).cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(0).cullface(Direction.WEST).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(0).cullface(Direction.EAST).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#overlay_2").tintindex(1).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#overlay_2").tintindex(1).cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 0, 16, 16).texture("#overlay_2").tintindex(1).cullface(Direction.WEST).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#overlay_2").tintindex(1).cullface(Direction.EAST).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#overlay_3").tintindex(2).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#overlay_3").tintindex(2).cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 0, 16, 16).texture("#overlay_3").tintindex(2).cullface(Direction.WEST).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#overlay_3").tintindex(2).cullface(Direction.EAST).end()
                .end();
    }

    public void dirtPath(Block block, Block dirtBlock) {
        ModelFile path = this.models().withExistingParent(this.name(block), this.mcLoc("block/dirt_path"))
                .texture("particle", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_top"))
                .texture("side", this.modLoc("block/construction/" + this.name(block) + "_side"))
                .texture("bottom", this.modLoc("block/natural/" + this.name(dirtBlock)));
        this.getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false));
    }

    public void farmland(Block block, Block dirtBlock) {
        ModelFile farmland = this.models().withExistingParent(this.name(block), this.mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block)));
        ModelFile moist = this.models().withExistingParent(this.name(block) + "_moist", mcLoc("block/template_farmland"))
                .texture("dirt", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_moist"));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            int moisture = state.getValue(AetherFarmBlock.MOISTURE);
            return ConfiguredModel.builder()
                    .modelFile(moisture < AetherFarmBlock.MAX_MOISTURE ? farmland : moist)
                    .build();
        });
    }

    public void roots(Block block) {
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            boolean snowy = state.getValue(BlockStateProperties.SNOWY);
            ModelFile model;
            if (snowy) {
                model = this.models().cross("frosted_" + this.name(block), this.texture("frosted_" + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            } else {
                model = this.models().cross(this.name(block), this.texture(this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            }
            return ConfiguredModel.builder().modelFile(model).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public void mossVines(Block block) {
        ModelFile normalModel = this.models().getBuilder(this.name(block))
                .ao(false)
                .texture("vine", this.texture(this.name(block), "natural/"))
                .texture("particle", this.texture(this.name(block), "natural/"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
                .element().from(0.0F, 0.0F, 0.2F).to(16.0F, 16.0F, 0.2F)//.shade(false)
                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
                .end();
        ModelFile bottomModel  = this.models().getBuilder(this.name(block) + "_bottom")
                .ao(false)
                .texture("vine", this.extend(texture(this.name(block), "natural/"), "_bottom"))
                .texture("particle", this.extend(texture(this.name(block), "natural/"), "_bottom"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
                .element().from(0.0F, 0.0F, 0.25F).to(16.0F, 16.0F, 0.25F)//.shade(false)
                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
                .end();

        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        List<BooleanProperty> directions = List.of(BottomedVineBlock.NORTH, BottomedVineBlock.EAST, BottomedVineBlock.SOUTH, BottomedVineBlock.WEST, BottomedVineBlock.UP);
        int y = 0;
        for (BooleanProperty direction : directions) {
            if (direction != BottomedVineBlock.UP) {
                builder = builder.part()
                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
                        .condition(direction, true)
                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
                        .end();
                builder = builder.part()
                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
                        .condition(BottomedVineBlock.EAST, false)
                        .condition(BottomedVineBlock.NORTH, false)
                        .condition(BottomedVineBlock.SOUTH, false)
                        .condition(BottomedVineBlock.UP, false)
                        .condition(BottomedVineBlock.WEST, false)
                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
                        .end();
                builder = builder.part()
                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
                        .condition(direction, true)
                        .condition(BottomedVineBlock.AGE, 25)
                        .end();
                builder = builder.part()
                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
                        .condition(BottomedVineBlock.EAST, false)
                        .condition(BottomedVineBlock.NORTH, false)
                        .condition(BottomedVineBlock.SOUTH, false)
                        .condition(BottomedVineBlock.UP, false)
                        .condition(BottomedVineBlock.WEST, false)
                        .condition(BottomedVineBlock.AGE, 25)
                        .end();
                y += 90;
            } else {
                builder = builder.part()
                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
                        .condition(direction, true)
                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
                        .end();
                builder = builder.part()
                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
                        .condition(BottomedVineBlock.EAST, false)
                        .condition(BottomedVineBlock.NORTH, false)
                        .condition(BottomedVineBlock.SOUTH, false)
                        .condition(BottomedVineBlock.UP, false)
                        .condition(BottomedVineBlock.WEST, false)
                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
                        .end();
                builder = builder.part()
                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
                        .condition(direction, true)
                        .condition(BottomedVineBlock.AGE, 25)
                        .end();
                builder = builder.part()
                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
                        .condition(BottomedVineBlock.EAST, false)
                        .condition(BottomedVineBlock.NORTH, false)
                        .condition(BottomedVineBlock.SOUTH, false)
                        .condition(BottomedVineBlock.UP, false)
                        .condition(BottomedVineBlock.WEST, false)
                        .condition(BottomedVineBlock.AGE, 25)
                        .end();
            }
        }
    }

    public void bryalinnFlowers(Block block) {
        ResourceLocation texture = this.texture(this.name(block), "natural/");

        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        for (int i = 1; i <= 4; i++) {
            ModelFile model = this.models().withExistingParent(this.name(block) + "_" + i, this.modLoc("block/template_bryalinn_moss_flowers_" + i))
                    .ao(false)
                    .renderType(ResourceLocation.withDefaultNamespace("cutout"))
                    .texture("0", texture)
                    .texture("particle", texture);

            builder = builder.part().modelFile(model).addModel()
                    .condition(BryalinnFlowersBlock.FACING, Direction.NORTH).condition(BryalinnFlowersBlock.AMOUNT, ArrayUtils.toObject(IntStream.range(i, 5).toArray()))
                    .end();
            builder = builder.part().modelFile(model).rotationY(90).addModel()
                    .condition(BryalinnFlowersBlock.FACING, Direction.EAST).condition(BryalinnFlowersBlock.AMOUNT, ArrayUtils.toObject(IntStream.range(i, 5).toArray()))
                    .end();
            builder = builder.part().modelFile(model).rotationY(180).addModel()
                    .condition(BryalinnFlowersBlock.FACING, Direction.SOUTH).condition(BryalinnFlowersBlock.AMOUNT, ArrayUtils.toObject(IntStream.range(i, 5).toArray()))
                    .end();
            builder = builder.part().modelFile(model).rotationY(270).addModel()
                    .condition(BryalinnFlowersBlock.FACING, Direction.WEST).condition(BryalinnFlowersBlock.AMOUNT, ArrayUtils.toObject(IntStream.range(i, 5).toArray()))
                    .end();
        }
    }

    public void snowLayer(Block block, Block base) {
        ResourceLocation texture = this.texture("natural/" + this.name(base));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            int i = state.getValue(SnowLayerBlock.LAYERS);
            boolean firstState = i == 1;
            i *= 2;
            String name = firstState ? this.name(block) : this.name(block) + i;
            BlockModelBuilder modelBuilder = firstState ? this.models().withExistingParent(name, this.mcLoc("block/thin_block")) : this.models().getBuilder(name);
            ModelFile model = modelBuilder
                    .texture("particle", texture)
                    .texture("texture", texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, i, 16.0F)
                    .face(Direction.DOWN).texture("#texture").end()
                    .face(Direction.UP).texture("#texture").end()
                    .face(Direction.NORTH).texture("#texture").end()
                    .face(Direction.SOUTH).texture("#texture").end()
                    .face(Direction.EAST).texture("#texture").end()
                    .face(Direction.WEST).texture("#texture").end()
                    .end();
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void iceCrystal(Block block) {
        ResourceLocation texture = this.texture("natural/" + this.name(block));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            ModelFile model = this.models().cross(this.name(block), texture).renderType(ResourceLocation.parse("cutout"));
            Direction direction = state.getValue(IceCrystalBlock.FACING);
            int x = 0;
            int y = 0;
            if (direction.getAxis().isHorizontal()) {
                x = 90;
            } else if (direction == Direction.DOWN) {
                x = 180;
            }
            if (direction == Direction.EAST) {
                y = 90;
            } else if (direction == Direction.SOUTH) {
                y = 180;
            } else if (direction == Direction.WEST) {
                y = 270;
            }
            return ConfiguredModel.builder().modelFile(model).rotationX(x).rotationY(y).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public void corroboniteCluster(Block block) {
        ResourceLocation texture = this.texture("natural/" + this.name(block));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction facing = state.getValue(CorroboniteClusterBlock.FACING);
            int x = 0;
            int y = 0;
            if (facing == Direction.DOWN) {
                x = 180;
            } else if (facing != Direction.UP) {
                x = 90;
            }
            y = (int) facing.getOpposite().toYRot();

            ModelFile modelFile = this.models().cross(this.name(block), texture).renderType(ResourceLocation.withDefaultNamespace("cutout"));

            return ConfiguredModel.builder().modelFile(modelFile).rotationX(x).rotationY(y).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public void aercloudAll(Block block, String location) {
        ResourceLocation texture = this.texture(this.name(block), location);
        this.aercloud(block, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture);
    }

    public void aercloud(Block block,
                         ResourceLocation upInside, ResourceLocation upOutside,
                         ResourceLocation downOutside, ResourceLocation downInside,
                         ResourceLocation northOutside, ResourceLocation northInside,
                         ResourceLocation southInside, ResourceLocation southOutside,
                         ResourceLocation westOutside, ResourceLocation westInside,
                         ResourceLocation eastInside, ResourceLocation eastOutside,
                         ResourceLocation particle) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("up_inside", upInside)
                .texture("up_outside", upOutside)
                .texture("down_outside", downOutside)
                .texture("down_inside", downInside)
                .texture("north_outside", northOutside)
                .texture("north_inside", northInside)
                .texture("south_inside", southInside)
                .texture("south_outside", southOutside)
                .texture("west_outside", westOutside)
                .texture("west_inside", westInside)
                .texture("east_inside", eastInside)
                .texture("east_outside", eastOutside)
                .texture("particle", particle)
                .renderType(ResourceLocation.withDefaultNamespace("translucent"))
                .element().from(0.0F, 15.998F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.DOWN).texture("#up_inside").uvs(0, 16, 16, 0).cullface(Direction.UP).end()
                .face(Direction.UP).texture("#up_outside").uvs(0, 0, 16, 16).cullface(Direction.UP).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 0.002F, 16.0F)
                .face(Direction.DOWN).texture("#down_outside").uvs(0, 0, 16, 16).cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#down_inside").uvs(0, 16, 16, 0).cullface(Direction.DOWN).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.002F)
                .face(Direction.NORTH).texture("#north_outside").uvs(0, 0, 16, 16).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#north_inside").uvs(16, 0, 0, 16).cullface(Direction.NORTH).end()
                .end()
                .element().from(0.0F, 0.0F, 15.998F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).texture("#south_inside").uvs(16, 0, 0, 16).cullface(Direction.SOUTH).end()
                .face(Direction.SOUTH).texture("#south_outside").uvs(0, 0, 16, 16).cullface(Direction.SOUTH).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(0.002F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#west_outside").uvs(0, 0, 16, 16).cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#west_inside").uvs(16, 0, 0, 16).cullface(Direction.WEST).end()
                .end()
                .element().from(15.998F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#east_inside").uvs(16, 0, 0, 16).cullface(Direction.EAST).end()
                .face(Direction.EAST).texture("#east_outside").uvs(0, 0, 16, 16).cullface(Direction.EAST).end()
                .end();
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
    }

    public void purpleAercloud(Block block) {
        String blockName = this.name(block);
        ResourceLocation front = this.extend(this.texture(this.name(block), "natural/"), "_front");
        ResourceLocation back = this.extend(this.texture(this.name(block), "natural/"), "_back");
        ResourceLocation right = this.extend(this.texture(this.name(block), "natural/"), "_right");
        ResourceLocation left = this.extend(this.texture(this.name(block), "natural/"), "_left");
        ModelFile rightModel = this.models().cubeBottomTop(blockName, right, back, front).renderType(ResourceLocation.withDefaultNamespace("translucent"));
        ModelFile leftModel = this.models().cubeBottomTop(blockName, left, back, front).renderType(ResourceLocation.withDefaultNamespace("translucent"));

        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(PurpleAercloudBlock.FACING);
            switch(direction) {
                case NORTH -> {
                    return ConfiguredModel.builder().modelFile(leftModel).rotationX(90).build();
                }
                case SOUTH -> {
                    return ConfiguredModel.builder().modelFile(rightModel).rotationX(-90).build();
                }
                case WEST -> {
                    return ConfiguredModel.builder().modelFile(leftModel).rotationX(-90).rotationY(90).build();
                }
                case EAST -> {
                    return ConfiguredModel.builder().modelFile(rightModel).rotationX(90).rotationY(90).build();
                }
            }
            return ConfiguredModel.builder().build();
        });
    }

    public void mossyWisprootLog(FacingPillarBlock block, Block endBlock) {
        ResourceLocation side = this.texture(this.name(block), "natural/");
        ResourceLocation bottom = this.texture(this.name(block) + "_top", "natural/");
        ResourceLocation top = this.texture(this.name(endBlock) + "_top", "natural/");
        this.facingPillar(block, side, bottom, top);
    }

    public void logDifferentTop(RotatedPillarBlock block, RotatedPillarBlock baseBlock) {
        this.axisBlock(block, this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
    }

    public void leafPile(Block block, Block base) {
        ResourceLocation texture = this.texture("natural/" + this.name(base));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            int i = state.getValue(AetherLeafPileBlock.PILES);
            boolean firstState = i == 1;
            boolean lastState = i == 16;
            String name = firstState ? this.name(block) : this.name(block) + i;
            BlockModelBuilder modelBuilder = firstState ? this.models().withExistingParent(name, this.mcLoc("block/thin_block")) : this.models().getBuilder(name);
            ModelFile model = modelBuilder.ao(lastState)
                    .texture("particle", texture)
                    .texture("texture", texture)
                    .element().from(0.0F, 0.0F, 0.0F).to(16.0F, i, 16.0F)
                    .face(Direction.DOWN).texture("#texture").end()
                    .face(Direction.UP).texture("#texture").end()
                    .face(Direction.NORTH).texture("#texture").end()
                    .face(Direction.SOUTH).texture("#texture").end()
                    .face(Direction.EAST).texture("#texture").end()
                    .face(Direction.WEST).texture("#texture").end()
                    .end()
                    .renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        }, AetherLeafPileBlock.PERSISTENT);
    }

    public void leaves(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean snowy = state.getValue(BlockStateProperties.SNOWY);
            ModelFile model;
            if (snowy) {
                model = this.models().cubeColumnHorizontal("frosted_" + this.name(block), this.texture("frosted_" + this.name(block), "natural/"), this.texture(this.name(block), "natural/"));
            } else {
                model = this.models().cubeAll(this.name(block), this.texture(this.name(block), "natural/"));
            }
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void tintedLeaves(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            ModelFile model = this.models().leaves(this.name(block), this.texture(this.name(block), "natural/"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void tintedFern(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/tinted_cross")).texture("cross", this.texture(this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void lilichime(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            ModelFile model = this.models().withExistingParent(this.name(block), this.modLoc("block/template_lilichime"))
                    .texture("flower", this.texture(this.name(block), "natural/"))
                    .texture("particle", this.texture(this.name(block), "natural/"))
                    .renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void asymmetricalCrossEven(Block block) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.modLoc("block/asymmetrical_cross_even"))
                .texture("0", this.extend(this.texture(this.name(block), "natural/"), "_0"))
                .texture("1", this.extend(this.texture(this.name(block), "natural/"), "_1"))
                .texture("particle", this.extend(this.texture(this.name(block), "natural/"), "_0"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(ConfiguredModel.builder().modelFile(model).build()).addModels(ConfiguredModel.builder().modelFile(model).rotationY(90).build());
    }

    public void asymmetricalCrossOdd(Block block) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.modLoc("block/asymmetrical_cross_odd"))
                .texture("0", this.extend(this.texture(this.name(block), "natural/"), "_0"))
                .texture("1", this.extend(this.texture(this.name(block), "natural/"), "_1"))
                .texture("particle", this.extend(this.texture(this.name(block), "natural/"), "_0"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(ConfiguredModel.builder().modelFile(model).build()).addModels(ConfiguredModel.builder().modelFile(model).rotationY(90).build());
    }

    public void pottedTintedPlant(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.mcLoc("block/tinted_flower_pot_cross")).texture("plant", this.modLoc("block/" + location + this.name(flower))).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedAsymmetricalEvenPlant(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.modLoc("block/flower_pot_asymmetrical_cross_even"))
                .texture("0", this.extend(this.texture(this.name(flower), location), "_0"))
                .texture("1", this.extend(this.texture(this.name(flower), location), "_1"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedAsymmetricalOddPlant(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.modLoc("block/flower_pot_asymmetrical_cross_odd"))
                .texture("0", this.extend(this.texture(this.name(flower), location), "_0"))
                .texture("1", this.extend(this.texture(this.name(flower), location), "_1"))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedLilichime(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.modLoc("block/flower_pot_lilichime"))
                .texture("flower", this.texture(this.name(flower), location))
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void shortGrass(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            AetherTallGrassBlock.GrassType type = state.getValue(AetherTallGrassBlock.TYPE);
            ModelFile grass;
            switch (type) {
                case SNOWY -> grass = this.models().cross("frosted_" + this.name(block), this.texture("frosted_" + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
                case ENCHANTED -> grass = this.models().cross("enchanted_" + this.name(block), this.texture("enchanted_" + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
                default -> grass = this.triTintedCross(this.name(block))
                        .texture("particle", this.texture(this.name(block), "natural/"))
                        .texture("cross_1", this.extend(this.texture(this.name(block), "natural/"), "_1"))
                        .texture("cross_2", this.extend(this.texture(this.name(block), "natural/"), "_2"))
                        .texture("cross_3", this.extend(this.texture(this.name(block), "natural/"), "_3"));
            }
            return ConfiguredModel.builder().modelFile(grass).build();
        });
    }

    public ModelBuilder<BlockModelBuilder> triTintedCross(String name) {
        return this.models().getBuilder(name)
                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
                .ao(false)
                .element()
                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
                .end()
                .element()
                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
                .end()
                .element()
                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
                .end()
                .element()
                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
                .end()
                .element()
                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
                .end()
                .element()
                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
                .shade(false)
                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
                .end();
    }

    public void frostedCross(Block block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean snowy = state.getValue(BlockStateProperties.SNOWY);
            String prefix = snowy ? "frosted_" : "";
            ModelFile grass = this.models().cross(prefix + this.name(block), this.texture(prefix + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(grass).build();
        });
    }

    public void bush(Block block) {
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(this.bush(block, this.name(block) + "_stem")));
    }

    public void berryBush(Block block, Block stem) {
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(this.bush(block, this.name(stem))));
    }

    public ModelFile bush(Block block, String stem) {
        return this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("particle", this.texture(this.name(block), "natural/")).texture("bush", this.texture(this.name(block), "natural/")).texture("stem", this.texture(stem, "natural/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end()
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
    }

    public void pottedBush(Block bush, String location) {
        this.pottedBush(bush, this.name(bush) + "_stem", location);
    }

    public void pottedBush(Block bush, Block stem, String location) {
        this.pottedBush(bush, this.name(stem), location);
    }

    public void pottedBush(Block bush, String stem, String location) {
        ModelFile pot = this.pottedStemModel(bush, stem, location)
                .texture("stem", this.modLoc("block/" + location + stem)).texture("bush", this.modLoc("block/" + location + this.name(bush)))
                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
                .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end()
                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(bush).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedStem(Block stem, String location) {
        ModelFile pot = this.pottedStemModel(stem, this.name(stem), location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(stem).partialState().addModels(new ConfiguredModel(pot));
    }

    public BlockModelBuilder pottedStemModel(Block block, String stem, String location) {
        return models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("particle", this.mcLoc("block/flower_pot")).texture("stem", this.modLoc("block/" + location + stem)).texture("dirt", this.mcLoc("block/dirt")).texture("flowerpot", this.mcLoc("block/flower_pot"))
                .element().from(5.0F, 0.0F, 5.0F).to(6.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(10.0F, 0.0F, 5.0F).to(11.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 5.0F).to(10.0F, 6.0F, 6.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 10.0F).to(10.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 6.0F).to(10.0F, 4.0F, 10.0F)
                .face(Direction.UP).uvs(6.0F, 6.0F, 10.0F, 10.0F).texture("#dirt").end()
                .face(Direction.DOWN).uvs(6.0F, 12.0F, 10.0F, 16.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(7.0F, 4.0F, 8.0F).to(9.0F, 6.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
                .element().from(1.0F, 6.0F, 8.0F).to(15.0F, 16.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end()
                .element().from(8.0F, 4.0F, 7.0F).to(8.0F, 6.0F, 9.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
                .element().from(8.0F, 6.0F, 1.0F).to(8.0F, 16.0F, 15.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end();
    }

    public void orangeTree(Block block) {
        String blockName = this.name(block);
        this.getVariantBuilder(block).forAllStates((state) -> {
            DoubleBlockHalf halfProperty = state.getValue(OrangeTreeBlock.HALF);
            int age = state.getValue(OrangeTreeBlock.AGE);
            boolean lower = halfProperty == DoubleBlockHalf.LOWER;
            int bottomAge = age == 3 ? 2 : age;
            int topAge = Math.max(age, 2);
            String halfString = lower ? "_bottom_" : "_top_";
            ResourceLocation location = lower ? this.extend(this.texture(blockName, "natural/"), halfString + bottomAge) : this.extend(this.texture(blockName, "natural/"), halfString + topAge);
            ModelFile model = this.models().cross(blockName + (lower ? (halfString + bottomAge) : (halfString + topAge)), location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void pottedOrangeTree(Block block, Block tree) {
        ModelFile pot = this.models().withExistingParent(this.name(block), this.mcLoc("block/flower_pot_cross")).texture("plant", this.modLoc("block/natural/" + this.name(tree) + "_bottom_0")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void valkyrieSprout(Block block) {
        String blockName = this.name(block);
        this.getVariantBuilder(block).forAllStates((state) -> {
            int age = state.getValue(ValkyrieSproutBlock.AGE);
            ResourceLocation location = this.extend(this.texture(blockName, "natural/"), "_" + age);
            ModelFile model = this.models().cross(blockName + "_" + age, location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void brettlPlant(Block block) {
        String blockName = this.name(block);
        ModelFile normal = this.models().cross(blockName, this.extend(this.texture(this.name(block), "natural/"), "")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        ModelFile grown = this.models().cross(blockName + "_grown", this.extend(this.texture(this.name(block), "natural/"), "_grown")).renderType(ResourceLocation.withDefaultNamespace("cutout"));

        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            if (state.getValue(BrettlPlantBlock.GROWN)) {
                return ConfiguredModel.builder().modelFile(grown).build();
            } else {
                return ConfiguredModel.builder().modelFile(normal).build();
            }
        });
    }

    public void twig(Block block, Block log) {
        String blockName = this.name(block);
        ResourceLocation texture = this.texture(this.name(log), "natural/");
        this.getVariantBuilder(block).forAllStates((state) -> {
            Direction direction = state.getValue(RockBlock.FACING);
            int twigCount = state.getValue(TwigBlock.AMOUNT);
            int offset = 0;
            switch (direction) {
                case SOUTH -> offset = 180;
                case WEST -> offset = 270;
                case EAST -> offset = 90;
            }
            ModelFile model;
            if (twigCount == 2) {
                model = models().getBuilder(blockName + "_2").texture("particle", texture).texture("side", texture).texture("top", texture + "_top")
                        .element().from(11, 0, 2).to(13, 2, 13)
                        .rotation().angle(0).axis(Direction.Axis.X).origin(9, 0, 7).end()
                        .face(Direction.NORTH).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
                        .face(Direction.EAST).uvs(2, 2, 4, 13).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
                        .face(Direction.SOUTH).uvs(7, 7, 9, 9).texture("#top").end()
                        .face(Direction.WEST).uvs(6, 2, 8, 13).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
                        .face(Direction.UP).uvs(4, 2, 6, 13).texture("#side").end()
                        .face(Direction.DOWN).uvs(0, 2, 2, 13).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#side").end()
                        .end()
                        .element().from(2, -1, 9).to(8, 1, 11)
                        .rotation().angle(0).axis(Direction.Axis.Y).origin(8, 0, 8).end()
                        .face(Direction.NORTH).uvs(2, 4, 4, 8).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
                        .face(Direction.EAST).uvs(7, 7, 9, 9).texture("#top").end()
                        .face(Direction.SOUTH).uvs(6, 2, 8, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
                        .face(Direction.WEST).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
                        .face(Direction.UP).uvs(4, 2, 6, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
                        .face(Direction.DOWN).uvs(0, 2, 2, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
                        .end();
            } else {
                model = models().getBuilder(blockName + "_1").texture("particle", texture).texture("side", texture).texture("top", texture + "_top")
                        .element().from(7, 0, 2).to(9, 2, 13)
                        .rotation().angle(0).axis(Direction.Axis.X).origin(9, 0, 7).end()
                        .face(Direction.NORTH).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
                        .face(Direction.EAST).uvs(2, 2, 4, 13).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
                        .face(Direction.SOUTH).uvs(7, 7, 9, 9).texture("#top").end()
                        .face(Direction.WEST).uvs(6, 2, 8, 13).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
                        .face(Direction.UP).uvs(4, 2, 6, 13).texture("#side").end()
                        .face(Direction.DOWN).uvs(0, 2, 2, 13).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#side").end()
                        .end();
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
        });
    }

    public void rock(Block block, Block stone) {
        String blockName = this.name(block);
        ResourceLocation texture = this.texture(this.name(stone), "natural/");
        this.getVariantBuilder(block).forAllStates((state) -> {
            Direction direction = state.getValue(RockBlock.FACING);
            int rockCount = state.getValue(RockBlock.AMOUNT);
            int offset = 0;
            switch (direction) {
                case SOUTH -> offset = 180;
                case WEST -> offset = 270;
                case EAST -> offset = 90;
            }
            switch (rockCount) {
                case 3 -> {
                    ModelFile model = models().getBuilder(blockName + "_3").texture("particle", texture).texture("texture", texture)
                            .element().from(2, 0, 2).to(8, 3, 8)
                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
                            .end()
                            .element().from(10, 0, 6).to(14, 2, 10)
                            .face(Direction.NORTH).uvs(0, 4, 4, 6).texture("#texture").end()
                            .face(Direction.EAST).uvs(4, 4, 8, 6).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 4, 4, 6).texture("#texture").end()
                            .face(Direction.WEST).uvs(4, 4, 8, 6).texture("#texture").end()
                            .face(Direction.UP).uvs(4, 0, 8, 4).texture("#texture").end()
                            .face(Direction.DOWN).uvs(4, 8, 8, 12).texture("#texture").end()
                            .end()
                            .element().from(7, 0, 12).to(9, 1, 14)
                            .face(Direction.NORTH).uvs(0, 2, 2, 3).texture("#texture").end()
                            .face(Direction.EAST).uvs(2, 2, 4, 3).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 2, 2, 3).texture("#texture").end()
                            .face(Direction.WEST).uvs(2, 2, 4, 3).texture("#texture").end()
                            .face(Direction.UP).uvs(2, 0, 4, 2).texture("#texture").end()
                            .face(Direction.DOWN).uvs(2, 3, 4, 5).texture("#texture").end()
                            .end();
                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
                }
                case 2 -> {
                    ModelFile model = models().getBuilder(blockName + "_2").texture("particle", texture).texture("texture", texture)
                            .element().from(2, 0, 2).to(8, 3, 8)
                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
                            .end()
                            .element().from(10, 0, 9).to(14, 2, 13)
                            .face(Direction.NORTH).uvs(0, 4, 4, 6).texture("#texture").end()
                            .face(Direction.EAST).uvs(4, 4, 8, 6).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 4, 4, 6).texture("#texture").end()
                            .face(Direction.WEST).uvs(4, 4, 8, 6).texture("#texture").end()
                            .face(Direction.UP).uvs(4, 0, 8, 4).texture("#texture").end()
                            .face(Direction.DOWN).uvs(4, 8, 8, 12).texture("#texture").end()
                            .end();
                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
                }
                default -> {
                    ModelFile model = models().getBuilder(blockName + "_1").texture("particle", texture).texture("texture", texture)
                            .element().from(5, 0, 5).to(11, 3, 11)
                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
                            .end();
                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
                }
            }
        });
    }

    public void doorBlock(DoorBlock block) {
        this.doorBlockWithRenderType(block, "cutout");
    }

    public void doorBlockWithRenderType(DoorBlock block, String renderType) {
        this.doorBlockInternalWithRenderType(block, this.name(block), ResourceLocation.tryParse(renderType));
    }

    private void doorBlockInternalWithRenderType(DoorBlock block, String baseName, ResourceLocation renderType) {
        ModelFile bottomLeft = this.door(block, baseName + "_bottom_left", "door_bottom_left").renderType(renderType);
        ModelFile bottomLeftOpen = this.door(block, baseName + "_bottom_left_open", "door_bottom_left_open").renderType(renderType);
        ModelFile bottomRight = this.door(block, baseName + "_bottom_right", "door_bottom_right").renderType(renderType);
        ModelFile bottomRightOpen = this.door(block, baseName + "_bottom_right_open", "door_bottom_right_open").renderType(renderType);
        ModelFile topLeft = this.door(block, baseName + "_top_left", "door_top_left").renderType(renderType);
        ModelFile topLeftOpen = this.door(block, baseName + "_top_left_open", "door_top_left_open").renderType(renderType);
        ModelFile topRight = this.door(block, baseName + "_top_right", "door_top_right").renderType(renderType);
        ModelFile topRightOpen = this.door(block, baseName + "_top_right_open", "door_top_right_open").renderType(renderType);
        this.doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
    }

    private BlockModelBuilder door(DoorBlock block, String name, String model) {
        return this.models().withExistingParent(name,  this.modLoc("block/" + model)).texture("door", this.texture(this.name(block), "construction/")).texture("bottom", this.texture(this.name(block), "construction/", "_bottom")).texture("top", this.texture(this.name(block), "construction/", "_top"));
    }

    public void secretTrapdoorBlock(TrapDoorBlock block, ResourceLocation texture) {
        this.secretTrapdoorBlockInternal(block, this.name(block), texture);
    }

    private void secretTrapdoorBlockInternal(TrapDoorBlock block, String baseName, ResourceLocation texture) {
        ModelFile bottom = this.models().withExistingParent(baseName + "_bottom", this.modLoc("block/template_orientable_secret_trapdoor_bottom")).texture("texture", texture);
        ModelFile top = this.models().withExistingParent(baseName + "_top", this.modLoc("block/template_orientable_secret_trapdoor_top")).texture("texture", texture);
        ModelFile open = this.models().withExistingParent(baseName + "_open", this.modLoc("block/template_orientable_secret_trapdoor_open")).texture("texture", texture);
        this.trapdoorBlock(block, bottom, top, open, true);
    }

    public void crudeScatterglassPane(IronBarsBlock block, HalfTransparentBlock glass, String location) {
        this.paneBlockWithRenderType(block, this.texture(this.name(glass), location), this.extend(this.texture(this.name(block), location), "_top"), ResourceLocation.withDefaultNamespace("translucent"));
    }

    public void carpet(Block block, Block baseBlock, String location) {
        simpleBlock(block, models().singleTexture(name(block), mcLoc("block/carpet"), "wool", texture(location + name(baseBlock))));
    }

    public void moaEgg(Block block) {
        ModelFile moaEgg = this.models().withExistingParent(this.name(block), modLoc("block/template_moa_egg"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(moaEgg));
    }

    public void torchBlock(Block block, Block wall) {
        ModelFile torch = this.models().withExistingParent(this.name(block), this.modLoc("block/template_tall_torch")).texture("torch", this.texture(this.name(block), "utility/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        ModelFile wallTorch = this.models().withExistingParent(this.name(wall), this.modLoc("block/template_tall_wall_torch")).texture("torch", this.texture(this.name(block), "utility/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
        this.simpleBlock(block, torch);
        getVariantBuilder(wall).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(wallTorch)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
                        .build());
    }

    public void skyrootCraftingTable(Block block, Block baseBlock, String location) {
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/" + location + this.name(baseBlock));
        ModelFile workbench = this.models().cube(this.name(block),
                        baseTexture,
                        this.extend(this.texture(this.name(block), "utility/"), "_top"),
                        this.extend(this.texture(this.name(block), "utility/"), "_front"),
                        this.extend(this.texture(this.name(block), "utility/"), "_side"),
                        this.extend(this.texture(this.name(block), "utility/"), "_front"),
                        this.extend(this.texture(this.name(block), "utility/"), "_side"))
                .texture("particle", this.extend(this.texture(this.name(block), "utility/"), "_front"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(workbench));
    }


    public void holystoneFurnace(Block block) {
        String blockName = this.name(block);
        ResourceLocation side = this.extend(this.texture(this.name(block), "utility/"), "_side");
        ResourceLocation front_on =  this.extend(this.texture(this.name(block), "utility/"), "_front_on");
        ResourceLocation front =  this.extend(this.texture(this.name(block), "utility/"), "_front");
        ResourceLocation top = this.extend(this.texture(this.name(block), "utility/"), "_top");
        ModelFile normal = this.models().orientable(blockName, side, front, top);
        ModelFile lit = this.models().orientable(blockName + "_on", side, front_on, top);
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(AbstractFurnaceBlock.FACING);
            if (state.getValue(AbstractFurnaceBlock.LIT))
                switch (direction) {
                    case NORTH -> {
                        return ConfiguredModel.builder().modelFile(lit).build();
                    }
                    case SOUTH -> {
                        return ConfiguredModel.builder().modelFile(lit).rotationY(180).build();
                    }
                    case WEST -> {
                        return ConfiguredModel.builder().modelFile(lit).rotationY(270).build();
                    }
                    case EAST -> {
                        return ConfiguredModel.builder().modelFile(lit).rotationY(90).build();
                    }
                }
            else
                switch (direction) {
                    case NORTH -> {
                        return ConfiguredModel.builder().modelFile(normal).build();
                    }
                    case SOUTH -> {
                        return ConfiguredModel.builder().modelFile(normal).rotationY(180).build();
                    }
                    case WEST -> {
                        return ConfiguredModel.builder().modelFile(normal).rotationY(270).build();
                    }
                    case EAST -> {
                        return ConfiguredModel.builder().modelFile(normal).rotationY(90).build();
                    }
                }
            return ConfiguredModel.builder().build();
        });
    }

    public void altar(Block block) {
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(AltarBlock.FACING);
            boolean charging = state.getValue(AltarBlock.CHARGING);
            boolean blasting = state.getValue(AltarBlock.BLASTING);
            int degrees = 0;
            switch (direction) {
                case EAST -> degrees = 90;
                case SOUTH -> degrees = 180;
                case WEST -> degrees = 270;
            }
            if (blasting) {
                ModelFile model = this.models().withExistingParent(this.name(block) + "_blasting", this.modLoc("block/template_altar"))
                        .texture("altar", this.texture(this.name(block) + "_blasting", "utility/"))
                        .texture("particle", this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"))
                        .renderType("cutout");
                return ConfiguredModel.builder().modelFile(model).rotationY(degrees).build();
            } else if (charging) {
                ModelFile model = this.models().withExistingParent(this.name(block) + "_charging", this.modLoc("block/template_altar"))
                        .texture("altar", this.texture(this.name(block) + "_charging", "utility/"))
                        .texture("particle", this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"))
                        .renderType("cutout");
                return ConfiguredModel.builder().modelFile(model).rotationY(degrees).build();
            } else {
                ModelFile model = this.models().withExistingParent(this.name(block), this.modLoc("block/template_altar"))
                        .texture("altar", this.texture(this.name(block), "utility/"))
                        .texture("particle", this.texture(this.name(AetherIIBlocks.HOLYSTONE.get()), "natural/"))
                        .renderType("cutout");
                return ConfiguredModel.builder().modelFile(model).rotationY(degrees).build();
            }
        });
    }

    public void artisansBench(Block block) {
        ModelFile model = models().withExistingParent(name(block), modLoc("block/template_artisans_bench"))
                .texture("bench", texture(name(block), "utility/"))
                .texture("particle", texture(name(AetherIIBlocks.HOLYSTONE_BRICKS.get()), "construction/"))
                .renderType("cutout");
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(ArtisansBenchBlock.FACING);
            switch (direction) {
                case NORTH -> {
                    return ConfiguredModel.builder().modelFile(model).build();
                }
                case EAST -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(90).build();
                }
                case SOUTH -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(180).build();
                }
                case WEST -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(270).build();
                }
            }
            return ConfiguredModel.builder().build();
        });
    }

    public void arkeniumForge(Block block) {
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(ArkeniumForgeBlock.FACING);
            boolean charged = state.getValue(ArkeniumForgeBlock.CHARGED);
            ModelFile model = this.models().withExistingParent(this.name(block), modLoc("block/template_arkenium_forge"))
                    .texture("forge", this.texture(name(block), "utility/"))
                    .texture("particle", this.texture(name(AetherIIBlocks.ARKENIUM_BLOCK.get()), "construction/"))
                    .renderType("cutout");
            if (charged) {
                model = this.models().withExistingParent(this.name(block) + "_charged", modLoc("block/template_arkenium_forge"))
                        .texture("forge", this.texture(name(block) + "_charged", "utility/"))
                        .texture("particle", this.texture(name(AetherIIBlocks.ARKENIUM_BLOCK.get()), "construction/"))
                        .renderType("cutout");
            }
            switch (direction) {
                case NORTH -> {
                    return ConfiguredModel.builder().modelFile(model).build();
                }
                case EAST -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(90).build();
                }
                case SOUTH -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(180).build();
                }
                case WEST -> {
                    return ConfiguredModel.builder().modelFile(model).rotationY(270).build();
                }
            }
            return ConfiguredModel.builder().build();
        });
    }

    public void decorativeBlock(Block block, Block endBlock) {
        ModelFile masonryBlock = this.models().cubeColumn(this.name(block), this.texture(this.name(block), "decorative/"), this.texture(this.name(endBlock), "decorative/"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(masonryBlock));
    }

    public void decorativeFacingPillar(FacingPillarBlock block, Block endBlock) {
        ResourceLocation side = this.texture(this.name(block), "decorative/");
        ResourceLocation bottom = this.texture(this.name(endBlock), "decorative/");
        ResourceLocation top = this.texture(this.name(endBlock), "decorative/");
        this.facingPillar(block, side, bottom, top);
    }

    public void facingPillar(FacingPillarBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        ModelFile vertical = this.models().cubeColumn(this.name(block), side, bottom).texture("up", top);
        ModelFile horizontal = this.models().cubeColumnHorizontal(this.name(block) + "_horizontal", side, bottom).texture("up", top);
        this.getVariantBuilder(block)
                .partialState().with(FacingPillarBlock.FACING, Direction.DOWN).modelForState().modelFile(vertical).rotationX(180).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.EAST).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.NORTH).modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.SOUTH).modelForState().modelFile(horizontal).rotationX(90).rotationY(180).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.UP).modelForState().modelFile(vertical).addModel()
                .partialState().with(FacingPillarBlock.FACING, Direction.WEST).modelForState().modelFile(horizontal).rotationX(90).rotationY(270).addModel();
    }

    public void skyrootChest(Block block) {
        ModelFile chest = this.models().cubeAll(this.name(block), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/construction/skyroot_planks"));
        this.chest(block, chest);
    }

    public void skyrootLadder(LadderBlock block) {
        ResourceLocation location = this.texture(this.name(block), "construction/");
        ModelFile ladder = models().withExistingParent(this.name(block), this.mcLoc("block/block")).renderType(ResourceLocation.withDefaultNamespace("cutout")).ao(false)
                .texture("particle", location).texture("texture", location)
                .element().from(0.0F, 0.0F, 15.2F).to(16.0F, 16.0F, 15.2F).shade(false)
                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#texture").end()
                .face(Direction.SOUTH).uvs(16.0F, 0.0F, 0.0F, 16.0F).texture("#texture").end()
                .end();
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction direction = state.getValue(LadderBlock.FACING);
            return ConfiguredModel.builder().modelFile(ladder).rotationY((int) (direction.toYRot() + 180) % 360).build();
        }, LadderBlock.WATERLOGGED);
    }

    public void bed(Block block, Block dummyBlock) {
        ModelFile head = this.models().cubeAll(this.name(block) + "_head", this.texture(this.name(dummyBlock), "construction/"));
        ModelFile foot = this.models().cubeAll(this.name(block) + "_foot", this.texture(this.name(dummyBlock), "construction/"));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BedPart part = state.getValue(BlockStateProperties.BED_PART);
            return ConfiguredModel.builder()
                    .modelFile(part == BedPart.HEAD ? head : foot)
                    .rotationY((((int) dir.toYRot()) + 180) % 360)
                    .build();
        }, BedBlock.OCCUPIED);
    }

    public void hangingSignBlock(CeilingHangingSignBlock signBlock, WallHangingSignBlock wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public void campfire(Block block) {
        ResourceLocation texture = this.texture(this.name(block), "furniture/");
        ResourceLocation particleTexture = this.texture(this.name(AetherIIBlocks.HOLYSTONE_BRICKS.get()), "construction/");
        this.getVariantBuilder(block).forAllStatesExcept((blockState) -> {
            Direction partFacing = blockState.getValue(OutpostCampfireBlock.PART_FACING);
            ModelFile model = this.models().withExistingParent(this.name(block) + "_" + partFacing.name().toLowerCase(), this.modLoc("template_" + this.name(block) + "_" + partFacing.name().toLowerCase()))
                    .texture("texture", texture)
                    .texture("particle", particleTexture)
                    .renderType(ResourceLocation.withDefaultNamespace("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        }, OutpostCampfireBlock.LIT);
    }
}
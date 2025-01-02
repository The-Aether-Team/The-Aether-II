package com.aetherteam.aetherii.data.providers;

//import com.aetherteam.aetherii.AetherII;
//import com.aetherteam.aetherii.block.AetherIIBlocks;
//import com.aetherteam.aetherii.block.construction.AetherFarmBlock;
//import com.aetherteam.aetherii.block.furniture.OutpostCampfireBlock;
//import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
//import com.aetherteam.aetherii.block.natural.*;
//import com.aetherteam.aetherii.block.utility.AltarBlock;
//import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
//import com.aetherteam.aetherii.block.utility.ArtisansBenchBlock;
//import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
//import net.minecraft.core.Direction;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.block.*;
//import net.minecraft.world.level.block.state.properties.*;
//import net.neoforged.neoforge.client.model.generators.*;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//import org.apache.commons.lang3.ArrayUtils;
//
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.IntStream;
//
//public abstract class AetherIIBlockStateProvider extends NitrogenBlockStateProvider {
//    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
//        super(output, id, helper);
//    }
//
//    public void mossVines(Block block) {
//        ModelFile normalModel = this.models().getBuilder(this.name(block))
//                .ao(false)
//                .texture("vine", this.texture(this.name(block), "natural/"))
//                .texture("particle", this.texture(this.name(block), "natural/"))
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
//                .element().from(0.0F, 0.0F, 0.2F).to(16.0F, 16.0F, 0.2F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(0.0F, 0.0F, 0.01F).to(16.0F, 16.0F, 0.01F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end();
//        ModelFile bottomModel  = this.models().getBuilder(this.name(block) + "_bottom")
//                .ao(false)
//                .texture("vine", this.extend(texture(this.name(block), "natural/"), "_bottom"))
//                .texture("particle", this.extend(texture(this.name(block), "natural/"), "_bottom"))
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
//                .element().from(0.0F, 0.0F, 0.2F).to(16.0F, 16.0F, 0.2F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(0.0F, 0.0F, 0.01F).to(16.0F, 16.0F, 0.01F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end();
//
//        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
//        List<BooleanProperty> directions = List.of(BottomedVineBlock.NORTH, BottomedVineBlock.EAST, BottomedVineBlock.SOUTH, BottomedVineBlock.WEST, BottomedVineBlock.UP);
//        int y = 0;
//        for (BooleanProperty direction : directions) {
//            if (direction != BottomedVineBlock.UP) {
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                y += 90;
//            } else {
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//            }
//        }
//    }
//
//    public void ambrelinnMossVines(Block block) {
//        ModelFile normalModel = this.models().getBuilder(this.name(block))
//                .ao(false)
//                .texture("vine", this.texture(this.name(block), "natural/"))
//                .texture("particle", this.texture(this.name(block), "natural/"))
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
//                .element().from(0.0F, 0.0F, 0.2F).to(16.0F, 16.0F, 0.2F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(0.0F, 0.0F, 0.01F).to(16.0F, 16.0F, 0.01F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(4.0F, 0.0F, -4.2F).to(4.0F, 16.0F, 4.2F)
//                .rotation().angle(0).axis(Direction.Axis.Y).origin(4.0F, 0.0F, -4.0F).end()
//                .face(Direction.NORTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.EAST).uvs(8, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.WEST).uvs(0, 0, 8, 16).texture("#vine").end()
//                .face(Direction.UP).uvs(0, 0, 8, 0).texture("#vine").end()
//                .face(Direction.DOWN).uvs(0, 0, 8, 0).texture("#vine").end()
//                .end()
//                .element().from(12.0F, 0.0F, -4.2F).to(12.0F, 16.0F, 4.2F)
//                .rotation().angle(0).axis(Direction.Axis.Y).origin(12.0F, 0.0F, -4.0F).end()
//                .face(Direction.NORTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.EAST).uvs(16, 0, 8, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.WEST).uvs(8, 0, 16, 16).texture("#vine").end()
//                .face(Direction.UP).uvs(0, 0, 8, 0).texture("#vine").end()
//                .face(Direction.DOWN).uvs(0, 0, 8, 0).texture("#vine").end()
//                .end();
//        ModelFile bottomModel  = this.models().getBuilder(this.name(block) + "_bottom")
//                .ao(false)
//                .texture("vine", this.extend(texture(this.name(block), "natural/"), "_bottom"))
//                .texture("particle", this.extend(texture(this.name(block), "natural/"), "_bottom"))
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
//                .element().from(0.0F, 0.0F, 0.2F).to(16.0F, 16.0F, 0.2F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(0.0F, 0.0F, 0.01F).to(16.0F, 16.0F, 0.01F)
//                .face(Direction.NORTH).uvs(16, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#vine").end()
//                .end()
//                .element().from(4.0F, 0.0F, -4.2F).to(4.0F, 16.0F, 4.2F)
//                .rotation().angle(0).axis(Direction.Axis.Y).origin(4.0F, 0.0F, -4.0F).end()
//                .face(Direction.NORTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.EAST).uvs(8, 0, 0, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.WEST).uvs(0, 0, 8, 16).texture("#vine").end()
//                .face(Direction.UP).uvs(0, 0, 8, 0).texture("#vine").end()
//                .face(Direction.DOWN).uvs(0, 0, 8, 0).texture("#vine").end()
//                .end()
//                .element().from(12.0F, 0.0F, -4.2F).to(12.0F, 16.0F, 4.2F)
//                .rotation().angle(0).axis(Direction.Axis.Y).origin(12.0F, 0.0F, -4.0F).end()
//                .face(Direction.NORTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.EAST).uvs(16, 0, 8, 16).texture("#vine").end()
//                .face(Direction.SOUTH).uvs(0, 0, 0, 16).texture("#vine").end()
//                .face(Direction.WEST).uvs(8, 0, 16, 16).texture("#vine").end()
//                .face(Direction.UP).uvs(0, 0, 8, 0).texture("#vine").end()
//                .face(Direction.DOWN).uvs(0, 0, 8, 0).texture("#vine").end()
//                .end();
//
//        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
//        List<BooleanProperty> directions = List.of(BottomedVineBlock.NORTH, BottomedVineBlock.EAST, BottomedVineBlock.SOUTH, BottomedVineBlock.WEST, BottomedVineBlock.UP);
//        int y = 0;
//        for (BooleanProperty direction : directions) {
//            if (direction != BottomedVineBlock.UP) {
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationY(y).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationY(y).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                y += 90;
//            } else {
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(normalModel).uvLock(true).rotationX(270).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, ArrayUtils.toObject(IntStream.range(0, 25).toArray()))
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
//                        .condition(direction, true)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//                builder = builder.part()
//                        .modelFile(bottomModel).uvLock(true).rotationX(270).addModel()
//                        .condition(BottomedVineBlock.EAST, false)
//                        .condition(BottomedVineBlock.NORTH, false)
//                        .condition(BottomedVineBlock.SOUTH, false)
//                        .condition(BottomedVineBlock.UP, false)
//                        .condition(BottomedVineBlock.WEST, false)
//                        .condition(BottomedVineBlock.AGE, 25)
//                        .end();
//            }
//        }
//    }
//
//    public void tintedFern(Block block) {
//        this.getVariantBuilder(block).forAllStates((state) -> {
//            AetherTallGrassBlock.GrassType type = state.getValue(AetherTallGrassBlock.TYPE);
//            ModelFile model;
//            switch (type) {
//                case SNOWY -> model = this.models().cross("frosted_" + this.name(block), this.texture("frosted_" + this.name(block), "natural/"))
//                        .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//                case ENCHANTED -> model = this.models().cross("enchanted_" + this.name(block), this.texture("enchanted_" + this.name(block), "natural/"))
//                        .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//                default -> model = this.models().withExistingParent(this.name(block), this.mcLoc("block/tinted_cross"))
//                        .texture("cross", this.texture(this.name(block), "natural/"))
//                        .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//            }
//            return ConfiguredModel.builder().modelFile(model).build();
//        });
//    }
//
//    public void shortGrass(Block block) {
//        this.getVariantBuilder(block).forAllStates((state) -> {
//            AetherTallGrassBlock.GrassType type = state.getValue(AetherTallGrassBlock.TYPE);
//            ModelFile grass;
//            switch (type) {
//                case SNOWY -> grass = this.models().cross("frosted_" + this.name(block), this.texture("frosted_" + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//                case ENCHANTED -> grass = this.models().cross("enchanted_" + this.name(block), this.texture("enchanted_" + this.name(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//                default -> grass = this.triTintedCross(this.name(block))
//                        .texture("particle", this.texture(this.name(block), "natural/"))
//                        .texture("cross_1", this.extend(this.texture(this.name(block), "natural/"), "_1"))
//                        .texture("cross_2", this.extend(this.texture(this.name(block), "natural/"), "_2"))
//                        .texture("cross_3", this.extend(this.texture(this.name(block), "natural/"), "_3"));
//            }
//            return ConfiguredModel.builder().modelFile(grass).build();
//        });
//    }
//
//    public ModelBuilder<BlockModelBuilder> triTintedCross(String name) {
//        return this.models().getBuilder(name)
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"))
//                .ao(false)
//                .element()
//                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
//                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
//                .end()
//                .element()
//                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
//                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_1").tintindex(0).end()
//                .end()
//                .element()
//                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
//                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
//                .end()
//                .element()
//                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
//                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_2").tintindex(1).end()
//                .end()
//                .element()
//                .from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.NORTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
//                .face(Direction.SOUTH).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
//                .end()
//                .element()
//                .from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
//                .rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end()
//                .shade(false)
//                .face(Direction.WEST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
//                .face(Direction.EAST).uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#cross_3").tintindex(2).end()
//                .end();
//    }
//
//    public void bush(Block block) {
//        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(this.bush(block, this.name(block) + "_stem")));
//    }
//
//    public void berryBush(Block block, Block stem) {
//        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(this.bush(block, this.name(stem))));
//    }
//
//    public ModelFile bush(Block block, String stem) {
//        return this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
//                .texture("particle", this.texture(this.name(block), "natural/")).texture("bush", this.texture(this.name(block), "natural/")).texture("stem", this.texture(stem, "natural/"))
//                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
//                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
//                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end()
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//    }
//
//    public void pottedBush(Block bush, String location) {
//        this.pottedBush(bush, this.name(bush) + "_stem", location);
//    }
//
//    public void pottedBush(Block bush, Block stem, String location) {
//        this.pottedBush(bush, this.name(stem), location);
//    }
//
//    public void pottedBush(Block bush, String stem, String location) {
//        ModelFile pot = this.pottedStemModel(bush, stem, location)
//                .texture("stem", this.modLoc("block/" + location + stem)).texture("bush", this.modLoc("block/" + location + this.name(bush)))
//                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
//                .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
//                .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
//                .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
//                .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
//                .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
//                .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end()
//                .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//        this.getVariantBuilder(bush).partialState().addModels(new ConfiguredModel(pot));
//    }
//
//    public void pottedStem(Block stem, String location) {
//        ModelFile pot = this.pottedStemModel(stem, this.name(stem), location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//        this.getVariantBuilder(stem).partialState().addModels(new ConfiguredModel(pot));
//    }
//
//    public BlockModelBuilder pottedStemModel(Block block, String stem, String location) {
//        return models().withExistingParent(this.name(block), this.mcLoc("block/block"))
//                .texture("particle", this.mcLoc("block/flower_pot")).texture("stem", this.modLoc("block/" + location + stem)).texture("dirt", this.mcLoc("block/dirt")).texture("flowerpot", this.mcLoc("block/flower_pot"))
//                .element().from(5.0F, 0.0F, 5.0F).to(6.0F, 6.0F, 11.0F)
//                .face(Direction.NORTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.SOUTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.UP).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").end()
//                .face(Direction.DOWN).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
//                .element().from(10.0F, 0.0F, 5.0F).to(11.0F, 6.0F, 11.0F)
//                .face(Direction.NORTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.SOUTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.UP).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").end()
//                .face(Direction.DOWN).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
//                .element().from(6.0F, 0.0F, 5.0F).to(10.0F, 6.0F, 6.0F)
//                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.UP).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").end()
//                .face(Direction.DOWN).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
//                .element().from(6.0F, 0.0F, 10.0F).to(10.0F, 6.0F, 11.0F)
//                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
//                .face(Direction.UP).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").end()
//                .face(Direction.DOWN).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
//                .element().from(6.0F, 0.0F, 6.0F).to(10.0F, 4.0F, 10.0F)
//                .face(Direction.UP).uvs(6.0F, 6.0F, 10.0F, 10.0F).texture("#dirt").end()
//                .face(Direction.DOWN).uvs(6.0F, 12.0F, 10.0F, 16.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
//                .element().from(7.0F, 4.0F, 8.0F).to(9.0F, 6.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
//                .face(Direction.NORTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
//                .face(Direction.SOUTH).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
//                .element().from(1.0F, 6.0F, 8.0F).to(15.0F, 16.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
//                .face(Direction.NORTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
//                .face(Direction.SOUTH).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end()
//                .element().from(8.0F, 4.0F, 7.0F).to(8.0F, 6.0F, 9.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
//                .face(Direction.EAST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end()
//                .face(Direction.WEST).uvs(7.0F, 14.0F, 9.0F, 16.0F).texture("#stem").end().end()
//                .element().from(8.0F, 6.0F, 1.0F).to(8.0F, 16.0F, 15.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
//                .face(Direction.EAST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end()
//                .face(Direction.WEST).uvs(1.0F, 4.0F, 15.0F, 14.0F).texture("#stem").end().end();
//    }
//
//    public void orangeTree(Block block) {
//        String blockName = this.name(block);
//        this.getVariantBuilder(block).forAllStates((state) -> {
//            DoubleBlockHalf halfProperty = state.getValue(OrangeTreeBlock.HALF);
//            int age = state.getValue(OrangeTreeBlock.AGE);
//            boolean lower = halfProperty == DoubleBlockHalf.LOWER;
//            int bottomAge = age == 3 ? 2 : age;
//            int topAge = Math.max(age, 2);
//            String halfString = lower ? "_bottom_" : "_top_";
//            ResourceLocation location = lower ? this.extend(this.texture(blockName, "natural/"), halfString + bottomAge) : this.extend(this.texture(blockName, "natural/"), halfString + topAge);
//            ModelFile model = this.models().cross(blockName + (lower ? (halfString + bottomAge) : (halfString + topAge)), location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//            return ConfiguredModel.builder().modelFile(model).build();
//        });
//    }
//
//    public void pottedOrangeTree(Block block, Block tree) {
//        ModelFile pot = this.models().withExistingParent(this.name(block), this.mcLoc("block/flower_pot_cross")).texture("plant", this.modLoc("block/natural/" + this.name(tree) + "_bottom_0")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
//    }
//
//    public void valkyrieSprout(Block block) {
//        String blockName = this.name(block);
//        this.getVariantBuilder(block).forAllStates((state) -> {
//            int age = state.getValue(ValkyrieSproutBlock.AGE);
//            ResourceLocation location = this.extend(this.texture(blockName, "natural/"), "_" + age);
//            ModelFile model = this.models().cross(blockName + "_" + age, location).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//            return ConfiguredModel.builder().modelFile(model).build();
//        });
//    }
//
//    public void brettlPlant(Block block) {
//        String blockName = this.name(block);
//        ModelFile normal = this.models().cross(blockName, this.extend(this.texture(this.name(block), "natural/"), "")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//        ModelFile grown = this.models().cross(blockName + "_grown", this.extend(this.texture(this.name(block), "natural/"), "_grown")).renderType(ResourceLocation.withDefaultNamespace("cutout"));
//
//        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
//            if (state.getValue(BrettlPlantBlock.GROWN)) {
//                return ConfiguredModel.builder().modelFile(grown).build();
//            } else {
//                return ConfiguredModel.builder().modelFile(normal).build();
//            }
//        });
//    }
//
//    public void twig(Block block, Block log) {
//        String blockName = this.name(block);
//        ResourceLocation texture = this.texture(this.name(log), "natural/");
//        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
//            Direction direction = state.getValue(RockBlock.FACING);
//            int twigCount = state.getValue(TwigBlock.AMOUNT);
//            int offset = 0;
//            switch (direction) {
//                case SOUTH -> offset = 180;
//                case WEST -> offset = 270;
//                case EAST -> offset = 90;
//            }
//            ModelFile model;
//            if (twigCount == 2) {
//                model = models().getBuilder(blockName + "_2").texture("particle", texture).texture("side", texture).texture("top", texture + "_top")
//                        .element().from(11, 0, 2).to(13, 2, 13)
//                        .rotation().angle(0).axis(Direction.Axis.X).origin(9, 0, 7).end()
//                        .face(Direction.NORTH).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
//                        .face(Direction.EAST).uvs(2, 2, 4, 13).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
//                        .face(Direction.SOUTH).uvs(7, 7, 9, 9).texture("#top").end()
//                        .face(Direction.WEST).uvs(6, 2, 8, 13).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
//                        .face(Direction.UP).uvs(4, 2, 6, 13).texture("#side").end()
//                        .face(Direction.DOWN).uvs(0, 2, 2, 13).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#side").end()
//                        .end()
//                        .element().from(2, -1, 9).to(8, 1, 11)
//                        .rotation().angle(0).axis(Direction.Axis.Y).origin(8, 0, 8).end()
//                        .face(Direction.NORTH).uvs(2, 4, 4, 8).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
//                        .face(Direction.EAST).uvs(7, 7, 9, 9).texture("#top").end()
//                        .face(Direction.SOUTH).uvs(6, 2, 8, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
//                        .face(Direction.WEST).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
//                        .face(Direction.UP).uvs(4, 2, 6, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
//                        .face(Direction.DOWN).uvs(0, 2, 2, 8).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
//                        .end();
//            } else {
//                model = models().getBuilder(blockName + "_1").texture("particle", texture).texture("side", texture).texture("top", texture + "_top")
//                        .element().from(7, 0, 2).to(9, 2, 13)
//                        .rotation().angle(0).axis(Direction.Axis.X).origin(9, 0, 7).end()
//                        .face(Direction.NORTH).uvs(7, 7, 9, 9).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#top").end()
//                        .face(Direction.EAST).uvs(2, 2, 4, 13).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).texture("#side").end()
//                        .face(Direction.SOUTH).uvs(7, 7, 9, 9).texture("#top").end()
//                        .face(Direction.WEST).uvs(6, 2, 8, 13).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).texture("#side").end()
//                        .face(Direction.UP).uvs(4, 2, 6, 13).texture("#side").end()
//                        .face(Direction.DOWN).uvs(0, 2, 2, 13).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).texture("#side").end()
//                        .end();
//            }
//            return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
//        }, BlockStateProperties.WATERLOGGED);
//    }
//
//    public void rock(Block block, Block stone) {
//        String blockName = this.name(block);
//        ResourceLocation texture = this.texture(this.name(stone), "natural/");
//        this.getVariantBuilder(block).forAllStates((state) -> {
//            Direction direction = state.getValue(RockBlock.FACING);
//            int rockCount = state.getValue(RockBlock.AMOUNT);
//            int offset = 0;
//            switch (direction) {
//                case SOUTH -> offset = 180;
//                case WEST -> offset = 270;
//                case EAST -> offset = 90;
//            }
//            switch (rockCount) {
//                case 3 -> {
//                    ModelFile model = models().getBuilder(blockName + "_3").texture("particle", texture).texture("texture", texture)
//                            .element().from(2, 0, 2).to(8, 3, 8)
//                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
//                            .end()
//                            .element().from(10, 0, 6).to(14, 2, 10)
//                            .face(Direction.NORTH).uvs(0, 4, 4, 6).texture("#texture").end()
//                            .face(Direction.EAST).uvs(4, 4, 8, 6).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 4, 4, 6).texture("#texture").end()
//                            .face(Direction.WEST).uvs(4, 4, 8, 6).texture("#texture").end()
//                            .face(Direction.UP).uvs(4, 0, 8, 4).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(4, 8, 8, 12).texture("#texture").end()
//                            .end()
//                            .element().from(7, 0, 12).to(9, 1, 14)
//                            .face(Direction.NORTH).uvs(0, 2, 2, 3).texture("#texture").end()
//                            .face(Direction.EAST).uvs(2, 2, 4, 3).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 2, 2, 3).texture("#texture").end()
//                            .face(Direction.WEST).uvs(2, 2, 4, 3).texture("#texture").end()
//                            .face(Direction.UP).uvs(2, 0, 4, 2).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(2, 3, 4, 5).texture("#texture").end()
//                            .end();
//                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
//                }
//                case 2 -> {
//                    ModelFile model = models().getBuilder(blockName + "_2").texture("particle", texture).texture("texture", texture)
//                            .element().from(2, 0, 2).to(8, 3, 8)
//                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
//                            .end()
//                            .element().from(10, 0, 9).to(14, 2, 13)
//                            .face(Direction.NORTH).uvs(0, 4, 4, 6).texture("#texture").end()
//                            .face(Direction.EAST).uvs(4, 4, 8, 6).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 4, 4, 6).texture("#texture").end()
//                            .face(Direction.WEST).uvs(4, 4, 8, 6).texture("#texture").end()
//                            .face(Direction.UP).uvs(4, 0, 8, 4).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(4, 8, 8, 12).texture("#texture").end()
//                            .end();
//                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
//                }
//                default -> {
//                    ModelFile model = models().getBuilder(blockName + "_1").texture("particle", texture).texture("texture", texture)
//                            .element().from(5, 0, 5).to(11, 3, 11)
//                            .face(Direction.NORTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.EAST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.SOUTH).uvs(0, 6, 6, 9).texture("#texture").end()
//                            .face(Direction.WEST).uvs(6, 6, 12, 9).texture("#texture").end()
//                            .face(Direction.UP).uvs(6, 0, 12, 6).texture("#texture").end()
//                            .face(Direction.DOWN).uvs(6, 9, 12, 15).texture("#texture").end()
//                            .end();
//                    return ConfiguredModel.builder().modelFile(model).rotationY(offset).build();
//                }
//            }
//        });
//    }
//
//    public void skyrootChest(Block block) {
//        ModelFile chest = this.models().cubeAll(this.name(block), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/construction/skyroot_planks"));
//        this.chest(block, chest);
//    }
//
//
//    public void bed(Block block, Block dummyBlock) {
//        ModelFile head = this.models().cubeAll(this.name(block) + "_head", this.texture(this.name(dummyBlock), "construction/"));
//        ModelFile foot = this.models().cubeAll(this.name(block) + "_foot", this.texture(this.name(dummyBlock), "construction/"));
//        this.getVariantBuilder(block).forAllStatesExcept(state -> {
//            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
//            BedPart part = state.getValue(BlockStateProperties.BED_PART);
//            return ConfiguredModel.builder()
//                    .modelFile(part == BedPart.HEAD ? head : foot)
//                    .rotationY((((int) dir.toYRot()) + 180) % 360)
//                    .build();
//        }, BedBlock.OCCUPIED);
//    }

//
//    public void campfire(Block block) {
//        ResourceLocation texture = this.texture(this.name(block), "furniture/");
//        ResourceLocation particleTexture = this.texture(this.name(AetherIIBlocks.HOLYSTONE_BRICKS.get()), "construction/");
//        this.getVariantBuilder(block).forAllStatesExcept((blockState) -> {
//            Direction partFacing = blockState.getValue(OutpostCampfireBlock.PART_FACING);
//            ModelFile model = this.models().withExistingParent(this.name(block) + "_" + partFacing.name().toLowerCase(), this.modLoc("template_" + this.name(block) + "_" + partFacing.name().toLowerCase()))
//                    .texture("texture", texture)
//                    .texture("particle", particleTexture)
//                    .renderType(ResourceLocation.withDefaultNamespace("cutout"));
//            return ConfiguredModel.builder().modelFile(model).build();
//        }, OutpostCampfireBlock.LIT);
//    }
//}
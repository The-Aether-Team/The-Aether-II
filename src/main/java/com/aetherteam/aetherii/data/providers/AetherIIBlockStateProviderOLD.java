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
//}
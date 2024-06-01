package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.construction.AetherFarmBlock;
import com.aetherteam.aetherii.block.natural.AetherLeafPileBlock;
import com.aetherteam.aetherii.block.natural.OrangeTreeBlock;
import com.aetherteam.aetherii.block.natural.PurpleAercloudBlock;
import com.aetherteam.aetherii.block.natural.WisprootLogBlock;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIBlockStateProvider extends NitrogenBlockStateProvider {
    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
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

    public void dirtPath(Block block, Block dirtBlock) {
        ModelFile path = this.models().withExistingParent(this.name(block), this.mcLoc("block/dirt_path"))
                .texture("particle", this.modLoc("block/natural/" + this.name(dirtBlock)))
                .texture("top", this.modLoc("block/construction/" + this.name(block) + "_top"))
                .texture("side", this.modLoc("block/construction/" + this.name(block) + "_side"))
                .texture("bottom", this.modLoc("block/natural/" + this.name(dirtBlock)));
        this.getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false));
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
                .renderType(new ResourceLocation("translucent"))
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
        ModelFile rightModel = this.models().cubeBottomTop(blockName, right, back, front).renderType(new ResourceLocation("translucent"));
        ModelFile leftModel = this.models().cubeBottomTop(blockName, left, back, front).renderType(new ResourceLocation("translucent"));

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

    public void logDifferentTop(RotatedPillarBlock block, RotatedPillarBlock baseBlock) {
        this.axisBlock(block, this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
    }

    public void wisprootLog(WisprootLogBlock block) {
        String blockName = this.name(block);
        ResourceLocation side = this.extend(this.texture(this.name(block), "natural/"), "");
        ResourceLocation top = this.extend(this.texture(this.name(block), "natural/"), "_top");
        ResourceLocation sideMossy = this.extend(this.texture(this.name(block), "natural/"), "_mossy");
        ModelFile normal = this.models().cubeColumn(blockName, side, top);
        ModelFile horizontal = this.models().cubeColumnHorizontal(blockName + "_horizontal", side, top);
        ModelFile mossy = this.models().cubeColumn(blockName + "_mossy", sideMossy, top);

        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction.Axis axis = state.getValue(RotatedPillarBlock.AXIS);
            if (state.getValue(WisprootLogBlock.MOSSY)) {
                switch (axis) {
                    case X -> {
                        return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).rotationY(90).build();
                    }
                    case Y -> {
                        return ConfiguredModel.builder().modelFile(mossy).build();
                    }
                    case Z -> {
                        return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).build();
                    }
                }
            } else {
                switch (axis) {
                    case X -> {
                        return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).rotationY(90).build();
                    }
                    case Y -> {
                        return ConfiguredModel.builder().modelFile(normal).build();
                    }
                    case Z -> {
                        return ConfiguredModel.builder().modelFile(horizontal).rotationX(90).build();
                    }
                }
            }
            return ConfiguredModel.builder().build();
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
                .renderType(new ResourceLocation("cutout"));
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
            ModelFile model = this.models().cross(blockName + (lower ? (halfString + bottomAge) : (halfString + topAge)), location).renderType(new ResourceLocation("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void grass(Block block) {
        ModelFile grass = this.models().withExistingParent(this.name(block), this.modLoc("block/tri_tinted_cross"))
                .texture("particle", this.texture(this.name(block), "natural/"))
                .texture("cross_1", this.extend(this.texture(this.name(block), "natural/"), "_1"))
                .texture("cross_2", this.extend(this.texture(this.name(block), "natural/"), "_2"))
                .texture("cross_3", this.extend(this.texture(this.name(block), "natural/"), "_3"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(grass));
    }

    public void leavesPile(Block block, Block base) {
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
                    .renderType(new ResourceLocation("cutout"));
            return ConfiguredModel.builder().modelFile(model).build();
        }, AetherLeafPileBlock.PERSISTENT);
    }

    public void carpet(Block block, Block baseBlock, String location) {
        simpleBlock(block, models().singleTexture(name(block), mcLoc("block/carpet"), "wool", texture(location + name(baseBlock))));
    }

    public void skyrootCraftingTable(Block block, Block baseBlock, String location) {
        ResourceLocation baseTexture = new ResourceLocation(AetherII.MODID, "block/" + location + this.name(baseBlock));
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

    public void skyrootChest(Block block) {
        ModelFile chest = this.models().cubeAll(this.name(block), new ResourceLocation(AetherII.MODID, "block/construction/skyroot_planks"));
        this.chest(block, chest);
    }

    public void skyrootLadder(LadderBlock block) {
        ResourceLocation location = this.texture(this.name(block), "construction/");
        ModelFile ladder = models().withExistingParent(this.name(block), this.mcLoc("block/block")).renderType(new ResourceLocation("cutout")).ao(false)
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

//    public void grass(Block block, Block dirtBlock) {
//        this.grassBlock(block, block, dirtBlock);
//    }
//
//    public void grassBlock(Block baseBlock, Block blockForSnow, Block blockForDirt) {
//        ModelFile grass = this.grassBlock(baseBlock, blockForDirt);
//        ModelFile grassSnowed = this.cubeBottomTop(this.name(blockForSnow) + "_snow",
//                this.extend(this.texture(this.name(blockForSnow), "natural/"), "_snow"),
//                this.texture(this.name(blockForDirt), "natural/"),
//                this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
//        this.getVariantBuilder(baseBlock).forAllStates(state -> {
//            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
//            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
//        });
//    }
//
//    public ModelFile grassBlock(Block block, Block dirtBlock) {
//        return this.cubeBottomTop(this.name(block),
//                this.extend(this.texture(this.name(block), "natural/"), "_side"),
//                this.texture(this.name(dirtBlock), "natural/"),
//                this.extend(this.texture(this.name(block), "natural/"), "_top"));
//    }
//
//    public void grass(Block block,
//                         ResourceLocation upOutside, ResourceLocation downOutside,
//                         ResourceLocation northOutside, ResourceLocation southOutside,
//                         ResourceLocation westOutside, ResourceLocation eastOutside,
//                         ResourceLocation particle) {
//        ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
//                .texture("up", upOutside)
//                .texture("down", downOutside)
//                .texture("north", northOutside)
//                .texture("south", southOutside)
//                .texture("west", westOutside)
//                .texture("east", eastOutside)
//                .texture("particle", particle)
//                .element().from(0.0F, 16.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
//                .face(Direction.UP).texture("#up").uvs(0, 0, 16, 16).cullface(Direction.UP).end().end()
//                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 0.0F, 16.0F)
//                .face(Direction.DOWN).texture("#down").uvs(0, 0, 16, 16).cullface(Direction.DOWN).end().end()
//                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.0F)
//                .face(Direction.NORTH).texture("#north").uvs(0, 0, 16, 16).cullface(Direction.NORTH).end().end()
//                .element().from(0.0F, 0.0F, 16.0F).to(16.0F, 16.0F, 16.0F)
//                .face(Direction.SOUTH).texture("#south").uvs(0, 0, 16, 16).cullface(Direction.SOUTH).end().end()
//                .element().from(0.0F, 0.0F, 0.0F).to(0.0F, 16.0F, 16.0F)
//                .face(Direction.WEST).texture("#west").uvs(0, 0, 16, 16).cullface(Direction.WEST).end().end()
//                .element().from(16.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
//                .face(Direction.EAST).texture("#east").uvs(0, 0, 16, 16).cullface(Direction.EAST).end().end();
//        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
//    }
}
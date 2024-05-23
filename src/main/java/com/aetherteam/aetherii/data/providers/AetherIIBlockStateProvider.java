package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIBlockStateProvider extends NitrogenBlockStateProvider {
    public AetherIIBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
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

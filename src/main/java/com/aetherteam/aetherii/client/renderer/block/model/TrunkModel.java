package com.aetherteam.aetherii.client.renderer.block.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.block.model.part.TrunkModelPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.model.data.ModelData;

import java.util.List;

public record TrunkModel(TrunkModelPart model) implements DynamicBlockStateModel { //todo
    @Override
    public TextureAtlasSprite particleIcon() {
        return this.model.particleIcon();
    }

    @Override
    public Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
        return this;
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        ModelData data = level.getModelData(pos);
        parts.add(this.model);
    }

    @Override
    public TextureAtlasSprite particleIcon(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        return this.particleIcon();
    }

    public record Unbaked(TrunkModelPart.Unbaked model) implements CustomUnbakedBlockStateModel {
        public static final MapCodec<Unbaked> CODEC = TrunkModelPart.Unbaked.CODEC.xmap(TrunkModel.Unbaked::new, TrunkModel.Unbaked::model);
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "trunk_model_loader");

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            this.model.resolveDependencies(resolver);
        }

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            return new TrunkModel((TrunkModelPart) this.model.bake(baker));
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }
    }
}

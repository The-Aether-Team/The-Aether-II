package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record TrunkModel(Map<Holder, BlockModelPart> connections, TextureAtlasSprite particleIcon) implements DynamicBlockStateModel {
    @Override
    public void collectParts(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, RandomSource randomSource, List<BlockModelPart> list) {
        Map<String, WallSide> properties = TrunkBlock.getCornerProperties(blockAndTintGetter, blockPos);
        for (var entry : properties.entrySet()) {
            for (var connection : this.connections.entrySet()) {
                if (entry.getKey().equals(connection.getKey().name()) && entry.getValue().equals(connection.getKey().value())) {
                    list.add(connection.getValue());
                }
            }
        }
    }

    public record Unbaked(ResourceLocation corner, ResourceLocation cornerTall) implements CustomUnbakedBlockStateModel {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "trunk_corners");
        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                ResourceLocation.CODEC.fieldOf("corner").forGetter(Unbaked::corner),
                ResourceLocation.CODEC.fieldOf("corner_tall").forGetter(Unbaked::cornerTall)
        ).apply(builder, Unbaked::new));

        private static final Map<String, BlockModelRotation> CORNER_ROTATIONS = Map.of(
                "northwest_connection", BlockModelRotation.X0_Y0,
                "northeast_connection", BlockModelRotation.X0_Y90,
                "southeast_connection", BlockModelRotation.X0_Y180,
                "southwest_connection", BlockModelRotation.X0_Y270);

        @Override
        public BlockStateModel bake(ModelBaker modelBaker) {
            Map<Holder, BlockModelPart> connections = new HashMap<>();
            for (Map.Entry<String, BlockModelRotation> entry : CORNER_ROTATIONS.entrySet()) {
                connections.put(new Holder(entry.getKey(), WallSide.LOW), SimpleModelWrapper.bake(modelBaker, this.corner(), entry.getValue().withUvLock()));
                connections.put(new Holder(entry.getKey(), WallSide.TALL), SimpleModelWrapper.bake(modelBaker, this.cornerTall(), entry.getValue().withUvLock()));
            }
            return new TrunkModel(connections, List.copyOf(connections.values()).getFirst().particleIcon());
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.corner());
            resolver.markDependency(this.cornerTall());
        }

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }
    }

    public record Holder(String name, WallSide value) { }
}

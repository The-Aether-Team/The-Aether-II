package com.aetherteam.aetherii.client.renderer.block.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.aetherteam.aetherii.client.renderer.block.model.property.NamedModelProperty;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.model.data.ModelData;

import javax.annotation.Nullable;
import java.util.*;

public class TrunkModel extends DelegateBlockStateModel { //todo
    public static final NamedModelProperty<WallSide> NORTHEAST_CONNECTION = new NamedModelProperty<>("northeast_connection");
    public static final NamedModelProperty<WallSide> NORTHWEST_CONNECTION = new NamedModelProperty<>("northwest_connection");
    public static final NamedModelProperty<WallSide> SOUTHEAST_CONNECTION = new NamedModelProperty<>("southeast_connection");
    public static final NamedModelProperty<WallSide> SOUTHWEST_CONNECTION = new NamedModelProperty<>("southwest_connection");
    public static final List<NamedModelProperty<WallSide>> CONNECTIONS = List.of(NORTHEAST_CONNECTION, NORTHWEST_CONNECTION, SOUTHEAST_CONNECTION, SOUTHWEST_CONNECTION);
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);

    private final Map<TrunkModel.Holder, BlockModelPart> connections;

    protected TrunkModel(BlockStateModel delegate, Map<TrunkModel.Holder, BlockModelPart> connections) {
        super(delegate);
        this.connections = connections;
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        ModelData modelData = level.getModelData(pos);
        if (modelData == ModelData.EMPTY) {
            Map<String, WallSide> properties = TrunkBlock.getCornerProperties(level, pos);
            ModelData.Builder modelDataBuilder = ModelData.builder();
            for (var entry : properties.entrySet()) {
                NamedModelProperty<WallSide> property = getPropertyForName(entry.getKey());
                if (property != null) {
                    modelDataBuilder.with(property, entry.getValue());
                }
            }
            modelData = modelDataBuilder.build();
        }

        for (BlockModelPart part : this.delegate.collectParts(level, pos, state, random)) {
            parts.add(part);
            for (var connection : this.connections.entrySet()) {
                TrunkModel.Holder holder = connection.getKey();
                this.getConnectionFromString(holder.name(), modelData).ifPresent((wallSide) -> {
                    QuadCollection.Builder builder = new QuadCollection.Builder();
                    for (Direction side : DIRECTIONS) {
                        for (BakedQuad quad : connection.getValue().getQuads(side)) {
                            if (wallSide != WallSide.NONE && wallSide == holder.value()) {
                                builder.addUnculledFace(quad);
                            } else {
                                builder.addCulledFace(side, quad);
                            }
                        }
                    }
                    parts.add(new SimpleModelWrapper(builder.build(), part.useAmbientOcclusion(), part.particleIcon(), part.getRenderType(state)));
                });
            }
        }
    }

//    @Override
//    public TextureAtlasSprite particleIcon() {
//        return this.model.particleIcon();
//    }
//
//    @Override
//    public Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
//        return this;
//    }
//
//    @Override
//    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
//        ModelData data = level.getModelData(pos);
//        parts.add(this.model);
//    }
//
//    @Override
//    public TextureAtlasSprite particleIcon(BlockAndTintGetter level, BlockPos pos, BlockState state) {
//        return this.particleIcon();
//    }

    private Optional<WallSide> getConnectionFromString(String name, ModelData data) {
        for (var properties : CONNECTIONS) {
            if (name.equals(properties.getName())) {
                WallSide connection = data.get(properties);
                if (connection != null) {
                    return Optional.of(connection);
                }
            }
        }
        return Optional.empty();
    }

    @Nullable
    protected static NamedModelProperty<WallSide> getPropertyForName(String name) {
        return switch (name) {
            case "northeast_connection" -> NORTHEAST_CONNECTION;
            case "northwest_connection" -> NORTHWEST_CONNECTION;
            case "southeast_connection" -> SOUTHEAST_CONNECTION;
            case "southwest_connection" -> SOUTHWEST_CONNECTION;
            default -> null;
        };
    }

    public static class Unbaked implements CustomUnbakedBlockStateModel {
        public static final MapCodec<TrunkModel.Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        BlockStateModel.Unbaked.CODEC.fieldOf("model").forGetter((unbaked) -> unbaked.model),
                        Codec.unboundedMap(TrunkModel.Holder.CODEC, Variant.CODEC).fieldOf("connections").forGetter((unbaked) -> unbaked.connections)
                ).apply(instance, TrunkModel.Unbaked::new));
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "trunk_model_loader");

        public final BlockStateModel.Unbaked model;
        private final Map<TrunkModel.Holder, Variant> connections;

        public Unbaked(BlockStateModel.Unbaked model, Map<TrunkModel.Holder, Variant> connections) {
            this.model = model;
            this.connections = connections;
        }

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            Map<TrunkModel.Holder, BlockModelPart> bakedConnections = new LinkedHashMap<>();
            for (var connection : this.connections.entrySet()) {
                bakedConnections.put(connection.getKey(), connection.getValue().withModel(connection.getValue().modelLocation()).bake(baker));
            }
            return new TrunkModel(this.model.bake(baker), bakedConnections);
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            this.model.resolveDependencies(resolver);
            for (var connection : this.connections.entrySet()) {
                resolver.markDependency(connection.getValue().modelLocation());
            }
        }

        @Override
        public MapCodec<TrunkModel.Unbaked> codec() {
            return CODEC;
        }
    }

    public record Holder(String name, WallSide value) {
        public static final StringRepresentable.EnumCodec<WallSide> WALL_SIDE_CODEC = StringRepresentable.fromEnum(WallSide::values);

        public static final Codec<TrunkModel.Holder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(Holder::name),
                WALL_SIDE_CODEC.fieldOf("value").forGetter(Holder::value)
        ).apply(instance, TrunkModel.Holder::new));
    }
}

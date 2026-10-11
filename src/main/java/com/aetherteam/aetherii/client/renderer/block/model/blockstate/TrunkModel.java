package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrunkModel implements DynamicBlockStateModel {
    private final Map<Holder, BlockStateModelPart> connections;
    private final Material.Baked particleIcon;
    @BakedQuad.MaterialFlags
    private final int materialFlags;

    public TrunkModel(Map<Holder, BlockStateModelPart> connections, Material.Baked particleIcon) {
        this.connections = connections;
        this.particleIcon = particleIcon;
        this.materialFlags = computeMaterialFlags(connections.values());
    }

    @Override
    public void collectParts(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, RandomSource randomSource, List<BlockStateModelPart> list) {
        Map<String, WallSide> properties = TrunkBlock.getCornerProperties(blockAndTintGetter, blockPos);
        for (var entry : properties.entrySet()) {
            for (var connection : this.connections.entrySet()) {
                if (entry.getKey().equals(connection.getKey().name()) && entry.getValue().equals(connection.getKey().value())) {
                    list.add(connection.getValue());
                }
            }
        }
    }

    @Override
    public Material.Baked particleMaterial() {
        return this.particleIcon;
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return this.materialFlags;
    }

    @BakedQuad.MaterialFlags
    private static int computeMaterialFlags(Collection<BlockStateModelPart> list) {
        int flags = 0;
        for (BlockStateModelPart entry : list) {
            flags |= entry.materialFlags();
        }
        return flags;
    }

    public record Unbaked(Identifier corner, Identifier cornerTall) implements CustomUnbakedBlockStateModel {
        public static final Identifier ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "trunk_corners");
        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                Identifier.CODEC.fieldOf("corner").forGetter(Unbaked::corner),
                Identifier.CODEC.fieldOf("corner_tall").forGetter(Unbaked::cornerTall)
        ).apply(builder, Unbaked::new));

        private static final Map<String, BlockModelRotation> CORNER_ROTATIONS = Map.of(
                "northwest_connection", BlockModelRotation.IDENTITY,
                "northeast_connection", BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_90),
                "southeast_connection", BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_180),
                "southwest_connection", BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_270));

        @Override
        public BlockStateModel bake(ModelBaker modelBaker) {
            Map<Holder, BlockStateModelPart> connections = new HashMap<>();
            for (Map.Entry<String, BlockModelRotation> entry : CORNER_ROTATIONS.entrySet()) {
                connections.put(new Holder(entry.getKey(), WallSide.LOW), SimpleModelWrapper.bake(modelBaker, this.corner(), entry.getValue().withUvLock()));
                connections.put(new Holder(entry.getKey(), WallSide.TALL), SimpleModelWrapper.bake(modelBaker, this.cornerTall(), entry.getValue().withUvLock()));
            }
            return new TrunkModel(connections, List.copyOf(connections.values()).getFirst().particleMaterial());
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

    public record Holder(String name, WallSide value) {
    }
}

package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmissiveModel extends CuboidItemModelWrapper {
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);
    public static final Transformation TRANSFORMATION = new Transformation(new Vector3f(0.0F, 0.0F, 0.0F), null, new Vector3f(1.0F, 1.0F, 1.0F), null);

    public EmissiveModel(QuadCollection quads, ModelRenderProperties properties, Matrix4fc transformation) {
        super(List.of(), quads, properties, transformation);
    }

    public record Unbaked(Identifier model, Optional<Transformation> transformation) implements ItemModel.Unbaked {
        public static final MapCodec<EmissiveModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(EmissiveModel.Unbaked::model),
                Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(EmissiveModel.Unbaked::transformation)
        ).apply(instance, EmissiveModel.Unbaked::new));

        public Unbaked(Identifier model) {
            this(model, Optional.of(TRANSFORMATION));
        }

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.model);
        }

        public ItemModel bake(ItemModel.BakingContext context, Matrix4fc transformation) {
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.model);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            QuadCollection originalQuads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY);
            QuadCollection.Builder newQuadBuilder = new QuadCollection.Builder();
            for (Direction direction : DIRECTIONS) {
                List<BakedQuad> originalBakedQuads = originalQuads.getQuads(direction);
                originalBakedQuads = originalBakedQuads.stream().map(oldQuad -> new BakedQuad(
                        oldQuad.position0(),
                        oldQuad.position1(),
                        oldQuad.position2(),
                        oldQuad.position3(),
                        oldQuad.packedUV0(),
                        oldQuad.packedUV1(),
                        oldQuad.packedUV2(),
                        oldQuad.packedUV3(),
                        oldQuad.direction(),
                        new BakedQuad.MaterialInfo(oldQuad.materialInfo().sprite(), oldQuad.materialInfo().layer(), oldQuad.materialInfo().itemRenderType(), oldQuad.materialInfo().tintIndex(), oldQuad.materialInfo().shade(), 15, oldQuad.materialInfo().ambientOcclusion()),
                        oldQuad.bakedNormals(),
                        oldQuad.bakedColors()
                )).toList();
                for (BakedQuad newQuad : originalBakedQuads) {
                    if (direction == null) {
                        newQuadBuilder.addUnculledFace(newQuad);
                    } else {
                        newQuadBuilder.addCulledFace(direction, newQuad);
                    }
                }
            }
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);
            Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation);
            return new EmissiveModel(newQuadBuilder.build(), properties, modelTransform);
        }

        public MapCodec<EmissiveModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}

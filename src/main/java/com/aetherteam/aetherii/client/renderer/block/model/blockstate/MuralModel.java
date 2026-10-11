package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

//public class MuralModel extends DelegateBlockStateModel { //TODO
//    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);
//
//    public MuralModel(BlockStateModel delegate) {
//        super(delegate);
//	}
//
//    @Override
//    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
//        MuralBlockEntity.MuralData data = level.getModelData(pos).get(MuralBlockEntity.MuralData.PROPERTY);
//        if (data == null) {
//            super.collectParts(level, pos, state, random, parts);
//            return;
//        }
//
//        List<BlockModelPart> modelParts = AetherIIClientCaches.CACHED_MURAL_BLOCK_PARTS.get(data);
//        if (modelParts != null) {
//            parts.addAll(modelParts);
//            return;
//        }
//
//        List<BlockModelPart> blockModelParts = this.delegate.collectParts(level, pos, state, random);
//        ImmutableList<BlockModelPart> rebakedModelParts = this.rebakeModelParts(blockModelParts, state, data);
//        AetherIIClientCaches.CACHED_MURAL_BLOCK_PARTS.put(data, rebakedModelParts);
//        parts.addAll(rebakedModelParts);
//    }
//
//    @NotNull
//    private ImmutableList<BlockModelPart> rebakeModelParts(List<BlockModelPart> blockModelParts, BlockState state, MuralBlockEntity.MuralData data) {
//        ImmutableList.Builder<BlockModelPart> partBuilder = ImmutableList.builder();
//        for (BlockModelPart part : blockModelParts) {
//            QuadCollection.Builder builder = new QuadCollection.Builder();
//            for (Direction side : DIRECTIONS) {
//                for (BakedQuad originalQuad : part.getQuads(side)) {
//                    BakedQuad newModelQuad = side != data.facing() ? originalQuad : rebakeQuad(data.section(), originalQuad);
//                    if (side == null) {
//                        builder.addUnculledFace(newModelQuad);
//                    } else {
//                        builder.addCulledFace(side, newModelQuad);
//                    }
//                }
//            }
//            partBuilder.add(new SimpleModelWrapper(builder.build(), part.useAmbientOcclusion(), part.particleIcon(), part.getRenderType(state)));
//        }
//		return partBuilder.build();
//    }
//
//    @NotNull
//    public static BakedQuad rebakeQuad(MuralSection section, BakedQuad quad) {
//        int[] bakedBuffer = quad.vertices();
//        int[] rebakedSection = Arrays.copyOf(bakedBuffer, bakedBuffer.length); // Avoids mutating the original model in-memory
//
//        TextureAtlasSprite muralSprite = AetherIIAtlases.MURAL_MATERIALS.get(section).sprite();
//        BlockElementFace.UVs uvs = shrinkUVs(muralSprite, new BlockElementFace.UVs(0, 0, 16, 16));
//
//        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
//            int bufferOffset = vertexIndex * 8;
//
//            float u = BlockElementFace.getU(uvs, Quadrant.R0, vertexIndex);
//            float v = BlockElementFace.getV(uvs, Quadrant.R0, vertexIndex);
//
//            rebakedSection[bufferOffset + 4] = Float.floatToRawIntBits(muralSprite.getU(u));
//            rebakedSection[bufferOffset + 5] = Float.floatToRawIntBits(muralSprite.getV(v));
//        }
//        return new BakedQuad(rebakedSection, quad.tintIndex(), quad.direction(), muralSprite, quad.shade(), quad.lightEmission(), quad.hasAmbientOcclusion());
//    }
//
//    private static BlockElementFace.UVs shrinkUVs(TextureAtlasSprite sprite, BlockElementFace.UVs uvs) {
//        float f = uvs.minU();
//        float f1 = uvs.minV();
//        float f2 = uvs.maxU();
//        float f3 = uvs.maxV();
//        float f4 = sprite.uvShrinkRatio();
//        float f5 = (f + f + f2 + f2) / 4.0F;
//        float f6 = (f1 + f1 + f3 + f3) / 4.0F;
//        return new BlockElementFace.UVs(Mth.lerp(f4, f, f5), Mth.lerp(f4, f1, f6), Mth.lerp(f4, f2, f5), Mth.lerp(f4, f3, f6));
//    }
//}

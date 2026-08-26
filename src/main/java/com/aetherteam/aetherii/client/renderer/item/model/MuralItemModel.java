package com.aetherteam.aetherii.client.renderer.item.model;

//public class MuralItemModel extends CuboidItemModelWrapper {
//    public MuralItemModel(List<ItemTintSource> tints, QuadCollection quads, ModelRenderProperties properties, Matrix4fc transformation) {
//        super(tints, quads, properties, transformation);
//    }
//
//    @Override
//    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int i) {
//        BlockModelWrapperAccessor accessor = (BlockModelWrapperAccessor) this;
//        MuralSection section = stack.get(AetherIIDataComponents.MURAL_SECTION);
//        List<BakedQuad> quads = AetherIIClientCaches.CACHED_MURAL_ITEM_PARTS.get(section);
//        if (section != null) {
//            if (quads == null) {
//                quads = new ArrayList<>(accessor.aether_ii$getQuads());
//                quads.replaceAll((originalQuad) -> {
////                    if (originalQuad.direction() == Direction.NORTH) { //todo
////                        return MuralModel.rebakeQuad(section, originalQuad);
////                    }
//                    return originalQuad;
//                });
//                AetherIIClientCaches.CACHED_MURAL_ITEM_PARTS.put(section, quads);
//            }
//            renderState.appendModelIdentityElement(section);
//        }
//        List<BakedQuad> finalQuads = quads;
//        accessor.aether_ii$setQuads(finalQuads);
//        accessor.aether_ii$setExtents(Suppliers.memoize(() -> computeExtents(finalQuads)));
//        super.update(renderState, stack, modelResolver, displayContext, level, owner, i);
//    }
//
//    public record Unbaked(Identifier model) implements ItemModel.Unbaked {
//        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
//                Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model)
//        ).apply(instance, Unbaked::new));
//
//        public void resolveDependencies(ResolvableModel.Resolver resolver) {
//            resolver.markDependency(this.model);
//        }
//
//        public ItemModel bake(ItemModel.BakingContext context, Matrix4fc matrix4f) {
//            ModelBaker modelbaker = context.blockModelBaker();
//            ResolvedModel resolvedmodel = modelbaker.getModel(this.model);
//            TextureSlots textureslots = resolvedmodel.getTopTextureSlots();
//            List<BakedQuad> list = resolvedmodel.bakeTopGeometry(textureslots, modelbaker, BlockModelRotation.IDENTITY).getAll();
//            ModelRenderProperties modelrenderproperties = ModelRenderProperties.fromResolvedModel(modelbaker, resolvedmodel, textureslots);
//            ChunkSectionLayerGroup chunkSectionLayerGroup = resolvedmodel.getTopAdditionalProperties().getOptional(NeoForgeModelProperties.TRANSFORM);
//            ChunkSectionLayer chunkSectionLayer = chunkSectionLayerGroup == null ? null : chunkSectionLayerGroup.entityItem();
//            return new MuralItemModel(List.of(), list, modelrenderproperties, (stack) -> chunkSectionLayer);
//        }
//
//        public MapCodec<Unbaked> type() {
//            return MAP_CODEC;
//        }
//    }
//}

package com.aetherteam.aetherii.data.providers;

//import com.aetherteam.aetherii.AetherII;
//import com.aetherteam.aetherii.entity.passive.Moa;
//import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.level.block.Block;
//import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
//import net.neoforged.neoforge.client.model.generators.ModelBuilder;
//import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//
//public abstract class AetherIIItemModelProvider extends NitrogenItemModelProvider {
//    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
//        super(output, id, helper);
//    }
//
//    public void gliderItem(Item item, boolean hasAbility) {
//        ItemModelBuilder normalInventorySprite = this.nested().parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", this.modLoc("item/miscellaneous/" + this.itemName(item)));
//
//        ItemModelBuilder closedGliderModelBase = this.nested().parent(this.getExistingFile(this.modLoc("item/aercloud_glider_closed"))).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -80.0F).translation(5.75F, -2.0F, 4.15F).scale(0.75F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0.0F, 0.0F, -90.0F).translation(7.5F, 0.0F, 4.0F).end()
//                .end();
//        ItemModelBuilder openGliderModelBase = this.nested().parent(this.getExistingFile(this.modLoc("item/aercloud_glider_open"))).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90.0F, 0.0F, -35.0F).translation(11.55F, -2.0F, -0.1F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0.0F, 0.0F, 0.0F).translation(0.0F, 9.0F, -6.0F).end()
//                .end();
//
//        ItemModelBuilder openPredicate = this.withExistingParent(this.itemName(item) + "_open", this.modLoc("item/aercloud_glider_open")).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                .customLoader((itemModelBuilder, existingFileHelper) ->
//                        SeparateTransformsModelBuilder.begin(itemModelBuilder, existingFileHelper)
//                                .base(openGliderModelBase)
//                                .perspective(ItemDisplayContext.GUI, normalInventorySprite)
//                                .perspective(ItemDisplayContext.GROUND, normalInventorySprite)
//                                .perspective(ItemDisplayContext.FIXED, normalInventorySprite)
//                ).end();
//        ItemModelBuilder closedPredicate = this.withExistingParent(this.itemName(item) + "_closed", this.modLoc("item/aercloud_glider_closed")).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                .customLoader((itemModelBuilder, existingFileHelper) ->
//                        SeparateTransformsModelBuilder.begin(itemModelBuilder, existingFileHelper)
//                                .base(closedGliderModelBase)
//                                .perspective(ItemDisplayContext.GUI, normalInventorySprite)
//                                .perspective(ItemDisplayContext.GROUND, normalInventorySprite)
//                                .perspective(ItemDisplayContext.FIXED, normalInventorySprite)
//                ).end();
//
//        if (!hasAbility) {
//            this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                    .texture("particle", this.modLoc("item/miscellaneous/" + this.itemName(item)))
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 0.0F).model(closedPredicate).end()
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 1.0F).model(openPredicate).end();
//        } else {
//            ItemModelBuilder dullInventorySprite = this.nested().parent(this.getExistingFile(this.mcLoc("item/generated"))).texture("layer0", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_dull"));
//
//            ItemModelBuilder dullOpenPredicate = this.withExistingParent(this.itemName(item) + "_dull_open", this.modLoc("item/aercloud_glider_open")).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                    .customLoader((itemModelBuilder, existingFileHelper) ->
//                            SeparateTransformsModelBuilder.begin(itemModelBuilder, existingFileHelper)
//                                    .base(openGliderModelBase)
//                                    .perspective(ItemDisplayContext.GUI, dullInventorySprite)
//                                    .perspective(ItemDisplayContext.GROUND, dullInventorySprite)
//                                    .perspective(ItemDisplayContext.FIXED, dullInventorySprite)
//                    ).end();
//            ItemModelBuilder dullClosedPredicate = this.withExistingParent(this.itemName(item) + "_dull_closed", this.modLoc("item/aercloud_glider_closed")).texture("glider", this.modLoc("item/miscellaneous/" + this.itemName(item) + "_model"))
//                    .customLoader((itemModelBuilder, existingFileHelper) ->
//                            SeparateTransformsModelBuilder.begin(itemModelBuilder, existingFileHelper)
//                                    .base(closedGliderModelBase)
//                                    .perspective(ItemDisplayContext.GUI, dullInventorySprite)
//                                    .perspective(ItemDisplayContext.GROUND, dullInventorySprite)
//                                    .perspective(ItemDisplayContext.FIXED, dullInventorySprite)
//                    ).end();
//
//            this.withExistingParent(this.itemName(item), this.mcLoc("item/generated"))
//                    .texture("particle", this.modLoc("item/miscellaneous/" + this.itemName(item)))
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 0.0F).predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull"), 0.0F).model(closedPredicate).end()
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 1.0F).predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull"), 0.0F).model(openPredicate).end()
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 0.0F).predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull"), 1.0F).model(dullClosedPredicate).end()
//                    .override().predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), 1.0F).predicate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull"), 1.0F).model(dullOpenPredicate).end();
//        }
//    }
//
//    public void itemBlockGrass(Block block, String location) {
//        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
//                .texture("layer0", this.texture(this.blockName(block) + "_1", location))
//                .texture("layer1", this.texture(this.blockName(block) + "_2", location))
//                .texture("layer2", this.texture(this.blockName(block) + "_3", location));
//    }
//
//    public void orangeTree(Block block) {
//        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated")).texture("layer0", this.texture(this.blockName(block) + "_bottom_0", "natural/"));
//    }
//
//    public void pointedStone(Block block, String location) {
//        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated")).texture("layer0", this.texture(this.blockName(block), location))
//                .transforms()
//                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, 100.0F, 0.0F).translation(-1.0F, -1.0F, 0.0F).scale(0.9F, 0.9F, 0.9F).end()
//                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0.0F, 100.0F, 0.0F).translation(0.0F, -2.0F, 0.0F).scale(0.9F, 0.9F, 0.9F).end()
//                .end();
//    }
//
//    public void aercloudItem(Block block) {
//        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube_all")).texture("all", this.texture(this.blockName(block), "natural/")).renderType(ResourceLocation.withDefaultNamespace("translucent"));
//    }
//}